package com.example.mechtech;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    //initialise fusedLocationProviderClient
    private FusedLocationProviderClient fusedLocationProviderClient;


    TextView textView1, textView2, textView3, textView4, textView5;
    private String county = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //floatingActionButton = view.findViewById(R.id.compose_message);
        Button btnGet_Service = view.findViewById(R.id.button_get_service);
        textView1 = view.findViewById(R.id.text_view1);
        textView2 = view.findViewById(R.id.text_view2);
        textView3 = view.findViewById(R.id.text_view3);
        textView4 = view.findViewById(R.id.text_view4);
        textView5 = view.findViewById(R.id.text_view5);

        //        //getLocation();
//        floatingActionButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HelpFragment()).commit());
        btnGet_Service.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StationCallFragment(county)).commit();
            // String filterCounty=textView2.getText().toString();
            //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ViewStationsFragment(filterCounty)).commit();
        });
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        checkPermission();

        return view;
    }

    private void checkPermission() {
        //Check permission
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //if permission granted
            getLocation();
        } else {
            //when permission denied
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            //Initialise location
            generateData(task.getResult());
        });
    }

    private void generateData(Location location) {
        if (location != null) {
            try {
                //Initialise geocoder
                Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
                //Initialise address list
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);

                county = addresses.get(1).getAdminArea();

                //Set Latitudes on textViews
                textView1.setText(Html.fromHtml("<font color='#000'><b>Country :</b></font>"
                        + addresses.get(1).getCountryName()));
                //Set Longitude
                textView2.setText(Html.fromHtml("<font color='#000'><b>County :</b></br></font>"
                        + addresses.get(1).getAdminArea()));
                //Set Country name
                textView3.setText(Html.fromHtml("<font color='#000'><b>Sub Locality :</b></font>"
                        + addresses.get(1).getSubLocality()));
                //Set Locality
                textView4.setText(Html.fromHtml("<font color='#000'><b>Featured Name :</b></br></font>"
                        + addresses.get(1).getFeatureName()));
                //Set Address
                textView5.setText(Html.fromHtml("<font color='#000'><b>Address :</b></font>"
                        + addresses.get(2).getAddressLine(0)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}