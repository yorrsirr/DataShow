package com.xq.datashow;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xq.datashow.Adapter.CarSetAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BrandDetailActivity extends AppCompatActivity {
    private static final String TAG = "BrandDetailActivity";

    private ImageView mBackImagView;
    private TextView mBrandNameTV, mCarSetCountTV, mPriceTV;
    private ListView mListView;

    private String brandName;

    private String carSetCount = "暂无", carSetPrice = "暂无";
    private List<CarSet> carSetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_detail);

        getBrandNameFromSearchBrandActivity();

        initViews();

        getBrandDetailData();

        getCarSetData();

        setStatusBar();
    }

    //用于对从品牌名字进行排序的辅助类
    class SortByName implements Comparator {
        public int compare(Object o1, Object o2) {
            CarSet s1 = (CarSet) o1;
            CarSet s2 = (CarSet) o2;
            return s1.getCarSetName().compareTo(s2.getCarSetName());
        }
    }

    private void getBrandNameFromSearchBrandActivity() {
        Intent intent = getIntent();
        brandName = intent.getStringExtra("brandName");
        Log.d(TAG, "getBrandNameFromSearchBrandActivity: brandName: " + brandName);
    }


    private void getCarSetData() {
        final String urlForCarSet = "http://193.112.22.179/dataview/find/carinfo.do?clickStr=" + brandName;

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url(urlForCarSet).build();
                    Response response = okHttpClient.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObjectCarSet(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObjectCarSet (String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            String dataString = jsonObject.getString("data");
            JSONArray jsonArray = new JSONArray(dataString);
            carSetList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i ++) {
                JSONObject jsonObjectCarSet = new JSONObject(jsonArray.get(i).toString());
                String name, price;
                name = jsonObjectCarSet.getString("car_set_name");
                price = jsonObjectCarSet.getString("price");
                if (!price.equals("暂无报价")) {
                    carSetList.add(new CarSet(brandName, name, price));
                }

            }

            //对carSetList进行排序
//            Collections.sort(carSetList, new SortByName());

            //更新UI
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final CarSetAdapter carSetAdapter = new CarSetAdapter(BrandDetailActivity.this, R.layout.car_set_item, carSetList);
                    mListView.setAdapter(carSetAdapter);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            CarSet carSet = carSetList.get(i);
                            Toast.makeText(BrandDetailActivity.this, carSet.getCarSetName(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(BrandDetailActivity.this, CarSetActivity.class);
                            intent.putExtra("carSetName", carSet.getCarSetName());
                            startActivity(intent);
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getBrandDetailData() {
        final String urlForBrandDetail = "http://193.112.22.179/dataview/find/getwhole.do?clickStr=" + brandName;

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient okHttpClient1 = new OkHttpClient();
                    Request request1 = new Request.Builder().url(urlForBrandDetail).build();
                    Response response = okHttpClient1.newCall(request1).execute();
                    String responseData1 = response.body().string();
                    parseJSONWithJSONObjectBrandDetail(responseData1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObjectBrandDetail(String jsonData) {
        try {
            JSONObject jsonObject1 = new JSONObject(jsonData);
            String dataString = jsonObject1.getString("data");
            JSONObject jsonObject2 = new JSONObject(dataString);
            carSetCount = jsonObject2.getString("car_set_count");
            carSetPrice = jsonObject2.getString("price_range");


            //更新UI
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mCarSetCountTV.setText(carSetCount);
                    mPriceTV.setText(carSetPrice);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViews() {
        mBackImagView = findViewById(R.id.iv_brand_detail_back);
        mBackImagView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBrandNameTV = findViewById(R.id.tv_brand_detail_brand_name);
        mBrandNameTV.setText(brandName);

        mCarSetCountTV = findViewById(R.id.tv_brand_detail_count);
        mPriceTV = findViewById(R.id.tv_brand_detail_price);


        mListView = findViewById(R.id.lv_brand_detail);

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
