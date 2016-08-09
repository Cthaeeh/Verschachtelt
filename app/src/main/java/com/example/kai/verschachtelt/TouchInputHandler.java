package com.example.kai.verschachtelt;

import android.view.MotionEvent;

/**
 * Created by Kai on 09.08.2016.
 * This class is for handling touch events and translate them to chess specific squares, moves
 */
public class TouchInputHandler {

    private ChessBoardSimple chessBoardSimple;
    private float squareSize;

    public TouchInputHandler(){
        chessBoardSimple = new ChessBoardSimple();
    }

    public void processTouchEvent(MotionEvent event){
        squareSize = GamePanel.squareSize;
        float x = event.getX();
        float y = event.getY();
        Integer position = getPositionOnBoard(x,y);
        if(position==-1) chessBoardSimple.resetFrames();         //If you touch outside the chessBoardSimple all frames are reseted.
        else{
            handleTouchOnSquare(position);
        }
    }

    private void handleTouchOnSquare(Integer position) {
        if(chessBoardSimple.getSquareStateAt(position)== ChessBoardSimple.SquareState.NORMAL) {    //A normal square was touched -> select it.
            chessBoardSimple.setSquareStateAt(position, ChessBoardSimple.SquareState.SELECTED);

            return;
        }
        if(chessBoardSimple.getSquareStateAt(position)== ChessBoardSimple.SquareState.SELECTED) {    //A selected square was touched -> deselect it -> normal again.
            chessBoardSimple.setSquareStateAt(position, ChessBoardSimple.SquareState.NORMAL);

            return;
        }
        if(chessBoardSimple.getSquareStateAt(position)== ChessBoardSimple.SquareState.POSSIBLE){
            return;
        }
        if(chessBoardSimple.getSquareStateAt(position)== ChessBoardSimple.SquareState.POSSIBLE_KILL){
            return;
        }
    }


    /**
     * Translate the exact touch position on the canvas to a square on the chessBoardSimple.
     * Or if outside return null
     * @param x
     * @param y
     * @return  an Integer representing the square on the chess Board
     */
    private int getPositionOnBoard(float x,float y){
        if(x<squareSize || y < squareSize || x > squareSize*9 || y > squareSize*9) return -1; //Touch event was not on the chess Board.
        int position = (int) (((int)(x-squareSize)/(int)(squareSize))+(8*((int)(y-squareSize)/(int)squareSize)));
        return position;

    }

    public ChessBoardSimple getChessBoardState() {
        return chessBoardSimple;
    }

}
