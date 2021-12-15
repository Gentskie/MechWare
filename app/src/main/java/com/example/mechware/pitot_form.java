package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class pitot_form extends AppCompatActivity {

    Button next_btn, clear_btn;
    EditText date_performed_editText, customer_name_editText, word_order_editText, instrument_name_editText, part_no_editText, serial_no_editText, model_editText, manufacturer_editText;
    EditText temperature_editText, registration_mark_editText, standard, calibration_due_date_editText, technician_editText, caap_lic_editText, director_of_maintenance_editText, caap_lic_2_editText;
    RadioButton fit_for_use_radiobtn, not_fit_for_use_radioBtn, limited_use_radioBtn, for_repair_radioBtn, adjusted_radioBtn, good_radioBtn, no_good_radioBtn, for_repair_2_radioBtn, bench_check_radioBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitot_form);

        // initialization of edit texts
        date_performed_editText = (EditText) findViewById(R.id.date_performed_editText);
        customer_name_editText = (EditText) findViewById(R.id.customer_name_editText);
        word_order_editText = (EditText) findViewById(R.id.word_order_editText);
        instrument_name_editText = (EditText) findViewById(R.id.instrument_name_editText);
        part_no_editText = (EditText) findViewById(R.id.part_no_editText);
        serial_no_editText = (EditText) findViewById(R.id.serial_no_editText);
        model_editText = (EditText) findViewById(R.id.model_editText);
        manufacturer_editText = (EditText) findViewById(R.id.manufacturer_editText);
        temperature_editText = (EditText) findViewById(R.id.temperature_editText);
        registration_mark_editText = (EditText) findViewById(R.id.registration_mark_editText);
        standard = (EditText) findViewById(R.id.standard);
        calibration_due_date_editText = (EditText) findViewById(R.id.calibration_due_date_editText);
        technician_editText = (EditText) findViewById(R.id.technician_editText);
        caap_lic_editText = (EditText) findViewById(R.id.caap_lic_editText);
        director_of_maintenance_editText = (EditText) findViewById(R.id.director_of_maintenance_editText);
        caap_lic_2_editText = (EditText) findViewById(R.id.caap_lic_2_editText);

        // initialization  of buttons
        next_btn = (Button) findViewById(R.id.next_btn);
        clear_btn = (Button) findViewById(R.id.clear_btn);

        // adding actions to next btn
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),home_page.class);
                startActivity(pass);
            }
        });

    }
}