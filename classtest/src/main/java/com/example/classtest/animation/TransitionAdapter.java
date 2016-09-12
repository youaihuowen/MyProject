package com.example.classtest.animation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.classtest.R;

/**
 * Created by WUYIXIONG on 2016-8-31.
 * @description 战士list iteam 过渡动画的适配器
 */
public class TransitionAdapter extends BaseAdapter{
    //上下文对象
    Context context;

    public TransitionAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载子item布局并返回
        LayoutInflater inflater=LayoutInflater.from(context);
        convertView=inflater.inflate(R.layout.item_transition,null);
        return convertView;
    }
}
