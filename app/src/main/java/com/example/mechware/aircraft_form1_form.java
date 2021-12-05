package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class aircraft_form1_form extends AppCompatActivity {

    EditText date_editText, time_of_service_editText, empty_weight_editText, empty_cg_editText, useful_load_editText, remarks_editText;
    Button clear_btn, next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aircraft_form1_form);

        // initialization of EditText
        date_editText = (EditText)findViewById(R.id.date_editText);
        time_of_service_editText = (EditText)findViewById(R.id.time_of_service_editText);
        empty_weight_editText = (EditText)findViewById(R.id.empty_weight_editText);
        empty_cg_editText = (EditText)findViewById(R.id.empty_cg_editText);
        useful_load_editText = (EditText)findViewById(R.id.useful_load_editText);
        remarks_editText = (EditText)findViewById(R.id.remarks_editText);

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