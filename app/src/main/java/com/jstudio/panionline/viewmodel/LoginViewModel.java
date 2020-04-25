package com.jstudio.panionline.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jstudio.panionline.model.LoginResponse;
import com.jstudio.panionline.model.ProductListResponse;
import com.jstudio.panionline.repository.LoginRepository;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<LoginResponse> loginLD;
    private LoginRepository loginRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository = new LoginRepository();
    }

    public void callLogin(String username){
        loginLD = loginRepository.login(username);
    }

    public LiveData<LoginResponse> getLoginResponse(){
        return loginLD;
    }
}
