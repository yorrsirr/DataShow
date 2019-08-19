package com.xq.datashow;

import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

public class LoudouActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private ImageView mBackImageView;
    private WebView mJibenloudoutuWV, mDingzhiloudoutuWV;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loudou);

        setStatusBar();

        initViews();
    }

    private void initViews() {
        mBackImageView = findViewById(R.id.iv_loudou_back);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mJibenloudoutuWV = findViewById(R.id.jibenloudoutu);
        mJibenloudoutuWV.getSettings().setJavaScriptEnabled(true);
        mJibenloudoutuWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mJibenloudoutuWV.getSettings().setAppCacheEnabled(true);
        mJibenloudoutuWV.loadUrl("file:///android_asset/LD_jibenloudoutu");

        mDingzhiloudoutuWV = findViewById(R.id.dingzhiloudoutu);
        mDingzhiloudoutuWV.getSettings().setJavaScriptEnabled(true);
        mDingzhiloudoutuWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mDingzhiloudoutuWV.getSettings().setAppCacheEnabled(true);
        mDingzhiloudoutuWV.loadUrl("file:///android_asset/LD_dingzhiloudoutu");

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_loudou);
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
