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
    private String fCounty;

    public StationCallFragment() {
    }

    public StationCallFragment(String fCounty) {
        this.fCounty = fCounty;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_call_station, container, false);

        db = FirebaseDatabase.getInstance();

        recyclerViewCall = view.findViewById(R.id.recyclerView_call);

        recyclerViewCall.setHasFixedSize(true);
        recyclerViewCall.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        adapter = new StationCallAdapterView(getContext(), list);
        recyclerViewCall.setAdapter(adapter);
        adapter.setOnSelect(number -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel: " + number));
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                startActivity(Intent.createChooser(intent, null));

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]
                        {Manifest.permission.CALL_PHONE}, 44);
            }
        });

        root = db.getReference("ServiceStations");
        //root=db.getReference().child();
        //  Query query=root.child("ServiceStations").orderByChild("county").equalTo(fCounty);
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Log.i("BUG_FIX", fCounty);
                list.clear();
                if (fCounty != null) {
                    // Log.i("BUG_FIX", fCounty + " is not null");
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        //Log.i("BUG_FIX", "dataSnapshot");
                        ServiceStations serviceStations = dataSnapshot.getValue(ServiceStations.class);
                        if (serviceStations != null && fCounty.startsWith(serviceStations.getCounty())) {
                            list.add(serviceStations);
                            //Log.i("BUG_FIX", "here");
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}