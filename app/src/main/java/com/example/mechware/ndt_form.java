package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ndt_form extends AppCompatActivity {

    Button next_btn, clear_btn;
    EditText customer_name_editText, work_order_no_editText, address_editText, date_editText, ac_type_editText, ac_reg_no_editText, yr_model_editText, ac_serial_no_editText, ac_total_time_editText;
    EditText part_nomenclature_editText, date_accomplish_editText, place_of_inspection_editText, purpose_of_inspection_editText, ndt_method_editText, reference_document_editText, equipment_used_editText;
    EditText findings_editText, inspected_by_editText, approved_by_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndt_form);

        // initialize edit texts
        customer_name_editText = (EditText) findViewById(R.id.customer_name_editText);
        work_order_no_editText = (EditText) findViewById(R.id.work_order_no_editText);
        address_editText = (EditText) findViewById(R.id.address_editText);
        date_editText = (EditText) findViewById(R.id.date_editText);
        ac_type_editText = (EditText) findViewById(R.id.ac_type_editText);
        ac_reg_no_editText = (EditText) findViewById(R.id.ac_reg_no_editText);
        yr_model_editText = (EditText) findViewById(R.id.yr_model_editText);
        ac_serial_no_editText = (EditText) findViewById(R.id.ac_serial_no_editText);
        ac_total_time_editText = (EditText) findViewById(R.id.ac_total_time_editText);
        part_nomenclature_editText = (EditText) findViewById(R.id.part_nomenclature_editText);
        date_accomplish_editText = (EditText) findViewById(R.id.date_accomplish_editText);
        place_of_inspection_editText = (EditText) findViewById(R.id.place_of_inspection_editText);
        purpose_of_inspection_editText = (EditText) findViewById(R.id.purpose_of_inspection_editText);
        ndt_method_editText = (EditText) findViewById(R.id.ndt_method_editText);
        reference_document_editText = (EditText) findViewById(R.id.reference_document_editText);
        equipment_used_editText = (EditText) findViewById(R.id.equipment_used_editText);
        findings_editText = (EditText) findViewById(R.id.findings_editText);
        inspected_by_editText = (EditText) findViewById(R.id.inspected_by_editText);
        approved_by_editText = (EditText) findViewById(R.id.approved_by_editText);

        // initialize buttons
        next_btn = (Button) findViewById(R.id.next_btn);
        clear_btn = (Button) findViewById(R.id.clear_btn);

        // adding action to next btn
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),home_page.class);
                startActivity(pass);
            }
        });
    }
}