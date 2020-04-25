package com.jstudio.panionline.view.ui.accountVerification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.ActivityAccountVerificationBinding;
import com.jstudio.panionline.utility.CommonMethods;
import com.jstudio.panionline.utility.constant.AppConstant;
import com.jstudio.panionline.utility.session.SessionManager;
import com.jstudio.panionline.view.base.BaseActivity;
import com.jstudio.panionline.viewmodel.VerifyOtpViewModel;

public class AccountVerificationActivity extends BaseActivity {
    private ActivityAccountVerificationBinding mBinding;
    private VerifyOtpViewModel verifyOtpVM;
    private String username = "";
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_account_verification);
        setBackEnabled_Title(true, "");
        verifyOtpVM = ViewModelProviders.of(this).get(VerifyOtpViewModel.class);
        if (getIntent().getExtras() != null) {
            username = getIntent().getExtras().getString(AppConstant.USERNAME);
        }
        initClickListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SessionManager.initSessionManager(this);
    }

    /**
     * Starts the AccountVerification Activity Using Intent
     *
     * @param context context of the current activity
     */
    public static void startAccountVerificationActivity(Context context, String username) {
        context.startActivity(createIntent(context, username));
    }

    public static Intent createIntent(Context context, String username) {
        Intent intent = new Intent(context, AccountVerificationActivity.class);
        intent.putExtra(AppConstant.USERNAME, username);
        return intent;
    }

    private String concatOtp() {
        return mBinding.otpOne.getText().toString() + mBinding.otpTwo.getText().toString()
                + mBinding.otpThree.getText().toString() + mBinding.otpFour.getText().toString();
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
                    CommonMethods.showDialog(this);
                    verifyOtpVM.callVerifyOtp(username, concatOtp());
                    Log.d(TAG, "Username" + username);
                    verifyOtpVM.getVerifyOtpResponse().observe(this, verifyOtpResponse -> {
                        CommonMethods.dismissDialog();
                        if (verifyOtpResponse != null && verifyOtpResponse.isStatusCode()) {
                            SessionManager.setIsLoggedIn(true);
                            Log.d(TAG, "Logged++++");
                        } else {
                            Log.d(TAG, "Loggin Error++++");
                        }
                    });
                    break;
            }
        }
    }
}
