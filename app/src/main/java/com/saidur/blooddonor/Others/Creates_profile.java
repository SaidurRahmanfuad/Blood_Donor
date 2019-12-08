package com.saidur.blooddonor.Others;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.saidur.blooddonor.Add_activity.Add_donorActivity;
import com.saidur.blooddonor.Database.Queary;
import com.saidur.blooddonor.Model.Donors;
import com.saidur.blooddonor.R;
import com.saidur.blooddonor.View_activity.Homes_main;
import com.saidur.blooddonor.View_activity.View_profileActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class Creates_profile extends AppCompatActivity {
    private EditText nameET,ageET,contractNumberET,addressET;
    DatabaseReference databaseDonors;
    private TextView lastDonationDateTV;
    private Spinner bloodGroupSPN;
    private Button doneBTN;
    private ImageView donorIV;
    private DatePickerDialog datePickerDialog;
    private Bitmap donorImage = null;
    private int CAMERA_REQUEAT_CODE = 10;
    private int GALLERY_REQUEST_CODE = 20;
    private String name,age,contractNumber,address,lastDonationDate,bloodGroup;
    //private Queary quary;
    private String donorImageString = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creates_profile);
      databaseDonors = FirebaseDatabase.getInstance().getReference("Donors_Profile");
        init();

        onClick();
    }



    private void init()
    {

        //quary = new Queary(this);
        nameET = findViewById(R.id.nameETId);
        ageET = findViewById(R.id.ageETId);
        contractNumberET = findViewById(R.id.contractETId);
        addressET = findViewById(R.id.addressETId);
        lastDonationDateTV = findViewById(R.id.lastDonatedateTVId);
        doneBTN = findViewById(R.id.doneBTNId);
        bloodGroupSPN = findViewById(R.id.spinnerId);
        donorIV = findViewById(R.id.donorImageId);
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }

    private void onClick() {

        doneBTN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {




                boolean verified = add_donorverifyFild();

                if(verified)
                {
                    ProgressDialog progressDialog = new ProgressDialog(Creates_profile.this);
                    progressDialog.setMessage("Waiting....");
                    progressDialog.show();
                    progressDialog.setCancelable(false);


                    //  String  image = getEncoded64ImageStringFromBitmap(donorImage);
                    progressDialog.dismiss();

                    Donors donors = new Donors(name, age, donorImageString, bloodGroup, contractNumber, lastDonationDate, address);
                    String id= FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                    databaseDonors.child(id).setValue(donors);


                    //Alertdialog("Message", "Profile Update Successfully");
                   /* try {

                        //quary.InsertData(donors);
                      //  clearAll();




                        String id = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                            DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("User");*/

                            // String unicId = firebaseDatabase.push().getKey();
                            //String sqliteId = quary.getUserId();
                           // donors = new Donors(sqliteId, name, age, donorImageString, bloodGroup, contractNumber, lastDonationDate, address);
                           // firebaseDatabase.child(id).child(sqliteId).setValue(donors);

                        startActivity(new Intent(Creates_profile.this, Homes_main.class));
                        finish();


                  /*  }catch (Exception e)
                    {

                    }*/

                }

            }

        });

        donorIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraGalleryImageCapture();
            }
        });

        lastDonationDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar =Calendar.getInstance();
                int Year = calendar.get(Calendar.YEAR);
                int Month= calendar.get(Calendar.MONTH);
                int Day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(Creates_profile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {

                        lastDonationDateTV.setText(dayOfMonth+"-"+(month+1)+"-"+year);

                    }
                },Year,Month,Day);

                datePickerDialog.show();
            }
        });

    }
    private boolean add_donorverifyFild() {

        name = nameET.getText().toString().trim();
        age = ageET.getText().toString().trim();
        contractNumber = contractNumberET.getText().toString().trim();
        address = addressET.getText().toString().trim();
        lastDonationDate = lastDonationDateTV.getText().toString().trim();
        bloodGroup = bloodGroupSPN.getSelectedItem().toString().trim();


        if(lastDonationDate.equals("dd-mm-yyyy"))
        {
            lastDonationDate = "01-01-2000";
        }

        if(name.isEmpty() || age.isEmpty() || contractNumber.isEmpty() || address.isEmpty()  || bloodGroup.isEmpty() )
        {
            Toast.makeText(this,"Insert all field",Toast.LENGTH_SHORT).show();
          //  Alertdialog("Error !! ","Filup the all fild..");

            //lastDonationDate.equals("dd-mm-yyyy")
        }


        else if(donorImage == null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Creates_profile.this);
            builder.setTitle("Are you sure ?");
            builder.setMessage("Donor profile set Default!");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    defaultImage();
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    CameraGalleryImageCapture();
                    dialog.dismiss();
                }
            });
            builder.show();

            //   return true;
        }
        else
        {

            return true;
        }

        return false;
        //return true;
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void defaultImage() {
        Drawable drawable = getResources().getDrawable(R.drawable.prof_icon);
        donorImage = ((BitmapDrawable)drawable).getBitmap();
        donorImageString =  getEncoded64ImageStringFromBitmap(donorImage);
        donorIV.setImageBitmap(donorImage);
    }

    private void CameraGalleryImageCapture() {

        View imageCaptureDialogView = getLayoutInflater().inflate(R.layout.alertdialog_camera_gallary,null,false);

        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(imageCaptureDialogView);
        dialog.show();

        ImageView camera,gallery;

        camera = imageCaptureDialogView.findViewById(R.id.getCameraImageId);
        gallery = imageCaptureDialogView.findViewById(R.id.getGalleryImageId);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAMERA_REQUEAT_CODE);
            }
        });


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST_CODE);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {

            if (requestCode == CAMERA_REQUEAT_CODE && resultCode == Activity.RESULT_OK) {
                donorImage = (Bitmap) data.getExtras().get("data");
                donorIV.setImageBitmap(donorImage);
                donorImageString = getEncoded64ImageStringFromBitmap(donorImage);
            } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

                Uri imgaeUri = data.getData();

                try {
                    InputStream imageStream = getContentResolver().openInputStream(imgaeUri);
                    donorImage = BitmapFactory.decodeStream(imageStream);
                    donorIV.setImageBitmap(donorImage);
                    donorImageString = getEncoded64ImageStringFromBitmap(donorImage);
                    //setImageVar.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (donorImage == null) {
                defaultImage();
            }

        }catch(Exception e)
        {

        }

    }
/*    private void Alertdialog(String title, String message) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(Creates_profile.this);
        dialog.setTitle(title);
        dialog.setMessage(message);

        //dialog.show();

        final AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                alertDialog.dismiss();
            }
        },1000);
    }*/
}
