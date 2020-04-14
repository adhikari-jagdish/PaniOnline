package com.jstudio.panionline.service.api;

import androidx.annotation.Nullable;

public class ApiServiceHolder {
    ApiService apiService = null;

    @Nullable
    public ApiService get() {
        return apiService;
    }

    public void set(ApiService apiInterface) {
        this.apiService = apiInterface;
    }

}
