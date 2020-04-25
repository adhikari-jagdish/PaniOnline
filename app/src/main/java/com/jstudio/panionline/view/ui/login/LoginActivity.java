package com.jstudio.panionline.view.ui.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.ActivityLoginBinding;
import com.jstudio.panionline.view.base.BaseActivity;
import com.jstudio.panionline.view.ui.accountVerification.AccountVerificationActivity;
import com.jstudio.panionline.viewmodel.LoginViewModel;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding mBinding;
    private LoginViewModel loginVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginVM = ViewModelProviders.of(this).get(LoginViewModel.class);

        initClickListener();
    }


    /**
     * Starts the Login Activity Using Intent
     *
     * @param context context of the current activity
     */
    public static void startLoginActivity(Context context) {
        context.startActivity(createIntent(context));
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    /**
     * Handle Click events here
     */
    private void initClickListener() {
        mBinding.loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v != null) {
            switch (v.getId()) {
                case R.id.login_btn:
                    loginVM.callLogin(mBinding.contactEt.getText().toString());
                    loginVM.getLoginResponse().observe(this, loginResponse -> {
                        if (loginResponse != null) {
                            if (loginResponse.isStatusCode()) {
                                AccountVerificationActivity.startAccountVerificationActivity(this, mBinding.contactEt.getText().toString());
                            }
                        }
                    });
                    break;
            }
        }
    }
}
