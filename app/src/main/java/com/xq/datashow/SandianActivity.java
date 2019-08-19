package com.xq.datashow;

import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

public class SandianActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private ImageView mBackImageView;
    private WebView mJibensandianWV, mQipaoWV, mXianxinghuiguiWV;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandian);

        setStatusBar();

        initViews();
    }

    private void initViews() {
        mBackImageView = findViewById(R.id.iv_sandiantu_back);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mJibensandianWV = findViewById(R.id.jibensandian);
        mJibensandianWV.getSettings().setJavaScriptEnabled(true);
        mJibensandianWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mJibensandianWV.getSettings().setAppCacheEnabled(true);
        mJibensandianWV.loadUrl("file:///android_asset/SD_jibensandiantu");

        mQipaoWV = findViewById(R.id.qipaotu);
        mQipaoWV.getSettings().setJavaScriptEnabled(true);
        mQipaoWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mQipaoWV.getSettings().setAppCacheEnabled(true);
        mQipaoWV.loadUrl("file:///android_asset/SD_ qipaotu");

        mXianxinghuiguiWV = findViewById(R.id.xianxinghuigui);
        mXianxinghuiguiWV.getSettings().setJavaScriptEnabled(true);
        mXianxinghuiguiWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mXianxinghuiguiWV.getSettings().setAppCacheEnabled(true);
        mXianxinghuiguiWV.loadUrl("file:///android_asset/SD_xianxinghuiguitu");

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_sandian);
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
