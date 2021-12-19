package com.example.mechware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

public class login_page extends AppCompatActivity {

    Button login_btn;

    EditText input_email, input_password;

    TextView userLabel;

    FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference userTypeRef, userRef, uid;

    String user_type;
    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        login_btn = (Button) findViewById(R.id.login_btn);

        input_email = findViewById(R.id.email_editTxt);
        input_password = findViewById(R.id.password_editTxt);

        mAuth = FirebaseAuth.getInstance();

        rootNode = FirebaseDatabase.getInstance();
        userRef = rootNode.getReference("users");


        user_type = getIntent().getStringExtra("user_type");

        userLabel = findViewById(R.id.userLabel);
        if(user_type.equalsIgnoreCase("mechanic")){
            userLabel.setText("MECHANIC");
        }
        else if (user_type.equalsIgnoreCase("owner")){
            userLabel.setText("OWNER");

        }

        input_email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_GO){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                    ProceedLogin();
                }
                return false;
            }
        });

        input_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_GO){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                    ProceedLogin();
                }
                return false;
            }
        });

        // start of activity instances
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProceedLogin();
            }
        });

    }

    public void ProceedLogin(){
        String userEmail = input_email.getText().toString();
        String userPass = input_password.getText().toString();

        if(input_email.getText().toString().isEmpty()){
            input_email.setError("Please enter an email");
            input_email.requestFocus();
            return;
        }

        if(input_password.getText().toString().isEmpty()){
            input_password.setError("Please enter a password");
            input_password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    userRef.orderByChild(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            String parent = "";
                            for(DataSnapshot ds: snapshot.getChildren()){
                                parent = ds.getKey();
                            }

                            if(parent.equalsIgnoreCase(user_type)){
                                userTypeRef = userRef.child(parent);
                                uid = userTypeRef.child(user_id);

                                Intent intent = new Intent(getApplicationContext(),home_page.class);
                                intent.putExtra("user_type", user_type);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                mAuth.signOut();
                                Toast.makeText(login_page.this, "WRONG USER TYPE", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) { }
                    });
                    //progress bar visibility here
                }
                else {
                    Toast.makeText(login_page.this, "LOGIN FAILED / INVALID USERNAME OR PASSWORD", Toast.LENGTH_LONG).show();
                    //progress bar visibility here
                    return;
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