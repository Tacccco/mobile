package com.onon.android.messageapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsListener extends BroadcastReceiver {

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
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("SMS_RECEIVED_ACTION");
            broadcastIntent.putExtra("message", message);
            context.sendBroadcast(broadcastIntent);

    }

}
