package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class reference_record_view extends AppCompatActivity {

    EditText date_editText, total_time_in_service_editText, reference_editText;
    Button clear_btn, submit_btn;
    String extra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_record_view);

        // initialization of EditText
        date_editText = (EditText)findViewById(R.id.date_editText);
        total_time_in_service_editText = (EditText)findViewById(R.id.total_time_in_service_editText);
        reference_editText = (EditText)findViewById(R.id.reference_editText);

        extra = getIntent().getStringExtra("action");
        if(extra.contentEquals("view")){
            date_editText.setEnabled(false);
            total_time_in_service_editText.setEnabled(false);
            reference_editText.setEnabled(false);
        }else{
            date_editText.setText("");
            total_time_in_service_editText.setText("");
            reference_editText.setText("");
        }

        // initialization of Buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        submit_btn = (Button) findViewById(R.id.next_btn);
    }
}