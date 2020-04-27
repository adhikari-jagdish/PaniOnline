package com.jstudio.panionline.view.ui.checkOut;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.ActivityDeliveryBinding;
import com.jstudio.panionline.model.AddressListResponse;
import com.jstudio.panionline.utility.constant.AppConstant;
import com.jstudio.panionline.utility.session.Preference_POSession;
import com.jstudio.panionline.view.base.BaseActivity;
import com.jstudio.panionline.view.ui.addAddress.AddAddressActivity;
import com.jstudio.panionline.view.ui.checkOut.adapter.DeliveryAddressAdapter;
import com.jstudio.panionline.view.ui.checkOut.adapter.OrderSummaryItemsAdapter;
import com.jstudio.panionline.viewmodel.AddressViewModel;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends BaseActivity {
    private ActivityDeliveryBinding mBinding;
    private AddressViewModel addressViewModel;
    private List<AddressListResponse.DataBean> addressList = new ArrayList<>();
    private DeliveryAddressAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_delivery);
        setBackEnabled_Title(true, getString(R.string.checkout));

        addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);

        initUi();
    }

    private void initUi() {
        getAddressList();
        mAdapter = new DeliveryAddressAdapter(this, addressList);
        mBinding.rcAddress.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mBinding.rcAddress.setAdapter(mAdapter);

        mBinding.rcOrderSummary.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rcOrderSummary.setAdapter(new OrderSummaryItemsAdapter(this));

        mBinding.btnConfirm.setOnClickListener(this);
        mBinding.imgAddAddress.setOnClickListener(this);
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
        if (v != null) {
            switch (v.getId()) {
                case R.id.btn_confirm:
                    PaymentActivity.startPaymentActivity(this);
                    break;

                case R.id.img_add_address:
                    Intent intent = new Intent(this, AddAddressActivity.class);
                    startActivityForResult(intent, AppConstant.ADD_ADDRESS_ONACTIVITY_CODE);
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.ADD_ADDRESS_ONACTIVITY_CODE && resultCode == RESULT_OK) {
            getAddressList();
        }
    }

    private void getAddressList() {
        addressViewModel.getAddress(String.valueOf(Preference_POSession.getInstance(this).getUserId()));

        addressViewModel.getAddAddressListResponse().observe(this, addressListResponse -> {
            if (addressListResponse != null) {
                if (addressListResponse.isStatusCode()) {
                    addressList.clear();
                    addressList.addAll(addressListResponse.getData());
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
