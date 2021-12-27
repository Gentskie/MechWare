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
import android.widget.Toast;

import com.example.mechware.Helper.EngineRecordHelper;
import com.example.mechware.Helper.PropellerRecordHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class propeller_record_form extends AppCompatActivity {

    EditText manufacturer_editText, model_editText, type_editText, date_of_manufacture_editTextDate, hub_series_no_editText, blade_number_editText, blade_number_2_editText, blade_number_3_editText, blade_number_4_editText, blade_number_5_editText;
    EditText make_model_editText, make_model_2_editText, make_model_3_editText, make_model_4_editText, serial_editText, serial_2_editText, serial_3_editText, serial_4_editText;
    Button clear_btn, submit_btn;

    FirebaseDatabase rootNode;
    DatabaseReference userRef, user_type_ref, propellerRecordRef, uidRef, propellerIDRef;
    FirebaseAuth mAuth;

    String user_type;

    ImageButton menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propeller_record_form);

        user_type  = getIntent().getStringExtra("user_type");

        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        userRef = rootNode.getReference("users");
        user_type_ref = userRef.child(user_type);
//        uidRef = user_type_ref.child(FirebaseAuth.getInstance().getUid());
        propellerRecordRef = rootNode.getReference("propeller_records");

        PropellerRecordHelper propellerRecordHelper = new PropellerRecordHelper();

        // initialization of edit texts
        manufacturer_editText = (EditText) findViewById(R.id.manufacturer_editText);
        model_editText = (EditText) findViewById(R.id.model_editText);
        type_editText = (EditText) findViewById(R.id.type_editText);
        date_of_manufacture_editTextDate = (EditText) findViewById(R.id.date_of_manufacture_editTextDate);
        hub_series_no_editText = (EditText) findViewById(R.id.hub_series_no_editText);
        blade_number_editText = (EditText) findViewById(R.id.blade_number_editText);
        blade_number_2_editText = (EditText) findViewById(R.id.blade_number_2_editText);
        blade_number_3_editText = (EditText) findViewById(R.id.blade_number_3_editText);
        blade_number_4_editText = (EditText) findViewById(R.id.blade_number_4_editText);
        blade_number_5_editText = (EditText) findViewById(R.id.blade_number_5_editText);
        make_model_editText = (EditText) findViewById(R.id.make_model_editText);
        make_model_2_editText = (EditText) findViewById(R.id.make_model_2_editText);
        make_model_3_editText = (EditText) findViewById(R.id.make_model_3_editText);
        make_model_4_editText = (EditText) findViewById(R.id.make_model_4_editText);
        serial_editText = (EditText) findViewById(R.id.serial_editText);
        serial_2_editText = (EditText) findViewById(R.id.serial_2_editText);
        serial_3_editText = (EditText) findViewById(R.id.serial_3_editText);
        serial_4_editText = (EditText) findViewById(R.id.serial_4_editText);

        // initialization of Buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        submit_btn = (Button) findViewById(R.id.next_btn);

        // setting action to next button
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                propellerRecordHelper.setUid(mAuth.getCurrentUser().getUid());

                propellerRecordHelper.setManufacturer(manufacturer_editText.getText().toString().trim());
                propellerRecordHelper.setModel(model_editText.getText().toString().trim());
                propellerRecordHelper.setType(type_editText.getText().toString().trim());
                propellerRecordHelper.setDate_of_manufacture(date_of_manufacture_editTextDate.getText().toString().trim());
                propellerRecordHelper.setHub_series_no(hub_series_no_editText.getText().toString().trim());
                propellerRecordHelper.setBlade_1_no(blade_number_editText.getText().toString().trim());
                propellerRecordHelper.setBlade_2_no(blade_number_2_editText.getText().toString().trim());
                propellerRecordHelper.setBlade_3_no(blade_number_3_editText.getText().toString().trim());
                propellerRecordHelper.setBlade_4_no(blade_number_4_editText.getText().toString().trim());
                propellerRecordHelper.setBlade_5_no(blade_number_5_editText.getText().toString().trim());
                propellerRecordHelper.setMake_model_1(make_model_editText.getText().toString().trim());
                propellerRecordHelper.setMake_model_2(make_model_2_editText.getText().toString().trim());
                propellerRecordHelper.setMake_model_3(make_model_3_editText.getText().toString().trim());
                propellerRecordHelper.setMake_model_4(make_model_4_editText.getText().toString().trim());
                propellerRecordHelper.setSerial_no_1(serial_editText.getText().toString().trim());
                propellerRecordHelper.setSerial_no_2(serial_2_editText.getText().toString().trim());
                propellerRecordHelper.setSerial_no_3(serial_3_editText.getText().toString().trim());
                propellerRecordHelper.setSerial_no_4(serial_4_editText.getText().toString().trim());

                propellerIDRef = propellerRecordRef.push();

                propellerIDRef.setValue(propellerRecordHelper);

                Toast.makeText(propeller_record_form.this, "Propeller Record is successfully Added!", Toast.LENGTH_SHORT).show();
                Intent pass = new Intent(getApplicationContext(),propeller_logbook.class);
                pass.putExtra("user_type", user_type);
                startActivity(pass);
                finish();
            }
        });

        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manufacturer_editText.setText("");
                model_editText.setText("");
                type_editText.setText("");
                date_of_manufacture_editTextDate.setText("");
                hub_series_no_editText.setText("");
                blade_number_editText.setText("");
                blade_number_2_editText.setText("");
                blade_number_3_editText.setText("");
                blade_number_4_editText.setText("");
                blade_number_5_editText.setText("");
                make_model_editText.setText("");
                make_model_2_editText.setText("");
                make_model_3_editText.setText("");
                make_model_4_editText.setText("");
                serial_editText.setText("");
                serial_2_editText.setText("");
                serial_3_editText.setText("");
                serial_4_editText.setText("");
            }
        });

        menu_btn = findViewById(R.id.menu_btn2);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), propeller_logbook.class);
                intent.putExtra("user_type", user_type);
                startActivity(intent);
                finish();
            }
        });
    }

    //for Back Button ng Cellphone
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), propeller_logbook.class);
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