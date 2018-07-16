package com.example.bum.abroad;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.bum.abroad.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignUpActivity extends AppCompatActivity {

    private static final int PICK_FROM_ALBUM = 10;
    private EditText email;
    private EditText name;
    private EditText password;
    private Button signup;
    private ImageView profile;
    private Uri imageUri;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        profile = findViewById(R.id.signUpActivity_image_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });
        email = findViewById(R.id.signUpActivity_editText_email);
        name = findViewById(R.id.signUpActivity_editText_name);
        password = findViewById(R.id.signUpActivity_editText_password);
        signup = findViewById(R.id.signUpActivity_button_signUp);

        //회원가입 버튼 리스너.
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });

    }//onCreate End

    private void addUser() {

        if (email.getText().toString() == null || name.getText().toString() == null || password.getText().length() < 6) {
            return;
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        final String uid = task.getResult().getUser().getUid();
                        final StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("userImages").child(uid);
                        imageRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                String imageUrl = imageRef.getDownloadUrl().toString();

                                UserModel userModel = new UserModel();
                                userModel.userName = name.getText().toString();
                                userModel.profileImageUrl = imageUrl;

                                FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel);

                            }
                        });


                    }
                });




//        FirebaseAuth.getInstance()
//                .createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
//                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        final String uid = task.getResult().getUser().getUid();
//                        FirebaseStorage.getInstance().getReference().child("userImages").child(uid).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                                @SuppressWarnings("VisibleForTests")
//                                String imageUrl = task.getResult().getDownloadUrl().toString();
//
//                                UserModel userModel = new UserModel();
//                                userModel.userName = name.getText().toString();
//                                userModel.profileImageUrl = imageUrl;
//
//                                FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel);
//
//
//                            }
//                        });
//
//
//
//
//                    }
//                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK) {
            profile.setImageURI(data.getData()); //프로필 뷰 바꾸기
            imageUri = data.getData(); //이미지 경로 원
        }
    }
}
