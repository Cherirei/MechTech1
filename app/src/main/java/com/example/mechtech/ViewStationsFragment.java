package com.example.mechtech;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewStationsFragment extends Fragment {

    Button back_view;
    private RecyclerView recyclerView;
    private View view;
    private FirebaseDatabase db;
    private DatabaseReference root ;
    private StationAdapterView adapter;
    private ArrayList<ServiceStations> list;
  /* *//* private String fCounty;

    public ViewStationsFragment() {
    }

    public ViewStationsFragment(String fCounty) {
        this.fCounty = fCounty;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_stations, container, false);


        db = FirebaseDatabase.getInstance();
        
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();

        root = db.getReference("ServiceStations");
        //root=db.getReference().child();
      //  Query query=root.child("ServiceStations").orderByChild("county").equalTo(fCounty);

        back_view=view.findViewById(R.id.back_view);
        back_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frament_container, new AdminPanelFragment()).commit();
            }
        });
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ServiceStations serviceStations = dataSnapshot.getValue(ServiceStations.class);
                        list.add(serviceStations);
                }
                adapter = new StationAdapterView(getContext(), list);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}