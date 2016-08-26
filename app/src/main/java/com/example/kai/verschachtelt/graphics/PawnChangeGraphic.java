package com.example.kai.verschachtelt.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;

import com.example.kai.verschachtelt.R;
import com.example.kai.verschachtelt.activitys.MainActivity;

/**
 * Created by ivayl on 14.08.2016.
 */
public class PawnChangeGraphic {

    // images of all available chessmen

    private Bitmap imageWhiteRook, imageWhiteKnight, imageWhiteBishop, imageWhiteQueen,
                   imageBlackRook, imageBlackKnight, imageBlackBishop, imageBlackQueen;
    private Paint paint;
    private final int frameColor = ContextCompat.getColor(MainActivity.getContext(), R.color.pawnChangeFrameColor);
    private final int backgroundColor = ContextCompat.getColor(MainActivity.getContext(), R.color.pawnChangeBackgroundColor);

    private final int cropSize = 92;

    public boolean isActivated; // a bool value which shows, if the graphic is drawn

    public PawnChangeGraphic(Bitmap map) {

        extractImages(map);
        this.isActivated = false;
        paint = new Paint();
        paint.setStrokeWidth(18);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(frameColor);
    }

    public void draw(Canvas canvas) {

        drawBackground(canvas);

        int imgSize = getShorterSide(canvas)/2;
        // draw the Queen
        canvas.drawBitmap(Bitmap.createScaledBitmap(imageBlackQueen, imgSize, imgSize, false),0,0,null);
        // draw the Rook
        canvas.drawBitmap(Bitmap.createScaledBitmap(imageBlackRook, imgSize, imgSize, false),imgSize,0,null);
        //draw the bishop
        canvas.drawBitmap(Bitmap.createScaledBitmap(imageBlackBishop, imgSize, imgSize, false),0,imgSize,null);
        //draw the knight
        canvas.drawBitmap(Bitmap.createScaledBitmap(imageBlackKnight, imgSize, imgSize, false),imgSize,imgSize,null);
        drawFrames(canvas);
    }

    /**
     * Draws some frames arround the chessmen
     * @param canvas
     */
    private void drawFrames(Canvas canvas) {
        int imgSize = getShorterSide(canvas)/2;
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(frameColor);
        canvas.drawRect(0,0,imgSize,imgSize,paint);
        canvas.drawRect(imgSize,0,2*imgSize,imgSize,paint);
        canvas.drawRect(0,imgSize,imgSize,2*imgSize,paint);
        canvas.drawRect(imgSize,imgSize,2*imgSize,2*imgSize,paint);
    }

    /**
     * Draws a Background for the chessmen (that you can choose from)
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        int imgSize = getShorterSide(canvas)/2;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(backgroundColor);
        canvas.drawRect(0,0,2*imgSize,2*imgSize,paint);
    }

    public void update(boolean isActivated) {
        this.isActivated = isActivated;
    }

    private void extractImages(Bitmap imageChessmen) {
        imageBlackRook = Bitmap.createBitmap(imageChessmen, ((960 - cropSize) / 5) * 2, 112, cropSize, cropSize);
        imageBlackKnight = Bitmap.createBitmap(imageChessmen, ((960 - cropSize) / 5) * 4, 112, cropSize, cropSize);
        imageBlackBishop = Bitmap.createBitmap(imageChessmen, ((960 - cropSize) / 5) * 3, 112, cropSize, cropSize);
        imageBlackQueen = Bitmap.createBitmap(imageChessmen, ((960 - cropSize) / 5) * 1, 112, cropSize, cropSize);

        imageWhiteRook = Bitmap.createBitmap(imageChessmen, ((960 - cropSize) / 5) * 2, 265, cropSize, cropSize);
        imageWhiteKnight = Bitmap.createBitmap(imageChessmen, ((960 - cropSize) / 5) * 4, 265, cropSize, cropSize);
        imageWhiteBishop = Bitmap.createBitmap(imageChessmen, ((960 - cropSize) / 5) * 3, 265, cropSize, cropSize);
        imageWhiteQueen = Bitmap.createBitmap(imageChessmen, ((960 - cropSize) / 5) * 1, 265, cropSize, cropSize);

    }

    private int getShorterSide(Canvas canvas){
        if(canvas.getHeight()<canvas.getWidth()) {
            return canvas.getHeight();
        }else{
            return canvas.getWidth();
        }
    }

    public void activate(){
        this.isActivated = true;
    }

    public void deactivate() {
        this.isActivated = false;
    }

}
