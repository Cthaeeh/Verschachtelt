package com.example.kai.verschachtelt.pvAIGame.chess_AI;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Kai on 04.09.2016.
 * This class wraps the protracted ai calculations.
 */
public class AI_Task extends AsyncTask<byte[], Integer, Move> {

    private final AI_Listener listener;
    private byte[] bestMove;
    private static final String TAG = "AI_Task";
    private int SEARCH_DEPTH = 3; //5 is max on shitty phones

    public AI_Task(AI_Listener listener, int difficulty) {
        this.listener = listener;
        SEARCH_DEPTH = difficulty+2;
    }

    @Override
    protected void onPostExecute(Move result) {
        super.onPostExecute(result);
        listener.onMoveCalculated(result);
    }

    @Override
    protected Move doInBackground(final byte[]... board) {
        getBestMove(board[0]);
        return    new Move(board[0],bestMove);  //Return the best one.
    }

    /**
     * From all possible Moves search for the best one.
     * @param root
     */
    private void getBestMove(byte[] root) {
        GameTree gameTree = new GameTree(root,this);
        long startTime = System.currentTimeMillis();    //For performance measurement
        bestMove = gameTree.getLeastWorstOutcome(SEARCH_DEPTH);
        long endTime = System.currentTimeMillis();
        Log.d(TAG,"DEPTH: "+SEARCH_DEPTH + "  Time it took: " + (endTime-startTime));
    }

}