package com.jstudio.panionline.view.ui.user.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.ActivityUserHomeBinding;
import com.jstudio.panionline.service.database.CartDataSource;
import com.jstudio.panionline.service.database.CartDatabase;
import com.jstudio.panionline.service.database.LocalCartDataSource;
import com.jstudio.panionline.utility.uiUtils.BottomTab;
import com.jstudio.panionline.view.base.BaseActivity;
import com.jstudio.panionline.view.ui.common.SettingsFragment;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserHomeActivity extends BaseActivity {
    private ActivityUserHomeBinding mBinding;
    private BottomTab bottomTab;
    final Fragment fragmentHome = new UserLocationFragment();
    final Fragment fragmentSettings = new SettingsFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragmentHome;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    private CartDataSource cartDataSource;
    private final String TAG = getClass().getSimpleName();
    private BadgeDrawable badgeDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_home);
        setUpBottomNav();
    }

    public static void startUserHomeActivity(Context context) {
        Intent intent = new Intent(context, UserHomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }


    private void setUpBottomNav() {
        mBinding.navigation.setItemIconTintList(null);

        fm.beginTransaction().add(R.id.frame_container, fragmentSettings, "2").hide(fragmentSettings).commit();
        fm.beginTransaction().add(R.id.frame_container, fragmentHome, "1").commit();

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
        badgeDrawable = mBinding.navigation.getOrCreateBadge(R.id.nav_cart);
        badgeDrawable.setVisible(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //get the number of items added to cart and show in badge
        cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(this).cartDAO());

        cartDataSource.countCart(100)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.d(TAG, "INteger====>" + integer);
                        badgeDrawable.setNumber(integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(UserHomeActivity.this, "[ADD CART]" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
