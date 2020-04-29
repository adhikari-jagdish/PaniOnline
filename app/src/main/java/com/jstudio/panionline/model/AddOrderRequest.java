package com.jstudio.panionline.model;

import java.util.List;

public class AddOrderRequest {
    private int clientId;
    private String orderDate;
    private String clientName;
    private String deliveryAddress;
    private int totalAmount;
    private String paymentMode;
    private List<CartItemsDetails> productDetails;

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public void setCartItems(List<CartItemsDetails> cartItems) {
        this.productDetails = cartItems;
    }

    public static class CartItemsDetails {
        private int productId;
        private String productName;
        private int productQuantity;

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public void setProductQuantity(int productQuantity) {
            this.productQuantity = productQuantity;
        }
    }
}
