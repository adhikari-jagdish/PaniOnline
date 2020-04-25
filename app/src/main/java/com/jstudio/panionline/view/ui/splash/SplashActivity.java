package com.jstudio.panionline.view.ui.splash;

import android.os.Bundle;

import com.jstudio.panionline.R;
import com.jstudio.panionline.view.base.BaseActivity;
import com.jstudio.panionline.view.ui.user.home.UserHomeActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {
    TimerTask timerTask;
    private static final long SPLASH_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashIntent();
    }

    //method to set the timer for the splash screen
    private void splashIntent() {
        Timer timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //WelcomeScreen.startTutorialActivity(SplashActivity.this);
                UserHomeActivity.startUserHomeActivity(SplashActivity.this);
            }
        };
        timer.schedule(timerTask, SPLASH_DELAY);
    }
}
