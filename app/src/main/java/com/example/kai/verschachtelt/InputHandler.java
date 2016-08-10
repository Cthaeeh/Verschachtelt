package com.example.kai.verschachtelt;

import android.view.MotionEvent;

import com.example.kai.verschachtelt.chessLogic.ChessBoardComplex;
import com.example.kai.verschachtelt.chessLogic.ChessBoardSimple;

/**
 * Created by Kai on 09.08.2016.
 * This class is for handling touch events and translate them to chess specific squares, moves
 */
public class InputHandler {

    private ChessBoardComplex board;
    private float squareSize;


    public InputHandler(){
        board = new ChessBoardComplex();
    }

    public void processTouchEvent(MotionEvent event){
        squareSize = GamePanel.squareSize;
        float x = event.getX();
        float y = event.getY();
        Integer position = getPositionOnBoard(x,y);
        if(position==-1) board.resetFrames();         //If you touch outside the board all frames are reseted.
        else{
            handleTouchOnSquare(position);
        }
    }

    /**
     * passes the position that was touched to a ComplexChessBoard.
     * Depending on the state of the touched squareStates a chessman is selected, deselected, moves
     * @param position
     */
    private void handleTouchOnSquare(Integer position) {
        if(board.getSquareStateAt(position)== ChessBoardSimple.SquareState.NORMAL) {    //A normal squareStates was touched -> select it.
            board.resetFrames();                                                        //Deselect other Squares
            board.setSquareStateAt(position, ChessBoardSimple.SquareState.SELECTED);    //Select the squareStates
            board.handleSquareSelection(position);
            return;
        }
        if(board.getSquareStateAt(position)== ChessBoardSimple.SquareState.SELECTED) {  //A selected squareStates was touched -> deselect it -> all squares normal again.
            board.setSquareStateAt(position, ChessBoardSimple.SquareState.NORMAL);
            board.handleSquareSelection(-1);                                            //We have nowhere to move from.
            board.resetFrames();
            return;
        }
        if(board.getSquareStateAt(position)== ChessBoardSimple.SquareState.POSSIBLE){   //If a chessman is selected and there is a squareStates where it can move
            board.handleMove(position);                                                     //Move there.
            board.resetFrames();
            return;
        }
        if(board.getSquareStateAt(position)== ChessBoardSimple.SquareState.POSSIBLE_KILL){  //same as above
            board.handleMove(position);
            board.resetFrames();
            return;
        }
    }


    /**
     * Translate the exact touch position on the canvas to a squareStates on the board.
     * Or if outside return null
     * @param x
     * @param y
     * @return  an Integer representing the squareStates on the chess Board
     */
    private int getPositionOnBoard(float x,float y){
        if(x<squareSize || y < squareSize || x > squareSize*9 || y > squareSize*9) return -1; //Touch event was not on the chess Board.
        int position = (int) (((int)(x-squareSize)/(int)(squareSize))+(8*((int)(y-squareSize)/(int)squareSize)));//Translate to Position on Board
        return position;

    }

    public ChessBoardSimple getChessBoardState() {
        return board;
    }

}
