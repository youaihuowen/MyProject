package com.example.exam9_9.Activity;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.exam9_9.Activity.BaseActivity;
import com.example.exam9_9.Entity.MessageEntity;
import com.example.exam9_9.R;
import com.example.exam9_9.adapter.MessageAdapter;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    //存放短信信息的数组
    ArrayList<MessageEntity> data = new  ArrayList<MessageEntity>();
    private final int READ_SMS = 0;
    private final int WRITE_SMS = 1;
    ListView lv_message;
    MessageAdapter adapter;
    //短信的uri
    final Uri SMS_INBOX_CONTENT_URI = Uri.parse("content://sms/");
    int position;//集合中数据的下标

    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        lv_message = (ListView) findViewById(R.id.lv_message);

        requsstPermission();

    }

    @Override
    protected void setLisetner() {
        lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MainActivity.this, DetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("name",data.get(i).getName());
                bundle.putString("message",data.get(i).getMessage());
                intent.putExtra("sms",bundle);
                startActivity(intent);
            }
        });

        lv_message.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                position=i;
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("警告")
                        .setMessage("是否要删除该条信息")
                        .setPositiveButton("删除",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteSms(data.get(position).getName());
                                        data.removeAll(data);
                                        querySms();
                                        adapter.notifyDataSetChanged();
                                    }

                                })
                        .setNegativeButton("取消",null)
                        .show();
                return true;
            }
        });
    }

    private void querySms(){

        //要查询的列
        String[] projection = {"address", "body"};
        //查询语句
        Cursor cursor = getContentResolver().query(
                SMS_INBOX_CONTENT_URI,//所有短信的uri
                projection,//查询的列
                null,//查询条件
                null,//查询条件占位符补全
                null//排序
        );
        int idColumn = cursor.getColumnIndexOrThrow("address");//id列的下标
        int body = cursor.getColumnIndexOrThrow("body");//内容列的下标

        if(cursor== null){
            Toast.makeText(this,"查询出错",Toast.LENGTH_LONG).show();
        }else if(cursor.getCount()<1){
            Toast.makeText(this,"没有信息",Toast.LENGTH_LONG).show();
        }else{
            MessageEntity sms;
            //下标移动到第一条
            cursor.moveToFirst();
            do {//遍历
                sms = new MessageEntity();
                sms.setName(cursor.getString(idColumn));
                sms.setMessage(cursor.getString(body));
                data.add(sms);
            }while (cursor.moveToNext());
        }

        cursor.close();
        adapter = new MessageAdapter(data, this);
        lv_message.setAdapter(adapter);
    }

    /**
     * 删除某条信息
     */
    private void deleteSms(String address){
        String selectionClause = " address = ?";
        String[] selectionArgs = {address};
        getContentResolver().delete(SMS_INBOX_CONTENT_URI,
                selectionClause,
                selectionArgs);
    }
    /**
     * @description 申请运行时权限
     */
    private void requsstPermission(){
        //检查是否有读取短信的权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED){
            //申请读取短信的权限
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS},
                    READ_SMS);

        }else{
            querySms();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case READ_SMS :
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //申请的第一个权限成功后
                   querySms();
                } else {
                    //申请的第一个权限失败后
                    finish();
                }
                break;
        }
    }
}
