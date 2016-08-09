package com.example.kai.verschachtelt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Kai on 08.08.2016.
 * This class is for drawing the chess pieces (and maybe a colored frame around them)
 */
public class ChessManGraphic {

    private int stepWidth;                              //The length / width of a square on the board.
    private final int STROKE_WIDTH = 4;                 //The thickness of the frame around a square in hundredth of stepWidth.
    private final int normalFrameColor = Color.BLACK;   //TODO get this somehow from the ressources, either by passing it through from activity or passing the context.
    private final int selectedSquareFrameColor = Color.BLUE;
    private final int possibleMoveFrameColor = Color.GREEN;
    private final int killMoveFrameColor = Color.RED;
    private Paint paint;

    //The images of all chessmen
    private Bitmap  imageWhiteRook, imageWhiteKnight, imageWhiteBishop, imageWhiteQueen, imageWhiteKing, imageWhitePawn,
                    imageBlackRook, imageBlackKnight, imageBlackBishop, imageBlackQueen, imageBlackKing, imageBlackPawn;
    private final int cropSize = 92;    //The length/width of the section containing the chessman to be cropped out. Only applies to the now used image ressource.

    private ChessBoard chessBoard;

    public ChessManGraphic(Bitmap res){
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        extractImages(res);

        chessBoard = new ChessBoard();
    }

    public void update(){

    }

    public void draw(Canvas canvas){
        int devideIndipendentStrokeWidth = (stepWidth/100)*STROKE_WIDTH;    //stepwidth is screen size sensitive -> deviceIndependentStrokewith is now as well.
        paint.setStrokeWidth(devideIndipendentStrokeWidth);

        calculateStepWidth(canvas.getWidth(),canvas.getHeight());           //The canvas size could have changed so we have to recalculate stepwidth.

        //draw all pieces, and their frames
        for(int i = 0;i < 64; i++){
            if(chessBoard.getSquareStateAt(i)==ChessBoard.squareState.POSSIBLE){    //If you can move to this square highlight it.
                int x = getX(i);
                int y = getY(i);
                paint.setColor(possibleMoveFrameColor);                             //Special Color for this type pf square.
                canvas.drawRect(x+devideIndipendentStrokeWidth/2,y+devideIndipendentStrokeWidth/2,
                        x+stepWidth-devideIndipendentStrokeWidth/2,y+stepWidth-devideIndipendentStrokeWidth/2,paint);
            }
            if(chessBoard.getChessManAt(i)!=null){
                int x = getX(i);
                int y = getY(i);
                canvas.drawBitmap(getChessManImage(chessBoard.getChessManAt(i)),x,y,null);
                paint.setStrokeWidth(devideIndipendentStrokeWidth);
                //Draw a matching frame arround the current square
                if(chessBoard.getSquareStateAt(i)==ChessBoard.squareState.NORMAL)paint.setColor(normalFrameColor);
                if(chessBoard.getSquareStateAt(i)==ChessBoard.squareState.SELECTED)paint.setColor(selectedSquareFrameColor);
                if(chessBoard.getSquareStateAt(i)==ChessBoard.squareState.POSSIBLE_KILL)paint.setColor(possibleMoveFrameColor);
                canvas.drawRect(x+devideIndipendentStrokeWidth/2,y+devideIndipendentStrokeWidth/2,
                                x+stepWidth-devideIndipendentStrokeWidth/2,y+stepWidth-devideIndipendentStrokeWidth/2,paint);
            }
        }
    }
    //Calculate the X position on the screen where a chessPiece must be drawn.
    private int getX(int position){
        int x = stepWidth + (position%8)*stepWidth;
        return x;
    }

    /**
     * Calculate the Y position on the screen where a chessPiece must be drawn.
     */
    private int getY(int position){
        int y = stepWidth + (position/8)*stepWidth;
        return y;
    }

    private void calculateStepWidth(int canvasWidth,int canvasHeight){
        if(canvasHeight<canvasWidth){ stepWidth = canvasHeight/10;
        }else{ stepWidth = canvasWidth/10;}
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
