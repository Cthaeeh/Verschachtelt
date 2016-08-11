package com.example.kai.verschachtelt.pvAIGame.chess_AI;

import android.content.Context;

import com.example.kai.verschachtelt.GamePanel;

/**
 * Created by Kai on 11.08.2016.
 */
public class GamePanelPvAI extends GamePanel{
    public GamePanelPvAI(Context context) {
        super(context);
        game = new ChessGamePvAI(inputHandler);
    }
}
