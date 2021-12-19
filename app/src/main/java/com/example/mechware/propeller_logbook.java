package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class propeller_logbook extends AppCompatActivity {

    Button propeller_record_btn, hub_and_blade_inspection_btn;

    String user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propeller_logbook);

        user_type = getIntent().getStringExtra("user_type");

        // initialization of buttons
        propeller_record_btn = (Button) findViewById(R.id.propeller_record_btn);
        hub_and_blade_inspection_btn = (Button) findViewById(R.id.hub_and_blade_inspection_btn);

        // adding action to propeller record button
        propeller_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),propeller_record_form.class);
                pass.putExtra("user_type", user_type);
                startActivity(pass);
            }
        });

        //adding action to hun and blade inspection button
        hub_and_blade_inspection_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),hub_and_blade_inspections_form.class);
                pass.putExtra("user_type", user_type);
                startActivity(pass);
            }
        });
    }
}