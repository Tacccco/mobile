package com.onon.android.messageapp;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static android.telephony.PhoneStateListener.LISTEN_CALL_STATE;
import static android.telephony.PhoneStateListener.LISTEN_CELL_INFO;
import static android.telephony.PhoneStateListener.LISTEN_CELL_LOCATION;
import static android.telephony.PhoneStateListener.LISTEN_DATA_ACTIVITY;
import static android.telephony.PhoneStateListener.LISTEN_SERVICE_STATE;

public class MainActivity extends AppCompatActivity {
    ListView contactList;
    Button btnCompose;
    Button load, device_btn, sim_btn;
    IntentFilter intentFilter;
    IntentFilter batteryIntentFilter;
    IntentFilter timeIntentFilter;
    TelephonyManager  telephonyManager;
    TextView r_msg;
    BatteryReceiver batteryReceiver;
    TimeReciever timeReciever;
    CustomPhoneStateListener cListener;
    private BroadcastReceiver intentReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            r_msg.setText(intent.getExtras().getString("message"));
        }
    };
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //intent filters
        intentFilter = new IntentFilter();
        batteryIntentFilter = new IntentFilter();
        timeIntentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        batteryIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        batteryIntentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        batteryIntentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        batteryIntentFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
        timeIntentFilter.addAction(Intent.ACTION_TIME_CHANGED);
        timeIntentFilter.addAction(Intent.ACTION_TIME_TICK);
        batteryReceiver = new BatteryReceiver();
        timeReciever = new TimeReciever();
        cListener = new CustomPhoneStateListener(this);
        r_msg = findViewById(R.id.receivedMsg);
        telephonyManager=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //write to phonestate.txt
        telephonyManager.listen(new CustomPhoneStateListener(this),
                PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR
        |LISTEN_CALL_STATE|LISTEN_CELL_INFO|LISTEN_CELL_LOCATION|LISTEN_DATA_ACTIVITY|LISTEN_SERVICE_STATE);


        //write to SimInfo.txt
        String simInfo = "";
        simInfo += "Country ISO: " + telephonyManager.getSimCountryIso();
        simInfo += "\nOperator name: " + telephonyManager.getSimOperatorName();
        simInfo += "\nOperator code: " + telephonyManager.getSimOperator();
        simInfo += "\nSim Serial: " + telephonyManager.getSimSerialNumber();
        simInfo += "\nPhone Number: " + telephonyManager.getLine1Number();
        cListener.writeToFile(simInfo, CustomPhoneStateListener.SIM_INFO);
        //buttons
           btnCompose  = findViewById(R.id.compose);
           load = findViewById(R.id.load);
           device_btn = findViewById(R.id.device);
           sim_btn = findViewById(R.id.sim);
           btnCompose.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   openCompose();
               }
           });
           load.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   r_msg.setText(cListener.readFromFile(CustomPhoneStateListener.PHONE_STATE));
               }
           });

           device_btn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   r_msg.setText(cListener.readFromFile(CustomPhoneStateListener.DEVICE_INFO));
               }
           });

           sim_btn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   r_msg.setText(cListener.readFromFile(CustomPhoneStateListener.SIM_INFO));
               }
           });

    }


    @Override
    protected void onResume() {
        registerReceiver(intentReciever, intentFilter);
        registerReceiver(batteryReceiver, batteryIntentFilter);
        registerReceiver(timeReciever, timeIntentFilter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(intentReciever);
        unregisterReceiver(batteryReceiver);
        unregisterReceiver(timeReciever);
        super.onPause();
    }

    void openCompose(){
        Intent intent = new Intent(this, Compose.class);
        startActivity(intent);
    }

    private class BatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

            if(intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED))cListener.writeToFile("Battery level: "+level, CustomPhoneStateListener.DEVICE_INFO);
            if(intent.getAction().equals(Intent.ACTION_POWER_CONNECTED) || intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) cListener.writeToFile("Power connected: "+ isCharging, CustomPhoneStateListener.DEVICE_INFO);
            if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) cListener.writeToFile("Boot Completed", CustomPhoneStateListener.DEVICE_INFO);
        }
    }

    private class TimeReciever extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Calendar current = Calendar.getInstance();

            if(intent.getAction().equals(Intent.ACTION_TIME_TICK) && current.get(Calendar.MINUTE) % 5 == 0) cListener.writeToFile(current.getTime().toString(), CustomPhoneStateListener.DEVICE_INFO);
            if(intent.getAction().equals(Intent.ACTION_TIME_CHANGED)) cListener.writeToFile(current.getTime().toString(), CustomPhoneStateListener.DEVICE_INFO);
        }
    }


}
