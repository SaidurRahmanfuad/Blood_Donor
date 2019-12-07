package com.saidur.blooddonor.View_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.saidur.blooddonor.Add_activity.Add_donorActivity;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

public class Univarsal_Activity extends AppCompatActivity implements SwipeBackActivityBase {

    private SwipeBackActivityHelper swipeBackActivityHelper;
//Menu te pres korle jkhane jabe ta aikhane bole dite hobe
    public static Intent newIntent(Context context, String title) {
        Intent intent = new Intent(context, Add_donorActivity.class);
        intent.putExtra("title", title);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swipeBackActivityHelper = new SwipeBackActivityHelper(this);
        swipeBackActivityHelper.onActivityCreate();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getIntent().getStringExtra("title"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        swipeBackActivityHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && swipeBackActivityHelper != null)
            return swipeBackActivityHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return swipeBackActivityHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

}
