package com.example.mechtech;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class UserProfileFragment extends Fragment {

    TextView name, username, email, phoneNo, username2, resetPassword, editProfile;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile_frament, container, false);

        name = view.findViewById(R.id.profile_name);
        username = view.findViewById(R.id.profile_username);
        username2 = view.findViewById(R.id.username2);
        email = view.findViewById(R.id.profile_email);
        phoneNo = view.findViewById(R.id.profile_phoneNo);
        resetPassword = view.findViewById(R.id.resetPassword);
        editProfile = view.findViewById(R.id.edit_profile);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        user = fAuth.getCurrentUser();

        DocumentReference documentReference = fStore.collection("Users").document(userId);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            username2.setText(documentSnapshot.getString("UserName"));
            name.setText(documentSnapshot.getString("Name"));
            username.setText(documentSnapshot.getString("UserName"));
            email.setText(documentSnapshot.getString("Email"));
            phoneNo.setText(documentSnapshot.getString("PhoneNo"));
        });

        resetPassword.setOnClickListener(v -> {

            final EditText resetPassword = new EditText(v.getContext());
            final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
            passwordResetDialog.setTitle("Reset Password?");
            passwordResetDialog.setMessage("Enter new Password > 6 characters long.");
            passwordResetDialog.setView(resetPassword);

            passwordResetDialog.setPositiveButton("Yes", (dialog, which) -> {
                //extract the mail and send the link
                String newPassword = resetPassword.getText().toString().trim();
                if (newPassword.isEmpty()) {
                    Toast.makeText(UserProfileFragment.this.getContext(), "Field is Empty!!", Toast.LENGTH_SHORT).show();
                } else {
                    user.updatePassword(newPassword).addOnSuccessListener(aVoid -> Toast.makeText(UserProfileFragment.this.getContext(), "Password Successfully Changed.", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(UserProfileFragment.this.getContext(), "Password Reset Failed!!", Toast.LENGTH_SHORT).show());
                }
            });
            passwordResetDialog.setNegativeButton("No", (dialog, which) -> {
                //Close the Dialog
            });
            passwordResetDialog.create().show();
        });

        editProfile.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), EditProfile.class);
            i.putExtra("Name", name.getText().toString());
            i.putExtra("Email", email.getText().toString());
            i.putExtra("UserName", username2.getText().toString());
            i.putExtra("PhoneNo", phoneNo.getText().toString());
            // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditProfileFragment()).commit();
            startActivity(i);
        });

        return view;
    }
}