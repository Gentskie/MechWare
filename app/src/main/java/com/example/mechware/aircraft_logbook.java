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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mechware.Helper.AircraftRecordHelper;
import com.example.mechware.Helper.DropdownHelper;
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

public class aircraft_logbook extends AppCompatActivity {

    TextView textView6;
    Button aircraft_record_btn, form_1_btn, description_btn, reference_btn, airworthiness_btn, mandatory_btn, equipment_btn;
    ImageButton menu_btn;

    TextInputLayout dropdownLayout;

    String user_type;

    Dialog dropdown_dialog;
    AppCompatButton confirm, cancel;
    AutoCompleteTextView dropdown_input;

    FirebaseDatabase rootNode;
    DatabaseReference aircraftRef;
    FirebaseAuth mAuth;

    //testing variable, possible changes here
    ArrayList<String> al_records;

    List<DropdownHelper> list_of_items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aircraft_logbook);

        user_type = getIntent().getStringExtra("user_type");

        mAuth = FirebaseAuth.getInstance();

        rootNode = FirebaseDatabase.getInstance();
        aircraftRef = rootNode.getReference("aircraft_records");

        list_of_items.clear();
        setUpDropdown();

        //initialize select aircraft dialog
        dropdown_dialog = new Dialog(this);

        // changing lorem text to bold
        textView6 = (TextView) findViewById(R.id.textView6);
        String text = "Aircraft Logbook";
        Spannable string = new SpannableString(text);
        StyleSpan bold = new StyleSpan(Typeface.BOLD);
        string.setSpan(bold, 0, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView6.setText(string);

        // adding action to Image View
        // menu_btn
        menu_btn = findViewById(R.id.menu_btn);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),home_page.class);
                pass.putExtra("user_type", user_type);
                startActivity(pass);
                finish();
            }
        });

        // adding action to buttons
        // Aircraft Records
        aircraft_record_btn = (Button) findViewById(R.id.aircraft_record_btn);
        aircraft_record_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),aircraft_form.class);
                pass.putExtra("user_type", user_type);
                startActivity(pass);
                finish();
            }
        });
        form_1_btn = (Button) findViewById(R.id.form_1_btn);
        form_1_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dropdownDialogFunction(aircraft_form1_form.class);
            }
        });
        description_btn = (Button) findViewById(R.id.description_btn);
        description_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dropdownDialogFunction(descriptions_form.class);
            }
        });

        reference_btn = (Button) findViewById(R.id.reference_btn);
        reference_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dropdownDialogFunction(reference_form.class);
            }
        });
        airworthiness_btn = (Button) findViewById(R.id.airworthiness_btn);
        airworthiness_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdownDialogFunction(airworthiness_form.class);
            }
        });
        mandatory_btn = (Button) findViewById(R.id.mandatory_btn);
        mandatory_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdownDialogFunction(mandatory_service_form.class);
            }
        });

        equipment_btn = (Button) findViewById(R.id.equipment_btn);
        equipment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdownDialogFunction(equipment_form.class);
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

    public void dropdownDialogFunction(Class FormClass){
        //initialize pop up dialog
        dropdown_dialog.setContentView(R.layout.dropdown_dialog_layout);
        confirm = dropdown_dialog.findViewById(R.id.btn_confirm);
        cancel = dropdown_dialog.findViewById(R.id.btn_cancel);
        dropdown_input = dropdown_dialog.findViewById(R.id.dropdown_input);

        dropdownLayout = dropdown_dialog.findViewById(R.id.dropdownLayout);
        dropdownLayout.setHint("Select an Aircraft");

        dropdown_input.setAdapter(null);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.drop_down, al_records);
        dropdown_input.setAdapter(adapter);
        dropdown_input.setThreshold(1);

        dropdown_input.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String ac_id = list_of_items.get(position).getIds();
//                String manufacturer = selectAircraft.get(position).getAircraft_info();
//                Log.i("SELECTED", ac_id + "/ " + manufacturer);

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dropdown_dialog.dismiss();
                        Intent pass = new Intent(getApplicationContext(), FormClass);
                        pass.putExtra("user_type", user_type);
                        pass.putExtra("aircraft_id", ac_id);
                        startActivity(pass);
                        finish();
                    }
                });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdown_dialog.dismiss();
            }
        });

        dropdown_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dropdown_dialog.show();
    }

    public void setUpDropdown(){

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
                            DropdownHelper dropdownHelper = new DropdownHelper();
                            dropdownHelper.setInformations(aircraftRecordHelper.getModel() + "/" + aircraftRecordHelper.getSerial());
                            dropdownHelper.setIds(aircraft_id);
                            list_of_items.add(dropdownHelper);

                            al_records = new ArrayList<>();
//                            Log.i("MANUFACTURER",   " "+snapshot.child("manufacturer").getValue());
                            for(int i = 0; i < list_of_items.size(); i++){
//                                Log.i("INFO", ""+selectAircraft.get(i).getAircraft_info());
                                al_records.add(list_of_items.get(i).getInformations());
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