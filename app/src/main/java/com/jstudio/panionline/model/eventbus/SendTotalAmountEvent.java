package com.jstudio.panionline.model.eventbus;

public class SendTotalAmountEvent {
    private String totalAmount;

    public SendTotalAmountEvent(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }
}
