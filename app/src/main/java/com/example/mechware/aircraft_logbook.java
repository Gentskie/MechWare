package com.example.mechware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mechware.Helper.AircraftRecordHelper;
import com.example.mechware.Helper.SelectAircraftHelper;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class aircraft_logbook extends AppCompatActivity {

    TextView textView6;
    Button aircraft_record_btn, form_1_btn, description_btn, reference_btn, airworthiness_btn, mandatory_btn, equipment_btn;
    ImageView menu_btn;

    String user_type;

    Dialog select_aircraft;
    AppCompatButton confirm, cancel;
    AutoCompleteTextView aircraft_input;

    FirebaseDatabase rootNode;
    DatabaseReference aircraftRef;
    FirebaseAuth mAuth;

    //testing variable, possible changes here
    ArrayList<String> al_aircraft_records;

    List<SelectAircraftHelper> selectAircraft = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aircraft_logbook);


        user_type = getIntent().getStringExtra("user_type");

        mAuth = FirebaseAuth.getInstance();

        rootNode = FirebaseDatabase.getInstance();
        aircraftRef = rootNode.getReference("aircraft_records");

        setUpAircraftDropdown();

        // changing lorem text to bold
        textView6 = (TextView) findViewById(R.id.textView6);
        String text = "Aircraft Logbook";
        Spannable string = new SpannableString(text);
        StyleSpan bold = new StyleSpan(Typeface.BOLD);
        string.setSpan(bold, 0, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView6.setText(string);

        // adding action to Image View
        // menu_btn
        menu_btn = (ImageView) findViewById(R.id.menu_btn);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),home_page.class);
                startActivity(pass);
            }
        });

        //initialize select aircraft dialog
        select_aircraft = new Dialog(this);

        // adding action to buttons
        // Aircraft Records
        aircraft_record_btn = (Button) findViewById(R.id.aircraft_record_btn);
        aircraft_record_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),aircraft_form.class);
                pass.putExtra("user_type", user_type);
                startActivity(pass);
            }
        });
        form_1_btn = (Button) findViewById(R.id.form_1_btn);
        form_1_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                selectAircraft(aircraft_form1_form.class);
            }
        });
        description_btn = (Button) findViewById(R.id.description_btn);
        description_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                selectAircraft(descriptions_form.class);
            }
        });

        reference_btn = (Button) findViewById(R.id.reference_btn);
        reference_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                selectAircraft(reference_form.class);
            }
        });
        airworthiness_btn = (Button) findViewById(R.id.airworthiness_btn);
        airworthiness_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAircraft(airworthiness_form.class);
            }
        });
        mandatory_btn = (Button) findViewById(R.id.mandatory_btn);
        mandatory_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAircraft(mandatory_service_form.class);
            }
        });

        equipment_btn = (Button) findViewById(R.id.equipment_btn);
        equipment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAircraft(equipment_form.class);
            }
        });
    }

    public void selectAircraft(Class FormClass){

        //initialize pop up dialog
        select_aircraft.setContentView(R.layout.select_aircraft_pop_up_layout);
        confirm = select_aircraft.findViewById(R.id.btn_confirm);
        cancel = select_aircraft.findViewById(R.id.btn_cancel);
        aircraft_input = select_aircraft.findViewById(R.id.input_aircraft);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.drop_down, al_aircraft_records);
        aircraft_input.setAdapter(adapter);
        aircraft_input.setThreshold(1);

        aircraft_input.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String ac_id = selectAircraft.get(position).getAircraft_id();
//                String manufacturer = selectAircraft.get(position).getAircraft_info();
//                Log.i("SELECTED", ac_id + "/ " + manufacturer);

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent pass = new Intent(getApplicationContext(), FormClass);
                        pass.putExtra("user_type", user_type);
                        pass.putExtra("aircraft_id", ac_id);
                        startActivity(pass);
                    }
                });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_aircraft.dismiss();
            }
        });

        select_aircraft.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        select_aircraft.show();
    }

    public void setUpAircraftDropdown(){

        aircraftRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    String aircraft_id = ds.getKey().toString();
//                    Log.i("AIRCRAFT ID", ""+ aircraft_id);

                    aircraftRef.child(aircraft_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            AircraftRecordHelper aircraftRecordHelper = snapshot.getValue(AircraftRecordHelper.class);
                            SelectAircraftHelper selectAircraftHelper = new SelectAircraftHelper();
                            selectAircraftHelper.setAircraft_info(aircraftRecordHelper.getManufacturer() + "/" + aircraftRecordHelper.getModel() + "/" + aircraftRecordHelper.getSerial());
                            selectAircraftHelper.setAircraft_id(aircraft_id);
                            selectAircraft.add(selectAircraftHelper);

                            al_aircraft_records = new ArrayList<>();
//                            Log.i("MANUFACTURER",   " "+snapshot.child("manufacturer").getValue());
                            for(int i = 0; i < selectAircraft.size(); i++){
//                                Log.i("INFO", ""+selectAircraft.get(i).getAircraft_info());
                                al_aircraft_records.add(selectAircraft.get(i).getAircraft_info());
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) { }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) { }
        });

    }

}