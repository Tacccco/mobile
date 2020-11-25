package com.onon.android.messagerecievingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    IntentFilter intentFilter;
    MessageReceiver messageReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        messageReceiver = new MessageReceiver();
    }

    @Override
    protected void onResume() {
        registerReceiver(messageReceiver, intentFilter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageReceiver);
        super.onPause();
    }

    private class MessageReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String message="";
            System.out.println("Recived smethig");
            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus");
            for(Object x: pdus){
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) x);
                message += "Address" + smsMessage.getOriginatingAddress() + "" + smsMessage.getMessageBody();

            }
            Toast.makeText(context, "Message:"+message, Toast.LENGTH_LONG).show();
        }
    }

}
