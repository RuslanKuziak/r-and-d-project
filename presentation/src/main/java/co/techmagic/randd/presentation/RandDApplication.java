package co.techmagic.randd.presentation;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import co.techmagic.randd.data.network.client.ApiClient;
import co.techmagic.randd.data.network.manager.NetworkManager;
import co.techmagic.randd.presentation.broadcast.ConnectivityBroadcastReceiver;

/**
 * Created by ruslankuziak on 12/20/17.
 */

public class RandDApplication extends Application {

    private static RandDApplication instance;
    private int activeActivitiesCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        NetworkManager.init(this);
        ApiClient.init();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activeActivitiesCount++;
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activeActivitiesCount--;
                if (activeActivitiesCount == 0 && !activity.isChangingConfigurations()) {
                    activeActivitiesCount = 0;
                    ApiClient.release();
                }
            }
        });
    }

    public static RandDApplication getInstance() {
        return instance;
    }

    public void setConnectivityListener(ConnectivityBroadcastReceiver.ConnectivityReceiverListener connectivityListener) {
        ConnectivityBroadcastReceiver.connectivityReceiverListener = connectivityListener;
    }
}