package com.example.kai.verschachtelt.graphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;

import com.example.kai.verschachtelt.R;
import com.example.kai.verschachtelt.activitys.MainActivity;
import com.example.kai.verschachtelt.chessLogic.Chessman;

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

    public boolean isActivated; // a bool value which shows, if the graphic is drawn
    private Chessman.Color color;


    public PawnChangeGraphic() {

        extractImages();
        this.isActivated = false;
        paint = new Paint();
        paint.setStrokeWidth(18);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(frameColor);
    }

    public void draw(Canvas canvas) {
        drawBackground(canvas);
        switch (color){
            case WHITE:
                drawWhiteChessmen(canvas);
                break;
            case BLACK:
                drawBlackChessmen(canvas);
                break;
        }
        drawFrames(canvas);
    }

    /**
     * Draws the black chessmen on the canvas that can be exchanged for the pawn.
     * @param canvas
     */
    private void drawBlackChessmen(Canvas canvas) {
        int imgSize = getShorterSide(canvas)/2;
        // draw the Queen
        canvas.drawBitmap(Bitmap.createScaledBitmap(imageBlackQueen, imgSize, imgSize, false),0,0,null);
        // draw the Rook
        canvas.drawBitmap(Bitmap.createScaledBitmap(imageBlackRook, imgSize, imgSize, false),imgSize,0,null);
        //draw the bishop
        canvas.drawBitmap(Bitmap.createScaledBitmap(imageBlackBishop, imgSize, imgSize, false),0,imgSize,null);
        //draw the knight
        canvas.drawBitmap(Bitmap.createScaledBitmap(imageBlackKnight, imgSize, imgSize, false),imgSize,imgSize,null);
    }

    /**
     * Draws the black chessmen on the canvas that can be exchanged for the pawn.
     * @param canvas
     */
    private void drawWhiteChessmen(Canvas canvas) {
        int imgSize = getShorterSide(canvas)/2;
        // draw the Queen
        canvas.drawBitmap(Bitmap.createScaledBitmap(imageWhiteQueen, imgSize, imgSize, false),0,0,null);
        // draw the Rook
        canvas.drawBitmap(Bitmap.createScaledBitmap(imageWhiteRook, imgSize, imgSize, false),imgSize,0,null);
        //draw the bishop
        canvas.drawBitmap(Bitmap.createScaledBitmap(imageWhiteBishop, imgSize, imgSize, false),0,imgSize,null);
        //draw the knight
        canvas.drawBitmap(Bitmap.createScaledBitmap(imageWhiteKnight, imgSize, imgSize, false),imgSize,imgSize,null);
    }

    /**
     * Draws some frames around the chessmen
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

    public void update(Chessman.Color color) {
        if(color == null) isActivated = false;
        else isActivated = true;
        this.color = color;
    }

    private void extractImages() {
        imageBlackRook = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.black_rook);
        imageBlackKnight = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.black_knight);
        imageBlackBishop = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.black_bishop);
        imageBlackQueen = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.black_queen);

        imageWhiteRook = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.white_rook);
        imageWhiteKnight = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.white_knight);
        imageWhiteBishop = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.white_bishop);
        imageWhiteQueen = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.white_queen);

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
