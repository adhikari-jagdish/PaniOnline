package com.jstudio.panionline.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jstudio.panionline.model.AddOrderRequest;
import com.jstudio.panionline.model.AddOrderResponse;
import com.jstudio.panionline.model.AddTransactionRequest;
import com.jstudio.panionline.model.CommonResponse;
import com.jstudio.panionline.repository.OrderRepository;

public class OrderViewModel extends AndroidViewModel {

    private MutableLiveData<AddOrderResponse> addOrderResponseLD;
    private MutableLiveData<CommonResponse> addTransactionResponseLD;
    private OrderRepository orderRepository;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        orderRepository = new OrderRepository();
    }

    public void createOrder(AddOrderRequest addOrderRequest) {
        addOrderResponseLD = orderRepository.createOrder(addOrderRequest);
    }

    public LiveData<AddOrderResponse> getCreateOrderResponse() {
        return addOrderResponseLD;
    }

    public void saveSuccessTransaction(AddTransactionRequest addTransactionRequest) {
        addTransactionResponseLD = orderRepository.saveTransaction(addTransactionRequest);
    }

    public LiveData<CommonResponse> getTransactionResponse() {
        return addTransactionResponseLD;
    }
}
