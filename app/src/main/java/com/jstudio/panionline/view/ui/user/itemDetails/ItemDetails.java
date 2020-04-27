package com.jstudio.panionline.view.ui.user.itemDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.ActivityItemDetailsBinding;
import com.jstudio.panionline.model.ProductListResponse;
import com.jstudio.panionline.model.eventbus.SendCartItemsCountEvent;
import com.jstudio.panionline.service.database.CartDataSource;
import com.jstudio.panionline.service.database.CartDatabase;
import com.jstudio.panionline.service.database.LocalCartDataSource;
import com.jstudio.panionline.utility.CommonMethods;
import com.jstudio.panionline.utility.constant.AppConstant;
import com.jstudio.panionline.utility.session.Preference_POSession;
import com.jstudio.panionline.view.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ItemDetails extends BaseActivity {
    private ActivityItemDetailsBinding mBinding;
    private CompositeDisposable compositeDisposable;
    private CartDataSource cartDataSource;
    private ProductListResponse.DataBean products;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_item_details);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            products = intent.getParcelableExtra(AppConstant.PRODUCT_DETAILS);
        }
        setBackEnabled_Title(true, products.getProductName(), true);


        CommonMethods.countItemsInCart(this, Preference_POSession.getInstance(this).getUserId());

        init();
    }


    /**
     * Init method called first
     */
    private void init() {
        fillData();

        mBinding.btnAddToCart.setOnClickListener(this);

        compositeDisposable = new CompositeDisposable();
        cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(this).cartDAO());
    }


    /**
     * Method to Add Content to Layout
     */
    private void fillData() {
        CommonMethods.loadImage(mBinding.topImage, products.getProductImageUrl());
        mBinding.txtLikeCounts.setText(String.valueOf(products.getProductLikes()));
        mBinding.txtReviewCounts.setText(String.valueOf(products.getProductReviews()));
        mBinding.txtDescription.setText(products.getProductDescription());
    }

    private void checkIfItemsExistsInCart() {
        cartDataSource.getProductQuantity(products.getProductId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.d(TAG, "ProductId++++"+integer);
                        if (integer >= 1) {

                            mBinding.btnAddToCart.setVisibility(View.INVISIBLE);
                            mBinding.btnAddedToCart.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Item Doesnot Exits++++" + e.getMessage());
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkIfItemsExistsInCart();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartItemsCount(SendCartItemsCountEvent itemsCountEvent) {
        setValuetoBadge(itemsCountEvent.getmCartItemsCount());
    }

    /**
     * Starts ItemDetails Activity Using Intent
     *
     * @param context context of the current activity
     */
    public static void startItemDetailsActivity(Context context, ProductListResponse.DataBean productDetails) {
        context.startActivity(createIntent(context, productDetails));
    }

    private static Intent createIntent(Context context, ProductListResponse.DataBean productDetails) {
        Intent intent = new Intent(context, ItemDetails.class);
        intent.putExtra(AppConstant.PRODUCT_DETAILS, productDetails);
        return intent;
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            super.onClick(v);
            switch (v.getId()) {
                case R.id.btn_add_to_cart:
                    compositeDisposable.add(
                            cartDataSource.insertOrReplaceAll(CommonMethods.addItemsToCart(Preference_POSession.getInstance(this).getUserId(),
                                    products.getProductId(), 1, products.getProductName(),
                                    products.getProductImageUrl(),
                                    products.getProductPrice()))
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                                // Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();
                                                mBinding.btnAddToCart.setVisibility(View.INVISIBLE);
                                                mBinding.btnAddedToCart.setVisibility(View.VISIBLE);
                                                CommonMethods.countItemsInCart(this, Preference_POSession.getInstance(this).getUserId());
                                            },

                                            throwable -> {
                                                Log.d(TAG, "[ADD CART]" + throwable.getMessage());
                                            })

                    );
                    break;
            }
        }
    }
}
