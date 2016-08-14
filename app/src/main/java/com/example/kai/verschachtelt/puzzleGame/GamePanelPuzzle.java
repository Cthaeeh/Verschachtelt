package com.example.kai.verschachtelt.puzzleGame;

import android.content.Context;

import com.example.kai.verschachtelt.GamePanel;

/**
 * Created by Kai on 14.08.2016.
 */
public class GamePanelPuzzle extends GamePanel {
    public GamePanelPuzzle(Context context) {
        super(context);
        game = new ChessGamePuzzle(inputHandler);

    }
}
