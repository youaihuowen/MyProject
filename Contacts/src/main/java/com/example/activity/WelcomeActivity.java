package com.example.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.main.R;

public class WelcomeActivity extends BaseActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    protected int setContent() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        imageView=(ImageView) findViewById(R.id.welcome_imageView);
        initAnim();
    }

    @Override
    protected void setLisetner() {

    }

    /**
     * 设置动画
     */
    private void initAnim(){
        //定义透明度动画
        AlphaAnimation alphaAnimation=new AlphaAnimation(0f,1f);
        //设置持续时间
        alphaAnimation.setDuration(3000);
        //设置加速
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        //添加监听
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //跳转到新页面
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                //销毁该页面
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(alphaAnimation);

    }
}
