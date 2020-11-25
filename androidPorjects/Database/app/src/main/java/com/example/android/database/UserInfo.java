package com.example.android.database;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class UserInfo extends AppCompatActivity {
    EditText uname;
    EditText phone;
    EditText age;
    RadioButton m ,f;
    EditText birth;
    DatabaseHelper db;
    User user;
    Button save;
    Button change_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        save = findViewById(R.id.save);
        change_pass = findViewById(R.id.pass_change);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uname.getText().toString().isEmpty()) return;
                if(birth.getText().toString().isEmpty()) return;
                if(phone.getText().toString().isEmpty()) return;
                if(age.getText().toString().isEmpty()) return;

                user.setName(uname.getText().toString());
                user.setAge(Integer.parseInt(age.getText().toString()));
                user.setPhone(phone.getText().toString());
                if(f.isChecked()) user.setGender("F");
                if(m.isChecked()) user.setGender("M");
                user.setBirth(birth.getText().toString());
                db.updateUser(user);
                AlertDialog.Builder builder = new AlertDialog.Builder(UserInfo.this);
                builder.setMessage(R.string.success_info)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //finish();
                            }
                        });
                builder.show();
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
        uname = findViewById(R.id.uname);
        phone = findViewById(R.id.phone);
        age = findViewById(R.id.age);
        m = findViewById(R.id.male);
        f = findViewById(R.id.female);
        birth = findViewById(R.id.birth);
        db = new DatabaseHelper(this);
        String name_temp = getIntent().getStringExtra("user_name");
        String pass_temp = getIntent().getStringExtra("password");
        user = db.getUser(name_temp, pass_temp);
        if(user == null) return;
        uname.setText(user.getName());
        phone.setText(user.getPhone());
        age.setText("" + user.getAge());
        if(user.getGender().equals("M")){
            m.setChecked(true);
            f.setChecked(false);
        }
        if(user.getGender().equals("F")){
            f.setChecked(true);
            m.setChecked(false);
        }
        birth.setText(user.getBirth());//*/

    }

    @Override
    public Intent getIntent() {
        return super.getIntent();
    }
    void openChangePass(){
        Intent intent = new Intent(this, PasswordUpdate.class);
        intent.putExtra("id", user.getId());
        intent.putExtra("password",user.getPassword());
        startActivity(intent);
    }
}
