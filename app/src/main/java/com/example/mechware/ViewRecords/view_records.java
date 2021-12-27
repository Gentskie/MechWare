package com.example.mechware.ViewRecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.mechware.Adapter.ViewRecordsAdapter;
import com.example.mechware.Helper.AircraftRecordHelper;
import com.example.mechware.Helper.EngineRecordHelper;
import com.example.mechware.Helper.NDTHelper;
import com.example.mechware.Helper.PitotHelper;
import com.example.mechware.Helper.PropellerRecordHelper;
import com.example.mechware.Helper.ViewRecordsHelper;
import com.example.mechware.R;
import com.example.mechware.home_page;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class view_records extends AppCompatActivity {

    EditText search_editText;
    HorizontalScrollView log_records_form;
    Button log_book_btn, pitot_static_btn, ndt_btn, aircraft_record_btn;
    Button engine_record_btn, propeller_record_btn;

    boolean pitot_form = false, ndt_form = false, log = false;
    boolean aircraft_records = false, engine_records = false, propeller_records = false;

    TextView view_label;
    ConstraintLayout recyclerViewLayout;
    ArrayList<ViewRecordsHelper> viewRecordsHelperList;
    RecyclerView forms_items;
    RecyclerView.Adapter mAdapter = new ViewRecordsAdapter(viewRecordsHelperList);
    LinearLayoutManager mLayoutManager;
    
    FirebaseDatabase rootNode;
    DatabaseReference firstReference, childReference, aircraftRef, engineRef, propellerRef, pitotRef, ndtRef;
    FirebaseAuth mAuth;

    String user_type;

    ImageButton menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_records_layout);

        user_type = getIntent().getStringExtra("user_type");

        // initialization of Image view

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

        // adding actions to button
        log_book_btn = (Button) findViewById(R.id.log_book_btn);

        aircraft_record_btn = (Button) findViewById(R.id.aircraft_record_btn);
        engine_record_btn = (Button) findViewById(R.id.engine_record_btn);
        propeller_record_btn = (Button) findViewById(R.id.propeller_record_btn);

        ndt_btn = (Button) findViewById(R.id.ndt_btn);
        pitot_static_btn = (Button) findViewById(R.id.pitot_static_btn);

        //Initialize Recycler View
        viewRecordsHelperList = new ArrayList<>();
        forms_items = findViewById(R.id.recycler_view_forms_item);
        recyclerViewLayout = findViewById(R.id.recycler_view_layout);
        view_label = findViewById(R.id.view_label);

        //Initialize Firebase Database and others
        rootNode = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();


        // adding action to aircraft record
        aircraft_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aircraft_records == false){
                    //reset Recycler View and Helper
                    forms_items.setAdapter(null);
                    viewRecordsHelperList.clear();
                    //set visible the recycler view and change the text of view label
                    recyclerViewLayout.setVisibility(View.VISIBLE);

                    firstReference = rootNode.getReference("aircraft_records");
                    firstReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if(!snapshot.exists()){
                                view_label.setText("No Record");
                                return;
                            }
                            else {
                                for (DataSnapshot cs : snapshot.getChildren()) {
                                    AircraftRecordHelper helper = cs.getValue(AircraftRecordHelper.class);

                                    view_label.setText("Aircraft Record");

                                    String parent_reference = "aircraft_records";
                                    String label = helper.model + "/" + helper.serial;
                                    String item_id = cs.getKey();

                                    viewRecordsHelperList.add(new ViewRecordsHelper(item_id, label, parent_reference, user_type));

                                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                    mLayoutManager.setReverseLayout(true);
                                    mLayoutManager.setStackFromEnd(true);
                                    mAdapter = new ViewRecordsAdapter(viewRecordsHelperList);
                                    forms_items.setLayoutManager(mLayoutManager);
                                    forms_items.setAdapter(mAdapter);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
                else{
                    recyclerViewLayout.setVisibility(View.GONE);
                    view_label.setText("");
                    //reset Recycler View and Helper
                    forms_items.setAdapter(null);
                    viewRecordsHelperList.clear();

                    aircraft_records = false;
                    engine_records = true;
                    propeller_records = true;
                    ndt_form = true;
                    pitot_form = true;
                }
            }
        });

        engine_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(engine_records == false){
                    //reset Recycler View and Helper
                    forms_items.setAdapter(null);
                    viewRecordsHelperList.clear();
                    //set visible the recycler view and change the text of view label
                    recyclerViewLayout.setVisibility(View.VISIBLE);

                    firstReference = rootNode.getReference("engine_records");
                    firstReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if(!snapshot.exists()){
                                view_label.setText("No Record");
                                return;
                            }
                            else {
                                for (DataSnapshot cs : snapshot.getChildren()) {
                                    EngineRecordHelper helper = cs.getValue(EngineRecordHelper.class);

                                    view_label.setText("Engine Record");

                                    String parent_reference = "engine_records";
                                    String label = helper.model + "/" + helper.serial;
                                    String item_id = cs.getKey();

                                    viewRecordsHelperList.add(new ViewRecordsHelper(item_id, label, parent_reference, user_type));

                                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                    mLayoutManager.setReverseLayout(true);
                                    mLayoutManager.setStackFromEnd(true);
                                    mAdapter = new ViewRecordsAdapter(viewRecordsHelperList);
                                    forms_items.setLayoutManager(mLayoutManager);
                                    forms_items.setAdapter(mAdapter);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
                else {
                    recyclerViewLayout.setVisibility(View.GONE);
                    view_label.setText("");
                    //reset Recycler View and Helper
                    forms_items.setAdapter(null);
                    viewRecordsHelperList.clear();

                    engine_records = false;
                    aircraft_records = true;
                    propeller_records = true;
                    ndt_form = true;
                    pitot_form = true;
                }
            }
        });

        propeller_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(propeller_records == false){
                    //reset Recycler View and Helper
                    forms_items.setAdapter(null);
                    viewRecordsHelperList.clear();
                    //set visible the recycler view and change the text of view label
                    recyclerViewLayout.setVisibility(View.VISIBLE);

                    firstReference = rootNode.getReference("propeller_records");
                    firstReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if(!snapshot.exists()){
                                view_label.setText("No Record");
                                return;
                            }
                            else {
                                for (DataSnapshot cs : snapshot.getChildren()) {
                                    PropellerRecordHelper helper = cs.getValue(PropellerRecordHelper.class);

                                    view_label.setText("Propeller Record");

                                    String parent_reference = "propeller_records";
                                    String label = helper.model + "/" + helper.hub_series_no;
                                    String item_id = cs.getKey();

                                    viewRecordsHelperList.add(new ViewRecordsHelper(item_id, label, parent_reference, user_type));

                                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                    mLayoutManager.setReverseLayout(true);
                                    mLayoutManager.setStackFromEnd(true);
                                    mAdapter = new ViewRecordsAdapter(viewRecordsHelperList);
                                    forms_items.setLayoutManager(mLayoutManager);
                                    forms_items.setAdapter(mAdapter);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
                else {
                    recyclerViewLayout.setVisibility(View.GONE);
                    view_label.setText("");
                    //reset Recycler View and Helper
                    forms_items.setAdapter(null);
                    viewRecordsHelperList.clear();

                    aircraft_records = true;
                    engine_records = true;
                    propeller_records = false;
                    ndt_form = true;
                    pitot_form = true;
                }
            }
        });

        log_records_form = (HorizontalScrollView) findViewById(R.id.log_records_btn);

        log_book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_records_form.setVisibility(View.VISIBLE);
            }
        });

        pitot_static_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_records_form.setVisibility(View.GONE);
                if(pitot_form == false){
                    //reset Recycler View and Helper
                    forms_items.setAdapter(null);
                    viewRecordsHelperList.clear();
                    //set visible the recycler view and change the text of view label
                    recyclerViewLayout.setVisibility(View.VISIBLE);

                    firstReference = rootNode.getReference("pitot_records");
                    firstReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if(!snapshot.exists()){
                                view_label.setText("No Record");
                                return;
                            }
                            else {
                                for (DataSnapshot cs : snapshot.getChildren()) {
                                    PitotHelper helper = cs.getValue(PitotHelper.class);

                                    view_label.setText("Pitot Static Record");

                                    String parent_reference = "pitot_records";
                                    String label = helper.model_no + "/" + helper.serial_no;
                                    String item_id = cs.getKey();

                                    viewRecordsHelperList.add(new ViewRecordsHelper(item_id, label, parent_reference, user_type));

                                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                    mLayoutManager.setReverseLayout(true);
                                    mLayoutManager.setStackFromEnd(true);
                                    mAdapter = new ViewRecordsAdapter(viewRecordsHelperList);
                                    forms_items.setLayoutManager(mLayoutManager);
                                    forms_items.setAdapter(mAdapter);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
                else {
                    recyclerViewLayout.setVisibility(View.GONE);
                    view_label.setText("");
                    //reset Recycler View and Helper
                    forms_items.setAdapter(null);
                    viewRecordsHelperList.clear();

                    aircraft_records = true;
                    engine_records = true;
                    propeller_records = true;
                    ndt_form = true;
                    pitot_form = false;
                }
            }
        });
        ndt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_records_form.setVisibility(View.GONE);
                if(ndt_form == false){
                    //reset Recycler View and Helper
                    forms_items.setAdapter(null);
                    viewRecordsHelperList.clear();
                    //set visible the recycler view and change the text of view label
                    recyclerViewLayout.setVisibility(View.VISIBLE);

                    firstReference = rootNode.getReference("NDT_records");
                    firstReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if(!snapshot.exists()){
                                view_label.setText("No Record");
                                return;
                            }
                            else {
                                for (DataSnapshot cs : snapshot.getChildren()) {
                                    NDTHelper helper = cs.getValue(NDTHelper.class);

                                    view_label.setText("Non Destructive Testing Record");

                                    String parent_reference = "NDT_records";
                                    String label = helper.customer_name + "/" + helper.work_order_no;
                                    String item_id = cs.getKey();

                                    viewRecordsHelperList.add(new ViewRecordsHelper(item_id, label, parent_reference, user_type));

                                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                    mLayoutManager.setReverseLayout(true);
                                    mLayoutManager.setStackFromEnd(true);
                                    mAdapter = new ViewRecordsAdapter(viewRecordsHelperList);
                                    forms_items.setLayoutManager(mLayoutManager);
                                    forms_items.setAdapter(mAdapter);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
                else {
                    recyclerViewLayout.setVisibility(View.GONE);
                    view_label.setText("");
                    //reset Recycler View and Helper
                    forms_items.setAdapter(null);
                    viewRecordsHelperList.clear();

                    aircraft_records = true;
                    engine_records = true;
                    propeller_records = true;
                    ndt_form = true;
                    pitot_form = false;
                }
            }
        });

        // initialization of search edit text
        search_editText = (EditText) findViewById(R.id.search_editText);
        search_editText.setHint("Search");

        search_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    public void filter(String text){
        ArrayList<ViewRecordsHelper> filteredList = new ArrayList<>();

        for (ViewRecordsHelper item : viewRecordsHelperList){
            if(item.getLabel().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mAdapter = new ViewRecordsAdapter(filteredList);
        forms_items.setLayoutManager(mLayoutManager);
        forms_items.setAdapter(mAdapter);
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
                w.clearFocus();
            }
        }
        return ret;
    }

}