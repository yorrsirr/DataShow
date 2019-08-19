package com.xq.datashow;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.xq.datashow.Adapter.BrandAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SearchBrandActivity extends AppCompatActivity {

    private static final String TAG = "SearchBrandActivity";

    private ImageView mBackImageView;
    private SearchView mSearchView;
    private ListView mListView;

    private String[] data;
    private List<Brand> brandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_brand);




        getData();

        setStatusBar();

    }

    class SortByName implements Comparator {
        public int compare(Object o1, Object o2) {
            Brand s1 = (Brand) o1;
            Brand s2 = (Brand) o2;
            return s1.getBrandName().compareTo(s2.getBrandName());
        }
    }

    private void getData() {
        final String url = "http://193.112.22.179/dataview/find/getall.do";

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
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
            String dataString = jsonObject.getString("data");
            JSONArray jsonArray = new JSONArray(dataString);
            data = new String[jsonArray.length()];
            brandList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i ++) {
                data[i] = jsonArray.getString(i);
                brandList.add(new Brand(jsonArray.getString(i)));

//                Collections.sort(brandList, new SortByName());
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    initViews();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViews() {

        //listView的测试数据
//        List<Brand> testList = new ArrayList<>();
//        testList.add(new Brand("aaa"));
//        testList.add(new Brand("aab"));
//        testList.add(new Brand("bb"));
//        testList.add(new Brand("cc"));
//        testList.add(new Brand("dd"));
        final BrandAdapter adapter = new BrandAdapter(SearchBrandActivity.this, R.layout.brand_item, brandList);

        mBackImageView = findViewById(R.id.iv_search_brand_back);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mSearchView = findViewById(R.id.sv_search_brand);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!TextUtils.isEmpty(s)){
//                    mListView.setFilterText(s);
                    adapter.getFilter().filter(s);
                }else{
                    mListView.clearTextFilter();
                    adapter.getFilter().filter("");
                }
                return false;
            }
        });

        mListView = findViewById(R.id.lv_search_brand);
        mListView.setAdapter(adapter);
        mListView.setTextFilterEnabled(true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Brand brand = adapter.getList().get(i);
//                Toast.makeText(SearchBrandActivity.this, brand.getBrandName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SearchBrandActivity.this, BrandDetailActivity.class);
                intent.putExtra("brandName", brand.getBrandName());
                startActivity(intent);
            }
        });
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
