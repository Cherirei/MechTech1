package com.example.mechtech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StationCallAdapterView extends RecyclerView.Adapter<StationCallAdapterView.MyViewHolder> {

    private ArrayList<ServiceStations> sList;
    private Context context;
    private OnSelect onSelect;
    String number;

    public interface OnSelect {
        void onPhoneClick(String number);
    }

    void setOnSelect(OnSelect os) {
        this.onSelect = os;
    }

    public StationCallAdapterView(Context context, ArrayList<ServiceStations> sList) {
        this.sList = sList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.call_stations_items, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final ServiceStations serviceStations = sList.get(position);
        holder.code.setText(serviceStations.getCode());
        holder.name.setText(serviceStations.getName());
        holder.county.setText(serviceStations.getCounty());
        holder.address.setText(serviceStations.getAddress());
        holder.workingHrs.setText(serviceStations.getWorkingHours());
        holder.phoneNumber.setText(serviceStations.getPhoneNo());
        holder.call_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelect.onPhoneClick(sList.get(position).getPhoneNo());
               /* Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: " + serviceStations.getPhoneNo()));*/
               /* if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    context.startActivity(Intent.createChooser(intent, null));*/
                // startActivity(Intent.createChooser(intent, "Share Options"),null);

                 /*}else {
                    ActivityCompat.requestPermissions(, new String[]
                            {Manifest.permission.CALL_PHONE}, 44);
                }*/



               /*
                if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    startActivity(context, Intent.createChooser(intent, "Share Options"),null);
                    startActivity(intent);
                }*/
               /* else
                {
                    ActivityCompat.requestPermissions(, new String[]
                            {Manifest.permission.CALL_PHONE}, 44);
                }*/

               /* if (ActivityCompat.checkSelfPermission(getC, Manifest.permission.CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED) {
                    //if permission granted
                    startActivity(intent);
                } else {
                    //when permission denied
                    ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.CALL_PHONE}, 44);
                }*/
                // sList.get(position).getPhoneNo();
            }
        });
    }

    @Override
    public int getItemCount() {
        return sList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView code, name, county, address, workingHrs, phoneNumber, call_us;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            code = itemView.findViewById(R.id.code_stat);
            name = itemView.findViewById(R.id.name_stat);
            county = itemView.findViewById(R.id.county_stat);
            address = itemView.findViewById(R.id.address_stat);
            workingHrs = itemView.findViewById(R.id.workingHrs_stat);
            phoneNumber = itemView.findViewById(R.id.phoneNo_stat);
            call_us = itemView.findViewById(R.id.call_us);
        }
    }
}
