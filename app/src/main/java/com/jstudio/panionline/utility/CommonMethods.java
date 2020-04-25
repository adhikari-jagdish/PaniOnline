package com.jstudio.panionline.utility;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gmail.samehadar.iosdialog.IOSDialog;
import com.jstudio.panionline.R;
import com.jstudio.panionline.model.eventbus.SendCartItemsCountEvent;
import com.jstudio.panionline.service.database.CartDataSource;
import com.jstudio.panionline.service.database.CartDatabase;
import com.jstudio.panionline.service.database.CartItem;
import com.jstudio.panionline.service.database.LocalCartDataSource;

import org.greenrobot.eventbus.EventBus;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CommonMethods {

    public static final String TAG = "Common Method";
    private static IOSDialog dialog0;


    //Method to Add Items to Room Database
    public static CartItem addItemsToCart(int userId, int productId, int productQuantity, String productName, String productImageUrl, int productPrice) {
        CartItem cartItem = new CartItem();
        cartItem.setUserId(userId);
        cartItem.setProductId(productId);
        cartItem.setProductQuantity(productQuantity);
        cartItem.setProductName(productName);
        cartItem.setProductImageUrl(productImageUrl);
        cartItem.setProductPrice(productPrice);
        return cartItem;
    }

    public static void loadImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.bisleri_bottle_img)
                .into(imageView);
    }

    public static void loadCircularImage(CircleImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.bisleri_bottle_img)
                .into(imageView);
    }


    public static void countItemsInCart(Context context, int userId) {
        CartDataSource cartDataSource;
        //get the number of items added to cart and show in badge
        cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(context).cartDAO());

        cartDataSource.countCart(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        EventBus.getDefault().post(new SendCartItemsCountEvent(integer));
                        //Toast.makeText(context, "[ITEMS_IN_CART]" + integer, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Toast.makeText(context, "[COUNT_CART]" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public static void showDialog(Context mContext) {
        dialog0 = new IOSDialog.Builder(mContext)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();
    }

    public static void dismissDialog() {
        dialog0.dismiss();
    }
}
