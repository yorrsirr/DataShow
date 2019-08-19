package com.xq.datashow;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xq.datashow.Adapter.MyFragmentPagerAdapter;
import com.xq.datashow.Fragment.CollectionFragment;
import com.xq.datashow.Fragment.DemoFragment;
import com.xq.datashow.Fragment.InstanceFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private TextView mTittleTextView;
    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;

    private List<Fragment> mFragmentList;

    private static final int DEMO_FRAGMENT = 0;
    private static final int INSTANCE_FRAGMENT = 1;
//    private static final int COLLECTION_FRAGMENT = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23) {
            initPermissions();
        }

        initViews();

        initViewPager();

        setStatusBar();
    }

    private void initViews() {
        mTittleTextView = findViewById(R.id.tv_main_tittle);

        mViewPager = findViewById(R.id.vp);
        mViewPager.setCurrentItem(DEMO_FRAGMENT);

        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    private void initViewPager() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new DemoFragment());
        mFragmentList.add(new InstanceFragment());
//        mFragmentList.add(new CollectionFragment());

        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(myFragmentPagerAdapter);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.navigation_demo:
                mTittleTextView.setText("图表演示");
                mViewPager.setCurrentItem(DEMO_FRAGMENT);
                return true;
            case R.id.navigation_instance:
                mTittleTextView.setText("实例分析");
                mViewPager.setCurrentItem(INSTANCE_FRAGMENT);
                return true;
//            case R.id.navigation_collection:
//                mTittleTextView.setText("收藏夹");
//                mViewPager.setCurrentItem(COLLECTION_FRAGMENT);
//                return true;
            default:
                return false;
        }
    }

    //初始化权限，请求用户获取对应的权限 没有获取的都可直接加到列表中
    private void initPermissions() {
        List<String> permissionList = new ArrayList<>();

        //用于访问GPS定位
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        //用户储存的权限，定位的一些缓存数据可能需要存储
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        //用户储存的权限，定位的一些缓存数据可能需要存储
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        //读取手机状态等信息，比如所网络的状态等
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_CONTACTS);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_SMS);
        }
        //判断是否还有没有获得的权限，如果有，就逐个申请对应的权限
        if (permissionList.size() > 0) {
            String[] permissions = new String[permissionList.size()];
            permissionList.toArray(permissions);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }
    }

    /**
     * 获取权限的状态等 ，检查一下有没有没有通过的权限
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
//                    Log.d(TAG, "onRequestPermissionsResult: length" + grantResults.length);
                    for (int i = 0; i < grantResults.length; i++) {
//                        Log.d(TAG, "onRequestPermissionsResult: " + grantResults[i]);
//                        Log.d(TAG, "onRequestPermissionsResult: " + permissions[i]);
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
//                            Toast.makeText(MainActivity.this, "请允许相关的权限才能使用定位功能哦", Toast.LENGTH_LONG).show();
//                            finish();
                        }
                    }

                }
        }
    }
}
