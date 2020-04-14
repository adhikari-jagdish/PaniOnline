package com.jstudio.panionline.utility.network;

import androidx.annotation.Nullable;

import java.io.IOException;

public class NoConnectivityException extends IOException {
    private String errorMsg;

    public NoConnectivityException(){
        errorMsg = "no network";
    }

    @Nullable
    @Override
    public String getMessage() {
        return errorMsg;
    }
}
