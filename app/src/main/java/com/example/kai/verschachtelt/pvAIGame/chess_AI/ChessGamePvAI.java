package com.example.kai.verschachtelt.pvAIGame.chess_AI;

import com.example.kai.verschachtelt.ChessGame;
import com.example.kai.verschachtelt.InputHandler;
import com.example.kai.verschachtelt.chessLogic.Chessman;
import com.example.kai.verschachtelt.graphics.VictoryScreenGraphic;

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
    protected void move(int position){
        if(boardCurrent.getChessManAt(boardCurrent.getSelectedPosition()).getColor()!=ai.getColor()){ //Can only move the humans chessman
            super.move(position);
            boardCurrent = ai.calculateMove(boardCurrent);
        }
    }

    @Override
    public VictoryScreenGraphic.VictoryState getWinner() {
        if(boardCurrent.getWinner() != ai.getColor())return VictoryScreenGraphic.VictoryState.VICTORY;
        if(boardCurrent.getWinner() == ai.getColor())return VictoryScreenGraphic.VictoryState.DEFEAT;
        return null;
    }
}
