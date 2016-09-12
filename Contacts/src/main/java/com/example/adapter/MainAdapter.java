package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.entity.PhoneTypeEntity;
import com.example.main.R;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2016-8-24.
 */
public class MainAdapter extends BaseAdapter {

    Context mContext;
    //存放数据的集合
    private ArrayList<PhoneTypeEntity> list=new ArrayList<PhoneTypeEntity>();
    //listview显示电话类型
    private TextView text;

    public MainAdapter(Context context, ArrayList<PhoneTypeEntity> list) {
        this.list = list;
        mContext =context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        //布局优化
        if (convertView==null){
            //获取布局加载器
            LayoutInflater inflater=LayoutInflater.from(mContext);
            //使用布局加载器加载item布局
            convertView=inflater.inflate(R.layout.item_main, null);
            //保存ViewHolder的状态，存在当前convertView中
            holder=new ViewHolder();
            holder.tv_typeName=(TextView) convertView.findViewById(R.id.iteam_main_textView);
            holder.iv_arrowRight=(ImageView) convertView.findViewById(R.id.iteam_main_imageView);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder) convertView.getTag();
        }
        holder.tv_typeName.setText(list.get(position).getPhoneType());

        return convertView;
    }

    /**
     * @descrition 用于优化listview的ViewHolder，保存iteam布局中的控件
     */
    class ViewHolder{
        public TextView tv_typeName;//电话类型名称
        public ImageView iv_arrowRight;//右箭头
    }

}
