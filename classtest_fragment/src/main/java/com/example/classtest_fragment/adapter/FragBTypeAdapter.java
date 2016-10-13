package com.example.classtest_fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.classtest_fragment.R;
import com.example.classtest_fragment.entity.TypeEntity;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by WUYIXIONG on 2016-10-10.
 */

public class FragBTypeAdapter extends BaseAdapter{
    ArrayList<TypeEntity> data = new ArrayList<TypeEntity>();
    Context mContext;

    public FragBTypeAdapter(ArrayList<TypeEntity> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView=inflater.inflate(R.layout.item_b_type,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_type=(TextView) convertView.findViewById(R.id.tv_b_type);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.tv_type.setText(data.get(position).getType());
        return convertView;
    }
    private class ViewHolder{
        public TextView tv_type;
    }
}
