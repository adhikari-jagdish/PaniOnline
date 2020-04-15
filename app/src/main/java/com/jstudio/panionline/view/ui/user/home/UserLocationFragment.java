package com.jstudio.panionline.view.ui.user.home;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gmail.samehadar.iosdialog.IOSDialog;
import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.FragmentUserLocationBinding;
import com.jstudio.panionline.model.ProductListResponse;
import com.jstudio.panionline.view.ui.user.home.adapter.HomeItemsAdapter;
import com.jstudio.panionline.viewmodel.ProductListViewModel;

import java.util.ArrayList;

public class UserLocationFragment extends Fragment {
    private FragmentUserLocationBinding mBinding;
    private HomeItemsAdapter mAdapter;
    private ViewModelProvider.Factory viewModelFactory;
    private final String LOG = getClass().getSimpleName();
    private ArrayList<ProductListResponse.DataBean> products = new ArrayList<>();
    private IOSDialog dialog0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*mProductListVM = new ViewModelProvider(requireActivity().getViewModelStore(), viewModelFactory)
                .get(ProductListViewModel.class);*/

      dialog0 = new IOSDialog.Builder(getActivity())
                .setTitleColorRes(R.color.gray)
                .build();
      dialog0.show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ProductListViewModel mProductListVM = ViewModelProviders.of(getActivity()).get(ProductListViewModel.class);
        observeViewModel(mProductListVM);
    }

    private void observeViewModel(ProductListViewModel viewModel) {
        viewModel.getProductList().observe(getViewLifecycleOwner(), new Observer<ProductListResponse>() {
            @Override
            public void onChanged(ProductListResponse productListResponse) {
                if (productListResponse != null) {
                    dialog0.dismiss();
                    products.clear();
                    products.addAll(productListResponse.getData());
                    mAdapter.notifyDataSetChanged();
                }

            }
        });

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
    private void initUI() {
        //Init Username Dashboard
        RequestOptions options = new RequestOptions();
        Glide.with(getActivity())
                .load(Uri.parse("https://i.ibb.co/TKDG2zw/images-q-tbn-ANd9-Gc-QIVo-QC0-EDa-Ai-X4-Tjzl3f7p-P6-GVPg-VQm-LIdci-CMg-Uu-Cc-VR9-QQRn8-Q-s.png")) // add your image url
                .apply(options.circleCrop())
                .into(mBinding.imgProfile);

        //Init RecyclerView
        mAdapter = new HomeItemsAdapter(getActivity(), products);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mBinding.homeItemsRv.setLayoutManager(gridLayoutManager);
        mBinding.homeItemsRv.setAdapter(mAdapter);
    }

}
