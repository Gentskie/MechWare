package com.example.mechware.ViewRecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mechware.Helper.AircraftRecordHelper;
import com.example.mechware.Helper.AircraftSubHelper.EngineCIHelper;
import com.example.mechware.Helper.AircraftSubHelper.PropellerCIHelper;
import com.example.mechware.R;
import com.example.mechware.aircraft_form;
import com.example.mechware.home_page;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.HashMap;

public class view_aircraft_records extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView uuid;
    ImageView menu_btn2;
    TextView textView13;
    Button submit_btn, restore_btn;
    EditText manufacturer_editText, model_editText, serial_editText, registration_no_editText, date_of_manufacture_editTextDate;
    EditText engine_manufacturer_editText, engine_model_editText, engine_serial_editText, engine_manufacturer_2_editText, engine_model_2_editText, engine_serial_2_editText;
    EditText propeller_manufacturer_editText, propeller_manufacturer_model_editText, HUB_model_editText, propeller_serial_editText, blade_model_editText, blade_model_serial_editText;
    EditText blade_model_serial_2_editText, blade_model_serial_3_editText, blade_model_2_editText, blade_model_2_serial_editText, blade_model_2_serial_2_editText, blade_model_2_serial_3_editText;

    FirebaseDatabase rootNode;
    DatabaseReference userRef, user_type_ref, uidRef, aircraftRecordRef , ECIRef, PCIRef, aircraftIDRef;
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
        setContentView(R.layout.view_aircraft_records_layout);

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
        ECIRef = aircraftIDRef.child("Engine_Currently_Installed");
        PCIRef = aircraftIDRef.child("Propeller_Currently_Installed");

        AircraftRecordHelper aircraftRecordHelper = new AircraftRecordHelper();
        EngineCIHelper engineCIHelper = new EngineCIHelper();
        PropellerCIHelper propellerCIHelper = new PropellerCIHelper();

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

        navigationView.getMenu().findItem(R.id.nav_aircraft_record).setVisible(false);
        navigationView.getMenu().setGroupVisible(R.id.engine_group, false);
        navigationView.getMenu().setGroupVisible(R.id.propeller_group, false);

        //Model
        manufacturer_editText = (EditText) findViewById(R.id.manufacturer_editText);
        model_editText = (EditText) findViewById(R.id.model_editText);
        serial_editText = (EditText) findViewById(R.id.serial_editText);
        registration_no_editText = (EditText) findViewById(R.id.registration_no_editText);
        date_of_manufacture_editTextDate = (EditText) findViewById(R.id.date_of_manufacture_editTextDate);
        //Engine Currently Installed
        engine_manufacturer_editText = (EditText) findViewById(R.id.engine_manufacturer_editText);
        engine_model_editText = (EditText) findViewById(R.id.engine_model_editText);
        engine_serial_editText = (EditText) findViewById(R.id.engine_serial_editText);
        engine_manufacturer_2_editText = (EditText) findViewById(R.id.engine_manufacturer_2_editText);
        engine_model_2_editText = (EditText) findViewById(R.id.engine_model_2_editText);
        engine_serial_2_editText = (EditText) findViewById(R.id.engine_serial_2_editText);
        //Propeller Currently Installed
        propeller_manufacturer_editText = (EditText) findViewById(R.id.propeller_manufacturer_editText);
        propeller_manufacturer_model_editText = (EditText) findViewById(R.id.propeller_manufacturer_model_editText);
        HUB_model_editText = (EditText) findViewById(R.id.HUB_model_editText);
        propeller_serial_editText = (EditText) findViewById(R.id.propeller_serial_editText);
        blade_model_editText = (EditText) findViewById(R.id.blade_model_editText);
        blade_model_serial_editText = (EditText) findViewById(R.id.blade_model_serial_editText);
        blade_model_serial_2_editText = (EditText) findViewById(R.id.blade_model_serial_2_editText);
        blade_model_serial_3_editText = (EditText) findViewById(R.id.blade_model_serial_3_editText);
        blade_model_2_editText = (EditText) findViewById(R.id.blade_model_2_editText);
        blade_model_2_serial_editText = (EditText) findViewById(R.id.blade_model_2_serial_editText);
        blade_model_2_serial_2_editText = (EditText) findViewById(R.id.blade_model_2_serial_2_editText);
        blade_model_2_serial_3_editText = (EditText) findViewById(R.id.blade_model_2_serial_3_editText);

        uuid = findViewById(R.id.uuid);

        submit_btn = (Button) findViewById(R.id.next_btn);
        restore_btn = (Button) findViewById(R.id.clear_btn);

        menu_btn2 = findViewById(R.id.menu_btn2);
        textView13 = findViewById(R.id.textView13);

        menu_btn2.setVisibility(View.GONE);
        textView13.setVisibility(View.GONE);

        restore_btn.setText("RESTORE DATA");

        setRecord();

        if(action_type.equalsIgnoreCase("view")){
            submit_btn.setVisibility(View.GONE);
            restore_btn.setVisibility(View.GONE);

            manufacturer_editText.setFocusable(false);
            model_editText.setFocusable(false);
            serial_editText.setFocusable(false);
            registration_no_editText.setFocusable(false);
            date_of_manufacture_editTextDate.setFocusable(false);
            //Engine Currently Installed
            engine_manufacturer_editText.setFocusable(false);
            engine_model_editText.setFocusable(false);
            engine_serial_editText.setFocusable(false);
            engine_manufacturer_2_editText.setFocusable(false);
            engine_model_2_editText.setFocusable(false);
            engine_serial_2_editText.setFocusable(false);
            //Propeller Currently Installed
            propeller_manufacturer_editText.setFocusable(false);
            propeller_manufacturer_model_editText.setFocusable(false);
            HUB_model_editText.setFocusable(false);
            propeller_serial_editText.setFocusable(false);
            blade_model_editText.setFocusable(false);
            blade_model_serial_editText.setFocusable(false);
            blade_model_serial_2_editText.setFocusable(false);
            blade_model_serial_3_editText.setFocusable(false);
            blade_model_2_editText.setFocusable(false);
            blade_model_2_serial_editText.setFocusable(false);
            blade_model_2_serial_2_editText.setFocusable(false);
            blade_model_2_serial_3_editText.setFocusable(false);
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


                //Aircraft
                HashMap<String, Object> aircraftMap = new HashMap<>();
                aircraftMap.put("manufacturer", manufacturer_editText.getText().toString().trim());
                aircraftMap.put("date_of_manufacture", date_of_manufacture_editTextDate.getText().toString().trim());
                aircraftMap.put("model",model_editText.getText().toString().trim() );
                aircraftMap.put("registration_number", registration_no_editText.getText().toString().trim());
                aircraftMap.put("serial", serial_editText.getText().toString().trim());
                aircraftMap.put("uid", uuid.getText().toString().trim());

                //Engine
                engineCIHelper.setEngine_manufacturer(engine_manufacturer_editText.getText().toString().trim());
                engineCIHelper.setEngine_model(engine_model_editText.getText().toString().trim());
                engineCIHelper.setEngine_serial(engine_serial_editText.getText().toString().trim());
                engineCIHelper.setEngine_manufacturer_2(engine_manufacturer_2_editText.getText().toString().trim());
                engineCIHelper.setEngine_model_2(engine_model_2_editText.getText().toString().trim());
                engineCIHelper.setEngine_serial_2(engine_serial_2_editText.getText().toString().trim());
                //Propeller
                propellerCIHelper.setPropeller_manufacturer(propeller_manufacturer_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_model(propeller_manufacturer_model_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_hub_model(HUB_model_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_hub_model_serial(propeller_serial_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_blade_model(blade_model_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_blade_model_serial_1(blade_model_serial_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_blade_model_serial_2(blade_model_serial_2_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_blade_model_serial_3(blade_model_serial_3_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_blade_model_2(blade_model_2_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_blade_model_2_serial_1(blade_model_2_serial_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_blade_model_2_serial_2(blade_model_2_serial_2_editText.getText().toString().trim());
                propellerCIHelper.setPropeller_blade_model_2_serial_3(blade_model_2_serial_3_editText.getText().toString().trim());

                aircraftIDRef.updateChildren(aircraftMap);
                ECIRef.setValue(engineCIHelper);
                PCIRef.setValue(propellerCIHelper);

                Toast.makeText(view_aircraft_records.this, "Aircraft Record has been updated!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setRecord(){
        aircraftIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                AircraftRecordHelper a_helper = snapshot.getValue(AircraftRecordHelper.class);

                manufacturer_editText.setText(a_helper.manufacturer);
                model_editText.setText(a_helper.model);
                serial_editText.setText(a_helper.serial);
                registration_no_editText.setText(a_helper.registration_number);
                date_of_manufacture_editTextDate.setText(a_helper.date_of_manufacture);
                uuid.setText(a_helper.uid);

                ECIRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        EngineCIHelper e_helper = snapshot.getValue(EngineCIHelper.class);

                        engine_manufacturer_editText.setText(e_helper.engine_manufacturer);
                        engine_model_editText.setText(e_helper.engine_model);
                        engine_serial_editText.setText(e_helper.engine_serial);
                        engine_manufacturer_2_editText.setText(e_helper.engine_manufacturer_2);
                        engine_model_2_editText.setText(e_helper.engine_model_2);
                        engine_serial_2_editText.setText(e_helper.engine_serial_2);

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) { }
                });

                PCIRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        PropellerCIHelper p_helper = snapshot.getValue(PropellerCIHelper.class);

                        propeller_manufacturer_editText.setText(p_helper.propeller_manufacturer);
                        propeller_manufacturer_model_editText.setText(p_helper.propeller_model);
                        HUB_model_editText.setText(p_helper.propeller_hub_model);
                        propeller_serial_editText.setText(p_helper.propeller_hub_model_serial);
                        blade_model_editText.setText(p_helper.propeller_blade_model);
                        blade_model_serial_editText.setText(p_helper.propeller_blade_model_serial_1);
                        blade_model_serial_2_editText.setText(p_helper.propeller_blade_model_serial_2);
                        blade_model_serial_3_editText.setText(p_helper.propeller_blade_model_serial_3);
                        blade_model_2_editText.setText(p_helper.propeller_blade_model_2);
                        blade_model_2_serial_editText.setText(p_helper.propeller_blade_model_2_serial_1);
                        blade_model_2_serial_2_editText.setText(p_helper.propeller_blade_model_2_serial_2);
                        blade_model_2_serial_3_editText.setText(p_helper.propeller_blade_model_2_serial_3);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) { }
                });
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) { }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_form_1:
                aircraftIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if(snapshot.hasChild("Form_1")){
                        drawerLayout.closeDrawer(Gravity.LEFT, false);
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
                    }
                    else{
                        Toast.makeText(view_aircraft_records.this, "This Aircraft Record doesn't have Form 1 Record~", Toast.LENGTH_SHORT).show();
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
                            drawerLayout.closeDrawer(Gravity.LEFT, false);
                            user_type  = getIntent().getStringExtra("user_type");
                            item_id = getIntent().getStringExtra("item_id");
                            parent_ref = getIntent().getStringExtra("parent_ref");
                            action_type = getIntent().getStringExtra("action_type");

                            Intent intent = new Intent(getApplicationContext(), view_description_records.class);
                            intent.putExtra("user_type", user_type);
                            intent.putExtra("item_id", item_id);
                            intent.putExtra("parent_ref", parent_ref);
                            intent.putExtra("action_type", action_type);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(view_aircraft_records.this, "This Aircraft Record doesn't have Description of Inspection Record~", Toast.LENGTH_SHORT).show();
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
                            drawerLayout.closeDrawer(Gravity.LEFT, false);
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
                        }
                        else{
                            Toast.makeText(view_aircraft_records.this, "This Aircraft Record doesn't have Reference of Major Repairs Record~", Toast.LENGTH_SHORT).show();
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
                            drawerLayout.closeDrawer(Gravity.LEFT, false);
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
                        }
                        else{
                            Toast.makeText(view_aircraft_records.this, "This Aircraft Record doesn't have Airworthiness Directive Record~", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
                break;
            case R.id.nav_mandatory:
                aircraftIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.hasChild("Mandatory_Services")){
                            drawerLayout.closeDrawer(Gravity.LEFT, false);
                            user_type  = getIntent().getStringExtra("user_type");
                            item_id = getIntent().getStringExtra("item_id");
                            parent_ref = getIntent().getStringExtra("parent_ref");
                            action_type = getIntent().getStringExtra("action_type");

                            Intent intent = new Intent(getApplicationContext(), view_mandatory_records.class);
                            intent.putExtra("user_type", user_type);
                            intent.putExtra("item_id", item_id);
                            intent.putExtra("parent_ref", parent_ref);
                            intent.putExtra("action_type", action_type);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(view_aircraft_records.this, "This Aircraft Record doesn't have Mandatory Services Record~", Toast.LENGTH_SHORT).show();
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
                            drawerLayout.closeDrawer(Gravity.LEFT, false);
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
                        }
                        else{
                            Toast.makeText(view_aircraft_records.this, "This Aircraft Record doesn't have Any Equipment Record~", Toast.LENGTH_SHORT).show();
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

                drawerLayout.closeDrawer(Gravity.LEFT, false);
                Intent intent = new Intent(getApplicationContext(), home_page.class);
                intent.putExtra("user_type", user_type);
                startActivity(intent);
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