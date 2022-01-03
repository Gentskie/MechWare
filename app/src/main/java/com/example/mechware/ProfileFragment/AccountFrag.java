package com.example.mechware.ProfileFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mechware.Helper.UsersHelper;
import com.example.mechware.Profile;
import com.example.mechware.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class AccountFrag extends Fragment {

    public String user_type;

    TextInputLayout fullname_layout, email_layout, cnum_layout;
    AppCompatButton restore_btn, save_btn;

    FirebaseDatabase rootNode;
    DatabaseReference usersRef, userTypeRef, userIDRef;
    FirebaseAuth mAuth;
    String uuid;

    public AccountFrag() {
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
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        Profile profile = (Profile) getActivity();

        Bundle result = profile.getUserType();
        user_type = result.getString("user_type");

        //Edit Text init
        fullname_layout = rootView.findViewById(R.id.fullname_layout);
        email_layout = rootView.findViewById(R.id.email_layout);
        cnum_layout = rootView.findViewById(R.id.contact_layout);

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

        //get current users Data
        userIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                UsersHelper helper = snapshot.getValue(UsersHelper.class);

                fullname_layout.getEditText().setText(helper.fullname);
                email_layout.getEditText().setText(helper.email);
                cnum_layout.getEditText().setText(helper.contact_number);

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

                        fullname_layout.getEditText().setText(helper.fullname);
                        email_layout.getEditText().setText(helper.email);
                        cnum_layout.getEditText().setText(helper.contact_number);

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
                        UsersHelper helper = snapshot.getValue(UsersHelper.class);
                        HashMap<String, Object> userMap = new HashMap<>();

                        userMap.put("fullname", fullname_layout.getEditText().getText().toString().trim());
                        userMap.put("email", email_layout.getEditText().getText().toString().trim());
                        userMap.put("contact_number", cnum_layout.getEditText().getText().toString().trim());

                        if(helper.email.equals(email_layout.getEditText().getText().toString())){
                            userIDRef.updateChildren(userMap);
                            Toast.makeText(profile, "Account information has been updated!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            //Reauthenticate User
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            AuthCredential credential = EmailAuthProvider.getCredential(helper.email, helper.password);
                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    //Update Email
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    user.updateEmail(email_layout.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                userIDRef.updateChildren(userMap);
                                                Toast.makeText(profile, "Account information has been updated!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
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