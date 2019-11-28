package com.jstudio.panionline.view.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.jstudio.panionline.R;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected BaseActivity mThis;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mThis = this;
    }

    public void setBackEnabled_Title(boolean isBackEnabled, String Title) {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setVisibility(View.VISIBLE);
        LinearLayout rightIconContainer = findViewById(R.id.right_icon_llyt);

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
        rightIconContainer.setVisibility(View.GONE);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.toolbar_back_image:
                onBackPressed();
                break;*/
        }
    }
}
