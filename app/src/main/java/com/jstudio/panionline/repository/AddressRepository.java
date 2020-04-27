package com.jstudio.panionline.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.jstudio.panionline.model.AddAddressRequest;
import com.jstudio.panionline.model.AddressListResponse;
import com.jstudio.panionline.service.api.ApiService;
import com.jstudio.panionline.service.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressRepository {
    private ApiService apiService;
    private static AddressRepository addressRepository;

    public static AddressRepository getInstance() {
        if (addressRepository == null) {
            addressRepository = new AddressRepository();
        }
        return addressRepository;
    }

    public AddressRepository() {
        apiService = RetrofitClient.getApiService(ApiService.class);
    }

    public MutableLiveData<AddressListResponse> getAddresses(String userId) {
        MutableLiveData<AddressListResponse> data = new MutableLiveData<>();
        apiService.address_list(userId).enqueue(new Callback<AddressListResponse>() {
            @Override
            public void onResponse(Call<AddressListResponse> call, Response<AddressListResponse> response) {
                if (response.body() != null)
                    data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<AddressListResponse> call, Throwable t) {
                Log.d("GetAddressFResponse+++", t.toString());
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<AddressListResponse> addAddress(AddAddressRequest addAddressRequest) {
        MutableLiveData<AddressListResponse> data = new MutableLiveData<>();
        apiService.add_new_address(addAddressRequest).enqueue(new Callback<AddressListResponse>() {
            @Override
            public void onResponse(Call<AddressListResponse> call, Response<AddressListResponse> response) {
                if (response.body() != null)
                    data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<AddressListResponse> call, Throwable t) {
                Log.d("AddAddressFResponse+++", t.toString());
                data.postValue(null);
            }
        });
        return data;
    }
}
