package com.example.exam9_9.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.exam9_9.Entity.MessageEntity;
import com.example.exam9_9.R;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2016-9-9.
 * 显示信息页面的适配器
 */
public class MessageAdapter extends BaseAdapter {
    ArrayList<MessageEntity> data = new ArrayList<MessageEntity>();
    Context mContext;
    public MessageAdapter(ArrayList<MessageEntity> data, Context context) {
        this.data = data;
        mContext = context;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (view==null){
            //定义布局加载器
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.item_contact,null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) view.findViewById(R.id.tv_item_name);
            holder.tv_message = (TextView) view.findViewById(R.id.tv_item_message);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_name .setText(data.get(i).getName());
        holder.tv_message.setText(data.get(i).getMessage());

        return view;
    }

    class ViewHolder{
        public TextView tv_name;//显示名字的text
        public TextView tv_message;//显示信息的text
    }

}
