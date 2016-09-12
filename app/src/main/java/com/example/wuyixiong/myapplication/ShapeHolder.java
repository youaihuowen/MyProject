package com.example.wuyixiong.myapplication;

import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

/**自定义的图形容器
 * Created by WUYIXIONG on 2016-8-23.
 */
public class ShapeHolder {
    private float x=0,y=0;
    private int color;
    private float alpha=1f;
    private Paint paint;
    private ShapeDrawable shape;
    private RadialGradient gradient;

    public ShapeHolder(ShapeDrawable shape) {
        this.shape = shape;
    }

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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        shape.getPaint().setColor(color);
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        shape.setAlpha((int)(alpha*255f+0.5f));
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
        this.gradient = gradient;
        shape.getPaint().setShader(gradient);
    }
    public float getWidth(){
       return shape.getShape().getWidth();
    }
    public void setWidth(float width){
        Shape shape1=shape.getShape();
        shape1.resize(width,shape1.getHeight());
    }
    public float getHeight(){
        return shape.getShape().getHeight();
    }
    public void setHeight(float height){
        Shape shape1=shape.getShape();
        shape1.resize(shape1.getWidth(),height);
    }
}
