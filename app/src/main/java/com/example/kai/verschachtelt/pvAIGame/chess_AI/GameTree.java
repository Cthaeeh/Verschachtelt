package com.example.kai.verschachtelt.pvAIGame.chess_AI;

/**
 * Created by Kai on 07.09.2016.
 * An Instance of this class represents one branch of the game tree.
 * It can be grown (e.g adding plies) and it can be evaluated (the last plie will be evaluated)
 */
public class GameTree {

    private final byte[] root;  //Or first plie if you want so.
    private AI_Task task;
    private int nodesSearched = 0;

    public GameTree(byte[] root, AI_Task task){
        this.root = root;
        this.task = task;
    }

    /**
     */
    public byte[] getLeastWorstOutcome(int depth){
        nodesSearched =0;
        short α = -32760;
        short β = 32760;
        byte player = root[MoveGenerator.PLAYER_ON_TURN_EXTRA_FIELD];
        byte[] bestMove = null;
        if (player == MoveGenerator.WHITE){
            for(byte[] child : MoveGenerator.generatePossibleMoves(root)){
                short val = alphabeta(child, depth-1, α, β);
                if(α < val ){
                    α = val;
                    bestMove = child;
                }
                if (β <= α)break;                             // Beta cut-off *)
            }
        } else {
            for(byte[] child : MoveGenerator.generatePossibleMoves(root)){
                short val = alphabeta(child, depth-1, α, β);
                if(β > val){
                    β = val;
                    bestMove = child;
                }
                if (β <= α)break;                             // Alpha cut-off *)
            }
        }
        return bestMove;
    }

    /**
     * See: https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning
     * @param node
     * @param depth the number of plies the alg needs to go deeper down the tree.
     * @param α
     * @param β
     * @return      //TODO comment this really well.
     */
    private short alphabeta(byte[] node,int depth,short α,short β){
        if(depth == 0 || node[MoveGenerator.GAME_HAS_ENDED_EXTRA_FIELD]==MoveGenerator.TRUE){
            nodesSearched++;
            return BordEvaluation.evaluate(node);
        }
        byte player = node[MoveGenerator.PLAYER_ON_TURN_EXTRA_FIELD];
        if (player == MoveGenerator.WHITE){
            for(byte [] child : MoveGenerator.generatePossibleMoves(node)){
                α = max(α, alphabeta(child, depth-1, α, β));
                if (β <= α)break;                             // Beta cut-off *)
            }
            return α;
        } else {
            for(byte [] child : MoveGenerator.generatePossibleMoves(node)){
                β = min(β, alphabeta(child, depth-1, α, β));
                if (β <= α)break;                             // Alpha cut-off *)
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
