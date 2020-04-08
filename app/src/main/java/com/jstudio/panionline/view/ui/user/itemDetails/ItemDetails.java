package com.jstudio.panionline.view.ui.user.itemDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.ActivityItemDetailsBinding;
import com.jstudio.panionline.view.base.BaseActivity;
import com.jstudio.panionline.view.ui.accountVerification.AccountVerificationActivity;
import com.jstudio.panionline.view.ui.checkOut.DeliveryActivity;

public class ItemDetails extends BaseActivity {
    private ActivityItemDetailsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_item_details);
       setBackEnabled_Title(true, "Bisleri");

       mBinding.imgLike.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DeliveryActivity.startDeliveryActivity(ItemDetails.this);
           }
       });
    }

    /**
     * Starts ItemDetails Activity Using Intent
     *
     * @param context context of the current activity
     */
    public static void startItemDetailsActivity(Context context) {
        context.startActivity(createIntent(context));
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, ItemDetails.class);
    }

}
