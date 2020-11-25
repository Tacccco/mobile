package com.example.android.contentuser;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button contactBtn;
    Button calendarBtn;
    Button useInfoBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactBtn =findViewById(R.id.contact_btn);
        calendarBtn = findViewById(R.id.calendar_btn);
        useInfoBtn = findViewById(R.id.userinfo_btn);
        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContacts();
            }
        });
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendar();
            }
        });
        useInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserInfo();
            }
        });
    }
    void openContacts(){
        Intent intent = new Intent(this, ContactView.class);
        startActivity(intent);
    }
    void openCalendar(){
        Intent intent = new Intent(this, CalendarView.class);
        startActivity(intent);
    }
    void openUserInfo(){
        Intent intent = new Intent(this, UserInfoView.class);
        startActivity(intent);
    }

}
