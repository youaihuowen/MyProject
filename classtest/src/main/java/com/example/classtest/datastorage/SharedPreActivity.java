package com.example.classtest.datastorage;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;
import com.example.classtest.datastorage.sqlite.MyOpenHelper;
import com.example.classtest.datastorage.sqlite.TypeEntry;

import java.io.File;

public class SharedPreActivity extends BaseActivity implements View.OnClickListener {

    private final String SPFILE_NAME="spfile";
    private final String BOOL_NAME="myboolean";

    Button btn_shard;
    TextView tv_shardValue;
    Button btn_toInter;
    Button btn_toCache;
    Button btn_toexter;
    TextView tv_message;
    Button btn_cp;
    Button btn_cpload;
    //获得openhelper的实例
    MyOpenHelper myHelper=new MyOpenHelper(this);
    //申请外部存储器写权限的申请码
    final int WRITE_EXTERNAL_STORAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int setContent() {
        return R.layout.activity_shared_pre;
    }

    @Override
    protected void initView() {
        btn_shard=(Button) findViewById(R.id.btn_shard);
        tv_shardValue=(TextView) findViewById(R.id.tv_shardVale);
        btn_toInter=(Button) findViewById(R.id.btn_shared_toInternalStorage);
        btn_toCache=(Button) findViewById(R.id.btn_shared_tocache) ;
        btn_toexter=(Button) findViewById(R.id.btn_shared_toexter);
        tv_message=(TextView) findViewById(R.id.tv_shared_message);
        btn_cp = (Button) findViewById(R.id.btn_shared_cp);
        btn_cpload = (Button) findViewById(R.id.btn_shard_cpload);
        readSPValue();
        creatDateBase();
        readDatabase();
       // deletedatabase();
        //updateDatabase();
        requsstPermission();
    }

    @Override
    protected void setLisetner() {
        btn_shard.setOnClickListener(this);
        btn_toInter.setOnClickListener(this);
        btn_toCache.setOnClickListener(this);
        btn_toexter.setOnClickListener(this);
        btn_cp.setOnClickListener(this);
        btn_cpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_shard:
                writeSPValue();
                readSPValue();
                break;
            case R.id.btn_shared_toInternalStorage:
                startActivity(new Intent(this, InternalStorageActivity.class));
                break;
            case R.id.btn_shared_tocache:
                startActivity(new Intent(this, CacheActivity.class));
                break;
            case R.id.btn_shared_toexter:
                startActivity(new Intent(this, ExternalStorageActivity.class));
                break;
            case R.id.btn_shared_cp:
                startActivity(new Intent(this, ContentProviderActivity.class));
                break;
            case R.id.btn_shard_cpload:
                startActivity(new Intent(this, CPLoadActivity.class));
                break;

        }

    }

    /**
     * @description 读取SharedPreferences中的值
     */
    private void readSPValue(){
        //获得SharedPreference的实例，参数1为文件名，参数2为文件模式
        //默认模式为0，即MODE_PRIVATE,有对应文件则读取，无则创建
        SharedPreferences sp=getSharedPreferences(SPFILE_NAME,0);
        //读取sp中的对应boolean数据
        Boolean temp=sp.getBoolean(BOOL_NAME,false);
        //设置到UI中去展示
        tv_shardValue.setText(temp+"");
    }
    /**
     * @description 写入SharedPreferences中的值
     */
    private void writeSPValue(){
        //获得SharedPreference的实例，参数1为文件名，参数2为文件模式
        //默认模式为0，即MODE_PRIVATE,有对应文件则读取，无则创建
        SharedPreferences sp=getSharedPreferences(SPFILE_NAME,0);
        //获得Sharedpreferences的编辑器
        SharedPreferences.Editor editor=sp.edit();
        //向Sharedpreferences添加一个key为"myBoolean"的布尔值
        editor.putBoolean(BOOL_NAME,true);
        //提交数据
        editor.commit();

    }

    /**
     * @description 建立数据库
     */
    private void creatDateBase(){
        File file = new File("data/data/"+getPackageName()+"/databases");

        if (!file.exists()){
            //通过帮助类实例化一个可编辑的数据库对象
            SQLiteDatabase db=myHelper.getWritableDatabase();
//            Log.i("tag", file.getPath());
//            Log.i("tag",  db.getPath());

            //准备好待写入的值
            ContentValues contentValues=new ContentValues();
            contentValues.put(TypeEntry.COLUMNS_NAME_TYPE,"本地电话");
            contentValues.put(TypeEntry.COLUMNS_NAME_SUBTABLE,"localservice");
            db.insert(TypeEntry.TABLE_NAME,//表名
                    null,////当插入空行或者contentvalues为空时替代的空数据内容
                    contentValues);//插入的值

            contentValues.put(TypeEntry.COLUMNS_NAME_TYPE,"订餐电话");
            contentValues.put(TypeEntry.COLUMNS_NAME_SUBTABLE,"ordermealtele");
            db.insert(TypeEntry.TABLE_NAME,//表名
                    null,////当插入空行或者contentvalues为空时替代的空数据内容
                    contentValues);//插入的值
            contentValues.put(TypeEntry.COLUMNS_NAME_TYPE,"公共服务");
            contentValues.put(TypeEntry.COLUMNS_NAME_SUBTABLE,"publicservice");
            db.insert(TypeEntry.TABLE_NAME,//表名
                    null,////当插入空行或者contentvalues为空时替代的空数据内容
                    contentValues);//插入的值
            contentValues.put(TypeEntry.COLUMNS_NAME_TYPE,"运营商");
            contentValues.put(TypeEntry.COLUMNS_NAME_SUBTABLE,"operator");
            db.insert(TypeEntry.TABLE_NAME,//表名
                    null,////当插入空行或者contentvalues为空时替代的空数据内容
                    contentValues);//插入的值
            contentValues.put(TypeEntry.COLUMNS_NAME_TYPE,"快递服务");
            contentValues.put(TypeEntry.COLUMNS_NAME_SUBTABLE,"courierservice");
            db.insert(TypeEntry.TABLE_NAME,//表名
                    null,////当插入空行或者contentvalues为空时替代的空数据内容
                    contentValues);//插入的值
            contentValues.put(TypeEntry.COLUMNS_NAME_TYPE,"机票酒店");
            contentValues.put(TypeEntry.COLUMNS_NAME_SUBTABLE,"tickethotel");
            db.insert(TypeEntry.TABLE_NAME,//表名
                    null,////当插入空行或者contentvalues为空时替代的空数据内容
                    contentValues);//插入的值
            contentValues.put(TypeEntry.COLUMNS_NAME_TYPE,"银行证券");
            contentValues.put(TypeEntry.COLUMNS_NAME_SUBTABLE,"banksecurity");
            db.insert(TypeEntry.TABLE_NAME,//表名
                    null,////当插入空行或者contentvalues为空时替代的空数据内容
                    contentValues);//插入的值
            contentValues.put(TypeEntry.COLUMNS_NAME_TYPE,"保险服务");
            contentValues.put(TypeEntry.COLUMNS_NAME_SUBTABLE,"insuranceservice");
            db.insert(TypeEntry.TABLE_NAME,//表名
                    null,////当插入空行或者contentvalues为空时替代的空数据内容
                    contentValues);//插入的值
            contentValues.put(TypeEntry.COLUMNS_NAME_TYPE,"品牌售后");
            contentValues.put(TypeEntry.COLUMNS_NAME_SUBTABLE,"brandaftersales");
            db.insert(TypeEntry.TABLE_NAME,//表名
                    null,////当插入空行或者contentvalues为空时替代的空数据内容
                    contentValues);//插入的值
        }
    }

    /**
     * @description 从数据库中读取数据
     */
    public void readDatabase(){
        //通过帮助类实例化一个可读的数据库对象
        SQLiteDatabase db=myHelper.getReadableDatabase();

        Cursor c=db.query(TypeEntry.TABLE_NAME,//表名
                new String[]{TypeEntry.COLUMNS_NAME_TYPE},//查询的列
                null,//相当于where之后的语句
                null,//对where中占位符的补全
                null,//相当于group by
                null,//相当于having
                null);//相当于older by
        //将游标移动到第一个
        c.moveToFirst();
        //存放数据的字符
        String temp="";
        do {
           temp+= c.getString(
                   //获得当前游标位置的下标
                   c.getColumnIndexOrThrow(TypeEntry.COLUMNS_NAME_TYPE)
           )+"\n\n";
        }while (c.moveToNext());
        tv_message.setText(temp);
    }
    public void deletedatabase(){
        //通过帮助类实例化一个可编辑的数据库对象
        SQLiteDatabase db=myHelper.getWritableDatabase();
        db.delete(TypeEntry.TABLE_NAME,//表名
                TypeEntry._ID+"=1",//where条件
                null);//Selection args
    }
    public void updateDatabase(){
        //通过帮助类实例化一个可编辑的数据库对象
        SQLiteDatabase db=myHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(TypeEntry.COLUMNS_NAME_TYPE,"abcdefg");
        db.update(TypeEntry.TABLE_NAME,//表名
                contentValues,//要放入的值
                TypeEntry._ID+"=?",//条件
                new String[]{"2"});//占位符补全
    }

    /**
     * @description 申请运行时权限
     */
    private void requsstPermission(){
        //检查是否有外部存储器写权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            //判断申请码
            case WRITE_EXTERNAL_STORAGE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //第一个权限成功后

                }else{
                    //第一个权限失败后
                    finish();
                }
                break;
        }
    }
}
