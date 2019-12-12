package com.saidur.blooddonor.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.saidur.blooddonor.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class firebase_donors_viewholder extends RecyclerView.ViewHolder {
    public TextView donor_name,donor_blood_group;
    public CircleImageView donor_image;
    public firebase_donors_viewholder(@NonNull View itemView) {
        super(itemView);

        donor_name=itemView.findViewById(R.id.modelNameShowTVId);
        donor_blood_group=itemView.findViewById(R.id.modelBloodShowTVId);
        donor_image=itemView.findViewById(R.id.modelProfileShowIvId);
    }

}
