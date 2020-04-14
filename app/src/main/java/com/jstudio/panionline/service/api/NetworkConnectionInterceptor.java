package com.jstudio.panionline.service.api;

import com.jstudio.panionline.utility.Utils;
import com.jstudio.panionline.utility.network.NoConnectivityException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnectionInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        //check if no network then just throw no network exception
        if (!Utils.isNetworkAvailable()) {
            throw new NoConnectivityException();
        }
        return chain.proceed(request);
    }
}
