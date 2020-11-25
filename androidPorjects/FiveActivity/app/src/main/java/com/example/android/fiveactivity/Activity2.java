package com.example.android.fiveactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Activity2 extends AppCompatActivity {
    private Button ok;
    private Button cancel;
    private EditText name;
    private EditText number;
    private String name_tmp;
    private String num_tmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        ok = findViewById(R.id.act02_ok);
        cancel = findViewById(R.id.act02_cancel);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_tmp = name.getText().toString();
                num_tmp = number.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("name", name_tmp);
                intent.putExtra("number", num_tmp);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        name_tmp = getIntent().getStringExtra("name");
        name.setText(name_tmp);
        num_tmp = getIntent().getStringExtra("number");
        number.setText(num_tmp);
    }

    @Override
    public Intent getIntent() {
        return super.getIntent();
    }
}
