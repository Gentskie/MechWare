package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mechware.Helper.AircraftRecordHelper;
import com.example.mechware.Helper.AircraftSubHelper.EngineCIHelper;
import com.example.mechware.Helper.AircraftSubHelper.PropellerCIHelper;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class aircraft_form extends AppCompatActivity {

    Button submit_btn, clear_btn;
    EditText manufacturer_editText, model_editText, serial_editText, registration_no_editText, date_of_manufacture_editTextDate;
    EditText engine_manufacturer_editText, engine_model_editText, engine_serial_editText, engine_manufacturer_2_editText, engine_model_2_editText, engine_serial_2_editText;
    EditText propeller_manufacturer_editText, propeller_manufacturer_model_editText, HUB_model_editText, propeller_serial_editText, blade_model_editText, blade_model_serial_editText;
    EditText blade_model_serial_2_editText, blade_model_serial_3_editText, blade_model_2_editText, blade_model_2_serial_editText, blade_model_2_serial_2_editText, blade_model_2_serial_3_editText;

    FirebaseDatabase rootNode;
    DatabaseReference userRef, user_type_ref, aircraftRecordRef, uidRef, ECIRef, PCIRef, aircraftIDRef;
    FirebaseAuth mAuth;

    String user_type;

    ImageButton menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aircraft_form);

        user_type  = getIntent().getStringExtra("user_type");

        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        userRef = rootNode.getReference("users");
        user_type_ref = userRef.child(user_type);
//        uidRef = user_type_ref.child(FirebaseAuth.getInstance().getUid());
        aircraftRecordRef = rootNode.getReference("aircraft_records");

        AircraftRecordHelper aircraftRecordHelper = new AircraftRecordHelper();
        EngineCIHelper engineCIHelper = new EngineCIHelper();
        PropellerCIHelper propellerCIHelper = new PropellerCIHelper();

        //Model
        manufacturer_editText = (EditText) findViewById(R.id.manufacturer_editText);
        model_editText = (EditText) findViewById(R.id.model_editText);
        serial_editText = (EditText) findViewById(R.id.serial_editText);
        registration_no_editText = (EditText) findViewById(R.id.registration_no_editText);
        date_of_manufacture_editTextDate = (EditText) findViewById(R.id.date_of_manufacture_editTextDate);
        //Engine Currently Installed
        engine_manufacturer_editText = (EditText) findViewById(R.id.engine_manufacturer_editText);
        engine_model_editText = (EditText) findViewById(R.id.engine_model_editText);
        engine_serial_editText = (EditText) findViewById(R.id.engine_serial_editText);
        engine_manufacturer_2_editText = (EditText) findViewById(R.id.engine_manufacturer_2_editText);
        engine_model_2_editText = (EditText) findViewById(R.id.engine_model_2_editText);
        engine_serial_2_editText = (EditText) findViewById(R.id.engine_serial_2_editText);
        //Propeller Currently Installed
        propeller_manufacturer_editText = (EditText) findViewById(R.id.propeller_manufacturer_editText);
        propeller_manufacturer_model_editText = (EditText) findViewById(R.id.propeller_manufacturer_model_editText);
        HUB_model_editText = (EditText) findViewById(R.id.HUB_model_editText);
        propeller_serial_editText = (EditText) findViewById(R.id.propeller_serial_editText);
        blade_model_editText = (EditText) findViewById(R.id.blade_model_editText);
        blade_model_serial_editText = (EditText) findViewById(R.id.blade_model_serial_editText);
        blade_model_serial_2_editText = (EditText) findViewById(R.id.blade_model_serial_2_editText);
        blade_model_serial_3_editText = (EditText) findViewById(R.id.blade_model_serial_3_editText);
        blade_model_2_editText = (EditText) findViewById(R.id.blade_model_2_editText);
        blade_model_2_serial_editText = (EditText) findViewById(R.id.blade_model_2_serial_editText);
        blade_model_2_serial_2_editText = (EditText) findViewById(R.id.blade_model_2_serial_2_editText);
        blade_model_2_serial_3_editText = (EditText) findViewById(R.id.blade_model_2_serial_3_editText);

        submit_btn = (Button) findViewById(R.id.next_btn);
        clear_btn = (Button) findViewById(R.id.clear_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aircraftRecordHelper.setUid(mAuth.getCurrentUser().getUid());

                //Aircraft
                aircraftRecordHelper.setManufacturer(manufacturer_editText.getText().toString().trim());
                aircraftRecordHelper.setModel(model_editText.getText().toString().trim());
                aircraftRecordHelper.setSerial(serial_editText.getText().toString().trim());
                aircraftRecordHelper.setRegistration_number(registration_no_editText.getText().toString().trim());
                aircraftRecordHelper.setDate_of_manufacture(date_of_manufacture_editTextDate.getText().toString().trim());
                //Engine
                engineCIHelper.setEngine_manufacturer(engine_manufacturer_editText.getText().toString().trim());
                engineCIHelper.setEngine_model(engine_model_editText.getText().toString().trim());
                engineCIHelper.setEngine_serial(engine_serial_editText.getText().toString().trim());
                engineCIHelper.setEngine_manufacturer_2(engine_manufacturer_2_editText.getText().toString().trim());
                engineCIHelper.setEngine_model_2(engine_model_2_editText.getText().toString().trim());
                engineCIHelper.setEngine_serial_2(engine_serial_2_editText.getText().toString().trim());
                //Propeller
                propellerCIHelper.setPropeller_manufacturer(propeller_manufacturer_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_model(propeller_manufacturer_model_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_hub_model(HUB_model_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_hub_model_serial(propeller_serial_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_blade_model(blade_model_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_blade_model_serial_1(blade_model_serial_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_blade_model_serial_2(blade_model_serial_2_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_blade_model_serial_3(blade_model_serial_3_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_blade_model_2(blade_model_2_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_blade_model_2_serial_1(blade_model_2_serial_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_blade_model_2_serial_2(blade_model_2_serial_2_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_blade_model_2_serial_3(blade_model_2_serial_3_editText.getText().toString().trim());

                aircraftIDRef = aircraftRecordRef.push();
                aircraftIDRef.setValue(aircraftRecordHelper);

                aircraftIDRef.child("Engine_Currently_Installed").setValue(engineCIHelper);

                aircraftIDRef.child("Propeller_Currently_Installed").setValue(propellerCIHelper);

                Toast.makeText(aircraft_form.this, "Aircraft Record is successfully Added!", Toast.LENGTH_SHORT).show();
                Intent pass = new Intent(getApplicationContext(),aircraft_logbook.class);
                pass.putExtra("user_type", user_type);
                startActivity(pass);
                finish();
            }
        });

        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create alert dialog clear text confirmation
                manufacturer_editText.setText("");
                model_editText.setText("");
                serial_editText.setText("");
                registration_no_editText.setText("");
                date_of_manufacture_editTextDate.setText("");
                engine_manufacturer_editText.setText("");
                engine_model_editText.setText("");
                engine_serial_editText.setText("");
                engine_manufacturer_2_editText.setText("");
                engine_model_2_editText.setText("");
                engine_serial_2_editText.setText("");
                propeller_manufacturer_editText.setText("");
                propeller_manufacturer_model_editText.setText("");
                HUB_model_editText.setText("");
                propeller_serial_editText.setText("");
                blade_model_editText.setText("");
                blade_model_serial_editText.setText("");
                blade_model_serial_2_editText.setText("");
                blade_model_serial_3_editText.setText("");
                blade_model_2_editText.setText("");
                blade_model_2_serial_editText.setText("");
                blade_model_2_serial_2_editText.setText("");
                blade_model_2_serial_3_editText.setText("");
            }
        });

        menu_btn = findViewById(R.id.menu_btn2);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), aircraft_logbook.class);
                intent.putExtra("user_type", user_type);
                startActivity(intent);
                finish();
            }
        });
    }

    //for Back Button ng Cellphone
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), aircraft_logbook.class);
        intent.putExtra("user_type", user_type);
        startActivity(intent);
        finish();
    }

    //Hide soft keyboard when touched the outside of the edit text.
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom()) ) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }
}