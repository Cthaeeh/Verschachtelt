package com.example.kai.verschachtelt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Kai on 08.08.2016.
 */
public class Background {

    private Bitmap image;
    private int x,y;
    private double avgFPS;
    private Paint paint;

    public Background(Bitmap res){
        image = res;
        paint = new Paint();        //Set the Text Size large enough
        paint.setTextSize(40.f);
    }

    public void update(double avgFPS){
        this.avgFPS = avgFPS;
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(image,x,y,null);
        canvas.drawText(String.valueOf(avgFPS)+" fps",10.f,40.f,paint);
    }
}
