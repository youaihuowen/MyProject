package com.example.classtest.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;

/**
 * @author WUYIXIONG 2016-8-31
 * @description 布局过渡动画的demo类
 */
public class TransitionActivity extends BaseActivity {

    //被添加view的容器
    LinearLayout ll_container;
    //当前添加按钮的数量
    int count;
    //自定义布局的过渡动画
    LayoutTransition transition = new LayoutTransition();
    //数据列表
    ListView lv_transition;
    @Override
    protected int setContent() {
        return R.layout.activity_transition;
    }

    @Override
    protected void initView() {
        ll_container = (LinearLayout) findViewById(R.id.ll_Transition_container);
        lv_transition = (ListView) findViewById(R.id.lv_transition);
        setTransition();
        //装载过渡动画
        setListView();
    }

    @Override
    protected void setLisetner() {

    }

    /**
     * @description 向容器中添加按钮（点击后触发）
     * @param view 监听的view
     */
    public void addButton(View view){
        //数量递增
        count++;
        //实例化一个按钮
        Button button = new Button(this);
        //按钮设置文本
        button.setText("Button"+count);
        //取消全部字母大写
        button.setAllCaps(false);
        //实例化一个布局参数
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);

        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        //给添加的按钮设置布局参数
        button.setLayoutParams(params);
        //将按钮添加到容器中
        ll_container.addView(button,0);

    }

    /**
     * @description 从容器中移除按钮（点击后触发）
     * @param view 监听的view
     */
    public void removeButton(View view){
        //判断当前是否以添加了按钮
        if (count > 0){
            //数量递减
            count--;
            //移除按钮
            ll_container.removeViewAt(0);
        }
    }

    /**
     * @description 设置布局的过渡动画
     */
    private void setTransition(){

        //自定义添加按钮的动画
        //target参数填null，最终setAnimator的时候会自动设置
        ValueAnimator appearAnim= ObjectAnimator.ofFloat(null,"rotationY",90f,0f);
        //设置一个默认的播放时间(300ms)
        appearAnim.setDuration(transition.getDuration(LayoutTransition.APPEARING));
        //给过渡动画指定动画类型和动画效果
        transition.setAnimator(LayoutTransition.APPEARING,appearAnim);

        //自定义删除View的动画
        //target参数填null，最终setAnimator的时候会自动设置
        ValueAnimator disappearAnim= ObjectAnimator.ofFloat(null,"rotationY",0f,90f,0f);
        //设置一个默认的播放时间(300ms)
        appearAnim.setDuration(transition.getDuration(LayoutTransition.DISAPPEARING));
        //给过渡动画指定动画类型和动画效果
        transition.setAnimator(LayoutTransition.DISAPPEARING,disappearAnim);

        //给指定容器设置过渡动画
        ll_container.setLayoutTransition(transition);

        //对于CHANGE_APPEARING 和 CHANGE_DISAPPEARING 类型的过渡动画
        //1.必须使用PropertyValuesHolder来构造动画
        //2.必须同时指定left top right bottom属性 否则无法正确显示
        //3.动画结束后也需要将view改为原样，否则会出现BUG
        //4.构建ObjectAnimator时需要设置对象

        //设置CHANGE_APPEARING类型的动画
        PropertyValuesHolder left=PropertyValuesHolder.ofInt("left",0,0);
        PropertyValuesHolder right=PropertyValuesHolder.ofInt("right",0,0);
        PropertyValuesHolder top=PropertyValuesHolder.ofInt("top",0,0);
        PropertyValuesHolder bottom=PropertyValuesHolder.ofInt("bottom",0,0);
        //编写自定义的CHANGE_APPEARING
        PropertyValuesHolder scaleX=PropertyValuesHolder.ofFloat("scaleX",1f,2f,1f);
        //通过PropertyValuesHolder构建动画
        ValueAnimator cgapAnim=ObjectAnimator.ofPropertyValuesHolder(
                ll_container,left,right,top,bottom,scaleX);

        //为view复原
        cgapAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ((View) ((ObjectAnimator)animation).getTarget()).setScaleX(1f);
            }
        });

        //给过渡动画指定动画类型和动画效果
        transition.setAnimator(LayoutTransition.CHANGE_APPEARING, cgapAnim);

        //这里借用了CHANGE_APPEARING的动画效果
        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, cgapAnim);

    }

    /**
     * @description 设置数据列表
     */
    private void setListView(){
        //设置列表的适配器
        lv_transition.setAdapter(new TransitionAdapter(this));
//        //加载单个iteam动画
//        Animation rightin= AnimationUtils.loadAnimation(this,R.anim.rightin);
//        //根据单个的item动画创建布局过渡动画控制器
//        LayoutAnimationController lc=new LayoutAnimationController(rightin);
//        //设置动画组延迟
//        lc.setDelay(0.4f);
//        //设置动画组播放次序
//        lc.setOrder(LayoutAnimationController.ORDER_RANDOM);
//        //给目标ListView设置item布局过渡动画
//        lv_transition.setLayoutAnimation(lc);
    }
}
