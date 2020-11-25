package com.example.android.database;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PasswordUpdate extends AppCompatActivity {
    EditText old_pass;
    EditText new_pass;
    EditText re_pass;
    Button apply;
    DatabaseHelper db;
    String pass;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_update);
        old_pass = findViewById(R.id.old_pass);
        new_pass = findViewById(R.id.new_pass);
        re_pass = findViewById(R.id.re_pass);
        apply = findViewById(R.id.apply);
        db = new DatabaseHelper(this);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("working", id+ " "+pass+" "+new_pass.getText().toString());
                if(!old_pass.getText().toString().equals(pass)) return;
                if(!new_pass.getText().toString().equals(re_pass.getText().toString())) return;

                db.updatePassword(id, new_pass.getText().toString());
                AlertDialog.Builder builder = new AlertDialog.Builder(PasswordUpdate.this);
                builder.setMessage(R.string.success)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.show();



            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        pass = getIntent().getStringExtra("password");
        id = getIntent().getExtras().getInt("id");

    }

    @Override
    public Intent getIntent() {
        return super.getIntent();
    }
}
