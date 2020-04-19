package com.jstudio.panionline.service.database;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface CartDataSource {

    Flowable<List<CartItem>> getCartItems(int userId);

    Single<Integer> countCart(int userId);

    Single<Integer> sumPrice(int userId);

    Completable insertOrReplaceAll(CartItem... cartItems);

    Single<Integer> updateCart(CartItem cartItem);

    Single<Integer> deleteCart(CartItem cartItem);

    Single<Integer> cleanCart(int userId);
}
