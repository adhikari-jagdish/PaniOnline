package com.jstudio.panionline.view.ui.checkOut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.ActivityPaymentBinding;
import com.jstudio.panionline.view.base.BaseActivity;

public class PaymentActivity extends BaseActivity {
    ActivityPaymentBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_payment);

        setBackEnabled_Title(true, getString(R.string.checkout));
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
}
