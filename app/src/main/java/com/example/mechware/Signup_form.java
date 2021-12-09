package com.example.mechware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mechware.Helper.UsersHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

public class Signup_form extends AppCompatActivity {

    ConstraintLayout first_form_layout, second_form_layout;

    AppCompatButton back, next;

    EditText fullname, email, contact_number, password, c_password;

    AppCompatCheckBox show_hide_pass;

    RadioButton mechanic, owner;

    RadioGroup user_type;

    FirebaseDatabase rootNode;
    DatabaseReference usersRef, mechanicRef, ownerRef;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_form_layout);

        first_form_layout = findViewById(R.id.first_form_layout);
        second_form_layout = findViewById(R.id.second_form_layout);

        back = findViewById(R.id.back_btn);
        next = findViewById(R.id.next_btn);

        fullname = findViewById(R.id.full_name_editText);
        email = findViewById(R.id.email_editText);
        contact_number = findViewById(R.id.contact_number_editText);
        password = findViewById(R.id.password_editText);
        c_password = findViewById(R.id.c_password_editText);

        show_hide_pass = findViewById(R.id.show_hide_pass);

        user_type = findViewById(R.id.user_type);
        mechanic = findViewById(R.id.mechanic_radio_btn);
        owner = findViewById(R.id.owner_radio_btn);

        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        usersRef = rootNode.getReference("users");
        mechanicRef = usersRef.child("mechanic");
        ownerRef = usersRef.child("owner");

        UsersHelper usersHelper = new UsersHelper();

        Intent login = new Intent(getApplicationContext(), login_page.class);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(first_form_layout.getVisibility() == View.VISIBLE){
                    if(fullname.getText().toString().isEmpty() || email.getText().toString().isEmpty() || contact_number.getText().toString().isEmpty()){
                        Toast.makeText(Signup_form.this, "PLEASE COMPLETE THE FORM", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else{
                        first_form_layout.setVisibility(View.GONE);
                        second_form_layout.setVisibility(View.VISIBLE);
                        back.setVisibility(View.VISIBLE);
                        next.setText("Finish");
                    }
                }

                else if(second_form_layout.getVisibility() == View.VISIBLE){

                    if(password.getText().toString().isEmpty() || c_password.getText().toString().isEmpty()){
                        Toast.makeText(Signup_form.this, "PLEASE ENTER YOUR PASSWORD", Toast.LENGTH_LONG).show();
                    }
                    else if(password.getText().length() != 8 || c_password.getText().toString().length() != 8){
                        Toast.makeText(Signup_form.this, "Password must be 8+ characters", Toast.LENGTH_LONG).show();
                    }
                    else{
                        if(password.getText().toString().equals(c_password.getText().toString())){

                            usersHelper.setFullname(fullname.getText().toString().trim());
                            usersHelper.setContact_number(contact_number.getText().toString().trim());
                            usersHelper.setEmail(email.getText().toString().trim());
                            usersHelper.setPassword(password.getText().toString().trim());


                            String emailID = email.getText().toString();
                            String passwordID = password.getText().toString();

                            mAuth.createUserWithEmailAndPassword(emailID, passwordID)
                                    .addOnCompleteListener(Signup_form.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        if(mechanic.isChecked()) {
                                            mechanicRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(usersHelper);
                                            startActivity(login);
                                            finish();
                                        }
                                        else if(owner.isChecked()){
                                            ownerRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(usersHelper);
                                            startActivity(login);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(Signup_form.this, "PLEASE SELECT MECHANIC/OWNER", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                    else{
                                        Toast.makeText(Signup_form.this, "Authentication Failed." + task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                        else{
                            Toast.makeText(Signup_form.this, "PASSWORD DOESN'T MATCH", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(second_form_layout.getVisibility() == View.VISIBLE){
                    next.setText("Next");
                    first_form_layout.setVisibility(View.VISIBLE);
                    second_form_layout.setVisibility(View.GONE);
                    back.setVisibility(View.GONE);
                }
            }
        });

        show_hide_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    password.setTransformationMethod(null);
                    c_password.setTransformationMethod(null);
                }
                else{
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    c_password.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
    }

    //Hide soft keyboard when touched the outside of the edit text.
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom()) ) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }
}