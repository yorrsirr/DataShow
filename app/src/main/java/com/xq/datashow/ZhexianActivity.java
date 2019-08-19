package com.xq.datashow;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class ZhexianActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private ImageView mBackImageView;
    private WebView mZhibenzhexianWV, mChuizhizhexianWV, mDuijimianjiWV, mPinghuaquxian, mDabilimianjiWV;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhexian);

        setStatusBar();

        initViews();
    }

    @SuppressLint("ResourceAsColor")
    private void initViews() {
        mBackImageView = findViewById(R.id.iv_zhexian_back);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mZhibenzhexianWV = findViewById(R.id.jibenzhexian);
        mZhibenzhexianWV.getSettings().setJavaScriptEnabled(true);
        mZhibenzhexianWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mZhibenzhexianWV.getSettings().setAppCacheEnabled(true);
        mZhibenzhexianWV.loadUrl("file:///android_asset/ZX_jiBenZheXian.html");

        mChuizhizhexianWV = findViewById(R.id.chuizhizhexian);
        mChuizhizhexianWV.getSettings().setJavaScriptEnabled(true);
        mChuizhizhexianWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mChuizhizhexianWV.loadUrl("file:///android_asset/ZX_chuizhizhexian.html");

        mDuijimianjiWV = findViewById(R.id.duijimianji);
        mDuijimianjiWV.getSettings().setJavaScriptEnabled(true);
        mDuijimianjiWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mDuijimianjiWV.loadUrl("file:///android_asset/ZX_duijimianji.html");

        mPinghuaquxian = findViewById(R.id.pinghuaquxian);
        mPinghuaquxian.getSettings().setJavaScriptEnabled(true);
        mPinghuaquxian.getSettings().setDefaultTextEncodingName("UTF-8");
        mPinghuaquxian.loadUrl("file:///android_asset/ZX_pinghuaquxian.html");

        mDabilimianjiWV = findViewById(R.id.dabilimianji);
        mDabilimianjiWV.getSettings().setJavaScriptEnabled(true);
        mDabilimianjiWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mDabilimianjiWV.loadUrl("file:///android_asset/ZX_dabilimianji.html");

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_zhexian);
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
