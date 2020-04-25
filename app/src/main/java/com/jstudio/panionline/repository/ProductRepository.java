package com.jstudio.panionline.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.jstudio.panionline.model.ProductListResponse;
import com.jstudio.panionline.service.api.ApiService;
import com.jstudio.panionline.service.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private ApiService apiService;
    private static ProductRepository productRepository;

    public static ProductRepository getInstance() {
        if (productRepository == null) {
            productRepository = new ProductRepository();
        }
        return productRepository;
    }

    public ProductRepository() {
        apiService = RetrofitClient.getApiService(ApiService.class);
    }

    public MutableLiveData<ProductListResponse> getProducts() {
        MutableLiveData<ProductListResponse> data = new MutableLiveData<>();
        apiService.getProducts().enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                if (response != null && response.body().getData() != null) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                Log.d("ProductsFResponse+++", t.toString());
                data.setValue(null);
            }
        });
        return data;
    }
}
