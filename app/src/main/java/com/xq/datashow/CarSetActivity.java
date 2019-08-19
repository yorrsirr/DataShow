package com.xq.datashow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CarSetActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = "CarSetActivity";

    private ImageView mBackImageView;
    private TextView mCarSetNameTV, mPriceTv;
    private WebView mPriceWV, mGradeWV, mPurposeWV;
    private GradeArcView mGradeArcView;

    private String carSetName;

    private List<String> carSetNameList, carSetPriceList, carSetIndexList, carSetGradeList, carSetPurposeCountList;
    private List<CarSetPurposeBean.NameValue> nameValueList;

    private String averageGrade;

    private CarSetPriceBean carSetPriceBean;
    private CarSetGradeBean carSetGradeBean;
    private CarSetPurposeBean carSetPurposeBean;

    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_set);

        setStatusBar();

        getCarSetNameFromBrandDetailActivity();

        initViews();

        getData();
    }

    private void getCarSetNameFromBrandDetailActivity() {
        Intent intent = getIntent();
        carSetName = intent.getStringExtra("carSetName");
//        carSetName = "奥迪A3";
        Log.d(TAG, "getCarSetNameFromBrandDetailActivity: carSetName: " + carSetName);
    }

    private void initList() {
        carSetIndexList = new ArrayList<String>();
        carSetIndexList.add("空间评分");
        carSetIndexList.add("动力评分");
        carSetIndexList.add("动力评分");
        carSetIndexList.add("油耗评分");
        carSetIndexList.add("舒适度评分");
        carSetIndexList.add("外观评分");
        carSetIndexList.add("性价比评分");

//        carSetPurposeList = new ArrayList<>();
//        carSetPurposeList.add("工作");
//        carSetPurposeList.add("购物");
//        carSetPurposeList.add("旅游");
//        carSetPurposeList.add("接送小孩");
//        carSetPurposeList.add("跑长途");
    }

    private void getData() {
        final String urlForCarSet = "http://193.112.22.179/dataview/detail/info.do?clickStr=" + carSetName;

        carSetNameList = new ArrayList<>();
        carSetPriceList = new ArrayList<>();
        carSetIndexList = new ArrayList<>();
        carSetGradeList = new ArrayList<>();
        carSetPurposeCountList = new ArrayList<>();
        nameValueList = new ArrayList<>();

        initList();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url(urlForCarSet).build();
                    Response response = okHttpClient.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject jsonObjectData = new JSONObject(jsonObject.getString("data"));
            JSONObject jsonObjectGrade = new JSONObject(jsonObjectData.getString("grade_every_model"));
            JSONObject jsonObjectPurpose = new JSONObject(jsonObjectData.getString("purchase_pourse"));
            JSONArray jsonArray = new JSONArray(jsonObjectData.getJSONArray("price_every_type").toString());

            for (int i = 0; i < jsonArray.length(); i ++) {
                JSONObject jsonObjectPrice = new JSONObject(jsonArray.get(i).toString());
                carSetNameList.add(jsonObjectPrice.getString("car_type_name"));
                carSetPriceList.add(jsonObjectPrice.getString("car_price"));
            }

            carSetGradeList.add(jsonObjectGrade.getString("spaceGrade"));
            carSetGradeList.add(jsonObjectGrade.getString("powerGrade"));
            carSetGradeList.add(jsonObjectGrade.getString("controlGrade"));
            carSetGradeList.add(jsonObjectGrade.getString("oilwearGrade"));
            carSetGradeList.add(jsonObjectGrade.getString("comfortGrade"));
            carSetGradeList.add(jsonObjectGrade.getString("facedeGrade"));
            carSetGradeList.add(jsonObjectGrade.getString("costperformanceGrade"));

//            carSetPurposeCountList.add("{value:" + jsonObjectPurpose.getString("workcount") + ", name:'工作'}");
//            carSetPurposeCountList.add("{value:" + jsonObjectPurpose.getString("shopcount") + ", name:'购物'}");
//            carSetPurposeCountList.add("{value:" + jsonObjectPurpose.getString("travelcount") + ", name:'旅游'}");
//            carSetPurposeCountList.add("{value:" + jsonObjectPurpose.getString("childcount") + ", name:'接送小孩'}");
//            carSetPurposeCountList.add("{value:" + jsonObjectPurpose.getString("longwaycount") + ", name:'跑长途'}");

            nameValueList.add(new CarSetPurposeBean.NameValue("工作", Double.valueOf(jsonObjectPurpose.getString("workcount"))));
            nameValueList.add(new CarSetPurposeBean.NameValue("购物", Double.valueOf(jsonObjectPurpose.getString("shopcount"))));
            nameValueList.add(new CarSetPurposeBean.NameValue("旅游", Double.valueOf(jsonObjectPurpose.getString("travelcount"))));
            nameValueList.add(new CarSetPurposeBean.NameValue("接送小孩", Double.valueOf(jsonObjectPurpose.getString("childcount"))));
            nameValueList.add(new CarSetPurposeBean.NameValue("跑长途", Double.valueOf(jsonObjectPurpose.getString("longwaycount"))));

            //求得综合评分
            float floatAverageGrade = (float)0.0;
            float allGrade = (float)0.0;
            for (String each : carSetGradeList) {
                float eachGrade = Float.valueOf(each);
                allGrade += eachGrade;
            }
            floatAverageGrade = allGrade / (float)7.0;
            DecimalFormat df2 = new DecimalFormat("###.0");
//            df2.format(floatAverageGrade);

            final String finalFloatAverageGrade = df2.format(floatAverageGrade);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mGradeArcView.setValues(0, 5, finalFloatAverageGrade, "综合评分");
                }
            });
            averageGrade = String.valueOf(finalFloatAverageGrade);

            //求得报价
            float floatLowerPrice = Float.valueOf(carSetPriceList.get(0));
            float floatHigherPrice = Float.valueOf(carSetPriceList.get(0));
            for (String each : carSetPriceList) {
                float temp = Float.valueOf(each);
                 if (temp < floatLowerPrice) {
                     floatLowerPrice = temp;
                 }
                 if (temp > floatHigherPrice) {
                     floatHigherPrice = temp;
                 }
            }
            final float finalFloatLowerPrice = floatLowerPrice;
            final float finalFloatHigherPrice = floatHigherPrice;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPriceTv.setText(String.valueOf(finalFloatLowerPrice) + "-" + String.valueOf(finalFloatHigherPrice));
                }
            });

            //各配置车型报价
            carSetPriceBean = new CarSetPriceBean(carSetNameList, carSetPriceList);
            final String jsonPrice = new Gson().toJson(carSetPriceBean);

            //各指标评分
            carSetGradeBean = new CarSetGradeBean(carSetIndexList, carSetGradeList);
            final String jsonGrade = new Gson().toJson(carSetGradeBean);

            //购车目的
            carSetPurposeBean = new CarSetPurposeBean(nameValueList);
            final String jsonPurpose = new Gson().toJson(carSetPurposeBean);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //各配置车型指导价WebView
//                    mPriceWV.setWebViewClient(new WebViewClient() {
//                        @Override
//                        public void onPageFinished(WebView view, String url) {
//                            mPriceWV.loadUrl("javascript:showprice(" + jsonPrice + ");");
//                            super.onPageFinished(view, url);
//                        }
//
//                        @Override
//                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                            super.onPageStarted(view, url, favicon);
//                        }
//
//                        @Override
//                        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                            return super.shouldOverrideUrlLoading(view, request);
//                        }
//                    });
                    mPriceWV.loadUrl("javascript:showprice(" + jsonPrice + ");");

                    //各指标评分WebView
//                    mGradeWV.setWebViewClient(new WebViewClient() {
//                        @Override
//                        public void onPageFinished(WebView view, String url) {
//                            mGradeWV.loadUrl("javascript:showgrade(" + jsonGrade + ");");
//                            super.onPageFinished(view, url);
//                        }
//
//                        @Override
//                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                            super.onPageStarted(view, url, favicon);
//                        }
//
//                        @Override
//                        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                            return super.shouldOverrideUrlLoading(view, request);
//                        }
//                    });
                    mGradeWV.loadUrl("javascript:showgrade(" + jsonGrade + ");");

                    //购车目的饼图WebView
//                    mPurposeWV.setWebViewClient(new WebViewClient() {
//                        @Override
//                        public void onPageFinished(WebView view, String url) {
//
//                            super.onPageFinished(view, url);
//                        }
//
//                        @Override
//                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                            super.onPageStarted(view, url, favicon);
//                        }
//
//                        @Override
//                        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                            return super.shouldOverrideUrlLoading(view, request);
//                        }
//                    });
                    mPurposeWV.loadUrl("javascript:showpurpose(" + jsonPurpose + ");");
                }

            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initViews() {
        mBackImageView = findViewById(R.id.iv_car_set_back);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sv_cat_set);
        mRefreshLayout.setOnRefreshListener(this);
        //设置刷新啥时候的小圈圈的颜色，最多设置4种
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        mRefreshLayout.setDistanceToTriggerSync(300);

        mPriceTv = findViewById(R.id.tv_car_set_price);
        mCarSetNameTV = findViewById(R.id.tv_car_set_name);
        mCarSetNameTV.setText(carSetName);

        mGradeArcView = findViewById(R.id.av_car_set_grade);

        mPriceWV = findViewById(R.id.wv_car_set_price);
        mPriceWV.getSettings().setJavaScriptEnabled(true);
        mPriceWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mPriceWV.getSettings().setAppCacheEnabled(true);
        mPriceWV.loadUrl("file:///android_asset/CarSet_price");

        mGradeWV = findViewById(R.id.wv_car_set_grade);
        mGradeWV.getSettings().setJavaScriptEnabled(true);
        mGradeWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mGradeWV.getSettings().setAppCacheEnabled(true);
        mGradeWV.loadUrl("file:///android_asset/CarSet_grade");

        mPurposeWV = findViewById(R.id.wv_car_set_purpose);
        mPurposeWV.getSettings().setJavaScriptEnabled(true);
        mPurposeWV.getSettings().setDefaultTextEncodingName("UTF-8");
        mPurposeWV.getSettings().setAppCacheEnabled(true);
        mPurposeWV.loadUrl("file:///android_asset/CarSet_purpose");
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
        getData();
        mRefreshLayout.setRefreshing(false);
    }
}
