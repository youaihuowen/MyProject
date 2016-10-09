package com.example.play_the_plane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by WUYIXIONG on 2016-10-8.
 */

public class GameView extends SurfaceView implements Runnable, SurfaceHolder.Callback, View.OnTouchListener {

    private Bitmap bg;//背景
    private Bitmap bullet;//子弹
    private Bitmap enemy;//敌人
    private Bitmap explosion;//爆炸
    private Bitmap my;//自己
    private Bitmap erjihuancun;//二级缓存图片
    private int display_w;
    private int display_h;
    ArrayList<GameImage> list = new ArrayList<GameImage>();
    ArrayList<Zidan> bullets = new ArrayList<Zidan>();


    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        this.setOnTouchListener(this);//事件注册
    }

    private void init() {
        //加载图片
        bg = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        bullet = BitmapFactory.decodeResource(getResources(), R.drawable.bullet);
        enemy = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
        explosion = BitmapFactory.decodeResource(getResources(), R.drawable.explosion);
        my = BitmapFactory.decodeResource(getResources(), R.drawable.my);
        //初始化二级缓存图片
        erjihuancun = Bitmap.createBitmap(display_w, display_h, Bitmap.Config.ARGB_8888);
        list.add(new BeiJingImage(bg));//先加入背景照片
        list.add(new FeiJiImage(my));//加入自己的飞机
        list.add(new DiJiImage(enemy));
    }

    private boolean state = false;
    private SurfaceHolder holder = null;

    //绘画
    @Override
    public void run() {
        Paint p = new Paint();
        int diji_num = 0;//出敌机的计时
        int bullet_num=0;
        try {
            while (state) {
                Canvas c = new Canvas(erjihuancun);
                //判断什么时候发射子弹
                if (selectFeiji != null) {
                    if (bullet_num==5){
                        bullets.add(new Zidan(selectFeiji, bullet));
                        bullet_num=0;
                    }
                    bullet_num++;
                }


                for (GameImage image : (ArrayList<GameImage>) list.clone()) {
                    c.drawBitmap(image.getBitmap(), image.getX(), image.getY(), p);
                }

                for (Zidan image : bullets) {
                    c.drawBitmap(image.getBitmap(), image.getX(), image.getY(), p);
                }
                //每当计数到50，出一架敌机
                if (diji_num == 70) {
                    diji_num = 0;
                    list.add(new DiJiImage(enemy));
                }
                diji_num++;

                Canvas canvas = holder.lockCanvas();
                canvas.drawBitmap(erjihuancun, 0, 0, p);
                holder.unlockCanvasAndPost(canvas);
                Thread.sleep(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.holder = holder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //获取屏幕的宽和高
        display_w = width;
        display_h = height;
        init();
        state = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        state = false;
    }

    /**
     * 点击事件监听
     *
     * @param v
     * @param event
     * @return
     */
    FeiJiImage selectFeiji = null;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (GameImage image : list) {
                if (image instanceof FeiJiImage) {
                    FeiJiImage feiji = (FeiJiImage) image;
                    if (feiji.getX() < event.getX()
                            && feiji.getY() < event.getY()
                            && feiji.getX() + feiji.getWidth() > event.getX()
                            && feiji.getY() + feiji.getHeight() > event.getY()) {
                        selectFeiji = feiji;
                    } else {
                        selectFeiji = null;
                    }
                    break;
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            selectFeiji = null;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (selectFeiji != null) {
                selectFeiji.setX((int) (event.getX() - selectFeiji.getWidth() / 2));
                selectFeiji.setY((int) (event.getY() - selectFeiji.getHeight() / 2));
            }
        }
        return true;
    }


    private interface GameImage {
        public Bitmap getBitmap();

        public int getX();

        public int getY();
    }


    /**
     * 负责照片的处理
     */
    private class BeiJingImage implements GameImage {
        private Bitmap bg;
        private Bitmap newBitmap = null;

        public BeiJingImage(Bitmap bg) {
            this.bg = bg;
            newBitmap = Bitmap.createBitmap(display_w, display_h, Bitmap.Config.ARGB_8888);
        }

        private int height = 0;

        @Override
        public Bitmap getBitmap() {
            Canvas canvas = new Canvas(newBitmap);
            Paint p = new Paint();

            canvas.drawBitmap(bg,
                    new Rect(0, 0, bg.getWidth(), bg.getHeight()),
                    new Rect(0, height, display_w, display_h + height),
                    p);
            canvas.drawBitmap(bg,
                    new Rect(0, 0, bg.getWidth(), bg.getHeight()),
                    new Rect(0, -display_h + height, display_w, height),
                    p);
            height++;
            if (height == display_h) {
                height = 0;
            }
            return newBitmap;
        }

        @Override
        public int getX() {
            return 0;
        }

        @Override
        public int getY() {
            return 0;
        }
    }


    /**
     * 飞机的封装类
     */
    private class FeiJiImage implements GameImage {

        private Bitmap my;
        List<Bitmap> bitmaps = new ArrayList<Bitmap>();
        private int x, y;
        private int width;//照片的宽
        private int height;//照片的高

        public FeiJiImage(Bitmap my) {
            this.my = my;
            bitmaps.add(Bitmap.createBitmap(my, 0, 0, my.getWidth() / 4, my.getHeight()));
            bitmaps.add(Bitmap.createBitmap(my, (my.getWidth() / 4) * 1, 0, my.getWidth() / 4, my.getHeight()));
            bitmaps.add(Bitmap.createBitmap(my, (my.getWidth() / 4) * 2, 0, my.getWidth() / 4, my.getHeight()));
            bitmaps.add(Bitmap.createBitmap(my, (my.getWidth() / 4) * 3, 0, my.getWidth() / 4, my.getHeight()));

            //得到照片的宽高
            width = my.getWidth() / 4;
            height = my.getHeight();
            //飞机的初始位置坐标
            x = (display_w - my.getWidth() / 4) / 2;
            y = display_h - my.getHeight() - 15;
        }

        private int index = 0;//飞机图片的下标
        private int num = 0;//控制图片更换的控制数，每调用10次 index加一

        @Override
        public Bitmap getBitmap() {
            Bitmap bitmap = bitmaps.get(index);
            if (num == 10) {
                index++;
                if (index == bitmaps.size()) {
                    index = 0;
                }
                num = 0;
            }
            num++;
            return bitmap;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }

        public boolean select(int x, int y) {
            return false;
        }
    }

    /**
     * 敌人飞机的包装类
     */
    private class DiJiImage implements GameImage {
        Bitmap enemy;
        List<Bitmap> bitmaps = new ArrayList<Bitmap>();
        private int x;
        private int y;

        public DiJiImage(Bitmap enemy) {
            this.enemy = enemy;
            bitmaps.add(Bitmap.createBitmap(enemy,
                    0, 0, enemy.getWidth() / 4, enemy.getHeight()));
            bitmaps.add(Bitmap.createBitmap(enemy,
                    (enemy.getWidth() / 4) * 1, 0, enemy.getWidth() / 4, enemy.getHeight()));
            bitmaps.add(Bitmap.createBitmap(enemy,
                    (enemy.getWidth() / 4) * 2, 0, enemy.getWidth() / 4, enemy.getHeight()));
            bitmaps.add(Bitmap.createBitmap(enemy,
                    (enemy.getWidth() / 4) * 3, 0, enemy.getWidth() / 4, enemy.getHeight()));

            y = -enemy.getHeight();
            Random ran = new Random();
            x = ran.nextInt(display_w - enemy.getWidth() / 4);
        }

        private int index = 0;//敌人飞机图片的下标
        private int num = 0;//控制图片更换的控制数，每调用10次 index加一

        @Override
        public Bitmap getBitmap() {
            Bitmap bitmap = bitmaps.get(index);
            if (num == 10) {
                index++;
                if (index == bitmaps.size()) {
                    index = 0;
                }
                num = 0;
            }
            num++;
            y += 2;
            if (y > display_h) {
                list.remove(this);
            }
            return bitmap;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }
    }

    /**
     * 子弹的封装类
     */
    private class Zidan implements GameImage {
        FeiJiImage feiji;
        Bitmap bullet;
        private int x;
        private int y;

        public Zidan(FeiJiImage feiji, Bitmap bullet) {
            this.feiji = feiji;
            this.bullet = bullet;
            x = feiji.getX() + feiji.getWidth() / 2 - bullet.getWidth() / 2;
            y = feiji.getY() - bullet.getHeight();
        }

        @Override
        public Bitmap getBitmap() {
            y -= 10;
            if (y<-10){
                bullets.remove(this);
            }
            return bullet;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }
    }

}
