package com.example.kai.verschachtelt;

import com.example.kai.verschachtelt.chessLogic.ChessBoardComplex;
import com.example.kai.verschachtelt.chessLogic.ChessBoardSimple;
import com.example.kai.verschachtelt.chessLogic.ChessBoardSimple.SquareState;
import com.example.kai.verschachtelt.chessLogic.Chessman;
import com.example.kai.verschachtelt.graphics.VictoryScreenGraphic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kai on 11.08.2016.
 * This class is for representing the current state of the game.
 * It holds the current Chess Board, a history of previous chess Boards (like a Stack)
 * this stuff is manipulated through the inputEvent interface.
 */
public class ChessGame implements InputEvent {
    private InputHandler inputHandler;
    protected ChessBoardComplex boardCurrent;
    private List<ChessBoardComplex> boardHistory = new ArrayList<>();
    //Number of moves performed by both players.
    protected int moveCounter = 0;

    private Chessman.Color changing; // for switchpawn

    public ChessGame(InputHandler inputHandler){
        this.inputHandler = inputHandler;
        //Subscribe so the inputHandler can call back.
        inputHandler.subscribe(this);
        //Create a new ChessBoard
        boardCurrent = new ChessBoardComplex();
        //Add this one as first entry in the history
        boardHistory.add(new ChessBoardComplex(boardCurrent));
    }

    /**
     * passes the position that was touched to a ComplexChessBoard.
     * Depending on the state of the touched squareStates a chessman is selected, deselected, moves
     * @param position  The position on the boardCurrent that was touched. Starts counting upper left edge. 0-63
     */
    public void handleTouchOnSquare(Integer position) {
        if(position <0){            //If the position is outside of the Chess boardCurrent reset the frames.
            boardCurrent.resetFrames();
            return;
        }
        if(boardCurrent.getSquareStateAt(position)== SquareState.NORMAL) {    //A normal squareStates was touched -> select it.
            boardCurrent.resetFrames();                                       //Deselect other Squares
            boardCurrent.setSquareStateAt(position,SquareState.SELECTED);     //Select the squareStates
            boardCurrent.handleSquareSelection(position);
            return;
        }
        if(boardCurrent.getSquareStateAt(position)== SquareState.SELECTED) {  //A selected squareStates was touched -> deselect it -> all squares normal again.
            boardCurrent.setSquareStateAt(position, SquareState.NORMAL);
            boardCurrent.handleSquareSelection(-1);                                            //We have nowhere to move from.
            boardCurrent.resetFrames();
            return;
        }
        if(boardCurrent.getSquareStateAt(position)== SquareState.POSSIBLE){   //If a chessman is selected and there is a squareStates where it can move
            move(position);
            return;
        }
        if(boardCurrent.getSquareStateAt(position)== SquareState.POSSIBLE_KILL){  //same as above
            move(position);
        }

    }

    public void handleTouchOnFigure(float x, float y, float shorterSide) {


        if(boardCurrent.getPlayerOnTurn() == Chessman.Color.BLACK){
            changing = Chessman.Color.WHITE;
        } else {
            changing = Chessman.Color.BLACK;

        }
        if(x < shorterSide & y < shorterSide){
            // draw a queen, if screen was touched top left
            Chessman newQueen = new Chessman(Chessman.Piece.QUEEN, changing);
            boardCurrent.switchPawn(boardCurrent.pawnChangePosition(), newQueen);
        }
        if(x >= shorterSide & y < shorterSide) {
            // draw a rook, if screen was touched top right
            Chessman newRook = new Chessman(Chessman.Piece.ROOK,changing);
            boardCurrent.switchPawn(boardCurrent.pawnChangePosition(), newRook);
        }
        if(x < shorterSide & y > shorterSide) {
            //draw a bishop, if screen was touched bottom left
            Chessman newBishop = new Chessman(Chessman.Piece.BISHOP, changing);
            boardCurrent.switchPawn(boardCurrent.pawnChangePosition(), newBishop);
        }

        if(x > shorterSide & y > shorterSide) {
            //draw a knight, if screen was touched bottom right
            Chessman newKnight = new Chessman(Chessman.Piece.KNIGHT, changing);
            boardCurrent.switchPawn(boardCurrent.pawnChangePosition(), newKnight);
        }
    }

    @Override
    public void handleUndoButton() {
        if(moveCounter>0){      //If there has been at least one move.
            boardCurrent = new ChessBoardComplex(boardHistory.get(moveCounter-1)); //Set to previous Boardstate.
            boardHistory.remove(moveCounter);
            moveCounter--;
        }
    }

    @Override
    public void handleRedoButton() {
        //TODO implement this.
    }

    @Override
    public void handleShowNextMoveButton() {

    }

    protected void move(int position){
        boardCurrent.handleMoveTo(position);    //Move there from a selected position.
        boardCurrent.resetFrames();
        boardHistory.add(new ChessBoardComplex(boardCurrent));
        moveCounter++;
    }

    /**
     *
     * @return a simple Instance of a chessBoard. For example if you just need info where to draw the chessmen.
     */
    public ChessBoardSimple getSimpleBoard(){
        return boardCurrent;
    }

    public ChessBoardComplex getComplexBoard() { return boardCurrent; }

    public VictoryScreenGraphic.VictoryState getWinner(){
        return null;
    };

}
