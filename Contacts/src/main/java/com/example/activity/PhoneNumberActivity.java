package com.example.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.adapter.PhoneNumAdapter;
import com.example.entity.PhoneNumEntity;
import com.example.main.R;
import com.example.sqlite.PhoneNumEntry;
import com.example.sqlite.TypeEntry;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhoneNumberActivity extends BaseActivity {
    //存放电话信息的集合
    List<PhoneNumEntity> data = new ArrayList<PhoneNumEntity>();
    //listview 的适配器
    PhoneNumAdapter adapter;
    //显示电话信息的listview
    ListView lv_container;
    //加载旋转进度条的布局
    LinearLayout ll_loading;
    @Override
    protected int setContent() {
        return R.layout.activity_phone_number;
    }

    @Override
    protected void initView() {
        lv_container = (ListView) findViewById(R.id.lv_phonenumber);
        ll_loading = (LinearLayout) findViewById(R.id.loading);
        //启动初始化异步任务
        new initTask().execute();
    }

    @Override
    protected void setLisetner() {

    }

    /**
     * @description 从数据库中读取数据初始化listview
     */
    public void initListView(){
        //获得数据库对象
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(TypeEntry.FILE_PATH+"/phone.db",null);
        String subtable=getIntent().getStringExtra(TypeEntry.COLUMNS_NAME_SUBTABLE);
        Cursor c=db.query(subtable,//表名
                new String[]{"*"}, //查询出的列名
                null,//WHERE
                null,//Where args
                null,//GROUP BY
                null,//HAVING
                null);//ORDER BY
        //将游标移动到第一行
        c.moveToFirst();
        //读出数据放入集合里
        do {
            PhoneNumEntity phone = new PhoneNumEntity();
            phone.setPhoneName(c.getString(c.getColumnIndexOrThrow(PhoneNumEntry.COLUM_PHONE_NAME)));
            phone.setPhoneNumber(c.getString(c.getColumnIndexOrThrow(PhoneNumEntry.COLUM_PHONE_NUMBER)));
            data.add(phone);
        }while (c.moveToNext());
        //实例化适配器
        adapter = new PhoneNumAdapter(data,this);

        lv_container.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //创建询问是否拨打电话的对话框
                new AlertDialog.Builder(PhoneNumberActivity.this)
                        .setTitle("警告")
                        .setMessage("是否开始拨打"
                                +data.get(position).getPhoneName()
                                +"电话 \n"
                                +"TEL:"+data.get(position).getPhoneNumber())
                        .setPositiveButton("拨号",
                                //点击拨号后的监听
                                new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //拨打当前点击的电话号码
                                Intent intent = new Intent("android.intent.action.CALL",
                                        Uri.parse("tel:" + data.get(position).getPhoneNumber()));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
            }
        });

    }

    /**
     * @description 异步初始化操作任务类
     */
    class initTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            //加载listv
            initListView();
            return null;
        }

        @Override
        protected void onPreExecute() {
            //让loding界面显示
            ll_loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Object o) {
            //隐藏loding界面
            ll_loading.setVisibility(View.GONE);
            //设置listview的适配器
            lv_container.setAdapter(adapter);
        }
    }
}

