package com.example.mechtech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StationAdapterView extends RecyclerView.Adapter<StationAdapterView.MyViewHolder> {

    ArrayList<ServiceStations> sList;
    Context context;

    public StationAdapterView(Context context, ArrayList<ServiceStations> sList) {
        this.sList = sList;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.stations_items, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ServiceStations serviceStations = sList.get(position);
        holder.code.setText(serviceStations.getCode());
        holder.name.setText(serviceStations.getName());
        holder.county.setText(serviceStations.getCounty());
        holder.address.setText(serviceStations.getAddress());
        holder.workingHrs.setText(serviceStations.getWorkingHours());
        holder.phone_No.setText(serviceStations.getPhoneNo());
    }

    @Override
    public int getItemCount() {
        return sList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView code, name, county, address, workingHrs,phone_No;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            code = itemView.findViewById(R.id.code_station);
            name = itemView.findViewById(R.id.name_station);
            county = itemView.findViewById(R.id.county_station);
            address = itemView.findViewById(R.id.address_station);
            workingHrs = itemView.findViewById(R.id.workingHrs_station);
            phone_No = itemView.findViewById(R.id.phone_no_station);
        }
    }
}
