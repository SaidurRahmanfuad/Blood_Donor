package com.saidur.blooddonor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saidur.blooddonor.Add_activity.Add_donorActivity;
import com.saidur.blooddonor.Available_activity.Available_donorActivity;
import com.saidur.blooddonor.Database.Queary;
import com.saidur.blooddonor.Model.Donors_online;
import com.saidur.blooddonor.Others.About_Activity;
import com.saidur.blooddonor.View_activity.View_Profile_main;
import com.saidur.blooddonor.View_activity.View_activity;

public class Home_Activity extends AppCompatActivity {
    private Queary quary;
    private Button viewBtn,addBtn,availableBtn,aboutBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);

        init();
        OnlineDataCheck();
        onClick();
    }
    private void OnlineDataCheck() {


        if (quary.getUserId() == null) {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("User");

            databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    //   Log.d("This is Test : ", "have data check name : " + donorOnline.getName());

                    if (dataSnapshot != null) {

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Donors_online donorOnline = dataSnapshot1.getValue(Donors_online.class);
                            //firebaseId.add(donorOnline);

                            quary.ReUpdateInsertData(donorOnline);

                        }

/*
                    for (int j = 0; j < firebaseId.size(); j++) {



                    }*/

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }


    private void onClick()
    {

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               Toast.makeText(MainActivity.this, countryCodePicker.getSelectedCountryCode(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Home_Activity.this, View_activity.class));

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_Activity.this, Add_donorActivity.class));


            }
        });

        availableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Home_Activity.this, Available_donorActivity.class));

            }
        });
        aboutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
                View imageCaptureDialogView = getLayoutInflater().inflate(R.layout.about_layout, null, false);
                dialog.setContentView(imageCaptureDialogView);
                dialog.show();*/
                startActivity(new Intent(Home_Activity.this, View_Profile_main.class));
                finish();

            }
        });
    }

    private void init()
    {
        quary = new Queary(Home_Activity.this);
        aboutBTN = findViewById(R.id.aboutBTNId);
        viewBtn = findViewById(R.id.viewBTNId);
        addBtn = findViewById(R.id.addBTNId);
        availableBtn = findViewById(R.id.avalibleBTNId);
    }
}
