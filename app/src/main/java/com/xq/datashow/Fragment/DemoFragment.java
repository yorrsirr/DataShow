package com.xq.datashow.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xq.datashow.Adapter.DemoItemAdapter;
import com.xq.datashow.DemoItem;
import com.xq.datashow.R;

import java.util.ArrayList;
import java.util.List;


public class DemoFragment extends Fragment {

    private View mView;
    private RecyclerView mRecycleView;
    private List<DemoItem> demoItemList;

    public DemoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_demo, container, false);

        initList();

        initViews();


        return mView;
    }

    private void initList() {
        demoItemList = new ArrayList<>();
        demoItemList.add(new DemoItem(R.drawable.ic_zhexiantu, "折线图"));
        demoItemList.add(new DemoItem(R.drawable.ic_zhuzhuangtu, "柱状图"));
        demoItemList.add(new DemoItem(R.drawable.ic_bingtu, "饼图"));
        demoItemList.add(new DemoItem(R.drawable.ic_sandiantu, "散点图"));
//        demoItemList.add(new DemoItem(R.drawable.ic_ditu, "地理坐标图"));
        demoItemList.add(new DemoItem(R.drawable.ic_leidatu, "雷达图"));
        demoItemList.add(new DemoItem(R.drawable.ic_loudou, "漏斗图"));
        demoItemList.add(new DemoItem(R.drawable.ic_yibiao, "仪表盘"));
        demoItemList.add(new DemoItem(R.drawable.ic_xiangxing, "象形图"));
        demoItemList.add(new DemoItem(R.drawable.ic_zuhe, "组合图"));
    }

    private void initViews() {
        mRecycleView = mView.findViewById(R.id.rv_demo);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecycleView.setAdapter(new DemoItemAdapter(getContext(), demoItemList));
    }

}
