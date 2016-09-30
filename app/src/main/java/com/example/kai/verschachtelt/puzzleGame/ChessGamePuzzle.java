package com.example.kai.verschachtelt.puzzleGame;

import android.os.Handler;
import android.widget.Toast;

import com.example.kai.verschachtelt.InputHandler;
import com.example.kai.verschachtelt.R;
import com.example.kai.verschachtelt.activitys.MainActivity;
import com.example.kai.verschachtelt.chessLogic.ChessBoardComplex;
import com.example.kai.verschachtelt.dataHandling.MyDBHandler;
import com.example.kai.verschachtelt.graphics.VictoryScreenGraphic;

/**
 * Created by Kai on 14.08.2016.
 * Class that handles a Puzzle Game.
 * When the player makes a move it looks if it was a correct move, if so displays it.
 * When the player solves a puzzle this is saved in the DB.
 */

public class ChessGamePuzzle extends com.example.kai.verschachtelt.ChessGame {

    private static Toast wrongMoveToast;    //To be able to cancel the previous Toast see: http://stackoverflow.com/questions/10070108/can-i-cancel-previous-toast-when-i-want-to-show-an-other-toast
    public static Puzzle PUZZLE;
    private int puzzleSteps = 0;            //The number of moves the player made to solve the puzzle.
    private boolean puzzleSolved = false;
    private final static int NEXT_MOVE_DELAY = MainActivity.getContext().getResources().getInteger(R.integer.puzzleNextMoveDelayMs);

    public ChessGamePuzzle(InputHandler inputHandler) {
        super(inputHandler);
        boardCurrent = PUZZLE.getStartPosition();   //The position where you need a solution for.
    }

    /**
     * Special move method that checks if the move is correct (e.g. the same like in the Puzzle)
     * and only then really executes the move. Otherwise does nothing.
     * @param position where to try to move to
     */
    @Override
    protected void moveByHuman(int position){
        if(isPuzzleSolved())return;
        ChessBoardComplex hypotheticalBoard = new ChessBoardComplex(boardCurrent);  //Make a copy to test if users move was correct.
        hypotheticalBoard.handleMoveTo(position);                                   //Make move on the copied board.
        if(hypotheticalBoard.comparePositions(PUZZLE.getPosition(puzzleSteps+1))){  //If the correct move was made.
            handleCorrectPuzzleMove(position);
        }else {
            showWrongMoveInfo();
        }
    }

    private void handleCorrectPuzzleMove(int position) {
        super.moveByHuman(position);                                            //Make the move on the real board.
        puzzleSteps++;
        puzzleSolved = isPuzzleSolved();
        if(!puzzleSolved){                                                      //If the puzzle isn´t already solved opponent make move.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    boardCurrent = PUZZLE.getPosition(puzzleSteps+1);
                    puzzleSteps++;
                }
            }, NEXT_MOVE_DELAY);

        }
        if(isPuzzleSolved())saveProgress();         //After the move see if the puzzle is solved.
    }

    /**
     * puts the current puzzles in the dataBase
     */
    private void saveProgress() {
        PUZZLE.updateSolved();
        new MyDBHandler(MainActivity.getContext()).updatePuzzle(PUZZLE);
    }

    private boolean isPuzzleSolved() {
        return (!(puzzleSteps<PUZZLE.getNeededSteps()));
    }

    @Override
    public void handleShowNextMoveButton() {
        if(!puzzleSolved){                                                      //If the puzzle isn´t already solved opponent make move.
            if(PUZZLE.getPosition(puzzleSteps+1)!=null){
                boardCurrent = PUZZLE.getPosition(puzzleSteps+1);
                puzzleSteps++;
            }
        }
    }

    @Override
    public VictoryScreenGraphic.VictoryState getWinner() {
        if(!isPuzzleSolved())return null;    //IF Puzzle isn´t solved don´t show the Victory screen.
        else return VictoryScreenGraphic.VictoryState.VICTORY;  //Otherwise show.

    }

    public String getPuzzleDescription() {
        return PUZZLE.getDescription();
    }

    /**
     * When the User did a wrong Move, he should be informed about that.
     * That is what we do here.
     */
    private void showWrongMoveInfo() {
        if(wrongMoveToast!=null)wrongMoveToast.cancel();
        wrongMoveToast = Toast.makeText(MainActivity.getContext(), MainActivity.getContext().getString(R.string.puzzle_wrong_move_toast), Toast.LENGTH_SHORT);
        wrongMoveToast.show();
    }


}
