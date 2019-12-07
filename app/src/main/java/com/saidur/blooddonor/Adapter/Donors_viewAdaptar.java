package com.saidur.blooddonor.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.saidur.blooddonor.Database.Queary;
import com.saidur.blooddonor.Model.Donors;
import com.saidur.blooddonor.R;
import com.saidur.blooddonor.View_activity.View_profileActivity;

import java.util.ArrayList;
import java.util.List;

public class Donors_viewAdaptar extends RecyclerView.Adapter<Donors_viewAdaptar.MyViewHolder> {

    private List<Donors> donorsList = new ArrayList<>();
    private Context context;

    public Donors_viewAdaptar(List<Donors> donorList, Context context) {
        this.donorsList = donorList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_model_design, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        final Donors donor = donorsList.get(i);

        myViewHolder.nameTV.setText(donor.getName());
        myViewHolder.bloodGroupTV.setText("("+donor.getBloodGroup()+")");
        myViewHolder.profileIV.setImageBitmap(StringToBitMap(donor.getImage()));
        myViewHolder.viewBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, View_profileActivity.class).putExtra("ID", donor.getId()));
            }
        });

        myViewHolder.deleteDonorIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PopupMenu popup = new PopupMenu(context, myViewHolder.deleteDonorIB);
                popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        //Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();


                        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        dialog.setTitle("Delete Message !!");
                        dialog.setMessage("Are you sure to delete it ?");

                        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Queary quary = new Queary(context);
                                if (isNetworkAvailable()) {

                                    quary.deleteData(donor.getId());

                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User");
                                    ref.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child(donor.getId()).removeValue();

                                } else {
                                    quary.offLineDelelteUser(donor.getId());

                                }
                                donorsList.remove(i);
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });

                        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        });

                        dialog.show();


                        return true;
                    }
                });

                popup.show();

                //donorList = quary.ge
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

    @Override
    public int getItemCount() {
        try {

            return donorsList.size();
        } catch (Exception e) {

        }
        return 0;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTV, bloodGroupTV;
        private Button viewBTN;
        private ImageView profileIV;
        private ImageButton deleteDonorIB;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.modelNameShowTVId);
            bloodGroupTV = itemView.findViewById(R.id.modelBloodShowTVId);
            profileIV = itemView.findViewById(R.id.modelProfileShowIvId);
            viewBTN = itemView.findViewById(R.id.modelViewProfileBTNId);
            deleteDonorIB = itemView.findViewById(R.id.donorDeleteIBId);

        }
    }

}
