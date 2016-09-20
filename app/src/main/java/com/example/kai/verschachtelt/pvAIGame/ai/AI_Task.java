package com.example.kai.verschachtelt.pvAIGame.ai;

import android.os.AsyncTask;
import android.util.Log;

import com.example.kai.verschachtelt.graphics.Background;

/**
 * Created by Kai on 04.09.2016.
 * This class wraps the protracted ai calculations.
 */
public class AI_Task extends AsyncTask<byte[], Integer, Move> {

    private final AI_Listener listener;
    private int bestMove;
    private static final String TAG = "AI_Task";
    private int SEARCH_DEPTH = 3;   //Default value
    private int extraInfo;          //Extra info like castling rights, en passant , half move clock.

    public AI_Task(AI_Listener listener, int difficulty, int extraInfo) {
        this.listener = listener;
        this.extraInfo = extraInfo;
        SEARCH_DEPTH = difficulty+3;
    }

    @Override
    protected void onPostExecute(Move result) {
        super.onPostExecute(result);
        listener.onMoveCalculated(result);
    }

    @Override
    protected Move doInBackground(final byte[]... board) {
        getBestMove(board[0]);
        return new Move(bestMove);  //Return the best one.
    }

    /**
     * From all possible Moves search for the best one.
     * @param root
     */
    private void getBestMove(byte[] root) {
        Search search = new Search(root,this,extraInfo);
        long startTime = System.currentTimeMillis();            //For performance measurement
        bestMove = search.performSearch(SEARCH_DEPTH); //Calc best move.

        //Debugging / Analyse
        long endTime = System.currentTimeMillis();
        int nps = (int)(search.getLeafsSearched() / ((endTime-startTime)/1000.0 ));
        Log.d(TAG,"DEPTH: "+SEARCH_DEPTH + "  Time it took: " + (endTime-startTime) +" NPS: " + nps);
        Log.d(TAG, "Move" + MoveAsInt.toReadableString(bestMove));
        Background.ai_debug_info ="DEPTH: "+SEARCH_DEPTH + " Time " + (endTime-startTime)/1000.0 +"s NPS:" + nps + " Bran: " + Math.pow(search.getLeafsSearched(),(1.0/SEARCH_DEPTH)) + " qNodes: " +search.getNodesInQuiescence() + "leafs: " + search.getLeafsSearched();
    }
}