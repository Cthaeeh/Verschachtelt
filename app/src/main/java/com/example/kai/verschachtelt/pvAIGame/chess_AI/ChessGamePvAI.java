package com.example.kai.verschachtelt.pvAIGame.chess_AI;

import com.example.kai.verschachtelt.ChessGame;
import com.example.kai.verschachtelt.InputHandler;
import com.example.kai.verschachtelt.chessLogic.ChessBoardComplex;
import com.example.kai.verschachtelt.chessLogic.Chessman;
import com.example.kai.verschachtelt.graphics.VictoryScreenGraphic;

/**
 * Created by Kai on 11.08.2016.
 * This class contains AI specific things: -The AI moves after the player
 */
public class ChessGamePvAI extends ChessGame {

    private AI ai;

    public ChessGamePvAI(InputHandler inputHandler, int difficulty) {
        super(inputHandler);
        ai = new AI(difficulty,Chessman.Color.BLACK);
    }

    @Override
    public void handleTouchOnSquare(Integer position) {
        if(boardCurrent.getPlayerOnTurn() != ai.getColor()){
            super.handleTouchOnSquare(position);
        }
    }

    @Override
    protected void moveByHuman(int position){
        if(boardCurrent.getChessManAt(boardCurrent.getSelectedPosition()).getColor()!=ai.getColor()){ //Can only move the humans chessman
            super.moveByHuman(position);
            if(boardCurrent.getWinner()==null)ai.calculateMove(this);   //Dont start ai calculations if there was already a move.
        }
    }

    public void moveByAi(Move move) {
        boardCurrent.handleMove(move);    //Move there from a selected position.
        boardCurrent.resetFrames();
        boardHistory.add(new ChessBoardComplex(boardCurrent));
        moveCounter++;
    }

    /**
     * Depending on the color of the ai and the color of the winning
     * @return  if human won VICTORY, if ai won DEFEAT
     */
    @Override
    public VictoryScreenGraphic.VictoryState getWinner(){
        if(boardCurrent.getWinner()==null)return null;
        if(boardCurrent.getWinner() == ai.getColor() ){
             return VictoryScreenGraphic.VictoryState.DEFEAT;
        }else return VictoryScreenGraphic.VictoryState.VICTORY;
    }

    /**
     * Overrides the standard UndoButton, because in a Game vs the AI you want to undo your own Move and the ai´s move.
     * If the AI is in the middle of calculating process we stop calculations and go back just one move -> humans turn again.
     */
    @Override
    public void handleUndoButton() {
        if(moveCounter>1 && boardCurrent.getPlayerOnTurn()!= ai.getColor()){      //If there has been at least one move.
            boardCurrent = new ChessBoardComplex(boardHistory.get(moveCounter-2)); //Set to previous Boardstate.
            boardHistory.remove(moveCounter);
            boardHistory.remove(moveCounter-1);
            moveCounter=moveCounter-2;
        }
        if(moveCounter>0 && boardCurrent.getPlayerOnTurn()== ai.getColor()){      //If there has been at least one move.
            ai.cancel();
            boardCurrent = new ChessBoardComplex(boardHistory.get(moveCounter-1)); //Set to previous Boardstate.
            boardHistory.remove(moveCounter);
            moveCounter=moveCounter-1;
        }
    }

}
