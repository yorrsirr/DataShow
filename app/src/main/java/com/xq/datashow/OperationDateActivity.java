package com.xq.datashow;

import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

public class OperationDateActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private ImageView mBackImageView;
    private ArcView mCTRArvView, mBounceRateArvView;
    private WebView mNewUserWV, mPvUvWV, mProvinceWV, mEndUserWV, mDayEndWV;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_date);

        setStatusBar();

        initViews();
    }

    private void initViews() {
        mBackImageView = findViewById(R.id.iv_operation_back);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mCTRArvView = findViewById(R.id.av_operation_ctr);
        mCTRArvView.setValues(0, 100, 85, "CTR");

        mBounceRateArvView = findViewById(R.id.av_operation_bounce_rate);
        mBounceRateArvView.setValues(0, 100, 66, "Bounce Tate");

        mNewUserWV = findViewById(R.id.wv_operation_new_user);
        mNewUserWV.getSettings().setJavaScriptEnabled(true);
        mNewUserWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mNewUserWV.getSettings().setAppCacheEnabled(true);
        mNewUserWV.loadUrl("file:///android_asset/YY_new_user");

        mPvUvWV = findViewById(R.id.wv_operation_pv_uv);
        mPvUvWV.getSettings().setJavaScriptEnabled(true);
        mPvUvWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mPvUvWV.getSettings().setAppCacheEnabled(true);
        mPvUvWV.loadUrl("file:///android_asset/YY_pv_uv");

        mProvinceWV = findViewById(R.id.wv_operation_province);
        mProvinceWV.getSettings().setJavaScriptEnabled(true);
        mProvinceWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mProvinceWV.getSettings().setAppCacheEnabled(true);
        mProvinceWV.loadUrl("file:///android_asset/YY_province");

        mEndUserWV = findViewById(R.id.wv_operation_end_user);
        mEndUserWV.getSettings().setJavaScriptEnabled(true);
        mEndUserWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mEndUserWV.getSettings().setAppCacheEnabled(true);
        mEndUserWV.loadUrl("file:///android_asset/YY_end_user");

        mDayEndWV = findViewById(R.id.wv_operation_day_end);
        mDayEndWV.getSettings().setJavaScriptEnabled(true);
        mDayEndWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mDayEndWV.getSettings().setAppCacheEnabled(true);
        mDayEndWV.loadUrl("file:///android_asset/YY_day_end");

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_operation_data);
        mRefreshLayout.setOnRefreshListener(this);
        //设置刷新啥时候的小圈圈的颜色，最多设置4种
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        mRefreshLayout.setDistanceToTriggerSync(300);
    }

    //设置沉浸式状态栏
    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public void onRefresh() {
        initViews();
        mRefreshLayout.setRefreshing(false);
    }
}
