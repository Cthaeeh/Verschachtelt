package com.example.kai.verschachtelt.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;

import com.example.kai.verschachtelt.chessLogic.ChessBoardComplex;

/**
 * Created by ivayl on 14.08.2016.
 */
public class PawnChangeGraphic {

    // images of all available chessmen

    private Bitmap imageWhiteRook, imageWhiteKnight, imageWhiteBishop, imageWhiteQueen,
                   imageBlackRook, imageBlackKnight, imageBlackBishop, imageBlackQueen;

    private final int cropSize = 92;

    public boolean activated; // a bool value which shows, if the graphic is drawn

   // private ChessBoardComplex complex;



    public PawnChangeGraphic(Bitmap map) {

        extractImages(map);
        this.activated = false;
       // complex = new ChessBoardComplex();

    }



public void draw(Canvas canvas) {


    // draw the Queen
    canvas.drawBitmap(Bitmap.createScaledBitmap(imageBlackQueen, getShorterSide(canvas)/2, getShorterSide(canvas)/2, false),0,0,null);
    // draw the Rook
    canvas.drawBitmap(Bitmap.createScaledBitmap(imageBlackRook, getShorterSide(canvas)/2, getShorterSide(canvas)/2, false),getShorterSide(canvas)/2,0,null);
    //draw the bishop
    canvas.drawBitmap(Bitmap.createScaledBitmap(imageBlackBishop, getShorterSide(canvas)/2, getShorterSide(canvas)/2, false),0,getShorterSide(canvas)/2,null);
    //draw the knight
    canvas.drawBitmap(Bitmap.createScaledBitmap(imageBlackKnight, getShorterSide(canvas)/2, getShorterSide(canvas)/2, false),getShorterSide(canvas)/2,getShorterSide(canvas)/2,null);

}

    public void update(ChessBoardComplex board) {

      //  this.complex = board;

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
        this.activated = true;
    }

    public void deactivate() {
        this.activated = false;
    }
}
