package com.example.kai.verschachtelt;

import com.example.kai.verschachtelt.chessLogic.ChessBoardComplex;
import com.example.kai.verschachtelt.chessLogic.ChessBoardSimple;

/**
 * Created by Kai on 11.08.2016.
 */
public class ChessGame implements InputEvent {
    private InputHandler inputHandler;
    private ChessBoardComplex board;

    public ChessGame(InputHandler inputHandler){
        this.inputHandler = inputHandler;
        inputHandler.subscribe(this);
    }

    public void handleTouchOnSquare(Integer position) {
        if(position==-1){
            board.resetFrames();         //If you touch outside the board all frames are reseted.
            return;
        }

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

    public ChessBoardSimple getSimpleBoard(){
        return board;
    }
}
