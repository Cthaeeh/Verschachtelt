package com.example.kai.verschachtelt.pvAIGame.ai;

import android.util.Log;

import com.example.kai.verschachtelt.graphics.Background;

/**
 * Created by Kai on 07.09.2016.
 * The Core of the AI
 * 
 */
public class Search {

    private final byte[] root;  //Or first plie if you want so.
    private AI_Task task;       
    private int nodesSearched = 0;
    private int[] bestLine = new int[10];
    private String TAG = "GAMETREE";
    protected static int[][] killerMoves;
    
    public Search(byte[] root, AI_Task task, int extraInfo){
        this.root = root;
        this.task = task;
        MoveGen.initialise(root,extraInfo);
    }

    /**
     */
    public int performSearch(int depth){
        killerMoves = new int[depth][2];
        bestLine = new int[depth+1];
        MoveGen.setCurrentDepth(depth);
        nodesSearched = 0;
        short α = -32760;       //Maximizer
        short β = 32760;        //Minimizer
        byte player = root[MoveGen.PLAYER_ON_TURN];
        int bestMove = 0;
        if (player == MoveGen.WHITE) {
            for (int move : MoveGen.generatePossibleMoves()) {      //Get all Moves
                MoveGen.makeMove(move);
                if (!MoveGen.wasLegalMove(move, depth)) {           //Illegal Move
                    Background.ai_debug_info2 = MoveAsInt.toReadableString(move) +  "was illegal";
                    MoveGen.unMakeMove(move);
                    continue;
                }
                short val = alphabeta(depth - 1, α, β);             //Go deeper
                MoveGen.unMakeMove(move);
                if (α < val || bestMove == 0) {
                    α = val;
                    bestMove = move;
                    bestLine[depth] = move;
                }
                if (β <= α){                                        // Beta cut-off *)
                    if(MoveAsInt.getCapture(move)==0){              // Safe the move, could be useful later
                        killerMoves[depth-1][1] = killerMoves[depth-1][0];
                        killerMoves[depth-1][0] = move;
                    }
                    break;
                }
            }
        } else {
            for (int move : MoveGen.generatePossibleMoves()) {
                MoveGen.makeMove(move);
                if (!MoveGen.wasLegalMove(move, depth)) {
                    Background.ai_debug_info2 = MoveAsInt.toReadableString(move) +  "was illegal";
                    MoveGen.unMakeMove(move);
                    continue;
                }
                short val = alphabeta(depth - 1, α, β);
                MoveGen.unMakeMove(move);
                if (β > val || bestMove == 0) {
                    β = val;
                    bestMove = move;
                    bestLine[depth] = move;
                }
                if (β <= α){                                        // Alpha cut-off *)
                    if(MoveAsInt.getCapture(move)==0){
                        killerMoves[depth-1][1] = killerMoves[depth-1][0];
                        killerMoves[depth-1][0] = move;
                    }
                    break;
                }
            }
        }
        Log.d(TAG,"Best Line:" + MoveAsInt.toReadableString(bestLine));
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
            return BordEvaluation.evaluate(MoveGen.getBoard()); //TODO quicksce
        }
        MoveGen.setCurrentDepth(depth);
        byte player = MoveGen.getBoard()[MoveGen.PLAYER_ON_TURN];
        if (player == MoveGen.WHITE){
            for(int move : MoveGen.generatePossibleMoves()){
                MoveGen.makeMove(move);
                if(depth>1&& !MoveGen.wasLegalMove(move,depth)){
                    MoveGen.unMakeMove(move);
                    continue;
                }
                short val = alphabeta(depth - 1, α, β);
                if(val > α) bestLine[depth] = move;
                α = max(α, val);
                MoveGen.unMakeMove(move);
                if (β <= α){                                // Beta cut-off *)
                    if(MoveAsInt.getCapture(move)==0){
                        killerMoves[depth-1][1] = killerMoves[depth-1][0];
                        killerMoves[depth-1][0] = move;
                    }
                    break;
                }
            }
            return α;
        } else {
            for(int move : MoveGen.generatePossibleMoves()){
                MoveGen.makeMove(move);
                if(depth>1&& !MoveGen.wasLegalMove(move, depth)){
                    MoveGen.unMakeMove(move);
                    continue;
                }
                short val = alphabeta(depth - 1, α, β);
                if (val < β) bestLine[depth] = move;
                β = min(β, val);
                MoveGen.unMakeMove(move);
                if (β <= α){                                  // Alpha cut-off *)
                    if(MoveAsInt.getCapture(move)==0){
                        killerMoves[depth-1][1] = killerMoves[depth-1][0];
                        killerMoves[depth-1][0] = move;
                    }
                    break;
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
