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

import com.example.mechware.Helper.NDTHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ndt_form extends AppCompatActivity {

    Button submit_btn, clear_btn;
    EditText customer_name_editText, work_order_no_editText, address_editText, date_editText, ac_type_editText, ac_reg_no_editText, yr_model_editText, ac_serial_no_editText, ac_total_time_editText;
    EditText part_nomenclature_editText, date_accomplish_editText, place_of_inspection_editText, purpose_of_inspection_editText, ndt_method_editText, reference_document_editText, equipment_used_editText;
    EditText findings_editText, inspected_by_editText, approved_by_editText;

    FirebaseDatabase rootNode;
    DatabaseReference userRef, user_type_ref, ndtRef, uidRef;
    FirebaseAuth mAuth;

    String user_type;

    ImageButton menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndt_form);

        user_type  = getIntent().getStringExtra("user_type");

        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        userRef = rootNode.getReference("users");
        user_type_ref = userRef.child(user_type);
//        uidRef = user_type_ref.child(FirebaseAuth.getInstance().getUid());
        ndtRef = rootNode.getReference("NDT_records");

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

        // initialize buttons
        submit_btn = (Button) findViewById(R.id.next_btn);
        clear_btn = (Button) findViewById(R.id.clear_btn);

        // adding action to next btn
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ndtHelper.setUuid(mAuth.getCurrentUser().getUid());

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

                ndtRef.push().setValue(ndtHelper);

                Toast.makeText(ndt_form.this, "Non Destructive Testing is successfully Added!", Toast.LENGTH_SHORT).show();
                Intent pass = new Intent(getApplicationContext(),home_page.class);
                pass.putExtra("user_type", user_type);
                startActivity(pass);
                finish();
            }
        });

        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customer_name_editText.setText("");
                work_order_no_editText.setText("");
                address_editText.setText("");
                date_editText.setText("");
                ac_type_editText.setText("");
                ac_reg_no_editText.setText("");
                yr_model_editText.setText("");
                ac_serial_no_editText.setText("");
                ac_total_time_editText.setText("");
                part_nomenclature_editText.setText("");
                date_accomplish_editText.setText("");
                place_of_inspection_editText.setText("");
                purpose_of_inspection_editText.setText("");
                ndt_method_editText.setText("");
                reference_document_editText.setText("");
                equipment_used_editText.setText("");
                findings_editText.setText("");
                inspected_by_editText.setText("");
                approved_by_editText.setText("");
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