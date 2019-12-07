package com.saidur.blooddonor.Bacgroud_task;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saidur.blooddonor.Database.Queary;
import com.saidur.blooddonor.Model.Donors;
import com.saidur.blooddonor.Model.Donors_online;

import java.util.ArrayList;
import java.util.List;
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Db_check extends JobService {
    private List<Donors_online> firebaseId = new ArrayList<>();
    private List<String> sqliteId = new ArrayList<>();
    private Queary queary = null;


    private DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("User");

    @Override
    public boolean onStartJob(JobParameters params) {



        FirebaseAuth auth = FirebaseAuth.getInstance();
        final String id = auth.getCurrentUser().getPhoneNumber();
        try {


            queary = new Queary(this);

            if (id != null) {

                Log.d("stop", "save ");

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("User");

                databaseReference.child(id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        firebaseId.clear();

                        if (dataSnapshot != null) {

                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                Log.d("stop", "firebase list test ");

                                try {

                                    Donors_online donorOnline = dataSnapshot1.getValue(Donors_online.class);
                                    firebaseId.add(donorOnline);

                                    Log.d("stop", "firebase list " + donorOnline);

                                } catch (Exception e) {

                                }
                            }

                        }

                        sqliteId = queary.getUserSize();

                        int update = 0;

                        //Log.d("stop", "size of list " + firebaseId.size() + " " + sqliteId.size());
                        List<String> deleteOnlineData = queary.getOffLineDeleteId();

                        for (int i = 0; i < deleteOnlineData.size(); i++) {
                            queary.deleteData(deleteOnlineData.get(i));

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User");
                            ref.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child(deleteOnlineData.get(i)).removeValue();

                        }

                        ///if()


                        for (int i = 0; i < sqliteId.size(); i++) {

                            if (0 == firebaseId.size()) {
                                updateCode(i, id);

                            } else {

                                for (int j = 0; j < firebaseId.size(); j++) {
                                    if (sqliteId.get(i).equals(firebaseId.get(j).getId())) {
                                        //  unicId[0] = firebaseId.get(j).getId();
                                        update = 1;
                                    }

                                }

                                Log.d("stop", " update " + update);

                                if (update != 1) {
                                    updateCode(i, id);
                                } else {
                                    update = 0;
                                }
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            Log.d("stop", "last");

        }catch (Exception e)
        {

        }
        return false;
    }

    private void updateCode(int i, String id) {
        List<Donors> allInfo = queary.getUser(sqliteId.get(i));
        Donors_online donorOnline = new Donors_online(allInfo.get(0).getId(), allInfo.get(0).getName(), allInfo.get(0).getAge(), allInfo.get(0).getImage(), allInfo.get(0).getBloodGroup(), allInfo.get(0).getCountractNumber(), allInfo.get(0).getLastDonationDate(), allInfo.get(0).getAddress());
        //String unicId = databaseReference1.push().getKey();

        databaseReference1.child(id).child(allInfo.get(0).getId()).setValue(donorOnline).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Toast.makeText(getApplicationContext(), "Added to the Firebase database", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        //Log.d("stop", "stop");

        return false;
    }
}
