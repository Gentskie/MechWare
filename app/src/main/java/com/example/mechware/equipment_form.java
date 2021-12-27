package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mechware.Helper.AircraftSubHelper.EquipmentHelper;
import com.example.mechware.Helper.AircraftSubHelper.MandatoryServiceHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class equipment_form extends AppCompatActivity {

    EditText date_editText, total_time_in_service_editText, item_editText, manufacturer_editText, model_editText, serial_no_editText;
    RadioButton addition_opt_eq_radioButton, removal_opt_eq_radioButton, addition_req_exch_opt_radioButton, removal_req_exch_opt_radioButton;
    RadioGroup radioGroup;
    Button clear_btn, submit_btn;

    FirebaseDatabase rootNode;
    DatabaseReference userRef, user_type_ref, aircraftRecordRef, uidRef, aircraftIDRef, equipmentID;
    FirebaseAuth mAuth;

    String user_type;
    String aircraft_id;

    ImageButton menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_form);

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

        EquipmentHelper equipmentHelper = new EquipmentHelper();

        // initialization of Edittext
        date_editText = (EditText) findViewById(R.id.date_editText);
        total_time_in_service_editText = (EditText) findViewById(R.id.total_time_in_service_editText);
        item_editText = (EditText) findViewById(R.id.item_editText);
        manufacturer_editText = (EditText) findViewById(R.id.manufacturer_editText);
        model_editText = (EditText) findViewById(R.id.model_editText);
        serial_no_editText = (EditText) findViewById(R.id.serial_no_editText);

        // initialization of radio Buttons
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        addition_opt_eq_radioButton = findViewById(R.id.addition_opt_eq_radioButton);
        removal_opt_eq_radioButton = findViewById(R.id.removal_opt_eq_radioButton);
        addition_req_exch_opt_radioButton = findViewById(R.id.addition_req_exch_opt_radioButton);
        removal_req_exch_opt_radioButton = findViewById(R.id.removal_req_exch_opt_radioButton);

        // initialization of Buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        submit_btn = (Button) findViewById(R.id.next_btn);

        // Setting action to buttons
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                equipmentHelper.setDate(date_editText.getText().toString().trim());
                equipmentHelper.setTotal_time(total_time_in_service_editText.getText().toString().trim());
                equipmentHelper.setItem(item_editText.getText().toString().trim());
                equipmentHelper.setManufacturer(manufacturer_editText.getText().toString().trim());
                equipmentHelper.setModel(model_editText.getText().toString().trim());
                equipmentHelper.setSerial(serial_no_editText.getText().toString().trim());

                if(addition_opt_eq_radioButton.isChecked()){
                    equipmentHelper.setAction("Addition of Optional Equipment");
                }
                if(removal_opt_eq_radioButton.isChecked()){
                    equipmentHelper.setAction("Removal of Optional Equipment");
                }
                if(addition_req_exch_opt_radioButton.isChecked()){
                    equipmentHelper.setAction("Addition of Required-Exchange for Optional");
                }
                if(removal_req_exch_opt_radioButton.isChecked()){
                    equipmentHelper.setAction("Removal of Required-Exchange for Optional");
                }

                equipmentID = aircraftIDRef.child("Equipment").push();
                equipmentID.setValue(equipmentHelper);

                Toast.makeText(equipment_form.this, "Equipment is successfully Added!", Toast.LENGTH_SHORT).show();
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
                item_editText.setText("");
                manufacturer_editText.setText("");
                model_editText.setText("");
                serial_no_editText.setText("");
                radioGroup.clearCheck();
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