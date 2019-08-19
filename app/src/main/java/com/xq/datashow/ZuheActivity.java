package com.xq.datashow;

import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

public class ZuheActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private ImageView mBackImageView;
    private WebView mZhexiantu_bingtuWv, mZhuzhuangtu_zhexiantuWV, mZhuzhuangtu_bingtuWV;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zuhe);

        setStatusBar();

        initViews();
    }

    private void initViews() {
        mBackImageView = findViewById(R.id.iv_zuhe_back);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mZhexiantu_bingtuWv = findViewById(R.id.zhexiantu_bingtu);
        mZhexiantu_bingtuWv.getSettings().setJavaScriptEnabled(true);
        mZhexiantu_bingtuWv.getSettings().setDefaultTextEncodingName("UTF-8");
        mZhexiantu_bingtuWv.getSettings().setAppCacheEnabled(true);
        mZhexiantu_bingtuWv.loadUrl("file:///android_asset/ZH_zhexian+bingtu");

        mZhuzhuangtu_zhexiantuWV = findViewById(R.id.zhuzhuangtu_zhexiantu);
        mZhuzhuangtu_zhexiantuWV.getSettings().setJavaScriptEnabled(true);
        mZhuzhuangtu_zhexiantuWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mZhuzhuangtu_zhexiantuWV.getSettings().setAppCacheEnabled(true);
        mZhuzhuangtu_zhexiantuWV.loadUrl("file:///android_asset/ZH_zhuzhuang+zhexian");

        mZhuzhuangtu_bingtuWV = findViewById(R.id.zhuzhuangtu_bingtu);
        mZhuzhuangtu_bingtuWV.getSettings().setJavaScriptEnabled(true);
        mZhuzhuangtu_bingtuWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mZhuzhuangtu_bingtuWV.getSettings().setAppCacheEnabled(true);
        mZhuzhuangtu_bingtuWV.loadUrl("file:///android_asset/ZH_zhuzhuang+bingtu");

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_zuhe);
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
