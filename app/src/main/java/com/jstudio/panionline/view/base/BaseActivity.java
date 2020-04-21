package com.jstudio.panionline.view.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.jstudio.panionline.R;
import com.jstudio.panionline.view.ui.cartItems.CartItemsActivity;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected BaseActivity mThis;
    private int cartCount = 0;
    private final String TAG = getClass().getSimpleName();
    private TextView cartBadge;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mThis = this;
    }

    protected void setValuetoBadge(int itemVal) {
        cartBadge.setText(String.valueOf(itemVal));
    }

    public void setBackEnabled_Title(boolean isBackEnabled, String Title) {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setVisibility(View.VISIBLE);

        ImageView backArrow = findViewById(R.id.toolbar_back_image);
        TextView title = findViewById(R.id.title_tv);
        TextView rightTv = findViewById(R.id.right_tv);
        rightTv.setVisibility(View.GONE);
        if (backArrow != null) {
            if (isBackEnabled) {
                backArrow.setVisibility(View.VISIBLE);
            } else {
                backArrow.setVisibility(View.GONE);
            }
            title.setVisibility(View.VISIBLE);
            title.setText(Title);
        }
        backArrow.setOnClickListener(this);
    }

    public void setBackEnabled_Title(boolean isBackEnabled, String Title, boolean isRightIconEnabled) {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setVisibility(View.VISIBLE);
        ImageView rightIcon = findViewById(R.id.toolbar_right_img);
        cartBadge = findViewById(R.id.cart_badge);

        ImageView backArrow = findViewById(R.id.toolbar_back_image);
        TextView title = findViewById(R.id.title_tv);

        if (backArrow != null) {
            if (isBackEnabled) {
                backArrow.setVisibility(View.VISIBLE);
            } else {
                backArrow.setVisibility(View.GONE);
            }
            title.setVisibility(View.VISIBLE);
            title.setText(Title);
        }
        if (isRightIconEnabled) {
            rightIcon.setVisibility(View.VISIBLE);
            cartBadge.setVisibility(View.VISIBLE);
        } else
            rightIcon.setVisibility(View.GONE);
        backArrow.setOnClickListener(this);
        rightIcon.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back_image:
                onBackPressed();
                break;

            case R.id.toolbar_right_img:
                Log.d(TAG, "RightClick===>");
                CartItemsActivity.startCartItemsActivity(this);
                break;


        }
    }
}
