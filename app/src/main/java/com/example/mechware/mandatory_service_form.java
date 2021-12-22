package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mechware.Helper.AircraftSubHelper.AirworthinessHelper;
import com.example.mechware.Helper.AircraftSubHelper.MandatoryServiceHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class mandatory_service_form extends AppCompatActivity {

    EditText date_editText, total_time_in_service_editText, mandatory_editText;
    Button clear_btn, submit_btn;

    FirebaseDatabase rootNode;
    DatabaseReference userRef, user_type_ref, aircraftRecordRef, uidRef, aircraftIDRef;
    FirebaseAuth mAuth;

    String user_type;
    String aircraft_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madatory_service_form);

        user_type  = getIntent().getStringExtra("user_type");
        aircraft_id = getIntent().getStringExtra("aircraft_id");

        //Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        userRef = rootNode.getReference("users");
        user_type_ref = userRef.child(user_type);
//        uidRef = user_type_ref.child(FirebaseAuth.getInstance().getUid());
        aircraftRecordRef = rootNode.getReference("aircraft_records");
        aircraftIDRef = aircraftRecordRef.child(aircraft_id);

        MandatoryServiceHelper mandatoryServiceHelper = new MandatoryServiceHelper();

        // initialization of EditText
        date_editText = (EditText)findViewById(R.id.date_editText);
        total_time_in_service_editText = (EditText)findViewById(R.id.total_time_in_service_editText);
        mandatory_editText = (EditText)findViewById(R.id.mandatory_editText);

        // initialization of Buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        submit_btn = (Button) findViewById(R.id.next_btn);

        // Setting action to buttons
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mandatoryServiceHelper.setDate(date_editText.getText().toString().trim());
                mandatoryServiceHelper.setTotal_time(total_time_in_service_editText.getText().toString().trim());
                mandatoryServiceHelper.setMandatory_service(mandatory_editText.getText().toString().trim());

                aircraftIDRef.child("Mandatory_Services").setValue(mandatoryServiceHelper);

                Toast.makeText(mandatory_service_form.this, "Mandatory Service is successfully Added!", Toast.LENGTH_SHORT).show();
                Intent pass = new Intent(getApplicationContext(),aircraft_logbook.class);
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
                mandatory_editText.setText("");
            }
        });
    }
}