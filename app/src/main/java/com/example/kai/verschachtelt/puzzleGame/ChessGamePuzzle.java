package com.example.kai.verschachtelt.puzzleGame;

import com.example.kai.verschachtelt.InputHandler;
import com.example.kai.verschachtelt.chessLogic.ChessBoardComplex;
import com.example.kai.verschachtelt.graphics.VictoryScreenGraphic;

/**
 * Created by Kai on 14.08.2016.
 */
public class ChessGamePuzzle extends com.example.kai.verschachtelt.ChessGame {
    public static Puzzle PUZZLE;
    private int puzzleSteps = 0;          //The number of moves the player made to solve the puzzle.
    private boolean puzzleSolved = false;

    public ChessGamePuzzle(InputHandler inputHandler) {
        super(inputHandler);
        boardCurrent = PUZZLE.getStartPosition();   //The position where you need a solution for.
    }

    /**
     * Special move method that checks if the move is correct (e.g. the same like in the Puzzle)
     * and only then really executes the move. Otherwise does nothing.
     * TODO inform the user that his move was BS
     * @param position
     */
    @Override
    protected void move(int position){
        ChessBoardComplex hypotheticalBoard = new ChessBoardComplex(boardCurrent);  //Make a copy to test if users move was correct.
        hypotheticalBoard.handleMoveTo(position);                                   //Make move on the copied board.
        if(hypotheticalBoard.comparePositions(PUZZLE.getPosition(puzzleSteps+1))){  //If the correct move was made.
            super.move(position);                                                   //Make the move on the real board.
            puzzleSteps++;
            puzzleSolved = isPuzzleSolved();
            if(!puzzleSolved){                                                      //If the puzzle isn´t already solved opponent make move.
                boardCurrent = PUZZLE.getPosition(puzzleSteps+1);
                puzzleSteps++;
            }
        }
    }

    private boolean isPuzzleSolved() {
        if (puzzleSteps<PUZZLE.getNeededSteps())return false;
        else return true;
    }

    @Override
    public void handleShowNextMoveButton() {
        if(!puzzleSolved){                                                      //If the puzzle isn´t already solved opponent make move.
            boardCurrent = PUZZLE.getPosition(puzzleSteps+1);
            puzzleSteps++;
        }
    }

    @Override
    public VictoryScreenGraphic.VictoryState getWinner() {
        if(!isPuzzleSolved())return null;    //IF Puzzle isn´t solved don´t show the Victory screen.
        else return VictoryScreenGraphic.VictoryState.VICTORY;  //Otherwise show.
    }
}
