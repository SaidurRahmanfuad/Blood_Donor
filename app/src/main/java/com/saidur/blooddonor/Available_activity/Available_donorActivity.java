package com.saidur.blooddonor.Available_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.saidur.blooddonor.Adapter.AvailableBloodGroupAdapter;
import com.saidur.blooddonor.Database.Queary;
import com.saidur.blooddonor.Model.Donors;
import com.saidur.blooddonor.R;

import java.util.ArrayList;
import java.util.List;

public class Available_donorActivity extends AppCompatActivity {
    private Button viewAvailableBTN;
    private RecyclerView recyclerView;
    private List<Donors> donorsList;
    private AvailableBloodGroupAdapter adapter;
    private Queary queary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_donor);
        init();

    }


    private void init()
    {
        recyclerView = findViewById(R.id.availableBloodGroupRecyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        queary = new Queary(this);

        donorsList = new ArrayList<>();
        donorsList = queary.getAvalableBloodGroup();
        ImageView imageView = findViewById(R.id.imageViewId);

        if(donorsList.size() < 1)
        {
            imageView.setImageResource(R.drawable.not_available_icon);
            //recyclerView.setBackgroundResource(R.drawable.not_available_icon);
        }
        else {
            imageView.setVisibility(View.GONE);
            adapter = new AvailableBloodGroupAdapter(donorsList, this);
            recyclerView.setAdapter(adapter);
        }
        //   viewAvailableBTN = findViewById(R.id.viewAvailableBTNId);
    }
}