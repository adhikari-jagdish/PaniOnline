package com.jstudio.panionline.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jstudio.panionline.model.LoginResponse;
import com.jstudio.panionline.model.ProductListResponse;
import com.jstudio.panionline.repository.LoginRepository;

public class LoginViewModel extends AndroidViewModel {
    private LiveData<LoginResponse> loginLD;
    private LoginRepository loginRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        init();
    }

    private void init(){
        loginRepository = new LoginRepository();
        loginLD = LoginRepository.getInstance().getLoginResponseLiveData();
    }


    public LiveData<LoginResponse> login(){
        return loginLD;
    }
}
