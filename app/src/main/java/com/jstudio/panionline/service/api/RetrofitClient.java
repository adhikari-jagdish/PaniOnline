package com.jstudio.panionline.service.api;

import com.jstudio.panionline.utility.constant.AppConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jstudio.panionline.utility.constant.AppConstant.BASE_URL_STAGE;

public class RetrofitClient {
    private static ApiService apiService;

    public RetrofitClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_STAGE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService(){
        return apiService;
    }
}