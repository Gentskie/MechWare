package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class hub_and_blade_record_view extends AppCompatActivity {

    EditText date_editText, total_time_in_service_editText, next_inspection_due_editText, time_since_overhaul, description_editText, mech_cert_editText;
    Button clear_btn, next_btn;
    String extra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_and_blade_record_view);

        // initialization of edit texts
        date_editText = (EditText) findViewById(R.id.date_editText);
        total_time_in_service_editText = (EditText) findViewById(R.id.total_time_in_service_editText);
        next_inspection_due_editText = (EditText) findViewById(R.id.next_inspection_due_editText);
        time_since_overhaul = (EditText) findViewById(R.id.time_since_overhaul);
        description_editText = (EditText) findViewById(R.id.description_editText);
        mech_cert_editText = (EditText) findViewById(R.id.mech_cert_editText);

        extra = getIntent().getStringExtra("action");
        if(extra.contentEquals("view")){
            date_editText.setEnabled(false);
            total_time_in_service_editText.setEnabled(false);
            next_inspection_due_editText.setEnabled(false);
            time_since_overhaul.setEnabled(false);
            description_editText.setEnabled(false);
            mech_cert_editText.setEnabled(false);
        }else{
            date_editText.setText("");
            total_time_in_service_editText.setText("");
            next_inspection_due_editText.setText("");
            time_since_overhaul.setText("");
            description_editText.setText("");
            mech_cert_editText.setText("");
        }

        // initialization of Buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        next_btn = (Button) findViewById(R.id.next_btn);
    }
}