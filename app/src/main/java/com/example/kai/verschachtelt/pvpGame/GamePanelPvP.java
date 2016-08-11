package com.example.kai.verschachtelt.pvpGame;

import android.content.Context;

import com.example.kai.verschachtelt.GamePanel;

/**
 * Created by Kai on 11.08.2016.
 */
public class GamePanelPvP extends GamePanel {

    public GamePanelPvP(Context context) {
        super(context);
        game = new ChessGamePvP(inputHandler);

    }
}
