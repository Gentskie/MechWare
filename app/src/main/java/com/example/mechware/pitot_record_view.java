package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class pitot_record_view extends AppCompatActivity {

    Button next_btn, clear_btn;
    EditText date_performed_editText, customer_name_editText, word_order_editText, instrument_name_editText, part_no_editText, serial_no_editText, model_editText, manufacturer_editText;
    EditText temperature_editText, registration_mark_editText, standard, calibration_due_date_editText, technician_editText, caap_lic_editText, director_of_maintenance_editText, caap_lic_2_editText;
    RadioButton fit_for_use_radiobtn, not_fit_for_use_radioBtn, limited_use_radioBtn, for_repair_radioBtn, adjusted_radioBtn, good_radioBtn, no_good_radioBtn, for_repair_2_radioBtn, bench_check_radioBtn;
    String extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitot_record_view);

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

        extra = getIntent().getStringExtra("action");
        if(extra.contentEquals("view")){
            date_performed_editText.setEnabled(false);
            customer_name_editText.setEnabled(false);
            word_order_editText.setEnabled(false);
            instrument_name_editText.setEnabled(false);
            part_no_editText.setEnabled(false);
            serial_no_editText.setEnabled(false);
            model_editText.setEnabled(false);
            manufacturer_editText.setEnabled(false);
            temperature_editText.setEnabled(false);
            registration_mark_editText.setEnabled(false);
            standard.setEnabled(false);
            calibration_due_date_editText.setEnabled(false);
            technician_editText.setEnabled(false);
            caap_lic_editText.setEnabled(false);
            director_of_maintenance_editText.setEnabled(false);
            caap_lic_2_editText.setEnabled(false);
        }else{
            date_performed_editText.setText("");
            customer_name_editText.setText("");
            word_order_editText.setText("");
            instrument_name_editText.setText("");
            part_no_editText.setText("");
            serial_no_editText.setText("");
            model_editText.setText("");
            manufacturer_editText.setText("");
            temperature_editText.setText("");
            registration_mark_editText.setText("");
            standard.setText("");
            calibration_due_date_editText.setText("");
            technician_editText.setText("");
            caap_lic_editText.setText("");
            director_of_maintenance_editText.setText("");
            caap_lic_2_editText.setText("");
        }

        // initialization  of buttons
        next_btn = (Button) findViewById(R.id.next_btn);
        clear_btn = (Button) findViewById(R.id.clear_btn);
    }
}