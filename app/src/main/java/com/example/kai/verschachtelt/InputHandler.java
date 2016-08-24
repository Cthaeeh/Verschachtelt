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
    private float figureChangeSize;
    private InputEvent inputEvent;

    public void processTouchEvent(MotionEvent event){

        squareSize = GamePanel.squareSize;
        float x = event.getX();
        float y = event.getY();
        Integer position = getPositionOnBoard(x,y);
        inputEvent.handleTouchOnSquare(position);
    }

    /**
     * this method activates the handling of a touch on the screen and is
     * meant to be used only for a touch, happening while the
     * PawnChangeGraphic is shown.
     * @param event: detected event, in this case a touch on the screen
     */
    public void processPawnChangeEvent(MotionEvent event) {

        /**size of a square on the PawnChangeGraphic, in which
         * the figures to be chosen from are shown
         */
        figureChangeSize = GamePanel.figureChangeSize;
        float x = event.getX();
        float y = event.getY();

        inputEvent.handleTouchOnFigure(x,y,figureChangeSize);
    }

    /**
     * Translate the exact touch position on the canvas to a squareStates on the boardCurrent.
     * Or if outside return null
     * @param x     xPosition on canvas
     * @param y     yPosition on canvas
     * @return  an Integer representing the squareStates on the chess Board
     */
    private int getPositionOnBoard(float x,float y){
        squareSize = GamePanel.squareSize;
        if(x<0 || y < 0 || x > squareSize*8 || y > squareSize*8) return -1; //Touch event was not on the chess Board.
        int position = (((int)(x)/(int)(squareSize))+(8*((int)(y)/(int)squareSize)));//Translate to Position on Board
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
