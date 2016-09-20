package com.example.kai.verschachtelt.chessLogic;

/**
 * Created by Kai on 16.08.2016.
 * This class is for handling all castling moves.
 * You can ask it if a selected chessman can castle.
 * And if the user decides to castle this class can move the King accordingly.
 * //TODO Bug test with FEN parser
 * //TODO Rewrite please, its a mess
 */
public class CastlingManager {
    //The castling state, e.g which player can still castle where. Only used for FENParsing.
    private boolean queenSideWhiteFEN = true;
    private boolean queenSideBlackFEN = true;
    private boolean kingSideWhiteFEN = true;
    private boolean kingSideBlackFEN = true;

    //Final positions where the king can castle.
    private final int KING_SIDE_BLACK_MOVE = 6;
    private final int QUEEN_SIDE_BLACK_MOVE = 2;
    private final int KING_SIDE_WHITE_MOVE = 62;
    private final int QUEEN_SIDE_WHITE_MOVE= 58;

    private static RuleBook ruleBook = new RuleBook();                  //There is just one Rulebook for every game of chess.
    boolean[] possibleMoves = new boolean[64];
    private Chessman[] chessmen;

    /**
     * Standart constructor.
     * @param chessmen
     */
    public CastlingManager(Chessman[] chessmen){
        this.chessmen = chessmen;
    }

    /**
     * Copy Constructor
     * @param toCopy the castling object you want to copy.
     */
    public CastlingManager(CastlingManager toCopy){
        chessmen = Chessman.deepCopy(toCopy.chessmen);
        kingSideBlackFEN = toCopy.kingSideBlackFEN;
        queenSideBlackFEN = toCopy.queenSideBlackFEN;
        kingSideWhiteFEN = toCopy.kingSideWhiteFEN;
        queenSideBlackFEN = toCopy.queenSideBlackFEN;
    }

    /**
     * If the king castled, the tower needs to jump over him.
     * So call this method if the king castled and you want to move
     * the tower accordingly.
     * @param chessmen
     * @param position where the king moved.
     */
    public void handleCastling(Chessman[] chessmen, int position) {
        this.chessmen = chessmen;
        switch (position){
            case KING_SIDE_BLACK_MOVE:
                lightMoveFromTo(7,5); //Move the tower over the king.
                break;
            case QUEEN_SIDE_BLACK_MOVE:
                lightMoveFromTo(0,3); //Move the tower over the king.
                break;
            case KING_SIDE_WHITE_MOVE:
                lightMoveFromTo(63,61);//Move the tower over the king.
                break;
            case QUEEN_SIDE_WHITE_MOVE:
                lightMoveFromTo(56,59);//Move the tower over the king.
                break;
        }
    }

    /**
     * Just takes a chessman from location "from" and takes it to location "to".
     * @param from
     * @param to
     */
    private void lightMoveFromTo(int from, int to) {
        if(from >=0 && chessmen[from]!=null){ //If we try to move from a legit position
            chessmen[from].notifyMove();      //Tell the chessman that he was moved (Important for Castling)
            chessmen[to]= chessmen[from];        //Set the chessman to its new position.
            chessmen[from]=null;              //Remove the chessman from its originally squareStates.
        }
    }

    /**
     *
     * @param selectedPosition the position the user selected
     * @param chessmen current positions of the chessmen
     * @return possible castling moves as a boolean array.
     */
    public boolean[] getPossibleMoves(int selectedPosition, Chessman[] chessmen) {
        this.chessmen = chessmen; //Save the new chessmen array, you got from ChessBoardComplex.
        resetPossibleMoves();     //Clean up the return board.
        if(chessmen[selectedPosition].getPiece()== Chessman.Piece.KING){
            if(chessmen[selectedPosition].getColor()== Chessman.Color.WHITE && selectedPosition/8==7 ){ //Must be a black king and on base line
                possibleMoves[KING_SIDE_WHITE_MOVE]=getKingSideWhiteMove(); //Mark possible moves.
                possibleMoves[QUEEN_SIDE_WHITE_MOVE]=getQueenSideWhiteMove();
            }
            if(chessmen[selectedPosition].getColor()== Chessman.Color.BLACK && selectedPosition/8==0 ){ //Must be a black king and on base line
                possibleMoves[KING_SIDE_BLACK_MOVE]=getKingSideBlackMove(); //Mark possible moves.
                possibleMoves[QUEEN_SIDE_BLACK_MOVE]=getQueenSideBlackMove();
            }
        }
        return possibleMoves;
    }


    /**
     * Checks if is possible for black to castle queenside.
     * See: https://en.wikipedia.org/wiki/Castling#Requirements
     * @return true if possible, false if not.
     */
    private boolean getQueenSideBlackMove() {
        if(!queenSideBlackFEN) return false;
        if(!isPathFree(4,0))return false; //Nothing in between.
        if(chessmen[4] == null || chessmen[4].hasBeenMoved())return false; //King wasn´t moved.
        if(chessmen[0] == null || chessmen[0].hasBeenMoved())return false; //Tower wasn´t moved.
        if(squareInDanger(Chessman.Color.WHITE,4))return false;    //King must be safe the whole journey.
        if(squareInDanger(Chessman.Color.WHITE,3))return false;
        if(squareInDanger(Chessman.Color.WHITE,2))return false;
        return true;
    }

    /**
     * Checks if is possible for black to castle king side.
     * See: https://en.wikipedia.org/wiki/Castling#Requirements
     * @return true if possible, false if not.
     */
    private boolean getKingSideBlackMove() {
        if(!kingSideBlackFEN) return false;
        if(!isPathFree(4,7))return false; //Nothing in between.
        if(chessmen[4] == null || chessmen[4].hasBeenMoved())return false; //King wasn´t moved.
        if(chessmen[7] == null || chessmen[7].hasBeenMoved())return false; //Tower wasn´t moved.
        if(squareInDanger(Chessman.Color.WHITE,4))return false;    //King must be safe the whole journey.
        if(squareInDanger(Chessman.Color.WHITE,5))return false;
        if(squareInDanger(Chessman.Color.WHITE,6))return false;
        return true;
    }

    /**
     * Checks if is possible for white to castle king side.
     * See: https://en.wikipedia.org/wiki/Castling#Requirements
     * @return true if possible, false if not.
     */
    private boolean getKingSideWhiteMove() {
        if(!kingSideWhiteFEN) return false;
        if(!isPathFree(60,62))return false; //Nothing in between.
        if(chessmen[60] == null || chessmen[60].hasBeenMoved())return false; //King wasn´t moved.
        if(chessmen[63] == null || chessmen[63].hasBeenMoved())return false; //Tower wasn´t moved.
        if(squareInDanger(Chessman.Color.BLACK,60))return false;    //King must be safe the whole journey.
        if(squareInDanger(Chessman.Color.BLACK,61))return false;
        if(squareInDanger(Chessman.Color.BLACK,62))return false;
        return true;
    }

    /**
     * Checks if is possible for white to castle queen side.
     * See: https://en.wikipedia.org/wiki/Castling#Requirements
     * @return true if possible, false if not.
     */
    private boolean getQueenSideWhiteMove() {
        if(!queenSideWhiteFEN) return false;
        if(!isPathFree(60,56))return false; //Nothing in between.
        if(chessmen[60] == null || chessmen[60].hasBeenMoved())return false; //King wasn´t moved.
        if(chessmen[56] == null || chessmen[56].hasBeenMoved())return false; //Tower wasn´t moved.
        if(squareInDanger(Chessman.Color.BLACK,60))return false;    //King must be safe the whole journey.
        if(squareInDanger(Chessman.Color.BLACK,59))return false;
        if(squareInDanger(Chessman.Color.BLACK,58))return false;
        return true;
    }

    /**
     * This method checks if the player with color playerColor can somehow move
     * with any chessman to the goalSquare.
     * @param playerColor
     * @param goalSquare
     * @return if the player can move there.
     */
    private boolean squareInDanger(Chessman.Color playerColor, int goalSquare){
        for(int i = 0;i<64;i++){
            if(chessmen[i]!=null && chessmen[i].getColor() == playerColor){
                if(ruleBook.getPossibleMoves(i, chessmen)[goalSquare])return true;
            }
        }
        return false;
    }

    /**
     * The method checks if the path from "from" to "to" is free
     * Only for x-Direction Movement.
     * @param from  the position of the selected chessman
     * @param to    where it wants to move
     * @return      when blocked false, otherwise true
     */
    private boolean isPathFree(int from, int to) {
        boolean pathFree = true;
        //TODO simplify this.
        byte xDirection = (byte)Math.signum(to%8-from%8);
        byte yDirection = (byte)Math.signum(to/8-from/8);

        byte xCurrent = (byte) (from%8+xDirection);
        byte yCurrent = (byte) (from/8+yDirection);

        byte xDest = (byte) (to%8);

        while(!((xCurrent==xDest) || (xCurrent+(yCurrent*8) < 0)||(xCurrent+(yCurrent*8) > 63))) {
            if (chessmen[xCurrent+(yCurrent*8)]!=null){
                pathFree = false;
            }
            xCurrent = (byte) (xCurrent + xDirection);
        }
        return pathFree;
    }

    //Some simple setters.
    public void setKingSideWhiteFEN(boolean kingSideWhiteFEN) {
        this.kingSideWhiteFEN = kingSideWhiteFEN;
    }

    public void setKingSideBlackFEN(boolean kingSideBlackFEN) {
        this.kingSideBlackFEN = kingSideBlackFEN;
    }

    public void setQueenSideWhiteFEN(boolean queenSideWhiteFEN) {
        this.queenSideWhiteFEN = queenSideWhiteFEN;
    }

    public void setQueenSideBlackFEN(boolean queenSideBlackFEN) {
        this.queenSideBlackFEN = queenSideBlackFEN;
    }

    /**
     * Resets the possibleMove array e.g all fields are false you can´t move anywhere.
     */
    private void resetPossibleMoves() {
        for (int i=0;i<64;i++){
            possibleMoves[i]=false;
        }
    }

    public void setChessMen(Chessman[] chessmen) {
        this.chessmen = chessmen;
    }


    public boolean getKingSideWhite() {
        boolean whiteKingMoved = (chessmen[60] == null || chessmen[60].hasBeenMoved());
        boolean whiteRookKingSideMoved = (chessmen[63] == null || chessmen[63].hasBeenMoved());
        return kingSideWhiteFEN && !whiteKingMoved && !whiteRookKingSideMoved;
    }

    public boolean getQueenSideWhite() {
        boolean whiteKingMoved = (chessmen[60] == null || chessmen[60].hasBeenMoved());
        boolean whiteRookQueenSideMoved = (chessmen[56] == null || chessmen[56].hasBeenMoved());
        return kingSideWhiteFEN && !whiteKingMoved && !whiteRookQueenSideMoved;
    }

    public boolean getKingSideBlack() {
        boolean blackKingMoved = (chessmen[4] == null || chessmen[4].hasBeenMoved());
        boolean blackRookKingSideMoved = (chessmen[7] == null || chessmen[7].hasBeenMoved());
        return kingSideWhiteFEN && !blackKingMoved && !blackRookKingSideMoved;
    }

    public boolean getQueenSideBlack() {
        boolean blackKingMoved = (chessmen[4] == null || chessmen[4].hasBeenMoved());
        boolean blackRookQueenSideMoved = (chessmen[0] == null || chessmen[0].hasBeenMoved());
        return kingSideWhiteFEN && !blackKingMoved && !blackRookQueenSideMoved;
    }
}
