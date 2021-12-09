package com.example.mechware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    // Initialization of objects
    Button owner_btn, mechanic_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        owner_btn = (Button) findViewById(R.id.Owner_btn);
        mechanic_btn = (Button) findViewById(R.id.Mechanic_btn);

        // start object Action
        owner_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),login_page.class);
                intent.putExtra("user_type", "owner");
                startActivity(intent);
            }
        });
        mechanic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),login_page.class);
                intent.putExtra("user_type", "mechanic");
                startActivity(intent);
            }
        });
    }
}