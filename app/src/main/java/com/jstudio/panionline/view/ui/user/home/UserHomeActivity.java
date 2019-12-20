package com.jstudio.panionline.view.ui.user.home;

import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.ActivityUserHomeBinding;
import com.jstudio.panionline.utility.uiUtils.BottomTab;
import com.jstudio.panionline.view.base.BaseActivity;

public class UserHomeActivity extends BaseActivity {
    private ActivityUserHomeBinding mBinding;
    private BottomTab bottomTab;

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


    private void setUpBottomNav(){
        mBinding.navigation.setItemIconTintList(null);
        bottomTab = new BottomTab().addFragmentActivity(this).addContainerId(R.id.frame_container)
                .addFragment(new UserLocationFragment())
                .build();
        bottomTab.show(1);
    }

}
