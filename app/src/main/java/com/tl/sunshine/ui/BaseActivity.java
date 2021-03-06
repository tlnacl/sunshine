package com.tl.sunshine.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.tl.sunshine.R;
import com.tl.sunshine.ui.widgets.MultiSwipeRefreshLayout;
import com.tl.sunshine.utils.BusProvider;
import com.squareup.otto.Bus;

/**
 * Created by tlnacl on 30/12/14.
 */
public abstract class BaseActivity extends ActionBarActivity implements MultiSwipeRefreshLayout.CanChildScrollUpCallback {
    private ActionBar mActionBar;


    // SwipeRefreshLayout allows the user to swipe the screen down to trigger a manual refresh
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mProgressBarTopWhenActionBarShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        trySetupSwipeRefresh();
        updateSwipeRefreshProgressBarTop();
    }

    private void trySetupSwipeRefresh() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorScheme(
                    R.color.refresh_progress_1,
                    R.color.refresh_progress_2,
                    R.color.refresh_progress_3,
                    R.color.refresh_progress_4);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    requestDataRefresh();
                }
            });

            if (mSwipeRefreshLayout instanceof MultiSwipeRefreshLayout) {
                MultiSwipeRefreshLayout mswrl = (MultiSwipeRefreshLayout) mSwipeRefreshLayout;
                mswrl.setCanChildScrollUpCallback(this);
            }
        }
    }

    public void setSwipeRefreshEnabled(boolean enabled) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setEnabled(enabled);
        }
    }

    protected void requestDataRefresh() {
//        Account activeAccount = AccountUtils.getActiveAccount(this);
//        ContentResolver contentResolver = getContentResolver();
//        if (contentResolver.isSyncActive(activeAccount, ApplicationContract.CONTENT_AUTHORITY)) {
//            LOGD(TAG, "Ignoring manual sync request because a sync is already in progress.");
//            return;
//        }
//        mManualSyncRequest = true;
//        LOGD(TAG, "Requesting manual data refresh.");
    }

    protected void setProgressBarTopWhenActionBarShown(int progressBarTopWhenActionBarShown) {
        mProgressBarTopWhenActionBarShown = progressBarTopWhenActionBarShown;
        updateSwipeRefreshProgressBarTop();
    }

    private void updateSwipeRefreshProgressBarTop() {
        if (mSwipeRefreshLayout == null) {
            return;
        }

//        if (mActionBarShown) {
//            mSwipeRefreshLayout.setProgressBarTop(mProgressBarTopWhenActionBarShown);
//        } else {
//            mSwipeRefreshLayout.setProgressBarTop(0);
//        }
    }

    protected void onRefreshingStateChanged(boolean refreshing) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(refreshing);
        }
    }

    protected void enableDisableSwipeRefresh(boolean enable) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setEnabled(enable);
        }
    }

    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.nav_to_search){
            this.startActivity(new Intent(this, SearchActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    protected Bus getBus(){
        return  BusProvider.getInstance();
    }

    @Override protected void onResume() {
        super.onResume();

        // Register ourselves so that we can provide the initial value.
        getBus().register(this);
    }

    @Override protected void onPause() {
        super.onPause();

        // Always unregister when an object no longer should be on the bus.
        getBus().unregister(this);
    }
}
