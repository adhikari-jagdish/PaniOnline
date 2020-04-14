package com.jstudio.panionline.service.api;

import com.jstudio.panionline.utility.constant.AppConstant;
import com.jstudio.panionline.model.ProductListResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    //Get Product List
    @GET(AppConstant.GET_PRODUCTS)
    Call<ProductListResponse> getProducts();
}
