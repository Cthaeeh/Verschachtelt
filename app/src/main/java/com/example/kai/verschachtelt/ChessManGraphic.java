package com.example.kai.verschachtelt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Kai on 08.08.2016.
 * This class is for drawing the chess pieces (and maybe a colored frame around them)
 */
public class ChessmanGraphic {

    private int squareSize;                              //The length / width of a square on the board.
    private final int STROKE_WIDTH = 4;                 //The thickness of the frame around a square in hundredth of squareSize.
    private final int normalFrameColor = Color.BLACK;   //TODO get this somehow from the ressources, either by passing it through from activity or passing the context.
    private final int selectedSquareFrameColor = Color.BLUE;
    private final int possibleMoveFrameColor = Color.GREEN;
    private final int killMoveFrameColor = Color.RED;
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
        squareSize = GamePanel.squareSize;                                    //length/width of a square of the chess board. Kind of global variable. Muste be changed.
        int devideIndipendentStrokeWidth = (squareSize /100)*STROKE_WIDTH;    //stepwidth is screen size sensitive -> deviceIndependentStrokewith is now as well.
        paint.setStrokeWidth(devideIndipendentStrokeWidth);
        //draw all pieces, and their frames
        for(int i = 0;i < 64; i++){
            if(chessBoardSimple.getSquareStateAt(i)== ChessBoardSimple.SquareState.POSSIBLE){    //If you can move to this square highlight it.
                int x = getX(i);
                int y = getY(i);
                paint.setColor(possibleMoveFrameColor);                             //Special Color for this type pf square.
                canvas.drawRect(x+devideIndipendentStrokeWidth/2,y+devideIndipendentStrokeWidth/2,
                        x+ squareSize -devideIndipendentStrokeWidth/2,y+ squareSize -devideIndipendentStrokeWidth/2,paint);
            }
            if(chessBoardSimple.getChessManAt(i)!=null){
                int x = getX(i);
                int y = getY(i);
                canvas.drawBitmap(getChessManImage(chessBoardSimple.getChessManAt(i)),x,y,null);
                paint.setStrokeWidth(devideIndipendentStrokeWidth);
                //Draw a matching frame arround the current square
                if(chessBoardSimple.getSquareStateAt(i)== ChessBoardSimple.SquareState.NORMAL)paint.setColor(normalFrameColor);
                if(chessBoardSimple.getSquareStateAt(i)== ChessBoardSimple.SquareState.SELECTED)paint.setColor(selectedSquareFrameColor);
                if(chessBoardSimple.getSquareStateAt(i)== ChessBoardSimple.SquareState.POSSIBLE_KILL)paint.setColor(killMoveFrameColor);
                canvas.drawRect(x+devideIndipendentStrokeWidth/2,y+devideIndipendentStrokeWidth/2,
                                x+ squareSize -devideIndipendentStrokeWidth/2,y+ squareSize -devideIndipendentStrokeWidth/2,paint);
            }
        }
    }
    //Calculate the X position on the screen where a chessPiece must be drawn.
    private int getX(int position){
        int x = squareSize + (position%8)* squareSize;
        return x;
    }

    /**
     * Calculate the Y position on the screen where a chessPiece must be drawn.
     */
    private int getY(int position){
        int y = squareSize + (position/8)* squareSize;
        return y;
    }

    private Bitmap getChessManImage(ChessBoardSimple.ChessMan ChessMan){
        if(ChessMan == ChessBoardSimple.ChessMan.WHITE_ROOK)return imageWhiteRook;
        if(ChessMan == ChessBoardSimple.ChessMan.WHITE_KNIGHT)return imageWhiteKnight;
        if(ChessMan == ChessBoardSimple.ChessMan.WHITE_BISHOP)return imageWhiteBishop;
        if(ChessMan == ChessBoardSimple.ChessMan.WHITE_QUEEN)return imageWhiteQueen;
        if(ChessMan == ChessBoardSimple.ChessMan.WHITE_KING)return imageWhiteKing;
        if(ChessMan == ChessBoardSimple.ChessMan.WHITE_PAWN)return imageWhitePawn;
        //Black chessman
        if(ChessMan == ChessBoardSimple.ChessMan.BLACK_ROOK)  return imageBlackRook;
        if(ChessMan == ChessBoardSimple.ChessMan.BLACK_KNIGHT)return imageBlackKnight;
        if(ChessMan == ChessBoardSimple.ChessMan.BLACK_BISHOP)return imageBlackBishop;
        if(ChessMan == ChessBoardSimple.ChessMan.BLACK_QUEEN) return imageBlackQueen;
        if(ChessMan == ChessBoardSimple.ChessMan.BLACK_KING)  return imageBlackKing;
        if(ChessMan == ChessBoardSimple.ChessMan.BLACK_PAWN)  return imageBlackPawn;
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
