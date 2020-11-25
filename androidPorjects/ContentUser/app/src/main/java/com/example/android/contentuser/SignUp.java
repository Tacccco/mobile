package com.example.android.contentuser;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    EditText uname;
    EditText pass;
    EditText phone;
    EditText age;
    RadioButton m ,f;
    DatePicker birth;
    Button submit;

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
        setContentView(R.layout.activity_sign_up);
        uname = findViewById(R.id.uname);
        pass = findViewById(R.id.pass);
        phone = findViewById(R.id.phone);
        age = findViewById(R.id.age);
        m = findViewById(R.id.male);
        f = findViewById(R.id.female);
        birth = findViewById(R.id.birth);
        submit =findViewById(R.id.submit);
        m.setChecked(true);
        String name_tmp = getIntent().getStringExtra("name");
        String pass_tmp = getIntent().getStringExtra("pass");
        uname.setText(name_tmp);
        pass.setText(pass_tmp);
        final Toast toast = Toast.makeText(getApplicationContext(), R.string.success_add, Toast.LENGTH_SHORT);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b = addUser();
                if(!b) toast.setText(R.string.empty);
                else toast.setText(R.string.success_add);
                toast.show();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    public Intent getIntent() {
        return super.getIntent();
    }
    public boolean addUser(){
        if(uname.getText().toString().isEmpty()) return false;
        if(pass.getText().toString().isEmpty()) return false;
        if(phone.getText().toString().isEmpty()) return false;
        if(age.getText().toString().isEmpty()) return false;
        Uri uri = Uri.parse("content://MyAccounts/user_info");
        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, uname.getText().toString());
        values.put(COLUMN_USER_AGE, age.getText().toString());
        values.put(COLUMN_USER_PASS, pass.getText().toString());
        values.put(COLUMN_USER_PHONE, phone.getText().toString());
        values.put(COLUMN_USER_BIRTH, ""+birth.getYear() + "-" + birth.getMonth() + "-" + birth.getDayOfMonth());
        if(m.isChecked()) values.put(COLUMN_USER_GENDER, "M");
        else if(f.isChecked()) values.put(COLUMN_USER_GENDER, "F");
        contentResolver.insert(uri,values);
        return true;
    }
}
