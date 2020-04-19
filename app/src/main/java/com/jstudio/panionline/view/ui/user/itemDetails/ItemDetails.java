package com.jstudio.panionline.view.ui.user.itemDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.ActivityItemDetailsBinding;
import com.jstudio.panionline.model.ProductListResponse;
import com.jstudio.panionline.service.database.CartDataSource;
import com.jstudio.panionline.service.database.CartDatabase;
import com.jstudio.panionline.service.database.LocalCartDataSource;
import com.jstudio.panionline.utility.CommonMethods;
import com.jstudio.panionline.utility.constant.AppConstant;
import com.jstudio.panionline.view.base.BaseActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ItemDetails extends BaseActivity {
    private ActivityItemDetailsBinding mBinding;
    private CompositeDisposable compositeDisposable;
    private CartDataSource cartDataSource;
    private ProductListResponse.DataBean products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_item_details);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            products = intent.getParcelableExtra(AppConstant.PRODUCT_DETAILS);
        }
        setBackEnabled_Title(true, products.getProductName());
        fillData();

        mBinding.cartAddIcon.setOnClickListener(this);
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

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
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
                case R.id.cart_add_icon:

                    compositeDisposable.add(
                            cartDataSource.insertOrReplaceAll(CommonMethods.addItemsToCart(100,
                                    products.getProductId(), products.getProductName(),
                                    products.getProductImageUrl(),
                                    products.getProductPrice()))
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                                Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();
                                            },

                                            throwable -> {
                                                Toast.makeText(this, "[ADD CART]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                            })

                    );
                    break;
            }
        }
    }
}
