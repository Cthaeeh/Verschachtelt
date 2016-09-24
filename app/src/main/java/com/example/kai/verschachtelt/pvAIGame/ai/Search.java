package com.example.kai.verschachtelt.pvAIGame.ai;
/**
 * Created by Kai on 07.09.2016.
 * The Core of the AI
 * Here we go through the game tree to look ahead in the future to see what move will turn out best.
 */
public class Search {

    private final byte[] root;      //Or first plie if you want so.
    private AI_Task task;       
    private int leafsSearched = 0;  //For Analyse
    private int nodesInQuiescence = 0;
    private int MAX_DEPTH = -3;     // For quiescence-search
    protected static int[][] killerMoves;   //See: https://chessprogramming.wikispaces.com/Killer+Move

    public Search(byte[] root, AI_Task task, int extraInfo){
        this.root = root;
        this.task = task;
        MoveGen.initialise(root,extraInfo);
    }

    /**
     * Does start the tree search and returns the "best" move
     * in the current position.
     */
    public int performSearch(int depth){
        killerMoves = new int[depth][2];
        MoveGen.setCurrentDepth(depth);
        leafsSearched = 0;
        nodesInQuiescence = 0;
        short α = -32760;       //Maximizer
        short β = 32760;        //Minimizer
        byte player = root[MoveGen.PLAYER_ON_TURN];
        int bestMove = 0;
        if (player == MoveGen.WHITE) {
            for (int move : MoveGen.generatePossibleMoves()) {      //Get all Moves
                if(task.isCancelled()) break;
                MoveGen.makeMove(move);
                if (!MoveGen.wasLegalMove(move)) {           //Illegal Move
                    MoveGen.unMakeMove(move);
                    continue;
                }
                short val = alphabeta(depth - 1, α, β);             //Go deeper
                MoveGen.unMakeMove(move);
                if (α < val || bestMove == 0) {
                    α = val;
                    bestMove = move;
                }
                if (β >= α ){                                        // Beta cut-off *)
                    if(MoveAsInt.getCapture(move)==0){              // Update Killer Moves if it was a quiet move.
                        killerMoves[depth-1][1] = killerMoves[depth-1][0];  //set secondary killer
                        killerMoves[depth-1][0] = move;                     //set primary killer.
                    }
                    break;
                }
            }
        } else {
            for (int move : MoveGen.generatePossibleMoves()) {
                if(task.isCancelled()) break;
                MoveGen.makeMove(move);
                if (!MoveGen.wasLegalMove(move)) {
                    MoveGen.unMakeMove(move);
                    continue;
                }
                short val = alphabeta(depth - 1, α, β);
                MoveGen.unMakeMove(move);
                if (β > val || bestMove == 0) {
                    β = val;
                    bestMove = move;
                }
                if (β <= α){                                        // Alpha cut-off *)
                    if(MoveAsInt.getCapture(move)==0){              // Update Killer Moves if it was a quiet move.
                        killerMoves[depth-1][1] = killerMoves[depth-1][0];
                        killerMoves[depth-1][0] = move;
                    }
                    break;
                }
            }
        }
        return bestMove;
    }

    /**
     * See: https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning
     * @param depth the number of plies the alg needs to go deeper down the tree.
     * @param α current maximizer value
     * @param β current minimizer value
     * @return      //TODO comment this really well.
     */
    private short alphabeta(int depth,short α,short β){
        if(depth == 0 || MoveGen.getBoard()[MoveGen.GAME_HAS_ENDED] == MoveGen.TRUE){   //If depth = 0 -> found a Leaf
            leafsSearched++;
            return quiescence(depth-1,α, β);    //Try to find a stable position to evaluate.
        }
        MoveGen.setCurrentDepth(depth);
        byte player = MoveGen.getBoard()[MoveGen.PLAYER_ON_TURN];
        if (player == MoveGen.WHITE){
            for(int move : MoveGen.generatePossibleMoves()){    //Go through available moves.
                MoveGen.makeMove(move);
                if(depth>1 && !MoveGen.wasLegalMove(move)){
                    MoveGen.unMakeMove(move);
                    continue;
                }
                short val = alphabeta(depth - 1, α, β);
                MoveGen.unMakeMove(move);
                if(α < val){
                    α = val;
                }
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
            for(int move : MoveGen.generatePossibleMoves()){    //Go through available moves.
                MoveGen.makeMove(move);
                if(depth>1 && !MoveGen.wasLegalMove(move)){
                    MoveGen.unMakeMove(move);
                    continue;
                }
                short val = alphabeta(depth - 1, α, β);
                MoveGen.unMakeMove(move);
                if(val < β){
                    β = val;
                }
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
     * See: http://chessprogramming.wikispaces.com/Quiescence+Search
     * @param depth we don´t want to go to deep, if we hit MAX_DEPTH stop
     * @param α current maximizer value
     * @param β current minimizer value
     * @return
     */
    private short quiescence(int depth,short α,short β){
        short score = BordEvaluation.evaluate(MoveGen.getBoard());
        nodesInQuiescence++;
        if(depth < MAX_DEPTH || MoveGen.getBoard()[MoveGen.GAME_HAS_ENDED] == MoveGen.TRUE){
            return score;
        }
        byte player = MoveGen.getBoard()[MoveGen.PLAYER_ON_TURN];
        if (player == MoveGen.WHITE){
            if(score >= β) return β;    //Found a stable position
            if(score > α) α = score;
            int[] moves = MoveGen.generateCaptureMoves();
            if(moves.length==0) return α;
            for(int move : MoveGen.generateCaptureMoves()){
                MoveGen.makeMove(move);
                /*
                if(!MoveGen.wasLegalCapture(move)){        //TODO Check if this is too slow
                    MoveGen.unMakeMove(move);
                    continue;
                }
                */
                short val = quiescence(depth - 1, α, β);
                MoveGen.unMakeMove(move);
                if(val > α){
                    α = val;
                    if (α >= β){                                // Beta cut-off *)
                        break;
                    }
                }
            }
            return α;
        } else {
            if(score <= α) return α;
            if(score < β) β = score;
            int[] moves = MoveGen.generateCaptureMoves();
            if(moves.length==0)return β;
            for(int move : moves){
                MoveGen.makeMove(move);
                /*
                if(!MoveGen.wasLegalCapture(move)){         //TODO Check if this is too slow
                    MoveGen.unMakeMove(move);
                    continue;
                }
                */
                short val = quiescence(depth - 1, α, β);
                MoveGen.unMakeMove(move);
                if(val < β){
                    β = val;
                    if (β <= α){                                  // Alpha cut-off *)
                        break;
                    }
                }
            }
            return β;
        }
    }

    //TODO delete when shipped
    public int getLeafsSearched(){
        return leafsSearched;
    }

    public int getNodesInQuiescence(){
        return nodesInQuiescence;
    }
}
