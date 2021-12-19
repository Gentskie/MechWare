package com.example.mechware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        //check if user is already login.
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            //put String Extra to the HomePageActivity
            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference users = FirebaseDatabase.getInstance().getReference("users");
            users.orderByChild(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                        Intent intent = new Intent(getApplicationContext(), home_page.class);
                        String parent = childSnapshot.getKey();
                        intent.putExtra("user_type", parent);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Log.e("ERR", "OR/ " + error);
                }
            });
        }

        //If there's no user logged in, redirect to OnBoarding Activity.
        else {
            startActivity(new Intent(SplashScreen.this, ChooseUser.class));
            finish();
        }

    }
}