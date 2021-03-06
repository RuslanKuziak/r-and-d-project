package co.techmagic.randd.presentation.ui.base;

import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import co.techmagic.randd.R;
import co.techmagic.randd.presentation.broadcast.ConnectivityBroadcastReceiver;

/**
 * Created by ruslankuziak on 12/20/17.
 */

public abstract class BaseActivity<VIEWMODEL> extends AppCompatActivity implements ConnectivityBroadcastReceiver.ConnectivityReceiverListener {

    protected abstract VIEWMODEL initViewModel();

    protected abstract boolean withToolbar();

    private ConnectivityBroadcastReceiver receiver = new ConnectivityBroadcastReceiver();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiver.setConnectivityReceiverListener(this);
        initViewModel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterConnectivityBroadcastReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerConnectivityBroadcastReceiver();
    }

    public void setContentViewIntoRoot(int layoutResID) {
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_base, null);
        inflater.inflate(layoutResID, rootView, true);
        FrameLayout activityContainer = rootView.findViewById(R.id.base_container);
        inflater.inflate(layoutResID, activityContainer, true);
        super.setContentView(rootView);

        Toolbar toolbar = getToolbar();

        if (toolbar != null) {
            if (withToolbar()) {
                setSupportActionBar(toolbar);
                toolbar.setVisibility(View.VISIBLE);
            } else {
                toolbar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onNetworkConnectionChanged(ConnectivityBroadcastReceiver.ConnectionStates connectionState) {
        handleConnectivityStates(connectionState);
    }

    protected Toolbar getToolbar() {
        return findViewById(R.id.toolbar_base);
    }

    protected void showSnackMessage(@NonNull View rootView, @NonNull String message, int color) {
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    protected void showSnackMessage(@NonNull View rootView, @NonNull String message) {
        showSnackMessage(rootView, message, Color.WHITE);
    }

    private void handleConnectivityStates(ConnectivityBroadcastReceiver.ConnectionStates connectionState) {
        switch (connectionState) {
            case CONNECTED:
                showSnackMessage(findViewById(android.R.id.content), "Connected to the network", Color.WHITE);
                break;

            case NO_CONNECTION:
                showSnackMessage(findViewById(android.R.id.content), "Connection error", Color.RED);
                break;
        }
    }

    private void registerConnectivityBroadcastReceiver() {
        registerReceiver(receiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    private void unregisterConnectivityBroadcastReceiver() {
        unregisterReceiver(receiver);
    }
}