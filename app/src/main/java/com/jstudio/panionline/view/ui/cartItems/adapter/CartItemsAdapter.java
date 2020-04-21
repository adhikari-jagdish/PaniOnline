package com.jstudio.panionline.view.ui.cartItems.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.CartListItemLayoutBinding;
import com.jstudio.panionline.model.eventbus.CalculatePriceEvent;
import com.jstudio.panionline.service.database.CartDataSource;
import com.jstudio.panionline.service.database.CartDatabase;
import com.jstudio.panionline.service.database.CartItem;
import com.jstudio.panionline.service.database.LocalCartDataSource;
import com.jstudio.panionline.view.ui.cartItems.Interface.OnImageViewAdapterClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.CartItemHolder> {

    private Context context;
    private List<CartItem> cartItemList;
    private CartItem cartItemObj;
    private CartDataSource cartDataSource;

    public CartItemsAdapter(Context context, List<CartItem> cartItemList) {
        this.context = context;
        this.cartItemList = cartItemList;
        cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(context).cartDAO());
    }

    @NonNull
    @Override
    public CartItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartListItemLayoutBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cart_list_item_layout, parent, false);
        return new CartItemsAdapter.CartItemHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemHolder holder, int position) {
        cartItemObj = cartItemList.get(position);
        holder.binding.setCartItems(cartItemObj);
        holder.binding.txtCartItemsVal.setText(String.valueOf(cartItemObj.getProductQuantity()));

        int finalPrice = cartItemList.get(position).getProductPrice() * cartItemList.get(position).getProductQuantity();

        holder.setListener((view, position1, isDecrease, isDelete) -> {
            if (!isDelete) {
                if (isDecrease) {
                    if (cartItemList.get(position1).getProductQuantity() > 1)
                        cartItemList.get(position1).setProductQuantity(cartItemList.get(position1).getProductQuantity() - 1);
                    else if (cartItemList.get(position1).getProductQuantity() == 1)
                        deleteCartItems(holder, position1);
                } else {
                    if (cartItemList.get(position1).getProductQuantity() < 99)
                        cartItemList.get(position1).setProductQuantity(cartItemList.get(position1).getProductQuantity() + 1);
                }

                //Update Cart
                cartDataSource.updateCart(cartItemList.get(position1))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(Integer integer) {
                                holder.binding.txtCartItemsVal.setText(String.valueOf(cartItemList.get(position1).getProductQuantity()));
                                EventBus.getDefault().postSticky(new CalculatePriceEvent());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(context, "[UPDATE_CART]" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                deleteCartItems(holder, position1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }


    public class CartItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CartListItemLayoutBinding binding;
        private OnImageViewAdapterClickListener listener;

        CartItemHolder(@NonNull CartListItemLayoutBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
            this.binding.cartAddIcon.setOnClickListener(this);
            this.binding.cartRemoveIcon.setOnClickListener(this);
            this.binding.imgCartItemTopCross.setOnClickListener(this);
        }

        public void setListener(OnImageViewAdapterClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            if (v != null) {
                switch (v.getId()) {
                    case R.id.cart_add_icon:
                        listener.onCalculatePriceListener(itemView, getAdapterPosition(), false, false);
                        break;

                    case R.id.cart_remove_icon:
                        listener.onCalculatePriceListener(itemView, getAdapterPosition(), true, false);
                        break;

                    case R.id.img_cart_item_top_cross:
                        listener.onCalculatePriceListener(itemView, getAdapterPosition(), true, true);
                        break;
                }
            }
        }
    }

    public void deleteCartItems(CartItemHolder holder, int position) {
        cartDataSource.deleteCart(cartItemList.get(position))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        notifyItemRemoved(holder.getAdapterPosition());
                        EventBus.getDefault().postSticky(new CalculatePriceEvent());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "[DELETE_CART]" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
