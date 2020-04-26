package com.jstudio.panionline.model;

public class LoginResponse {


    /**
     * statusCode : true
     * message : Otp has been sent to registered username!
     */

    private boolean statusCode;
    private String message;

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
}
