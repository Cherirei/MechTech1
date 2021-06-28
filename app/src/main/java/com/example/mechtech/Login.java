package com.example.mechtech;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    TextInputLayout input_email, input_password;

    //Boolean valid = true;
    TextView btnForget;
    Button btnNewUser, btnLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.button_Login);
        btnNewUser = findViewById(R.id.button_New_User);
        btnForget = findViewById(R.id.button_forget_password);
        progressBar = findViewById(R.id.progress_Bar);
        input_email = findViewById(R.id.email);
        input_password = findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = input_email.getEditText().getText().toString().trim();
                String password = input_password.getEditText().getText().toString().trim();

                progressBar.setVisibility(View.VISIBLE);
                if (email.isEmpty()) {
                    Toast.makeText(Login.this, "Email must not be empty", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else if (password.isEmpty()) {
                    Toast.makeText(Login.this, "Password must not be empty", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                   else if (password.length() < 6) {
                        input_password.setError("Characters must be more than 6");
                        progressBar.setVisibility(View.GONE);
                } else {
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            checkUserAccessLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this, "Error!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });
        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter Your Email To Receive Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //extract the mail and send the link
                        String mail = resetMail.getText().toString().trim();
                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "Reset Link Sent To Your Mail.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Error! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Close the Dialog
                    }
                });
                passwordResetDialog.create().show();
            }
        });
    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        //Extract the data from the document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: " + documentSnapshot.getData());
                //Identify the access level
                if (documentSnapshot.getString("isAdmin") != null) {
                    //If user is Admin
                    startActivity(new Intent(getApplicationContext(), AdminUser.class));
                    finish();
                }
                    if (documentSnapshot.getString("isUser") != null) {
                        startActivity(new Intent(getApplicationContext(),Dash.class));
                        finish();
                }
            }
        });
    }

  /*  private boolean checkField(TextInputLayout textInputLayout) {
        if (textInputLayout.getEditText().toString().isEmpty()) {
            textInputLayout.setError("Ensure all fields are Entered");
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }*/

}