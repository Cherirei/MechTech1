package com.example.mechtech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    //  Boolean valid = true;

    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button btnlogin_user, btnRegister;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnlogin_user = findViewById(R.id.button_login_account);
        btnRegister = findViewById(R.id.buttonRegister);
        progressBar = findViewById(R.id.progress_bar);
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        //Hook to all xml elements in activity_signup.xml
        regName = findViewById(R.id.reg_name);
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNo = findViewById(R.id.reg_phoneNo);
        regPassword = findViewById(R.id.reg_password);

        //save data in Firebase on button click
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("user");

                //Get all the values
                final String name = regName.getEditText().getText().toString();
                final String username = regUsername.getEditText().getText().toString();
                final String email = regEmail.getEditText().getText().toString();
                final String phonenumber = regPhoneNo.getEditText().getText().toString();
                final String password = regPassword.getEditText().getText().toString();


                progressBar.setVisibility(View.VISIBLE);
                if (email.isEmpty()) {
                    Toast.makeText(SignUp.this, "Email must not be empty", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else if (password.isEmpty()) {
                    Toast.makeText(SignUp.this, "Password must not be empty", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else if (password.length() < 6) {
                    regPassword.setError("Characters must be more than 6");
                    progressBar.setVisibility(View.GONE);
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(email.trim(), password.trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                DocumentReference df = fStore.collection("Users").document(user.getUid());
                                Map<String, Object> userInfo = new HashMap<>();
                                userInfo.put("Name", name);
                                userInfo.put("UserName", username);
                                userInfo.put("Email", email.trim());
                                userInfo.put("PhoneNo", phonenumber);
                                userInfo.put("Password", password.trim());
                                //Specify the access level
                                userInfo.put("isUser", "1");
                                //Save data to firestore
                                df.set(userInfo);

                                startActivity(new Intent(getApplicationContext(), Dash.class));
                                finish();
                            } else {
                                Toast.makeText(SignUp.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }

             /*   UserHelperClass helperClass=new UserHelperClass(name,username,email,phonenumber,password);

                reference.child(phonenumber).setValue(helperClass);*/
            }
        });

        btnlogin_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }
}