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

    private static LoginRepository instance;
    private ApiService apiService;
    private MutableLiveData<LoginResponse> data;



    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }


    public LoginRepository() {
        RetrofitClient.getApiService(ApiService.class);
        data = new MutableLiveData<>();
    }

    public LiveData<LoginResponse> login(String username) {

        apiService.login(username).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("LoginFResponse+++", t.toString());
            }
        });

        return data;
    }

    public LiveData<LoginResponse> getLoginResponseLiveData() {
        return data;
    }


}
