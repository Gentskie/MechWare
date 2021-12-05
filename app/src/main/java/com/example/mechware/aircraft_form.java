package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class aircraft_form extends AppCompatActivity {

    Button next_btn, clear_btn;
    EditText manufacturer_editText, model_editText, serial_editText, registration_no_editText, date_of_manufacture_editTextDate;
    EditText engine_manufacturer_editText, engine_model_editText, engine_serial_editText, engine_manufacturer_2_editText, engine_model_2_editText, engine_serial_2_editText;
    EditText propeller_manufacturer_editText, propeller_manufacturer_model_editText, HUB_model_editText, propeller_serial_editText, blade_model_editText, blade_model_serial_editText;
    EditText blade_model_serial_2_editText, blade_model_serial_3_editText, blade_model_2_editText, blade_model_2_serial_editText, blade_model_2_serial_2_editText, blade_model_2_serial_3_editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aircraft_form);

        manufacturer_editText = (EditText) findViewById(R.id.manufacturer_editText);
        model_editText = (EditText) findViewById(R.id.model_editText);
        serial_editText = (EditText) findViewById(R.id.serial_editText);
        registration_no_editText = (EditText) findViewById(R.id.registration_no_editText);
        date_of_manufacture_editTextDate = (EditText) findViewById(R.id.date_of_manufacture_editTextDate);
        engine_manufacturer_editText = (EditText) findViewById(R.id.engine_manufacturer_editText);
        engine_model_editText = (EditText) findViewById(R.id.engine_model_editText);
        engine_serial_editText = (EditText) findViewById(R.id.engine_serial_editText);
        engine_manufacturer_2_editText = (EditText) findViewById(R.id.engine_manufacturer_2_editText);
        engine_model_2_editText = (EditText) findViewById(R.id.engine_model_2_editText);
        engine_serial_2_editText = (EditText) findViewById(R.id.engine_serial_2_editText);
        propeller_manufacturer_editText = (EditText) findViewById(R.id.propeller_manufacturer_editText);
        propeller_manufacturer_model_editText = (EditText) findViewById(R.id.propeller_manufacturer_model_editText);
        HUB_model_editText = (EditText) findViewById(R.id.HUB_model_editText);
        propeller_serial_editText = (EditText) findViewById(R.id.propeller_serial_editText);
        blade_model_editText = (EditText) findViewById(R.id.blade_model_editText);
        blade_model_serial_editText = (EditText) findViewById(R.id.blade_model_serial_editText);
        blade_model_serial_2_editText = (EditText) findViewById(R.id.blade_model_serial_2_editText);
        blade_model_serial_3_editText = (EditText) findViewById(R.id.blade_model_serial_3_editText);
        blade_model_2_editText = (EditText) findViewById(R.id.blade_model_2_editText);
        blade_model_2_serial_editText = (EditText) findViewById(R.id.blade_model_2_serial_editText);
        blade_model_2_serial_2_editText = (EditText) findViewById(R.id.blade_model_2_serial_2_editText);
        blade_model_2_serial_3_editText = (EditText) findViewById(R.id.blade_model_2_serial_3_editText);

        next_btn = (Button) findViewById(R.id.next_btn);
        clear_btn = (Button) findViewById(R.id.clear_btn);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),aircraft_logbook.class);
                startActivity(pass);
            }
        });
    }
}