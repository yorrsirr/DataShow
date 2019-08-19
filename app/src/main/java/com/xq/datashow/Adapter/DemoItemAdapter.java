package com.xq.datashow.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xq.datashow.BingtuActivity;
import com.xq.datashow.DemoItem;
import com.xq.datashow.DilizuobiaoActivity;
import com.xq.datashow.LeidaActivity;
import com.xq.datashow.LoudouActivity;
import com.xq.datashow.R;
import com.xq.datashow.SandianActivity;
import com.xq.datashow.XiangxingActivity;
import com.xq.datashow.YibiaopanActivity;
import com.xq.datashow.ZhexianActivity;
import com.xq.datashow.ZhuzhuangActivity;
import com.xq.datashow.ZuheActivity;

import java.util.List;

public class DemoItemAdapter extends RecyclerView.Adapter<DemoItemAdapter.ViewHolder> {
    private List<DemoItem> mDemoItemList;
    private Context mContext;

    public DemoItemAdapter(Context context, List<DemoItem> mDemoItemList) {
        this.mDemoItemList = mDemoItemList;
        mContext = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mDemoImageView;
        private TextView mDemoTextView;
        private CardView mItemCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mDemoImageView = itemView.findViewById(R.id.iv_demo_item);
            mDemoTextView = itemView.findViewById(R.id.tv_demo_item_name);
            mItemCardView = itemView.findViewById(R.id.cv_demo_item);
        }
    }

    //i代表recycleView的第几个item
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.demo_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //i代表recycleView的第几个item
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final DemoItem mDemoItem = mDemoItemList.get(i);

        viewHolder.mDemoImageView.setBackground(mContext.getDrawable(mDemoItem.getDemoImageId()));
        viewHolder.mDemoTextView.setText(mDemoItem.getDemoName());

        viewHolder.mItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDemoItem.getDemoName().equals("折线图")) {
                    mContext.startActivity(new Intent(mContext, ZhexianActivity.class));
                }else if (mDemoItem.getDemoName().equals("柱状图")) {
                    mContext.startActivity(new Intent(mContext, ZhuzhuangActivity.class));
                }else if (mDemoItem.getDemoName().equals("饼图")) {
                    mContext.startActivity(new Intent(mContext, BingtuActivity.class));
                }else if (mDemoItem.getDemoName().equals("散点图")) {
                    mContext.startActivity(new Intent(mContext, SandianActivity.class));
                }else if (mDemoItem.getDemoName().equals("地理坐标图")) {
                    mContext.startActivity(new Intent(mContext, DilizuobiaoActivity.class));
                }else if (mDemoItem.getDemoName().equals("雷达图")) {
                    mContext.startActivity(new Intent(mContext, LeidaActivity.class));
                }else if (mDemoItem.getDemoName().equals("漏斗图")) {
                    mContext.startActivity(new Intent(mContext, LoudouActivity.class));
                }else if (mDemoItem.getDemoName().equals("仪表盘")) {
                    mContext.startActivity(new Intent(mContext, YibiaopanActivity.class));
                }else if (mDemoItem.getDemoName().equals("象形图")) {
                    mContext.startActivity(new Intent(mContext, XiangxingActivity.class));
                }else if (mDemoItem.getDemoName().equals("组合图")) {
                    mContext.startActivity(new Intent(mContext, ZuheActivity.class));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDemoItemList.size();
    }
}
