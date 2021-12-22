package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class form_1_record_view extends AppCompatActivity {

    EditText date_editText, time_of_service_editText, empty_weight_editText, empty_cg_editText, useful_load_editText, remarks_editText;
    Button clear_btn, submit_btn;

    String extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_1_record_view);

        // initialization of EditText
        date_editText = (EditText)findViewById(R.id.date_editText);
        time_of_service_editText = (EditText)findViewById(R.id.time_of_service_editText);
        empty_weight_editText = (EditText)findViewById(R.id.empty_weight_editText);
        empty_cg_editText = (EditText)findViewById(R.id.empty_cg_editText);
        useful_load_editText = (EditText)findViewById(R.id.useful_load_editText);
        remarks_editText = (EditText)findViewById(R.id.remarks_editText);

        extra = getIntent().getStringExtra("action");
        if(extra.contentEquals("view")){
            date_editText.setEnabled(false);
            time_of_service_editText.setEnabled(false);
            empty_weight_editText.setEnabled(false);
            empty_cg_editText.setEnabled(false);
            useful_load_editText.setEnabled(false);
            remarks_editText.setEnabled(false);
        }else{
            date_editText.setText("");
            time_of_service_editText.setText("");
            empty_weight_editText.setText("");
            empty_cg_editText.setText("");
            useful_load_editText.setText("");
            remarks_editText.setText("");
        }

        // initialization of Buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        submit_btn = (Button) findViewById(R.id.next_btn);


    }
}