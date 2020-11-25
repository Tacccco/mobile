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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CityWeather extends Fragment {
    RequestQueue requestQueue;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_weather, container, false);
        requestQueue = Volley.newRequestQueue(getActivity());
        listView = view.findViewById(R.id.city_view);
        list = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        fetchWeather("London");
        fetchWeather("Berlin");
        fetchWeather("Moscow");
        fetchWeather("Ulaanbaatar");
        fetchWeather("Paris");

        return view;
    }

    void fetchWeather(final String city){
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&appid=c01e5473228a5d6ab58acdc4df822cb1";

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET, url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray weather_arr = response.getJSONArray("weather");
                            JSONObject weather = weather_arr.getJSONObject(0);
                            String desc = weather.getString("description");
                            Double temp = response.getJSONObject("main").getDouble("temp");
                            Log.e("Response:", city+" "+desc +" "+temp);
                            list.add(city+"\n"+desc +" "+temp+"'C");
                            adapter.notifyDataSetChanged();
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
