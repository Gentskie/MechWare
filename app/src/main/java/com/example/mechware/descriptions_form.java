package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mechware.Helper.AircraftSubHelper.DescriptionHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class descriptions_form extends AppCompatActivity {

    EditText date_editText, tach_time_editText, todays_flight_editText, total_time_in_service_editText, description_editText;
    Button clear_btn, submit_btn;

    FirebaseDatabase rootNode;
    DatabaseReference userRef, user_type_ref, aircraftRecordRef, uidRef, aircraftIDRef;
    FirebaseAuth mAuth;

    String user_type;
    String aircraft_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descriptions_form);

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

        DescriptionHelper descriptionHelper = new DescriptionHelper();

        // initialization of EditText
        date_editText = (EditText)findViewById(R.id.date_editText);
        tach_time_editText = (EditText)findViewById(R.id.tach_time_editText);
        todays_flight_editText = (EditText)findViewById(R.id.todays_flight_editText);
        total_time_in_service_editText = (EditText)findViewById(R.id.total_time_in_service_editText);
        description_editText = (EditText)findViewById(R.id.description_editText);

        // initialization of Buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        submit_btn = (Button) findViewById(R.id.next_btn);

        // Setting action to buttons
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                descriptionHelper.setDate(date_editText.getText().toString().trim());
                descriptionHelper.setTach_time(tach_time_editText.getText().toString().trim());
                descriptionHelper.setTodays_flight(todays_flight_editText.getText().toString().trim());
                descriptionHelper.setTotal_time(total_time_in_service_editText.getText().toString().trim());
                descriptionHelper.setDescription(description_editText.getText().toString().trim());

                aircraftIDRef.child("Description_of_Inspection").setValue(descriptionHelper);

                Toast.makeText(descriptions_form.this, "Description of Inspection is successfully Added!", Toast.LENGTH_SHORT).show();
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
                tach_time_editText.setText("");
                todays_flight_editText.setText("");
                total_time_in_service_editText.setText("");
                description_editText.setText("");
            }
        });
    }
}