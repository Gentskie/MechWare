package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class registered_owner_record_view extends AppCompatActivity {

    EditText owner_name_editText, address_editText, state_editText, city_editText, from_editText, to_editText;
    Button clear_btn, next_btn;
    String extra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_owner_record_view);

        // initialization of text fields
        owner_name_editText = (EditText) findViewById(R.id.owner_name_editText);
        address_editText = (EditText) findViewById(R.id.address_editText);
        state_editText = (EditText) findViewById(R.id.state_editText);
        city_editText = (EditText) findViewById(R.id.city_editText);
        from_editText = (EditText) findViewById(R.id.from_editText);
        to_editText = (EditText) findViewById(R.id.to_editText);

        extra = getIntent().getStringExtra("action");
        if(extra.contentEquals("view")){
            owner_name_editText.setEnabled(false);
            address_editText.setEnabled(false);
            state_editText.setEnabled(false);
            city_editText.setEnabled(false);
            from_editText.setEnabled(false);
            to_editText.setEnabled(false);
        }else{
            owner_name_editText.setText("");
            address_editText.setText("");
            state_editText.setText("");
            city_editText.setText("");
            from_editText.setText("");
            to_editText.setText("");
        }

        // initialization of buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        next_btn = (Button) findViewById(R.id.next_btn);
    }
}