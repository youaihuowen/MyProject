package com.example.wuyixiong.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class ViewAnimActivity extends Activity {

    ImageView iv_xml;
    ImageView iv_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_anim);
        initView();
        initAnimXml();
        initAnimCode();
    }

    private void initView(){
        iv_code=(ImageView) findViewById(R.id.iv_viewAnim_code);
        iv_xml=(ImageView) findViewById(R.id.iv_viewAnim_xml);
    }
    private  void initAnimXml(){
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.viewanim);
//        animation.setDuration(2000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv_xml.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        iv_xml.startAnimation(animation);
    }
    private void initAnimCode(){
        final RotateAnimation rotateAnimation=new RotateAnimation(360f,0f,
                Animation.RELATIVE_TO_SELF,.5f,Animation.RELATIVE_TO_SELF,.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv_code.startAnimation(rotateAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        iv_code.startAnimation(rotateAnimation);
    }
}
