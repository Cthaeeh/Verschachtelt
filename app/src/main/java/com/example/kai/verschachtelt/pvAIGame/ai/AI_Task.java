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
    private int MAX_DEPTH = -3;     //quiesce search depth.
    private int extraInfo;          //Extra info like castling rights, en passant , half move clock.

    public AI_Task(AI_Listener listener, int difficulty, int extraInfo) {
        this.listener = listener;
        this.extraInfo = extraInfo;
        setDepths(difficulty);
    }

    /**
     * Sets the values of SEARCH_DEPTH and MAX_DEPTH,
     * the only variables were we can control the AI-Strength to
     * hand picked values.  //TODO move them to res/values/integers
     * @param difficulty
     */
    private void setDepths(int difficulty) {
        switch (difficulty){
            case 0:
                SEARCH_DEPTH = 1;       //Schäferzug is possible here
                MAX_DEPTH = 0;
                break;
            case 1:
                SEARCH_DEPTH = 2;
                MAX_DEPTH = -2;
                break;
            case 2:
                SEARCH_DEPTH = 3;
                MAX_DEPTH = -2;
                break;
            case 3:
                SEARCH_DEPTH = 3;
                MAX_DEPTH = -3;
                break;
            case 4:
                SEARCH_DEPTH = 4;
                MAX_DEPTH = -3;
                break;
            case 5:
                SEARCH_DEPTH = 5;
                MAX_DEPTH = -3;
                break;
            case 6:
                SEARCH_DEPTH = 6;
                MAX_DEPTH = -3;
                break;
        }
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
        search.setMAX_DEPTH(MAX_DEPTH);
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