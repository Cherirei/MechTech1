package com.example.mechtech;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class AdminPanelFragment extends Fragment {

    CardView cardViewAdd_Stations,cardView_ViewSations;
    TextView textView_users;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_admin_panel, container, false);


        cardViewAdd_Stations=view.findViewById(R.id.card_add_stations);
        cardView_ViewSations=view.findViewById(R.id.card_view_stations);
        textView_users=view.findViewById(R.id.view_users);

        textView_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new UserFragment()).commit();
            }
        });



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