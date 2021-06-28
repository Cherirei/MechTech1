package com.example.mechtech;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class EditProfile extends AppCompatActivity {


    public static final String TAG = "TAG";
    EditText prof_name, prof_username, prof_email, prof_phoneNo;
    Button btnSaveProfileInfo;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent data = getIntent();
        String name = data.getStringExtra("Name");
        String username = data.getStringExtra("UserName");
        String email = data.getStringExtra("Email");
        final String phoneNo = data.getStringExtra("PhoneNo");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        prof_name = findViewById(R.id.edit_name);
        prof_username = findViewById(R.id.edit_username);
        prof_email = findViewById(R.id.edit_email);
        prof_phoneNo = findViewById(R.id.edit_phoneNo);
        btnSaveProfileInfo = findViewById(R.id.button_Save);

        prof_name.setText(name);
        prof_username.setText(username);
        prof_email.setText(email);
        prof_phoneNo.setText(phoneNo);

        btnSaveProfileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final String name = prof_name.getText().toString();
                //final String username = prof_username.getText().toString();
                //final String email = prof_email.getText().toString();
                // final String phonenumber = prof_phoneNo.getText().toString();

                if (prof_name.getText().toString().isEmpty() || prof_username.getText().toString().isEmpty() || prof_email.getText().toString().isEmpty() || prof_phoneNo.getText().toString().isEmpty()) {
                    Toast.makeText(EditProfile.this, "One or More fields are empty.", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String email = prof_email.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fStore.collection("Users").document(user.getUid());
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("Email", email);
                        edited.put("Name", prof_name.getText().toString());
                        edited.put("PhoneNo", prof_phoneNo.getText().toString());
                        edited.put("UserName", prof_username.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfile.this, "Profile Updated.", Toast.LENGTH_SHORT).show();
                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.frament_container, new UserProfileFragment());
                                finish();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        Log.d(TAG, "onCreate: " + name + " " + username + " " + email + " " + phoneNo);
    }

}