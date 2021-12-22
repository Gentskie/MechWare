package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class equipment_record_view extends AppCompatActivity {

    EditText date_editText, total_time_in_service_editText, item_editText, manufacturer_editText, model_editText, serial_no_editText;
    RadioButton addition_opt_eq_radioButton, removal_opt_eq_radioButton, addition_req_exch_opt_radioButton, removal_req_exch_opt_radioButton;
    RadioGroup radioGroup;
    Button clear_btn, submit_btn;
    String extra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_record_view);

        // initialization of Edittext
        date_editText = (EditText) findViewById(R.id.date_editText);
        total_time_in_service_editText = (EditText) findViewById(R.id.total_time_in_service_editText);
        item_editText = (EditText) findViewById(R.id.item_editText);
        manufacturer_editText = (EditText) findViewById(R.id.manufacturer_editText);
        model_editText = (EditText) findViewById(R.id.model_editText);
        serial_no_editText = (EditText) findViewById(R.id.serial_no_editText);

        // initialization of radio Buttons
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        addition_opt_eq_radioButton = findViewById(R.id.addition_opt_eq_radioButton);
        removal_opt_eq_radioButton = findViewById(R.id.removal_opt_eq_radioButton);
        addition_req_exch_opt_radioButton = findViewById(R.id.addition_req_exch_opt_radioButton);
        removal_req_exch_opt_radioButton = findViewById(R.id.removal_req_exch_opt_radioButton);

        extra = getIntent().getStringExtra("action");
        if(extra.contentEquals("view")){
            date_editText.setEnabled(false);
            total_time_in_service_editText.setEnabled(false);
            item_editText.setEnabled(false);
            manufacturer_editText.setEnabled(false);
            model_editText.setEnabled(false);
            serial_no_editText.setEnabled(false);
            radioGroup.setEnabled(false);
        }else{
            date_editText.setText("");
            total_time_in_service_editText.setText("");
            item_editText.setText("");
            manufacturer_editText.setText("");
            model_editText.setText("");
            serial_no_editText.setText("");
            radioGroup.clearCheck();
        }

        // initialization of Buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        submit_btn = (Button) findViewById(R.id.next_btn);
    }
}