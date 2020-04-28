package com.jstudio.panionline.view.ui.checkOut;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.databinding.DataBindingUtil;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.ActivityPaymentBinding;
import com.jstudio.panionline.utility.constant.AppConstant;
import com.jstudio.panionline.view.base.BaseActivity;

public class PaymentActivity extends BaseActivity {
    ActivityPaymentBinding mBinding;
    private String paymentMode = "";
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_payment);

        setBackEnabled_Title(true, getString(R.string.checkout));
        init();
    }


    /**
     * Starts PaymentActivity Using Intent
     *
     * @param context context of the current activity
     */
    public static void startPaymentActivity(Context context) {
        context.startActivity(createIntent(context));
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, PaymentActivity.class);
    }

    private void init() {
        mBinding.rbGroup.SetOnCheckedChangeListener((rg, nCheckedId) -> {
            if (nCheckedId == AppConstant.UPI_RB_ID) {
                paymentMode = getString(R.string.wallet_upi);
            } else if (nCheckedId == AppConstant.COD_RB_ID) {
                paymentMode = getString(R.string.cash_on_delivery);
            }
        });
    }
}
