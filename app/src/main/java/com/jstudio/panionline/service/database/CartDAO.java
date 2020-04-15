package com.jstudio.panionline.service.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface CartDAO {

    @Query("SELECT * FROM Cart WHERE userId = :userId")
    Flowable<List<CartItem>> getCartItems(int userId);

    @Query("SELECT COUNT(*) FROM Cart WHERE userId = :userId")
    Single<Integer> countCart(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE) //If productId conflict, will update info
    Completable insertOrReplaceAll (CartItem... cartItems);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Single<Integer> updateCart(CartItem cartItem);

    @Delete
    Single<Integer> deleteCart(CartItem cartItem);

    @Query("DELETE FROM Cart WHERE userId= :userId")
    Single<Integer>cleanCart (int userId);

}
