package com.xq.datashow.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.xq.datashow.Brand;
import com.xq.datashow.R;

import java.util.ArrayList;
import java.util.List;

public class BrandAdapter extends BaseAdapter implements Filterable{
    private int resourceId;
    private List<Brand> brandList;
    private List<Brand> backBrandList; //备份数据
    private Context mContext;

    List<Brand> list;

    MyFilter mFilter ;

    public BrandAdapter(@NonNull Context context, int resource, List<Brand> brandList) {
        mContext = context;
        resourceId = resource;
        this.brandList = brandList;
        backBrandList = brandList;
        list = backBrandList;
    }


    @Override
    public int getCount() {
        return brandList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    //用来优化的viewholder内部类，优化控件findviewbyid
    class ViewHolder {
        TextView cityName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(resourceId,null);
            holder = new ViewHolder();
            holder.cityName = (TextView) convertView.findViewById(R.id.tv_brand_item);
            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.cityName.setText(brandList.get(position).getBrandName());

        return convertView;
    }


    @Override
    public Filter getFilter() {
        if (mFilter ==null){
            mFilter = new MyFilter();
        }
        return mFilter;
    }

    //我们需要定义一个过滤器的类来定义过滤规则
    class MyFilter extends Filter{
        //我们在performFiltering(CharSequence charSequence)这个方法中定义过滤规则
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults result = new FilterResults();

            if (TextUtils.isEmpty(charSequence)){//当过滤的关键字为空的时候，我们则显示所有的数据
                list  = backBrandList;
            }else {//否则把符合条件的数据对象添加到集合中
                list = new ArrayList<>();
                for (Brand brand:backBrandList){
                    if (brand.getBrandName().contains(charSequence)){ //要匹配的item中的view
                        list.add(brand);
                    }

                }
            }
            result.values = list; //将得到的集合保存到FilterResults的value变量中
            result.count = list.size();//将集合的大小保存到FilterResults的count变量中

            return result;
        }
        //在publishResults方法中告诉适配器更新界面
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            brandList = (List<Brand>)filterResults.values;
            if (filterResults.count>0){
                notifyDataSetChanged();//通知数据发生了改变
            }else {
                notifyDataSetInvalidated();//通知数据失效
            }
        }
    }

    public List<Brand> getList() {
        return list;
    }
}
