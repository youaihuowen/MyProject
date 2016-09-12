package com.example.wuyixiong.myapplication;

import android.annotation.TargetApi;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

public class DrawableActivity extends BaseActivity {
    // the imageview's animation is realized by XML 通过xml设置动画的imageview
    ImageView iv_anim_xml;
    // the imageview's animation is realized by code 通过代码设置动画的imageview
    ImageView iv_anim_code;

    AnimationDrawable xmlAnim; //animation is realized by XML
    AnimationDrawable codeAnim; //animation is realized by code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setContent() {
        return R.layout.activity_drawable;
    }

    @Override
    protected void initView() {
        iv_anim_xml = (ImageView) findViewById(R.id.iv_drawableanim_xml);
        iv_anim_code = (ImageView) findViewById(R.id.iv_drawableanim_code);
        //loading animation 装载动画
        initAnim();
    }

    @Override
    protected void setListener() {

    }

    /**
     * @description initialize animation 初始化动画
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initAnim(){
        xmlAnim = (AnimationDrawable) iv_anim_xml.getBackground();

        codeAnim = new AnimationDrawable();
        //逐一加载个帧
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

        iv_anim_code.setBackground(codeAnim);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            // stop Animation 停止动画
            xmlAnim.stop();
            // set the current frame to the first frame 将当前帧设置成第一帧
            xmlAnim.selectDrawable(0);
            // start Animation 开启动画
            xmlAnim.start();


            // stop Animation 停止动画
            codeAnim.stop();
            // set the current frame to the first frame 将当前帧设置成第一帧
            codeAnim.selectDrawable(0);
            // start Animation 开启动画
            codeAnim.start();
        }
        return true;
    }
}
