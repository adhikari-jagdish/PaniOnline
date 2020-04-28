package com.jstudio.panionline.view.ui.cartItems;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.ActivityCartItemsBinding;
import com.jstudio.panionline.model.eventbus.CalculatePriceEvent;
import com.jstudio.panionline.model.eventbus.SendTotalAmountEvent;
import com.jstudio.panionline.service.database.CartDataSource;
import com.jstudio.panionline.service.database.CartDatabase;
import com.jstudio.panionline.service.database.CartItem;
import com.jstudio.panionline.service.database.LocalCartDataSource;
import com.jstudio.panionline.utility.session.Preference_POSession;
import com.jstudio.panionline.view.base.BaseActivity;
import com.jstudio.panionline.view.ui.cartItems.adapter.CartItemsAdapter;
import com.jstudio.panionline.view.ui.checkOut.DeliveryActivity;
import com.jstudio.panionline.view.ui.login.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CartItemsActivity extends BaseActivity {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private CartDataSource cartDataSource;
    private List<CartItem> cartItemList = new ArrayList<>();
    private CartItemsAdapter mAdapter;
    private ActivityCartItemsBinding mbinding;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbinding = DataBindingUtil.setContentView(this, R.layout.activity_cart_items);

        setBackEnabled_Title(true, getString(R.string.cart));
        init();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        getAllItemsInCart();
        getItemsTotal();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getItemTotal(CalculatePriceEvent calculatePriceEvent) {
        if (calculatePriceEvent != null) {
            getItemsTotal();
        }
    }

    public static void startCartItemsActivity(Context context) {
        Intent intent = new Intent(context, CartItemsActivity.class);
        context.startActivity(intent);
    }

    /**
     * Initialize the view components
     */
    private void initView() {

        //Init the recyclerview
        mbinding.cartListRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CartItemsAdapter(this, cartItemList);
        mbinding.cartListRv.setAdapter(mAdapter);

        mbinding.btnProceedCheckout.setOnClickListener(this);

        Log.d(TAG, "UserId+++===>" + Preference_POSession.getInstance(this).getUserId());
    }


    /**
     * Initialize the data components and function
     */
    private void init() {
        cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(this).cartDAO());
    }

    /**
     * Method to Get all Items in Cart
     */
    private void getAllItemsInCart() {
        compositeDisposable.add(cartDataSource.getCartItems(Preference_POSession.getInstance(this).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cartItems -> {
                    if (cartItems.isEmpty()) {
                        mbinding.clBottom.setVisibility(View.INVISIBLE);
                        mbinding.cartListRv.setVisibility(View.INVISIBLE);
                        mbinding.txtNoItemsInCart.setVisibility(View.VISIBLE);
                    } else {
                        //Toast.makeText(this, "[GET CART_ITEMS_CALLED]", Toast.LENGTH_SHORT).show();
                        cartItemList.clear();
                        cartItemList.addAll(cartItems);
                        mAdapter.notifyDataSetChanged();
                        mbinding.clBottom.setVisibility(View.VISIBLE);
                        mbinding.cartListRv.setVisibility(View.VISIBLE);
                        mbinding.txtNoItemsInCart.setVisibility(View.INVISIBLE);
                    }
                }, throwable -> {
                    // Toast.makeText(this, "[GET CART]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                })

        );
    }

    /**
     * Method to show the total calculated amount
     */
    @SuppressLint("SetTextI18n")
    private void getItemsTotal() {
        compositeDisposable.add(cartDataSource.sumPrice(Preference_POSession.getInstance(this).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sum -> {
                    mbinding.txtTotalVal.setText(getText(R.string.inr_symbol) + String.valueOf(sum) + ".00");
                    EventBus.getDefault().postSticky(new SendTotalAmountEvent(String.valueOf(sum)));
                }, throwable -> {
                    Log.d(TAG, "Sum_Error===>");
                })

        );

    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            super.onClick(v);
            switch (v.getId()) {
                case R.id.btn_proceed_checkout:
                    if (Preference_POSession.getInstance(this).getIsLoggedIn()) {
                        DeliveryActivity.startDeliveryActivity(this);
                    } else {
                        LoginActivity.startLoginActivity(this);
                    }
                    break;


            }
        }

    }
}
