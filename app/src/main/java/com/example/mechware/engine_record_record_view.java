package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class engine_record_record_view extends AppCompatActivity {

    EditText manufacturer_editText, model_editText, serial_editText, engine_belongs_to_editText, minimum_octane_editText, summer_editText, winter_editText, Magneto_time_editText;
    EditText point_setting_editText, firing_order_editText, spark_plug_gap_editText, recommended_overhaul_editText;
    Button clear_btn, submit_btn;
    String extra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engine_record_record_view);

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

        extra = getIntent().getStringExtra("action");
        if(extra.contentEquals("view")){
            manufacturer_editText.setEnabled(false);
            model_editText.setEnabled(false);
            serial_editText.setEnabled(false);
            engine_belongs_to_editText.setEnabled(false);
            minimum_octane_editText.setEnabled(false);
            summer_editText.setEnabled(false);
            winter_editText.setEnabled(false);
            Magneto_time_editText.setEnabled(false);
            point_setting_editText.setEnabled(false);
            firing_order_editText.setEnabled(false);
            spark_plug_gap_editText.setEnabled(false);
            recommended_overhaul_editText.setEnabled(false);
        }else{
            manufacturer_editText.setText("");
            model_editText.setText("");
            serial_editText.setText("");
            engine_belongs_to_editText.setText("");
            minimum_octane_editText.setText("");
            summer_editText.setText("");
            winter_editText.setText("");
            Magneto_time_editText.setText("");
            point_setting_editText.setText("");
            firing_order_editText.setText("");
            spark_plug_gap_editText.setText("");
            recommended_overhaul_editText.setText("");
        }

        // initialization of buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        submit_btn = (Button) findViewById(R.id.next_btn);
    }
}