package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class registered_owner_record_form extends AppCompatActivity {

    EditText owner_name_editText, address_editText, state_editText, city_editText, from_editText, to_editText;
    Button clear_btn, next_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_owner_record_form);

        // initialization of text fields
        owner_name_editText = (EditText) findViewById(R.id.owner_name_editText);
        address_editText = (EditText) findViewById(R.id.address_editText);
        state_editText = (EditText) findViewById(R.id.state_editText);
        city_editText = (EditText) findViewById(R.id.city_editText);
        from_editText = (EditText) findViewById(R.id.from_editText);
        to_editText = (EditText) findViewById(R.id.to_editText);

        // initialization of buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        next_btn = (Button) findViewById(R.id.next_btn);


        // setting action to next button
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),engine_logbook.class);
                startActivity(pass);
            }
        });
    }
}