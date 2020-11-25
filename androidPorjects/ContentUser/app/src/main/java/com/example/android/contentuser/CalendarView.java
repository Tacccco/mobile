package com.example.android.contentuser;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CalendarView extends AppCompatActivity {
    ListView calendarList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);
        calendarList = findViewById(R.id.cal_List);
        fetchCalendar();
    }
    public void fetchCalendar(){
        ContentResolver contentResolver = getContentResolver();
        Uri uri = CalendarContract.Events.CONTENT_URI;
        ArrayList<String> contacts = new ArrayList<>();
        String[] projection = {CalendarContract.Events.TITLE,CalendarContract.Events.DTSTART};
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;
        @SuppressLint("MissingPermission") Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
        if(cursor != null) {

            while (cursor.moveToNext()) {
                String event = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
                long dt = cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART));
                Log.i("@myEvents", event);
                //TimeZone tz = TimeZone.getTimeZone("Mongolia/Ulaanbaatar");
                Date date = new Date(dt);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                Date current = new Date();
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(current);
                if( calendar.get(Calendar.MONTH) - calendar2.get(Calendar.MONTH)==1 && calendar2.get(Calendar.WEEK_OF_MONTH) == 4 && calendar.get(Calendar.WEEK_OF_MONTH) == 1) contacts.add(event+"\n" + date);
                else if(calendar.get(Calendar.MONTH)==calendar2.get(Calendar.MONTH)  && calendar.get(Calendar.WEEK_OF_MONTH) - calendar2.get(Calendar.WEEK_OF_MONTH) == 1) contacts.add(event+"\n" + date);

            }
            calendarList.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, contacts));
        }
    }
}
