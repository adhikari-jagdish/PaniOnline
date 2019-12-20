package com.jstudio.panionline.view.ui.user.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.HomeItemsCellLayoutBinding;

public class HomeItemsAdapter extends RecyclerView.Adapter<HomeItemsAdapter.HomeItemViewHolder> {
    private Context mContext;


    public HomeItemsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public HomeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HomeItemsCellLayoutBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.home_items_cell_layout, parent, false);
        return new HomeItemsAdapter.HomeItemViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class HomeItemViewHolder extends RecyclerView.ViewHolder {
        private HomeItemsCellLayoutBinding binding;

        public HomeItemViewHolder(@NonNull HomeItemsCellLayoutBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void bind(int position) {

        }
    }
}
