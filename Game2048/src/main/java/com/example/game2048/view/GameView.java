package com.example.game2048.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

import com.example.game2048.entity.Card;

/**
 * Created by WUYIXIONG on 2016-9-2.
 */
public class GameView extends GridView {
    public GameView(Context context) {
        super(context);
        initView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        setNumColumns(4);
        setOnTouchListener(new OnTouchListener() {
            float startX,startY,endX,endY,offsetX, offsetY;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    startX = motionEvent.getX();
                    startY = motionEvent.getY();

                }
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    endX = motionEvent.getX();
                    endY = motionEvent.getY();
                    offsetX = endX - startX;
                    offsetY = endY - startY;
                    if(Math.abs(offsetX)>Math.abs(offsetY)){
                        if(offsetX > 5){
                            rightOperat();
                        }
                        else if(offsetX < -5){
                            leftOperat();
                        }
                    }else{
                        if(offsetY > 5){
                            downOperat();
                        }else if (offsetY < -5){
                            upOperat();
                        }
                    }
                }

                return true;
            }
        });
    }

    private void leftOperat(){
        Log.i("tag", "leftOperat: ");
    }
    private void rightOperat(){
        Log.i("tag", "rightOperat: ");
    }
    private void upOperat(){
        Log.i("tag", "upOperat: ");
    }
    private void downOperat(){
        Log.i("tag", "downOperat: ");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth=(Math.min(w,h)-10)/4;
        addCards(cardWidth, cardWidth);
    }

    private void addCards(int width, int height){
        Card c;
        for (int x=0; x<4; x++){
            for (int y=0; y<4; y++){
                c=new Card(getContext());
                c.setNum(2);
                addView(c, width, height);
            }
        }
    }
}
