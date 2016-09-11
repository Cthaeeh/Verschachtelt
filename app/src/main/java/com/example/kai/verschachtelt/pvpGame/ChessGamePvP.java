package com.example.kai.verschachtelt.pvpGame;

import com.example.kai.verschachtelt.ChessGame;
import com.example.kai.verschachtelt.InputHandler;
import com.example.kai.verschachtelt.chessLogic.Chessman;
import com.example.kai.verschachtelt.graphics.VictoryScreenGraphic;

/**
 * Created by Kai on 11.08.2016.
 */
public class ChessGamePvP extends ChessGame {

    public ChessGamePvP(InputHandler inputHandler) {
        super(inputHandler);
    }

    @Override
    public VictoryScreenGraphic.VictoryState getWinner(){
        if(boardCurrent.getWinner()==null)return null;
        if(boardCurrent.getWinner() == Chessman.Color.WHITE){
            return VictoryScreenGraphic.VictoryState.WHITEWIN;
        }else return VictoryScreenGraphic.VictoryState.BLACKWIN;
    }


}
