package com.xq.datashow;

import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

public class BingtuActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private ImageView mBackImageView;
    private WebView mPutongbingtuWV, mKegundongbingtuWV, mTiantianquantuWv, mQiantaobingtuWV, mNandinggeermeiguituWV;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bingtu);

        setStatusBar();

        initViews();

    }

    private void initViews() {
        mBackImageView = findViewById(R.id.iv_bingtu_back);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mKegundongbingtuWV = findViewById(R.id.kegundongbingtu);
        mKegundongbingtuWV.getSettings().setJavaScriptEnabled(true);
        mKegundongbingtuWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mKegundongbingtuWV.getSettings().setAppCacheEnabled(true);
        mKegundongbingtuWV.loadUrl("file:///android_asset/BT_kegundongbingtu");

        mPutongbingtuWV = findViewById(R.id.putongbingtu);
        mPutongbingtuWV.getSettings().setJavaScriptEnabled(true);
        mPutongbingtuWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mPutongbingtuWV.getSettings().setAppCacheEnabled(true);
        mPutongbingtuWV.loadUrl("file:///android_asset/BT_putongbingtu");

        mTiantianquantuWv = findViewById(R.id.tiantianquantu);
        mTiantianquantuWv.getSettings().setJavaScriptEnabled(true);
        mTiantianquantuWv.getSettings().setDefaultTextEncodingName("UTF-8");
        mTiantianquantuWv.getSettings().setAppCacheEnabled(true);
        mTiantianquantuWv.loadUrl("file:///android_asset/BT_tiantianquantu");

        mQiantaobingtuWV = findViewById(R.id.qiantaobingtu);
        mQiantaobingtuWV.getSettings().setJavaScriptEnabled(true);
        mQiantaobingtuWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mQiantaobingtuWV.getSettings().setAppCacheEnabled(true);
        mQiantaobingtuWV.loadUrl("file:///android_asset/BT_qiantaobingtu");

        mNandinggeermeiguituWV = findViewById(R.id.nandinggeermeiguitu);
        mNandinggeermeiguituWV.getSettings().setJavaScriptEnabled(true);
        mNandinggeermeiguituWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mNandinggeermeiguituWV.getSettings().setAppCacheEnabled(true);
        mNandinggeermeiguituWV.loadUrl("file:///android_asset/BT_nandinggeermeiguitu");

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_bingtu);
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
