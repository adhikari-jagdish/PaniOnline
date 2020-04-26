package com.jstudio.panionline.model;

public class VerifyOtpResponse {


    /**
     * statusCode : true
     * message : Otp validation success
     * data : {"secret":"GQVHW52SKRZVG6TLK5UU2XJEGMUCSOKA","userId":3,"name":"Jagdish","imageUrl":"https://www.outfittrends.com/wp-content/uploads/2017/06/beard-styles-for-men.jpg","username":"916396667854","__v":0}
     */

    private boolean statusCode;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * secret : GQVHW52SKRZVG6TLK5UU2XJEGMUCSOKA
         * userId : 3
         * name : Jagdish
         * imageUrl : https://www.outfittrends.com/wp-content/uploads/2017/06/beard-styles-for-men.jpg
         * username : 916396667854
         * __v : 0
         */

        private int userId;
        private String name;
        private String imageUrl;
        private String username;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
