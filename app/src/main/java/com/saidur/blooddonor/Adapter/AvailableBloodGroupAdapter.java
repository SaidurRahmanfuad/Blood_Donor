package com.saidur.blooddonor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.saidur.blooddonor.Model.Donors;
import com.saidur.blooddonor.R;
import com.saidur.blooddonor.View_activity.View_activity;


import java.util.ArrayList;
import java.util.List;

public class AvailableBloodGroupAdapter extends  RecyclerView.Adapter<AvailableBloodGroupAdapter.MyViewHolder>{

    private List<Donors> donorsList = new ArrayList<>();
    private Context context;

    public AvailableBloodGroupAdapter(List<Donors> donorList, Context context)
    {
        this.donorsList = donorList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.model_available_blood,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
        final Donors donor = donorsList.get(i);
        myViewHolder.bloodGroupTV.setText(donor.getBloodGroup());

        myViewHolder.viewBloodBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                context.startActivity(new Intent(context, View_activity.class).putExtra("Bloodgroup",donor.getBloodGroup()).putExtra("ID",donor.getId()));
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return donorsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        private TextView bloodGroupTV;
        private Button viewBloodBTN;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            bloodGroupTV = itemView.findViewById(R.id.modelAvailableBloodGroupTVId);
            viewBloodBTN = itemView.findViewById(R.id.modelViewAvailableBTNId);

        }
    }
}