package com.example.mechtech;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StationsFragment extends Fragment {

    EditText stationCode, stationName, stationCounty, stationAddress, stationworkingHrs,stationPhoneNo;
    Button btnAdd, btnBack;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stations, container, false);

        stationCode = view.findViewById(R.id.station_code);
        stationCounty = view.findViewById(R.id.station_county);
        stationName = view.findViewById(R.id.station_name);
        stationAddress = view.findViewById(R.id.station_postal_code);
        stationworkingHrs = view.findViewById(R.id.working_hours);
        stationPhoneNo=view.findViewById(R.id.station_phone_no);
        btnAdd = view.findViewById(R.id.btn_add_station);
        btnBack = view.findViewById(R.id.btn_back_station);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("ServiceStations");

                String code = stationCode.getText().toString();
                String name = stationName.getText().toString();
                String county = stationCounty.getText().toString();
                String address = stationAddress.getText().toString();
                String workingHrs = stationworkingHrs.getText().toString();
                String phoneNo=stationPhoneNo.getText().toString();
                if (code.isEmpty()) {
                    Toast.makeText(getContext(), "Code Field is empty", Toast.LENGTH_SHORT).show();

                } else if (name.isEmpty()) {
                    Toast.makeText(getContext(), "Name Field is empty", Toast.LENGTH_SHORT).show();

                } else if (county.isEmpty()) {
                    Toast.makeText(getContext(), "County Field is empty", Toast.LENGTH_SHORT).show();
                } else if (address.isEmpty()) {
                    Toast.makeText(getContext(), "Address Field is empty", Toast.LENGTH_SHORT).show();
                } else if (workingHrs.isEmpty()) {
                    Toast.makeText(getContext(), "Working Hours Field is empty", Toast.LENGTH_SHORT).show();

                } else if (phoneNo.isEmpty()) {
                    Toast.makeText(getContext(), "Phone Number Field is empty", Toast.LENGTH_SHORT).show();
                } else {
                    // String userId = FirebaseDatabase.getInstance().toString();
                    ServiceStations serviceStations = new ServiceStations(code, name, county, address, workingHrs,phoneNo);
                    reference.child(address).setValue(serviceStations);
                    Toast.makeText(getActivity(), "Successfully Added", Toast.LENGTH_SHORT).show();
                }

                stationCode.setText("");
                stationName.setText("");
                stationCounty.setText("");
                stationAddress.setText("");
                stationworkingHrs.setText("");
                stationPhoneNo.setText("");
                stationCode.requestFocus();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminPanelFragment()).commit();
            }
        });
        return view;
    }
}