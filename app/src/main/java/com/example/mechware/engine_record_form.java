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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mechware.Helper.EngineRecordHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class engine_record_form extends AppCompatActivity {

    EditText manufacturer_editText, model_editText, serial_editText, engine_belongs_to_editText, minimum_octane_editText, summer_editText, winter_editText, Magneto_time_editText;
    EditText point_setting_editText, firing_order_editText, spark_plug_gap_editText, recommended_overhaul_editText;
    Button clear_btn, submit_btn;

    FirebaseDatabase rootNode;
    DatabaseReference userRef, user_type_ref, engineRecordRef, uidRef, engineIDRef;
    FirebaseAuth mAuth;

    String user_type;

    ImageButton menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engine_record_form);

        user_type  = getIntent().getStringExtra("user_type");

        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        userRef = rootNode.getReference("users");
        user_type_ref = userRef.child(user_type);
//        uidRef = user_type_ref.child(FirebaseAuth.getInstance().getUid());
        engineRecordRef = rootNode.getReference("engine_records");

        EngineRecordHelper engineRecordHelper = new EngineRecordHelper();

        // initialization of edit texts
        manufacturer_editText = (EditText) findViewById(R.id.manufacturer_editText);
        model_editText = (EditText) findViewById(R.id.model_editText);
        serial_editText = (EditText) findViewById(R.id.serial_editText);
        engine_belongs_to_editText = (EditText) findViewById(R.id.engine_belongs_to_editText);
        minimum_octane_editText = (EditText) findViewById(R.id.minimum_octane_editText);
        summer_editText = (EditText) findViewById(R.id.summer_editText);
        winter_editText = (EditText) findViewById(R.id.winter_editText);
        Magneto_time_editText = (EditText) findViewById(R.id.Magneto_time_editText);
        point_setting_editText = (EditText) findViewById(R.id.point_setting_editText);
        firing_order_editText = (EditText) findViewById(R.id.firing_order_editText);
        spark_plug_gap_editText = (EditText) findViewById(R.id.spark_plug_gap_editText);
        recommended_overhaul_editText = (EditText) findViewById(R.id.recommended_overhaul_editText);

        // initialization of buttons
        clear_btn = (Button) findViewById(R.id.clear_btn);
        submit_btn = (Button) findViewById(R.id.next_btn);

        // setting actions to buttons
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                engineRecordHelper.setUid(mAuth.getCurrentUser().getUid());

                engineRecordHelper.setManufacturer(manufacturer_editText.getText().toString().trim());
                engineRecordHelper.setModel(model_editText.getText().toString().trim());
                engineRecordHelper.setSerial(serial_editText.getText().toString().trim());
                engineRecordHelper.setEngine_belongs_to(engine_belongs_to_editText.getText().toString().trim());
                engineRecordHelper.setMin_octane_fuel(minimum_octane_editText.getText().toString().trim());
                engineRecordHelper.setSummer(summer_editText.getText().toString().trim());
                engineRecordHelper.setWinter(winter_editText.getText().toString().trim());
                engineRecordHelper.setMagneto_time(Magneto_time_editText.getText().toString().trim());
                engineRecordHelper.setPoint_setting(point_setting_editText.getText().toString().trim());
                engineRecordHelper.setFiring_order(firing_order_editText.getText().toString().trim());
                engineRecordHelper.setSpark_plug_gap(spark_plug_gap_editText.getText().toString().trim());
                engineRecordHelper.setRecommended_overhaul(recommended_overhaul_editText.getText().toString().trim());

                engineIDRef = engineRecordRef.push();
                engineIDRef.setValue(engineRecordHelper);


                Toast.makeText(engine_record_form.this, "Engine Record is successfully Added!", Toast.LENGTH_SHORT).show();
                Intent pass = new Intent(getApplicationContext(),engine_logbook.class);
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
                serial_editText.setText("");
                engine_belongs_to_editText.setText("");
                minimum_octane_editText.setText("");
                summer_editText.setText("");
                winter_editText.setText("");
                Magneto_time_editText.setText("");
                point_setting_editText.setText("");
                firing_order_editText.setText("");
                spark_plug_gap_editText.setText("");
                recommended_overhaul_editText.setText("");

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