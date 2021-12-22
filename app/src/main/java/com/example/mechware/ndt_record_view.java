package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ndt_record_view extends AppCompatActivity {

    Button next_btn, clear_btn;
    EditText customer_name_editText, work_order_no_editText, address_editText, date_editText, ac_type_editText, ac_reg_no_editText, yr_model_editText, ac_serial_no_editText, ac_total_time_editText;
    EditText part_nomenclature_editText, date_accomplish_editText, place_of_inspection_editText, purpose_of_inspection_editText, ndt_method_editText, reference_document_editText, equipment_used_editText;
    EditText findings_editText, inspected_by_editText, approved_by_editText;
    String extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndt_record_view);

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

        extra = getIntent().getStringExtra("action");
        if(extra.contentEquals("view")){
            customer_name_editText.setEnabled(false);
            work_order_no_editText.setEnabled(false);
            address_editText.setEnabled(false);
            date_editText.setEnabled(false);
            date_editText.setEnabled(false);
            ac_type_editText.setEnabled(false);
            date_editText.setEnabled(false);
            ac_reg_no_editText.setEnabled(false);
            yr_model_editText.setEnabled(false);
            date_editText.setEnabled(false);
            ac_serial_no_editText.setEnabled(false);
            ac_total_time_editText.setEnabled(false);
            date_editText.setEnabled(false);
            part_nomenclature_editText.setEnabled(false);
            date_accomplish_editText.setEnabled(false);
            place_of_inspection_editText.setEnabled(false);
            purpose_of_inspection_editText.setEnabled(false);
            ndt_method_editText.setEnabled(false);
            reference_document_editText.setEnabled(false);
            equipment_used_editText.setEnabled(false);
            findings_editText.setEnabled(false);
            inspected_by_editText.setEnabled(false);
            approved_by_editText.setEnabled(false);
        }else{
            customer_name_editText.setText("");
            work_order_no_editText.setText("");
            address_editText.setText("");
            date_editText.setText("");
            date_editText.setText("");
            ac_type_editText.setText("");
            date_editText.setText("");
            ac_reg_no_editText.setText("");
            yr_model_editText.setText("");
            date_editText.setText("");
            ac_serial_no_editText.setText("");
            ac_total_time_editText.setText("");
            date_editText.setText("");
            part_nomenclature_editText.setText("");
            date_accomplish_editText.setText("");
            place_of_inspection_editText.setText("");
            purpose_of_inspection_editText.setText("");
            ndt_method_editText.setText("");
            reference_document_editText.setText("");
            equipment_used_editText.setText("");
            findings_editText.setText("");
            inspected_by_editText.setText("");
            approved_by_editText.setText("");
        }

        // initialize buttons
        next_btn = (Button) findViewById(R.id.next_btn);
        clear_btn = (Button) findViewById(R.id.clear_btn);
    }
}