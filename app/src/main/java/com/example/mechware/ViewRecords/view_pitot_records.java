package com.example.mechware.ViewRecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mechware.Helper.PitotHelper;
import com.example.mechware.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.HashMap;

public class view_pitot_records extends AppCompatActivity {

    Button submit_btn, restore_btn;
    EditText date_performed_editText, customer_name_editText, work_order_editText, instrument_name_editText, part_no_editText, serial_no_editText, model_editText, manufacturer_editText;
    EditText temperature_editText, registration_mark_editText, standard, calibration_due_date_editText, technician_editText, caap_lic_editText, director_of_maintenance_editText, caap_lic_2_editText;
    RadioButton fit_for_use_radiobtn, not_fit_for_use_radioBtn, limited_use_radioBtn, for_repair_radioBtn, adjusted_radioBtn, good_radioBtn, no_good_radioBtn, for_repair_2_radioBtn, bench_check_radioBtn;
    RadioGroup calibration_rg, judgement_rg;

    TextView uuid;
    FirebaseDatabase rootNode;
    DatabaseReference userRef, user_type_ref, uidRef, pitotRef, pitotIDRef;
    FirebaseAuth mAuth;

    String user_type;
    String item_id;
    String parent_ref;
    String action_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pitot_records_layout);

        user_type  = getIntent().getStringExtra("user_type");
        item_id = getIntent().getStringExtra("item_id");
        parent_ref = getIntent().getStringExtra("parent_ref");
        action_type = getIntent().getStringExtra("action_type");

        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        //just in case we need the user reference , user id reference and user type
//        userRef = rootNode.getReference("users");
//        uidRef = user_type_ref.child(FirebaseAuth.getInstance().getUid());
//        user_type_ref = userRef.child(user_type);

        pitotRef = rootNode.getReference(parent_ref);
        pitotIDRef = pitotRef.child(item_id);

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

        uuid = findViewById(R.id.uuid);

        submit_btn = (Button) findViewById(R.id.next_btn);
        restore_btn = (Button) findViewById(R.id.clear_btn);

        restore_btn.setText("RESTORE DATA");

        setRecord();

        if(action_type.equalsIgnoreCase("view")){
            submit_btn.setVisibility(View.GONE);
            restore_btn.setVisibility(View.GONE);

            date_performed_editText.setFocusable(false);
            customer_name_editText.setFocusable(false);
            work_order_editText.setFocusable(false);
            instrument_name_editText.setFocusable(false);
            part_no_editText.setFocusable(false);
            serial_no_editText.setFocusable(false);
            model_editText.setFocusable(false);
            manufacturer_editText.setFocusable(false);
            temperature_editText.setFocusable(false);
            registration_mark_editText.setFocusable(false);
            standard.setFocusable(false);
            calibration_due_date_editText.setFocusable(false);
            technician_editText.setFocusable(false);
            caap_lic_editText.setFocusable(false);
            director_of_maintenance_editText.setFocusable(false);
            caap_lic_2_editText.setFocusable(false);

            calibration_rg.setEnabled(false);
            judgement_rg.setEnabled(false);
        }
        else{
            submit_btn.setVisibility(View.VISIBLE);
            restore_btn.setVisibility(View.VISIBLE);
        }

        restore_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecord();
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                pitotHelper.setDirector_caap(caap_lic_2_editText.getText().toString().trim());
                pitotHelper.setUuid(uuid.getText().toString().trim());

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

                pitotIDRef.setValue(pitotHelper);

                Toast.makeText(view_pitot_records.this, "Pitot Static Record has been updated!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setRecord(){
        pitotIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                PitotHelper helper = snapshot.getValue(PitotHelper.class);

                date_performed_editText.setText(helper.date_performed);
                customer_name_editText.setText(helper.customer_name);
                work_order_editText.setText(helper.work_order);
                instrument_name_editText.setText(helper.instrument_name);
                part_no_editText.setText(helper.part_no);
                serial_no_editText.setText(helper.serial_no);
                model_editText.setText(helper.model_no);
                manufacturer_editText.setText(helper.manufacturer);
                temperature_editText.setText(helper.temperature);
                registration_mark_editText.setText(helper.registration_mark_and_type_model);
                standard.setText(helper.standard);
                calibration_due_date_editText.setText(helper.calibration_due_date);
                technician_editText.setText(helper.avionics_technician);
                caap_lic_editText.setText(helper.techinician_caap);
                director_of_maintenance_editText.setText(helper.director_of_maintenance);
                caap_lic_2_editText.setText(helper.director_caap);
                uuid.setText(helper.uuid);

                String judgement = helper.judgement;
                String calibration = helper.calibration_results;

                if(calibration.equalsIgnoreCase("Fit for use")){
                    calibration_rg.check(R.id.fit_for_use_radiobtn);
                }
                if(calibration.equalsIgnoreCase("Not fit for use")){
                    calibration_rg.check(R.id.not_fit_for_use_radioBtn);
                }
                if(calibration.equalsIgnoreCase("Limited use")){
                    calibration_rg.check(R.id.limited_use_radioBtn);
                }
                if(calibration.equalsIgnoreCase("For repair")){
                    calibration_rg.check(R.id.for_repair_radioBtn);
                }
                if(calibration.equalsIgnoreCase("Adjusted")){
                    calibration_rg.check(R.id.adjusted_radioBtn);
                }

                if(judgement.equalsIgnoreCase("Good")){
                    judgement_rg.check(R.id.good_radioBtn);
                }
                if(judgement.equalsIgnoreCase("No Good")){
                    judgement_rg.check(R.id.no_good_radioBtn);
                }
                if(judgement.equalsIgnoreCase("For repair")){
                    judgement_rg.check(R.id.for_repair_2_radioBtn);
                }
                if(judgement.equalsIgnoreCase("Bench Check")){
                    judgement_rg.check(R.id.bench_check_radioBtn);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}