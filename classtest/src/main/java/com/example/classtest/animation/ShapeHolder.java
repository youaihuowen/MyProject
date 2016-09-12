package com.example.classtest.animation;

import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by WUYIXIONG on 2016-8-22.
 */
public class ShapeHolder {
    private float x=0,y=0;
    private float alpha=1f;
    private int color;
    private Paint paint;
    //图形绘制类
    private ShapeDrawable shape;
    //环形放射
    private RadialGradient gradient;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        shape.setAlpha((int)((alpha*255f)+0.5f));
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        shape.getPaint().setColor(color);
        this.color=color;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public ShapeDrawable getShape() {
        return shape;
    }

    public void setShape(ShapeDrawable shape) {
        this.shape = shape;
    }

    public RadialGradient getGradient() {
        return gradient;
    }

    public void setGradient(RadialGradient gradient) {
        Paint paint=getShape().getPaint();
        paint.setShader(gradient);
        this.gradient = gradient;
    }

    public float getWidth() {
        return shape.getShape().getWidth();
    }
    public void setWidth(float width) {
        Shape s = shape.getShape();
        s.resize(width, s.getHeight());
    }

    public float getHeight() {
        return shape.getShape().getHeight();
    }
    public void setHeight(float height) {
        Shape s = shape.getShape();
        s.resize(s.getWidth(), height);
    }
    public ShapeHolder(ShapeDrawable s){
        shape=s;
    }
}
