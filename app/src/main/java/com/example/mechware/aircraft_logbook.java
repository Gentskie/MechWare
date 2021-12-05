package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class aircraft_logbook extends AppCompatActivity {

    TextView textView6;
    Button aircraft_record_btn, form_1_btn, description_btn, reference_btn, airworthiness_btn, mandatory_btn, equipment_btn;
    ImageView menu_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aircraft_logbook);

        // changing lorem text to bold
        textView6 = (TextView) findViewById(R.id.textView6);
        String text = "Aircraft Logbook";
        Spannable string = new SpannableString(text);
        StyleSpan bold = new StyleSpan(Typeface.BOLD);
        string.setSpan(bold, 0, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView6.setText(string);

        // adding action to Image View
        // menu_btn
        menu_btn = (ImageView) findViewById(R.id.menu_btn);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),home_page.class);
                startActivity(pass);
            }
        });
        // adding action to buttons
        // Aircraft Records
        aircraft_record_btn = (Button) findViewById(R.id.aircraft_record_btn);
        aircraft_record_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),aircraft_form.class);
                startActivity(pass);
            }
        });
        form_1_btn = (Button) findViewById(R.id.form_1_btn);
        form_1_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),aircraft_form1_form.class);
                startActivity(pass);
            }
        });
        description_btn = (Button) findViewById(R.id.description_btn);
        description_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),descriptions_form.class);
                startActivity(pass);
            }
        });

        reference_btn = (Button) findViewById(R.id.reference_btn);
        reference_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),reference_form.class);
                startActivity(pass);
            }
        });
        airworthiness_btn = (Button) findViewById(R.id.airworthiness_btn);
        airworthiness_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),airworthiness_form.class);
                startActivity(pass);
            }
        });
        mandatory_btn = (Button) findViewById(R.id.mandatory_btn);
        mandatory_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),madatory_service_form.class);
                startActivity(pass);
            }
        });

        equipment_btn = (Button) findViewById(R.id.equipment_btn);
        equipment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),equipment_form.class);
                startActivity(pass);
            }
        });
    }
}