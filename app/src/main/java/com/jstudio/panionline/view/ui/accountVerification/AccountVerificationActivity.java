package com.jstudio.panionline.view.ui.accountVerification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.ActivityAccountVerificationBinding;
import com.jstudio.panionline.model.eventbus.SendUserProfileDetails;
import com.jstudio.panionline.utility.CommonMethods;
import com.jstudio.panionline.utility.constant.AppConstant;
import com.jstudio.panionline.utility.session.Preference_POSession;
import com.jstudio.panionline.view.base.BaseActivity;
import com.jstudio.panionline.view.ui.checkOut.DeliveryActivity;
import com.jstudio.panionline.viewmodel.VerifyOtpViewModel;

import org.greenrobot.eventbus.EventBus;

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
        initOtpSys();
    }

    @Override
    protected void onStart() {
        super.onStart();
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
                    verifyOtpVM.getVerifyOtpResponse().observe(this, verifyOtpResponse -> {
                        CommonMethods.dismissDialog();
                        if (verifyOtpResponse != null && verifyOtpResponse.isStatusCode()) {
                            Preference_POSession poSession = Preference_POSession.getInstance(this);
                            poSession.putIsLoggedIn(true);
                            poSession.putName(verifyOtpResponse.getData().getName());
                            poSession.putImageUrl(verifyOtpResponse.getData().getImageUrl());
                            poSession.putUserId(verifyOtpResponse.getData().getUserId());
                            CommonMethods.updateUserIdInDb(this, poSession.getUserId(), verifyOtpResponse.getData().getUserId());
                            EventBus.getDefault().postSticky(new SendUserProfileDetails(verifyOtpResponse.getData().getName(), verifyOtpResponse.getData().getImageUrl(), true));
                            DeliveryActivity.startDeliveryActivity(this);
                        } else {
                            CommonMethods.showSnackBar(mBinding.getRoot(), verifyOtpResponse.getMessage());
                        }
                    });
                    break;
            }
        }
    }

    /**
     * Method to fill the otp numbers inf fields
     */
    private void initOtpSys() {
        mBinding.otpOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mBinding.otpOne.getText().toString().length() == 1) {
                    mBinding.otpTwo.requestFocus();
                }
            }
        });

        mBinding.otpTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mBinding.otpTwo.getText().toString().length() == 0) {
                    mBinding.otpOne.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mBinding.otpTwo.getText().toString().length() == 1) {
                    mBinding.otpThree.requestFocus();
                }
            }
        });

        mBinding.otpThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mBinding.otpThree.getText().toString().length() == 0) {
                    mBinding.otpTwo.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mBinding.otpThree.getText().toString().length() == 1) {
                    mBinding.otpFour.requestFocus();
                }
            }
        });

        mBinding.otpFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mBinding.otpFour.getText().toString().length() == 0) {
                    mBinding.otpThree.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


}
