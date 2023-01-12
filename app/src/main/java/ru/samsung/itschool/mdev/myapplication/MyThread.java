package ru.samsung.itschool.mdev.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.Random;

public class MyThread extends Thread {

    private Paint paint;
    // держатель для SurfaceView
    private SurfaceHolder holder;
    private boolean flag;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    MyThread(SurfaceHolder h) {
        this.holder = h;
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true); // сглаживание
    }

    public long getTime() {
        return System.nanoTime()/1000; // микросек.
    }

    // UnixTime
    // 01/01/1970
    private long redrawTime = 0;

    @Override
    public void run() {
        Canvas canvas;
        while(flag) {
            long currentTime = getTime();
            long elapsedTime = currentTime - redrawTime;
            if(elapsedTime < 1000000) {
                continue;
            }
            // перерисовка круга
            // вызов метода
            // блокируем Canvas для отрисовки
            canvas = holder.lockCanvas();
            drawCircle(canvas);
            // разблокируем и показываем
            holder.unlockCanvasAndPost(canvas);
            redrawTime = getTime();
        }
    }

    public void drawCircle(Canvas canvas) {
        int x = canvas.getWidth()/2;
        int y = canvas.getHeight()/2;
        canvas.drawColor(Color.BLACK);
        Random r = new Random();
        paint.setColor(Color.rgb(r.nextInt(255),
                r.nextInt(255),
                r.nextInt(255)));
        float rad = (float)(300*Math.random());
      //  Log.d("RRR","r="+rad);
        canvas.drawCircle(x,y,rad,paint);

    }
}
