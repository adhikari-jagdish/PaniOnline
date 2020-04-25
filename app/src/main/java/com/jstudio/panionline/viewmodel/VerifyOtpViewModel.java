package com.jstudio.panionline.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jstudio.panionline.model.VerifyOtpResponse;
import com.jstudio.panionline.repository.VerifyOtpRepository;

public class VerifyOtpViewModel extends AndroidViewModel {

    private MutableLiveData<VerifyOtpResponse> verifyOtpResponseLD;
    private VerifyOtpRepository verifyOtpRepository;

    public VerifyOtpViewModel(@NonNull Application application) {
        super(application);
        verifyOtpRepository = new VerifyOtpRepository();
    }

    public void callVerifyOtp(String username, String otp) {
        verifyOtpResponseLD = verifyOtpRepository.verify_otp(username, otp);
    }

    public LiveData<VerifyOtpResponse> getVerifyOtpResponse() {
        return verifyOtpResponseLD;
    }
}


