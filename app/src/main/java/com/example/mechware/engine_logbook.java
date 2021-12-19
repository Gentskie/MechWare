package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class engine_logbook extends AppCompatActivity {

    Button engine_record_btn, registered_owner_record_btn, description_btn;

    String user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engine_logbook);

        user_type = getIntent().getStringExtra("user_type");

        // initialization of buttons
        engine_record_btn = (Button) findViewById(R.id.engine_record_btn);
        registered_owner_record_btn = (Button) findViewById(R.id.registered_owner_record_btn);
        description_btn = (Button) findViewById(R.id.description_btn);

        // setting action to buttons
        engine_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),engine_record_form.class);
                pass.putExtra("user_type", user_type);
                startActivity(pass);
            }
        });
        registered_owner_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),registered_owner_record_form.class);
                pass.putExtra("user_type", user_type);
                startActivity(pass);
            }
        });
        description_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),description_engine_form.class);
                pass.putExtra("user_type", user_type);
                startActivity(pass);
            }
        });
    }
}