package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mechware.Helper.PropellerSubHelper.HubAndBladeHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class hub_and_blade_inspections_form extends AppCompatActivity {

    EditText date_editText, total_time_in_service_editText, next_inspection_due_editText, time_since_overhaul, description_editText, mech_cert_editText;
    Button clear_btn, submit_btn;

    FirebaseDatabase rootNode;
    DatabaseReference userRef, user_type_ref, propellerRecordRef, uidRef, propellerIDRef;
    FirebaseAuth mAuth;

    String user_type;
    String propeller_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_and_blade_inspections_form);

        user_type  = getIntent().getStringExtra("user_type");
        propeller_id = getIntent().getStringExtra("propeller_id");

        //Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        userRef = rootNode.getReference("users");
        user_type_ref = userRef.child(user_type);
//        uidRef = user_type_ref.child(FirebaseAuth.getInstance().getUid());
        propellerRecordRef = rootNode.getReference("propeller_records");
        propellerIDRef = propellerRecordRef.child(propeller_id);

        HubAndBladeHelper hubAndBladeHelper = new HubAndBladeHelper();

        // initialization of edit texts
        date_editText = (EditText) findViewById(R.id.date_editText);
        total_time_in_service_editText = (EditText) findViewById(R.id.total_time_in_service_editText);
        next_inspection_due_editText = (EditText) findViewById(R.id.next_inspection_due_editText);
        time_since_overhaul = (EditText) findViewById(R.id.time_since_overhaul);
        description_editText = (EditText) findViewById(R.id.description_editText);
        mech_cert_editText = (EditText) findViewById(R.id.mech_cert_editText);

        // initialization of Buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        submit_btn = (Button) findViewById(R.id.next_btn);

        // adding action to next button
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hubAndBladeHelper.setDate(date_editText.getText().toString().trim());
                hubAndBladeHelper.setTotal_time(total_time_in_service_editText.getText().toString().trim());
                hubAndBladeHelper.setNext_inspection_due(next_inspection_due_editText.getText().toString().trim());
                hubAndBladeHelper.setTime_since_overhaul(time_since_overhaul.getText().toString().trim());
                hubAndBladeHelper.setDescription(description_editText.getText().toString().trim());
                hubAndBladeHelper.setMech_cert_no_or_repair_station_no(mech_cert_editText.getText().toString().trim());

                propellerIDRef.child("Hub_and_Blade_Inspection").setValue(hubAndBladeHelper);

                Toast.makeText(hub_and_blade_inspections_form.this, "Hub and Blade Inspection is successfully Added!", Toast.LENGTH_SHORT).show();
                Intent pass = new Intent(getApplicationContext(),propeller_logbook.class);
                pass.putExtra("user_type", user_type);
                startActivity(pass);
                finish();
            }
        });

        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_editText.setText("");
                total_time_in_service_editText.setText("");
                next_inspection_due_editText.setText("");
                time_since_overhaul.setText("");
                description_editText.setText("");
                mech_cert_editText.setText("");
            }
        });
    }
}