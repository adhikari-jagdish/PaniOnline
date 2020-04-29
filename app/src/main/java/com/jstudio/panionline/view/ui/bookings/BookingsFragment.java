package com.jstudio.panionline.view.ui.bookings;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.FragmentBookingsBinding;
import com.jstudio.panionline.view.base.BaseFragment;

public class BookingsFragment extends BaseFragment {
    private FragmentBookingsBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookings, container, false);
        return mBinding.getRoot();
    }
}
