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
import android.widget.Toast;

import com.example.mechware.Helper.EngineSubHelper.EngineDescriptionHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class description_engine_form extends AppCompatActivity {

    EditText date_editText, tach_time_editText, todays_flight_editText, engine_total_time_in_service, description_editText;
    Button clear_btn, submit_btn;

    FirebaseDatabase rootNode;
    DatabaseReference userRef, user_type_ref, engineRecordRef, uidRef, engineIDRef;
    FirebaseAuth mAuth;

    String user_type;
    String engine_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_engine_form);

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

        EngineDescriptionHelper engineDescriptionHelper = new EngineDescriptionHelper();

        // initialization of EditText
        date_editText = (EditText)findViewById(R.id.date_editText);
        tach_time_editText = (EditText)findViewById(R.id.tach_time_editText);
        todays_flight_editText = (EditText)findViewById(R.id.todays_flight_editText);
        engine_total_time_in_service = (EditText)findViewById(R.id.engine_total_time_in_service);
        description_editText = (EditText)findViewById(R.id.description_editText);

        // initialization of Buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        submit_btn = (Button) findViewById(R.id.next_btn);

        // Setting action to buttons
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                engineDescriptionHelper.setDate(date_editText.getText().toString().trim());
                engineDescriptionHelper.setTach_time(tach_time_editText.getText().toString().trim());
                engineDescriptionHelper.setTodays_flight(todays_flight_editText.getText().toString().trim());
                engineDescriptionHelper.setTotal_time(engine_total_time_in_service.getText().toString().trim());
                engineDescriptionHelper.setDescription(description_editText.getText().toString().trim());

                engineIDRef.child("Description_of_Inspection").setValue(engineDescriptionHelper);

                Toast.makeText(description_engine_form.this, "Description of inspection is successfully Added!", Toast.LENGTH_SHORT).show();
                Intent pass = new Intent(getApplicationContext(),engine_logbook.class);
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
                engine_total_time_in_service.setText("");
                description_editText.setText("");
            }
        });
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