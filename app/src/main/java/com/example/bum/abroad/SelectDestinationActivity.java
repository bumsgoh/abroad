package com.example.bum.abroad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;

public class SelectDestinationActivity extends AppCompatActivity {

    ArrayList<String> countryArr;
    ArrayList<String> cityArr;
    ArrayAdapter<String> cityAdp;
    Spinner citySpin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_destination);

        countryArr = new ArrayList<>();
        addCountryArr();

        citySpin = findViewById(R.id.spinner2);
        cityArr = new ArrayList<>();
        cityAdp = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, cityArr);

        Spinner countrySpin = findViewById(R.id.spinner);
        ArrayAdapter<String> countryAdp = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, countryArr);
        countryAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpin.setAdapter(countryAdp);
        countrySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        removeArr();
                        cityArr.add("뮌헨");
                        cityArr.add("프랑크푸르트");
                        cityArr.add("베를린");
                        break;
                    case 1:
                        removeArr();
                        cityArr.add("바르셀로나");
                        break;
                    case 2:
                        removeArr();
                        cityArr.add("런던");
                        break;
                    case 3:
                        removeArr();
                        cityArr.add("파리");
                        break;
                    default:
                        removeArr();
                        break;
                }
                citySpin.setAdapter(cityAdp);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }//onCreate End

    public void addCountryArr(){
        countryArr.add("독일");
        countryArr.add("스페인");
        countryArr.add("영국");
        countryArr.add("프랑스");
    }

    public void removeArr(){
        for(int i = 0; i < cityArr.size(); i++){
            cityArr.remove(i);
        }
    }
}
