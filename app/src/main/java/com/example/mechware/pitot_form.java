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

import com.example.mechware.Helper.PitotHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class pitot_form extends AppCompatActivity {

    Button submit_btn, clear_btn;
    EditText date_performed_editText, customer_name_editText, work_order_editText, instrument_name_editText, part_no_editText, serial_no_editText, model_editText, manufacturer_editText;
    EditText temperature_editText, registration_mark_editText, standard, calibration_due_date_editText, technician_editText, caap_lic_editText, director_of_maintenance_editText, caap_lic_2_editText;
    RadioButton fit_for_use_radiobtn, not_fit_for_use_radioBtn, limited_use_radioBtn, for_repair_radioBtn, adjusted_radioBtn, good_radioBtn, no_good_radioBtn, for_repair_2_radioBtn, bench_check_radioBtn;
    RadioGroup calibration_rg, judgement_rg;

    FirebaseDatabase rootNode;
    DatabaseReference userRef, user_type_ref, pitotRef, uidRef;
    FirebaseAuth mAuth;

    String user_type;

    ImageButton menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitot_form);

        user_type  = getIntent().getStringExtra("user_type");

        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        userRef = rootNode.getReference("users");
        user_type_ref = userRef.child(user_type);
//        uidRef = user_type_ref.child(FirebaseAuth.getInstance().getUid());
        pitotRef = rootNode.getReference("pitot_records");

        PitotHelper pitotHelper = new PitotHelper();

        // initialization of edit texts
        date_performed_editText = (EditText) findViewById(R.id.date_performed_editText);
        customer_name_editText = (EditText) findViewById(R.id.customer_name_editText);
        work_order_editText = (EditText) findViewById(R.id.word_order_editText);
        instrument_name_editText = (EditText) findViewById(R.id.instrument_name_editText);
        part_no_editText = (EditText) findViewById(R.id.part_no_editText);
        serial_no_editText = (EditText) findViewById(R.id.serial_no_editText);
        model_editText = (EditText) findViewById(R.id.model_editText);
        manufacturer_editText = (EditText) findViewById(R.id.manufacturer_editText);
        temperature_editText = (EditText) findViewById(R.id.temperature_editText);
        registration_mark_editText = (EditText) findViewById(R.id.registration_mark_editText);
        standard = (EditText) findViewById(R.id.standard);
        calibration_due_date_editText = (EditText) findViewById(R.id.calibration_due_date_editText);
        technician_editText = (EditText) findViewById(R.id.technician_editText);
        caap_lic_editText = (EditText) findViewById(R.id.caap_lic_editText);
        director_of_maintenance_editText = (EditText) findViewById(R.id.director_of_maintenance_editText);
        caap_lic_2_editText = (EditText) findViewById(R.id.caap_lic_2_editText);

        calibration_rg = findViewById(R.id.calibration_result_rg);
        judgement_rg = findViewById(R.id.judgement_rg);

        //Calibration Result
        fit_for_use_radiobtn = findViewById(R.id.fit_for_use_radiobtn);
        not_fit_for_use_radioBtn = findViewById(R.id.fit_for_use_radiobtn);
        limited_use_radioBtn = findViewById(R.id.limited_use_radioBtn);
        for_repair_radioBtn = findViewById(R.id.for_repair_radioBtn);
        adjusted_radioBtn = findViewById(R.id.adjusted_radioBtn);

        //Judgement
        good_radioBtn = findViewById(R.id.good_radioBtn);
        no_good_radioBtn = findViewById(R.id.no_good_radioBtn);
        for_repair_2_radioBtn = findViewById(R.id.for_repair_2_radioBtn);
        bench_check_radioBtn = findViewById(R.id.bench_check_radioBtn);

        // initialization  of buttons
        submit_btn = (Button) findViewById(R.id.next_btn);
        clear_btn = (Button) findViewById(R.id.clear_btn);

        // adding actions to next btn
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pitotHelper.setUuid(mAuth.getCurrentUser().getUid());

                pitotHelper.setDate_performed(date_performed_editText.getText().toString().trim());
                pitotHelper.setCustomer_name(customer_name_editText.getText().toString().trim());
                pitotHelper.setWork_order(work_order_editText.getText().toString().trim());
                pitotHelper.setInstrument_name(instrument_name_editText.getText().toString().trim());
                pitotHelper.setPart_no(part_no_editText.getText().toString().trim());
                pitotHelper.setSerial_no(serial_no_editText.getText().toString().trim());
                pitotHelper.setModel_no(model_editText.getText().toString().trim());
                pitotHelper.setManufacturer(manufacturer_editText.getText().toString().trim());
                pitotHelper.setTemperature(temperature_editText.getText().toString().trim());
                pitotHelper.setRegistration_mark_and_type_model(registration_mark_editText.getText().toString().trim());
                pitotHelper.setStandard(standard.getText().toString().trim());
                pitotHelper.setCalibration_due_date(calibration_due_date_editText.getText().toString().trim());
                pitotHelper.setAvionics_technician(technician_editText.getText().toString().trim());
                pitotHelper.setTechinician_caap(caap_lic_editText.getText().toString().trim());
                pitotHelper.setDirector_of_maintenance(director_of_maintenance_editText.getText().toString().trim());
                pitotHelper.setDirector_of_maintenance(caap_lic_2_editText.getText().toString().trim());

                //Calibration Result
                if(fit_for_use_radiobtn.isChecked()){
                    pitotHelper.setCalibration_results("Fit for use");
                }
                else if(not_fit_for_use_radioBtn.isChecked()){
                    pitotHelper.setCalibration_results("Not fit for use");
                }
                else if(limited_use_radioBtn.isChecked()){
                    pitotHelper.setCalibration_results("Limited use");
                }
                else if(for_repair_radioBtn.isChecked()){
                    pitotHelper.setCalibration_results("For repair");
                }
                else if(adjusted_radioBtn.isChecked()){
                    pitotHelper.setCalibration_results("Adjusted");
                }

                //Judgement
                if(good_radioBtn.isChecked()){
                    pitotHelper.setJudgement("Good");
                }
                else if(no_good_radioBtn.isChecked()){
                    pitotHelper.setJudgement("No Good");
                }
                else if(for_repair_2_radioBtn.isChecked()){
                    pitotHelper.setJudgement("For repair");
                }
                else if(bench_check_radioBtn.isChecked()){
                    pitotHelper.setJudgement("Bench Check");
                }

                pitotRef.push().setValue(pitotHelper);

                Toast.makeText(pitot_form.this, "Pitot static system test result is successfully Added!", Toast.LENGTH_SHORT).show();
                Intent pass = new Intent(getApplicationContext(),home_page.class);
                pass.putExtra("user_type", user_type);
                startActivity(pass);
                finish();
            }
        });

        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_performed_editText.setText("");
                customer_name_editText.setText("");
                work_order_editText.setText("");
                instrument_name_editText.setText("");
                part_no_editText.setText("");
                serial_no_editText.setText("");
                model_editText.setText("");
                manufacturer_editText.setText("");
                temperature_editText.setText("");
                registration_mark_editText.setText("");
                standard.setText("");
                calibration_due_date_editText.setText("");
                technician_editText.setText("");
                caap_lic_editText.setText("");
                director_of_maintenance_editText.setText("");
                caap_lic_2_editText.setText("");

                calibration_rg.clearCheck();
                judgement_rg.clearCheck();
            }
        });

        menu_btn = findViewById(R.id.menu_btn2);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), home_page.class);
                intent.putExtra("user_type", user_type);
                startActivity(intent);
                finish();
            }
        });

    }

    //for Back Button ng Cellphone
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), home_page.class);
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