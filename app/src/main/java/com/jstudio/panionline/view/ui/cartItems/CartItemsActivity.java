package com.jstudio.panionline.view.ui.cartItems;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.ActivityCartItemsBinding;
import com.jstudio.panionline.service.database.CartDataSource;
import com.jstudio.panionline.service.database.CartDatabase;
import com.jstudio.panionline.service.database.CartItem;
import com.jstudio.panionline.service.database.LocalCartDataSource;
import com.jstudio.panionline.view.base.BaseActivity;
import com.jstudio.panionline.view.ui.cartItems.adapter.CartItemsAdapter;

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
        getAllItemsInCart();
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
    }


    /**
     * Initialize the data components and functionalities
     */
    private void init() {
        cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(this).cartDAO());
    }

    /**
     * Method to Get all Items in Cart
     */
    private void getAllItemsInCart() {
        compositeDisposable.add(cartDataSource.getCartItems(100)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cartItems -> {
                    if (cartItems.isEmpty()) {
                        mbinding.clBottom.setVisibility(View.INVISIBLE);
                        mbinding.cartListRv.setVisibility(View.INVISIBLE);
                        mbinding.txtNoItemsInCart.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(this, "[GET CART_ITEMS_CALLED]", Toast.LENGTH_SHORT).show();
                        cartItemList.clear();
                        cartItemList.addAll(cartItems);
                        mAdapter.notifyDataSetChanged();
                        mbinding.clBottom.setVisibility(View.VISIBLE);
                        mbinding.cartListRv.setVisibility(View.VISIBLE);
                        mbinding.txtNoItemsInCart.setVisibility(View.INVISIBLE);
                    }
                }, throwable -> {
                    Toast.makeText(this, "[GET CART]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                })

        );
    }
}
