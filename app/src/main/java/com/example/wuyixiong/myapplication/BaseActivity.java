package com.example.wuyixiong.myapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(setContent());
        //绑定布局
        initView();
        //加载监听
        setListener();
    }

    /**
     * 绑定布局
     * @return 布局的id
     */
    protected abstract int setContent();

    /**
     * 加载控件
     */
    protected abstract void initView();

    /**
     * 设置监听
     */
    protected abstract void setListener();
}
