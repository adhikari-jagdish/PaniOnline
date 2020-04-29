package com.jstudio.panionline.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gmail.samehadar.iosdialog.IOSDialog;
import com.google.android.material.snackbar.Snackbar;
import com.jstudio.panionline.R;
import com.jstudio.panionline.model.eventbus.SendCartItemsCountEvent;
import com.jstudio.panionline.service.database.CartDataSource;
import com.jstudio.panionline.service.database.CartDatabase;
import com.jstudio.panionline.service.database.CartItem;
import com.jstudio.panionline.service.database.LocalCartDataSource;
import com.jstudio.panionline.utility.session.Preference_POSession;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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

    public static void displayProfileImage(Context mContext, String imgUrl, ImageView imageView) {
        //Init Username Dashboard
        Glide.with(mContext)
                .load(Uri.parse(imgUrl)) // add your image url
                .apply(new RequestOptions().circleCrop())
                .placeholder(R.drawable.ic_user)
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

    public static void updateUserIdInDb(Context mContext, int tempUserId, int newUserId) {

        CartDataSource cartDataSource;
        cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(mContext).cartDAO());
        cartDataSource.updateUserId(newUserId, tempUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        if (integer != null && integer != 0)
                            Preference_POSession.getInstance(mContext).putUserId(newUserId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, "[UPDATE_USERID]" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }


    public static void showDialog(Context mContext) {
        try {
            dialog0 = new IOSDialog.Builder(mContext)
                    .setTitleColorRes(R.color.gray)
                    .build();
            dialog0.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissDialog() {
        dialog0.dismiss();
    }

    public static void showSnackBar(View view, String message) {
        try {
            Snackbar snackbar = Snackbar
                    .make(view, message, Snackbar.LENGTH_LONG);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(80 - 65) + 65;
    }

    public static String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        return dateFormat.format(calendar.getTime());
    }

    //Check Internet Connection
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
}
