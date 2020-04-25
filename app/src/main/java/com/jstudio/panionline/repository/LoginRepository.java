package com.jstudio.panionline.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jstudio.panionline.model.LoginResponse;
import com.jstudio.panionline.service.api.ApiService;
import com.jstudio.panionline.service.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private ApiService apiService;
    private static LoginRepository loginRepository;

    private static LoginRepository getInstance() {
        if (loginRepository == null) {
            loginRepository = new LoginRepository();
        }
        return loginRepository;
    }


    public LoginRepository() {
        apiService = RetrofitClient.getApiService(ApiService.class);
    }

    public MutableLiveData<LoginResponse> login(String username) {
        MutableLiveData<LoginResponse> data = new MutableLiveData<>();
        apiService.login(username).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null)
                    data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("LoginFResponse+++", t.toString());
                data.postValue(null);
            }
        });
        return data;
    }



}
