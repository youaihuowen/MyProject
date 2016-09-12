package com.example.classtest.animation;

import android.annotation.TargetApi;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.classtest.R;

public class DrawableActivity extends AppCompatActivity {

    ImageView fish_xml;//xml实现图片动画的控件
    ImageView fish_code;//代码实现图片动画的控件

    AnimationDrawable xmlAnim;//xml动画
    AnimationDrawable codeAnim;//code实现的动画
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);
        initview();//装载控件
        initAnim();//装载动画
    }

    /**
     * 装载控件
     */
    private void initview(){
        fish_xml=(ImageView) findViewById(R.id.fish_imageView1);
        fish_code=(ImageView) findViewById(R.id.fish_imageView2);
    }

    /**
     * 装载动画
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initAnim(){
        //xml方式装载动画
        fish_xml.setBackgroundResource(R.drawable.fishanim);
        xmlAnim=(AnimationDrawable) fish_xml.getBackground();

        //代码方式装载动画
        codeAnim=new AnimationDrawable();
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_01), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_02), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_03), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_04), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_05), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_06), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_07), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_08), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_09), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_10), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_11), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_12), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_13), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_14), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_15), 200);
        codeAnim.addFrame(getResources().getDrawable(R.drawable.fish_16), 200);

        fish_code.setBackground(codeAnim);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            //停止动画
            xmlAnim.stop();
            //将当前帧设置为第一帧
            xmlAnim.selectDrawable(0);
            //开始动画
            xmlAnim.start();

            //停止动画
            codeAnim.stop();
            //将当前帧设置为第一帧
            codeAnim.selectDrawable(0);
            //开始动画
            codeAnim.start();

        }
        return true;
    }
}
