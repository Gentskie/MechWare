package com.example.mechware.ViewRecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mechware.Helper.AircraftSubHelper.Equipment.EquipmentSubHelper;
import com.example.mechware.Helper.AircraftSubHelper.EquipmentHelper;
import com.example.mechware.R;
import com.example.mechware.home_page;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class view_equipment_records extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TextView textview15;

    AutoCompleteTextView equipments_input;
    TextInputLayout equipments_layout;

    EditText date_editText, total_time_in_service_editText, item_editText, manufacturer_editText, model_editText, serial_no_editText;
    RadioButton addition_opt_eq_radioButton, removal_opt_eq_radioButton, addition_req_exch_opt_radioButton, removal_req_exch_opt_radioButton;
    RadioGroup radioGroup;
    Button restore_btn, submit_btn;

    FirebaseDatabase rootNode;
    DatabaseReference userRef, user_type_ref, uidRef, aircraftRecordRef, aircraftIDRef, equipmentRef, equipmentIDRef;
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

    ArrayList<String> al_records;

    List<EquipmentSubHelper> equipmentSubHelperList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_equipment_records_layout);

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
        equipmentRef = aircraftIDRef.child("Equipment");

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

        navigationView.getMenu().findItem(R.id.nav_equipment).setVisible(false);
        navigationView.getMenu().setGroupVisible(R.id.engine_group, false);
        navigationView.getMenu().setGroupVisible(R.id.propeller_group, false);

        textview15 = findViewById(R.id.textView15);
        textview15.setVisibility(View.GONE);

        // initialization of Edittext
        date_editText = (EditText) findViewById(R.id.date_editText);
        total_time_in_service_editText = (EditText) findViewById(R.id.total_time_in_service_editText);
        item_editText = (EditText) findViewById(R.id.item_editText);
        manufacturer_editText = (EditText) findViewById(R.id.manufacturer_editText);
        model_editText = (EditText) findViewById(R.id.model_editText);
        serial_no_editText = (EditText) findViewById(R.id.serial_no_editText);

        // initialization of radio Buttons
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        addition_opt_eq_radioButton = findViewById(R.id.addition_opt_eq_radioButton);
        removal_opt_eq_radioButton = findViewById(R.id.removal_opt_eq_radioButton);
        addition_req_exch_opt_radioButton = findViewById(R.id.addition_req_exch_opt_radioButton);
        removal_req_exch_opt_radioButton = findViewById(R.id.removal_req_exch_opt_radioButton);

        equipments_input = findViewById(R.id.equipment_dropdown_input);
        equipments_layout = findViewById(R.id.equipment_dropdown);

        // initialization of Buttons
        restore_btn = (Button) findViewById(R.id.clear_btn);
        submit_btn = (Button) findViewById(R.id.next_btn);

        restore_btn.setText("RESTORE DATA");

        setDropDown();
        setRecord();

        if(action_type.equalsIgnoreCase("view")){
            submit_btn.setVisibility(View.GONE);
            restore_btn.setVisibility(View.GONE);

            date_editText.setFocusable(false);
            total_time_in_service_editText.setFocusable(false);
            item_editText.setFocusable(false);
            manufacturer_editText.setFocusable(false);
            model_editText.setFocusable(false);
            serial_no_editText.setFocusable(false);

            radioGroup.setEnabled(false);
            addition_opt_eq_radioButton.setEnabled(false);
            removal_opt_eq_radioButton.setEnabled(false);
            addition_req_exch_opt_radioButton.setEnabled(false);
            removal_req_exch_opt_radioButton.setEnabled(false);

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
    }

    public void setRecord(){
        EquipmentHelper equipmentHelper = new EquipmentHelper();

        equipments_layout.setHint("Select an Equipment");
        equipments_input.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String equipment_id = equipmentSubHelperList.get(position).getEquipment_id();
//                String manufacturer = selectAircraft.get(position).getAircraft_info();
//                Log.i("SELECTED", ac_id + "/ " + manufacturer);
                equipmentIDRef = equipmentRef.child(equipment_id);
                equipmentIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        EquipmentHelper helper = snapshot.getValue(EquipmentHelper.class);
                        date_editText.setText(helper.date);
                        total_time_in_service_editText.setText(helper.total_time);
                        item_editText.setText(helper.item);
                        manufacturer_editText.setText(helper.manufacturer);
                        model_editText.setText(helper.model);
                        serial_no_editText.setText(helper.serial);

                        String action = helper.action;

                        if(action.equalsIgnoreCase("Addition of Optional Equipment")){radioGroup.check(R.id.addition_opt_eq_radioButton);}
                        else if(action.equalsIgnoreCase("Removal of Optional Equipment")){radioGroup.check(R.id.removal_opt_eq_radioButton);}
                        else if(action.equalsIgnoreCase("Addition of Required-Exchange for Optional")){radioGroup.check(R.id.addition_req_exch_opt_radioButton);}
                        else if(action.equalsIgnoreCase("Removal of Required-Exchange for Optional")){radioGroup.check(R.id.removal_req_exch_opt_radioButton);}

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

                // Setting action to buttons
                submit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        equipmentHelper.setDate(date_editText.getText().toString().trim());
                        equipmentHelper.setTotal_time(total_time_in_service_editText.getText().toString().trim());
                        equipmentHelper.setItem(item_editText.getText().toString().trim());
                        equipmentHelper.setManufacturer(manufacturer_editText.getText().toString().trim());
                        equipmentHelper.setModel(model_editText.getText().toString().trim());
                        equipmentHelper.setSerial(serial_no_editText.getText().toString().trim());

                        if(addition_opt_eq_radioButton.isChecked()){
                            equipmentHelper.setAction("Addition of Optional Equipment");
                        }
                        if(removal_opt_eq_radioButton.isChecked()){
                            equipmentHelper.setAction("Removal of Optional Equipment");
                        }
                        if(addition_req_exch_opt_radioButton.isChecked()){
                            equipmentHelper.setAction("Addition of Required-Exchange for Optional");
                        }
                        if(removal_req_exch_opt_radioButton.isChecked()){
                            equipmentHelper.setAction("Removal of Required-Exchange for Optional");
                        }

                        equipmentIDRef.setValue(equipmentHelper);

                        Toast.makeText(view_equipment_records.this, "Equipment Record has been updated", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    public void setDropDown(){
        equipmentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    String equipment_id = ds.getKey().toString();
                    equipmentRef.child(equipment_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            EquipmentHelper helper = snapshot.getValue(EquipmentHelper.class);
                            EquipmentSubHelper subHelper = new EquipmentSubHelper();
                            subHelper.setEquipment_id(equipment_id);
                            subHelper.setLabel(helper.getModel()+"/"+helper.getSerial());
                            equipmentSubHelperList.add(subHelper);

                            al_records = new ArrayList<>();
                            for(int i = 0; i < equipmentSubHelperList.size(); i++){
                                al_records.add(equipmentSubHelperList.get(i).getLabel());
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.drop_down, al_records);
                            equipments_input.setAdapter(adapter);
                            equipments_input.setThreshold(1);

                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
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
                drawerLayout.closeDrawer(Gravity.LEFT, false);
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
                break;

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
                            Toast.makeText(view_equipment_records.this, "This Aircraft Record doesn't have Form 1 Record~", Toast.LENGTH_SHORT).show();
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

                            Intent intent = new Intent(getApplicationContext(), view_reference_records.class);
                            intent.putExtra("user_type", user_type);
                            intent.putExtra("item_id", item_id);
                            intent.putExtra("parent_ref", parent_ref);
                            intent.putExtra("action_type", action_type);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(view_equipment_records.this, "This Aircraft Record doesn't have Reference of Major Repairs Record~", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(view_equipment_records.this, "This Aircraft Record doesn't have Airworthiness Directive Record~", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(view_equipment_records.this, "This Aircraft Record doesn't have Reference of Major Repairs Record~", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(view_equipment_records.this, "This Aircraft Record doesn't have Any Equipment Record~", Toast.LENGTH_SHORT).show();
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