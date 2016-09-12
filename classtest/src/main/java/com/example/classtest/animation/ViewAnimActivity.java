package com.example.classtest.animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.example.classtest.R;

public class ViewAnimActivity extends AppCompatActivity {

    TextView tv_xml;
    TextView tv_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_anim);
        initView();

        initAnim();

    }

    private void initView(){
        tv_xml=(TextView) findViewById(R.id.view_textView1);
        tv_code=(TextView)findViewById(R.id.view_textView2);
    }

    private void initAnim(){
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.viewanim);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tv_xml.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        tv_xml.startAnimation(animation);

        //代码方式创建viewAnimation
        //创建一个旋转动画
        RotateAnimation rotateAnimation=new RotateAnimation(
                0f, 360f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setInterpolator(new AccelerateInterpolator());

        //创建一个平移动画
        TranslateAnimation translateAnimation=new TranslateAnimation(0f, 400f, 0f, 400f);
        translateAnimation.setDuration(2000);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        translateAnimation.setStartOffset(1000);

        //创建缩放动画
        ScaleAnimation scaleAnimation=new ScaleAnimation(
                1f, 2f, 1f,2f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setInterpolator(new AccelerateInterpolator());
        scaleAnimation.setStartOffset(1000);

        //创建透明度动画
        AlphaAnimation alphaAnimation=new AlphaAnimation(1f,0f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        alphaAnimation.setStartOffset(1000);

        final AnimationSet allSet=new AnimationSet(false);
        AnimationSet childSet=new AnimationSet(false);

        childSet.addAnimation(translateAnimation);
        childSet.addAnimation(scaleAnimation);
        childSet.addAnimation(alphaAnimation);

        allSet.addAnimation(rotateAnimation);
        allSet.addAnimation(childSet);

        allSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //启动动画方法一
//                tv_code.startAnimation(allSet);
                //启动动画方法二
                allSet.reset();
                allSet.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //启动方法一
        tv_code.startAnimation(allSet);
        //启动方法二
//        tv_code.setAnimation(allSet);
//        allSet.start();

    }
}
