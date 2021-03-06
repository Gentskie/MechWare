package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

public class home_page extends AppCompatActivity {

    TextView bold_text, back_layout;
    ImageView logbook_btn, pitot_btn, ndt_btn;
    CardView card_view;
    Button aircraft_btn, propeller_btn, engine_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // changing lorem text to bold
        bold_text = (TextView) findViewById(R.id.bold_txt);
        String text = "Lorem Impsum";
        Spannable string = new SpannableString(text);
        StyleSpan bold = new StyleSpan(Typeface.BOLD);
        string.setSpan(bold, 0, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        bold_text.setText(string);

        card_view = (CardView) findViewById(R.id.card_view);
        back_layout = (TextView) findViewById(R.id.back_layout);

        // seeting cardview and back_layout invisible
        card_view.setVisibility(View.INVISIBLE);
        back_layout.setVisibility(View.INVISIBLE);

        // showing card_view and back_layout
        logbook_btn = (ImageView) findViewById(R.id.logbook_btn);
        logbook_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_view.setVisibility(View.VISIBLE);
                back_layout.setVisibility(View.VISIBLE);
            }
        });

        // hiding card_view and back_layout
        back_layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                card_view.setVisibility(View.INVISIBLE);
                back_layout.setVisibility(View.INVISIBLE);
            }
        });

        // setting action to logbook button
        aircraft_btn = (Button) findViewById(R.id.aircraft_btn);
        aircraft_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),aircraft_logbook.class);
                startActivity(pass);
            }
        });

        // setting action to propeller button
        propeller_btn = (Button) findViewById(R.id.propeller_btn);
        propeller_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),propeller_logbook.class);
                startActivity(pass);
            }
        });

        // setting action to engine button
        engine_btn = (Button) findViewById(R.id.engine_btn);
        engine_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),engine_logbook.class);
                startActivity(pass);
            }
        });

        // adding action to pitot image view
        pitot_btn = (ImageView) findViewById(R.id.pitot_btn);
        pitot_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),pitot_form.class);
                startActivity(pass);
            }
        });
        // adding action to ndt image view
        ndt_btn = (ImageView) findViewById(R.id.ndt_btn);
        ndt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),ndt_form.class);
                startActivity(pass);
            }
        });
    }
}