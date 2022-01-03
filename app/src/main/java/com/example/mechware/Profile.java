package com.example.mechware;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.mechware.Adapter.ProfileFragAdapter;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

public class Profile extends AppCompatActivity {

    ViewPager2 data_pager;

    ImageButton menu_btn2, change_picture_btn;

    ProfileFragAdapter profileFragAdapter;

    FirebaseDatabase rootNode;
    DatabaseReference usersRef, userTypeRef, uuidRef;
    FirebaseAuth mAuth;

    String user_type, uuid;

    TabLayout profile_tab;

    Uri imageUri, croppedImageUri;
    ShapeableImageView profile_picture;

    FirebaseStorage storage;
    StorageReference storageReference;
    StorageTask uploadTask;
    String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        user_type = getIntent().getStringExtra("user_type");

        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        usersRef = rootNode.getReference("users");
        userTypeRef = usersRef.child(user_type);

        uuid = mAuth.getCurrentUser().getUid();

        uuidRef = userTypeRef.child(uuid);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        data_pager = findViewById(R.id.data_pager);

        data_pager.setClipToPadding(false);
        data_pager.setClipChildren(false);
        data_pager.setOffscreenPageLimit(3);
        data_pager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        profile_tab = findViewById(R.id.profile_tab);
        profile_tab.setTabGravity(TabLayout.GRAVITY_FILL);

        profileFragAdapter = new ProfileFragAdapter(this);
        data_pager.setAdapter(profileFragAdapter);

        new TabLayoutMediator(profile_tab, data_pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull @NotNull TabLayout.Tab tab, int position) {
                if(position == 0){
                    tab.setText("Account");
                }
                else if (position == 1){
                    tab.setText("Other Info");
                }
                else{
                    tab.setText("Change Password");
                }
            }
        }).attach();

        menu_btn2 = findViewById(R.id.menu_btn2);
        menu_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), home_page.class);
                intent.putExtra("user_type", user_type);
                startActivity(intent);
                finish();
            }
        });

        change_picture_btn = findViewById(R.id.change_picture_btn);
        change_picture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        profile_picture = findViewById(R.id.profile_picture);

        uuidRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                if (snapshot.hasChild("profile_picture")){
                    String image = snapshot.child("profile_picture").getValue().toString();
                    Picasso.get().load(image).into(profile_picture);
                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });

    }


    public void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null) {
            imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);

        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK){
                croppedImageUri = result.getUri();

                storeImage();
                profile_picture.setImageURI(croppedImageUri);

            }
        }
    }


    public void storeImage(){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading...");
        pd.show();

        StorageReference ref = storageReference.child("images/" + uuid);

        uploadTask = ref.putFile(croppedImageUri).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @org.jetbrains.annotations.NotNull UploadTask.TaskSnapshot snapshot) {
                double progress = (99 + snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage((int) progress + "%");
            }
        });
        uploadTask.continueWithTask(new Continuation() {
            @Override
            public Object then(@NonNull @org.jetbrains.annotations.NotNull Task task) throws Exception {
                if(!task.isSuccessful()){
                    throw task.getException();
                }
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Uri downloadUrl = task.getResult();
                    uri = downloadUrl.toString();

                    HashMap<String, Object> userMap = new HashMap<>();
                    userMap.put("profile_picture", uri);
                    userTypeRef.child(uuid).updateChildren(userMap);

                    pd.dismiss();
                }
            }
        });
    }

    public Bundle getUserType(){
        //Pass the user type into the Fragments
        Bundle userType = new Bundle();
        userType.putString("user_type", user_type);
        return userType;
    }

    //for Back Button ng Cellphone
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), home_page.class);
        intent.putExtra("user_type", user_type);
        startActivity(intent);
        finish();
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