package com.jstudio.panionline.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jstudio.panionline.view.base.BaseApplication;

public class Utils {

    private static final String TAG = "Utils";

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) BaseApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
