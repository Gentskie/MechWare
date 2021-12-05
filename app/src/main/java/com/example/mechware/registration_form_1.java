package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class registration_form_1 extends AppCompatActivity {

    Button next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form_1);

        next_btn = (Button) findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),registration_form_2.class);
                startActivity(pass);
            }
        });
    }
}