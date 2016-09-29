package com.example.kai.verschachtelt.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Kai on 08.08.2016.
 * In development this class is used for displaying dev infos,
 * because logcat doesn´t work ...
 */
public class Background {

    private final Bitmap image;
    private int x,y;        //Position of Background

    public Background(Bitmap res){
        image = res;
    }

    public void update(){

    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image,x,y,null);
    }
}
