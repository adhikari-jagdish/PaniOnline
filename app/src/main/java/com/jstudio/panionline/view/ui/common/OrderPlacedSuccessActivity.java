package com.jstudio.panionline.view.ui.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.ActivityOrderPlacedSuccessBinding;
import com.jstudio.panionline.view.base.BaseActivity;
import com.jstudio.panionline.view.ui.home.UserHomeActivity;

public class OrderPlacedSuccessActivity extends BaseActivity {
    private ActivityOrderPlacedSuccessBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_placed_success);

        mBinding.btnOrderPlacedDone.setOnClickListener(this);
    }

    /**
     * Starts Order Success Using Intent
     *
     * @param context context of the current activity
     */
    public static void startOrderPlacedActivity(Context context) {
        context.startActivity(createIntent(context));
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, OrderPlacedSuccessActivity.class);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.btn_order_placed_done:
                    Intent i = new Intent(this, UserHomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    break;
            }
        }

    }
}
