package com.jstudio.panionline.utility;

import com.jstudio.panionline.service.database.CartItem;

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
}
