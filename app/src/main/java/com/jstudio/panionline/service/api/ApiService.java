package com.jstudio.panionline.service.api;

import com.jstudio.panionline.model.AddAddressRequest;
import com.jstudio.panionline.model.AddOrderRequest;
import com.jstudio.panionline.model.AddressListResponse;
import com.jstudio.panionline.model.CommonResponse;
import com.jstudio.panionline.model.LoginResponse;
import com.jstudio.panionline.model.ProductListResponse;
import com.jstudio.panionline.model.VerifyOtpResponse;
import com.jstudio.panionline.utility.constant.AppConstant;

import retrofit2.Call;
import retrofit2.http.Body;
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

    /**
     * Get Address
     * @param userId
     */
    @FormUrlEncoded
    @POST(AppConstant.GET_ADDRESSES)
    Call<AddressListResponse> address_list(@Field("userId") String userId);

    /**
     * Add New Address
     * @param addAddressRequest Model contains all fields to post
     */
    @POST(AppConstant.ADD_ADDRESS)
    Call<AddressListResponse> add_new_address(@Body  AddAddressRequest addAddressRequest);


    /**
     * Create New Order
     */
    @POST(AppConstant.ADD_ADDRESS)
    Call<CommonResponse> create_new_order(@Body AddOrderRequest addOrderRequest);

}
