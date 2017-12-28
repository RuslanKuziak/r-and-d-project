package co.techmagic.randd.presentation.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import co.techmagic.randd.data.network.NetworkManager;

/**
 * Created by ruslankuziak on 12/21/17.
 */

public class ConnectivityBroadcastReceiver extends BroadcastReceiver {

    public static ConnectivityReceiverListener connectivityReceiverListener;

    public ConnectivityBroadcastReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = NetworkManager.get().isNetworkAvailable();

        if (connectivityReceiverListener != null) {
            connectivityReceiverListener.onNetworkConnectionChanged(isConnected ? ConnectionStates.CONNECTED : ConnectionStates.NO_CONNECTION);
        }
    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(ConnectionStates connectionState);
    }

    public enum ConnectionStates {
        NO_CONNECTION,
        CONNECTED,
        CONNECTING,
        DISCONNECTING
    }
}