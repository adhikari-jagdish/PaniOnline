package com.jstudio.panionline.model;

public class AddOrderResponse {


    /**
     * statusCode : true
     * message : Order Placed Successfully!
     * data : 6
     */

    private boolean statusCode;
    private String message;
    private int data;

    public boolean isStatusCode() {
        return statusCode;
    }

    public void setStatusCode(boolean statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
