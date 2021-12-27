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

import com.example.mechware.Helper.DropdownHelper;
import com.example.mechware.Helper.EngineRecordHelper;
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

public class engine_logbook extends AppCompatActivity {

    Button engine_record_btn, registered_owner_record_btn, description_btn;

    TextInputLayout dropdownLayout;

    String user_type;

    Dialog dropdown_dialog;
    AppCompatButton confirm, cancel;
    AutoCompleteTextView dropdown_input;

    FirebaseDatabase rootNode;
    DatabaseReference engineRef;
    FirebaseAuth mAuth;

    //testing variable, possible changes here
    ArrayList<String> al_records;

    List<DropdownHelper> list_of_items = new ArrayList<>();

    ImageButton menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engine_logbook);

        user_type = getIntent().getStringExtra("user_type");

        mAuth = FirebaseAuth.getInstance();

        rootNode = FirebaseDatabase.getInstance();
        engineRef = rootNode.getReference("engine_records");

        list_of_items.clear();

        setUpDropdown();

        //initialize select aircraft dialog
        dropdown_dialog = new Dialog(this);

        // initialization of buttons
        engine_record_btn = (Button) findViewById(R.id.engine_record_btn);
        registered_owner_record_btn = (Button) findViewById(R.id.registered_owner_record_btn);
        description_btn = (Button) findViewById(R.id.description_btn);

        // setting action to buttons
        engine_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),engine_record_form.class);
                pass.putExtra("user_type", user_type);
                startActivity(pass);
                finish();
            }
        });
        registered_owner_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdownDialogFunction(registered_owner_record_form.class);
            }
        });
        description_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdownDialogFunction(description_engine_form.class);
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

        //initialize pop up dialog
        dropdown_dialog.setContentView(R.layout.dropdown_dialog_layout);
        confirm = dropdown_dialog.findViewById(R.id.btn_confirm);
        cancel = dropdown_dialog.findViewById(R.id.btn_cancel);
        dropdown_input = dropdown_dialog.findViewById(R.id.dropdown_input);

        dropdownLayout = dropdown_dialog.findViewById(R.id.dropdownLayout);
        dropdownLayout.setHint("Select an Engine");

        dropdown_input.setAdapter(null);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.drop_down, al_records);
        dropdown_input.setAdapter(adapter);
        dropdown_input.setThreshold(1);

        dropdown_input.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String engine_id = list_of_items.get(position).getIds();


                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dropdown_dialog.dismiss();
                        Intent pass = new Intent(getApplicationContext(), FormClass);
                        pass.putExtra("user_type", user_type);
                        pass.putExtra("engine_id", engine_id);
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

        engineRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    String aircraft_id = ds.getKey().toString();

                    engineRef.child(aircraft_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            EngineRecordHelper engineRecordHelper = snapshot.getValue(EngineRecordHelper.class);
                            DropdownHelper dropdownHelper = new DropdownHelper();
                            dropdownHelper.setInformations(engineRecordHelper.getModel() + "/" + engineRecordHelper.getSerial());
                            dropdownHelper.setIds(aircraft_id);
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