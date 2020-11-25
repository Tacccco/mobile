package com.onon.android.weatherapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class DayIntervalWeather extends Fragment {
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter adapter;
    RequestQueue requestQueue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_interval_weather, container, false);
        requestQueue = Volley.newRequestQueue(getActivity());
        listView = view.findViewById(R.id.listView);
        list = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        fetchWeather("Ulaanbaatar,mn");
        return view;
    }
    void fetchWeather(String city){
        String url = "https://api.openweathermap.org/data/2.5/forecast?q="+city+"&units=metric&cnt=10&appid=c01e5473228a5d6ab58acdc4df822cb1";

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET, url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray weatherList  = response.getJSONArray("list");
                            for(int i = 0; i < weatherList.length(); i++){
                                JSONObject node = weatherList.getJSONObject(i);
                                JSONArray weather_info = node.getJSONArray("weather");
                                Long dt = node.getLong("dt");
                                /*LocalDate today = LocalDate.now();
                                LocalDate tomorrow = today.plusDays(1);
                                String tm_Str = tomorrow.toString();//*/
                                Date date = new Date(dt*1000);
                                Date today = new Date();
                                System.out.println(date);
                                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                                Calendar calendar = GregorianCalendar.getInstance();
                                calendar.setTime(date);
                                Calendar calendar2 = GregorianCalendar.getInstance();
                                calendar2.setTime(today);
                                calendar2.add(Calendar.DATE, 1);
                                today = calendar2.getTime();
                                String dt1 = format.format(date);
                                String dt2 = format.format(today);
                                System.out.println(dt1 +" "+dt2+" "+calendar.get(Calendar.HOUR));
                                if(dt1.equals(dt2) && (calendar.get(Calendar.HOUR)%6==0)){
                                    JSONObject main_info = node.getJSONObject("main");
                                    String temp = main_info.getString("temp");

                                    String desc = weather_info.getJSONObject(0).getString("description");
                                    list.add(date + "\n"+ desc+" "+temp+"'C");
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error:", error.toString());
                    }
                }
        );


        requestQueue.add(objectRequest);
    }


}
