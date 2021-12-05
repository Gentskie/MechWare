package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class description_engine_form extends AppCompatActivity {

    EditText date_editText, tach_time_editText, todays_flight_editText, total_time_in_service_editText, description_editText;
    Button clear_btn, next_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_engine_form);

        // initialization of EditText
        date_editText = (EditText)findViewById(R.id.date_editText);
        tach_time_editText = (EditText)findViewById(R.id.tach_time_editText);
        todays_flight_editText = (EditText)findViewById(R.id.todays_flight_editText);
        total_time_in_service_editText = (EditText)findViewById(R.id.total_time_in_service_editText);
        description_editText = (EditText)findViewById(R.id.description_editText);

        // initialization of Buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        next_btn = (Button) findViewById(R.id.next_btn);

        // Setting action to buttons
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),engine_logbook.class);
                startActivity(pass);
            }
        });
    }
}