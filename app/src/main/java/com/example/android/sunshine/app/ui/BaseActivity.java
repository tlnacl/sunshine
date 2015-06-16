package com.example.android.sunshine.app.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.network.NetworkException;
import com.example.android.sunshine.app.ui.widgets.MultiSwipeRefreshLayout;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by tlnacl on 30/12/14.
 */
public abstract class BaseActivity extends ActionBarActivity implements MultiSwipeRefreshLayout.CanChildScrollUpCallback, ErrorCallback {
    private ActionBar mActionBar;
    protected final CompositeSubscription mCompositeSubscription = new CompositeSubscription();

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

    @Override
    public void onApiError(@NonNull NetworkException exception) {
        String message = null;
//        switch (exception.getErrorKind()) {
//            case UNEXPECTED:
//            case NETWORK:
//                message = getString(R.string.network_not_connected);
//                break;
//            case HTTP:
//                message = exception.getMessage();
//                break;
//            default:
//                message = getString(R.string.network_server_side_problem);
//                break;
//        }
        onError(null, message);
    }

    @Override
    public void onError(@Nullable String title, @NonNull String message) {
        View rootView = findViewById(R.id.root_container);
        if(!TextUtils.isEmpty(title) || rootView == null) {
            new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        } else {
            Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
        }
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

    @Override
    protected void onDestroy() {
        mCompositeSubscription.unsubscribe();
        super.onDestroy();
    }
}
