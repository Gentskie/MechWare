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

import com.example.mechware.Helper.AircraftSubHelper.DescriptionHelper;
import com.example.mechware.Helper.EngineSubHelper.EngineRegisteredOwnerHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registered_owner_record_form extends AppCompatActivity {

    EditText owner_name_editText, address_editText, state_editText, city_editText, from_editText, to_editText;
    Button clear_btn, submit_btn;


    FirebaseDatabase rootNode;
    DatabaseReference userRef, user_type_ref, engineRecordRef, uidRef, engineIDRef;
    FirebaseAuth mAuth;

    String user_type;
    String engine_id;

    ImageButton menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_owner_record_form);

        user_type  = getIntent().getStringExtra("user_type");
        engine_id = getIntent().getStringExtra("engine_id");

        //Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        userRef = rootNode.getReference("users");
        user_type_ref = userRef.child(user_type);
//        uidRef = user_type_ref.child(FirebaseAuth.getInstance().getUid());
        engineRecordRef = rootNode.getReference("engine_records");
        engineIDRef = engineRecordRef.child(engine_id);

        EngineRegisteredOwnerHelper engineRegisteredOwnerHelper = new EngineRegisteredOwnerHelper();

        // initialization of text fields
        owner_name_editText = (EditText) findViewById(R.id.owner_name_editText);
        address_editText = (EditText) findViewById(R.id.address_editText);
        state_editText = (EditText) findViewById(R.id.state_editText);
        city_editText = (EditText) findViewById(R.id.city_editText);
        from_editText = (EditText) findViewById(R.id.from_editText);
        to_editText = (EditText) findViewById(R.id.to_editText);

        // initialization of buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        submit_btn = (Button) findViewById(R.id.next_btn);


        // setting action to next button
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                engineRegisteredOwnerHelper.setName(owner_name_editText.getText().toString().trim());
                engineRegisteredOwnerHelper.setAddress(address_editText.getText().toString().trim());
                engineRegisteredOwnerHelper.setState(state_editText.getText().toString().trim());
                engineRegisteredOwnerHelper.setCity(city_editText.getText().toString().trim());
                engineRegisteredOwnerHelper.setFrom(from_editText.getText().toString().trim());
                engineRegisteredOwnerHelper.setTo(to_editText.getText().toString().trim());

                engineIDRef.child("Registered_Owner_Record").setValue(engineRegisteredOwnerHelper);

                Toast.makeText(registered_owner_record_form.this, "Registered Owner is successfully Added!", Toast.LENGTH_SHORT).show();
                Intent pass = new Intent(getApplicationContext(),engine_logbook.class);
                pass.putExtra("user_type", user_type);
                startActivity(pass);
                finish();
            }
        });

        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                owner_name_editText.setText("");
                address_editText.setText("");
                state_editText.setText("");
                city_editText.setText("");
                from_editText.setText("");
                to_editText.setText("");
            }
        });

        menu_btn = findViewById(R.id.menu_btn2);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), engine_logbook.class);
                intent.putExtra("user_type", user_type);
                startActivity(intent);
                finish();
            }
        });
    }

    //for Back Button ng Cellphone
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), engine_logbook.class);
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