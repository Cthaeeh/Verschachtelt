package com.example.kai.verschachtelt;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Kai on 08.08.2016.
 */
public class ChessBoardGraphic {

    private Paint paint;
    private int canvasWidth,canvasHeight;

    public ChessBoardGraphic(){
        paint = new Paint();
    }

    public void update(){

    }
    public void draw(Canvas canvas){
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        float[] pts = getLinePts();             //Starting and end Points of all Lines
        Rect[]  rects = getRects();
        paint.setColor(Color.BLACK);
        canvas.drawLines(pts,paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.LTGRAY);
        for(int i = 0; i<32;i++){
            canvas.drawRect(rects[i],paint);
        }
    }

    //Calculate all Rectangulars of the Chessboard that need to be filled with color
    //Ugly code ahead ;)
    private Rect[] getRects() {
        Rect[] rects = new Rect[32];//
        float stepWidth;              //length and width of a field of the board
        int   rectCount = 0;          //Just a counter to go through the array

        if(canvasHeight<canvasWidth){
            stepWidth = canvasHeight/10;
        }else{
            stepWidth = canvasWidth/10;
        }

        for(int i = 0;i < 32; i++){
            int x = (i%4)*2;
            int y = (i/4);
            if((i/4)%2==0)x++;

            int left =  (int) (stepWidth + x*stepWidth);
            int top  =  (int) (stepWidth + y*stepWidth);
            int right = (int) (left+stepWidth);
            int bottom = (int) (top+stepWidth);

            rects[i] = new Rect(left,top,right,bottom);
        }


        return rects;
    }

    //Calculates all points of the Chessboard, which connected make up the board
    //Ugly code ahead ;)
    private float[] getLinePts() {
        float[] pts = new float[72];//8*8 fields so 9+9=18 lines. To define a line it needs 4 values -> 18*4=72
        float stepWidth;            //length and width of a field of the board
        int   ptCount = 0;          //Just a counter to go through the array

        if(canvasHeight<canvasWidth){
            stepWidth = canvasHeight/10;
        }else{
            stepWidth = canvasWidth/10;
        }
        //vertical lines
        for(int x=1;x<10;x++){
            int y = 1;
            pts[ptCount]=x*stepWidth;
            ptCount++;
            pts[ptCount]=y*stepWidth;
            ptCount++;
            y = 9;
            pts[ptCount]=x*stepWidth;
            ptCount++;
            pts[ptCount]=y*stepWidth;
            ptCount++;
        }
        //horizontal lines
        for(int y=1;y<10;y++){
            int x = 1;
            pts[ptCount]=x*stepWidth;
            ptCount++;
            pts[ptCount]=y*stepWidth;
            ptCount++;
            x = 9;
            pts[ptCount]=x*stepWidth;
            ptCount++;
            pts[ptCount]=y*stepWidth;
            ptCount++;
        }
        return pts;
    }
}
