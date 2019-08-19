package com.xq.datashow.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.xq.datashow.CarSet;
import com.xq.datashow.R;

import java.util.List;

public class CarSetAdapter extends ArrayAdapter<CarSet> {
    private Context mContext;
    private int resourceId;
    private List<CarSet> carSetList;

    public CarSetAdapter (Context context, int resourceId, List<CarSet> carSetList) {
        super(context, resourceId, carSetList);
        mContext = context;
        this.resourceId = resourceId;
        this.carSetList = carSetList;
    }

    class ViewHolder {
        TextView mBrandNameTV, mCarSetNameTV, mPriceTV;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(resourceId,null);
            viewHolder = new CarSetAdapter.ViewHolder();
            viewHolder.mBrandNameTV = (TextView) convertView.findViewById(R.id.tv_car_set_item_brand_name);
            viewHolder.mCarSetNameTV = (TextView)convertView.findViewById(R.id.tv_car_set_item_car_set_name);
            viewHolder.mPriceTV = (TextView)convertView.findViewById(R.id.tv_car_set_item_price);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (CarSetAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.mBrandNameTV.setText(carSetList.get(position).getBrandName());
        viewHolder.mCarSetNameTV.setText(carSetList.get(position).getCarSetName());
        viewHolder.mPriceTV.setText(carSetList.get(position).getPrice());

        return convertView;
    }
}
