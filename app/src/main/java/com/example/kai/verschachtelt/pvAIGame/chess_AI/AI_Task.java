package com.example.kai.verschachtelt.pvAIGame.chess_AI;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Kai on 04.09.2016.
 */
public class AI_Task extends AsyncTask<byte[], Integer, Move> {

    private final AI_Listener listener;
    private volatile boolean running = true;


    public AI_Task(AI_Listener listener) {
        this.listener = listener;
    }


    @Override
    protected void onPostExecute(Move result) {
        super.onPostExecute(result);
        listener.onMoveCalculated(result);
    }

    @Override
    protected Move doInBackground(byte[]... board) {
        ArrayList<byte[]> possibleMoves = MoveGenerator.generatePossibleMoves(board[0]);    //Calc all possible Moves that the AI can make
        Random randomGenerator = new Random();
        return    new Move(board[0],possibleMoves.get(randomGenerator.nextInt(possibleMoves.size())));                                                     //Return the first one.
    }

}