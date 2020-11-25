package com.onon.android.weatherapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class FiveDaysWeather extends Fragment {

    ArrayList<String> list;
    ListView listView;
    ArrayAdapter adapter;
    boolean isFinished = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_five_days_weather, container, false);
        list = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);

        listView = view.findViewById(R.id.dayWeather_list);

        fetchFiveDaysWeather();
        while (isFinished);
        listView.setAdapter(adapter);
        return view;
    }
    void fetchFiveDaysWeather(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?q=Paris,fr&mode=xml&units=metric&appid=c01e5473228a5d6ab58acdc4df822cb1");
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.setReadTimeout(10000);
                    connection.setConnectTimeout(15000);
                    connection.connect();

                    InputStream stream = connection.getInputStream();
                    XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = xmlPullParserFactory.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(stream, null);

                    int event;
                    event = parser.getEventType();
                    String time = null;
                    String symbol = null;
                    String temp = null;
                    String text = null;
                    while(event != XmlPullParser.END_DOCUMENT){
                        String name = parser.getName();

                        //Log.i("TAG anme", name);
                        switch (event){
                            case XmlPullParser.START_TAG:
                                if(name.equals("temperature")) temp = parser.getAttributeValue(null, "value");
                                if(name.equals("symbol")) symbol = parser.getAttributeValue(null, "name");
                                if(name.equals("time")) time = parser.getAttributeValue(null, "from");


                                break;
                            case XmlPullParser.TEXT:
                                text = parser.getText();
                                break;
                            case  XmlPullParser.END_TAG:
                                if(name.equals("time")){
                                    list.add("At time " + time+"\n" + symbol+ " "+ temp);
                                   // adapter.notifyDataSetChanged();
                                }
                                if(name.equals("name"))  list.add(text);
                                break;
                        }
                        event = parser.next();
                    }
                isFinished =false;
                stream.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }

}
