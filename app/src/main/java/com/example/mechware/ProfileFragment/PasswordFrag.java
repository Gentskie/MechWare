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

public class PasswordFrag extends Fragment {

    public String user_type;

    TextInputLayout password_layout, c_password_layout;
    AppCompatButton restore_btn, save_btn;

    FirebaseDatabase rootNode;
    DatabaseReference usersRef, userTypeRef, userIDRef;
    FirebaseAuth mAuth;
    String uuid;

    public PasswordFrag() {
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
        View rootView = inflater.inflate(R.layout.fragment_password, container, false);

        Profile profile = (Profile) getActivity();

        Bundle result = profile.getUserType();
        user_type = result.getString("user_type");

        //Edit Text init
        password_layout = rootView.findViewById(R.id.password_layout);
        c_password_layout = rootView.findViewById(R.id.password_2_layout);

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
                if (helper == null){
                    return;
                }
                password_layout.getEditText().setText(helper.password);
                c_password_layout.getEditText().setText(helper.password);

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
                        password_layout.getEditText().setText(helper.password);
                        c_password_layout.getEditText().setText(helper.password);

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

                        if(password_layout.getEditText().getText().length() < 8){
                            Toast.makeText(profile, "Password must be 8+ characters", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(password_layout.getEditText().getText().toString().equals(c_password_layout.getEditText().getText().toString())){
                            HashMap<String, Object> userMap = new HashMap<>();
                            userMap.put("password", password_layout.getEditText().getText().toString().trim());

                            //Reauthenticate User
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            AuthCredential credential = EmailAuthProvider.getCredential(helper.email, helper.password);
                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    //update password
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    user.updatePassword(password_layout.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                userIDRef.updateChildren(userMap);
                                                Toast.makeText(profile, "Password has been changed!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }

                        else{
                            Toast.makeText(profile, "Password didn't match!", Toast.LENGTH_SHORT).show();
                            return;
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