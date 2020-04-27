package com.jstudio.panionline.model;

import java.util.List;

public class AddressListResponse {

    /**
     * statusCode : true
     * message : Address Listed Successfully!
     * data : [{"_id":"5ea727fa2bc9371eace0b31d","addressId":1,"userId":4,"houseNo":"s80","colony":"School Block","landmark":"Near Bikaner","pinCode":10092,"completeAddress":"s80,School Block,Near Bikaner,10092","__v":0},{"_id":"5ea728482bc9371eace0b31e","addressId":2,"userId":4,"houseNo":"s50","colony":"School Block","landmark":"Near Bikaner","pinCode":10092,"completeAddress":"s50,School Block,Near Bikaner,10092","__v":0}]
     */

    private boolean statusCode;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * _id : 5ea727fa2bc9371eace0b31d
         * addressId : 1
         * userId : 4
         * houseNo : s80
         * colony : School Block
         * landmark : Near Bikaner
         * pinCode : 10092
         * completeAddress : s80,School Block,Near Bikaner,10092
         * __v : 0
         */

        private int addressId;
        private int userId;
        private String houseNo;
        private String colony;
        private String landmark;
        private int pinCode;
        private String completeAddress;

        public int getAddressId() {
            return addressId;
        }

        public void setAddressId(int addressId) {
            this.addressId = addressId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getHouseNo() {
            return houseNo;
        }

        public void setHouseNo(String houseNo) {
            this.houseNo = houseNo;
        }

        public String getColony() {
            return colony;
        }

        public void setColony(String colony) {
            this.colony = colony;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public int getPinCode() {
            return pinCode;
        }

        public void setPinCode(int pinCode) {
            this.pinCode = pinCode;
        }

        public String getCompleteAddress() {
            return completeAddress;
        }

        public void setCompleteAddress(String completeAddress) {
            this.completeAddress = completeAddress;
        }
    }
}
