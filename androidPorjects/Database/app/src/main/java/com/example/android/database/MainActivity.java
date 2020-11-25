package com.example.android.database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    Button login;
    Button sign;
    EditText pass_txt;
    EditText user_name;
    TextView last;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(this);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.btn_login);
        sign = findViewById(R.id.btn_sign);
        pass_txt = findViewById(R.id.passTxt);
        user_name = findViewById(R.id.user_name);
        last = findViewById(R.id.last);
        last.setText(db.returnLastUser());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user_name.getText().toString().isEmpty()) return;
                if(pass_txt.getText().toString().isEmpty()) return;
                User user = new User();
                user.setName(user_name.getText().toString());
                user.setPassword(pass_txt.getText().toString());
                if(!db.isUser(user.getName(), user.getPassword())) return;
                db.registerLastUser(user);
                openUserInfo();


            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUp();
            }
        });
    }
    void openUserInfo(){
        Intent intent = new Intent(this, UserInfo.class);
        intent.putExtra("user_name", user_name.getText().toString());
        intent.putExtra("password",pass_txt.getText().toString());
        startActivity(intent);
    }
    void openSignUp(){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

}
