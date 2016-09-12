package com.example.game2048.entity;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by WUYIXIONG on 2016-9-2.
 * @description  卡片类
 */
public class Card extends FrameLayout {

    private int num;//卡片上的数字
    private TextView tv_num;//用于显示卡片上的数字
    Context mContext;


    public Card(Context context) {
        super(context);
        mContext=context;
        setNum(0);

        initTextView();
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    /**
     * 判断两张卡片上的数字是否相等
     * @param c 要比较的卡片
     * @return 相等 true 不等 false
     */
    private boolean equal(Card c){
        return c.getNum()==getNum();
    }

    /**
     * 初始化显示数字的textview
     */
    private void initTextView(){
        tv_num=new TextView(mContext);//实例化textview
        tv_num.setTextSize(32);//设置卡片上数字的大小
        ViewGroup.LayoutParams lp = new LayoutParams(-1,-1);//-1,-1表示充满父容器
        tv_num.setText(num);
        addView(tv_num,lp);//将数字的textview添加到card中
    }
}
