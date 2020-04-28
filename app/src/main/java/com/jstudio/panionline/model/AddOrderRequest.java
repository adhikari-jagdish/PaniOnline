package com.jstudio.panionline.model;

import java.util.List;

public class AddOrderRequest {
    private int clientId;
    private String orderDate;
    private String clientName;
    private String deliveryAddress;
    private int totalAmount;
    private String paymentMode;
    private List<ProductDetails> productDetails;

    public AddOrderRequest(int clientId, String orderDate, String clientName, String deliveryAddress, int totalAmount, String paymentMode, List<ProductDetails> productDetails) {
        this.clientId = clientId;
        this.orderDate = orderDate;
        this.clientName = clientName;
        this.deliveryAddress = deliveryAddress;
        this.totalAmount = totalAmount;
        this.paymentMode = paymentMode;
        this.productDetails = productDetails;
    }

    private static class ProductDetails{
        private int productId;
        private String productName;
        private int productQuantity;

    }
}
