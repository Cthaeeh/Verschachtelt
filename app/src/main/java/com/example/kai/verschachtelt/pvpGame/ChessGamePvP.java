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
        if(boardCurrent.getChessManAt(boardCurrent.getSelectedPosition()).getColor()==playerOnTurn){
            super.move(position);
            //After a move the Player on turn must be switched.
            switchPlayerOnTurn();
        }
    }
    @Override
    public void handleUndoButton() {
        if(moveCounter>0) switchPlayerOnTurn(); //If we go back in history the Player on turn must be changed as well.
        super.handleUndoButton();

    }

    private void switchPlayerOnTurn() {
        if(playerOnTurn== Chessman.Color.BLACK)playerOnTurn= Chessman.Color.WHITE;
        else playerOnTurn= Chessman.Color.BLACK;
    }

}
