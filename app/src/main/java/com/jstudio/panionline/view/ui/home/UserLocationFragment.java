package com.jstudio.panionline.view.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.FragmentUserLocationBinding;
import com.jstudio.panionline.model.ProductListResponse;
import com.jstudio.panionline.model.eventbus.SendUserProfileDetails;
import com.jstudio.panionline.utility.CommonMethods;
import com.jstudio.panionline.view.ui.home.adapter.HomeItemsAdapter;
import com.jstudio.panionline.viewmodel.ProductListViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class UserLocationFragment extends Fragment {
    private FragmentUserLocationBinding mBinding;
    private HomeItemsAdapter mAdapter;
    private final String LOG = getClass().getSimpleName();
    private ArrayList<ProductListResponse.DataBean> products = new ArrayList<>();
    private String profileImageUrl = "";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProductListViewModel mProductListVM = new ViewModelProvider(this).get(ProductListViewModel.class);
        mProductListVM.getProductList();
        observeViewModel(mProductListVM);
    }

    private void observeViewModel(ProductListViewModel viewModel) {
        CommonMethods.showDialog(getActivity());
        viewModel.getProductList().observe(getViewLifecycleOwner(), productListResponse -> {
            if (productListResponse != null) {
                CommonMethods.dismissDialog();
                products.clear();
                products.addAll(productListResponse.getData());
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getUserDetails(SendUserProfileDetails userProfileDetails) {
        if (userProfileDetails.isLoggedIn()) {
            mBinding.txtUsernameTitle.setText("Welcome " + userProfileDetails.getProfileName() + "!");
            profileImageUrl = userProfileDetails.getProfileImageUrl();
            CommonMethods.displayProfileImage(getActivity(), profileImageUrl, mBinding.imgProfile);
        } else {
            mBinding.txtUsernameTitle.setText(getString(R.string.welcome_user));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_location, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    /**
     * Initialize the UI first
     */
    @SuppressLint("SetTextI18n")
    private void initUI() {
        CommonMethods.displayProfileImage(getActivity(), profileImageUrl, mBinding.imgProfile);

        //Init RecyclerView
        mAdapter = new HomeItemsAdapter(getActivity(), products);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mBinding.homeItemsRv.setLayoutManager(gridLayoutManager);
        mBinding.homeItemsRv.setAdapter(mAdapter);
    }



}
