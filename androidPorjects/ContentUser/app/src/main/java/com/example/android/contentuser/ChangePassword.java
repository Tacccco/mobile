package com.example.android.contentuser;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {
    EditText old_pass;
    EditText new_pass;
    EditText re_pass;
    Button apply;
    public static final String COLUMN_USER_PASS = " user_pass ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        old_pass = findViewById(R.id.old_pass);
        new_pass = findViewById(R.id.new_pass);
        re_pass = findViewById(R.id.re_pass);
        apply = findViewById(R.id.apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b = changePass();
                Toast toast = Toast.makeText(getApplicationContext(), R.string.success, Toast.LENGTH_SHORT);
                if(!b) toast.setText(R.string.fail);
                toast.show();
            }
        });
    }
    @Override
    public Intent getIntent() {
        return super.getIntent();
    }
    public boolean changePass(){
        //System.out.println("the paswooed:"+getIntent().getExtras().getInt("id"));
        String pass_tmp = getIntent().getStringExtra("pass");
        if(!old_pass.getText().toString().equals(pass_tmp)) return false;
        if(new_pass.getText().toString().isEmpty()) return false;
        if(re_pass.getText().toString().isEmpty()) return false;
        if(!re_pass.getText().toString().equals(new_pass.getText().toString())) return false;
        Uri uri= Uri.parse("content://MyAccounts/user_info");
        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_PASS, new_pass.getText().toString());
        String selection = " user_id = ?";

        String[] selectionArgs = {""+getIntent().getExtras().getInt("id")};
        contentResolver.update(uri, values, selection, selectionArgs);
        return true;
    }
}
