package com.example.kai.verschachtelt.chessLogic;

/**
 * Created by Kai on 09.08.2016.
 * This class extends the normal Chessboard class and adds basic move funtionality, possible move functionality.
 */
public class ChessBoardComplex extends ChessBoardSimple {
    private int moveFrom = -1;                                  //position to move a chessman from. By default no real position.
    private RuleBook ruleBook = new RuleBook();
    private boolean[] possibleDestinations = new boolean[64];   //It is either possible to move there or not.

    /**
     * If the user selected a square this is saved in moveFrom and possible Destinations are marked.
     * @param position
     */
    public void handleSquareSelection(int position) {
        moveFrom = position;
        if(position>=0&&position<64&&board[position]!=null){        //If there is a chessman at this square
            getPossibleDestinations();                                    //get the possible destinations
            markPossibleDestinations();                                   //and mark them.
        }
    }

    public void handleMove(int position){
        if(moveFrom>=0){                    //If we try to move from a legit position
            board[position]=board[moveFrom];//Set the chessman to its new position.
            board[moveFrom]=null;           //Remove the chessman from its originally square.
        }
        resetFrames();
    }

    /**
     * Depending on the selected chessman the method acceses the rulebook to see where it can move.
     */
    private void getPossibleDestinations() {
        switch (board[moveFrom]){
            case BLACK_ROOK:
            case WHITE_ROOK:
                possibleDestinations = ruleBook.getPossibleRookMoves(moveFrom);
                break;
            case BLACK_KNIGHT:
            case WHITE_KNIGHT:
                possibleDestinations = ruleBook.getPossibleKnightMoves(moveFrom);
                break;
            case BLACK_BISHOP:
            case WHITE_BISHOP:
                possibleDestinations = ruleBook.getPossibleBishopMoves(moveFrom);
                break;
            case BLACK_QUEEN:
            case WHITE_QUEEN:
                possibleDestinations = ruleBook.getPossibleQueenMoves(moveFrom);
                break;
            case BLACK_KING:
            case WHITE_KING:
                possibleDestinations = ruleBook.getPossibleKingMoves(moveFrom);
                break;
            case BLACK_PAWN:
                possibleDestinations = ruleBook.getPossibleLowerPawnMoves(moveFrom);
                break;
            case WHITE_PAWN:
                possibleDestinations = ruleBook.getPossibleUpperPawnMoves(moveFrom);
                break;
        }
    }

    /**Depending on the possible move destination the frames are colored accordingly.
     */
    private void markPossibleDestinations(){
        for(int i = 0;i<64;i++){
            if(possibleDestinations[i]&&board[i]==null)boardRepresentingStates[i]=SquareState.POSSIBLE;
            if(possibleDestinations[i]&&board[i]!=null)boardRepresentingStates[i]=SquareState.POSSIBLE_KILL;
        }
    }
}
