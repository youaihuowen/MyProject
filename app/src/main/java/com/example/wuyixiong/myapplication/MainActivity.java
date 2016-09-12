package com.example.wuyixiong.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import anim.Marbles;
import anim.Zoom;

public class MainActivity extends Activity implements View.OnClickListener {

    LinearLayout ll_container;
    Button but1;
    Button but2;
    Button but3;
    Button but4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        ll_container =(LinearLayout) findViewById(R.id.ll_container);
//        initView();
        but1=(Button) findViewById(R.id.main_button1);
        but1.setOnClickListener(this);
        but2=(Button)findViewById(R.id.main_button2);
        but2.setOnClickListener(this);
        but3=(Button) findViewById(R.id.main_button3);
        but3.setOnClickListener(this);
        but4=(Button) findViewById(R.id.main_button4);
        but4.setOnClickListener(this);
    }

    /**
     * 初始化弹球动画
     */
    public void initView_popball(){
        ll_container.removeAllViews();
        Marbles marbles=new Marbles(this);
        ll_container.addView(marbles);

    }

    /**
     * 初始化改变球大小的动画
     */
    public void initView_changeBall(){
        ll_container.removeAllViews();
        ll_container.setBackgroundColor(0xff808080);
        Zoom zoom=new Zoom(this);
        ll_container.addView(zoom);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_button1:
                initView_popball();
                break;
            case R.id.main_button2:
                initView_changeBall();
                break;
            case R.id.main_button3:
                startActivity(new Intent(this,ViewAnimActivity.class));
                break;
            case R.id.main_button4:
                startActivity(new Intent(this,DrawableActivity.class));
        }
    }
}
