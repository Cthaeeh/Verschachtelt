package com.example.kai.verschachtelt.pvpGame;

import com.example.kai.verschachtelt.ChessGame;
import com.example.kai.verschachtelt.InputHandler;
import com.example.kai.verschachtelt.chessLogic.Chessman;

/**
 * Created by Kai on 11.08.2016.
 */
public class ChessGamePvP extends ChessGame {

    private Chessman.Color playerOnTurn = Chessman.Color.WHITE; //White always starts.

    public ChessGamePvP(InputHandler inputHandler) {
        super(inputHandler);
    }

    @Override
    protected void move(int position){
        if(board.getChessManAt(board.getSelectedPosition()).getColor()==playerOnTurn){
            board.handleMoveTo(position);
            board.resetFrames();
            if(playerOnTurn== Chessman.Color.BLACK)playerOnTurn= Chessman.Color.WHITE;
            else playerOnTurn= Chessman.Color.BLACK;
        }
    }

}
