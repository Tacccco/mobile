package com.onon.android.messageapp;

import android.content.Context;
import android.provider.Settings;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;


public class CustomPhoneStateListener extends PhoneStateListener {
    Context context;
    public static final String LOG = "PhoneState";
    public static final String PHONE_STATE = "phonestate.txt";
    public static final String SIM_INFO = "simInfo.txt";
    public static final String DEVICE_INFO = "deviceInfo.txt";
    public CustomPhoneStateListener(Context context){
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String phoneNumber) {
        super.onCallStateChanged(state, phoneNumber);

            switch (state){
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.i(LOG, "State Idle");
                    writeToFile("call state: Idle", PHONE_STATE);
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                        Log.i(LOG, "State Ringing");
                        writeToFile("call state: Ringing number: "+phoneNumber,PHONE_STATE);
                        break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    writeToFile("call state: Offhook number:"+phoneNumber,PHONE_STATE);
                    Log.i(LOG, "State offhook"); break;
                    default:
                        Log.i(LOG, "Unknown"); break;
            }
        readFromFile(PHONE_STATE);
    }

    @Override
    public void onCallForwardingIndicatorChanged(boolean cfi) {
        super.onCallForwardingIndicatorChanged(cfi);
        Log.i(LOG, "Call forwarding indicator: "+cfi);
        writeToFile("Call forwarding indicator: "+cfi,PHONE_STATE);
        readFromFile(PHONE_STATE);
    }

    @Override
    public void onCellInfoChanged(List<CellInfo> cellInfo) {
        super.onCellInfoChanged(cellInfo);

    }

    @Override
    public void onCellLocationChanged(CellLocation location) {
        super.onCellLocationChanged(location);
        if(location instanceof GsmCellLocation){
            GsmCellLocation gcLoc = (GsmCellLocation) location;
            Log.i(LOG,"Location changed: " + gcLoc.toString());
            writeToFile("Location changed: " + gcLoc.toString(), PHONE_STATE);
            readFromFile(PHONE_STATE);
        }

    }

    @Override
    public void onServiceStateChanged(ServiceState serviceState) {
        super.onServiceStateChanged(serviceState);
        switch (serviceState.getState()){
            case ServiceState.STATE_IN_SERVICE:
                Log.i(LOG,"Service State: In Service");
                writeToFile("Service State: In Service", PHONE_STATE); break;
            case ServiceState.STATE_OUT_OF_SERVICE:
                Log.i(LOG,"Service State: Out of Service");
                writeToFile("Service State: Out of Service", PHONE_STATE); break;
            case ServiceState.STATE_EMERGENCY_ONLY:
                Log.i(LOG,"Service State: Emergency only");
                writeToFile("Service State: Emergency only", PHONE_STATE); break;
            case ServiceState.STATE_POWER_OFF:
                Log.i(LOG,"Service State: Power off");
                writeToFile("Service State: Power off", PHONE_STATE); break;

        }
        readFromFile(PHONE_STATE);
    }

    @Override
    public void onDataActivity(int direction) {
        super.onDataActivity(direction);
        switch (direction){
            case TelephonyManager.DATA_ACTIVITY_NONE:
                Log.i(LOG, "data activity: none");
                writeToFile("data activity: None", PHONE_STATE); break;
            case TelephonyManager.DATA_ACTIVITY_IN:
                Log.i(LOG, "data activity: In");
                writeToFile("data avtivity: In", PHONE_STATE); break;
            case TelephonyManager.DATA_ACTIVITY_OUT:
                Log.i(LOG, "data activity: out");
                writeToFile("data activity: out", PHONE_STATE);break;
            case TelephonyManager.DATA_ACTIVITY_INOUT:
                Log.i(LOG, "data activity: Inout");
                writeToFile("data activity: Inout", PHONE_STATE);break;
            case TelephonyManager.DATA_ACTIVITY_DORMANT:
                Log.i(LOG, "data activity: Dormant");
                writeToFile("data activity: Dormant", PHONE_STATE);break;
            default:
                Log.i(LOG, "data activity: unknown");
                writeToFile("data activity: unknown", PHONE_STATE);break;
        }
        readFromFile(PHONE_STATE);
    }

    public void writeToFile(String data, String filename){
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = context.openFileOutput(filename, Context.MODE_APPEND);
            fileOutputStream.write(System.lineSeparator().getBytes());
            fileOutputStream.write(data.getBytes());
            fileOutputStream.flush();

            Toast.makeText(context, "Saved to " + context.getFilesDir() + "/"+filename, Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
    public String readFromFile(String filename){
        FileInputStream fileInputStream = null;
        StringBuilder text = new StringBuilder();

        try {
            fileInputStream = context.openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String temp;

            while((temp = bufferedReader.readLine()) != null){
                text.append(temp + "\n");
            }
            //System.out.println(text);
            //Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return text.toString();
    }
}
