package com.example.kai.verschachtelt.graphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.kai.verschachtelt.R;
import com.example.kai.verschachtelt.activitys.MainActivity;

/**
 * Created by Kai on 12.08.2016.
 */
public class VictoryScreenGraphic {

    private Bitmap image, imageVictory, imageDefeat, imageWhiteWins, imageBlackWins, imageDraw;
    private int x,y;        //Position of Background
    private VictoryState victoryState = null;
    public enum VictoryState {
        VICTORY,DEFEAT,WHITEWIN,BLACKWIN, DRAW
    }


    public VictoryScreenGraphic(Bitmap res){
        image = res;
        extractImages();
    }

    public void update(VictoryState victoryState){
        this.victoryState = victoryState;
    }

    public void draw(Canvas canvas){
        switch (victoryState){
            case VICTORY:
                canvas.drawBitmap(Bitmap.createScaledBitmap(imageVictory, getShorterSide(canvas), getShorterSide(canvas), false),0,0,null);
                break;
            case DEFEAT:
                canvas.drawBitmap(Bitmap.createScaledBitmap(imageDefeat, getShorterSide(canvas), getShorterSide(canvas), false),0,0,null);
                break;
            case WHITEWIN:
                canvas.drawBitmap(Bitmap.createScaledBitmap(imageWhiteWins, getShorterSide(canvas), getShorterSide(canvas), false),0,0,null);
                break;
            case BLACKWIN:
                canvas.drawBitmap(Bitmap.createScaledBitmap(imageBlackWins, getShorterSide(canvas), getShorterSide(canvas), false),0,0,null);
                break;
            case DRAW:
                canvas.drawBitmap(Bitmap.createScaledBitmap(imageDraw,getShorterSide(canvas), getShorterSide(canvas), false),0,0,null );
                break;
        }
    }

    private void extractImages(){
        imageVictory = Bitmap.createBitmap(image  ,0   ,0   ,1000,1000);
        imageDefeat  = Bitmap.createBitmap(image  ,1000,0   ,1000,1000);
        imageWhiteWins = Bitmap.createBitmap(image,0   ,1000,1000,1000);
        imageBlackWins = Bitmap.createBitmap(image,1000,1000,1000,1000);
        imageDraw = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.drawbackground);
      //  imageDraw = Bitmap.createBitmap(image,2000,0,1000,1000);
    }

    private int getShorterSide(Canvas canvas){
        if(canvas.getHeight()<canvas.getWidth()) {
            return canvas.getHeight();
        }else{
            return canvas.getWidth();
        }
    }
}
