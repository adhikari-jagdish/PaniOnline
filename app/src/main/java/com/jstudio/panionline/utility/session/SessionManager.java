package com.jstudio.panionline.utility.session;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SessionManager {
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    public static void initSessionManager(Context mContext) {
        pref = mContext.getSharedPreferences(PreferenceConstants.PREFS_NAME, MODE_PRIVATE);
        editor = pref.edit();
        editor.apply();
    }


    public static void setIsLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(PreferenceConstants.IS_USER_LOGGED_IN, isLoggedIn);
    }


    public static boolean getIsLoggedIn() {
        return pref.getBoolean(PreferenceConstants.IS_USER_LOGGED_IN, false);
    }
}
