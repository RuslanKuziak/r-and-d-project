package co.techmagic.randd.domain;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ruslankuziak on 12/22/17.
 */

public class NetworkManager implements NetworkListener {

    private static NetworkManager instance;
    private Context context;

    public static synchronized void init(Context context) {
        if (instance == null) {
            instance = new NetworkManager(context);
        }
    }

    private NetworkManager(Context context) {
        this.context = context;
    }

    public static NetworkManager get() {
        return instance;
    }

    @Override
    public boolean isNetworkAvailable() {
        if (context == null) {
            return false;
        }

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}