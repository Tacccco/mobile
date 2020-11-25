package com.onon.android.weatherapplication;

import android.app.VoiceInteractor;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    FragmentManager fragmentManager;
    FrameLayout frameLayout;
    //FragmentTransaction fragmentTransaction;
    Button btn2;
    Button btn1;
    Button btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        fragmentManager = getSupportFragmentManager();
        frameLayout = findViewById(R.id.frag_cont);

        //fragmentTransaction = fragmentManager.beginTransaction();

        btn2 = findViewById(R.id.btn_2);
        btn1 = findViewById(R.id.btn_1);
        btn3 = findViewById(R.id.btn_3);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragmentFiveDayWeather();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragmentCityWeather();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDayIntervalWeather();
            }
        });
    }
    void openFragmentCityWeather(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_cont, new CityWeather());
        try {
            fragmentTransaction.commit();
        }catch (IllegalStateException e){
            System.out.println("Ignore");
        }
    }
    void openFragmentFiveDayWeather(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_cont, new FiveDaysWeather());
        try {
            fragmentTransaction.commit();
        }catch (IllegalStateException e){
            System.out.println("Ignore");
        }
    }
    void openDayIntervalWeather(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_cont, new DayIntervalWeather());
        try {
            fragmentTransaction.commit();
        }catch (IllegalStateException e){
            System.out.println("Ignore");
        }
    }


}
