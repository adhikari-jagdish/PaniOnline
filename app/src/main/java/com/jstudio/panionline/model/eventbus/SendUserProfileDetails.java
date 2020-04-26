package com.jstudio.panionline.model.eventbus;

public class SendUserProfileDetails {

    private String profileName;
    private String profileImageUrl;
    private boolean isLoggedIn;

    public SendUserProfileDetails(String profileName, String profileImageUrl, boolean isLoggedIn) {
        this.profileName = profileName;
        this.profileImageUrl = profileImageUrl;
        this.isLoggedIn = isLoggedIn;
    }

    public String getProfileName() {
        return profileName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}
