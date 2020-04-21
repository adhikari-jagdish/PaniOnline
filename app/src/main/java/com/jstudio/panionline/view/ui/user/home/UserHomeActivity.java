package com.jstudio.panionline.view.ui.user.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.ActivityUserHomeBinding;
import com.jstudio.panionline.model.eventbus.SendCartItemsCountEvent;
import com.jstudio.panionline.utility.CommonMethods;
import com.jstudio.panionline.view.base.BaseActivity;
import com.jstudio.panionline.view.ui.common.SettingsFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class UserHomeActivity extends BaseActivity {
    private ActivityUserHomeBinding mBinding;
    final Fragment fragmentHome = new UserLocationFragment();
    final Fragment fragmentSettings = new SettingsFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragmentHome;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_home);

        setBackEnabled_Title(false, "", true);

        setUpBottomNav();

    }

    public static void startUserHomeActivity(Context context) {
        Intent intent = new Intent(context, UserHomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        CommonMethods.countItemsInCart(this, 100);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartItemsCount(SendCartItemsCountEvent itemsCountEvent) {
        setValuetoBadge(itemsCountEvent.getmCartItemsCount());
    }

    private void setUpBottomNav() {
        mBinding.navigation.setItemIconTintList(null);

        fm.beginTransaction().add(R.id.frame_container, fragmentHome, "1").commit();
        fm.beginTransaction().add(R.id.frame_container, fragmentSettings, "2").hide(fragmentSettings).commit();

        mOnNavigationItemSelectedListener
                = menuItem -> {
            switch (menuItem.getItemId()) {

                case R.id.nav_home:
                    fm.beginTransaction().hide(active).show(fragmentHome).commit();
                    active = fragmentHome;
                    return true;

                case R.id.nav_settings:
                    fm.beginTransaction().hide(active).show(fragmentSettings).commit();
                    active = fragmentSettings;
                    return true;
            }
            return false;
        };

        mBinding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


}
