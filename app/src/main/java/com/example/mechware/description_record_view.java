package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class description_record_view extends AppCompatActivity {

    EditText date_editText, tach_time_editText, todays_flight_editText, total_time_in_service_editText, description_editText;
    Button clear_btn, submit_btn;

    String extra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_record_view);

        // initialization of EditText
        date_editText = (EditText)findViewById(R.id.date_editText);
        tach_time_editText = (EditText)findViewById(R.id.tach_time_editText);
        todays_flight_editText = (EditText)findViewById(R.id.todays_flight_editText);
        total_time_in_service_editText = (EditText)findViewById(R.id.total_time_in_service_editText);
        description_editText = (EditText)findViewById(R.id.description_editText);

        extra = getIntent().getStringExtra("action");
        if(extra.contentEquals("view")){
            date_editText.setEnabled(false);
            tach_time_editText.setEnabled(false);
            todays_flight_editText.setEnabled(false);
            total_time_in_service_editText.setEnabled(false);
            description_editText.setEnabled(false);
        }else{
            date_editText.setText("");
            tach_time_editText.setText("");
            todays_flight_editText.setText("");
            total_time_in_service_editText.setText("");
            description_editText.setText("");
        }

        // initialization of Buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        submit_btn = (Button) findViewById(R.id.next_btn);
    }
}