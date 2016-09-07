package com.example.kai.verschachtelt.pvAIGame.chess_AI;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kai on 07.09.2016.
 * An Instance of this class represents one branch of the game tree.
 * It can be grown (e.g adding plies) and it can be evaluated (the last plie will be evaluated)
 */
public class GameTree {

    private final byte[] root;  //Or first plie if you want so.
    private AI_Task task;
    private static final String TAG = "GameTree";

    public GameTree(byte[] root, AI_Task task){
        this.root = root;
        this.task = task;
    }

    /**
     */
    public byte[] getLeastWorstOutcome(int depth){
        double α = -500;
        double β = 500;
        byte player = root[MoveGenerator.PLAYER_ON_TURN_EXTRA_FIELD];
        byte[] bestMove = null;
        if (player == MoveGenerator.WHITE){
            for(byte[] child : MoveGenerator.generatePossibleMoves(root)){
                double val = alphabeta(child, depth-1, α, β);
                if(α < val ){
                    α = val;
                    bestMove = child;
                }
                if (β <= α)break;                             // Beta cut-off *)
            }
        } else {
            for(byte[] child : MoveGenerator.generatePossibleMoves(root)){
                double val = alphabeta(child, depth-1, α, β);
                if(β > val){
                    β = val;
                    bestMove = child;
                    Log.d(TAG,"Found bette" + Arrays.toString(bestMove));
                }
                if (β <= α)break;                             // Alpha cut-off *)
            }
        }

        return bestMove;
    }

    /**
     * See: https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning
     * @param node
     * @param depth
     * @param α
     * @param β
     * @return
     */
    private double alphabeta(byte[] node,int depth,double α,double β){
        if(depth == 0){ //TODO add possibility of leaf
            return BordEvaluation.evaluate(node);
        }
        byte player = node[MoveGenerator.PLAYER_ON_TURN_EXTRA_FIELD];
        if (player == MoveGenerator.WHITE){
            for(byte [] child : MoveGenerator.generatePossibleMoves(node)){
                α = Math.max(α, alphabeta(child, depth-1, α, β));
                if (β <= α)break;                             // Beta cut-off *)
            }
            return α;
        } else {
            for(byte [] child : MoveGenerator.generatePossibleMoves(node)){
                β = Math.min(β, alphabeta(child, depth-1, α, β));
                if (β <= α)break;                             // Alpha cut-off *)
            }
            return β;
        }
    }

}
