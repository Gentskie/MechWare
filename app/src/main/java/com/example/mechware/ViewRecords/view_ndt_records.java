package com.example.mechware.ViewRecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mechware.Helper.NDTHelper;
import com.example.mechware.Helper.PitotHelper;
import com.example.mechware.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

public class view_ndt_records extends AppCompatActivity {
    Button submit_btn, restore_btn;

    EditText customer_name_editText, work_order_no_editText, address_editText, date_editText, ac_type_editText, ac_reg_no_editText, yr_model_editText, ac_serial_no_editText, ac_total_time_editText;
    EditText part_nomenclature_editText, date_accomplish_editText, place_of_inspection_editText, purpose_of_inspection_editText, ndt_method_editText, reference_document_editText, equipment_used_editText;
    EditText findings_editText, inspected_by_editText, approved_by_editText;

    TextView uuid;
    FirebaseDatabase rootNode;
    DatabaseReference userRef, user_type_ref, uidRef, ndtRef, ndtIDRef;
    FirebaseAuth mAuth;

    String user_type;
    String item_id;
    String parent_ref;
    String action_type;

    ImageButton menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_ndt_records_layout);

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

        ndtRef = rootNode.getReference(parent_ref);
        ndtIDRef = ndtRef.child(item_id);

        NDTHelper ndtHelper = new NDTHelper();

        // initialize edit texts
        customer_name_editText = (EditText) findViewById(R.id.customer_name_editText);
        work_order_no_editText = (EditText) findViewById(R.id.work_order_no_editText);
        address_editText = (EditText) findViewById(R.id.address_editText);
        date_editText = (EditText) findViewById(R.id.date_editText);
        ac_type_editText = (EditText) findViewById(R.id.ac_type_editText);
        ac_reg_no_editText = (EditText) findViewById(R.id.ac_reg_no_editText);
        yr_model_editText = (EditText) findViewById(R.id.yr_model_editText);
        ac_serial_no_editText = (EditText) findViewById(R.id.ac_serial_no_editText);
        ac_total_time_editText = (EditText) findViewById(R.id.ac_total_time_editText);
        part_nomenclature_editText = (EditText) findViewById(R.id.part_nomenclature_editText);
        date_accomplish_editText = (EditText) findViewById(R.id.date_accomplish_editText);
        place_of_inspection_editText = (EditText) findViewById(R.id.place_of_inspection_editText);
        purpose_of_inspection_editText = (EditText) findViewById(R.id.purpose_of_inspection_editText);
        ndt_method_editText = (EditText) findViewById(R.id.ndt_method_editText);
        reference_document_editText = (EditText) findViewById(R.id.reference_document_editText);
        equipment_used_editText = (EditText) findViewById(R.id.equipment_used_editText);
        findings_editText = (EditText) findViewById(R.id.findings_editText);
        inspected_by_editText = (EditText) findViewById(R.id.inspected_by_editText);
        approved_by_editText = (EditText) findViewById(R.id.approved_by_editText);

        uuid = findViewById(R.id.uuid);

        submit_btn = (Button) findViewById(R.id.next_btn);
        restore_btn = (Button) findViewById(R.id.clear_btn);

        restore_btn.setText("RESTORE DATA");

        setRecord();

        if(action_type.equalsIgnoreCase("view")){
            submit_btn.setVisibility(View.GONE);
            restore_btn.setVisibility(View.GONE);

            customer_name_editText.setFocusable(false);
            work_order_no_editText.setFocusable(false);
            address_editText.setFocusable(false);
            date_editText.setFocusable(false);
            ac_type_editText.setFocusable(false);
            ac_reg_no_editText.setFocusable(false);
            yr_model_editText.setFocusable(false);
            ac_serial_no_editText.setFocusable(false);
            ac_total_time_editText.setFocusable(false);
            part_nomenclature_editText.setFocusable(false);
            date_accomplish_editText.setFocusable(false);
            place_of_inspection_editText.setFocusable(false);
            purpose_of_inspection_editText.setFocusable(false);
            ndt_method_editText.setFocusable(false);
            reference_document_editText.setFocusable(false);
            equipment_used_editText.setFocusable(false);
            findings_editText.setFocusable(false);
            inspected_by_editText.setFocusable(false);
            approved_by_editText.setFocusable(false);
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

                ndtHelper.setCustomer_name(customer_name_editText.getText().toString().trim());
                ndtHelper.setWork_order_no(work_order_no_editText.getText().toString().trim());
                ndtHelper.setAddress(address_editText.getText().toString().trim());
                ndtHelper.setDate(date_editText.getText().toString().trim());
                ndtHelper.setAc_type(ac_type_editText.getText().toString().trim());
                ndtHelper.setAc_reg_no(ac_reg_no_editText.getText().toString().trim());
                ndtHelper.setYr_model(yr_model_editText.getText().toString().trim());
                ndtHelper.setAc_serial_no(ac_serial_no_editText.getText().toString().trim());
                ndtHelper.setAc_total_time(ac_total_time_editText.getText().toString().trim());
                ndtHelper.setPart_nomenclature(part_nomenclature_editText.getText().toString().trim());
                ndtHelper.setDate_accomplish(date_accomplish_editText.getText().toString().trim());
                ndtHelper.setPlace_of_inspection(place_of_inspection_editText.getText().toString().trim());
                ndtHelper.setPurpose_of_inspection(purpose_of_inspection_editText.getText().toString().trim());
                ndtHelper.setNdt_method(ndt_method_editText.getText().toString().trim());
                ndtHelper.setReference_documents(reference_document_editText.getText().toString().trim());
                ndtHelper.setEquipment_used(equipment_used_editText.getText().toString().trim());
                ndtHelper.setFindings(findings_editText.getText().toString().trim());
                ndtHelper.setInspected_by(inspected_by_editText.getText().toString().trim());
                ndtHelper.setApproved_by(approved_by_editText.getText().toString().trim());
                ndtHelper.setUuid(uuid.getText().toString().trim());

                ndtIDRef.setValue(ndtHelper);

                Toast.makeText(view_ndt_records.this, "NDT Record has been updated!", Toast.LENGTH_SHORT).show();
            }
        });

        menu_btn = findViewById(R.id.menu_btn2);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), view_records.class);
                intent.putExtra("user_type", user_type);
                startActivity(intent);
                finish();
            }
        });

    }

    //for Back Button ng Cellphone
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), view_records.class);
        intent.putExtra("user_type", user_type);
        startActivity(intent);
        finish();
    }

    public void setRecord(){
        ndtIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                NDTHelper helper = snapshot.getValue(NDTHelper.class);

                uuid.setText(helper.uuid);
                customer_name_editText.setText(helper.customer_name);
                work_order_no_editText.setText(helper.work_order_no);
                address_editText.setText(helper.address);
                date_editText.setText(helper.date);
                ac_type_editText.setText(helper.ac_type);
                ac_reg_no_editText.setText(helper.ac_reg_no);
                yr_model_editText.setText(helper.yr_model);
                ac_serial_no_editText.setText(helper.ac_serial_no);
                ac_total_time_editText.setText(helper.ac_total_time);
                part_nomenclature_editText.setText(helper.part_nomenclature);
                date_accomplish_editText.setText(helper.date_accomplish);
                place_of_inspection_editText.setText(helper.place_of_inspection);
                purpose_of_inspection_editText.setText(helper.purpose_of_inspection);
                ndt_method_editText.setText(helper.ndt_method);
                reference_document_editText.setText(helper.reference_documents);
                equipment_used_editText.setText(helper.equipment_used);
                findings_editText.setText(helper.findings);
                inspected_by_editText.setText(helper.inspected_by);
                approved_by_editText.setText(helper.approved_by);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}