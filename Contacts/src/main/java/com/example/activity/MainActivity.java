package com.example.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.MainAdapter;
import com.example.entity.PhoneTypeEntity;
import com.example.main.R;
import com.example.sqlite.TypeEntry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    ListView lv_phoneType;// 表单列表
    long l_firstClick;//第一次点击退出键时的时间
    long l_secondClick;//第二次点击退出键时的时间
    boolean isExit;//是否已经点击了第一次
    String filePath;
    //加载的布局
    LinearLayout ll_loading;
    //适配器
    MainAdapter adapter;


    ArrayList<PhoneTypeEntity> list=new ArrayList<PhoneTypeEntity>();//存放item的集合


    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        lv_phoneType =(ListView) findViewById(R.id.main_listView);
        ll_loading=(LinearLayout) findViewById(R.id.loading);

        filePath="data/data/"+getPackageName()+"/database";
//        Log.e("tag", filePath );
        //启动初始化异步任务
        new initTask().execute();


    }

    @Override
    protected void setLisetner() {
        //设置点击电话类型某一项的监听
        lv_phoneType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //判断从数据库中获取到的subTable是什么字符
                switch (list.get(position).getSubTable()){
                    case TypeEntry.SUB_LOCAL://本地服务
                        //隐式启动拨号界面
                        intent=new Intent(Intent.ACTION_DIAL);
                        startActivity(intent);
                        break;
                    case TypeEntry.SUB_CATERING://订餐电话
                        //跳转到显示电话号码的页面(将要打开的表名放到intent中传递过去)
                        intent=new Intent(MainActivity.this, PhoneNumberActivity.class);
                        intent.putExtra(TypeEntry.COLUMNS_NAME_SUBTABLE, TypeEntry.SUB_CATERING);
                        startActivity(intent);
                        break;
                    case TypeEntry.SUB_PUBLICSERVICE://公共服务
                        //跳转到显示电话号码的页面(将要打开的表名放到intent中传递过去)
                        intent=new Intent(MainActivity.this, PhoneNumberActivity.class);
                        intent.putExtra(TypeEntry.COLUMNS_NAME_SUBTABLE, TypeEntry.SUB_PUBLICSERVICE);
                        startActivity(intent);
                        break;
                    case TypeEntry.SUB_OPERATOR://运营商
                        //跳转到显示电话号码的页面(将要打开的表名放到intent中传递过去)
                        intent=new Intent(MainActivity.this, PhoneNumberActivity.class);
                        intent.putExtra(TypeEntry.COLUMNS_NAME_SUBTABLE, TypeEntry.SUB_OPERATOR);
                        startActivity(intent);
                        break;
                    case TypeEntry.SUB_EXPERSSAGE://快递服务
                        //跳转到显示电话号码的页面(将要打开的表名放到intent中传递过去)
                        intent=new Intent(MainActivity.this, PhoneNumberActivity.class);
                        intent.putExtra(TypeEntry.COLUMNS_NAME_SUBTABLE, TypeEntry.SUB_EXPERSSAGE);
                        startActivity(intent);
                        break;
                    case TypeEntry.SUB_TRAVEL://机票酒店
                        //跳转到显示电话号码的页面(将要打开的表名放到intent中传递过去)
                        intent=new Intent(MainActivity.this, PhoneNumberActivity.class);
                        intent.putExtra(TypeEntry.COLUMNS_NAME_SUBTABLE, TypeEntry.SUB_TRAVEL);
                        startActivity(intent);
                        break;
                    case TypeEntry.SUB_BANK://银行证券
                        //跳转到显示电话号码的页面(将要打开的表名放到intent中传递过去)
                        intent=new Intent(MainActivity.this, PhoneNumberActivity.class);
                        intent.putExtra(TypeEntry.COLUMNS_NAME_SUBTABLE, TypeEntry.SUB_BANK);
                        startActivity(intent);
                        break;
                    case TypeEntry.SUB_INSURANCE://保险服务
                        //跳转到显示电话号码的页面(将要打开的表名放到intent中传递过去)
                        intent=new Intent(MainActivity.this, PhoneNumberActivity.class);
                        intent.putExtra(TypeEntry.COLUMNS_NAME_SUBTABLE, TypeEntry.SUB_INSURANCE);
                        startActivity(intent);
                        break;
                    case TypeEntry.SUB_AFTERSALE://品牌售后
                        //跳转到显示电话号码的页面(将要打开的表名放到intent中传递过去)
                        intent=new Intent(MainActivity.this, PhoneNumberActivity.class);
                        intent.putExtra(TypeEntry.COLUMNS_NAME_SUBTABLE, TypeEntry.SUB_AFTERSALE);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        doubleClickExit(keyCode,event);
        return true;
    }

    /**
     * @description 从数据库中读取数据初始化listview
     */
    private void initListView(){
        //将内容加入集合
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(filePath+"/phone.db", null);
        Log.i("tag", filePath+"/phone.db");
        Cursor c=db.query(TypeEntry.TABLE_NAME,//表名
                new String[]{TypeEntry.COLUMNS_NAME_TYPE, TypeEntry.COLUMNS_NAME_SUBTABLE},//COLUMN NAME
                null,//WHERE
                null,//Where args
                null,//GROUP BY
                null,//HAVING
                null);//ORDER BY

        //将游标移到第一位
        c.moveToFirst();
        do {
            //存放电话类型的实体类
            PhoneTypeEntity phone=new PhoneTypeEntity();
            phone.setPhoneType(c.getString(c.getColumnIndexOrThrow(TypeEntry.COLUMNS_NAME_TYPE)));
            phone.setSubTable(c.getString(c.getColumnIndexOrThrow(TypeEntry.COLUMNS_NAME_SUBTABLE)));
            list.add(phone);
        }while (c.moveToNext());
        //实例化自定义的适配器
       adapter=new MainAdapter(this, list);

    }

    /**
     * @description 从raw中导入数据库
     */
    private void importDatabase(){
        //实例化database的file
        File file=new File(filePath);

        try {
            //如果database不存在，建立目录
            if(!file.exists()){
                file.mkdir();
            }
            //实例化路径为phone.db的file
            file=new File(filePath,"phone.db");


            //如果phone.db不存在，新建文件
            if (!file.exists()){
                file.createNewFile();
            }else{
                return;
            }

            //建立从raw读取数据的输入流
            InputStream input=getResources().openRawResource(R.raw.phone);
            //建立写到数据库中的输出流
            FileOutputStream output=new FileOutputStream(file);

            byte[] buffer=new byte[1024];//缓冲区
            int count=0;
            while((count=input.read(buffer))!=-1){
                output.write(buffer,0,count);
            }
            input.close();
            output.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @description 双击退出键退出
     * @param keyCode
     * @param event
     */
    private void doubleClickExit(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK && isExit==false){
            //获取第一次点击的时间
            l_firstClick =System.currentTimeMillis();
            Toast.makeText(this,"再次点击退出",Toast.LENGTH_SHORT).show();
            isExit=true;
        }else if (keyCode==KeyEvent.KEYCODE_BACK && isExit==true){
            //获取第二次点击的时间
            l_secondClick =System.currentTimeMillis();
            if(l_secondClick - l_firstClick <1500){
                finish();
            }else{//如果时间间隔大于1.5s更改isExit的值，重调次方法
                isExit=false;
                doubleClickExit(keyCode,event);

            }
        }
    }

    /**
     * @description 异步初始化操作任务类
     */
    class initTask extends AsyncTask<Void,Void,Void>{

        //任务启动后在异步线程中执行的代码，不可操作UI
        @Override
        protected Void doInBackground(Void... params) {
            //调用导入数据库的方法
            importDatabase();
            //加载listview
            initListView();
            return null;
        }
        //任务启动之前执行的代码，可操作UI
        @Override
        protected void onPreExecute() {
            //让loding界面显示
            ll_loading.setVisibility(View.VISIBLE);
        }

        //任务完成之后执行的代码，可操作UI
        @Override
        protected void onPostExecute(Void aVoid) {
            //隐藏loding界面
            ll_loading.setVisibility(View.GONE);
            //设置listview的适配器
            lv_phoneType.setAdapter(adapter);
        }
    }
}
