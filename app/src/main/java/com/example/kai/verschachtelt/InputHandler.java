package com.example.kai.verschachtelt;

import android.view.MotionEvent;

import com.example.kai.verschachtelt.chessLogic.Chessman;

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
     * this method calculates on which chessman (from PawnChangeGraphic you touched) and tells
     * the ChessGame that.
     * Meant to be used only for a touch, happening while the
     * PawnChangeGraphic is shown.
     * @param event: detected event, in this case a touch on the screen
     */
    public void processPawnChangeEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float imgSize = 4*GamePanel.squareSize;
        if(0 < x && x < imgSize && 0<y  && y < imgSize){            //upper left edge
            inputEvent.handlePromotion(Chessman.Piece.QUEEN);
        }
        if(imgSize < x && x < 2*imgSize && 0<y  && y < imgSize) {   //upper right edge
            inputEvent.handlePromotion(Chessman.Piece.ROOK);
        }
        if(0 < x && x < imgSize && imgSize<y  && y < 2*imgSize) {   //lower left edge
            inputEvent.handlePromotion(Chessman.Piece.BISHOP);
        }
        if(imgSize < x && x < 2*imgSize && imgSize<y  && y < 2*imgSize) {//lower right edge
            inputEvent.handlePromotion(Chessman.Piece.KNIGHT);
        }
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
