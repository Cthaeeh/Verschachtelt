package com.example.kai.verschachtelt.pvAIGame.chess_AI;

import com.example.kai.verschachtelt.ChessGame;
import com.example.kai.verschachtelt.InputHandler;
import com.example.kai.verschachtelt.chessLogic.ChessBoardComplex;
import com.example.kai.verschachtelt.chessLogic.Chessman;

/**
 * Created by Kai on 11.08.2016.
 * This class contains AI specific things: -The AI moves after the player
 */
public class ChessGamePvAI extends ChessGame {

    private AI ai;

    public ChessGamePvAI(InputHandler inputHandler) {
        super(inputHandler);
        ai = new AI(1,Chessman.Color.BLACK);
    }

    @Override
    protected void moveByHuman(int position){
        if(boardCurrent.getChessManAt(boardCurrent.getSelectedPosition()).getColor()!=ai.getColor()){ //Can only move the humans chessman
            super.moveByHuman(position);
            ai.calculateMove(this);
        }
    }

    public void moveByAi(Move move) {
        boardCurrent.handleMove(move);    //Move there from a selected position.
        boardCurrent.resetFrames();
        boardHistory.add(new ChessBoardComplex(boardCurrent));
        moveCounter++;
    }

    /**
     * Overrides the standard UndoButton, because in a Game vs the AI you want to undo your own Move and the ai´s move.
     */
    @Override
    public void handleUndoButton() {
        if(moveCounter>0){      //If there has been at least one move.
            boardCurrent = new ChessBoardComplex(boardHistory.get(moveCounter-2)); //Set to previous Boardstate.
            boardHistory.remove(moveCounter);
            boardHistory.remove(moveCounter-1);
            moveCounter=moveCounter-2;
        }
    }
}
