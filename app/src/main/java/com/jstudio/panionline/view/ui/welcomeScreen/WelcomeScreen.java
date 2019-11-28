package com.jstudio.panionline.view.ui.welcomeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jstudio.panionline.R;
import com.jstudio.panionline.databinding.ActivityWelcomeScreenBinding;
import com.jstudio.panionline.view.ui.login.LoginActivity;

public class WelcomeScreen extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ActivityWelcomeScreenBinding mBinding;
    private int mPagerIndicatorCount;
    private CustomPagerAdapter mCustomPagerAdapter;
    private ImageView[] mIndicatorArray;

    int[] mResources = {
            R.drawable.slider2,
            R.drawable.slider1,
            R.drawable.slider3
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mCustomPagerAdapter = new CustomPagerAdapter(this);
        mBinding.pager.setAdapter(mCustomPagerAdapter);
        mBinding.pager.setCurrentItem(0);
        mBinding.pager.addOnPageChangeListener(this);
        setUiPageViewController();
        initClickListeners();
    }

    /**
     * Starts the Tutorial Activity Using Intent
     *
     * @param context context of the current activity
     */
    public static void startTutorialActivity(Context context) {
        context.startActivity(createIntent(context));
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, WelcomeScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    /**
     * All click events of the app handled here
     */
    private void initClickListeners() {
        mBinding.agentBtn.setOnClickListener(this);
        mBinding.clientBtn.setOnClickListener(this);
        mBinding.businessBtn.setOnClickListener(this);
    }


    private void setUiPageViewController() {
        mPagerIndicatorCount = mCustomPagerAdapter.getCount();
        mIndicatorArray = new ImageView[mPagerIndicatorCount];

        for (int i = 0; i < mPagerIndicatorCount; i++) {
            mIndicatorArray[i] = new ImageView(this);
            mIndicatorArray[i].setImageDrawable(getResources().getDrawable(R.drawable.shape_unselected_pager_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            mBinding.tutorialPagerCircleIndicator.addView(mIndicatorArray[i], params);
        }

        if (mIndicatorArray.length > 0)
            mIndicatorArray[0].setImageDrawable(getResources().getDrawable(R.drawable.shape_selected_pager_dot));
    }

    @Override
    public void onPageScrolled(int position, float v, int i1) {
       /* if(position==4) {
            mBinding.getStartedBtn.setVisibility(View.VISIBLE);
            mBinding.tutorialPagerCircleIndicator.setVisibility(View.GONE);
        }
        else {
            mBinding.getStartedBtn.setVisibility(View.GONE);
            mBinding.tutorialPagerCircleIndicator.setVisibility(View.VISIBLE);

        }*/
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < mPagerIndicatorCount; i++) {
            mIndicatorArray[i].setImageDrawable(getResources().getDrawable(R.drawable.shape_unselected_pager_dot));
        }

        mIndicatorArray[position].setImageDrawable(getResources().getDrawable(R.drawable.shape_selected_pager_dot));

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {

                case R.id.agent_btn:

                    break;

                case R.id.client_btn:
                    LoginActivity.startLoginActivity(this);
                    break;

                case R.id.business_btn:

                    break;
            }
        }
    }


    private class CustomPagerAdapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((FrameLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
            ImageView imageView = itemView.findViewById(R.id.imageView);
            TextView tutorial_tv = itemView.findViewById(R.id.tutorial_tv);
            imageView.setImageResource(mResources[position]);
            //tutorial_tv.setText(mStrings[position]);
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((FrameLayout) object);
        }
    }


}
