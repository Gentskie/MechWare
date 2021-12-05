package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class engine_record_form extends AppCompatActivity {

    EditText manufacturer_editText, model_editText, serial_editText, engine_belongs_to_editText, minimum_octane_editText, summer_editText, winter_editText, Magneto_time_editText;
    EditText point_setting_editText, firing_order_editText, spark_plug_gap_editText, recommended_overhaul_editText;
    Button clear_btn, next_btn;
    ImageView menu_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engine_record_form);

        // initialization of edit texts
        manufacturer_editText = (EditText) findViewById(R.id.manufacturer_editText);
        model_editText = (EditText) findViewById(R.id.model_editText);
        serial_editText = (EditText) findViewById(R.id.serial_editText);
        engine_belongs_to_editText = (EditText) findViewById(R.id.engine_belongs_to_editText);
        minimum_octane_editText = (EditText) findViewById(R.id.minimum_octane_editText);
        summer_editText = (EditText) findViewById(R.id.summer_editText);
        winter_editText = (EditText) findViewById(R.id.winter_editText);
        Magneto_time_editText = (EditText) findViewById(R.id.Magneto_time_editText);
        point_setting_editText = (EditText) findViewById(R.id.point_setting_editText);
        firing_order_editText = (EditText) findViewById(R.id.firing_order_editText);
        spark_plug_gap_editText = (EditText) findViewById(R.id.spark_plug_gap_editText);
        recommended_overhaul_editText = (EditText) findViewById(R.id.recommended_overhaul_editText);

        // initialization of buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        next_btn = (Button) findViewById(R.id.next_btn);

        // initialization of imageview
        menu_btn = (ImageView) findViewById(R.id.menu_btn);

        // setting actions to buttons
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),engine_logbook.class);
                startActivity(pass);
            }
        });

    }
}