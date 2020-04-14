package com.jstudio.panionline.view.base;

import android.app.Application;
import android.content.Context;

import com.jstudio.panionline.service.api.RetrofitClient;
import com.jstudio.panionline.utility.notification.NotificationCreator;

public class BaseApplication extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        //NotificationCreator.createNotificationChannel();
        //RetrofitClient.instance();
    }
    public static Context getContext(){
        return appContext;
    }
}
