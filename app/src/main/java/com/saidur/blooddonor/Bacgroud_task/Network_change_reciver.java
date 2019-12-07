package com.saidur.blooddonor.Bacgroud_task;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class Network_change_reciver extends BroadcastReceiver {
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // throw new UnsupportedOperationException("Not yet implemented");
        Log.d("stop", "Flag ");

        if (isNetworkAvailable(context)) {
            // Do something

            // Log.d("stop", "Flag No 1");

            ComponentName componentName = new ComponentName(context, Db_check.class);
            JobScheduler jobScheduler = (JobScheduler)context.getSystemService(JOB_SCHEDULER_SERVICE);
            JobInfo jobInfo = new JobInfo.Builder(10, componentName).setRequiredNetworkType(JobInfo.NETWORK_TYPE_NOT_ROAMING).setPersisted(true).build();
            jobScheduler.schedule(jobInfo);
            //  jobScheduler.schedule(builder.build());
        }

    }

    public boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
