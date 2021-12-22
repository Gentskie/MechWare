package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class airworthiness_record_view extends AppCompatActivity {

    EditText date_editText, ad_number_editText, total_time_in_service_editText, airworthiness_editText;
    Button clear_btn, submit_btn;
    String extra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airworthiness_record_view);

        // initialization of EditText
        date_editText = (EditText)findViewById(R.id.date_editText);
        total_time_in_service_editText = (EditText)findViewById(R.id.total_time_in_service_editText);
        ad_number_editText = (EditText)findViewById(R.id.ad_number_editText);
        airworthiness_editText = (EditText)findViewById(R.id.airworthiness_editText);

        extra = getIntent().getStringExtra("action");
        if(extra.contentEquals("view")){
            date_editText.setEnabled(false);
            total_time_in_service_editText.setEnabled(false);
            ad_number_editText.setEnabled(false);
            airworthiness_editText.setEnabled(false);
        }else{
            date_editText.setText("");
            total_time_in_service_editText.setText("");
            ad_number_editText.setText("");
            airworthiness_editText.setText("");
        }

        // initialization of Buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        submit_btn = (Button) findViewById(R.id.next_btn);
    }
}