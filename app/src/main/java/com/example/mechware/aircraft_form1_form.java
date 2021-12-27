package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mechware.Helper.AircraftSubHelper.Form1Helper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class aircraft_form1_form extends AppCompatActivity {

    EditText date_editText, time_of_service_editText, empty_weight_editText, empty_cg_editText, useful_load_editText, remarks_editText;
    Button clear_btn, submit_btn;

    FirebaseDatabase rootNode;
    DatabaseReference userRef, user_type_ref, aircraftRecordRef, uidRef, aircraftIDRef;
    FirebaseAuth mAuth;

    String user_type;
    String aircraft_id;

    ImageButton menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aircraft_form1_form);

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

        Form1Helper form1Helper = new Form1Helper();

        // initialization of EditText
        date_editText = (EditText)findViewById(R.id.date_editText);
        time_of_service_editText = (EditText)findViewById(R.id.time_of_service_editText);
        empty_weight_editText = (EditText)findViewById(R.id.empty_weight_editText);
        empty_cg_editText = (EditText)findViewById(R.id.empty_cg_editText);
        useful_load_editText = (EditText)findViewById(R.id.useful_load_editText);
        remarks_editText = (EditText)findViewById(R.id.remarks_editText);

        // initialization of Buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        submit_btn = (Button) findViewById(R.id.next_btn);

        // Setting action to buttons
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                form1Helper.setDate(date_editText.getText().toString().trim());
                form1Helper.setTime_of_service(time_of_service_editText.getText().toString().trim());
                form1Helper.setEmpty_weight(empty_weight_editText.getText().toString().trim());
                form1Helper.setEmpty_CG(empty_cg_editText.getText().toString().trim());
                form1Helper.setUseful_load(useful_load_editText.getText().toString().trim());
                form1Helper.setRemarks(remarks_editText.getText().toString().trim());

                aircraftIDRef.child("Form_1").setValue(form1Helper);

                Toast.makeText(aircraft_form1_form.this, "Form 1 is successfully Added!", Toast.LENGTH_SHORT).show();
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
                time_of_service_editText.setText("");
                empty_weight_editText.setText("");
                empty_cg_editText.setText("");
                useful_load_editText.setText("");
                remarks_editText.setText("");
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