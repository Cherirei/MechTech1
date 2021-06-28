package com.example.mechtech;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StationCallFragment extends Fragment {

    private RecyclerView recyclerViewCall;
    private View view;
    private FirebaseDatabase db;
    private DatabaseReference root;
    private StationCallAdapterView adapter;
    private ArrayList<ServiceStations> list;
    private CardView cardView_Call;
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
        view = inflater.inflate(R.layout.fragment_call_station, container, false);

/*
        cardView_Call=view.findViewById(R.id.cardView_call);

        cardView_Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "I am responding", Toast.LENGTH_SHORT).show();
            }
        });*/
        db = FirebaseDatabase.getInstance();

        recyclerViewCall = view.findViewById(R.id.recyclerView_call);
        recyclerViewCall.setHasFixedSize(true);
        recyclerViewCall.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        adapter = new StationCallAdapterView(getContext(), list);
        recyclerViewCall.setAdapter(adapter);
        adapter.setOnSelect(new StationCallAdapterView.OnSelect() {
            @Override
            public void onPhoneClick(String number) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: " + number));
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    startActivity(Intent.createChooser(intent, null));
                    // startActivity(Intent.createChooser(intent, "Share Options"),null);

                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]
                            {Manifest.permission.CALL_PHONE}, 44);
                }

            }
        });

        root = db.getReference("ServiceStations");
        //root=db.getReference().child();
        //  Query query=root.child("ServiceStations").orderByChild("county").equalTo(fCounty);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ServiceStations serviceStations = dataSnapshot.getValue(ServiceStations.class);
                    list.add(serviceStations);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}