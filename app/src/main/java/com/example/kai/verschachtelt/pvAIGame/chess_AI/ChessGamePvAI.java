package com.example.kai.verschachtelt.pvAIGame.chess_AI;

import com.example.kai.verschachtelt.ChessGame;
import com.example.kai.verschachtelt.InputHandler;
import com.example.kai.verschachtelt.chessLogic.Chessman;

/**
 * Created by Kai on 11.08.2016.
 * This class contains AI specific things: -The AI moves after the player
 */
public class ChessGamePvAI extends ChessGame {

    private AI ai;


    public ChessGamePvAI(InputHandler inputHandler) {
        super(inputHandler);
        ai = new AI(1,Chessman.Color.WHITE);

    }

    @Override
    protected void move(int position){
        if(board.getChessManAt(board.getSelectedPosition()).getColor()!=ai.getColor()){ //Can only move the humans chessman
            board.handleMoveTo(position);
            board.resetFrames();
            board = ai.calculateMove(board);

        }
    }
}
