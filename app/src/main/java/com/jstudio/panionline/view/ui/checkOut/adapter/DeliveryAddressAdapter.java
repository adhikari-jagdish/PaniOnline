package com.jstudio.panionline.view.ui.checkOut.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.DeliveryAddressCardLayoutBinding;
import com.jstudio.panionline.model.AddressListResponse;

import java.util.List;

public class DeliveryAddressAdapter extends RecyclerView.Adapter<DeliveryAddressAdapter.AddressViewHolder> {
    private Context mContext;
    private List<AddressListResponse.DataBean> mAddressList;
    private AddressListResponse.DataBean addressObj;
    private int lastSelectedPosition = -1;

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
        holder.binding.imgBottomCheck.setChecked(lastSelectedPosition == position);

    }

    @Override
    public int getItemCount() {
        return mAddressList.size();
    }

    public int getTotalAddressCount() {
        return mAddressList.size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private DeliveryAddressCardLayoutBinding binding;

        public AddressViewHolder(@NonNull DeliveryAddressCardLayoutBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
            this.binding.itemsCard.setOnClickListener(this);
            this.binding.rightThreeDots.setOnClickListener(this);
            this.binding.imgBottomCheck.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v != null) {
                switch (v.getId()) {
                    case R.id.right_three_dots:
                        showMenu(this.binding.rightThreeDots);
                        break;

                    case R.id.img_bottom_check:
                        lastSelectedPosition = getAdapterPosition();
                        notifyDataSetChanged();
                        getSelectedAddress(this.binding.txtAddress.getText().toString().trim());
                        break;
                }
            }
        }
    }

    public String getSelectedAddress(String selectedAddress) {
        return selectedAddress;
    }

    private void showMenu(View view) {
        PopupMenu popup = new PopupMenu(mContext, view);
        popup.getMenuInflater().inflate(R.menu.address_option_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            Toast.makeText(mContext,
                    "Clicked popup menu item " + item.getTitle(),
                    Toast.LENGTH_SHORT).show();
            return true;
        });
        popup.show();
    }
}
