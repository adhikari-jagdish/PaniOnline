package com.jstudio.panionline.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.jstudio.panionline.model.AddOrderRequest;
import com.jstudio.panionline.model.AddOrderResponse;
import com.jstudio.panionline.model.AddTransactionRequest;
import com.jstudio.panionline.model.CommonResponse;
import com.jstudio.panionline.service.api.ApiService;
import com.jstudio.panionline.service.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {
    private ApiService apiService;
    private static OrderRepository orderRepository;

    public static OrderRepository getInstance() {
        if (orderRepository == null) {
            orderRepository = new OrderRepository();
        }
        return orderRepository;
    }

    public OrderRepository() {
        apiService = RetrofitClient.getApiService(ApiService.class);
    }

    public MutableLiveData<AddOrderResponse> createOrder(AddOrderRequest addOrderRequest) {
        MutableLiveData<AddOrderResponse> data = new MutableLiveData<>();
        apiService.create_new_order(addOrderRequest).enqueue(new Callback<AddOrderResponse>() {
            @Override
            public void onResponse(Call<AddOrderResponse> call, Response<AddOrderResponse> response) {
                if (response.body() != null)
                    data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<AddOrderResponse> call, Throwable t) {
                Log.d("CreateOrderFResponse+++", t.toString());
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<CommonResponse> saveTransaction(AddTransactionRequest addTransactionRequest) {
        MutableLiveData<CommonResponse> data = new MutableLiveData<>();
        apiService.save_success_transaction(addTransactionRequest).enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.body() != null)
                    data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.d("SaveTranFResponse+++", t.toString());
                data.postValue(null);
            }
        });
        return data;
    }
}
