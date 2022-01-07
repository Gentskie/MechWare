package com.example.mechware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mechware.Helper.DropdownHelper;
import com.example.mechware.Helper.PropellerRecordHelper;
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

public class propeller_logbook extends AppCompatActivity {

    Button propeller_record_btn, hub_and_blade_inspection_btn;

    TextInputLayout dropdownLayout;

    String user_type;

    Dialog dropdown_dialog;
    AppCompatButton confirm, cancel;
    AutoCompleteTextView dropdown_input;

    FirebaseDatabase rootNode;
    DatabaseReference propellerRef;
    FirebaseAuth mAuth;

    //testing variable, possible changes here
    ArrayList<String> al_records;

    List<DropdownHelper> list_of_items = new ArrayList<>();

    ImageButton menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propeller_logbook);

        user_type = getIntent().getStringExtra("user_type");

        mAuth = FirebaseAuth.getInstance();

        rootNode = FirebaseDatabase.getInstance();
        propellerRef = rootNode.getReference("propeller_records");

        list_of_items.clear();

        setUpDropdown();

        //initialize select aircraft dialog
        dropdown_dialog = new Dialog(this);

        // initialization of buttons
        propeller_record_btn = (Button) findViewById(R.id.propeller_record_btn);
        hub_and_blade_inspection_btn = (Button) findViewById(R.id.hub_and_blade_inspection_btn);

        // adding action to propeller record button
        propeller_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),propeller_record_form.class);
                pass.putExtra("user_type", user_type);
                startActivity(pass);
                finish();
            }
        });

        //adding action to hun and blade inspection button
        hub_and_blade_inspection_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdownDialogFunction(hub_and_blade_inspections_form.class);
            }
        });

        menu_btn = findViewById(R.id.menu_btn);
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

    public void dropdownDialogFunction(Class FormClass){
        if(list_of_items.size() == 0){
            Toast.makeText(this, "No Propeller Records to select", Toast.LENGTH_SHORT).show();
            return;
        }
        //initialize pop up dialog
        dropdown_dialog.setContentView(R.layout.dropdown_dialog_layout);
        confirm = dropdown_dialog.findViewById(R.id.btn_confirm);
        cancel = dropdown_dialog.findViewById(R.id.btn_cancel);
        dropdown_input = dropdown_dialog.findViewById(R.id.dropdown_input);

        dropdownLayout = dropdown_dialog.findViewById(R.id.dropdownLayout);
        dropdownLayout.setHint("Select a Propeller");

        dropdown_input.setAdapter(null);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.drop_down, al_records);
        dropdown_input.setAdapter(adapter);
        dropdown_input.setThreshold(1);

        dropdown_input.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String propeller_id = list_of_items.get(position).getIds();

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dropdown_dialog.dismiss();
                        Intent pass = new Intent(getApplicationContext(), FormClass);
                        pass.putExtra("user_type", user_type);
                        pass.putExtra("propeller_id", propeller_id);
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

        propellerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    String propeller_id = ds.getKey().toString();

                    propellerRef.child(propeller_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            PropellerRecordHelper propellerRecordHelper = snapshot.getValue(PropellerRecordHelper.class);
                            DropdownHelper dropdownHelper = new DropdownHelper();
                            dropdownHelper.setInformations(propellerRecordHelper.getModel() + "/" + propellerRecordHelper.getType());
                            dropdownHelper.setIds(propeller_id);
                            list_of_items.add(dropdownHelper);

                            al_records = new ArrayList<>();
                            for(int i = 0; i < list_of_items.size(); i++){
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