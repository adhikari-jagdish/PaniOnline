package com.jstudio.panionline.view.ui.checkOut.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.DeliveryAddressCardLayoutBinding;
import com.jstudio.panionline.databinding.HomeItemsCellLayoutBinding;
import com.jstudio.panionline.model.AddressListResponse;
import com.jstudio.panionline.view.ui.user.home.adapter.HomeItemsAdapter;

import java.util.List;

public class DeliveryAddressAdapter extends RecyclerView.Adapter<DeliveryAddressAdapter.AddressViewHolder> {
    private Context mContext;
    private List<AddressListResponse.DataBean> mAddressList;
    private AddressListResponse.DataBean addressObj;

    public DeliveryAddressAdapter(Context mContext, List<AddressListResponse.DataBean> addressList) {
        this.mContext = mContext;
        this.mAddressList = addressList;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DeliveryAddressCardLayoutBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.delivery_address_card_layout, parent, false);
        return new DeliveryAddressAdapter.AddressViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
       addressObj = mAddressList.get(position);
       holder.binding.setAddress(addressObj);
    }

    @Override
    public int getItemCount() {
        return mAddressList.size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private DeliveryAddressCardLayoutBinding binding;

        public AddressViewHolder(@NonNull DeliveryAddressCardLayoutBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
            this.binding.itemsCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v != null) {
                switch (v.getId()) {

                }
            }
        }
    }
}
