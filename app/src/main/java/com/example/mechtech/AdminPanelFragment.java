package com.example.mechtech;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class AdminPanelFragment extends Fragment {

    CardView cardViewAdd_Stations,cardView_ViewSations;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_admin_panel, container, false);


        cardViewAdd_Stations=view.findViewById(R.id.card_add_stations);
        cardView_ViewSations=view.findViewById(R.id.card_view_stations);



        cardViewAdd_Stations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new StationsFragment()).commit();
            }
        });
        cardView_ViewSations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ViewStationsFragment()).commit();
            }
        });
        return view;
    }
}