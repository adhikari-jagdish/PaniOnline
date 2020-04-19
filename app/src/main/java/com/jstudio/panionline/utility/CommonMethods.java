package com.jstudio.panionline.utility;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jstudio.panionline.R;
import com.jstudio.panionline.service.database.CartItem;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommonMethods {

    public static final String TAG = "Common Method";

    //Method to Add Items to Room Database
    public static CartItem addItemsToCart(int userId, int productId, String productName, String productImageUrl, int productPrice) {
        CartItem cartItem = new CartItem();
        cartItem.setUserId(userId);
        cartItem.setProductId(productId);
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
}
