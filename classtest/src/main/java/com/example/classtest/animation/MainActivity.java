package com.example.classtest.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.classtest.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    LinearLayout ll_container;
    Button btn_property;
    Button btn_view;
    Button btn_frame;
    Button btn_transition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView(){
        ll_container=(LinearLayout) findViewById(R.id.ll_container);

        MyAnim myAnim=new MyAnim(this);

        ll_container.addView(myAnim);

        btn_property =(Button) findViewById(R.id.btn_main_property);
        btn_property.setOnClickListener(this);

        btn_view=(Button) findViewById(R.id.btn_main_view);
        btn_view.setOnClickListener(this);

        btn_frame =(Button) findViewById(R.id.btn_main_frame);
        btn_frame.setOnClickListener(this);

        btn_transition = (Button) findViewById(R.id.btn_main_transition);
        btn_transition.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_main_property:
               Intent intent= new Intent(MainActivity.this,XMLAnimActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_main_view:
                Intent intent1= new Intent(MainActivity.this,ViewAnimActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_main_frame:
                Intent intent2= new Intent(MainActivity.this,DrawableActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_main_transition:
                Intent intent3= new Intent(MainActivity.this,TransitionActivity.class);
                startActivity(intent3);
                break;
        }

    }


    class MyAnim extends View{

        int color1=0xffff8080;
        int color2=0xff8080ff;

        ShapeHolder shapeHolder;

        List<ShapeHolder> balls=new ArrayList<ShapeHolder>();

        public MyAnim(Context context) {
            super(context);
            //声明属性动画，改变backgroundColor属性，值在color1和color2之间
            ValueAnimator animator= ObjectAnimator.ofInt(this,"backgroundColor",color1,color2);
            //设置播放时间为3s
            animator.setDuration(3000);
            //设置重复次数
            animator.setRepeatCount(ValueAnimator.INFINITE);
            //设置重复模式
            animator.setRepeatMode(ValueAnimator.REVERSE);
            //设置颜色计算器
            animator.setEvaluator(new ArgbEvaluator());
            //开始动画
            animator.start();

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if(event.getAction()==MotionEvent.ACTION_DOWN ||event.getAction()==MotionEvent.ACTION_MOVE){

                ShapeHolder ball=addBalls(event.getX(),event.getY());

                balls.add(ball);

                int h=getHeight();

                float currX=event.getX();
                float currY=event.getY();

                float endY=h-50f;

                float currWidth=ball.getWidth();
                float currHeight=ball.getHeight();

                long duration=1000;

                long realDuration= (long) ((h-shapeHolder.getY())/h*duration);

                //小球下落动画
                ValueAnimator downAnim=ObjectAnimator.ofFloat(shapeHolder,"y",currY,endY);
                downAnim.setDuration(realDuration);
                downAnim.setInterpolator(new AccelerateInterpolator());

                //小球压缩
                //左移小球坐标
                ValueAnimator squash1Anim=ObjectAnimator.ofFloat(shapeHolder,"x",currX,currX-25f);
                squash1Anim.setDuration(realDuration/4);
                squash1Anim.setInterpolator(new DecelerateInterpolator());

                squash1Anim.setRepeatCount(1);
                squash1Anim.setRepeatMode(ValueAnimator.REVERSE);

                //小球压缩
                //增加小球的宽度
                ValueAnimator squash2Anim=ObjectAnimator.ofFloat(shapeHolder,"width",currWidth,currWidth+50f);
                squash2Anim.setDuration(realDuration/4);
                squash2Anim.setInterpolator(new DecelerateInterpolator());

                squash2Anim.setRepeatCount(1);
                squash2Anim.setRepeatMode(ValueAnimator.REVERSE);


                //小球压缩
                //降低小球高度
                ValueAnimator strech1Anim=ObjectAnimator.ofFloat(shapeHolder,"y",endY,endY+25f);
                strech1Anim.setDuration(realDuration/4);
                strech1Anim.setInterpolator(new DecelerateInterpolator());

                strech1Anim.setRepeatCount(1);
                strech1Anim.setRepeatMode(ValueAnimator.REVERSE);

                //小球压缩
                //减小小球的高度
                ValueAnimator strech2Anim=ObjectAnimator.ofFloat(shapeHolder,"heigth",currHeight,currHeight-50f);
                strech2Anim.setDuration(realDuration/4);
                strech2Anim.setInterpolator(new DecelerateInterpolator());

                strech2Anim.setRepeatCount(1);
                strech2Anim.setRepeatMode(ValueAnimator.REVERSE);

                //小球上升动画
                ValueAnimator upAnim=ObjectAnimator.ofFloat(shapeHolder,"y",endY,currY);
                upAnim.setDuration(realDuration);
                upAnim.setInterpolator(new AccelerateInterpolator());

                //小球消失动画
                ValueAnimator fadeAnim=ObjectAnimator.ofFloat(shapeHolder,"alpha",1.0f,0.0f);
                fadeAnim.setDuration(realDuration/4);
                fadeAnim.setInterpolator(new LinearInterpolator());

                //动画结束播放后的监听
                fadeAnim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        balls.remove(((ObjectAnimator)animation).getTarget());
                    }
                });
                //创建第一组弹球运动动画
                AnimatorSet bouncerSet=new AnimatorSet();
                bouncerSet.play(downAnim).before(squash1Anim);
                bouncerSet.play(squash1Anim).with(squash2Anim);
                bouncerSet.play(squash2Anim).with(strech1Anim);
                bouncerSet.play(strech1Anim).with(strech2Anim);
                bouncerSet.play(upAnim).after(strech2Anim);

                AnimatorSet animSet=new AnimatorSet();
                animSet.play(fadeAnim).after(bouncerSet);

                animSet.start();
            }


            return true;
        }


        @Override
        protected void onDraw(Canvas canvas) {
            for (ShapeHolder ball:balls){
                canvas.save();
                canvas.translate(ball.getX()-50f,ball.getY()-50f);
                ball.getShape().draw(canvas);
                canvas.restore();
            }

        }

        private ShapeHolder addBalls(float x,float y){
            int red= (int) (Math.random()*255)<<16;
            int green= (int) (Math.random()*255)<<8;
            int buld= (int) (Math.random()*255);
            int color=0xff000000 |red | green | buld;

            int darkColor=0xff000000 |red/4 | green/4 | buld/4;

            OvalShape circle=new OvalShape();
            circle.resize(100f,100f);
            ShapeDrawable shapeDrawable=new ShapeDrawable(circle);
            shapeHolder=new ShapeHolder(shapeDrawable);
            shapeHolder.setColor(color);
            shapeHolder.setX(x);
            shapeHolder.setY(y);
            RadialGradient gradient=new RadialGradient(75f,25f,100,color,darkColor, Shader.TileMode.CLAMP);
            shapeHolder.setGradient(gradient);
            return shapeHolder;
        }
    }



}
