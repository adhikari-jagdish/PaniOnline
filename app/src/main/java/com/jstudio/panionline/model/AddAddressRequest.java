package com.jstudio.panionline.model;

public class AddAddressRequest {

    private int userId;
    private String houseNo;
    private String colony;
    private String landmark;
    private String pinCode;
    private String completeAddress;


    public AddAddressRequest(int userId, String houseNo, String colony, String landmark, String pinCode, String completeAddress) {
        this.userId = userId;
        this.houseNo = houseNo;
        this.colony = colony;
        this.landmark = landmark;
        this.pinCode = pinCode;
        this.completeAddress = completeAddress;
    }
}
