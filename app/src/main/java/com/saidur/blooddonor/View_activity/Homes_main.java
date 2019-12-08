package com.saidur.blooddonor.View_activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saidur.blooddonor.Add_activity.Add_donorActivity;
import com.saidur.blooddonor.Available_activity.Available_donorActivity;
import com.saidur.blooddonor.Database.Queary;
import com.saidur.blooddonor.Home_Activity;
import com.saidur.blooddonor.Model.Donors;
import com.saidur.blooddonor.Others.About_Activity;
import com.saidur.blooddonor.Others.Creates_profile;
import com.saidur.blooddonor.Others.Registration_Activity;
import com.saidur.blooddonor.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Homes_main extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Button viewBtn,addBtn,availableBtn,aboutBTN;
    FirebaseAuth firebaseauth;
    FirebaseDatabase firebaseDatabase;
    TextView namedrawer;
   // private Queary quary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homes_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final DrawerArrowDrawable indicator = new DrawerArrowDrawable(this);
        indicator.setColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(indicator);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (((ViewGroup) drawerView).getChildAt(1).getId() == R.id.leftSideBar) {
                    indicator.setProgress(slideOffset);
                }
            }
        });


        //init();
        //onClicks();
        //fetchdata();
          namedrawer=findViewById(R.id.nameShowdrwar);
         CircleImageView imagedrawer = findViewById(R.id.imageShow_drwar);
        firebaseauth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference donor_prof_drawer_reference =firebaseDatabase.getReference(firebaseauth.getUid());

        donor_prof_drawer_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Donors donorsdrawer = dataSnapshot.getValue(Donors.class);
                namedrawer.setText(donorsdrawer.getName());
                //imagedrawer.setImageBitmap(donorsdrawer.getImage());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Homes_main.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchdata(){

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }
        return true;
    }

    //Drwar menu te chap dile oitar nam toast dekhabe
    public void onClicks(View view) {
        if (view instanceof TextView) {
            String title = ((TextView) view).getText().toString().trim();
            if (title.startsWith("DashBoard")) {
                // Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Homes_main.this, Available_donorActivity.class);
                startActivity(i);
            } if(title.startsWith("Profile")){
                Intent i = new Intent(Homes_main.this, View_profileActivity.class);
                startActivity(i);
            }if(title.startsWith("About Us")){
                Intent i = new Intent(Homes_main.this, About_Activity.class);
                startActivity(i);

            }
            if(title.startsWith("Logout")){
                finish();
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(Homes_main.this, Registration_Activity.class);
                startActivity(i);
                finish();
            }
        } /*else if (view.getId() == R.id.userInfo) {
            startActivity(Univarsal_Activity.newIntent(this, "Personal"));
        }*/
    }

   /* private void onClickz()
    {

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               Toast.makeText(MainActivity.this, countryCodePicker.getSelectedCountryCode(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Homes_main.this, View_activity.class));

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homes_main.this, Add_donorActivity.class));


            }
        });

        availableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Homes_main.this, View_profileActivity.class));

            }
        });
        aboutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              *//* BottomSheetDialog dialog = new BottomSheetDialog(Home_Activity.this);
                View imageCaptureDialogView = getLayoutInflater().inflate(R.layout.activity_about_, null, false);
                dialog.setContentView(imageCaptureDialogView);
                dialog.show();*//*
                startActivity(new Intent(Homes_main.this, View_activity.class));
                finish();

            }
        });
    }*/

    private void init()
    {
       // quary = new Queary(Homes_main.this);
        /*aboutBTN = findViewById(R.id.aboutBTNId);
        viewBtn = findViewById(R.id.viewBTNId);
        addBtn = findViewById(R.id.addBTNId);
        availableBtn = findViewById(R.id.avalibleBTNId);*/


    }

}
