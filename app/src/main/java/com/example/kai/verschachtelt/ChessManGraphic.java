package com.example.kai.verschachtelt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Kai on 08.08.2016.
 * This class is for drawing the chess pieces (and maybe a colored frame around them)
 */
public class ChessManGraphic {

    private Paint paint;
    private int canvasWidth,canvasHeight;
    //The images of all chessmen
    private Bitmap  imageWhiteRook, imageWhiteKnight, imageWhiteBishop, imageWhiteQueen, imageWhiteKing, imageWhitePawn,
                    imageBlackRook, imageBlackKnight, imageBlackBishop, imageBlackQueen, imageBlackKing, imageBlackPawn;
    //The length/width of the section containing the chessman to be cropped out. Only applies to the now used image ressource.
    private final int cropSize = 92;
    private ChessBoard chessBoard;

    public ChessManGraphic(Bitmap res){
        paint = new Paint();
        extractImages(res);

        chessBoard = new ChessBoard();
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
            int x = getX(i);
            int y = getY(i);
            if(chessBoard.getChessManAt(i)!=null){
                canvas.drawBitmap(getChessManImage(chessBoard.getChessManAt(i)),x,y,null);
                int test =0;
            }
        }
    }
    //Calculate the X position on the screen where a chessPiece must be drawn.
    private int getX(int position){
        int stepWidth;                  //The length / width of a square on the board.
        if(canvasHeight<canvasWidth){ stepWidth = canvasHeight/10;
        }else{ stepWidth = canvasWidth/10;}

        int x = stepWidth + (position%8)*stepWidth;
        return x;
    }

    /**
     * Calculate the Y position on the screen where a chessPiece must be drawn.
     */
    private int getY(int position){
        int stepWidth;                  //The length / width of a square on the board.
        if(canvasHeight<canvasWidth){ stepWidth = canvasHeight/10;
        }else{ stepWidth = canvasWidth/10;}

        int y = stepWidth + (position/8)*stepWidth;
        return y;
    }

    private Bitmap getChessManImage(ChessBoard.chessMan chessMan){
        if(chessMan == ChessBoard.chessMan.WHITE_ROOK)return imageWhiteRook;
        if(chessMan == ChessBoard.chessMan.WHITE_KNIGHT)return imageWhiteKnight;
        if(chessMan == ChessBoard.chessMan.WHITE_BISHOP)return imageWhiteBishop;
        if(chessMan == ChessBoard.chessMan.WHITE_QUEEN)return imageWhiteQueen;
        if(chessMan == ChessBoard.chessMan.WHITE_KING)return imageWhiteKing;
        if(chessMan == ChessBoard.chessMan.WHITE_PAWN)return imageWhitePawn;
        //Black chessman
        if(chessMan == ChessBoard.chessMan.BLACK_ROOK)  return imageBlackRook;
        if(chessMan == ChessBoard.chessMan.BLACK_KNIGHT)return imageBlackKnight;
        if(chessMan == ChessBoard.chessMan.BLACK_BISHOP)return imageBlackBishop;
        if(chessMan == ChessBoard.chessMan.BLACK_QUEEN) return imageBlackQueen;
        if(chessMan == ChessBoard.chessMan.BLACK_KING)  return imageBlackKing;
        if(chessMan == ChessBoard.chessMan.BLACK_PAWN)  return imageBlackPawn;
        return null;
    }

    /**From a larger image, smaller images of the chessmen are cropped out.
     * @param imageChessmen
     */
    private void extractImages(Bitmap imageChessmen){
        imageBlackRook = Bitmap.createBitmap(imageChessmen,((960-cropSize)/5)*2,  112  ,cropSize,cropSize);
        imageBlackKnight = Bitmap.createBitmap(imageChessmen,((960-cropSize)/5)*4,112  ,cropSize,cropSize);
        imageBlackBishop = Bitmap.createBitmap(imageChessmen,((960-cropSize)/5)*3,112  ,cropSize,cropSize);
        imageBlackQueen = Bitmap.createBitmap(imageChessmen,((960-cropSize)/5)*1, 112  ,cropSize,cropSize);
        imageBlackKing = Bitmap.createBitmap(imageChessmen,0,      112  ,cropSize,cropSize);
        imageBlackPawn = Bitmap.createBitmap(imageChessmen,960-cropSize , 112  ,cropSize,cropSize);

        imageWhiteRook = Bitmap.createBitmap(imageChessmen,((960-cropSize)/5)*2,  265  ,cropSize,cropSize);
        imageWhiteKnight = Bitmap.createBitmap(imageChessmen,((960-cropSize)/5)*4,265  ,cropSize,cropSize);
        imageWhiteBishop = Bitmap.createBitmap(imageChessmen,((960-cropSize)/5)*3,265  ,cropSize,cropSize);
        imageWhiteQueen = Bitmap.createBitmap(imageChessmen,((960-cropSize)/5)*1, 265  ,cropSize,cropSize);
        imageWhiteKing = Bitmap.createBitmap(imageChessmen,0,      265  ,cropSize,cropSize);
        imageWhitePawn = Bitmap.createBitmap(imageChessmen,960-cropSize , 265  ,cropSize,cropSize);
    }
}
