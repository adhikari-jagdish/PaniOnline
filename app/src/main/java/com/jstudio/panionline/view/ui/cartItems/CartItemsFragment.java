package com.jstudio.panionline.view.ui.cartItems;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.FragmentCartItemsBinding;
import com.jstudio.panionline.model.eventbus.CalculatePriceEvent;
import com.jstudio.panionline.service.database.CartDataSource;
import com.jstudio.panionline.service.database.CartDatabase;
import com.jstudio.panionline.service.database.CartItem;
import com.jstudio.panionline.service.database.LocalCartDataSource;
import com.jstudio.panionline.view.base.BaseFragment;
import com.jstudio.panionline.view.ui.cartItems.adapter.CartItemsAdapter;
import com.jstudio.panionline.view.ui.checkOut.DeliveryActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class CartItemsFragment extends BaseFragment {
    private FragmentCartItemsBinding mbinding;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private CartDataSource cartDataSource;
    private List<CartItem> cartItemList = new ArrayList<>();
    private CartItemsAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mbinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart_items, container, false);
        return mbinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();
        initView();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllItemsInCart();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void calculatePrice(CalculatePriceEvent event) {
        if (event != null) {
            //calculateTotalPrice();
        }
    }

    /**
     * Initialize the view components
     */
    private void initView() {

        //Init the recyclerview
        mbinding.cartListRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CartItemsAdapter(getActivity(), cartItemList);
        mbinding.cartListRv.setAdapter(mAdapter);

        mbinding.btnProceedCheckout.setOnClickListener(this);
    }


    /**
     * Initialize the data components and functionalities
     */
    private void init() {
        cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(getActivity()).cartDAO());

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
                        Toast.makeText(getActivity(), "[GET CART_ITEMS_CALLED]", Toast.LENGTH_SHORT).show();
                        cartItemList.clear();
                        cartItemList.addAll(cartItems);
                        mAdapter.notifyDataSetChanged();
                        mbinding.clBottom.setVisibility(View.VISIBLE);
                        mbinding.cartListRv.setVisibility(View.VISIBLE);
                        mbinding.txtNoItemsInCart.setVisibility(View.INVISIBLE);
                    }
                }, throwable -> {
                    Toast.makeText(getActivity(), "[GET CART]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                })

        );
    }

    /**
     * Method to calculate total price
     */
    private void calculateTotalPrice() {
        cartDataSource.sumPrice(100)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.d("CalCulate Total==>", "integer" + integer);
                       /* if (integer <= 0) {
                            mbinding.btnConfirmCart.setText(getString(R.string.empty_cart));
                            mbinding.btnConfirmCart.setEnabled(false);
                        } else {
                            mbinding.btnConfirmCart.setText(getString(R.string.proceed));
                            mbinding.btnConfirmCart.setEnabled(true);
                        }*/
                        mbinding.txtTotalVal.setText(String.valueOf(integer));
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage().contains("Query returned empty"))
                            mbinding.txtTotalVal.setText("0");
                        else
                            Toast.makeText(getActivity(), "[SUM_CART]" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            super.onClick(v);
            switch (v.getId()) {
                case R.id.btn_proceed_checkout:
                    // EventBus.getDefault().postSticky(new SendTotalAmountEvent(mbinding.txtTotalVal.getText().toString()));
                    DeliveryActivity.startDeliveryActivity(getActivity());
                    break;
            }
        }
    }
}
