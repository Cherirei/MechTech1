package com.example.mechtech;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class RatingFragment extends Fragment {

    Button btn_rating;
    RatingBar ratingBar;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_rating, container, false);

        btn_rating=view.findViewById(R.id.button_rating);
        ratingBar=view.findViewById(R.id.rating1);

        btn_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float ratingValue=ratingBar.getRating();
                Toast.makeText(getActivity(), "Rating: "+ratingValue, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}