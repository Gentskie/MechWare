package com.example.mechware.ProfileFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mechware.Helper.UsersHelper;
import com.example.mechware.Profile;
import com.example.mechware.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class OtherFrag extends Fragment {

    public String user_type;

    TextView license_label, address_label;
    ConstraintLayout parent;
    TextInputLayout license_layout, address_layout;
    AppCompatButton restore_btn, save_btn;

    FirebaseDatabase rootNode;
    DatabaseReference usersRef, userTypeRef, userIDRef;
    FirebaseAuth mAuth;
    String uuid;

    public OtherFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_other, container, false);

        Profile profile = (Profile) getActivity();

        Bundle result = profile.getUserType();
        user_type = result.getString("user_type");

        //Edit Text init
        license_layout = rootView.findViewById(R.id.license_layout);
        address_layout = rootView.findViewById(R.id.address_layout);
        license_label = rootView.findViewById(R.id.license_label);
        address_label = rootView.findViewById(R.id.address_label);

        //Button init
        restore_btn = rootView.findViewById(R.id.restore_btn);
        save_btn = rootView.findViewById(R.id.save_btn);

        //Database init
        mAuth = FirebaseAuth.getInstance();
        uuid = mAuth.getCurrentUser().getUid();
        rootNode = FirebaseDatabase.getInstance();
        usersRef = rootNode.getReference("users");
        userTypeRef = usersRef.child(user_type);
        userIDRef = userTypeRef.child(uuid);

        //if user is mechanic show license number edit text
        if(!(user_type.equals("mechanic"))){
            license_layout.setVisibility(View.GONE);
            license_label.setVisibility(View.GONE);
        }

        //get current users Data
        userIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                UsersHelper helper = snapshot.getValue(UsersHelper.class);
                if (helper == null){
                    return;
                }
                license_layout.getEditText().setText(helper.license);
                address_layout.getEditText().setText(helper.address);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        //Restore Data/Reset Edit text to default
        restore_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        UsersHelper helper = snapshot.getValue(UsersHelper.class);
                        if (helper == null){
                            return;
                        }
                        license_layout.getEditText().setText(helper.license);
                        address_layout.getEditText().setText(helper.address);

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        //Save Data
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        HashMap<String, Object> userMap = new HashMap<>();

                        userMap.put("license", license_layout.getEditText().getText().toString().trim());
                        userMap.put("address", address_layout.getEditText().getText().toString().trim());

                        userIDRef.updateChildren(userMap);

                        Toast.makeText(profile, "Other information has been updated!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        return rootView;
    }
}