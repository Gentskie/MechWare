package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login_page extends AppCompatActivity {

    TextView signup;
    Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        signup = (TextView) findViewById(R.id.signup_btn);
        login_btn = (Button) findViewById(R.id.login_btn);
        // start of activity instances
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),registration_form_1.class);
                startActivity(pass);
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),home_page.class);
                startActivity(pass);
            }
        });
    }
}