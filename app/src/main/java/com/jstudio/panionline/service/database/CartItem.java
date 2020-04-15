package com.jstudio.panionline.service.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cart")
public class CartItem {

    public CartItem() {
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "productId")
    private int productId;

    @ColumnInfo(name = "productName")
    private String productName;

    @ColumnInfo(name = "productImageUrl")
    private String productImageUrl;

    @ColumnInfo(name = "productPrice")
    private int productPrice;

    @ColumnInfo(name = "productQuantity")
    private int productQuantity;

    @ColumnInfo(name = "userId")
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
}
