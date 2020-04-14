package com.jstudio.panionline.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.jstudio.panionline.model.ProductListResponse;
import com.jstudio.panionline.repository.ProductRepository;
import com.jstudio.panionline.service.api.ApiService;
import com.jstudio.panionline.service.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListViewModel extends AndroidViewModel {
    private final LiveData<ProductListResponse> productList;


    public ProductListViewModel(@NonNull Application application) {
        super(application);

        productList = ProductRepository.getInstance().getProducts();

    }

    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<ProductListResponse> getProductList(){
        return productList;
    }
}
