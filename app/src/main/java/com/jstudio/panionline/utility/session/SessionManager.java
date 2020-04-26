package com.jstudio.panionline.utility.session;

import com.skydoves.preferenceroom.KeyName;
import com.skydoves.preferenceroom.PreferenceEntity;

@PreferenceEntity(name = "POSession")
public class SessionManager {
    @KeyName(name = "isLoggedIn")
    protected final boolean userLoggedIn = false;

    @KeyName(name = "name")
    protected final String userDisplayName = "";

    @KeyName(name = "imageUrl")
    protected final String userProfileImage = "";

    @KeyName(name = "userId")
    protected final int userId = 0;
}
