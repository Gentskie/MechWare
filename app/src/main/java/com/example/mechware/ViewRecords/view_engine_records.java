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

import com.example.mechware.Helper.AircraftRecordHelper;
import com.example.mechware.Helper.AircraftSubHelper.EngineCIHelper;
import com.example.mechware.Helper.AircraftSubHelper.PropellerCIHelper;
import com.example.mechware.Helper.EngineRecordHelper;
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

public class view_engine_records extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TextView uuid;
    ImageView menu_btn2;
    TextView textView13;
    EditText manufacturer_editText, model_editText, serial_editText, engine_belongs_to_editText, minimum_octane_editText, summer_editText, winter_editText, Magneto_time_editText;
    EditText point_setting_editText, firing_order_editText, spark_plug_gap_editText, recommended_overhaul_editText;
    Button restore_btn, submit_btn;
    ImageView menu_btn;

    FirebaseDatabase rootNode;
    DatabaseReference userRef, user_type_ref, uidRef, engineRecordRef, engineIDRef;
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
        setContentView(R.layout.view_engine_records_layout);

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

        engineRecordRef = rootNode.getReference(parent_ref);
        engineIDRef = engineRecordRef.child(item_id);

        EngineRecordHelper engineRecordHelper = new EngineRecordHelper();

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

        navigationView.getMenu().findItem(R.id.nav_engine_record).setVisible(false);
        navigationView.getMenu().setGroupVisible(R.id.aircraft_group, false);
        navigationView.getMenu().setGroupVisible(R.id.propeller_group, false);

        submit_btn = (Button) findViewById(R.id.next_btn);
        restore_btn = (Button) findViewById(R.id.clear_btn);

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
            serial_editText.setFocusable(false);
            engine_belongs_to_editText.setFocusable(false);
            minimum_octane_editText.setFocusable(false);
            summer_editText.setFocusable(false);
            winter_editText.setFocusable(false);
            Magneto_time_editText.setFocusable(false);
            point_setting_editText.setFocusable(false);
            firing_order_editText.setFocusable(false);
            spark_plug_gap_editText.setFocusable(false);
            recommended_overhaul_editText.setFocusable(false);

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

                HashMap<String, Object> engineRecordMap = new HashMap<>();
                engineRecordMap.put("manufacturer", manufacturer_editText.getText().toString().trim());
                engineRecordMap.put("model", model_editText.getText().toString().trim());
                engineRecordMap.put("serial", serial_editText.getText().toString().trim());
                engineRecordMap.put("engine_belongs_to", engine_belongs_to_editText.getText().toString().trim());
                engineRecordMap.put("min_octane_fuel", minimum_octane_editText.getText().toString().trim());
                engineRecordMap.put("summer", summer_editText.getText().toString().trim());
                engineRecordMap.put("winter", winter_editText.getText().toString().trim());
                engineRecordMap.put("magneto_time", Magneto_time_editText.getText().toString().trim());

                engineRecordMap.put("point_setting", point_setting_editText.getText().toString().trim());
                engineRecordMap.put("firing_order", firing_order_editText.getText().toString().trim());
                engineRecordMap.put("spark_plug_gap", spark_plug_gap_editText.getText().toString().trim());
                engineRecordMap.put("recommended_overhaul", recommended_overhaul_editText.getText().toString().trim());

                engineIDRef.updateChildren(engineRecordMap);

                Toast.makeText(view_engine_records.this, "Engine Record has been updated!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setRecord(){
        engineIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                EngineRecordHelper helper = snapshot.getValue(EngineRecordHelper.class);

                manufacturer_editText.setText(helper.manufacturer);
                model_editText.setText(helper.model);
                serial_editText.setText(helper.serial);
                engine_belongs_to_editText.setText(helper.engine_belongs_to);
                minimum_octane_editText.setText(helper.min_octane_fuel);
                summer_editText.setText(helper.summer);
                winter_editText.setText(helper.winter);
                Magneto_time_editText.setText(helper.magneto_time);
                point_setting_editText.setText(helper.point_setting);
                firing_order_editText.setText(helper.firing_order);
                spark_plug_gap_editText.setText(helper.spark_plug_gap);
                recommended_overhaul_editText.setText(helper.recommended_overhaul);

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
            case R.id.nav_engine_registered_owner:
                engineIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.hasChild("Registered_Owner_Record")) {
                            user_type = getIntent().getStringExtra("user_type");
                            item_id = getIntent().getStringExtra("item_id");
                            parent_ref = getIntent().getStringExtra("parent_ref");
                            action_type = getIntent().getStringExtra("action_type");

                            Intent intent = new Intent(getApplicationContext(), view_engine_registered_owner_records.class);
                            intent.putExtra("user_type", user_type);
                            intent.putExtra("item_id", item_id);
                            intent.putExtra("parent_ref", parent_ref);
                            intent.putExtra("action_type", action_type);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(view_engine_records.this, "This Engine Record doesn't have Registered Owner Record~", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
                break;

            case R.id.nav_engine_description:
                engineIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.hasChild("Description_of_Inspection")) {
                            user_type = getIntent().getStringExtra("user_type");
                            item_id = getIntent().getStringExtra("item_id");
                            parent_ref = getIntent().getStringExtra("parent_ref");
                            action_type = getIntent().getStringExtra("action_type");

                            Intent intent = new Intent(getApplicationContext(), view_engine_description_records.class);
                            intent.putExtra("user_type", user_type);
                            intent.putExtra("item_id", item_id);
                            intent.putExtra("parent_ref", parent_ref);
                            intent.putExtra("action_type", action_type);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(view_engine_records.this, "This Engine Record doesn't have Description of Inspection Record~", Toast.LENGTH_SHORT).show();
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