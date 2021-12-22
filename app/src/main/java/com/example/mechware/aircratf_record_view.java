package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class aircratf_record_view extends AppCompatActivity {

    Button submit_btn, clear_btn;
    EditText manufacturer_editText, model_editText, serial_editText, registration_no_editText, date_of_manufacture_editTextDate;
    EditText engine_manufacturer_editText, engine_model_editText, engine_serial_editText, engine_manufacturer_2_editText, engine_model_2_editText, engine_serial_2_editText;
    EditText propeller_manufacturer_editText, propeller_manufacturer_model_editText, HUB_model_editText, propeller_serial_editText, blade_model_editText, blade_model_serial_editText;
    EditText blade_model_serial_2_editText, blade_model_serial_3_editText, blade_model_2_editText, blade_model_2_serial_editText, blade_model_2_serial_2_editText, blade_model_2_serial_3_editText;

    String extra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aircratf_record_view);

        //Model
        manufacturer_editText = (EditText) findViewById(R.id.manufacturer_editText);
        model_editText = (EditText) findViewById(R.id.model_editText);
        serial_editText = (EditText) findViewById(R.id.serial_editText);
        registration_no_editText = (EditText) findViewById(R.id.registration_no_editText);
        date_of_manufacture_editTextDate = (EditText) findViewById(R.id.date_of_manufacture_editTextDate);
        //Engine Currently Installed
        engine_manufacturer_editText = (EditText) findViewById(R.id.engine_manufacturer_editText);
        engine_model_editText = (EditText) findViewById(R.id.engine_model_editText);
        engine_serial_editText = (EditText) findViewById(R.id.engine_serial_editText);
        engine_manufacturer_2_editText = (EditText) findViewById(R.id.engine_manufacturer_2_editText);
        engine_model_2_editText = (EditText) findViewById(R.id.engine_model_2_editText);
        engine_serial_2_editText = (EditText) findViewById(R.id.engine_serial_2_editText);
        //Propeller Currently Installed
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



        extra = getIntent().getStringExtra("action");
        if(extra.contentEquals("view")){
            manufacturer_editText.setEnabled(false);
            model_editText.setEnabled(false);
            serial_editText.setEnabled(false);
            registration_no_editText.setEnabled(false);
            date_of_manufacture_editTextDate.setEnabled(false);
            engine_manufacturer_editText.setEnabled(false);
            engine_model_editText.setEnabled(false);
            engine_serial_editText.setEnabled(false);
            engine_manufacturer_2_editText.setEnabled(false);
            engine_model_2_editText.setEnabled(false);
            engine_serial_2_editText.setEnabled(false);
            propeller_manufacturer_editText.setEnabled(false);
            propeller_manufacturer_model_editText.setEnabled(false);
            HUB_model_editText.setEnabled(false);
            propeller_serial_editText.setEnabled(false);
            blade_model_editText.setEnabled(false);
            blade_model_serial_editText.setEnabled(false);
            blade_model_serial_2_editText.setEnabled(false);
            blade_model_serial_3_editText.setEnabled(false);
            blade_model_2_editText.setEnabled(false);
            blade_model_2_serial_editText.setEnabled(false);
            blade_model_2_serial_2_editText.setEnabled(false);
            blade_model_2_serial_3_editText.setEnabled(false);

        }else{
            manufacturer_editText.setText("");
            model_editText.setText("");
            serial_editText.setText("");
            registration_no_editText.setText("");
            date_of_manufacture_editTextDate.setText("");
            engine_manufacturer_editText.setText("");
            engine_model_editText.setText("");
            engine_serial_editText.setText("");
            engine_manufacturer_2_editText.setText("");
            engine_model_2_editText.setText("");
            engine_serial_2_editText.setText("");
            propeller_manufacturer_editText.setText("");
            propeller_manufacturer_model_editText.setText("");
            HUB_model_editText.setText("");
            propeller_serial_editText.setText("");
            blade_model_editText.setText("");
            blade_model_serial_editText.setText("");
            blade_model_serial_2_editText.setText("");
            blade_model_serial_3_editText.setText("");
            blade_model_2_editText.setText("");
            blade_model_2_serial_editText.setText("");
            blade_model_2_serial_2_editText.setText("");
            blade_model_2_serial_3_editText.setText("");
        }

        submit_btn = (Button) findViewById(R.id.next_btn);
        clear_btn = (Button) findViewById(R.id.clear_btn);
    }
}