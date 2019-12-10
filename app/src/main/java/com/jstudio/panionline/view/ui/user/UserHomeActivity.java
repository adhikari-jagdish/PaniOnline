package com.jstudio.panionline.view.ui.user;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
        context.startActivity(intent);
    }


    private void setUpBottomNav(){
        bottomTab = new BottomTab().addFragmentActivity(this).addContainerId(R.id.frame_container)
                .addFragment(new UserLocationFragment())
                .build();
        bottomTab.show(1);
    }

}
