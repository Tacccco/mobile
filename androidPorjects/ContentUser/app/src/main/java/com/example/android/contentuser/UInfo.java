package com.example.android.contentuser;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class UInfo extends AppCompatActivity {
    EditText uname;
    EditText phone;
    EditText age;
    RadioButton m ,f;
    EditText birth;

    Button save;
    Button change_pass;
    int id;
    String pass;
    public static final String TABLE_USER = " user_info ";
    public static final String COLUMN_USER_ID = " user_id ";
    public static final String COLUMN_USER_NAME = " user_name ";
    public static final String COLUMN_USER_PASS = " user_pass ";
    public static final String COLUMN_USER_GENDER = " user_gender ";
    public static final String COLUMN_USER_PHONE = " user_phone ";
    public static final String COLUMN_USER_AGE = " user_age ";
    public static final String COLUMN_USER_BIRTH = " user_birth ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uinfo);
        change_pass = findViewById(R.id.pass_change);
        save = findViewById(R.id.save);

        uname = findViewById(R.id.uname);
        phone = findViewById(R.id.phone);
        age = findViewById(R.id.age);
        m = findViewById(R.id.male);
        f = findViewById(R.id.female);
        birth = findViewById(R.id.birth);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               boolean b =  updateUserInfo();
                Toast toast = Toast.makeText(getApplicationContext(), R.string.success_info, Toast.LENGTH_SHORT);
                if(!b) toast.setText(R.string.empty);
                toast.show();
            }
        });
        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChangePass();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        setUserInfo();



    }
    @Override
    public Intent getIntent() {
        return super.getIntent();
    }
    public void setUserInfo(){
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://MyAccounts/user_info");
        String[] projection = {COLUMN_USER_ID,"user_name","user_pass","user_gender", "user_phone", "user_age", "user_birth"};
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;
        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);

        if(cursor != null ) {

            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("user_name"));
                pass = cursor.getString(cursor.getColumnIndex("user_pass"));
                String name_temp = getIntent().getStringExtra("name");
                String pass_temp = getIntent().getStringExtra("pass");
                String gend = cursor.getString(cursor.getColumnIndex("user_gender"));
                if(name.equals(name_temp) && pass.equals(pass_temp)){
                    id = cursor.getInt(cursor.getColumnIndex("user_id"));
                    uname.setText(name);
                    phone.setText(cursor.getString(cursor.getColumnIndex("user_phone")));
                    age.setText(cursor.getString(cursor.getColumnIndex("user_age")));
                    if(gend.equals("M")) m.setChecked(true);
                    if(gend.equals("F")) f.setChecked(true);
                    birth.setText(cursor.getString(cursor.getColumnIndex("user_birth")));
                    return;
                }
            }
        }
        cursor.close();

    }//*/
    public boolean updateUserInfo(){
        if(uname.getText().toString().isEmpty()) return false;
        if(age.getText().toString().isEmpty()) return false;
        if(phone.getText().toString().isEmpty()) return false;
        if(birth.getText().toString().isEmpty()) return false;
        ContentResolver contentResolver = getContentResolver();
        String selection = COLUMN_USER_ID + " = " + id;
        String[] selectionArgs = null;
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_NAME, uname.getText().toString());
        values.put(COLUMN_USER_AGE, age.getText().toString());
        values.put(COLUMN_USER_PHONE, phone.getText().toString());
        if(m.isChecked()) values.put(COLUMN_USER_GENDER,"M");
        if(f.isChecked()) values.put(COLUMN_USER_GENDER,"F");
        values.put(COLUMN_USER_BIRTH, birth.getText().toString());
        Uri uri = Uri.parse("content://MyAccounts/user_info");
        System.out.println(values);
        contentResolver.update(uri, values,selection,selectionArgs);
        return true;
    }
    public void openChangePass(){
        Intent intent = new Intent(this, ChangePassword.class);
        intent.putExtra("id", id);
        intent.putExtra("pass", pass);
        startActivity(intent);
    }
}
