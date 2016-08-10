package com.example.kai.verschachtelt.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.kai.verschachtelt.GamePanel;

/**
 * Created by Kai on 08.08.2016.
 * This class is for drawing the chess board on the canvas.
 */
public class ChessBoardGraphic {

    private Paint paint;
    private float squareSize;              //length and width of a field of the board

    public ChessBoardGraphic(){
        paint = new Paint();
        paint.setStrokeWidth(3);
    }

    public void update(){

    }
    public void draw(Canvas canvas){
        squareSize = GamePanel.squareSize;      //Very ugly must be changed ! TODO find a better solution than this
        float[] pts = getLinePts();             //Starting and end Points of all Lines
        Rect[]  rects = getRects();             //All rectangulars around squares that must be filled with color.

        paint.setStyle(Paint.Style.STROKE);
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

        int   rectCount = 0;          //Just a counter to go through the array

        for(int i = 0;i < 32; i++){
            int x = (i%4)*2;
            int y = (i/4);
            if((i/4)%2==0)x++;

            int left =  (int) (squareSize + x* squareSize);
            int top  =  (int) (squareSize + y* squareSize);
            int right = (int) (left+ squareSize);
            int bottom = (int) (top+ squareSize);

            rects[i] = new Rect(left,top,right,bottom);
        }


        return rects;
    }

    //Calculates all points of the Chessboard, which connected make up the board
    //Ugly code ahead ;)
    private float[] getLinePts() {
        float[] pts = new float[72];//8*8 fields so 9+9=18 lines. To define a line it needs 4 values -> 18*4=72
        int   ptCount = 0;          //Just a counter to go through the array

        //vertical lines
        for(int x=1;x<10;x++){
            int y = 1;
            pts[ptCount]=x*squareSize;
            ptCount++;
            pts[ptCount]=y*squareSize;
            ptCount++;
            y = 9;
            pts[ptCount]=x*squareSize;
            ptCount++;
            pts[ptCount]=y*squareSize;
            ptCount++;
        }
        //horizontal lines
        for(int y=1;y<10;y++){
            int x = 1;
            pts[ptCount]=x*squareSize;
            ptCount++;
            pts[ptCount]=y*squareSize;
            ptCount++;
            x = 9;
            pts[ptCount]=x*squareSize;
            ptCount++;
            pts[ptCount]=y*squareSize;
            ptCount++;
        }
        return pts;
    }

}
