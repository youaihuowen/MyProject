package com.example.providerclient;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @description 通讯大全ContentProvider的客户端
 * Created by WUYIXIONG on 2016-9-12.
 */
public class MainActivity extends Activity {

    TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_show = (TextView) findViewById(R.id.tv_main_show);
        queryProvider();
    }

    private void queryProvider() {
        //与普通的ContentProvider查询数据的方式相同
        //自定义的uri地址
        Uri uri = Uri.parse("content://com.example.contacts.provider/phonetype");
        Cursor cursor = getContentResolver().query(
                uri,
                new String[]{"*"},
                null,
                null,
                null);
        //临时保存数据的字符串
        String temp = "";
        int typenameIndex = cursor.getColumnIndexOrThrow(TypeEntry.COLUMNS_NAME_TYPE);
        while (cursor.moveToNext()) {
            String name = cursor.getString(typenameIndex);
            temp += name+"\n\n";
        }
        tv_show.setText(temp);
    }
}
