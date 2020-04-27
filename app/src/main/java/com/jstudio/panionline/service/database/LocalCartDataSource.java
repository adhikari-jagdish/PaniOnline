package com.jstudio.panionline.service.database;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class LocalCartDataSource implements CartDataSource {

    private CartDAO cartDAO;

    public LocalCartDataSource(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    @Override
    public Flowable<List<CartItem>> getCartItems(int userId) {
        return cartDAO.getCartItems(userId);
    }

    @Override
    public Single<Integer> countCart(int userId) {
        return cartDAO.countCart(userId);
    }

    @Override
    public Single<Integer> getProductQuantity(int productId) {
        return cartDAO.getProductQuantity(productId);
    }

    @Override
    public Single<Integer> sumPrice(int userId) {
        return cartDAO.sumCart(userId);
    }

    @Override
    public Completable insertOrReplaceAll(CartItem... cartItems) {
        return cartDAO.insertOrReplaceAll(cartItems);
    }

    @Override
    public Single<Integer> updateCart(CartItem cartItem) {
        return cartDAO.updateCart(cartItem);
    }

    @Override
    public Single<Integer> deleteCart(CartItem cartItem) {
        return cartDAO.deleteCart(cartItem);
    }

    @Override
    public Single<Integer> cleanCart(int userId) {
        return cartDAO.cleanCart(userId);
    }

    @Override
    public Single<Integer> updateUserId(int newUserId, int oldUserId) {
        return cartDAO.updateUserId(newUserId, oldUserId);
    }
}
