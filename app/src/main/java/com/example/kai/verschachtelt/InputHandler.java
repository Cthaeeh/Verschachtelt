package com.example.kai.verschachtelt;

import android.view.MotionEvent;

import com.example.kai.verschachtelt.chessLogic.ChessBoardComplex;
import com.example.kai.verschachtelt.chessLogic.ChessBoardSimple;

/**
 * Created by Kai on 09.08.2016.
 * This class is for handling touch events and translate them to chess specific squares, moves
 */
public class InputHandler {

    private float squareSize;
    private InputEvent inputEvent;

    public InputHandler(){
    }

    public void processTouchEvent(MotionEvent event){
        squareSize = GamePanel.squareSize;
        float x = event.getX();
        float y = event.getY();
        Integer position = getPositionOnBoard(x,y);
        inputEvent.handleTouchOnSquare(position);
    }

    /**
     * Translate the exact touch position on the canvas to a squareStates on the boardCurrent.
     * Or if outside return null
     * @param x     xPosition on canvas
     * @param y     yPosition on canvas
     * @return  an Integer representing the squareStates on the chess Board
     */
    private int getPositionOnBoard(float x,float y){
        if(x<squareSize || y < squareSize || x > squareSize*9 || y > squareSize*9) return -1; //Touch event was not on the chess Board.
        int position = (((int)(x-squareSize)/(int)(squareSize))+(8*((int)(y-squareSize)/(int)squareSize)));//Translate to Position on Board
        return position;

    }

    public void subscribe(InputEvent inputEvent) {
        this.inputEvent = inputEvent;
    }

    public void processRedoButton() {
        inputEvent.handleRedoButton();
    }

    public void processUndoButton() {
        inputEvent.handleUndoButton();
    }

    public void processShowNextMoveButton() {
        inputEvent.handleShowNextMoveButton();
    }
}
