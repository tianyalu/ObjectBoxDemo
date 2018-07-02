package com.sty.object.box;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sty.object.box.model.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tian on 2018/7/2.
 */

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private List<Customer> mDataList;
    private LayoutInflater inflater;

    public MyAdapter(){}

    public MyAdapter(Context context, List<Customer> dataList){
        this.mContext = context;
        this.mDataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataList == null ? null : mDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if(convertView == null){
            viewHolder = new ViewHolder(); //Important!!!

            convertView = inflater.inflate(R.layout.item_db_list, parent, false);
            viewHolder.tvId = (TextView) convertView.findViewById(R.id.tv_id);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvAge = (TextView) convertView.findViewById(R.id.tv_age);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.tvId.setText(mDataList.get(position).getId() + "");
        viewHolder.tvName.setText(mDataList.get(position).getName());
        viewHolder.tvAge.setText(mDataList.get(position).getAge() + "");

        return convertView;
    }


    private static class ViewHolder{
        public TextView tvId;
        public TextView tvName;
        public TextView tvAge;
    }

    public void setDataList(List<Customer> dataList){
        if(mDataList == null){
            mDataList = new ArrayList<>();
        }else {
            mDataList.clear();
        }
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }
}
