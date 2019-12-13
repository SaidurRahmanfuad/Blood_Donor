package com.saidur.blooddonor;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.saidur.blooddonor.Others.Registration_Activity;
import com.saidur.blooddonor.View_activity.Homes_main;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private VideoView srVideoview;
    MediaPlayer srMediaPlayer;
    int srCurrentVideoPosition;

FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;
    Button goto_register;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //checkisloggedin();
        goto_register = findViewById(R.id.btn_goto_reg);

        goto_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registration_Activity.class);
                startActivity(intent);
                finish();
            }
        });

      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
               // WindowManager.LayoutParams.FLAG_FULLSCREEN);
       /* new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                //getResources();


                new Handler().postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {

                        Intent intent = new Intent(MainActivity.this, Registration_Activity.class);
                        startActivity(intent);
                        finish();
                    }

                },1500);
                CoordinatorLayout coordinatorLayout = findViewById(R.id.layout);
              coordinatorLayout.setBackgroundResource(R.drawable.splash_screen_back1);
            }

        }, 1500);*/
           timer =new Timer();
           timer.schedule(new TimerTask() {
               @Override
               public void run() {
                   Intent intent = new Intent(MainActivity.this, Registration_Activity.class);
                   startActivity(intent);
                   finish();
               }
           },10000);


        srVideoview=findViewById(R.id.sr_VideoView);

        Uri uri = Uri.parse("android.resource://com.saidur.blooddonor/"+ R.raw.dnvg);
        srVideoview.setVideoURI(uri);
        srVideoview.start();

        srVideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                srMediaPlayer =mp;
                srMediaPlayer.setLooping(true);

                if(srCurrentVideoPosition !=0)
                {
                    srMediaPlayer.seekTo(srCurrentVideoPosition);
                    srMediaPlayer.start();
                }
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        // Capture the current video position and pause the video.
        srCurrentVideoPosition = srMediaPlayer.getCurrentPosition();
        srVideoview.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restart the video when resuming the Activity
        srVideoview.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // When the Activity is destroyed, release our MediaPlayer and set it to null.
        srMediaPlayer.release();
        srMediaPlayer = null;
    }
}