package com.example.mechware.ViewRecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mechware.Helper.AircraftSubHelper.MandatoryServiceHelper;
import com.example.mechware.Helper.AircraftSubHelper.ReferenceHelper;
import com.example.mechware.R;
import com.example.mechware.aircraft_logbook;
import com.example.mechware.home_page;
import com.example.mechware.mandatory_service_form;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

public class view_mandatory_records extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TextView date_editText, total_time_in_service_editText, mandatory_editText;
    Button restore_btn, submit_btn;

    FirebaseDatabase rootNode;
    DatabaseReference userRef, user_type_ref, uidRef, aircraftRecordRef, aircraftIDRef, mandatoryRef;
    FirebaseAuth mAuth;

    String user_type;
    String item_id;
    String parent_ref;
    String action_type;

    //Navigation menu variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    boolean navigationStateOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_mandatory_records_layout);

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

        aircraftRecordRef = rootNode.getReference(parent_ref);
        aircraftIDRef = aircraftRecordRef.child(item_id);
        mandatoryRef = aircraftIDRef.child("Mandatory_Services");

        MandatoryServiceHelper mandatoryServiceHelper = new MandatoryServiceHelper();

        //Navigation Menu
        drawerLayout = findViewById(R.id.parent);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close){
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                navigationStateOpen = false;
            }
            public void onDrawerOpened(View view){
                super.onDrawerOpened(view);
                navigationStateOpen = true;
            }
        };

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().findItem(R.id.nav_mandatory).setVisible(false);
        navigationView.getMenu().setGroupVisible(R.id.engine_group, false);
        navigationView.getMenu().setGroupVisible(R.id.propeller_group, false);

        // initialization of EditText
        date_editText = (EditText)findViewById(R.id.date_editText);
        total_time_in_service_editText = (EditText)findViewById(R.id.total_time_in_service_editText);
        mandatory_editText = (EditText)findViewById(R.id.mandatory_editText);

        // initialization of Buttons
        restore_btn = (Button) findViewById(R.id.clear_btn);
        submit_btn = (Button) findViewById(R.id.next_btn);

        restore_btn.setText("RESTORE DATA");

        setRecord();
        if(action_type.equalsIgnoreCase("view")){
            submit_btn.setVisibility(View.GONE);
            restore_btn.setVisibility(View.GONE);

            date_editText.setFocusable(false);
            total_time_in_service_editText.setFocusable(false);
            mandatory_editText.setFocusable(false);

        }
        else{
            submit_btn.setVisibility(View.VISIBLE);
            restore_btn.setVisibility(View.VISIBLE);
        }

        // Setting action to buttons
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mandatoryServiceHelper.setDate(date_editText.getText().toString().trim());
                mandatoryServiceHelper.setTotal_time(total_time_in_service_editText.getText().toString().trim());
                mandatoryServiceHelper.setMandatory_service(mandatory_editText.getText().toString().trim());

                mandatoryRef.setValue(mandatoryServiceHelper);

                Toast.makeText(view_mandatory_records.this, "Mandatory Service Record has been updated", Toast.LENGTH_SHORT).show();
            }
        });

        restore_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecord();
            }
        });
    }

    public void setRecord(){
        mandatoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                MandatoryServiceHelper helper = snapshot.getValue(MandatoryServiceHelper.class);
                date_editText.setText(helper.date);
                total_time_in_service_editText.setText(helper.total_time);
                mandatory_editText.setText(helper.mandatory_service);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_aircraft_record:
                user_type = getIntent().getStringExtra("user_type");
                item_id = getIntent().getStringExtra("item_id");
                parent_ref = getIntent().getStringExtra("parent_ref");
                action_type = getIntent().getStringExtra("action_type");

                Intent intent = new Intent(this, view_aircraft_records.class);
                intent.putExtra("user_type", user_type);
                intent.putExtra("item_id", item_id);
                intent.putExtra("parent_ref", parent_ref);
                intent.putExtra("action_type", action_type);
                startActivity(intent);
                finish();
                break;

            case R.id.nav_form_1:
                aircraftIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.hasChild("Form_1")){
                            user_type  = getIntent().getStringExtra("user_type");
                            item_id = getIntent().getStringExtra("item_id");
                            parent_ref = getIntent().getStringExtra("parent_ref");
                            action_type = getIntent().getStringExtra("action_type");

                            Intent intent = new Intent(getApplicationContext(), view_form_1_records.class);
                            intent.putExtra("user_type", user_type);
                            intent.putExtra("item_id", item_id);
                            intent.putExtra("parent_ref", parent_ref);
                            intent.putExtra("action_type", action_type);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(view_mandatory_records.this, "This Aircraft Record doesn't have Form 1 Record~", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
                break;
            case R.id.nav_description:
                aircraftIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.hasChild("Description_of_Inspection")){
                            user_type  = getIntent().getStringExtra("user_type");
                            item_id = getIntent().getStringExtra("item_id");
                            parent_ref = getIntent().getStringExtra("parent_ref");
                            action_type = getIntent().getStringExtra("action_type");

                            Intent intent = new Intent(getApplicationContext(), view_reference_records.class);
                            intent.putExtra("user_type", user_type);
                            intent.putExtra("item_id", item_id);
                            intent.putExtra("parent_ref", parent_ref);
                            intent.putExtra("action_type", action_type);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(view_mandatory_records.this, "This Aircraft Record doesn't have Reference of Major Repairs Record~", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
                break;
            case R.id.nav_airworthiness:
                aircraftIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.hasChild("Airworthiness_Directive")){
                            user_type  = getIntent().getStringExtra("user_type");
                            item_id = getIntent().getStringExtra("item_id");
                            parent_ref = getIntent().getStringExtra("parent_ref");
                            action_type = getIntent().getStringExtra("action_type");

                            Intent intent = new Intent(getApplicationContext(), view_airworthiness_records.class);
                            intent.putExtra("user_type", user_type);
                            intent.putExtra("item_id", item_id);
                            intent.putExtra("parent_ref", parent_ref);
                            intent.putExtra("action_type", action_type);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(view_mandatory_records.this, "This Aircraft Record doesn't have Airworthiness Directive Record~", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
                break;
            case R.id.nav_reference:
                aircraftIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.hasChild("Reference_of_Major_Repairs")){
                            user_type  = getIntent().getStringExtra("user_type");
                            item_id = getIntent().getStringExtra("item_id");
                            parent_ref = getIntent().getStringExtra("parent_ref");
                            action_type = getIntent().getStringExtra("action_type");

                            Intent intent = new Intent(getApplicationContext(), view_reference_records.class);
                            intent.putExtra("user_type", user_type);
                            intent.putExtra("item_id", item_id);
                            intent.putExtra("parent_ref", parent_ref);
                            intent.putExtra("action_type", action_type);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(view_mandatory_records.this, "This Aircraft Record doesn't have Reference of Major Repairs Record~", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
                break;
            case R.id.nav_equipment:
                aircraftIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.hasChild("Equipment")){
                            user_type  = getIntent().getStringExtra("user_type");
                            item_id = getIntent().getStringExtra("item_id");
                            parent_ref = getIntent().getStringExtra("parent_ref");
                            action_type = getIntent().getStringExtra("action_type");

                            Intent intent = new Intent(getApplicationContext(), view_equipment_records.class);
                            intent.putExtra("user_type", user_type);
                            intent.putExtra("item_id", item_id);
                            intent.putExtra("parent_ref", parent_ref);
                            intent.putExtra("action_type", action_type);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(view_mandatory_records.this, "This Aircraft Record doesn't have Any Equipment Record~", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
                break;
            case R.id.nav_home_page:
                user_type  = getIntent().getStringExtra("user_type");


                Intent intentHomePage = new Intent(getApplicationContext(), home_page.class);
                intentHomePage.putExtra("user_type", user_type);
                startActivity(intentHomePage);
                finish();
                break;
        }
        return true;
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