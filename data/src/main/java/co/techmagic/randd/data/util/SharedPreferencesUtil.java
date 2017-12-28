package co.techmagic.randd.data.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Created by ruslankuziak on 12/26/17.
 */

public class SharedPreferencesUtil {

    private static SharedPreferences prefs;

    private SharedPreferencesUtil() {}

    public static synchronized void init(@NonNull Context appContext) {
        if (prefs == null) {
            prefs = appContext.getSharedPreferences(SharedPreferencesKeys.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        }
    }

    private interface SharedPreferencesKeys {
        String SHARED_PREFS_NAME = "appPrefs";
    }
}