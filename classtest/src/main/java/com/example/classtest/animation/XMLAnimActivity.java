package com.example.classtest.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
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
import android.widget.LinearLayout;

import com.example.classtest.R;

import java.util.ArrayList;
import java.util.List;

public class XMLAnimActivity extends Activity {
    //布局容器
    LinearLayout ll_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_xmlanim);
        //装载控件
        initView();

    }

    /**
     * 装载view控件的方法
     */
    private void initView(){
        //所有空间的盒子
        ll_container=(LinearLayout) findViewById(R.id.ll_container);
        //自定义控件
        MyAnim myAnim=new MyAnim(this);
        //将自定义控件加到盒子中
        ll_container.addView(myAnim);


    }

    /**
     * 自定义控件的类
     */
    class MyAnim extends View {
        //背景颜色的开始值和结束值
        int color1=0xffff8080;
        int color2=0xff8080ff;
        //图形容器 用来存放变化的球
        ShapeHolder shapeHolder;
        //图形容器 用来存放小球
        ShapeHolder littleBall;
        List<ShapeHolder> list=new ArrayList<ShapeHolder>();


        /**
         * 默认构造器  里边实现了背景动画
         * @param context
         */
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
            //创建颜色、大小变化的球
            addColorBall();
        }


        /**
         * 绘制
         * @param canvas
         */
        @Override
        protected void onDraw(Canvas canvas) {
            //canvas.save();与canvas.retore();之间的代码为一帧的绘制
            canvas.save();
            //移动画布
            canvas.translate(shapeHolder.getX(),shapeHolder.getY());

            shapeHolder.getShape().draw(canvas);

            canvas.restore();


            for(ShapeHolder ball:list){
                canvas.save();
                //移动画布
                canvas.translate(ball.getX(),ball.getY());
                ball.getShape().draw(canvas);
                canvas.restore();
            }


        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if(event.getAction()==MotionEvent.ACTION_MOVE
                    || event.getAction()==MotionEvent.ACTION_DOWN){
                littleBall=addBalls(event.getX(),event.getY());
                list.add(littleBall);
                AnimatorSet animatorSet=(AnimatorSet) AnimatorInflater.loadAnimator(
                        XMLAnimActivity.this, R.animator.bouncer);

                animatorSet.setTarget(littleBall);

                AnimatorSet lastSet= (AnimatorSet) animatorSet.getChildAnimations().get(1);
                ObjectAnimator lastAnim=(ObjectAnimator) lastSet.getChildAnimations().get(1);

                lastAnim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        list.remove(((ObjectAnimator)animation).getTarget());
                    }
                });

                animatorSet.start();


            }
            return true;
        }

        /**
         * 创建 颜色 大小 变化的小球
         */
        private void addColorBall(){
            OvalShape circle=new OvalShape();//创建一个椭圆
            circle.resize(200f,200f);//设置椭圆的水平半径和垂直半径
            ShapeDrawable shapeDrawable=new ShapeDrawable(circle);//创建可绘制图形
            shapeHolder =new ShapeHolder(shapeDrawable);//创建自定义图形容器
            //设置球的默认坐标
            shapeHolder.setX(0f);
            shapeHolder.setY(0f);
            //创建改变宽度的关键帧
            Keyframe kf0=Keyframe.ofFloat(0f, 200f);//刚开始时设置为默认宽度
            Keyframe kf1=Keyframe.ofFloat(.5f, 400f);//运行到一半时设置宽度为400f
            Keyframe kf2=Keyframe.ofFloat(1f, 100f);//结束时宽度为100f
            //创建保存宽度关键帧的PropertyValuesHolder
            PropertyValuesHolder widthHolder=PropertyValuesHolder.ofKeyframe("width",
                    kf0, kf1, kf2);
            //创建改变高度的关键帧
            Keyframe kf3=Keyframe.ofFloat(0f, 200f);//刚开始时设置为默认高度
            Keyframe kf4=Keyframe.ofFloat(.5f, 400f);//运行到一半时设置高度为400f
            Keyframe kf5=Keyframe.ofFloat(1f, 100f);//结束时高度为100f
            //创建保存高度关键帧的PropertyValuesHolder
            PropertyValuesHolder heightHolder=PropertyValuesHolder.ofKeyframe("height",
                    kf3, kf4, kf5);
            //创建改变颜色的关键帧
            Keyframe kf6=Keyframe.ofInt(0f, 0xffff0000);//刚开始时颜色
            Keyframe kf7=Keyframe.ofInt(.5f, 0xff00ff00);//运行中期颜色
            Keyframe kf8=Keyframe.ofInt(1f, 0xff0000ff);//结束时颜色
            //创建保存颜色关键帧的PropertyValuesHolder
            PropertyValuesHolder colorHolder=PropertyValuesHolder.ofKeyframe("color",
                    kf6, kf7, kf8);
            //设置颜色计算器
            colorHolder.setEvaluator(new ArgbEvaluator());
            //创建可以控制多个属性的ObjectAnimator
            ObjectAnimator objectAnimator=ObjectAnimator.ofPropertyValuesHolder(shapeHolder,
                    widthHolder, heightHolder, colorHolder);
            //设置动画的运行时间3000ms
            objectAnimator.setDuration(3000);
            //设置循环模式和循环次数
            objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
            //执行动画
            objectAnimator.start();

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
            littleBall=new ShapeHolder(shapeDrawable);
            littleBall.setColor(color);
            littleBall.setX(x);
            littleBall.setY(y);
            RadialGradient gradient=new RadialGradient(75f,25f,100,color,darkColor, Shader.TileMode.CLAMP);
            littleBall.setGradient(gradient);
            return littleBall;
        }


    }
}
