package com.example.kai.verschachtelt.puzzleGame;

import com.example.kai.verschachtelt.InputHandler;

/**
 * Created by Kai on 14.08.2016.
 */
public class ChessGamePuzzle extends com.example.kai.verschachtelt.ChessGame {
    public static Puzzle PUZZLE;

    public ChessGamePuzzle(InputHandler inputHandler) {
        super(inputHandler);
        boardCurrent = PUZZLE.getStartPosition();
    }
}
