package com.jstudio.panionline.service.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jstudio.panionline.utility.constant.AppConstant.BASE_URL_STAGE;

public class RetrofitClient {

   private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL_STAGE)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S getApiService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
