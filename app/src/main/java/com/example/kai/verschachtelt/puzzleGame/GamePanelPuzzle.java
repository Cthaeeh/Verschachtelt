package com.example.kai.verschachtelt.puzzleGame;

import android.content.Context;
import android.graphics.Canvas;

import com.example.kai.verschachtelt.GamePanel;
import com.example.kai.verschachtelt.graphics.PuzzleInfoGraphic;

/**
 * Created by Kai on 14.08.2016.
 */
public class GamePanelPuzzle extends GamePanel {

    PuzzleInfoGraphic puzzleInfoGraphic;

    public GamePanelPuzzle(Context context) {
        super(context);
        game = new ChessGamePuzzle(inputHandler);

        puzzleInfoGraphic = new PuzzleInfoGraphic(((ChessGamePuzzle)(game)).getPuzzleDescription());
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        puzzleInfoGraphic.draw(canvas);
    }
    @Override
    public void update(double avgFPS){
        super.update(avgFPS);
        puzzleInfoGraphic.update(((ChessGamePuzzle)(game)).getPuzzleProgess());
    }
}
