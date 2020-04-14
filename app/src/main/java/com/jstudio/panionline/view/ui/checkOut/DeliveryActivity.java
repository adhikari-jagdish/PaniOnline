package com.jstudio.panionline.view.ui.checkOut;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.ActivityDeliveryBinding;
import com.jstudio.panionline.view.base.BaseActivity;
import com.jstudio.panionline.view.ui.checkOut.adapter.DeliveryAddressAdapter;
import com.jstudio.panionline.view.ui.checkOut.adapter.OrderSummaryItemsAdapter;

public class DeliveryActivity extends BaseActivity {
    private ActivityDeliveryBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_delivery);
        setBackEnabled_Title(true, getString(R.string.checkout));

        initUi();
    }

    private void initUi() {
        mBinding.rcAddress.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mBinding.rcAddress.setAdapter(new DeliveryAddressAdapter(this));

        mBinding.rcOrderSummary.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rcOrderSummary.setAdapter(new OrderSummaryItemsAdapter(this));

        mBinding.btnConfirm.setOnClickListener(this);
    }

    /**
     * Starts DeliveryActivity Using Intent
     *
     * @param context context of the current activity
     */
    public static void startDeliveryActivity(Context context) {
        context.startActivity(createIntent(context));
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, DeliveryActivity.class);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v !=null){
            switch (v.getId()){
                case R.id.btn_confirm:
                    PaymentActivity.startPaymentActivity(this);
                    break;
            }
        }
    }
}
