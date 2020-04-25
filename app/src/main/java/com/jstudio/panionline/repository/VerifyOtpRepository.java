package com.jstudio.panionline.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.jstudio.panionline.model.VerifyOtpResponse;
import com.jstudio.panionline.service.api.ApiService;
import com.jstudio.panionline.service.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtpRepository {

    private ApiService apiService;
    private static VerifyOtpRepository verifyOtpRepository;
    private final String TAG = getClass().getSimpleName();

    private static VerifyOtpRepository getInstance() {
        if (verifyOtpRepository == null) {
            verifyOtpRepository = new VerifyOtpRepository();
        }
        return verifyOtpRepository;
    }

    public VerifyOtpRepository() {
        apiService = RetrofitClient.getApiService(ApiService.class);
    }

    public MutableLiveData<VerifyOtpResponse> verify_otp(String username, String otp) {
        MutableLiveData<VerifyOtpResponse> data = new MutableLiveData<>();
        Log.d(TAG, "VerifyOtpRepo++++"+username+""+otp);
        apiService.verify_otp(username, otp).enqueue(new Callback<VerifyOtpResponse>() {
            @Override
            public void onResponse(Call<VerifyOtpResponse> call, Response<VerifyOtpResponse> response) {
                if (response.body() != null)
                    data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<VerifyOtpResponse> call, Throwable t) {
                Log.d("VerifyOtpError+++", t.toString());
                data.postValue(null);
            }
        });
        return data;
    }

}
