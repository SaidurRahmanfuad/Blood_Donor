package com.saidur.blooddonor.View_activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.saidur.blooddonor.Adapter.Donors_viewAdaptar;
import com.saidur.blooddonor.Adapter.firebase_donors_viewholder;
import com.saidur.blooddonor.Database.Queary;
import com.saidur.blooddonor.Model.Donors;
import com.saidur.blooddonor.R;

import java.util.ArrayList;
import java.util.List;

public class View_activity extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_activity);
        /*inti();
        blood = getIntent().getStringExtra("Bloodgroup");
        DataShow();

        onClick();
        recyclerView.setAdapter(adapter);
        onStart();*/
    }

    /*@Override
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

    private void onClick() {
       viewProfileBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(View_activity.this,View_profileActivity.class));
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


    }
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
                return new firebase_donors_viewholder(LayoutInflater.from(View_activity.this)
                        .inflate(R.layout.view_model_design,viewGroup,false));
            }
        };
    }*/
}
