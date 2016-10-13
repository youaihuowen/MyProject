package com.example.classtest_activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends Activity {

    TextView tv_request;
    public static final int REQUEST_CODE = 1;
    public static final String RESULT = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //绑定控件
        tv_request = (TextView) findViewById(R.id.tv_second_request);

        //解析传过来的字符串
        parseIntent();
    }

    /**
     * @param view
     * @description 完成操作返回前一个页面
     */
    public void exit(View view) {
        Intent mIntent = new Intent();
        switch (tv_request.getText().toString()) {
            case "Login":
                //设置返回码和返回数据
                mIntent.putExtra(RESULT, "登录成功");
                setResult(Activity.RESULT_OK, mIntent);
                break;
            case "Register":
                //设置返回码和返回数据
                mIntent.putExtra(RESULT, "注册成功");
                setResult(Activity.RESULT_OK, mIntent);
                break;
            case "Other":
                //设置返回码和返回数据
                mIntent.putExtra(RESULT, "未知操作");
                setResult(Activity.RESULT_CANCELED, mIntent);
                break;
        }
        finish();
    }

    /**
     * @description 解析从上一个页面传过来的Intent
     */
    private void parseIntent() {
        Intent mIntent = getIntent();
        String data = mIntent.getStringExtra(FirstActivity.REQUEST);
        tv_request.setText(data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent mIntent = new Intent();
            switch (tv_request.getText().toString()) {
                case "Login":
                    //设置返回码和返回数据
                    mIntent.putExtra(RESULT, "登录成功");
                    setResult(Activity.RESULT_OK, mIntent);
                    break;
                case "Register":
                    //设置返回码和返回数据
                    mIntent.putExtra(RESULT, "注册成功");
                    setResult(Activity.RESULT_OK, mIntent);
                    break;
                case "Other":
                    //设置返回码和返回数据
                    mIntent.putExtra(RESULT, "未知操作");
                    setResult(Activity.RESULT_CANCELED, mIntent);
                    break;
            }
            finish();
        }
        return false;
    }

    /**
     * @param view 点击的视图
     * @description 使用Intent 通过Providerclient程序获得临时权限和uri
     * 读取通讯大全中的数据
     */
    public void contentProvidetByIntent(View view) {
        Intent mIntent = new Intent();
        mIntent.setClassName("com.example.providerclient",
                "com.example.providerclient.MainActivity");
        startActivityForResult(mIntent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==Intent.FLAG_GRANT_READ_URI_PERMISSION && data.getData() != null){
            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri,
                    new String[]{"Type"},
                    null,
                    null,
                    null);
            String temp="";
            int index = cursor.getColumnIndexOrThrow("Type");
            while (cursor.moveToNext()){
                temp +=cursor.getString(index)+"\n";
            }
            tv_request.setText(temp);
        }

    }
}
