package anim;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.MotionEvent;
import android.view.View;

import com.example.wuyixiong.myapplication.MainActivity;
import com.example.wuyixiong.myapplication.R;
import com.example.wuyixiong.myapplication.ShapeHolder;

import java.util.ArrayList;
import java.util.List;

/**小球缩放
 * Created by WUYIXIONG on 2016-8-24.
 */
public class Zoom extends View{
    ShapeHolder ball;
    ShapeHolder littleBall;
    List<ShapeHolder> balls=new ArrayList<ShapeHolder>();
    Context context=null;
    public Zoom(Context context) {
        super(context);
        this.context=context;
        change();
    }

    /**
     * 球大小变化，颜色变化的动画
     */
    private void change(){
        ball=changeBall();

        int color1=0xffff8080;
        int color2=0xff80ff80;
        int color3=0xff8080ff;
        Keyframe kf0=Keyframe.ofFloat(0f, 200f);
        Keyframe kf1=Keyframe.ofFloat(.5f, 400f);
        Keyframe kf2=Keyframe.ofFloat(1f, 100f);
        PropertyValuesHolder widthHolder=
                PropertyValuesHolder.ofKeyframe("width", kf0, kf1, kf2);

        Keyframe kf3=Keyframe.ofFloat(0f, 200f);
        Keyframe kf4=Keyframe.ofFloat(.5f, 400f);
        Keyframe kf5=Keyframe.ofFloat(1f, 100f);
        PropertyValuesHolder heightHolder=
                PropertyValuesHolder.ofKeyframe("height", kf3, kf4, kf5);

        Keyframe kf6=Keyframe.ofInt(0f, color1);
        Keyframe kf7=Keyframe.ofInt(.5f, color2);
        Keyframe kf8=Keyframe.ofInt(1f, color3);
        PropertyValuesHolder colorHolder=
                PropertyValuesHolder.ofKeyframe("color", kf6, kf7, kf8);
        colorHolder.setEvaluator(new ArgbEvaluator());

        ObjectAnimator objectAnimator=ObjectAnimator.ofPropertyValuesHolder(
                ball, widthHolder, heightHolder, colorHolder);

        objectAnimator.setDuration(3000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);

        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                invalidate();
            }
        });

        objectAnimator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_MOVE ||
                event.getAction()==MotionEvent.ACTION_DOWN){
            littleBall=addBalls(event.getX(),event.getY());
            balls.add(littleBall);

            AnimatorSet animatorSet= (AnimatorSet) AnimatorInflater.loadAnimator(
                    context, R.animator.bouncer);
            animatorSet.setTarget(littleBall);

            AnimatorSet lastSet= (AnimatorSet) animatorSet.getChildAnimations().get(1);
            ObjectAnimator lastAnim= (ObjectAnimator) lastSet.getChildAnimations().get(1);

            lastAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    balls.remove(((ObjectAnimator)animation).getTarget());
                }
            });
//            lastAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                    invalidate();
//                }
//            });
            animatorSet.start();
        }
       return true;
    }

    /**
     * 定义出要改变大小的球
     * @return 变化大小的球
     */
    private ShapeHolder changeBall(){
        OvalShape circle=new OvalShape();
        circle.resize(200f, 200f);
        ShapeDrawable drawable=new ShapeDrawable(circle);
        ShapeHolder ball=new ShapeHolder(drawable);

        ball.setX(0f);
        ball.setY(0f);
        return ball;
    }


    /**
     * 创建小球
     * @param x 点击的x坐标
     * @param y 点击的y坐标
     * @return  小球
     */
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

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(ball.getX(), ball.getY());
        ball.getShape().draw(canvas);
        canvas.restore();

        for (ShapeHolder shape:balls){
            canvas.save();
            canvas.translate(shape.getX(), shape.getY());
            shape.getShape().draw(canvas);
            canvas.restore();
        }
    }
}
