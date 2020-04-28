package com.jstudio.panionline.view.ui.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.FragmentSettingsBinding;
import com.jstudio.panionline.utility.CommonMethods;
import com.jstudio.panionline.utility.session.Preference_POSession;
import com.jstudio.panionline.view.base.BaseFragment;


public class SettingsFragment extends BaseFragment {

    private FragmentSettingsBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        CommonMethods.displayProfileImage(getActivity(),
                Preference_POSession.getInstance(getActivity()).getImageUrl(), mBinding.imgProfileSettings);

        mBinding.txtProfileName.setText(Preference_POSession.getInstance(getActivity()).getName());
    }
}
