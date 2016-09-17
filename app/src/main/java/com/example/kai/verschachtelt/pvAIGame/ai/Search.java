package com.example.kai.verschachtelt.pvAIGame.ai;

import android.util.Log;

import com.example.kai.verschachtelt.graphics.Background;

/**
 * Created by Kai on 07.09.2016.
 * An Instance of this class represents one branch of the game tree.
 * It can be grown (e.g adding plies) and it can be evaluated (the last plie will be evaluated)
 */
public class Search {

    private final byte[] root;  //Or first plie if you want so.
    private AI_Task task;
    private int nodesSearched = 0;
    private String TAG = "GAMETREE";

    public Search(byte[] root, AI_Task task, int extraInfo){
        this.root = root;
        this.task = task;
        MoveGenerator.setRoot(root,extraInfo);
    }

    /**
     */
    public int getLeastWorstOutcome(int depth){
        nodesSearched = 0;
        short α = -32760;
        short β = 32760;
        byte player = root[MoveGenerator.PLAYER_ON_TURN];
        int bestMove = 0;
        if (player == MoveGenerator.WHITE) {
            for (int move : MoveGenerator.generatePossibleMoves()) {
                MoveGenerator.makeMove(move);
                if (!MoveGenerator.wasLegalMove(move)) {
                    MoveGenerator.unMakeMove(move);
                    break;
                }
                Log.d(TAG,MoveAsInteger.toReadableString(move));
                short val = alphabeta(depth - 1, α, β);
                MoveGenerator.unMakeMove(move);
                if (α < val) {
                    α = val;
                    bestMove = move;
                }
                if (β <= α) break;                             // Beta cut-off *)
            }
        } else {
            for (int move : MoveGenerator.generatePossibleMoves()) {
                MoveGenerator.makeMove(move);
                if (!MoveGenerator.wasLegalMove(move)) {
                    MoveGenerator.unMakeMove(move);
                    break;
                }
                Log.d(TAG,MoveAsInteger.toReadableString(move));
                short val = alphabeta(depth - 1, α, β);
                MoveGenerator.unMakeMove(move);
                if (β > val) {
                    β = val;
                    bestMove = move;
                }
                if (β <= α) break;                             // Alpha cut-off *)
            }
        }
        return bestMove;
    }

    /**
     * See: https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning
     * @param depth the number of plies the alg needs to go deeper down the tree.
     * @param α
     * @param β
     * @return      //TODO comment this really well.
     */
    private short alphabeta(int depth,short α,short β){
        if(depth == 0){
            nodesSearched++;
            return BordEvaluation.evaluate(MoveGenerator.getBoard());
        }
        byte player = MoveGenerator.getBoard()[MoveGenerator.PLAYER_ON_TURN];
        if (player == MoveGenerator.WHITE){
            for(int move : MoveGenerator.generatePossibleMoves()){
                MoveGenerator.makeMove(move);
                if(depth>1&& !MoveGenerator.wasLegalMove(move)){
                    MoveGenerator.unMakeMove(move);
                    break;
                }
                α = max(α, alphabeta(depth-1, α, β));
                MoveGenerator.unMakeMove(move);
                if (β <= α){
                    break;                             // Beta cut-off *)
                }
            }
            return α;
        } else {
            for(int move : MoveGenerator.generatePossibleMoves()){
                MoveGenerator.makeMove(move);
                if(depth>1&& !MoveGenerator.wasLegalMove(move)){
                    MoveGenerator.unMakeMove(move);
                    break;
                }
                β = min(β, alphabeta(depth-1, α, β));
                MoveGenerator.unMakeMove(move);
                if (β <= α){
                    break;                             // Alpha cut-off *)
                }
            }
            return β;
        }
    }

    /**
     * Returns the greater value.
     * @param val1
     * @param val2
     * @return the greater one
     */
    private short max(short val1, short val2) {
        if(val1>val2)return val1;
        else return val2;
    }

    /**
     * Returns the smaller value.
     * @param val1
     * @param val2
     * @return the smaller one
     */
    private short min(short val1, short val2) {
        if(val1<val2)return val1;
        else return val2;
    }

    public int getNodesSearched(){
        return nodesSearched;
    }

}
