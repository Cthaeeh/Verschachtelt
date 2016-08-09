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
    private Bitmap image;
    private ChessBoard chessBoard;

    public ChessManGraphic(Bitmap res){
        paint = new Paint();
        image = res;
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
    //Calculate the Y position on the screen where a chessPiece must be drawn.
    private int getY(int position){
        int stepWidth;                  //The length / width of a square on the board.
        if(canvasHeight<canvasWidth){ stepWidth = canvasHeight/10;
        }else{ stepWidth = canvasWidth/10;}

        int y = stepWidth + (position/8)*stepWidth;
        return y;
    }

    private Bitmap getChessManImage(ChessBoard.chessMan chessMan){
        if(chessMan == ChessBoard.chessMan.WHITE_ROOK)
            return Bitmap.createBitmap(image,1,2,3,4);
        if(chessMan == ChessBoard.chessMan.WHITE_KNIGHT)
            return Bitmap.createBitmap(image,1,2,3,4);
        if(chessMan == ChessBoard.chessMan.WHITE_BISHOP)
            return Bitmap.createBitmap(image,1,2,3,4);
        if(chessMan == ChessBoard.chessMan.WHITE_QUEEN)
            return Bitmap.createBitmap(image,1,2,3,4);
        if(chessMan == ChessBoard.chessMan.WHITE_KING)
            return Bitmap.createBitmap(image,1,2,3,4);
        if(chessMan == ChessBoard.chessMan.WHITE_PAWN)
            return Bitmap.createBitmap(image,1,2,3,4);
        //Black chessman
        if(chessMan == ChessBoard.chessMan.BLACK_ROOK){
            return Bitmap.createBitmap(image,174*3,112,88,88);}
        if(chessMan == ChessBoard.chessMan.BLACK_KNIGHT){
            return Bitmap.createBitmap(image,174*5,112,88,88);}
        if(chessMan == ChessBoard.chessMan.BLACK_BISHOP){
            return Bitmap.createBitmap(image,174*4,112,88,88);}
        if(chessMan == ChessBoard.chessMan.BLACK_QUEEN){
            return Bitmap.createBitmap(image,174*2,112,88,88);}
        if(chessMan == ChessBoard.chessMan.BLACK_KING)
            return Bitmap.createBitmap(image,0,112,88,88);
        if(chessMan == ChessBoard.chessMan.BLACK_PAWN)
            try {
                return Bitmap.createBitmap(image,960-88,112, 88,88);
            }catch (Exception e){
                String output = e.toString();
                int test = image.getWidth();
                int x =0;
            }

        return null;
    }
}
