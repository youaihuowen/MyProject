package com.example.providerclient;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mycontactscontract.MyContactContract;

/**
 * @description 通讯大全ContentProvider的客户端
 * Created by WUYIXIONG on 2016-9-12.
 */
public class MainActivity extends Activity {

    //自定义的uri地址
    TextView tv_show;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_show = (TextView) findViewById(R.id.tv_main_show);
    }

    /**
     * @description 查询通讯录大全的contentProvider
     */
    public void queryProvider(View view) {
        //与普通的ContentProvider查询数据的方式相同
        Cursor cursor = getContentResolver().query(
                MyContactContract.TABLENAME_URI,
                new String[]{"*"},
                null,
                null,
                null);
        //临时保存数据的字符串
        String temp = "";
        if (cursor != null && cursor.getCount() > 0) {
            int typenameIndex = cursor.getColumnIndexOrThrow(MyContactContract.COLUMNS_NAME_TYPE);
            while (cursor.moveToNext()) {
                String name = cursor.getString(typenameIndex);
                temp += name + "\n\n";
            }
            tv_show.setText(temp);
        }

    }

    /**
     * @description 插入通讯录大全数据的contentProvider
     */
    public void insertProvider(View view) {
        //想要插入的值
        ContentValues values = new ContentValues();
        values.put(MyContactContract.COLUMNS_NAME_TYPE, "插入的新数据");
        getContentResolver().insert(
                MyContactContract.TABLENAME_URI,
                values);
        queryProvider(view);
    }

    /**
     * @description 更新通讯录大全数据的contentProvider
     */
    public void updateProvider(View view) {
        //待更新的值
        ContentValues values = new ContentValues();
        values.put(MyContactContract.COLUMNS_NAME_TYPE, "被修改的数据");
        //查询条件
        String selection = MyContactContract.COLUMNS_NAME_TYPE + " like ?";
        //查询条件的参数
        String[] selectionArgs = {"插入的新数据"};
        getContentResolver().update(
                MyContactContract.TABLENAME_URI,
                values,
                selection,
                selectionArgs);
        queryProvider(view);

    }

    /**
     * @description 删除通讯录大全数据的contentProvider
     */
    public void deleteProvider(View view) {
        String selection = MyContactContract.COLUMNS_NAME_TYPE+ " like ? or "
                +MyContactContract.COLUMNS_NAME_TYPE+" like ?";
        String[] selectionArgs = {"插入的新数据", "被修改的数据"};
        getContentResolver().delete(MyContactContract.TABLENAME_URI, selection, selectionArgs);
        queryProvider(view);

    }

    /**
     * @description 给请求activity返回临时权限和uri
     * @param view
     */
    public void giveAuthority(View view){
        Intent mIntent = getIntent();
        mIntent.setData(MyContactContract.TABLENAME_URI);
        setResult(Intent.FLAG_GRANT_READ_URI_PERMISSION, mIntent);
        finish();
    }
}
