package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class propeller_record_form extends AppCompatActivity {

    EditText manufacturer_editText, model_editText, type_editText, date_of_manufacture_editTextDate, hub_series_no_editText, blade_number_editText, blade_number_2_editText, blade_number_3_editText, blade_number_4_editText, blade_number_5_editText;
    EditText make_model_editText, make_model_2_editText, make_model_3_editText, make_model_4_editText, serial_editText, serial_2_editText, serial_3_editText, serial_4_editText;
    Button clear_btn, next_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propeller_record_form);

        // initialization of edit texts
        manufacturer_editText = (EditText) findViewById(R.id.manufacturer_editText);
        model_editText = (EditText) findViewById(R.id.model_editText);
        type_editText = (EditText) findViewById(R.id.type_editText);
        date_of_manufacture_editTextDate = (EditText) findViewById(R.id.date_of_manufacture_editTextDate);
        hub_series_no_editText = (EditText) findViewById(R.id.hub_series_no_editText);
        blade_number_editText = (EditText) findViewById(R.id.blade_number_editText);
        blade_number_2_editText = (EditText) findViewById(R.id.blade_number_2_editText);
        blade_number_3_editText = (EditText) findViewById(R.id.blade_number_3_editText);
        blade_number_4_editText = (EditText) findViewById(R.id.blade_number_4_editText);
        blade_number_5_editText = (EditText) findViewById(R.id.blade_number_5_editText);
        make_model_editText = (EditText) findViewById(R.id.make_model_editText);
        make_model_2_editText = (EditText) findViewById(R.id.make_model_2_editText);
        make_model_3_editText = (EditText) findViewById(R.id.make_model_3_editText);
        make_model_4_editText = (EditText) findViewById(R.id.make_model_4_editText);
        serial_editText = (EditText) findViewById(R.id.serial_editText);
        serial_2_editText = (EditText) findViewById(R.id.serial_2_editText);
        serial_3_editText = (EditText) findViewById(R.id.serial_3_editText);
        serial_4_editText = (EditText) findViewById(R.id.serial_4_editText);

        // initialization of Buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        next_btn = (Button) findViewById(R.id.next_btn);

        // setting action to next button
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),propeller_logbook.class);
                startActivity(pass);
            }
        });


    }
}