package co.techmagic.randd.presentation.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import co.techmagic.randd.data.network.NetworkManager;

/**
 * Created by ruslankuziak on 12/21/17.
 */

public class ConnectivityBroadcastReceiver extends BroadcastReceiver {

    private ConnectivityReceiverListener connectivityReceiverListener;

    public ConnectivityBroadcastReceiver() {
        super();
    }

    public void setConnectivityReceiverListener(ConnectivityReceiverListener connectivityReceiverListener) {
        this.connectivityReceiverListener = connectivityReceiverListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Current broadcast is registered as Sticky Broadcast, so we need to skip initial
        // We want to ignore the current state, just only to process state changes
        if (isInitialStickyBroadcast()) {
            return;
        }

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