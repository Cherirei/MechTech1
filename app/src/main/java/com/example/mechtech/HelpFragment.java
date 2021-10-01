package com.example.mechtech;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class HelpFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_help, container, false);


        //setTitle("Help");

        View tvcontactus = view.findViewById(R.id.contact_us);
        View tvReachus = view.findViewById(R.id.contact_us_bottom);

        tvcontactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"johkirwa17.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Having trouble using the outside catering mobile application");
                i.putExtra(Intent.EXTRA_TEXT, "I am ..........");
                try {
                    startActivity(Intent.createChooser(i, "Send email with...?"));
                } catch (android.content.ActivityNotFoundException exception) {
                    Toast.makeText(getActivity(), "Gmail not Installed!" + exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        tvReachus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"johkirwa17@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Having trouble using Near Mechanic mobile application");
                i.putExtra(Intent.EXTRA_TEXT, "I am ..........");
                try {
                    startActivity(Intent.createChooser(i, "Send email with...?"));
                } catch (android.content.ActivityNotFoundException exception) {
                    Toast.makeText(getActivity(), "Gmail not Installed!" + exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
}