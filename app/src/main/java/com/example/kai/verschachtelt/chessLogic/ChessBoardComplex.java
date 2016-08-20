package com.example.kai.verschachtelt.chessLogic;

import java.util.Arrays;

/**
 * Created by Kai on 09.08.2016.
 * This class extends the normal Chessboard class and adds basic move functionality, possible move functionality.
 */
public class ChessBoardComplex extends ChessBoardSimple {
    private int selectedPosition = -1;                                  //position to move a chessman from. By default no real position.
    private static RuleBook ruleBook = new RuleBook();                  //There is just one Rulebook for every game of chess.
    private boolean[] possibleDestinations = new boolean[64];           //It is either possible to move there or not.
    private Chessman.Color playerOnTurn;
    private Castling castling;


    public ChessBoardComplex(){
        super();
        playerOnTurn = Chessman.Color.WHITE;    //Always white when start
        castling = new Castling(chessmen);
    }

    /**
     * A copy constructor. To ensure the object can be copied correctly.
     * @param board The object to make a copy of.
     */
    public ChessBoardComplex(ChessBoardComplex board) {
        chessmen = Chessman.deepCopy(board.chessmen);
        selectedPosition = board.selectedPosition;
        playerOnTurn = board.playerOnTurn;
        castling = new Castling(board.castling);
    }

    /**
     * Method takes a String with Dorsyth Edwards Notation and converts it to ChessBoardComplex Object.
     * @param fenNotation See: https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
     */
    public ChessBoardComplex(String fenNotation) {
        super();    //create Normal chessBoard.
        chessmen = FENParser.getChessmen(fenNotation);    //Set the chessmen to the positions from the notation.
        playerOnTurn = FENParser.getColor(fenNotation);   //Get the color of the player that has to move.
        castling = FENParser.getCastlingState(fenNotation);
    }

    /**
     * If the user selected a square this is saved in selectedPosition and possible Destinations are marked.
     * @param position  where the user touched.
     */
    public void handleSquareSelection(int position) {
        selectedPosition = position;
        if(position>=0&&position<64&& chessmen[position]!=null){        //If there is a chessman at this squareStates
            if(chessmen[position].getColor().equals(playerOnTurn)){     //If the player on turn wants to move.
                getPossibleDestinations();                              //get the possible destinations
                markPossibleDestinations();                             //and mark them.
            }
        }
    }

    /**
     * If there is a selectedPosition we can directly move the chessman on that square to position.
     * @param position  The position to move to.
     */
    public void handleMoveTo(int position){
        if(selectedPosition >=0 ){                    //If we try to move from a legit position
            chessmen[selectedPosition].notifyMove();  //Tell the chessman that he was moved (Important for Castling)
            chessmen[position]= chessmen[selectedPosition];//Set the chessman to its new position.
            chessmen[selectedPosition]=null;           //Remove the chessman from its originally squareStates.
            //If a King did a jump:
            if(chessmen[position].getPiece()== Chessman.Piece.KING&&Math.abs(position-selectedPosition)==2){
                castling.handleCastling(chessmen,position);//The corresponding tower needs to move as well.
            }
            switchPlayerOnTurn();
        }
        resetFrames();
    }

    /**
     * The Chessboard will rearrange the chessman.
     * @param from  where to move from.
     * @param to    where to move to.
     */
    public void handleMoveFromTo(int from, int to) {
        if(from>=0 && chessmen[from]!=null){//If we try to move from a legit position
            chessmen[from].notifyMove();                 //Tell the chessman that he was moved (Important for Castling)
            chessmen[to]= chessmen[from];                //Set the chessman to its new position.
            chessmen[from]=null;                         //Remove the chessman from its originally squareStates.
            //If a King did a jump:
            if(chessmen[to].getPiece()== Chessman.Piece.KING&&Math.abs(from-to)==2){
                castling.handleCastling(chessmen,to);//The corresponding tower needs to move as well.
            }
            switchPlayerOnTurn();
        }
        resetFrames();
    }

    /**
     * Depending on the selected chessman the method acceses the rulebook to see where it can move.
     */
    private void getPossibleDestinations() {
        possibleDestinations=ruleBook.getPossibleMoves(selectedPosition,chessmen);
        boolean[] possibleCastlingDestinations = castling.getPossibleMoves(selectedPosition,chessmen);
        possibleDestinations= combine(possibleCastlingDestinations,possibleDestinations);
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

    public Chessman.Color getWinner() {
        //TODO can also return draw.
        return ruleBook.getWinner(chessmen);
    }

    /**
     * Method checks if the Boards are same concerning position (of the chessmen).
     * @param boardToCompare    The board to compare with the one this method is called on.
     * @return
     */
    public boolean comparePositions(ChessBoardComplex boardToCompare) {
        for(int i = 0;i<64;i++){
            if(chessmen[i]!=null && boardToCompare.chessmen[i]!=null){  //If both are not null
                if(!(chessmen[i].equals(boardToCompare.chessmen[i]))){
                    return false;
                }
            }
            //If one is null but the other not return false.
            if(chessmen[i]==null&&boardToCompare.chessmen[i]!=null)return false;
            if(chessmen[i]!=null&&boardToCompare.chessmen[i]==null)return false;
        }
        return true;
    }

    public Chessman.Color getPlayerOnTurn() {
        return playerOnTurn;
    }

    private void switchPlayerOnTurn() {
        if(playerOnTurn== Chessman.Color.BLACK)playerOnTurn= Chessman.Color.WHITE;
        else playerOnTurn= Chessman.Color.BLACK;
    }

    private boolean[] combine(boolean[] array1, boolean[] array2) {
        //Combine both possibleDestinations
        for(int i = 0;i<64;i++){
            array2[i] = (array2[i]||array1[i]);
        }
        return array2;
    }


    // check, if a pawn change is possible
    public boolean pawnChangePossible() {

        for(int j = 0; j < 8; j++) {
         //   if (chessmen[j].equals(new Chessman(Chessman.Piece.PAWN, Chessman.Color.WHITE)) == true) {
         //
         //       return true;
         //   }

            if(chessmen[j] == null) {
                j++;
            }

            switch (chessmen[j].getPiece()) {

                case PAWN:
                    if(chessmen[j].getColor() == Chessman.Color.WHITE) {
                        return true;
                    }

                case ROOK:
                case KNIGHT:
                case BISHOP:
                case QUEEN:
                case KING:

                    j++;
            }
        }




        for (int j = 56; j<64; j++) {
           // if(chessmen[j].equals(new Chessman(Chessman.Piece.PAWN, Chessman.Color.BLACK)) == true) {
            //return true;
            // }
            if(chessmen[j] == null) {
                j++;
            }

            switch (chessmen[j].getPiece()) {

                case PAWN:
                    if(chessmen[j].getColor() == Chessman.Color.BLACK) {
                        return true;
                    }

                case ROOK:
                case KNIGHT:
                case BISHOP:
                case QUEEN:
                case KING:

                    j++;
            }



        }
        return false;   //false, if there is no pawn in first/last row
    }

    // check and return the position, where a pawn is waiting to be changed
    public int pawnChangePosition(){
        for(int j = 0; j < 8; j++) {
           // if (chessmen[j].equals(new Chessman(Chessman.Piece.PAWN, Chessman.Color.WHITE)) == true) {

           //     return j;
           // }

            if(chessmen[j] == null) {
                j++;
            }

            switch (chessmen[j].getPiece()) {

                case PAWN:
                    if(chessmen[j].getColor() == Chessman.Color.WHITE) {
                        return j;
                    }

                case ROOK:
                case KNIGHT:
                case BISHOP:
                case QUEEN:
                case KING:

                    j++;
            }
        }
        for(int j = 56; j < 64; j++) {
           // if(chessmen[j].equals(new Chessman(Chessman.Piece.PAWN, Chessman.Color.BLACK)) == true) {

           //     return j;
           // }

            if(chessmen[j] == null) {
                j++;
            }

            switch (chessmen[j].getPiece()) {

                case PAWN:
                    if(chessmen[j].getColor() == Chessman.Color.BLACK) {
                        return j;
                    }

                case ROOK:
                case KNIGHT:
                case BISHOP:
                case QUEEN:
                case KING:

                    j++;
            }
        }

        return -1;   // -1, if there is no such position

    }


    public void switchPawn(int position, Chessman newMan) {

        chessmen[position] = newMan;

    }


}
