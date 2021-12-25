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

import com.example.mechware.Helper.PropellerRecordHelper;
import com.example.mechware.Helper.PropellerSubHelper.HubAndBladeHelper;
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

public class view_hub_and_blade_records extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    EditText date_editText, total_time_in_service_editText, next_inspection_due_editText, time_since_overhaul, description_editText, mech_cert_editText;

    TextView uuid;
    ImageView menu_btn2;
    TextView textView13;
    Button restore_btn, submit_btn;
    ImageView menu_btn;

    FirebaseDatabase rootNode;
    DatabaseReference userRef, user_type_ref, uidRef, propellerRecordRef, propellerIDRef, hubandbladeRef;
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
        setContentView(R.layout.view_hub_and_blade_records_layout);

        user_type = getIntent().getStringExtra("user_type");
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
        hubandbladeRef = propellerIDRef.child("Hub_and_Blade_Inspection");

        HubAndBladeHelper hubAndBladeHelper = new HubAndBladeHelper();

        //Navigation Menu
        drawerLayout = findViewById(R.id.parent);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                navigationStateOpen = false;
            }

            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                navigationStateOpen = true;
            }
        };

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().findItem(R.id.nav_hub_and_blade).setVisible(false);
        navigationView.getMenu().setGroupVisible(R.id.aircraft_group, false);
        navigationView.getMenu().setGroupVisible(R.id.engine_group, false);

        // initialization of edit texts
        date_editText = (EditText) findViewById(R.id.date_editText);
        total_time_in_service_editText = (EditText) findViewById(R.id.total_time_in_service_editText);
        next_inspection_due_editText = (EditText) findViewById(R.id.next_inspection_due_editText);
        time_since_overhaul = (EditText) findViewById(R.id.time_since_overhaul);
        description_editText = (EditText) findViewById(R.id.description_editText);
        mech_cert_editText = (EditText) findViewById(R.id.mech_cert_editText);

        submit_btn = (Button) findViewById(R.id.next_btn);
        restore_btn = (Button) findViewById(R.id.clear_btn);

        uuid = findViewById(R.id.uuid);

        menu_btn2 = findViewById(R.id.menu_btn2);
        textView13 = findViewById(R.id.textView13);

        menu_btn2.setVisibility(View.GONE);
        textView13.setVisibility(View.GONE);

        restore_btn.setText("RESTORE DATA");

        setRecord();

        if (action_type.equalsIgnoreCase("view")) {
            submit_btn.setVisibility(View.GONE);
            restore_btn.setVisibility(View.GONE);

            date_editText.setFocusable(false);
            total_time_in_service_editText.setFocusable(false);
            next_inspection_due_editText.setFocusable(false);
            time_since_overhaul.setFocusable(false);
            description_editText.setFocusable(false);
            mech_cert_editText.setFocusable(false);
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

                hubAndBladeHelper.setDate(date_editText.getText().toString().trim());
                hubAndBladeHelper.setTotal_time(total_time_in_service_editText.getText().toString().trim());
                hubAndBladeHelper.setNext_inspection_due(next_inspection_due_editText.getText().toString().trim());
                hubAndBladeHelper.setTime_since_overhaul(time_since_overhaul.getText().toString().trim());
                hubAndBladeHelper.setDescription(description_editText.getText().toString().trim());
                hubAndBladeHelper.setMech_cert_no_or_repair_station_no(mech_cert_editText.getText().toString().trim());

                hubandbladeRef.setValue(hubAndBladeHelper);

                Toast.makeText(view_hub_and_blade_records.this, "Hub and Blade Inspection Record has been updated!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setRecord(){
        hubandbladeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                HubAndBladeHelper helper = snapshot.getValue(HubAndBladeHelper.class);

                date_editText.setText(helper.date);
                total_time_in_service_editText.setText(helper.total_time);
                next_inspection_due_editText.setText(helper.next_inspection_due);
                time_since_overhaul.setText(helper.time_since_overhaul);
                description_editText.setText(helper.description);
                mech_cert_editText.setText(helper.mech_cert_no_or_repair_station_no);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_propeller_record:
                user_type = getIntent().getStringExtra("user_type");
                item_id = getIntent().getStringExtra("item_id");
                parent_ref = getIntent().getStringExtra("parent_ref");
                action_type = getIntent().getStringExtra("action_type");

                Intent intent = new Intent(getApplicationContext(), view_propeller_records.class);
                intent.putExtra("user_type", user_type);
                intent.putExtra("item_id", item_id);
                intent.putExtra("parent_ref", parent_ref);
                intent.putExtra("action_type", action_type);
                startActivity(intent);
                finish();
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