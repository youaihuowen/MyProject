package com.example.classtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * @auhtor WUYIXIONG
 * @description Activity的基类
 */
public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //绑定布局
        setContentView(setContent());
        //加载控件
        initView();
        //加载监听
        setLisetner();
    }

    /**
     * 绑定布局
     * @return 布局的id
     */
    protected abstract int setContent();

    /**
     * 绑定控件
     */
    protected abstract void initView();

    /**
     * 设置监听
     */
    protected abstract void setLisetner();
}
