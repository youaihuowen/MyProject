package com.example.robot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by WUYIXIONG on 2016-9-26.
 */

public class TextAdapter extends BaseAdapter {
    List<ListData> list;
    Context mContext;

    public TextAdapter(List<ListData> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
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
        LayoutInflater inflater = LayoutInflater.from(mContext);
        TextView textView = null;
        if (list.get(position).getFlag() == ListData.robot) {
            convertView = inflater.inflate(R.layout.iteam_left, null);
            textView = (TextView) convertView.findViewById(R.id.tv_iteam_left);
        } else if (list.get(position).getFlag() == ListData.people) {
            convertView = inflater.inflate(R.layout.iteam_right, null);
            textView = (TextView) convertView.findViewById(R.id.tv_iteam_right);
        }
        textView.setText(list.get(position).getContent());
        return convertView;
    }
}
