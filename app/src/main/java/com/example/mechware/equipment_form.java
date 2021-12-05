package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class equipment_form extends AppCompatActivity {

    EditText date_editText, total_time_in_service_editText, item_editText, manufacturer_editText, model_editText, serial_no_editText;
    RadioButton addition_opt_eq_radioButton, removal_opt_eq_radioButton, addition_req_exch_opt_radioButton, removal_req_exch_opt_radioButton;
    RadioGroup radioGroup;
    Button clear_btn, next_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_form);

        // initialization of Edittext
        date_editText = (EditText) findViewById(R.id.date_editText);
        total_time_in_service_editText = (EditText) findViewById(R.id.total_time_in_service_editText);
        item_editText = (EditText) findViewById(R.id.item_editText);
        manufacturer_editText = (EditText) findViewById(R.id.manufacturer_editText);
        model_editText = (EditText) findViewById(R.id.model_editText);
        serial_no_editText = (EditText) findViewById(R.id.serial_no_editText);

        // initialization of radio Buttons
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        // initialization of Buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        next_btn = (Button) findViewById(R.id.next_btn);

        // Setting action to buttons
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),aircraft_logbook.class);
                startActivity(pass);
            }
        });

    }
}