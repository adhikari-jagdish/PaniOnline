package com.jstudio.panionline.service.api;

import com.jstudio.panionline.model.LoginResponse;
import com.jstudio.panionline.model.ProductListResponse;
import com.jstudio.panionline.model.VerifyOtpResponse;
import com.jstudio.panionline.utility.constant.AppConstant;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    //Get Product List
    @GET(AppConstant.GET_PRODUCTS)
    Call<ProductListResponse> getProducts();

    //Login/Register using mobile/email
    @FormUrlEncoded
    @POST(AppConstant.LOGIN_MOBILE)
    Call<LoginResponse> login(@Field("username") String username);

    //Login/Register using mobile/email
    @FormUrlEncoded
    @POST(AppConstant.VERIFY_OTP)
    Call<VerifyOtpResponse> verify_otp(@Field("username") String username, @Field("otp") String otp);
}
