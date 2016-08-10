package com.example.kai.verschachtelt.chessLogic;

/**
 * Created by Kai on 09.08.2016.
 * This class extends the normal Chessboard class and adds basic move funtionality, possible move functionality.
 */
public class ChessBoardComplex extends ChessBoardSimple {
    private int selectedPosition = -1;                                  //position to move a chessman from. By default no real position.
    private RuleBook ruleBook = new RuleBook(board);
    private boolean[] possibleDestinations = new boolean[64];   //It is either possible to move there or not.

    /**
     * If the user selected a squareStates this is saved in selectedPosition and possible Destinations are marked.
     * @param position
     */
    public void handleSquareSelection(int position) {
        selectedPosition = position;
        if(position>=0&&position<64&&board[position]!=null){        //If there is a chessman at this squareStates
            getPossibleDestinations();                                    //get the possible destinations
            markPossibleDestinations();                                   //and mark them.
        }
    }

    public void handleMove(int position){
        if(selectedPosition >=0){                    //If we try to move from a legit position
            board[position]=board[selectedPosition];//Set the chessman to its new position.
            board[selectedPosition]=null;           //Remove the chessman from its originally squareStates.
        }
        resetFrames();
    }

    /**
     * Depending on the selected chessman the method acceses the rulebook to see where it can move.
     */
    private void getPossibleDestinations() {
        possibleDestinations=ruleBook.getPossibleMoves(selectedPosition);
    }

    /**Depending on the possible move destination the frames are colored accordingly.
     */
    private void markPossibleDestinations(){
        for(int i = 0;i<64;i++){
            if(possibleDestinations[i]&&board[i]==null) squareStates[i]=SquareState.POSSIBLE;
            if(possibleDestinations[i]&&board[i]!=null) squareStates[i]=SquareState.POSSIBLE_KILL;
        }
    }
}
