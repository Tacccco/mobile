package com.example.android.fiveactivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Activity3 extends AppCompatActivity {
    private CheckBox checkBox, checkBox2, checkBox3;
    private EditText date;
    private Button ok;
    private Button cancel;
    private boolean chk = false, chk2 = false, chk3 = false;
    private String date_tmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        checkBox = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        date = findViewById(R.id.date);

        ok = findViewById(R.id.ok);
        cancel = findViewById(R.id.cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chk = checkBox.isChecked();
                chk2 = checkBox2.isChecked();
                chk3 = checkBox3.isChecked();
                date_tmp = date.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("chk", chk);
                intent.putExtra("chk2", chk2);
                intent.putExtra("chk3", chk3);

               intent.putExtra("date", date_tmp);
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
        chk = getIntent().getExtras().getBoolean("chk");
        chk2 = getIntent().getExtras().getBoolean("chk2");
        chk3 = getIntent().getExtras().getBoolean("chk3");

       date_tmp = getIntent().getStringExtra("date");
        checkBox.setChecked(chk);
        checkBox2.setChecked(chk2);
        checkBox3.setChecked(chk3);
        date.setText(date_tmp);
    }

    @Override
    public Intent getIntent() {
        return super.getIntent();
    }
}
