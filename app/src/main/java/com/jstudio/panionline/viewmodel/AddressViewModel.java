package com.jstudio.panionline.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jstudio.panionline.model.AddAddressRequest;
import com.jstudio.panionline.model.AddressListResponse;
import com.jstudio.panionline.model.LoginResponse;
import com.jstudio.panionline.repository.AddressRepository;

public class AddressViewModel extends AndroidViewModel {
    private MutableLiveData<AddressListResponse> addressListLD;
    private AddressRepository addressRepository;

    public AddressViewModel(@NonNull Application application) {
        super(application);
        addressRepository = new AddressRepository();
    }

    public void addAddress(AddAddressRequest addAddressRequest){
        addressListLD = addressRepository.addAddress(addAddressRequest);
    }

    public LiveData<AddressListResponse> getAddAddressResponse(){
        return addressListLD;
    }

    public void getAddress(String userId){
        addressListLD = addressRepository.getAddresses(userId);
    }

    public LiveData<AddressListResponse> getAddAddressListResponse(){
        return addressListLD;
    }
}
