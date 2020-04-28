package com.jstudio.panionline.view.ui.addAddress;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.ActivityAddAddressBinding;
import com.jstudio.panionline.model.AddAddressRequest;
import com.jstudio.panionline.utility.CommonMethods;
import com.jstudio.panionline.utility.session.Preference_POSession;
import com.jstudio.panionline.view.base.BaseActivity;
import com.jstudio.panionline.viewmodel.AddressViewModel;

public class AddAddressActivity extends BaseActivity {

    private ActivityAddAddressBinding mBinding;
    private AddressViewModel addressViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_address);
        setBackEnabled_Title(true, getString(R.string.add_address));

        addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);
        init();
    }

    private void init() {
        mBinding.btnAddAddress.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v != null) {
            super.onClick(v);

            switch (v.getId()) {
                case R.id.btn_add_address:
                    String flatBuilding = mBinding.flatBuildingEt.getText().toString().trim();
                    String colonyStreet = mBinding.colonyStreetEt.getText().toString().trim();
                    String landMark = mBinding.landmarkEt.getText().toString().trim();
                    String pinCode = mBinding.pincodeEt.getText().toString().trim();
                    String completeAddress = flatBuilding + ", " + colonyStreet + ", " + landMark + ", " + pinCode;
                    addressViewModel.addAddress(new AddAddressRequest(Preference_POSession.getInstance(this).getUserId(),
                            flatBuilding, colonyStreet, landMark, pinCode, completeAddress
                    ));
                    addressViewModel.getAddAddressResponse().observe(this, addressListResponse -> {
                        if (addressListResponse != null) {
                            if (addressListResponse.isStatusCode()) {
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                CommonMethods.showSnackBar(mBinding.getRoot(), addressListResponse.getMessage());
                            }
                        }
                    });
                    break;
            }
        }

    }
}
