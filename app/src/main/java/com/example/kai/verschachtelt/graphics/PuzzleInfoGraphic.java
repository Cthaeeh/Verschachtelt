package com.example.kai.verschachtelt.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;

import com.example.kai.verschachtelt.GamePanel;
import com.example.kai.verschachtelt.R;
import com.example.kai.verschachtelt.activitys.MainActivity;


/**
 * Created by Kai on 15.08.2016.
 */
public class PuzzleInfoGraphic {

    private Paint paint;
    private final String puzzleDescription;
    private String       puzzleDescription2 = "Steps 0 out of 0 solved";
    private int squareSize;
    private final int decriptionColor = ContextCompat.getColor(MainActivity.getContext(), R.color.decriptionColor);

    public PuzzleInfoGraphic(String puzzleDescription){
        this.puzzleDescription=puzzleDescription;
        paint = new Paint();
        paint.setColor(decriptionColor);
    }

    public void update(String text){
        puzzleDescription2 = text;
    }

    public void draw(Canvas canvas){
        //TODO make this method orientation sensitive.
        squareSize = GamePanel.squareSize;
        paint.setTextSize(squareSize/2);
        canvas.drawText(puzzleDescription,squareSize,squareSize*10,paint);
        canvas.drawText(puzzleDescription2,squareSize,squareSize*11,paint);
    }
}
