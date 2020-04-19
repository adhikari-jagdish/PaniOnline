package com.jstudio.panionline.view.ui.user.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.HomeItemsCellLayoutBinding;
import com.jstudio.panionline.model.ProductListResponse;
import com.jstudio.panionline.view.ui.user.itemDetails.ItemDetails;

import java.util.List;

public class HomeItemsAdapter extends RecyclerView.Adapter<HomeItemsAdapter.HomeItemViewHolder> {
    private Context mContext;
    private List<ProductListResponse.DataBean> productList;
    private ProductListResponse.DataBean product;

    public HomeItemsAdapter(Context mContext, List<ProductListResponse.DataBean> products) {
        this.mContext = mContext;
        this.productList = products;
    }

    @NonNull
    @Override
    public HomeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HomeItemsCellLayoutBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.home_items_cell_layout, parent, false);
        return new HomeItemsAdapter.HomeItemViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemViewHolder holder, int position) {
        product = productList.get(position);
        holder.binding.setProduct(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class HomeItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private HomeItemsCellLayoutBinding binding;

        public HomeItemViewHolder(@NonNull HomeItemsCellLayoutBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
            this.binding.itemsCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v != null) {
                switch (v.getId()) {
                    case R.id.items_card:
                        ItemDetails.startItemDetailsActivity(mContext, productList.get(getAdapterPosition()));
                        break;
                }
            }
        }
    }
}
