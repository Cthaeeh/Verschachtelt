package com.example.kai.verschachtelt;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Kai on 08.08.2016.
 * This class is for drawing the chess pieces (and maybe a colored frame around them)
 */
public class ChessManGraphic {

    private Paint paint;
    private int canvasWidth,canvasHeight;



    public ChessManGraphic(){
        paint = new Paint();
    }

    public void update(){

    }

    public void draw(Canvas canvas){
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        //draw all pieces
        for(int i = 0;i < 64; i++){

        }
    }
}
