package com.example.android.fiveactivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TimePicker;

public class activity_5 extends AppCompatActivity {
    RatingBar rb;
    TimePicker timePicker;
    private Button ok;
    private Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);

        rb = findViewById(R.id.rate);
        timePicker = findViewById(R.id.time_pick);

        ok = findViewById(R.id.ok);
        cancel = findViewById(R.id.cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("rate", rb.getRating());
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
        rb.setRating(getIntent().getExtras().getFloat("rate"));
        timePicker.setHour(getIntent().getExtras().getInt("hour"));
        timePicker.setMinute(getIntent().getExtras().getInt("minute"));
    }

    @Override
    public Intent getIntent() {
        return super.getIntent();
    }
}
