package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class View_records extends AppCompatActivity {

    ImageView search_btn;
    EditText search_editText;
    TextView modal_bg;
    CardView modal_card;
    LinearLayout aircraft_forms_layout, form_1_form_layouts, description_form_layouts, reference_form_layouts, airworthiness_form_layout, mandatory_form_layout, equipment_form_layout;
    LinearLayout engine_form_layout, registered_owner, engine_description_form_layout, propeller_record_form_layout, hub_and_blade_inspection_form_layout, pitot_static_form_layout, ndt_form_layout;
    HorizontalScrollView aircraft_record_forms_btn, engine_record_forms_btn, propeller_record_forms_btn;
    Button log_book_btn, pitot_static_btn, ndt_btn, aircraft_record_btn, form_1_btn, description_btn, reference_btn, airworthiness_btn, mandatory_btn, equipment_btn;
    Button engine_record_btn, registered_owner_record_btn, engine_description_btn, propeller_record_btn, hub_and_blade_inspection_btn;
    Button aircraft_edit_btn, aircraft_view_btn, form_1_edit_btn, form_1_view_btn, description_edit_btn, description_view_btn, reference_edit_btn, reference_view_btn, airworthiness_edit_btn, airworthiness_view_btn, mandatory_edit_btn, mandatory_view_btn, equipment_edit_btn, equipment_view_btn;
    Button engine_record_edit_btn, engine_record_view_btn, registered_owner_edit_btn, registered_owner_view_btn, engine_description_edit_btn, engine_description_view_btn, propeller_record_edit_btn, propeller_record_view_btn, hub_and_blade_inspection_edit_btn, hub_and_blade_inspection_view_btn;
    Button pitot_static_edit_btn, pitot_static_view_btn, ndt_edit_btn, ndt_view_btn;
    Button aircraft_logbook_btn, engine_logbook_btn, propeller_logbook_btn;
    boolean search = false, aircraft = false, form_1 = false, description = false, reference = false, airworthiness = false, mandatory = false, equipment = false;
    boolean engine = false, registered = false, engine_description = false, propeller = false, hub_and_blade = false, pitot_form = false, ndt_form = false;
    boolean aircraft_logbook = false, engine_logbook = false, propeller_logbook = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_records);

        // initialization of Image view
        search_btn = (ImageView) findViewById(R.id.search_btn);

        // initialization of search edit text
        search_editText = (EditText) findViewById(R.id.search_editText);
        search_editText.setVisibility(View.INVISIBLE);

        // adding action to Image View
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search == false){
                    search_editText.setVisibility(View.VISIBLE);
                    search = true;
                }
                else if(search == true){
                    search_editText.setVisibility(View.INVISIBLE);
                    search = false;
                }
            }
        });

        // adding actions to button
        aircraft_record_btn = (Button) findViewById(R.id.aircraft_record_btn);
        form_1_btn = (Button) findViewById(R.id.form_1_btn);
        description_btn = (Button) findViewById(R.id.description_btn);
        reference_btn = (Button) findViewById(R.id.reference_btn);
        airworthiness_btn = (Button) findViewById(R.id.airworthiness_btn);
        mandatory_btn = (Button) findViewById(R.id.mandatory_btn);
        equipment_btn = (Button) findViewById(R.id.equipment_btn);
        engine_record_btn = (Button) findViewById(R.id.engine_record_btn);
        registered_owner_record_btn = (Button) findViewById(R.id.registered_owner_record_btn);
        engine_description_btn = (Button) findViewById(R.id.engine_description_btn);
        propeller_record_btn = (Button) findViewById(R.id.propeller_record_btn);
        hub_and_blade_inspection_btn = (Button) findViewById(R.id.hub_and_blade_inspection_btn);
        ndt_btn = (Button) findViewById(R.id.ndt_btn);
        pitot_static_btn = (Button) findViewById(R.id.pitot_static_btn);
        log_book_btn = (Button) findViewById(R.id.log_book_btn);
        aircraft_logbook_btn = (Button) findViewById(R.id.aircraft_logbook_btn);
        engine_logbook_btn = (Button) findViewById(R.id.engine_logbook_btn);
        propeller_logbook_btn = (Button) findViewById(R.id.propeller_logbook_btn);

        aircraft_edit_btn = (Button) findViewById(R.id.aircraft_edit_btn);
        aircraft_view_btn = (Button) findViewById(R.id.aircraft_view_btn);
        form_1_edit_btn = (Button) findViewById(R.id.form_1_edit_btn);
        form_1_view_btn = (Button) findViewById(R.id.form_1_view_btn);
        description_edit_btn = (Button) findViewById(R.id.description_edit_btn);
        description_view_btn = (Button) findViewById(R.id.description_view_btn);
        reference_edit_btn = (Button) findViewById(R.id.reference_edit_btn);
        reference_view_btn = (Button) findViewById(R.id.reference_view_btn);
        airworthiness_edit_btn = (Button) findViewById(R.id.airworthiness_edit_btn);
        airworthiness_view_btn = (Button) findViewById(R.id.airworthiness_view_btn);
        mandatory_edit_btn = (Button) findViewById(R.id.mandatory_edit_btn);
        mandatory_view_btn = (Button) findViewById(R.id.mandatory_view_btn);
        equipment_edit_btn = (Button) findViewById(R.id.equipment_edit_btn);
        equipment_view_btn = (Button) findViewById(R.id.equipment_view_btn);
        engine_record_edit_btn = (Button) findViewById(R.id.engine_record_edit_btn);
        engine_record_view_btn = (Button) findViewById(R.id.engine_record_view_btn);
        registered_owner_edit_btn = (Button) findViewById(R.id.registered_owner_edit_btn);
        registered_owner_view_btn = (Button) findViewById(R.id.registered_owner_view_btn);
        engine_description_edit_btn = (Button) findViewById(R.id.engine_description_edit_btn);
        engine_description_view_btn = (Button) findViewById(R.id.engine_description_view_btn);
        propeller_record_edit_btn = (Button) findViewById(R.id.propeller_record_edit_btn);
        propeller_record_view_btn = (Button) findViewById(R.id.propeller_record_view_btn);
        hub_and_blade_inspection_edit_btn = (Button) findViewById(R.id.hub_and_blade_inspection_edit_btn);
        hub_and_blade_inspection_view_btn = (Button) findViewById(R.id.hub_and_blade_inspection_view_btn);
        pitot_static_edit_btn = (Button) findViewById(R.id.pitot_static_edit_btn);
        pitot_static_view_btn = (Button) findViewById(R.id.pitot_static_view_btn);
        ndt_edit_btn = (Button) findViewById(R.id.ndt_edit_btn);
        ndt_view_btn = (Button) findViewById(R.id.ndt_view_btn);

        // aircraft form layout
        aircraft_forms_layout = (LinearLayout) findViewById(R.id.aircraft_forms_layout);
        aircraft_forms_layout.setVisibility(View.VISIBLE);
        form_1_form_layouts = (LinearLayout) findViewById(R.id.form_1_form_layouts);
        form_1_form_layouts.setVisibility(View.INVISIBLE);
        description_form_layouts = (LinearLayout) findViewById(R.id.description_form_layouts);
        description_form_layouts.setVisibility(View.INVISIBLE);
        reference_form_layouts = (LinearLayout) findViewById(R.id.reference_form_layouts);
        reference_form_layouts.setVisibility(View.INVISIBLE);
        airworthiness_form_layout = (LinearLayout) findViewById(R.id.airworthiness_form_layout);
        airworthiness_form_layout.setVisibility(View.INVISIBLE);
        mandatory_form_layout = (LinearLayout) findViewById(R.id.mandatory_form_layout);
        mandatory_form_layout.setVisibility(View.INVISIBLE);
        equipment_form_layout = (LinearLayout) findViewById(R.id.equipment_form_layout);
        equipment_form_layout.setVisibility(View.INVISIBLE);

        // engine form layout
        engine_form_layout = (LinearLayout) findViewById(R.id.engine_form_layout);
        engine_form_layout.setVisibility(View.INVISIBLE);
        registered_owner = (LinearLayout) findViewById(R.id.registered_owner_form_layout);
        registered_owner.setVisibility(View.INVISIBLE);
        engine_description_form_layout = (LinearLayout) findViewById(R.id.engine_description_form_layout);
        engine_description_form_layout.setVisibility(View.INVISIBLE);

        // propeller form layout
        propeller_record_form_layout = (LinearLayout) findViewById(R.id.propeller_record_form_layout);
        propeller_record_form_layout.setVisibility(View.INVISIBLE);
        hub_and_blade_inspection_form_layout = (LinearLayout) findViewById(R.id.hub_and_blade_inspection_form_layout);
        hub_and_blade_inspection_form_layout.setVisibility(View.INVISIBLE);

        // adding action to aircraft record
        aircraft_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aircraft == false){
                    aircraft_forms_layout.setVisibility(View.VISIBLE);
                    form_1_form_layouts.setVisibility(View.INVISIBLE);
                    description_form_layouts.setVisibility(View.INVISIBLE);
                    reference_form_layouts.setVisibility(View.INVISIBLE);
                    airworthiness_form_layout.setVisibility(View.INVISIBLE);
                    mandatory_form_layout.setVisibility(View.INVISIBLE);
                    equipment_form_layout.setVisibility(View.INVISIBLE);
                    engine_form_layout.setVisibility(View.INVISIBLE);
                    registered_owner.setVisibility(View.INVISIBLE);
                    engine_description_form_layout.setVisibility(View.INVISIBLE);
                    propeller_record_form_layout.setVisibility(View.INVISIBLE);
                    hub_and_blade_inspection_form_layout.setVisibility(View.INVISIBLE);
                    pitot_static_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form = false;
                    pitot_form = false;
                    hub_and_blade = false;
                    propeller = false;
                    engine_description = false;
                    registered = false;
                    engine = false;
                    equipment = false;
                    mandatory = false;
                    airworthiness = false;
                    reference = false;
                    aircraft = true;
                    form_1 = false;
                    description = false;
                }
                else if (aircraft == true){
                    aircraft_forms_layout.setVisibility(View.INVISIBLE);
                    aircraft = false;
                    form_1 = false;
                }
            }
        });
        form_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(form_1 == false){
                    form_1_form_layouts.setVisibility(View.VISIBLE);
                    aircraft_forms_layout.setVisibility(View.INVISIBLE);
                    description_form_layouts.setVisibility(View.INVISIBLE);
                    reference_form_layouts.setVisibility(View.INVISIBLE);
                    airworthiness_form_layout.setVisibility(View.INVISIBLE);
                    mandatory_form_layout.setVisibility(View.INVISIBLE);
                    equipment_form_layout.setVisibility(View.INVISIBLE);
                    engine_form_layout.setVisibility(View.INVISIBLE);
                    registered_owner.setVisibility(View.INVISIBLE);
                    engine_description_form_layout.setVisibility(View.INVISIBLE);
                    propeller_record_form_layout.setVisibility(View.INVISIBLE);
                    hub_and_blade_inspection_form_layout.setVisibility(View.INVISIBLE);
                    pitot_static_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form = false;
                    pitot_form = false;
                    hub_and_blade = false;
                    propeller = false;
                    engine_description = false;
                    registered = false;
                    engine = false;
                    equipment = false;
                    mandatory = false;
                    airworthiness = false;
                    reference = false;
                    form_1 = true;
                    aircraft = false;
                    description = false;
                }
                else if (form_1 == true){
                    form_1_form_layouts.setVisibility(View.INVISIBLE);
                    form_1 = false;
                }
            }
        });
        description_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(description == false){
                    description_form_layouts.setVisibility(View.VISIBLE);
                    form_1_form_layouts.setVisibility(View.INVISIBLE);
                    aircraft_forms_layout.setVisibility(View.INVISIBLE);
                    reference_form_layouts.setVisibility(View.INVISIBLE);
                    airworthiness_form_layout.setVisibility(View.INVISIBLE);
                    mandatory_form_layout.setVisibility(View.INVISIBLE);
                    equipment_form_layout.setVisibility(View.INVISIBLE);
                    engine_form_layout.setVisibility(View.INVISIBLE);
                    registered_owner.setVisibility(View.INVISIBLE);
                    engine_description_form_layout.setVisibility(View.INVISIBLE);
                    propeller_record_form_layout.setVisibility(View.INVISIBLE);
                    hub_and_blade_inspection_form_layout.setVisibility(View.INVISIBLE);
                    pitot_static_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form = false;
                    pitot_form = false;
                    hub_and_blade = false;
                    propeller = false;
                    engine_description = false;
                    registered = false;
                    engine = false;
                    equipment = false;
                    mandatory = false;
                    airworthiness = false;
                    reference = false;
                    description = true;
                    form_1 = false;
                    aircraft = false;
                }
                else if(description == true){
                    description_form_layouts.setVisibility(View.INVISIBLE);
                    description = false;
                }
            }
        });
        reference_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reference == false){
                    reference_form_layouts.setVisibility(View.VISIBLE);
                    description_form_layouts.setVisibility(View.INVISIBLE);
                    form_1_form_layouts.setVisibility(View.INVISIBLE);
                    aircraft_forms_layout.setVisibility(View.INVISIBLE);
                    airworthiness_form_layout.setVisibility(View.INVISIBLE);
                    mandatory_form_layout.setVisibility(View.INVISIBLE);
                    equipment_form_layout.setVisibility(View.INVISIBLE);
                    engine_form_layout.setVisibility(View.INVISIBLE);
                    registered_owner.setVisibility(View.INVISIBLE);
                    engine_description_form_layout.setVisibility(View.INVISIBLE);
                    propeller_record_form_layout.setVisibility(View.INVISIBLE);
                    hub_and_blade_inspection_form_layout.setVisibility(View.INVISIBLE);
                    pitot_static_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form = false;
                    pitot_form = false;
                    hub_and_blade = false;
                    propeller = false;
                    engine_description = false;
                    registered = false;
                    engine = false;
                    equipment = false;
                    mandatory = false;
                    airworthiness = false;
                    description = false;
                    form_1 = false;
                    aircraft = false;
                    reference = true;
                }
                else if (reference == true){
                    reference_form_layouts.setVisibility(View.INVISIBLE);
                    reference = false;
                }
            }
        });
        airworthiness_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(airworthiness == false){
                    reference_form_layouts.setVisibility(View.INVISIBLE);
                    description_form_layouts.setVisibility(View.INVISIBLE);
                    form_1_form_layouts.setVisibility(View.INVISIBLE);
                    aircraft_forms_layout.setVisibility(View.INVISIBLE);
                    airworthiness_form_layout.setVisibility(View.VISIBLE);
                    mandatory_form_layout.setVisibility(View.INVISIBLE);
                    equipment_form_layout.setVisibility(View.INVISIBLE);
                    engine_form_layout.setVisibility(View.INVISIBLE);
                    registered_owner.setVisibility(View.INVISIBLE);
                    engine_description_form_layout.setVisibility(View.INVISIBLE);
                    propeller_record_form_layout.setVisibility(View.INVISIBLE);
                    hub_and_blade_inspection_form_layout.setVisibility(View.INVISIBLE);
                    pitot_static_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form = false;
                    pitot_form = false;
                    hub_and_blade = false;
                    propeller = false;
                    engine_description = false;
                    registered = false;
                    engine = false;
                    equipment = false;
                    mandatory = false;
                    airworthiness = true;
                    description = false;
                    form_1 = false;
                    aircraft = false;
                    reference = false;
                }
                else if (airworthiness == true){
                    airworthiness_form_layout.setVisibility(View.INVISIBLE);
                    airworthiness = false;
                }
            }
        });
        mandatory_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mandatory == false){
                    reference_form_layouts.setVisibility(View.INVISIBLE);
                    description_form_layouts.setVisibility(View.INVISIBLE);
                    form_1_form_layouts.setVisibility(View.INVISIBLE);
                    aircraft_forms_layout.setVisibility(View.INVISIBLE);
                    airworthiness_form_layout.setVisibility(View.INVISIBLE);
                    mandatory_form_layout.setVisibility(View.VISIBLE);
                    equipment_form_layout.setVisibility(View.INVISIBLE);
                    engine_form_layout.setVisibility(View.INVISIBLE);
                    registered_owner.setVisibility(View.INVISIBLE);
                    engine_description_form_layout.setVisibility(View.INVISIBLE);
                    propeller_record_form_layout.setVisibility(View.INVISIBLE);
                    hub_and_blade_inspection_form_layout.setVisibility(View.INVISIBLE);
                    pitot_static_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form = false;
                    pitot_form = false;
                    hub_and_blade = false;
                    propeller = false;
                    engine_description = false;
                    registered = false;
                    engine = false;
                    equipment = false;
                    mandatory = true;
                    airworthiness = false;
                    description = false;
                    form_1 = false;
                    aircraft = false;
                    reference = false;
                }
                else if (mandatory == true){
                    mandatory_form_layout.setVisibility(View.INVISIBLE);
                    mandatory = false;
                }
            }
        });
        equipment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(equipment == false){
                    reference_form_layouts.setVisibility(View.INVISIBLE);
                    description_form_layouts.setVisibility(View.INVISIBLE);
                    form_1_form_layouts.setVisibility(View.INVISIBLE);
                    aircraft_forms_layout.setVisibility(View.INVISIBLE);
                    airworthiness_form_layout.setVisibility(View.INVISIBLE);
                    mandatory_form_layout.setVisibility(View.INVISIBLE);
                    equipment_form_layout.setVisibility(View.VISIBLE);
                    engine_form_layout.setVisibility(View.INVISIBLE);
                    registered_owner.setVisibility(View.INVISIBLE);
                    engine_description_form_layout.setVisibility(View.INVISIBLE);
                    propeller_record_form_layout.setVisibility(View.INVISIBLE);
                    hub_and_blade_inspection_form_layout.setVisibility(View.INVISIBLE);
                    pitot_static_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form = false;
                    pitot_form = false;
                    hub_and_blade = false;
                    propeller = false;
                    engine_description = false;
                    registered = false;
                    engine = false;
                    equipment = true;
                    mandatory = false;
                    airworthiness = false;
                    description = false;
                    form_1 = false;
                    aircraft = false;
                    reference = false;
                }
                else if (equipment == true){
                    equipment_form_layout.setVisibility(View.INVISIBLE);
                    equipment = false;
                }
            }
        });
        engine_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(engine == false){
                    reference_form_layouts.setVisibility(View.INVISIBLE);
                    description_form_layouts.setVisibility(View.INVISIBLE);
                    form_1_form_layouts.setVisibility(View.INVISIBLE);
                    aircraft_forms_layout.setVisibility(View.INVISIBLE);
                    airworthiness_form_layout.setVisibility(View.INVISIBLE);
                    mandatory_form_layout.setVisibility(View.INVISIBLE);
                    equipment_form_layout.setVisibility(View.INVISIBLE);
                    engine_form_layout.setVisibility(View.VISIBLE);
                    registered_owner.setVisibility(View.INVISIBLE);
                    engine_description_form_layout.setVisibility(View.INVISIBLE);
                    propeller_record_form_layout.setVisibility(View.INVISIBLE);
                    hub_and_blade_inspection_form_layout.setVisibility(View.INVISIBLE);
                    pitot_static_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form = false;
                    pitot_form = false;
                    hub_and_blade = false;
                    propeller = false;
                    engine_description = false;
                    registered = false;
                    engine = true;
                    equipment = false;
                    mandatory = false;
                    airworthiness = false;
                    description = false;
                    form_1 = false;
                    aircraft = false;
                    reference = false;
                }
                else if (engine == true){
                    engine_form_layout.setVisibility(View.INVISIBLE);
                    engine = false;
                }
            }
        });
        registered_owner_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(registered == false){
                    reference_form_layouts.setVisibility(View.INVISIBLE);
                    description_form_layouts.setVisibility(View.INVISIBLE);
                    form_1_form_layouts.setVisibility(View.INVISIBLE);
                    aircraft_forms_layout.setVisibility(View.INVISIBLE);
                    airworthiness_form_layout.setVisibility(View.INVISIBLE);
                    mandatory_form_layout.setVisibility(View.INVISIBLE);
                    equipment_form_layout.setVisibility(View.INVISIBLE);
                    engine_form_layout.setVisibility(View.INVISIBLE);
                    registered_owner.setVisibility(View.VISIBLE);
                    engine_description_form_layout.setVisibility(View.INVISIBLE);
                    propeller_record_form_layout.setVisibility(View.INVISIBLE);
                    hub_and_blade_inspection_form_layout.setVisibility(View.INVISIBLE);
                    pitot_static_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form = false;
                    pitot_form = false;
                    hub_and_blade = false;
                    propeller = false;
                    engine_description = false;
                    registered = true;
                    engine = false;
                    equipment = false;
                    mandatory = false;
                    airworthiness = false;
                    description = false;
                    form_1 = false;
                    aircraft = false;
                    reference = false;
                }
                else if (registered == true){
                    registered_owner.setVisibility(View.INVISIBLE);
                    registered = false;
                }
            }
        });

        engine_description_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(engine_description == false){
                    reference_form_layouts.setVisibility(View.INVISIBLE);
                    description_form_layouts.setVisibility(View.INVISIBLE);
                    form_1_form_layouts.setVisibility(View.INVISIBLE);
                    aircraft_forms_layout.setVisibility(View.INVISIBLE);
                    airworthiness_form_layout.setVisibility(View.INVISIBLE);
                    mandatory_form_layout.setVisibility(View.INVISIBLE);
                    equipment_form_layout.setVisibility(View.INVISIBLE);
                    engine_form_layout.setVisibility(View.INVISIBLE);
                    registered_owner.setVisibility(View.INVISIBLE);
                    engine_description_form_layout.setVisibility(View.VISIBLE);
                    propeller_record_form_layout.setVisibility(View.INVISIBLE);
                    hub_and_blade_inspection_form_layout.setVisibility(View.INVISIBLE);
                    pitot_static_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form = false;
                    pitot_form = false;
                    hub_and_blade = false;
                    propeller = false;
                    engine_description = true;
                    registered = false;
                    engine = false;
                    equipment = false;
                    mandatory = false;
                    airworthiness = false;
                    description = false;
                    form_1 = false;
                    aircraft = false;
                    reference = false;
                }
                else if (engine_description == true){
                    engine_description_form_layout.setVisibility(View.INVISIBLE);
                    engine_description = false;
                }
            }
        });
        propeller_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(propeller == false){
                    reference_form_layouts.setVisibility(View.INVISIBLE);
                    description_form_layouts.setVisibility(View.INVISIBLE);
                    form_1_form_layouts.setVisibility(View.INVISIBLE);
                    aircraft_forms_layout.setVisibility(View.INVISIBLE);
                    airworthiness_form_layout.setVisibility(View.INVISIBLE);
                    mandatory_form_layout.setVisibility(View.INVISIBLE);
                    equipment_form_layout.setVisibility(View.INVISIBLE);
                    engine_form_layout.setVisibility(View.INVISIBLE);
                    registered_owner.setVisibility(View.INVISIBLE);
                    engine_description_form_layout.setVisibility(View.INVISIBLE);
                    propeller_record_form_layout.setVisibility(View.VISIBLE);
                    hub_and_blade_inspection_form_layout.setVisibility(View.INVISIBLE);
                    pitot_static_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form = false;
                    pitot_form = false;
                    hub_and_blade = false;
                    propeller = true;
                    engine_description = false;
                    registered = false;
                    engine = false;
                    equipment = false;
                    mandatory = false;
                    airworthiness = false;
                    description = false;
                    form_1 = false;
                    aircraft = false;
                    reference = false;
                }
                else if (propeller == true){
                    propeller_record_form_layout.setVisibility(View.INVISIBLE);
                    propeller = false;
                }
            }
        });

        hub_and_blade_inspection_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hub_and_blade == false){
                    reference_form_layouts.setVisibility(View.INVISIBLE);
                    description_form_layouts.setVisibility(View.INVISIBLE);
                    form_1_form_layouts.setVisibility(View.INVISIBLE);
                    aircraft_forms_layout.setVisibility(View.INVISIBLE);
                    airworthiness_form_layout.setVisibility(View.INVISIBLE);
                    mandatory_form_layout.setVisibility(View.INVISIBLE);
                    equipment_form_layout.setVisibility(View.INVISIBLE);
                    engine_form_layout.setVisibility(View.INVISIBLE);
                    registered_owner.setVisibility(View.INVISIBLE);
                    engine_description_form_layout.setVisibility(View.INVISIBLE);
                    propeller_record_form_layout.setVisibility(View.INVISIBLE);
                    hub_and_blade_inspection_form_layout.setVisibility(View.VISIBLE);
                    pitot_static_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form = false;
                    pitot_form = false;
                    hub_and_blade = true;
                    propeller = false;
                    engine_description = false;
                    registered = false;
                    engine = false;
                    equipment = false;
                    mandatory = false;
                    airworthiness = false;
                    description = false;
                    form_1 = false;
                    aircraft = false;
                    reference = false;
                }
                else if (hub_and_blade == true){
                    hub_and_blade_inspection_form_layout.setVisibility(View.INVISIBLE);
                    hub_and_blade = false;
                }
            }
        });

        // for modal
        modal_bg = (TextView) findViewById(R.id.modal_bg);
        modal_bg.setVisibility(View.INVISIBLE);
        modal_card = (CardView) findViewById(R.id.modal_card);
        modal_card.setVisibility(View.INVISIBLE);

        aircraft_record_forms_btn = (HorizontalScrollView) findViewById(R.id.aircraft_record_forms_btn);
        aircraft_record_forms_btn.setVisibility(View.VISIBLE);
        engine_record_forms_btn = (HorizontalScrollView) findViewById(R.id.engine_record_forms_btn);
        engine_record_forms_btn.setVisibility(View.INVISIBLE);
        propeller_record_forms_btn = (HorizontalScrollView) findViewById(R.id.propeller_record_forms_btn);
        propeller_record_forms_btn.setVisibility(View.INVISIBLE);

        aircraft_logbook_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aircraft_logbook == false){

                    modal_bg.setVisibility(View.INVISIBLE);
                    modal_card.setVisibility(View.INVISIBLE);

                    aircraft_record_forms_btn.setVisibility(View.VISIBLE);
                    aircraft_logbook = true;
                    engine_record_forms_btn.setVisibility(View.INVISIBLE);
                    engine_logbook = false;
                    propeller_record_forms_btn.setVisibility(View.INVISIBLE);
                    propeller_logbook = false;

                    form_1_form_layouts.setVisibility(View.INVISIBLE);
                    aircraft_forms_layout.setVisibility(View.VISIBLE);
                    description_form_layouts.setVisibility(View.INVISIBLE);
                    reference_form_layouts.setVisibility(View.INVISIBLE);
                    airworthiness_form_layout.setVisibility(View.INVISIBLE);
                    mandatory_form_layout.setVisibility(View.INVISIBLE);
                    equipment_form_layout.setVisibility(View.INVISIBLE);
                    engine_form_layout.setVisibility(View.INVISIBLE);
                    registered_owner.setVisibility(View.INVISIBLE);
                    engine_description_form_layout.setVisibility(View.INVISIBLE);
                    propeller_record_form_layout.setVisibility(View.INVISIBLE);
                    hub_and_blade_inspection_form_layout.setVisibility(View.INVISIBLE);
                    pitot_static_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form = false;
                    pitot_form = false;
                    hub_and_blade = false;
                    propeller = false;
                    engine_description = false;
                    registered = false;
                    engine = false;
                    equipment = false;
                    mandatory = false;
                    airworthiness = false;
                    reference = false;
                    form_1 = false;
                    aircraft = true;
                    description = false;
                }
                else if (aircraft_logbook == true){
                    aircraft_forms_layout.setVisibility(View.VISIBLE);
                    aircraft_logbook = false;
                    modal_bg.setVisibility(View.INVISIBLE);
                    modal_card.setVisibility(View.INVISIBLE);
                }
            }
        });

        engine_logbook_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(engine_logbook == false){
                    modal_bg.setVisibility(View.INVISIBLE);
                    modal_card.setVisibility(View.INVISIBLE);

                    aircraft_record_forms_btn.setVisibility(View.INVISIBLE);
                    aircraft_logbook = false;
                    engine_record_forms_btn.setVisibility(View.VISIBLE);
                    engine_logbook = true;
                    propeller_record_forms_btn.setVisibility(View.INVISIBLE);
                    propeller_logbook = false;

                    form_1_form_layouts.setVisibility(View.INVISIBLE);
                    aircraft_forms_layout.setVisibility(View.INVISIBLE);
                    description_form_layouts.setVisibility(View.INVISIBLE);
                    reference_form_layouts.setVisibility(View.INVISIBLE);
                    airworthiness_form_layout.setVisibility(View.INVISIBLE);
                    mandatory_form_layout.setVisibility(View.INVISIBLE);
                    equipment_form_layout.setVisibility(View.INVISIBLE);
                    engine_form_layout.setVisibility(View.VISIBLE);
                    registered_owner.setVisibility(View.INVISIBLE);
                    engine_description_form_layout.setVisibility(View.INVISIBLE);
                    propeller_record_form_layout.setVisibility(View.INVISIBLE);
                    hub_and_blade_inspection_form_layout.setVisibility(View.INVISIBLE);
                    pitot_static_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form = false;
                    pitot_form = false;
                    hub_and_blade = false;
                    propeller = false;
                    engine_description = false;
                    registered = false;
                    engine = true;
                    equipment = false;
                    mandatory = false;
                    airworthiness = false;
                    reference = false;
                    form_1 = false;
                    aircraft = false;
                    description = false;
                }
                    else if (engine_logbook == true){
                    engine_form_layout.setVisibility(View.VISIBLE);
                    engine_logbook = false;
                    modal_bg.setVisibility(View.INVISIBLE);
                    modal_card.setVisibility(View.INVISIBLE);
                }
            }
        });

        propeller_logbook_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(propeller_logbook == false){
                    modal_bg.setVisibility(View.INVISIBLE);
                    modal_card.setVisibility(View.INVISIBLE);

                    aircraft_record_forms_btn.setVisibility(View.INVISIBLE);
                    aircraft_logbook = false;
                    engine_record_forms_btn.setVisibility(View.INVISIBLE);
                    engine_logbook = false;
                    propeller_record_forms_btn.setVisibility(View.VISIBLE);
                    propeller_logbook = true;

                    form_1_form_layouts.setVisibility(View.INVISIBLE);
                    aircraft_forms_layout.setVisibility(View.INVISIBLE);
                    description_form_layouts.setVisibility(View.INVISIBLE);
                    reference_form_layouts.setVisibility(View.INVISIBLE);
                    airworthiness_form_layout.setVisibility(View.INVISIBLE);
                    mandatory_form_layout.setVisibility(View.INVISIBLE);
                    equipment_form_layout.setVisibility(View.INVISIBLE);
                    engine_form_layout.setVisibility(View.INVISIBLE);
                    registered_owner.setVisibility(View.INVISIBLE);
                    engine_description_form_layout.setVisibility(View.INVISIBLE);
                    propeller_record_form_layout.setVisibility(View.VISIBLE);
                    hub_and_blade_inspection_form_layout.setVisibility(View.INVISIBLE);
                    pitot_static_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form = false;
                    pitot_form = false;
                    hub_and_blade = false;
                    propeller = true;
                    engine_description = false;
                    registered = false;
                    engine = false;
                    equipment = false;
                    mandatory = false;
                    airworthiness = false;
                    reference = false;
                    form_1 = false;
                    aircraft = false;
                    description = false;
                }
                else if (propeller_logbook == true){
                    propeller_record_form_layout.setVisibility(View.VISIBLE);
                    propeller_logbook = false;
                    modal_bg.setVisibility(View.INVISIBLE);
                    modal_card.setVisibility(View.INVISIBLE);
                }
            }
        });

        log_book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modal_bg.setVisibility(View.VISIBLE);
                modal_card.setVisibility(View.VISIBLE);
            }
        });
        modal_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modal_bg.setVisibility(View.INVISIBLE);
                modal_card.setVisibility(View.INVISIBLE);
            }
        });

        // pitot static layout
        pitot_static_form_layout = (LinearLayout) findViewById(R.id.pitot_static_form_layout);
        pitot_static_form_layout.setVisibility(View.INVISIBLE);
        ndt_form_layout = (LinearLayout) findViewById(R.id.ndt_form_layout) ;
        ndt_form_layout.setVisibility(View.INVISIBLE);

        // pitot static layout form layout
        pitot_static_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pitot_form == false){
                    aircraft_record_forms_btn.setVisibility(View.INVISIBLE);
                    aircraft_logbook = false;
                    engine_record_forms_btn.setVisibility(View.INVISIBLE);
                    engine_logbook = false;
                    propeller_record_forms_btn.setVisibility(View.INVISIBLE);
                    propeller_logbook = false;
                    reference_form_layouts.setVisibility(View.INVISIBLE);
                    description_form_layouts.setVisibility(View.INVISIBLE);
                    form_1_form_layouts.setVisibility(View.INVISIBLE);
                    aircraft_forms_layout.setVisibility(View.INVISIBLE);
                    airworthiness_form_layout.setVisibility(View.INVISIBLE);
                    mandatory_form_layout.setVisibility(View.INVISIBLE);
                    equipment_form_layout.setVisibility(View.INVISIBLE);
                    engine_form_layout.setVisibility(View.INVISIBLE);
                    registered_owner.setVisibility(View.INVISIBLE);
                    engine_description_form_layout.setVisibility(View.INVISIBLE);
                    propeller_record_form_layout.setVisibility(View.INVISIBLE);
                    hub_and_blade_inspection_form_layout.setVisibility(View.INVISIBLE);
                    pitot_static_form_layout.setVisibility(View.VISIBLE);
                    ndt_form_layout.setVisibility(View.INVISIBLE);
                    aircraft_record_forms_btn.setVisibility(View.INVISIBLE);
                    ndt_form = false;
                    pitot_form = true;
                    hub_and_blade = false;
                    propeller = false;
                    engine_description = false;
                    registered = false;
                    engine = false;
                    equipment = false;
                    mandatory = false;
                    airworthiness = false;
                    description = false;
                    form_1 = false;
                    aircraft = false;
                    reference = false;
                }
                else if (pitot_form == true){
                    pitot_static_form_layout.setVisibility(View.INVISIBLE);
                    pitot_form = false;
                    aircraft_record_forms_btn.setVisibility(View.INVISIBLE);
                    aircraft_logbook = false;
                    engine_record_forms_btn.setVisibility(View.INVISIBLE);
                    engine_logbook = false;
                    propeller_record_forms_btn.setVisibility(View.INVISIBLE);
                    propeller_logbook = false;
                }
            }
        });

        ndt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ndt_form == false){
                    aircraft_record_forms_btn.setVisibility(View.INVISIBLE);
                    aircraft_logbook = false;
                    engine_record_forms_btn.setVisibility(View.INVISIBLE);
                    engine_logbook = false;
                    propeller_record_forms_btn.setVisibility(View.INVISIBLE);
                    propeller_logbook = false;

                    reference_form_layouts.setVisibility(View.INVISIBLE);
                    description_form_layouts.setVisibility(View.INVISIBLE);
                    form_1_form_layouts.setVisibility(View.INVISIBLE);
                    aircraft_forms_layout.setVisibility(View.INVISIBLE);
                    airworthiness_form_layout.setVisibility(View.INVISIBLE);
                    mandatory_form_layout.setVisibility(View.INVISIBLE);
                    equipment_form_layout.setVisibility(View.INVISIBLE);
                    engine_form_layout.setVisibility(View.INVISIBLE);
                    registered_owner.setVisibility(View.INVISIBLE);
                    engine_description_form_layout.setVisibility(View.INVISIBLE);
                    propeller_record_form_layout.setVisibility(View.INVISIBLE);
                    hub_and_blade_inspection_form_layout.setVisibility(View.INVISIBLE);
                    pitot_static_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form_layout.setVisibility(View.VISIBLE);
                    aircraft_record_forms_btn.setVisibility(View.INVISIBLE);
                    ndt_form = true;
                    pitot_form = false;
                    hub_and_blade = false;
                    propeller = false;
                    engine_description = false;
                    registered = false;
                    engine = false;
                    equipment = false;
                    mandatory = false;
                    airworthiness = false;
                    description = false;
                    form_1 = false;
                    aircraft = false;
                    reference = false;
                }
                else if (ndt_form == true){
                    ndt_form_layout.setVisibility(View.INVISIBLE);
                    ndt_form = false;
                    aircraft_record_forms_btn.setVisibility(View.INVISIBLE);
                    aircraft_logbook = false;
                    engine_record_forms_btn.setVisibility(View.INVISIBLE);
                    engine_logbook = false;
                    propeller_record_forms_btn.setVisibility(View.INVISIBLE);
                    propeller_logbook = false;
                }
            }
        });

        // adding action to aircraft_edit_btn and aircraft_view_btn
        aircraft_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),aircratf_record_view.class);
                pass.putExtra("action", "edit");
                startActivity(pass);
            }
        });
        aircraft_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),aircratf_record_view.class);
                pass.putExtra("action", "view");
                startActivity(pass);
            }
        });

        // adding action to form_1_edit_btn and form_1_view_btn
        form_1_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),form_1_record_view.class);
                pass.putExtra("action", "edit");
                startActivity(pass);
            }
        });
        form_1_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),form_1_record_view.class);
                pass.putExtra("action", "view");
                startActivity(pass);
            }
        });
        // adding action to description_edit_btn and description_view_btn
        description_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),description_record_view.class);
                pass.putExtra("action", "edit");
                startActivity(pass);
            }
        });
        description_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),description_record_view.class);
                pass.putExtra("action", "view");
                startActivity(pass);
            }
        });
        // adding action to description_edit_btn and description_view_btn
        reference_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),reference_record_view.class);
                pass.putExtra("action", "edit");
                startActivity(pass);
            }
        });
        reference_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),reference_record_view.class);
                pass.putExtra("action", "view");
                startActivity(pass);
            }
        });
        // adding action to airworthiness_edit_btn and airworthiness_view_btn
        airworthiness_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),airworthiness_record_view.class);
                pass.putExtra("action", "edit");
                startActivity(pass);
            }
        });
        airworthiness_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),airworthiness_record_view.class);
                pass.putExtra("action", "view");
                startActivity(pass);
            }
        });
        // adding action to mandatory_edit_btn and mandatory_view_btn
        mandatory_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),mandatory_record_view.class);
                pass.putExtra("action", "edit");
                startActivity(pass);
            }
        });
        mandatory_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),mandatory_record_view.class);
                pass.putExtra("action", "view");
                startActivity(pass);
            }
        });
        // adding action to equipment_edit_btn and equipment_view_btn
        equipment_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),equipment_record_view.class);
                pass.putExtra("action", "edit");
                startActivity(pass);
            }
        });
        equipment_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),equipment_record_view.class);
                pass.putExtra("action", "view");
                startActivity(pass);
            }
        });
        // adding action to engine_record_edit_btn and engine_record_view_btn
        engine_record_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),engine_record_record_view.class);
                pass.putExtra("action", "edit");
                startActivity(pass);
            }
        });
        engine_record_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),engine_record_record_view.class);
                pass.putExtra("action", "view");
                startActivity(pass);
            }
        });
        // adding action to registered_owner_edit_btn and registered_owner_view_btn
        registered_owner_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),registered_owner_record_view.class);
                pass.putExtra("action", "edit");
                startActivity(pass);
            }
        });
        registered_owner_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),registered_owner_record_view.class);
                pass.putExtra("action", "view");
                startActivity(pass);
            }
        });
        // adding action to propeller_record_edit_btn and propeller_record_view_btn
        propeller_record_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),propeller_record_view.class);
                pass.putExtra("action", "edit");
                startActivity(pass);
            }
        });
        propeller_record_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),propeller_record_view.class);
                pass.putExtra("action", "view");
                startActivity(pass);
            }
        });
        // adding action to hub_and_blade_inspection_edit_btn and hub_and_blade_inspection_view_btn
        hub_and_blade_inspection_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),hub_and_blade_record_view.class);
                pass.putExtra("action", "edit");
                startActivity(pass);
            }
        });
        hub_and_blade_inspection_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),hub_and_blade_record_view.class);
                pass.putExtra("action", "view");
                startActivity(pass);
            }
        });
        // adding action to ndt_edit_btn and ndt_view_btn
        ndt_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),ndt_record_view.class);
                pass.putExtra("action", "edit");
                startActivity(pass);
            }
        });
        ndt_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),ndt_record_view.class);
                pass.putExtra("action", "view");
                startActivity(pass);
            }
        });
        // adding action to pitot_static_edit_btn and pitot_static_view_btn
        pitot_static_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),pitot_record_view.class);
                pass.putExtra("action", "edit");
                startActivity(pass);
            }
        });
        pitot_static_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),pitot_record_view.class);
                pass.putExtra("action", "view");
                startActivity(pass);
            }
        });

    }
}