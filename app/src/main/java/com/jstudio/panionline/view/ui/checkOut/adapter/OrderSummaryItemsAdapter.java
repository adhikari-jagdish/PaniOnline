package com.jstudio.panionline.view.ui.checkOut.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.SummaryItemsLayoutBinding;
import com.jstudio.panionline.service.database.CartItem;

import java.util.List;

public class OrderSummaryItemsAdapter extends RecyclerView.Adapter<OrderSummaryItemsAdapter.OrderSummaryViewHolder> {
    private Context mContext;
    private List<CartItem> mCartItemList;
    private CartItem cartItemObj;

    public OrderSummaryItemsAdapter(Context mContext, List<CartItem> cartItemList) {
        this.mContext = mContext;
        this.mCartItemList = cartItemList;
    }

    @NonNull
    @Override
    public OrderSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SummaryItemsLayoutBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.summary_items_layout, parent, false);
        return new OrderSummaryItemsAdapter.OrderSummaryViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSummaryViewHolder holder, int position) {
        cartItemObj = mCartItemList.get(position);
        holder.binding.setCartItems(cartItemObj);
    }

    @Override
    public int getItemCount() {
        return mCartItemList.size();
    }

    public class OrderSummaryViewHolder extends RecyclerView.ViewHolder {
        private SummaryItemsLayoutBinding binding;

        public OrderSummaryViewHolder(@NonNull SummaryItemsLayoutBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
