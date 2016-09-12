package com.example.classtest.datastorage;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;

public class CPLoadActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    EditText ed_search;//编辑框
    TextView tv_search;//查询结果

    @Override
    protected int setContent() {
        return R.layout.activity_cpload;
    }

    @Override
    protected void initView() {
        ed_search = (EditText) findViewById(R.id.et_cpload_search);
        tv_search = (TextView) findViewById(R.id.tv_cpload_show);

        getLoaderManager().initLoader(
                0, //加载器的id
                null,//传入的bundle参数
                this);//回调对象

    }

    @Override
    protected void setLisetner() {

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //通讯录的uri
        Uri mUri = ContactsContract.Contacts.CONTENT_URI;
        //要查询的列
        String[] projection = {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        //输入的内容
        String filter = ed_search.getText().toString();

        if (TextUtils.isEmpty(filter)) {
            filter = "0";
        }
        //查询的条件
        String selection = ContactsContract.Contacts._ID + " > " + filter;

        return new CursorLoader(
                this,//上下文
                mUri,//通讯录的uri
                projection,//要查询的列
                selection,//查询条件
                null,//占位符补齐
                ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");//根据本地语言进行排序

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //要查询列的下标
        int idIndex = data.getColumnIndexOrThrow(ContactsContract.Contacts._ID);
        int displayIndex = data.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME);
        //存放查询字符串的临时变量
        String temp = "";
        if (data == null || data.getCount() < 1) {
            temp="没有符合条件的数据";
        } else {
            //循环遍历游标
            while (data.moveToNext()) {
                String id = data.getString(idIndex);
                String displayname = data.getString(displayIndex);
                temp += "ID=" + id + " DisplayName=" + displayname + "\n\n";
            }
        }
        tv_search.setText(temp);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        tv_search.setBackgroundColor(0xffff0000);
    }

    public void searchContacts(View view) {
        getLoaderManager().restartLoader(
                0, //要释放加载器的id
                null,//传入的bundle参数
                this);//回调对象
    }
}
