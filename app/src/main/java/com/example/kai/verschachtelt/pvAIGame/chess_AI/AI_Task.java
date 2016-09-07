package com.example.kai.verschachtelt.pvAIGame.chess_AI;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by Kai on 04.09.2016.
 * This class wraps the protracted ai calculations.
 */
public class AI_Task extends AsyncTask<byte[], Integer, Move> {

    private final AI_Listener listener;
    private byte[] bestMove;
    private ArrayList<TreeBranch> gameTree = new ArrayList<TreeBranch>();
    private static final String TAG = "AI_Task";
    private byte AiColor = MoveGenerator.BLACK;
    private final int SEARCH_DEPTH = 4;

    public AI_Task(AI_Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Move result) {
        super.onPostExecute(result);
        listener.onMoveCalculated(result);
    }

    @Override
    protected Move doInBackground(final byte[]... board) {
        setUpGameTree(board[0]);                //Setup the gametree to see what move is best
        if (isCancelled()) return null;
        getBestMove();
        return    new Move(board[0],bestMove);  //Return the best one.
    }

    /**
     * From all possible Moves search for the best one.
     */
    private void getBestMove() {
        double leastWorstMove = gameTree.get(0).getWorstOutcome(AiColor, SEARCH_DEPTH);
        bestMove = gameTree.get(0).getRoot();

        for(int i = 1 ; i < gameTree.size();i++){
            double worstOutcome = gameTree.get(i).getWorstOutcome(AiColor, SEARCH_DEPTH);
            if(AiColor == MoveGenerator.WHITE && worstOutcome>leastWorstMove){
                leastWorstMove = worstOutcome;
                bestMove = gameTree.get(i).getRoot();
            }
            if(AiColor == MoveGenerator.BLACK && worstOutcome<leastWorstMove){
                leastWorstMove = worstOutcome;
                bestMove = gameTree.get(i).getRoot();
            }
        }
    }

    /**
     * Setup the roots of the gameTree by getting all moves the ai can make originating from the
     * starting position.
     * @param startingPosition
     */
    private void setUpGameTree(byte[] startingPosition){
        ArrayList<byte[]> possibleStartingMoves = MoveGenerator.generatePossibleMoves(startingPosition);    //Calc all possible Moves that the AI can make.
        AiColor = startingPosition[MoveGenerator.PLAYER_ON_TURN_EXTRA_FIELD];
        for(int i = 0 ; i < possibleStartingMoves.size() ; i++ ){
            gameTree.add(new TreeBranch(possibleStartingMoves.get(i),this));
            if (isCancelled()) break;
        }
    }

}