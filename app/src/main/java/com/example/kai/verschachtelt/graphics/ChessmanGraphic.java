package com.example.kai.verschachtelt.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;

import com.example.kai.verschachtelt.R;
import com.example.kai.verschachtelt.activitys.MainActivity;
import com.example.kai.verschachtelt.chessLogic.ChessBoardSimple;
import com.example.kai.verschachtelt.GamePanel;
import com.example.kai.verschachtelt.chessLogic.Chessman;

/**
 * Created by Kai on 08.08.2016.
 * This class is for drawing the chess pieces (and maybe a colored frame around them)
 */
public class ChessmanGraphic {

    private int squareSize;                              //The length / width of a squareStates on the boardCurrent.
    //The thickness of the frame around a squareStates in hundredth of squareSize.
    private final int STROKE_WIDTH = 4; //TODO make this a dimen ressource
    private final int normalFrameColor = ContextCompat.getColor(MainActivity.getContext(), R.color.normalFrameColor);
    private final int selectedSquareFrameColor = ContextCompat.getColor(MainActivity.getContext(), R.color.selectedFrameColor);
    private final int possibleMoveFrameColor = ContextCompat.getColor(MainActivity.getContext(), R.color.possibleMoveFrameColor);
    private final int killMoveFrameColor = ContextCompat.getColor(MainActivity.getContext(), R.color.possibleKillFrameColor);
    private Paint paint;

    //The images of all chessmen
    private Bitmap  imageWhiteRook, imageWhiteKnight, imageWhiteBishop, imageWhiteQueen, imageWhiteKing, imageWhitePawn,
                    imageBlackRook, imageBlackKnight, imageBlackBishop, imageBlackQueen, imageBlackKing, imageBlackPawn;
    private final int cropSize = 92;    //The length/width of the section containing the chessman to be cropped out. Only applies to the now used image ressource.

    private ChessBoardSimple chessBoardSimple;

    public ChessmanGraphic(Bitmap res){
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        extractImages(res);

        chessBoardSimple = new ChessBoardSimple();
    }

    public void update(ChessBoardSimple chessboard){
        this.chessBoardSimple = chessboard;
    }

    public void draw(Canvas canvas){
        squareSize = GamePanel.squareSize;                                    //length/width of a squareStates of the chess boardCurrent. Kind of global variable. Muste be changed.
        int deviceIndependentStrokeWidth = (squareSize /100)*STROKE_WIDTH;    //stepwidth is screen size sensitive -> deviceIndependentStrokewith is now as well.
        paint.setStrokeWidth(deviceIndependentStrokeWidth);
        //draw all pieces, and their frames
        for(int i = 0;i < 64; i++){
            if(chessBoardSimple.getSquareStateAt(i)== ChessBoardSimple.SquareState.POSSIBLE){    //If you can move to this squareStates highlight it.
                int x = getX(i);
                int y = getY(i);
                paint.setColor(possibleMoveFrameColor);                             //Special Color for this type pf squareStates.
                canvas.drawRect(x+deviceIndependentStrokeWidth/2,y+deviceIndependentStrokeWidth/2,
                        x+ squareSize -deviceIndependentStrokeWidth/2,y+ squareSize -deviceIndependentStrokeWidth/2,paint);
            }
            if(chessBoardSimple.getChessManAt(i)!=null){
                int x = getX(i);
                int y = getY(i);
                canvas.drawBitmap(getChessManImage(chessBoardSimple.getChessManAt(i)),x,y,null);
                paint.setStrokeWidth(deviceIndependentStrokeWidth);
                //Draw a matching frame arround the current squareStates
                if(chessBoardSimple.getSquareStateAt(i)== ChessBoardSimple.SquareState.NORMAL)paint.setColor(normalFrameColor);
                if(chessBoardSimple.getSquareStateAt(i)== ChessBoardSimple.SquareState.SELECTED)paint.setColor(selectedSquareFrameColor);
                if(chessBoardSimple.getSquareStateAt(i)== ChessBoardSimple.SquareState.POSSIBLE_KILL)paint.setColor(killMoveFrameColor);
                canvas.drawRect(x+deviceIndependentStrokeWidth/2,y+deviceIndependentStrokeWidth/2,
                                x+ squareSize -deviceIndependentStrokeWidth/2,y+ squareSize -deviceIndependentStrokeWidth/2,paint);
            }
        }
    }
    //Calculate the X position on the screen where a chessPiece must be drawn.
    private int getX(int position){
        return squareSize + (position%8)* squareSize;
    }

    /**
     * Calculate the Y position on the screen where a chessPiece must be drawn.
     */
    private int getY(int position){
        return squareSize + (position/8)* squareSize;
    }

    private Bitmap getChessManImage(Chessman chessman){
        if(chessman.getPiece() == Chessman.Piece.ROOK){
            if(chessman.getColor() == Chessman.Color.WHITE) return imageWhiteRook;
            else                                            return imageBlackRook;}
        if(chessman.getPiece() == Chessman.Piece.KNIGHT){
            if(chessman.getColor() == Chessman.Color.WHITE) return imageWhiteKnight;
            else                                            return imageBlackKnight;}
        if(chessman.getPiece() == Chessman.Piece.BISHOP){
            if(chessman.getColor() == Chessman.Color.WHITE) return imageWhiteBishop;
            else                                            return imageBlackBishop;}
        if(chessman.getPiece() == Chessman.Piece.QUEEN){
            if(chessman.getColor() == Chessman.Color.WHITE) return imageWhiteQueen;
            else                                            return imageBlackQueen;}
        if(chessman.getPiece() == Chessman.Piece.KING){
            if(chessman.getColor() == Chessman.Color.WHITE) return imageWhiteKing;
            else                                            return imageBlackKing;}
        if(chessman.getPiece() == Chessman.Piece.PAWN){
            if(chessman.getColor() == Chessman.Color.WHITE) return imageWhitePawn;
            else                                            return imageBlackPawn;}
        return null;
    }

    /**From a larger image, smaller images of the chessmen are cropped out.
     * @param imageChessmen A large image with all the chessmen
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
