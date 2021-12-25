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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mechware.Helper.EngineRecordHelper;
import com.example.mechware.Helper.PropellerRecordHelper;
import com.example.mechware.R;
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

public class view_propeller_records extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    EditText manufacturer_editText, model_editText, type_editText, date_of_manufacture_editTextDate, hub_series_no_editText, blade_number_editText, blade_number_2_editText, blade_number_3_editText, blade_number_4_editText, blade_number_5_editText;
    EditText make_model_editText, make_model_2_editText, make_model_3_editText, make_model_4_editText, serial_editText, serial_2_editText, serial_3_editText, serial_4_editText;

    TextView uuid;
    ImageView menu_btn2;
    TextView textView13;
    Button restore_btn, submit_btn;
    ImageView menu_btn;

    FirebaseDatabase rootNode;
    DatabaseReference userRef, user_type_ref, uidRef, propellerRecordRef, propellerIDRef;
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
        setContentView(R.layout.view_propeller_records_layout);

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

        propellerRecordRef = rootNode.getReference(parent_ref);
        propellerIDRef = propellerRecordRef.child(item_id);

        PropellerRecordHelper propellerRecordHelper = new PropellerRecordHelper();

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

        navigationView.getMenu().findItem(R.id.nav_propeller_record).setVisible(false);
        navigationView.getMenu().setGroupVisible(R.id.aircraft_group, false);
        navigationView.getMenu().setGroupVisible(R.id.engine_group, false);

        // initialization of edit texts
        manufacturer_editText = (EditText) findViewById(R.id.manufacturer_editText);
        model_editText = (EditText) findViewById(R.id.model_editText);
        type_editText = (EditText) findViewById(R.id.type_editText);
        date_of_manufacture_editTextDate = (EditText) findViewById(R.id.date_of_manufacture_editTextDate);
        hub_series_no_editText = (EditText) findViewById(R.id.hub_series_no_editText);
        blade_number_editText = (EditText) findViewById(R.id.blade_number_editText);
        blade_number_2_editText = (EditText) findViewById(R.id.blade_number_2_editText);
        blade_number_3_editText = (EditText) findViewById(R.id.blade_number_3_editText);
        blade_number_4_editText = (EditText) findViewById(R.id.blade_number_4_editText);
        blade_number_5_editText = (EditText) findViewById(R.id.blade_number_5_editText);
        make_model_editText = (EditText) findViewById(R.id.make_model_editText);
        make_model_2_editText = (EditText) findViewById(R.id.make_model_2_editText);
        make_model_3_editText = (EditText) findViewById(R.id.make_model_3_editText);
        make_model_4_editText = (EditText) findViewById(R.id.make_model_4_editText);
        serial_editText = (EditText) findViewById(R.id.serial_editText);
        serial_2_editText = (EditText) findViewById(R.id.serial_2_editText);
        serial_3_editText = (EditText) findViewById(R.id.serial_3_editText);
        serial_4_editText = (EditText) findViewById(R.id.serial_4_editText);

        submit_btn = (Button) findViewById(R.id.next_btn);
        restore_btn = (Button) findViewById(R.id.clear_btn);

        uuid = findViewById(R.id.uuid);

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
            type_editText.setFocusable(false);
            date_of_manufacture_editTextDate.setFocusable(false);
            hub_series_no_editText.setFocusable(false);
            blade_number_editText.setFocusable(false);
            blade_number_2_editText.setFocusable(false);
            blade_number_3_editText.setFocusable(false);
            blade_number_4_editText.setFocusable(false);
            blade_number_5_editText.setFocusable(false);
            make_model_editText.setFocusable(false);
            make_model_2_editText.setFocusable(false);
            make_model_3_editText.setFocusable(false);
            make_model_4_editText.setFocusable(false);
            serial_editText.setFocusable(false);
            serial_2_editText.setFocusable(false);
            serial_3_editText.setFocusable(false);
            serial_4_editText.setFocusable(false);
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

                HashMap<String, Object> propellerMap = new HashMap<>();

                propellerMap.put("manufacturer", manufacturer_editText.getText().toString().trim());
                propellerMap.put("model", model_editText.getText().toString().trim());
                propellerMap.put("type", type_editText.getText().toString().trim());
                propellerMap.put("date_of_manufacture", date_of_manufacture_editTextDate.getText().toString().trim());
                propellerMap.put("hub_series_no", hub_series_no_editText.getText().toString().trim());
                propellerMap.put("blade_1_no", blade_number_editText.getText().toString().trim());
                propellerMap.put("blade_2_no", blade_number_2_editText.getText().toString().trim());
                propellerMap.put("blade_3_no", blade_number_3_editText.getText().toString().trim());
                propellerMap.put("blade_4_no", blade_number_4_editText.getText().toString().trim());
                propellerMap.put("blade_5_no", blade_number_5_editText.getText().toString().trim());
                propellerMap.put("make_model_1", make_model_editText.getText().toString().trim());
                propellerMap.put("make_model_2", make_model_2_editText.getText().toString().trim());
                propellerMap.put("make_model_3", make_model_3_editText.getText().toString().trim());
                propellerMap.put("make_model_4", make_model_4_editText.getText().toString().trim());
                propellerMap.put("serial_no_1", serial_editText.getText().toString().trim());
                propellerMap.put("serial_no_2", serial_2_editText.getText().toString().trim());
                propellerMap.put("serial_no_3", serial_3_editText.getText().toString().trim());
                propellerMap.put("serial_no_4", serial_4_editText.getText().toString().trim());
                propellerMap.put("uid", uuid.getText().toString().trim());

                propellerIDRef.updateChildren(propellerMap);

                Toast.makeText(view_propeller_records.this, "Propeller Record has been updated!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setRecord(){
        propellerIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                PropellerRecordHelper helper = snapshot.getValue(PropellerRecordHelper.class);

                manufacturer_editText.setText(helper.manufacturer);
                model_editText.setText(helper.model);
                type_editText.setText(helper.manufacturer);
                date_of_manufacture_editTextDate.setText(helper.date_of_manufacture);
                hub_series_no_editText.setText(helper.hub_series_no);
                blade_number_editText.setText(helper.blade_1_no);
                blade_number_2_editText.setText(helper.blade_2_no);
                blade_number_3_editText.setText(helper.blade_3_no);
                blade_number_4_editText.setText(helper.blade_4_no);
                blade_number_5_editText.setText(helper.blade_5_no);
                make_model_editText.setText(helper.make_model_1);
                make_model_2_editText.setText(helper.make_model_2);
                make_model_3_editText.setText(helper.make_model_3);
                make_model_4_editText.setText(helper.make_model_4);
                serial_editText.setText(helper.serial_no_1);
                serial_2_editText.setText(helper.serial_no_2);
                serial_3_editText.setText(helper.serial_no_3);
                serial_4_editText.setText(helper.serial_no_4);
                uuid.setText(helper.uid);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_hub_and_blade:
                propellerIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.hasChild("Hub_and_Blade_Inspection")) {
                            user_type = getIntent().getStringExtra("user_type");
                            item_id = getIntent().getStringExtra("item_id");
                            parent_ref = getIntent().getStringExtra("parent_ref");
                            action_type = getIntent().getStringExtra("action_type");

                            Intent intent = new Intent(getApplicationContext(), view_hub_and_blade_records.class);
                            intent.putExtra("user_type", user_type);
                            intent.putExtra("item_id", item_id);
                            intent.putExtra("parent_ref", parent_ref);
                            intent.putExtra("action_type", action_type);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(view_propeller_records.this, "This Propeller Record doesn't have Hub and Blade Inspection Record~", Toast.LENGTH_SHORT).show();
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