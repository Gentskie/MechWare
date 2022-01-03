package com.example.mechware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mechware.ViewRecords.view_records;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;

public class home_page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView back_layout, toolbar_label;
    ImageView logbook_btn, ndt_btn, pitot_btn, view_records_btn;
    CardView card_view;
    Button aircraft_btn, propeller_btn, engine_btn;

    String user_type;

    //Navigation menu variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    boolean navigationStateOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        user_type = getIntent().getStringExtra("user_type");

        card_view = (CardView) findViewById(R.id.card_view);
        back_layout = (TextView) findViewById(R.id.back_layout);

        toolbar_label = findViewById(R.id.toolbar_label);

        toolbar_label.setText("Home Page");

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


        // setting cardview and back_layout invisible
        card_view.setVisibility(View.INVISIBLE);
        back_layout.setVisibility(View.INVISIBLE);

        // showing card_view and back_layout
        logbook_btn = (ImageView) findViewById(R.id.logbook_btn);
        logbook_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_view.setVisibility(View.VISIBLE);
                back_layout.setVisibility(View.VISIBLE);
            }
        });

        ndt_btn = findViewById(R.id.ndt_btn);
        ndt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ndt_form.class);
                intent.putExtra("user_type", user_type);
                startActivity(intent);
            }
        });

        pitot_btn = findViewById(R.id.pitot_btn);
        pitot_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), pitot_form.class);
                intent.putExtra("user_type", user_type);
                startActivity(intent);
            }
        });

        // hiding card_view and back_layout
        back_layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                card_view.setVisibility(View.INVISIBLE);
                back_layout.setVisibility(View.INVISIBLE);
            }
        });

        // setting action to logbook button
        aircraft_btn = (Button) findViewById(R.id.aircraft_btn);
        aircraft_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),aircraft_logbook.class);
                pass.putExtra("user_type", user_type);
                startActivity(pass);
            }
        });

        // setting action to propeller button
        propeller_btn = (Button) findViewById(R.id.propeller_btn);
        propeller_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),propeller_logbook.class);
                pass.putExtra("user_type", user_type);
                startActivity(pass);
            }
        });

        // setting action to engine button
        engine_btn = (Button) findViewById(R.id.engine_btn);
        engine_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(),engine_logbook.class);
                pass.putExtra("user_type", user_type);
                startActivity(pass);
            }
        });

        view_records_btn = findViewById(R.id.view_records_btn);
        view_records_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getApplicationContext(), view_records.class);
                pass.putExtra("user_type", user_type);
                startActivity(pass);
            }
        });

    }

    //for Back Button ng Cellphone
    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_profile:
                //change this to profile view
                Intent profile = new Intent(getApplicationContext(), Profile.class);
                profile.putExtra("user_type", user_type);
                startActivity(profile);
                finish();
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent chooseUser = new Intent(getApplicationContext(), ChooseUser.class);
                startActivity(chooseUser);
                finish();
                break;
        }
        return true;
    }
}