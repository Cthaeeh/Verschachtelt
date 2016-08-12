package com.example.kai.verschachtelt.chessLogic;

/**
 * Created by Kai on 09.08.2016.
 * This class extends the normal Chessboard class and adds basic move functionality, possible move functionality.
 */
public class ChessBoardComplex extends ChessBoardSimple {
    private int selectedPosition = -1;                                  //position to move a chessman from. By default no real position.
    private static RuleBook ruleBook = new RuleBook();                  //There is just one Rulebook for every game of chess.
    private boolean[] possibleDestinations = new boolean[64];           //It is either possible to move there or not.

    public ChessBoardComplex(){
        super();
    }

    /**
     * A copy constructor. To ensure the object can be copied correctly.
     * @param board The object to make a copy of.
     */
    public ChessBoardComplex(ChessBoardComplex board) {
        chessmen = board.chessmen.clone();
    }

    /**
     * If the user selected a square this is saved in selectedPosition and possible Destinations are marked.
     * @param position  where the user touched.
     */
    public void handleSquareSelection(int position) {
        selectedPosition = position;
        if(position>=0&&position<64&& chessmen[position]!=null){        //If there is a chessman at this squareStates
            getPossibleDestinations();                                    //get the possible destinations
            markPossibleDestinations();                                   //and mark them.
        }
    }

    /**
     * If there is a selectedPosition we can directly move the chessman on that square to position.
     * @param position  The position to move to.
     */
    public void handleMoveTo(int position){
        if(selectedPosition >=0){                    //If we try to move from a legit position
            chessmen[position]= chessmen[selectedPosition];//Set the chessman to its new position.
            chessmen[selectedPosition]=null;           //Remove the chessman from its originally squareStates.
        }
        resetFrames();
    }

    /**
     * The Chessboard will rearrange the chessman.
     * @param from  where to move from.
     * @param to    where to move to.
     */
    public void handleMoveFromTo(int from, int to) {
        if(selectedPosition >=0 && chessmen[from]!=null){//If we try to move from a legit position
            chessmen[to]= chessmen[from];                    //Set the chessman to its new position.
            chessmen[from]=null;                         //Remove the chessman from its originally squareStates.
        }
        resetFrames();
    }

    /**
     * Depending on the selected chessman the method acceses the rulebook to see where it can move.
     */
    private void getPossibleDestinations() {
        possibleDestinations=ruleBook.getPossibleMoves(selectedPosition,chessmen);
    }

    /**Depending on the possible move destination the frames are colored accordingly.
     */
    private void markPossibleDestinations(){
        for(int i = 0;i<64;i++){
            if(possibleDestinations[i]&& chessmen[i]==null) squareStates[i]=SquareState.POSSIBLE;
            if(possibleDestinations[i]&& chessmen[i]!=null) squareStates[i]=SquareState.POSSIBLE_KILL;
        }
    }

    public int getSelectedPosition(){
        return selectedPosition;
    }

}
