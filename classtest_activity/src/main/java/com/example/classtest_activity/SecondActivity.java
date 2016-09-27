package com.example.classtest_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends Activity{

    TextView tv_request;
    public static final String RESULT="result";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //绑定控件
        tv_request=(TextView) findViewById(R.id.tv_second_request);

        //解析传过来的字符串
        parseIntent();
    }
    /**
     * @description 完成操作返回前一个页面
     * @param view
     */
    public void exit(View view){
        Intent mIntent = new Intent();
        switch (tv_request.getText().toString()){
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
    private void parseIntent(){
        Intent mIntent = getIntent();
        String data = mIntent.getStringExtra(FirstActivity.REQUEST);
        tv_request.setText(data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK){
                Intent mIntent = new Intent();
                switch (tv_request.getText().toString()){
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
}
