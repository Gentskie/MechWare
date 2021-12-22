package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class propeller_record_view extends AppCompatActivity {

    EditText manufacturer_editText, model_editText, type_editText, date_of_manufacture_editTextDate, hub_series_no_editText, blade_number_editText, blade_number_2_editText, blade_number_3_editText, blade_number_4_editText, blade_number_5_editText;
    EditText make_model_editText, make_model_2_editText, make_model_3_editText, make_model_4_editText, serial_editText, serial_2_editText, serial_3_editText, serial_4_editText;
    Button clear_btn, submit_btn;
    String extra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propeller_record_view);

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

        extra = getIntent().getStringExtra("action");
        if(extra.contentEquals("view")){
            manufacturer_editText.setEnabled(false);
            model_editText.setEnabled(false);
            type_editText.setEnabled(false);
            date_of_manufacture_editTextDate.setEnabled(false);
            hub_series_no_editText.setEnabled(false);
            blade_number_editText.setEnabled(false);
            blade_number_2_editText.setEnabled(false);
            blade_number_3_editText.setEnabled(false);
            blade_number_4_editText.setEnabled(false);
            blade_number_5_editText.setEnabled(false);
            make_model_editText.setEnabled(false);
            make_model_2_editText.setEnabled(false);
            make_model_3_editText.setEnabled(false);
            make_model_4_editText.setEnabled(false);
            serial_editText.setEnabled(false);
            serial_2_editText.setEnabled(false);
            serial_3_editText.setEnabled(false);
            serial_4_editText.setEnabled(false);
        }else{
            manufacturer_editText.setText("");
            model_editText.setText("");
            type_editText.setText("");
            date_of_manufacture_editTextDate.setText("");
            hub_series_no_editText.setText("");
            blade_number_editText.setText("");
            blade_number_2_editText.setText("");
            blade_number_3_editText.setText("");
            blade_number_4_editText.setText("");
            blade_number_5_editText.setText("");
            make_model_editText.setText("");
            make_model_2_editText.setText("");
            make_model_3_editText.setText("");
            make_model_4_editText.setText("");
            serial_editText.setText("");
            serial_2_editText.setText("");
            serial_3_editText.setText("");
            serial_4_editText.setText("");
        }

        // initialization of Buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        submit_btn = (Button) findViewById(R.id.next_btn);
    }
}