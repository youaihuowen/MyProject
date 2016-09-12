package anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.example.wuyixiong.myapplication.R;
import com.example.wuyixiong.myapplication.ShapeHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WUYIXIONG on 2016-8-24.
 */
public class Marbles extends View {

    int color1=0xffff8080;
    int color2=0xff8080ff;
    ShapeHolder ball;
    List<ShapeHolder> balls=new ArrayList<ShapeHolder>();
    public Marbles(Context context) {
        super(context);
        backgroundAnim();
    }


    private void backgroundAnim(){
        ValueAnimator backAnim= ObjectAnimator.ofInt(this, "backgroundColor", color1, color2);
        backAnim.setDuration(3000);
        backAnim.setEvaluator(new ArgbEvaluator());
        backAnim.setRepeatCount(ValueAnimator.INFINITE);
        backAnim.setRepeatMode(ValueAnimator.REVERSE);
        backAnim.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN||
                event.getAction()==MotionEvent.ACTION_MOVE){

            ball =addBalls(event.getX(),event.getY());

            balls.add(ball);

            float startX=ball.getX();
            float startY=ball.getY();

            float h=getHeight();
            float endY=getHeight()-50f;

            float width=ball.getWidth();
            float height=ball.getHeight();

            long allTime=1000;
            long reallTime=(int)(allTime*((h-startY)/h));
            //落下动画
            ValueAnimator downAnim=ObjectAnimator.ofFloat(ball, "y", startY, endY);
            downAnim.setDuration(reallTime);
            downAnim.setInterpolator(new AccelerateInterpolator());

            //压缩动画   宽度增加
            ValueAnimator widthAnim1=ObjectAnimator.ofFloat(ball, "width", width, width+50f);
            widthAnim1.setDuration(reallTime/4);
            widthAnim1.setInterpolator(new DecelerateInterpolator());
            widthAnim1.setRepeatMode(ValueAnimator.REVERSE);
            widthAnim1.setRepeatCount(1);

            //压缩动画   位置左移
            ValueAnimator widthAnim2=ObjectAnimator.ofFloat(ball, "x", startX, startX-25f);
            widthAnim2.setDuration(reallTime/4);
            widthAnim2.setInterpolator(new DecelerateInterpolator());
            widthAnim2.setRepeatMode(ValueAnimator.REVERSE);
            widthAnim2.setRepeatCount(1);

            //压缩动画  高度减小
            ValueAnimator heightAnim1=ObjectAnimator.ofFloat(ball, "height", height, height-50f);
            heightAnim1.setDuration(reallTime/4);
            heightAnim1.setInterpolator(new DecelerateInterpolator());
            heightAnim1.setRepeatMode(ValueAnimator.REVERSE);
            heightAnim1.setRepeatCount(1);

            //压缩动画  位置下移
            ValueAnimator heightAnim2=ObjectAnimator.ofFloat(ball, "y", endY, endY+50f);
            heightAnim2.setDuration(reallTime/4);
            heightAnim2.setInterpolator(new DecelerateInterpolator());
            heightAnim2.setRepeatMode(ValueAnimator.REVERSE);
            heightAnim2.setRepeatCount(1);

            //回弹动画
            ValueAnimator upAnim=ObjectAnimator.ofFloat(ball, "y", endY, startY);
            upAnim.setDuration(reallTime);
            upAnim.setInterpolator(new DecelerateInterpolator());

            //小球消失动画
            ValueAnimator alphaAnim=ObjectAnimator.ofFloat(ball, "alpha", 1.0f,0.0f);
            alphaAnim.setDuration(200);

            alphaAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    balls.remove(((ObjectAnimator)animation).getTarget());
                }
            });

            AnimatorSet animatorSet=new AnimatorSet();

            animatorSet.play(downAnim).before(widthAnim1);
            animatorSet.play(widthAnim1).with(widthAnim2);
            animatorSet.play(widthAnim2).with(heightAnim1);
            animatorSet.play(heightAnim1).with(heightAnim2);
            animatorSet.play(upAnim).after(heightAnim2);
            animatorSet.play(alphaAnim).after(upAnim);


            animatorSet.start();
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (ShapeHolder shape:balls){
            canvas.save();
            canvas.translate(shape.getX()-50f, shape.getY()-50f);
            shape.getShape().draw(canvas);
            canvas.restore();
        }

    }

    public ShapeHolder addBalls(float x, float y){
        OvalShape circle=new OvalShape();
        circle.resize(100f, 100f);
        ShapeDrawable shapeDrawable=new ShapeDrawable(circle);
        ShapeHolder shapeHolder=new ShapeHolder(shapeDrawable);
        shapeHolder.setX(x);
        shapeHolder.setY(y);

        int R=(int) (Math.random()*255)<<16;
        int G=(int) (Math.random()*255)<<8;
        int B=(int) (Math.random()*255);
        int color=0xff000000| R | G | B ;
        int darkColor=0xff000000| R/4 | G/4 | B/4 ;
        shapeHolder.setColor(color);
        shapeHolder.setGradient(
                new RadialGradient(75f, 25f, 100f,color,darkColor, Shader.TileMode.CLAMP));
        return shapeHolder;
    }
}
