package com.example.kai.verschachtelt.pvAIGame.chess_AI;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Kai on 07.09.2016.
 * An Instance of this class represents one branch of the game tree.
 * It can be grown (e.g adding plies) and it can be evaluated (the last plie will be evaluated)
 */
public class TreeBranch {

    private final byte[] root;  //Or first plie if you want so.
    private ArrayList<ArrayList<byte[]>> plies = new ArrayList<>();
    private AI_Task task;
    private static final String TAG = "TreeBranch";

    public TreeBranch(byte[] root, AI_Task task){
        this.root = root;
        this.task = task;
        ArrayList<byte[]> rootAsList = new ArrayList<>();
        rootAsList.add(root);
        plies.add(rootAsList);
    }

    /**
     * Returns the worst Outcome in this TreeBranch. Always looks into the highest plie.
     * if ist negative its better for black. If its positive its better for white.
     * @param aiColor   The color of the ai.
     * @return          The rating for this branch. (POSITIVE = BETTER)
     */
    public double getWorstOutcome(byte aiColor, int depth){
        calcPlies(depth);
        ArrayList<byte[]> lastPlie = plies.get(plies.size()-1); // Get the highest Plie
        double worstOutCome = 0.0;
        for(int i = 0; i < lastPlie.size(); i++){               //Go through boards.
            double boardValue = BordEvaluation.evaluate(lastPlie.get(i));
            if(aiColor == MoveGenerator.WHITE && boardValue<worstOutCome)worstOutCome = boardValue;
            if(aiColor == MoveGenerator.BLACK && boardValue>worstOutCome)worstOutCome = boardValue;
        }
        cleanUp();
        return worstOutCome;
    }

    /**
     * Deepens the tree
     * @param depth
     */
    public void calcPlies(int depth) {
        if(depth<=1 || depth>=10)throw new IllegalArgumentException("Number of plies make no sense!");
        for(int i = 1; i < depth; i++){
            plies.add(getPlie(plies.get(plies.size()-1)));
            if(task.isCancelled())break;
        }
    }

    /**
     * Calcs the possible boards that can follow to the ones in boards.
     * @param startingBoards
     * @return a list of boards that can be reached by one move from startingBoards.
     */
    private ArrayList<byte[]> getPlie(ArrayList<byte[]> startingBoards){
        ArrayList<byte[]> plie = new ArrayList<>();
        for(int i = 0; i<startingBoards.size();i++){
            plie.addAll(MoveGenerator.generatePossibleMoves(startingBoards.get(i)));
            if(task.isCancelled())break;
        }
        return plie;
    }

    public byte[] getRoot() {
        return root;
    }

    /**
     * Clears most of the memory the branch was using.
     */
    public void cleanUp() {
        plies.clear();
    }
}
