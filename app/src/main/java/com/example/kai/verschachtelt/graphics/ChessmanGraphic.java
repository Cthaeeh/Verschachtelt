package com.example.kai.verschachtelt.graphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private boolean drawnFirstTime = true;
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

    public ChessmanGraphic(){
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        extractImages();

        chessBoardSimple = new ChessBoardSimple();
    }

    public void update(ChessBoardSimple chessboard){
        this.chessBoardSimple = chessboard;
    }

    public void draw(Canvas canvas){
        squareSize = GamePanel.squareSize;                                    //length/width of a squareStates of the chess boardCurrent. Kind of global variable. Muste be changed.
        if(drawnFirstTime){
            scaleImages();
            drawnFirstTime=false;
        }
        int deviceIndependentStrokeWidth = (squareSize /100)*STROKE_WIDTH;    //stepwidth is screen size sensitive -> deviceIndependentStrokewith is now as well.
        paint.setStrokeWidth(deviceIndependentStrokeWidth);
        //draw all pieces and their frames
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
        return (position%8)* squareSize;
    }

    /**
     * Calculate the Y position on the screen where a chessPiece must be drawn.
     */
    private int getY(int position){
        return (position/8)* squareSize;
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
    private void extractImages(){
        imageBlackRook = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.black_rook);
        imageBlackKnight = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.black_knight);
        imageBlackBishop = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.black_bishop);
        imageBlackQueen = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.black_queen);
        imageBlackKing = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.black_king);
        imageBlackPawn = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.black_pawn);

        imageWhiteRook = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.white_rook);
        imageWhiteKnight = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.white_knight);
        imageWhiteBishop = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.white_bishop);
        imageWhiteQueen = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.white_queen);
        imageWhiteKing = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.white_king);
        imageWhitePawn = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.white_pawn);
    }

    private void scaleImages(){
        imageBlackRook = Bitmap.createScaledBitmap(imageBlackRook, squareSize, squareSize, false);
        imageBlackKnight = Bitmap.createScaledBitmap(imageBlackKnight, squareSize, squareSize, false);
        imageBlackBishop = Bitmap.createScaledBitmap(imageBlackBishop, squareSize, squareSize, false);
        imageBlackQueen = Bitmap.createScaledBitmap(imageBlackQueen, squareSize, squareSize, false);
        imageBlackKing = Bitmap.createScaledBitmap(imageBlackKing, squareSize, squareSize, false);
        imageBlackPawn = Bitmap.createScaledBitmap(imageBlackPawn, squareSize, squareSize, false);

        imageWhiteRook = Bitmap.createScaledBitmap(imageWhiteRook, squareSize, squareSize, false);
        imageWhiteKnight = Bitmap.createScaledBitmap(imageWhiteKnight, squareSize, squareSize, false);
        imageWhiteBishop = Bitmap.createScaledBitmap(imageWhiteBishop, squareSize, squareSize, false);
        imageWhiteQueen = Bitmap.createScaledBitmap(imageWhiteQueen, squareSize, squareSize, false);
        imageWhiteKing = Bitmap.createScaledBitmap(imageWhiteKing, squareSize, squareSize, false);
        imageWhitePawn = Bitmap.createScaledBitmap(imageWhitePawn, squareSize, squareSize, false);
    }
}
