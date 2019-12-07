package com.saidur.blooddonor.View_activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.saidur.blooddonor.Add_activity.Add_donorActivity;
import com.saidur.blooddonor.Available_activity.Available_donorActivity;
import com.saidur.blooddonor.Others.About_Activity;
import com.saidur.blooddonor.Others.Registration_Activity;
import com.saidur.blooddonor.R;
import com.github.mzule.fantasyslide.SideBar;
import com.github.mzule.fantasyslide.SimpleFantasyListener;
import com.github.mzule.fantasyslide.Transformer;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class View_Profile_main extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__profile_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final DrawerArrowDrawable indicator = new DrawerArrowDrawable(this);
        indicator.setColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(indicator);

        //setTransformer();
         //setListener();

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
    }

    //-->setListener , menu click korle tar nam ki and kon position a ase ta dekhabe
  /*private void setListener() {
        final TextView tipView = (TextView) findViewById(R.id.tipView);
        SideBar leftSideBar = (SideBar) findViewById(R.id.leftSideBar);
        leftSideBar.setFantasyListener(new SimpleFantasyListener() {
            @Override
            public boolean onHover(@Nullable View view, int index) {
                tipView.setVisibility(View.VISIBLE);
                if (view instanceof TextView) {
                    tipView.setText(String.format("%s at %d", ((TextView) view).getText().toString(), index));
                } else if (view != null && view.getId() == R.id.userInfo) {
                    tipView.setText(String.format("Personal at %d", index));
                } else {
                    tipView.setText(null);
                }
                return false;

            }

            @Override
            public boolean onSelect(View view, int index) {
                tipView.setVisibility(View.INVISIBLE);

                Toast.makeText(View_Profile_main.this, String.format("%d", index), Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onCancel() {
                tipView.setVisibility(View.INVISIBLE);
            }
        });
    }
*/
/*    private void setTransformer() {
        final float spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        SideBar rightSideBar = (SideBar) findViewById(R.id.rightSideBar);
        rightSideBar.setTransformer(new Transformer() {
            private View lastHoverView;

            @Override
            public void apply(ViewGroup sideBar, View itemView, float touchY, float slideOffset, boolean isLeft) {
                boolean hovered = itemView.isPressed();
                if (hovered && lastHoverView != itemView) {
                    animateIn(itemView);
                    animateOut(lastHoverView);
                    lastHoverView = itemView;
                }
            }

            private void animateOut(View view) {
                if (view == null) {
                    return;
                }
                ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", -spacing, 0);
                translationX.setDuration(200);
                translationX.start();
            }

            private void animateIn(View view) {
                ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", 0, -spacing);
                translationX.setDuration(200);
                translationX.start();
            }
        });
    }*/

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
                Intent i = new Intent(View_Profile_main.this,Available_donorActivity.class);
               startActivity(i);
            } if(title.startsWith("Profile")){
                Intent i = new Intent(View_Profile_main.this, Add_donorActivity.class);
                startActivity(i);
            }if(title.startsWith("About Us")){
                Intent i = new Intent(View_Profile_main.this, About_Activity.class);
                startActivity(i);

            }
            if(title.startsWith("Logout")){
                finish();
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(View_Profile_main.this, Registration_Activity.class);
                startActivity(i);
                finish();
            }
        } else if (view.getId() == R.id.userInfo) {
            startActivity(Univarsal_Activity.newIntent(this, "Personal"));
        }
    }
}
