package com.jstudio.panionline.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jstudio.panionline.model.ProductListResponse;
import com.jstudio.panionline.service.api.ApiService;
import com.jstudio.panionline.service.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private ApiService apiService;
    private static ProductRepository instance;

    public static ProductRepository getInstance(){
        if(instance == null){
            instance = new ProductRepository();
        }
        return instance;
    }

    private ProductRepository() {
        RetrofitClient.getApiService(ApiService.class);
    }

    public LiveData<ProductListResponse> getProducts(){
        final MutableLiveData<ProductListResponse> data = new MutableLiveData<>();

        apiService.getProducts().enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                Log.d("ProductsFResponse+++",t.toString());
            }
        });

        return data;
    }
}
