package com.example.android.fiveactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_02 = 10;
    public static final int REQUEST_CODE_03 = 11;
    public static final int REQUEST_CODE_04 = 12;
    public static final int REQUEST_CODE_05 = 13;
    private Button button02;
    private Button button03;
    private Button button04;
    private Button button05;
    //On activity 2
    String name;
    String number;
    //On activity 3
    private boolean chk = false, chk2 = false, chk3 = false;
    private String date_tmp;
    //On activity 4
    boolean rb_f;
    boolean rb_m;
    int hour, minute;
    //On activity 5
    float rate;
    int hour2, minute2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button02 = findViewById(R.id.btn_act02);
        button03 = findViewById(R.id.btn_act03);
        button04 = findViewById(R.id.btn_act04);
        button05 = findViewById(R.id.btn_act05);
        button02.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    openActivity2();
                }
            }
        );
        button03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity3();
            }
        });
        button04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity4();
            }
        });

        button05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity5();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.imp_menu,menu);
        return true;
    }//*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item02:
                //Intent intent = new Intent();

                Intent intent = new Intent();
                intent.setAction("jdfk.dkdk");
                intent.addCategory("vdfjjv.vdfbkjkb");
                intent.putExtra("rate",rate);
                intent.putExtra("hour", hour2);
                intent.putExtra("minute", minute2);
                //intent.addCategory("android.intent.category.LAUNCHER")intent.setData(Uri.parse("content://contacts/people/"));
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }//*/

    public void openActivity2(){
        Intent intent = new Intent(this, Activity2.class);
        intent.putExtra("name",name);
        intent.putExtra("number", number);
        startActivityForResult(intent, REQUEST_CODE_02);
    }

    public void openActivity3(){
        Intent intent = new Intent(this, Activity3.class);
        intent.putExtra("chk", chk);
        intent.putExtra("chk2", chk2);
        intent.putExtra("chk3", chk3);
        intent.putExtra("date", date_tmp);
        startActivityForResult(intent, REQUEST_CODE_03);
    }
    public void openActivity4(){
        Intent intent = new Intent(this, Activity4.class);
        intent.putExtra("female",rb_f);
        intent.putExtra("male",rb_m);
        intent.putExtra("hour", hour);
        intent.putExtra("minute", minute);
        startActivityForResult(intent, REQUEST_CODE_04);
    }
    public void openActivity5(){
        Intent intent = new Intent(this, activity_5.class);
        intent.putExtra("rate",rate);
        intent.putExtra("hour", hour2);
        intent.putExtra("minute", minute2);
        startActivityForResult(intent, REQUEST_CODE_05);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_CODE_02:
                if(resultCode == Activity.RESULT_OK){
                    name = data.getStringExtra("name");
                    number = data.getStringExtra("number");

                }
                break;
            case REQUEST_CODE_03:
                if(resultCode == Activity.RESULT_OK){
                    chk = data.getExtras().getBoolean("chk");
                    chk2 = data.getExtras().getBoolean("chk2");
                    chk3 = data.getExtras().getBoolean("chk3");
                    date_tmp = data.getStringExtra("date");

                }
                break;
            case REQUEST_CODE_04:
                if(resultCode == Activity.RESULT_OK){
                    rb_f = data.getExtras().getBoolean("female");
                    rb_m = data.getExtras().getBoolean("male");
                    hour = data.getExtras().getInt("hour");
                    minute = data.getExtras().getInt("minute");
                }
                break;
            case REQUEST_CODE_05:
                if(resultCode == Activity.RESULT_OK){
                    rate = data.getExtras().getFloat("rate");
                    hour2 = data.getExtras().getInt("hour");
                    minute2 = data.getExtras().getInt("minute");
                }
        }
    }


}
