package com.example.kai.verschachtelt;

import com.example.kai.verschachtelt.chessLogic.ChessBoardComplex;
import com.example.kai.verschachtelt.chessLogic.ChessBoardSimple;
import com.example.kai.verschachtelt.chessLogic.ChessBoardSimple.SquareState;

/**
 * Created by Kai on 11.08.2016.
 */
public class ChessGame implements InputEvent {
    private InputHandler inputHandler;
    protected ChessBoardComplex board;


    public ChessGame(InputHandler inputHandler){
        this.inputHandler = inputHandler;
        inputHandler.subscribe(this);
        board = new ChessBoardComplex();
    }

    /**
     * passes the position that was touched to a ComplexChessBoard.
     * Depending on the state of the touched squareStates a chessman is selected, deselected, moves
     * @param position  The position on the board that was touched. Starts counting upper left edge. 0-63
     */
    public void handleTouchOnSquare(Integer position) {
        if(position <0){            //If the position is outside of the Chess board reset the frames.
            board.resetFrames();
            return;
        }
        if(board.getSquareStateAt(position)== SquareState.NORMAL) {    //A normal squareStates was touched -> select it.
            board.resetFrames();                                                        //Deselect other Squares
            board.setSquareStateAt(position,SquareState.SELECTED);    //Select the squareStates
            board.handleSquareSelection(position);
            return;
        }
        if(board.getSquareStateAt(position)== SquareState.SELECTED) {  //A selected squareStates was touched -> deselect it -> all squares normal again.
            board.setSquareStateAt(position, SquareState.NORMAL);
            board.handleSquareSelection(-1);                                            //We have nowhere to move from.
            board.resetFrames();
            return;
        }
        if(board.getSquareStateAt(position)== SquareState.POSSIBLE){   //If a chessman is selected and there is a squareStates where it can move
            move(position);
            return;
        }
        if(board.getSquareStateAt(position)== SquareState.POSSIBLE_KILL){  //same as above
            move(position);
        }

    }

    protected void move(int position){
        board.handleMoveTo(position);
        board.resetFrames();
    }

    public ChessBoardSimple getSimpleBoard(){
        return board;
    }
}
