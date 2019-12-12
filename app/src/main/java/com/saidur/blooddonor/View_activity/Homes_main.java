package com.saidur.blooddonor.View_activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saidur.blooddonor.Adapter.Donors_viewAdaptar;
import com.saidur.blooddonor.Adapter.firebase_donors_viewholder;
import com.saidur.blooddonor.Add_activity.Add_donorActivity;
import com.saidur.blooddonor.Available_activity.Available_donorActivity;
import com.saidur.blooddonor.Database.Queary;
import com.saidur.blooddonor.Home_Activity;
import com.saidur.blooddonor.Model.Donors;
import com.saidur.blooddonor.Others.About_Activity;
import com.saidur.blooddonor.Others.Creates_profile;
import com.saidur.blooddonor.Others.Registration_Activity;
import com.saidur.blooddonor.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Homes_main extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    FirebaseAuth firebaseauth;
    FirebaseDatabase firebaseDatabase;
    TextView namedrawer;
    private Spinner spinner;
    private EditText editText;
    private TextView availableDonorTV;
    private Button viewProfileBTN;
    private RecyclerView recyclerView;
    private List<Donors> donorsList ;
    private Donors_viewAdaptar donorViewAdepter;
    private Queary quary;
    private String blood = null;
    private ImageView imageView;
    private FirebaseRecyclerOptions<Donors> options;
    private FirebaseRecyclerAdapter<Donors, firebase_donors_viewholder> adapter;
    DatabaseReference databaseReference;
   // private Queary quary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homes_main);
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

          namedrawer=findViewById(R.id.nameShowdrwar);
         CircleImageView imagedrawer = findViewById(R.id.imageShow_drwar);
        firebaseauth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference donor_prof_drawer_reference =firebaseDatabase.getReference(firebaseauth.getUid());

        donor_prof_drawer_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Donors donorsdrawer = dataSnapshot.getValue(Donors.class);
               // namedrawer.setText(donorsdrawer.getName());
                //imagedrawer.setImageBitmap(donorsdrawer.getImage());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Homes_main.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

        inti();
        blood = getIntent().getStringExtra("Bloodgroup");
        DataShow();

        recyclerView.setAdapter(adapter);
        onStart();
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
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        DataShow();
    }

    private void DataShow()
    {

        try {

            availableDonorTV.setBackgroundResource(R.drawable.available_donor_bg_design);

            if (blood == null) {


                //  donorList = quary.getDonorDBdata();

            } else {
                spinner.setVisibility(View.GONE);
                availableDonorTV.setText("Available Donor List");
                donorsList = quary.getAvalableBloodGroupDetails(blood);
            }

        } catch (Exception e) {

        }

        if(donorsList == null)
        {
            imageView.setVisibility(View.VISIBLE);
            //Toast.makeText(this, "image", Toast.LENGTH_SHORT).show();
            imageView.setImageResource(R.drawable.not_found);
        }
        else
        {
            //Toast.makeText(this, "found", Toast.LENGTH_SHORT).show();
            imageView.setVisibility(View.GONE);
            //  recyclerView.setVisibility(View.VISIBLE);
        }

        donorViewAdepter = new Donors_viewAdaptar(donorsList, this);
        recyclerView.setAdapter(donorViewAdepter);
    }

    private void searchDataShow(String search, boolean blood) {

        try {


            if (blood) {

                donorsList = quary.searchByBlood(search);

            } else {
                //spinner.setVisibility(View.GONE);
                donorsList = quary.searchByName(search);
            }

        } catch (Exception e) {

        }

        if(donorsList.size()<1)
        {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.not_found);
        }
        else {
            imageView.setVisibility(View.GONE);

        }
        donorViewAdepter = new Donors_viewAdaptar(donorsList, this);
        recyclerView.setAdapter(donorViewAdepter);
    }

    /*private void onClickzs() {
        viewProfileBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homes_main.this,View_profileActivity.class));
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String blood = spinner.getSelectedItem().toString();
                    searchDataShow(blood, true);
                } else {
                    DataShow();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != 0) {

                    searchDataShow(s.toString(), false);
                } else {

                    DataShow();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }*/
    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
    private void inti() {

        imageView = findViewById(R.id.bloodSearchIVId);
        availableDonorTV = findViewById(R.id.availableDonorTVId);
        //donors class create korarpor  arrylit create korbo
        donorsList = new ArrayList<>();
        //quary = new Queary(this);
        spinner = findViewById(R.id.search_by_blood_spinner_id);
        editText = findViewById(R.id.search_by_name_editText_id);
        recyclerView = findViewById(R.id.viewRecyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //  viewProfileBTN = findViewById(R.id.viewProfileBTNId);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Donors_Profile");
        options=new FirebaseRecyclerOptions.Builder<Donors>()
                .setQuery(databaseReference,Donors.class).build();

        adapter =new FirebaseRecyclerAdapter<Donors, firebase_donors_viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull firebase_donors_viewholder holder, int position, @NonNull Donors model) {

                holder.donor_name.setText(model.getName());
                holder.donor_blood_group.setText(model.getBloodGroup());
                holder.donor_image.setImageBitmap(StringToBitMap(model.getImage()));
            }

            @NonNull
            @Override
            public firebase_donors_viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new firebase_donors_viewholder(LayoutInflater.from(Homes_main.this)
                        .inflate(R.layout.view_model_design,viewGroup,false));
            }
        };
    }
}



