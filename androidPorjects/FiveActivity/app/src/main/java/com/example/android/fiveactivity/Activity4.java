package com.example.android.fiveactivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TimePicker;

public class Activity4 extends AppCompatActivity {
    RadioButton rb_f, rb_m;
    TimePicker timePicker;
    private Button ok;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);

        rb_f = findViewById(R.id.rb_f);
        rb_m = findViewById(R.id.rb_m);
        timePicker = findViewById(R.id.time_pick);

        ok = findViewById(R.id.ok);
        cancel = findViewById(R.id.cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("female",rb_f.isChecked());
                intent.putExtra("male",rb_m.isChecked());
                intent.putExtra("hour", timePicker.getHour());
                intent.putExtra("minute", timePicker.getMinute());
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
        rb_f.setChecked(getIntent().getExtras().getBoolean("female"));
        rb_m.setChecked(getIntent().getExtras().getBoolean("male"));
        timePicker.setHour(getIntent().getExtras().getInt("hour"));
        timePicker.setMinute(getIntent().getExtras().getInt("minute"));
    }

    @Override
    public Intent getIntent() {
        return super.getIntent();
    }
}
