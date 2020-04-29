package com.jstudio.panionline.view.ui.splash;

import android.os.Bundle;

import com.jstudio.panionline.R;
import com.jstudio.panionline.utility.CommonMethods;
import com.jstudio.panionline.utility.session.Preference_POSession;
import com.jstudio.panionline.view.base.BaseActivity;
import com.jstudio.panionline.view.ui.home.UserHomeActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {
    private static final long SPLASH_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        generateTempUserId();
        splashIntent();
    }

    //method to set the timer for the splash screen
    private void splashIntent() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //WelcomeScreen.startTutorialActivity(SplashActivity.this);
                UserHomeActivity.startUserHomeActivity(SplashActivity.this);
            }
        };
        timer.schedule(timerTask, SPLASH_DELAY);
    }

    /**
     * Method to generate random number, acts as temp userId
     * Unless user logs in and gets userid from api
     */
    private void generateTempUserId() {
        Preference_POSession poSession = Preference_POSession.getInstance(this);
        if (!poSession.getIsLoggedIn() && poSession.getUserId() == 0) {
            poSession.putUserId(CommonMethods.generateRandomNumber());
        }
    }
}
