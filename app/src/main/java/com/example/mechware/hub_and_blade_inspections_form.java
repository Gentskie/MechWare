package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class hub_and_blade_inspections_form extends AppCompatActivity {

    EditText date_editText, total_time_in_service_editText, next_inspection_due_editText, time_since_overhaul, description_editText, mech_cert_editText;
    Button clear_btn, next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_and_blade_inspections_form);

        // initialization of edit texts
        date_editText = (EditText) findViewById(R.id.date_editText);
        total_time_in_service_editText = (EditText) findViewById(R.id.total_time_in_service_editText);
        next_inspection_due_editText = (EditText) findViewById(R.id.next_inspection_due_editText);
        time_since_overhaul = (EditText) findViewById(R.id.time_since_overhaul);
        description_editText = (EditText) findViewById(R.id.description_editText);
        mech_cert_editText = (EditText) findViewById(R.id.mech_cert_editText);

        // initialization of Buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        next_btn = (Button) findViewById(R.id.next_btn);

        // adding action to next button
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),propeller_logbook.class);
                startActivity(pass);
            }
        });
    }
}