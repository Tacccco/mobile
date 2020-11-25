package com.onon.android.messageapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Compose extends AppCompatActivity {
    SmsManager smsManager ;
    EditText number;
    EditText sms;
    Button btn_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        smsManager = SmsManager.getDefault();
        sms = findViewById(R.id.message);
        number = findViewById(R.id.number);
        btn_send = findViewById(R.id.send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMSMessage();
            }
        });

    }
    protected void sendSMSMessage() {

        smsManager.sendTextMessage(number.getText().toString(), null, sms.getText().toString(), null, null);
    }


}
