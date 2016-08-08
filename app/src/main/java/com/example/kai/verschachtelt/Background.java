package com.example.kai.verschachtelt;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Kai on 08.08.2016.
 */
public class Background {

    private Bitmap image;
    private int x,y;

    public Background(Bitmap res){
        image =res;
    }

    public void update(){

    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(image,x,y,null);
    }
}
