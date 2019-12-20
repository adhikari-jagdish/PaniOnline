package com.jstudio.panionline.view.ui.user.home;

import android.Manifest;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.FragmentUserLocationBinding;
import com.jstudio.panionline.view.ui.user.home.adapter.HomeItemsAdapter;


public class UserLocationFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private FragmentUserLocationBinding mBinding;
    private HomeItemsAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.user_home_map);
        mapFragment.getMapAsync(this);
        initUI();
    }

    /**
     * Initialize the UI first
     */
    private void initUI() {
        //Init RecyclerView
        mAdapter = new HomeItemsAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mBinding.homeItemsRv.setLayoutManager(linearLayoutManager);
        mBinding.homeItemsRv.setAdapter(mAdapter);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLng customMarkerLocationOne = new LatLng(28.583911, 77.319116);
                LatLng customMarkerLocationTwo = new LatLng(28.583078, 77.313744);
                LatLng customMarkerLocationThree = new LatLng(28.580903, 77.317408);
                LatLng customMarkerLocationFour = new LatLng(28.580108, 77.315271);

                mMap.addMarker(new MarkerOptions().position(customMarkerLocationOne).title("Bisleri"));
                mMap.addMarker(new MarkerOptions().position(customMarkerLocationTwo).title("Aqua"));
                mMap.addMarker(new MarkerOptions().position(customMarkerLocationThree).title("Our"));
                mMap.addMarker(new MarkerOptions().position(customMarkerLocationFour).title("Kinsley"));

                //LatLngBound will cover all your marker on Google Maps
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(customMarkerLocationOne); //Taking Point A (First LatLng)
                builder.include(customMarkerLocationThree); //Taking Point B (Second LatLng)
                builder.include(customMarkerLocationTwo); //Taking Point A (First LatLng)
                builder.include(customMarkerLocationFour); //Taking Point B (Second LatLng)
                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
                mMap.moveCamera(cu);
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            }
        });
    }
}
