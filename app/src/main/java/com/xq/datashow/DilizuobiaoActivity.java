package com.xq.datashow;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

public class DilizuobiaoActivity extends AppCompatActivity {

    private ImageView mBackImageView;
    private WebView mXianlutuWV, mRenkoumidutuWV, mKongqizhiliangtuWV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dilizuobiao);

        setStatusBar();

        initViews();
    }

    private void initViews() {
        mBackImageView = findViewById(R.id.iv_dilizuobiao_back);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mXianlutuWV = findViewById(R.id.xianlutu);
        mXianlutuWV.getSettings().setJavaScriptEnabled(true);
        mXianlutuWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mXianlutuWV.getSettings().setAppCacheEnabled(true);
        mXianlutuWV.loadUrl("file:///android_asset/DL_xianlutu");
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
}
