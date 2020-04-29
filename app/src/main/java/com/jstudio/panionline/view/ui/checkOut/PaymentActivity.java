package com.jstudio.panionline.view.ui.checkOut;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.ActivityPaymentBinding;
import com.jstudio.panionline.model.AddOrderRequest;
import com.jstudio.panionline.model.AddTransactionRequest;
import com.jstudio.panionline.service.database.CartItem;
import com.jstudio.panionline.utility.CommonMethods;
import com.jstudio.panionline.utility.constant.AppConstant;
import com.jstudio.panionline.utility.session.Preference_POSession;
import com.jstudio.panionline.view.base.BaseActivity;
import com.jstudio.panionline.view.ui.common.OrderPlacedSuccessActivity;
import com.jstudio.panionline.viewmodel.OrderViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.jstudio.panionline.utility.constant.AppConstant.GOOGLE_PAY_MERCHANT_NAME;
import static com.jstudio.panionline.utility.constant.AppConstant.GOOGLE_PAY_REQUEST_CODE;
import static com.jstudio.panionline.utility.constant.AppConstant.GOOGLE_PAY_VPA;

public class PaymentActivity extends BaseActivity {
    ActivityPaymentBinding mBinding;
    private String paymentMode = "";
    private String totalAmount = "";
    private String deliveryAddress = "";
    private List<CartItem> cartItemList = new ArrayList<>();
    private List<AddOrderRequest.CartItemsDetails> orderItemList = new ArrayList<>();
    private final String TAG = getClass().getSimpleName();
    private OrderViewModel orderViewModel;
    private AddOrderRequest request;
    private final int UPI_PAYMENT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        if (getIntent().getExtras() != null) {
            totalAmount = getIntent().getExtras().getString(AppConstant.TOTAL_AMOUNT);
            deliveryAddress = getIntent().getExtras().getString(AppConstant.DELIVERY_ADDRESS);
            cartItemList = getIntent().getExtras().getParcelableArrayList(AppConstant.ORDER_LIST);
        }
        setBackEnabled_Title(true, getString(R.string.checkout));
        init();
    }


    /**
     * Starts PaymentActivity Using Intent
     *
     * @param context context of the current activity
     */
    public static void startPaymentActivity(Context context, String deliveryAddress, String totalAmount, List<CartItem> orderList) {
        context.startActivity(createIntent(context, deliveryAddress, totalAmount, orderList));
    }

    public static Intent createIntent(Context context, String deliveryAddress, String totalAmount, List<CartItem> orderList) {
        Intent intent = new Intent(context, PaymentActivity.class);
        intent.putExtra(AppConstant.DELIVERY_ADDRESS, deliveryAddress);
        intent.putExtra(AppConstant.TOTAL_AMOUNT, totalAmount);
        intent.putParcelableArrayListExtra(AppConstant.ORDER_LIST, (ArrayList<? extends Parcelable>) orderList);
        return intent;

    }

    @SuppressLint("SetTextI18n")
    private void init() {
        mBinding.btnProceedPayment.setOnClickListener(this);
        mBinding.txtTotalVal.setText(getText(R.string.inr_symbol) + totalAmount + ".00");

        mBinding.rbGroup.SetOnCheckedChangeListener((rg, nCheckedId) -> {
            Log.d(TAG, "CheckID===>" + nCheckedId);
            paymentMode = getString(R.string.wallet_upi);
            /*if (nCheckedId == AppConstant.UPI_RB_ID) {
                paymentMode = getString(R.string.wallet_upi);
                Log.d(TAG, "PaymentMode===>" + paymentMode);
            } else if (nCheckedId == AppConstant.COD_RB_ID) {
                paymentMode = getString(R.string.cash_on_delivery);
                Log.d(TAG, "PaymentMode===>" + paymentMode);
            }*/
        });

        //Iterate CartItemList and get only required field
        AddOrderRequest.CartItemsDetails details = new AddOrderRequest.CartItemsDetails();
        orderItemList.clear();
        for (CartItem cart : cartItemList) {
            details.setProductId(cart.getProductId());
            details.setProductName(cart.getProductName());
            details.setProductQuantity(cart.getProductQuantity());
            orderItemList.add(details);
        }
    }

    private void setOrderRequest() {
        request = new AddOrderRequest();
        Preference_POSession poSession = Preference_POSession.getInstance(this);
        request.setClientId(poSession.getUserId());
        request.setClientName(poSession.getName());
        request.setDeliveryAddress(deliveryAddress);
        request.setOrderDate(CommonMethods.getCurrentDate());
        request.setPaymentMode(paymentMode);
        request.setTotalAmount(Integer.parseInt(totalAmount));
        request.setCartItems(orderItemList);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            super.onClick(v);
            switch (v.getId()) {
                case R.id.btn_proceed_payment:
                    setOrderRequest();
                    Log.d(TAG, "PaymentMode===>" + paymentMode);
                    if (paymentMode.equals(getString(R.string.cash_on_delivery))) {
                        callAddOrderApi();
                    } else {
                        callGooglePayPayment();
                    }
                    break;
            }
        }
    }

    /**
     * Method to call the place order api
     */
    private void callAddOrderApi() {
        CommonMethods.showDialog(this);
        orderViewModel.createOrder(request);
        orderViewModel.getCreateOrderResponse().observe(this, addOrderResponse -> {
            CommonMethods.dismissDialog();
            if (addOrderResponse != null) {
                if (addOrderResponse.isStatusCode()) {
                    callTransactionSuccessApi(addOrderResponse.getData());
                } else {
                    CommonMethods.showSnackBar(mBinding.getRoot(), addOrderResponse.getMessage());
                }
            }
        });
    }

    /**
     * Transaction Success after order is placed
     *
     * @param orderId Order Id is generated after new order is sucessfully placed
     */
    private void callTransactionSuccessApi(int orderId) {
        AddTransactionRequest request = new AddTransactionRequest();
        Preference_POSession poSession = Preference_POSession.getInstance(this);
        request.setClientId(poSession.getUserId());
        request.setClientName(poSession.getName());
        request.setOrderId(orderId);
        request.setPaymentMode(paymentMode);
        request.setTotalAmount(Integer.parseInt(totalAmount));
        request.setStatus(getString(R.string.delivery_status_pending));
        request.setTransactionDate(CommonMethods.getCurrentDate());

        orderViewModel.saveSuccessTransaction(request);
        orderViewModel.getTransactionResponse().observe(this, commonResponse -> {
            CommonMethods.dismissDialog();
            if (commonResponse != null) {
                if (commonResponse.isStatusCode()) {
                    OrderPlacedSuccessActivity.startOrderPlacedActivity(this);
                } else {
                    CommonMethods.showSnackBar(mBinding.getRoot(), commonResponse.getMessage());
                }
            }
        });
    }

    private void callGooglePayPayment() {
        Uri uri =
                new Uri.Builder()
                        .scheme("upi")
                        .authority("pay")
                        .appendQueryParameter("pa", GOOGLE_PAY_VPA)
                        .appendQueryParameter("pn", GOOGLE_PAY_MERCHANT_NAME)
                        .appendQueryParameter("tn", "PaniOnline Order Payment")
                        .appendQueryParameter("am", "1")
                        .appendQueryParameter("cu", "INR")
                        .build();
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);
        upiPayIntent.setPackage(AppConstant.GOOGLE_PAY_PACKAGE_NAME);
        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
        if (null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, GOOGLE_PAY_REQUEST_CODE);
        } else {
            CommonMethods.showSnackBar(mBinding.getRoot(), getString(R.string.no_upi_app_found));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_PAY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
               Log.d(TAG, "Response Data++++"+data);

            } else {
                ArrayList<String> dataList = new ArrayList<>();
                dataList.add("nothing");

            }
        } else {
            ArrayList<String> dataList = new ArrayList<>();
            dataList.add("nothing");

        }
    }


}
