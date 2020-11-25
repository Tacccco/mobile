package com.example.android.contentuser;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class UserInfoView extends AppCompatActivity {
    Button login;
    Button sign;
    EditText pass_txt;
    EditText user_name;
    TextView last;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_view);
        login = findViewById(R.id.btn_login);
        sign = findViewById(R.id.btn_sign);
        pass_txt = findViewById(R.id.passTxt);
        user_name = findViewById(R.id.user_name);
        last = findViewById(R.id.last);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isUserInfo()) openUInfo();

            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUp();
            }
        });
    }
    public boolean isUserInfo(){
        ContentResolver contentResolver = getContentResolver();
        //Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        //Uri uri = CalendarContract.Events.CONTENT_URI;
        Uri uri = Uri.parse("content://MyAccounts/user_info");

        String[] projection = null;
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;
        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);

        if(cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("user_name"));
                String pass = cursor.getString(cursor.getColumnIndex("user_pass"));
                Log.i("@myAccounts", name + " " + pass);
                if(name.equals(user_name.getText().toString()) && pass.equals(pass_txt.getText().toString())) {
                    cursor.close();
                    return true;
                }

            }
            cursor.close();
            return false;

        }
        cursor.close();
        return false;
    }
    public void openUInfo(){
        Intent intent = new Intent(this, UInfo.class);
        intent.putExtra("name", user_name.getText().toString());
        intent.putExtra("pass", pass_txt.getText().toString());

        startActivity(intent);
    }
    public void openSignUp(){
        Intent intent = new Intent(this, SignUp.class);
        intent.putExtra("name", user_name.getText().toString());
        intent.putExtra("pass", pass_txt.getText().toString());
        startActivity(intent);
    }
}
