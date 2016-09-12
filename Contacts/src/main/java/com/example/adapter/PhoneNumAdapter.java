package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.entity.PhoneNumEntity;
import com.example.main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WUYIXIONG on 2016-9-1.
 * 显示电话信息的listview的适配器
 */
public class PhoneNumAdapter extends BaseAdapter {
    //存放电话名称和号码信息的集合
    List<PhoneNumEntity> data = new ArrayList<PhoneNumEntity>();

    Context mContext;

    public PhoneNumAdapter(List<PhoneNumEntity> data, Context mContext) {
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
        ViewHolder holder = null;
        //优化布局
        if(convertView==null){
            //实例化布局加载器
            LayoutInflater inflater = LayoutInflater.from(mContext);
            //使用布局加载器加载item布局
            convertView=inflater.inflate(R.layout.item_phonenum, null);
            ////保存ViewHolder的状态，存在当前convertView中
            holder = new ViewHolder();
            holder.tv_phoneName = (TextView) convertView.findViewById(R.id.tv_item_phonename);
            holder.tv_phonenumber = (TextView) convertView.findViewById(R.id.tv_item_phonenumber);
            holder.im_arrows = (ImageView) convertView.findViewById(R.id.im_item_arrows);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_phoneName.setText(data.get(position).getPhoneName());
        holder.tv_phonenumber.setText(data.get(position).getPhoneNumber());
        return convertView;
    }

    /**
     * @descrition 用于优化listview的ViewHolder，保存iteam布局中的控件
     */
    class ViewHolder{
        TextView tv_phoneName;//电话名称
        TextView tv_phonenumber;//电话号码
        ImageView im_arrows;//向右的箭头
    }
}
