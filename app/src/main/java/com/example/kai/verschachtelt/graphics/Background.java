package com.example.kai.verschachtelt.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Kai on 08.08.2016.
 */
public class Background {

    //TODO somehow react to a change from portrait to landscape mode

    private Bitmap image;
    private int x,y;        //Position of Background
    private String info;    //Dev info to display
    public static String ai_debug_info = "";
    private Paint paint;

    public Background(Bitmap res){
        image = res;
        paint = new Paint();        //Set the Text Size large enough
        paint.setTextSize(40.f);
    }

    public void update(String info){
        this.info = info;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image,x,y,null);
        canvas.drawText(info,10.f,40.f,paint);  //Draw info in upper left edge
        canvas.drawText(ai_debug_info,10.f,canvas.getHeight()-80,paint);   //TODO remove when ship
    }
}
