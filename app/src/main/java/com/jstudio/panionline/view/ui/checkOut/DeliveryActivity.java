package com.jstudio.panionline.view.ui.checkOut;

import android.annotation.SuppressLint;
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
import com.jstudio.panionline.model.eventbus.SendTotalAmountEvent;
import com.jstudio.panionline.service.database.CartDataSource;
import com.jstudio.panionline.service.database.CartDatabase;
import com.jstudio.panionline.service.database.CartItem;
import com.jstudio.panionline.service.database.LocalCartDataSource;
import com.jstudio.panionline.utility.CommonMethods;
import com.jstudio.panionline.utility.constant.AppConstant;
import com.jstudio.panionline.utility.session.Preference_POSession;
import com.jstudio.panionline.view.base.BaseActivity;
import com.jstudio.panionline.view.ui.addAddress.AddAddressActivity;
import com.jstudio.panionline.view.ui.checkOut.adapter.DeliveryAddressAdapter;
import com.jstudio.panionline.view.ui.checkOut.adapter.OrderSummaryItemsAdapter;
import com.jstudio.panionline.viewmodel.AddressViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DeliveryActivity extends BaseActivity {
    private ActivityDeliveryBinding mBinding;
    private AddressViewModel addressViewModel;
    private List<AddressListResponse.DataBean> addressList = new ArrayList<>();
    private DeliveryAddressAdapter mAdapter;
    private OrderSummaryItemsAdapter oAdapter;
    private final String TAG = getClass().getSimpleName();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private CartDataSource cartDataSource;
    private List<CartItem> cartItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_delivery);
        setBackEnabled_Title(true, getString(R.string.checkout));

        addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);

        initUi();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @SuppressLint("SetTextI18n")
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getTotal(SendTotalAmountEvent sendTotalAmountEvent) {
       if(sendTotalAmountEvent!=null){
           mBinding.txtTotalVal.setText(getText(R.string.inr_symbol)
                   + String.valueOf(sendTotalAmountEvent.getTotalAmount()) + ".00");
       }
    }

    private void initUi() {
        cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(this).cartDAO());
        getAddressList();
        getAllItemsInCart();
        mAdapter = new DeliveryAddressAdapter(this, addressList);
        mBinding.rcAddress.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mBinding.rcAddress.setAdapter(mAdapter);

        oAdapter = new OrderSummaryItemsAdapter(this, cartItemList);
        mBinding.rcOrderSummary.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rcOrderSummary.setAdapter(oAdapter);

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
                    if (mAdapter.getTotalAddressCount() < 4) {
                        Intent intent = new Intent(this, AddAddressActivity.class);
                        startActivityForResult(intent, AppConstant.ADD_ADDRESS_ONACTIVITY_CODE);
                    } else {
                        CommonMethods.showSnackBar(mBinding.getRoot(), getString(R.string.add_new_address_validation));
                    }
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
        CommonMethods.showDialog(this);
        addressViewModel.getAddress(String.valueOf(Preference_POSession.getInstance(this).getUserId()));

        addressViewModel.getAddAddressListResponse().observe(this, addressListResponse -> {
            CommonMethods.dismissDialog();
            if (addressListResponse != null) {
                if (addressListResponse.isStatusCode()) {
                    addressList.clear();
                    addressList.addAll(addressListResponse.getData());
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * Method to get all the items in cart from local db
     */
    private void getAllItemsInCart() {
        compositeDisposable.add(cartDataSource.getCartItems(Preference_POSession.getInstance(this).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cartItems -> {
                    if (cartItems.isEmpty()) {
                        mBinding.rcAddress.setVisibility(View.INVISIBLE);
                        mBinding.txtNoAddressAvailable.setVisibility(View.VISIBLE);
                    } else {
                        mBinding.rcAddress.setVisibility(View.VISIBLE);
                        mBinding.txtNoAddressAvailable.setVisibility(View.INVISIBLE);
                        cartItemList.clear();
                        cartItemList.addAll(cartItems);
                        oAdapter.notifyDataSetChanged();
                    }
                }, throwable -> {
                    // Toast.makeText(this, "[GET CART]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                })

        );
    }
}
