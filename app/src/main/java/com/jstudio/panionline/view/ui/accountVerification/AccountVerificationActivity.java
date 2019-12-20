package com.jstudio.panionline.view.ui.accountVerification;

import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.ActivityAccountVerificationBinding;
import com.jstudio.panionline.view.base.BaseActivity;
import com.jstudio.panionline.view.ui.user.home.UserHomeActivity;

public class AccountVerificationActivity extends BaseActivity {
    private ActivityAccountVerificationBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_account_verification);
        setBackEnabled_Title(true, "");

        initClickListener();
    }

    /**
     * Starts the AccountVerification Activity Using Intent
     *
     * @param context context of the current activity
     */
    public static void startAccountVerificationActivity(Context context) {
        context.startActivity(createIntent(context));
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, AccountVerificationActivity.class);
    }

    /**
     * Init ClickListener for the Activity
     */
    private void initClickListener() {
        mBinding.verifyOtpTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v != null) {

            switch (v.getId()) {

                case R.id.verify_otp_tv:
                    UserHomeActivity.startUserHomeActivity(this);
                    break;
            }
        }
    }
}
