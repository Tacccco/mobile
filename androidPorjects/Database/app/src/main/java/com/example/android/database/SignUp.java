package com.example.android.database;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {
    EditText uname;
    EditText pass;
    EditText phone;
    EditText age;
    RadioButton m ,f;
    DatePicker birth;
    Button submit;
    DatabaseHelper db;
    TextView temp;
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
        temp = findViewById(R.id.temp);
        db = new DatabaseHelper(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printDB();
                if(uname.getText().toString().isEmpty()) return;
                if(pass.getText().toString().isEmpty()) return;
                if(phone.getText().toString().isEmpty()) return;
                if(age.getText().toString().isEmpty()) return;
                User user = new User();
                user.setName(uname.getText().toString());
                user.setAge(Integer.parseInt(age.getText().toString()));
                user.setPassword(pass.getText().toString());
                user.setPhone(phone.getText().toString());
                if(f.isChecked()) user.setGender("F");
                if(m.isChecked()) user.setGender("M");
                String bday = "" + birth.getYear() + "-" + birth.getMonth() + "-" + birth.getDayOfMonth();
                user.setBirth(bday);
                db.addUser(user);
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                builder.setMessage(R.string.success_info)
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
    public void printDB(){
        String dbs = db.DBtoString();
        temp.setText(dbs);
    }
}
