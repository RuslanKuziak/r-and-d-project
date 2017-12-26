package co.techmagic.randd.presentation.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import co.techmagic.randd.presentation.RandDApplication;
import co.techmagic.randd.presentation.broadcast.ConnectivityBroadcastReceiver;

/**
 * Created by ruslankuziak on 12/20/17.
 */

public abstract class BaseActivity<VIEWMODEL> extends AppCompatActivity implements ConnectivityBroadcastReceiver.ConnectivityReceiverListener {

    protected abstract VIEWMODEL initViewModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RandDApplication.getInstance().setConnectivityListener(this);
        initViewModel();
    }

    @Override
    public void onNetworkConnectionChanged(ConnectivityBroadcastReceiver.ConnectionStates connectionState) {

    }

    protected void showSnackMessage(@NonNull View rootView, @NonNull String message, int color) {
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }
}