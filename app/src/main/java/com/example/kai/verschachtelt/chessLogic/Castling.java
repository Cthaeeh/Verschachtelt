package com.example.kai.verschachtelt.chessLogic;

/**
 * Created by Kai on 16.08.2016.
 * //TODO make this work with redo,undo (implement all copy constructors correctly)
 * //TODO simplify, comment everything
 * //TODO Bug test with FEN parser
 */
public class Castling {
    private boolean queenSideWhite = true;
    private boolean queenSideBlack = true;
    private boolean kingSideWhite  = true;
    private boolean kingSideBlack  = true;

    private final int KING_SIDE_BLACK_MOVE = 6;
    private final int QUEEN_SIDE_BLACK_MOVE = 2;
    private final int KING_SIDE_WHITE_MOVE = 62;
    private final int QUEEN_SIDE_WHITE_MOVE= 58;

    private static RuleBook ruleBook = new RuleBook();                  //There is just one Rulebook for every game of chess.
    boolean[] possibleMoves = new boolean[64];
    private Chessman[] board;

    public Castling(){

    }

    /**
     * Copy Constructor
     * //TODO implement this carefully
     * @param toCopy
     */
    public Castling(Castling toCopy){

    }

    public boolean[] getPossibleMoves(int selectedPosition, Chessman[] chessmen) {
        this.board = chessmen;
        resetPossibleMoves();
        if(chessmen[selectedPosition].getPiece()== Chessman.Piece.KING){
            if(chessmen[selectedPosition].getColor()== Chessman.Color.WHITE){
                possibleMoves[KING_SIDE_WHITE_MOVE]=getKingSideWhiteMove();
                possibleMoves[QUEEN_SIDE_WHITE_MOVE]=getQueenSideWhiteMove();
            }else {
                possibleMoves[KING_SIDE_BLACK_MOVE]=getKingSideBlackMove();
                possibleMoves[QUEEN_SIDE_BLACK_MOVE]=getQueenSideBlackMove();
            }
        }
        return possibleMoves;
    }

    /**
     * TODO Comment the four methods, simplify
     * @return
     */
    private boolean getQueenSideBlackMove() {
        if(!isPathFree(4,2))return false; //Nothing in between.
        if(board[4].hasBeenMoved())return false; //King wasn´t moved.
        if(board[0].hasBeenMoved())return false; //Tower wasn´t moved.
        if(canMoveTo(Chessman.Color.WHITE,4))return false;    //King must be safe the whole journey.
        if(canMoveTo(Chessman.Color.WHITE,3))return false;
        if(canMoveTo(Chessman.Color.WHITE,2))return false;
        return true;
    }

    private boolean getKingSideBlackMove() {
        if(!isPathFree(4,6))return false; //Nothing in between.
        if(board[4].hasBeenMoved())return false; //King wasn´t moved.
        if(board[7].hasBeenMoved())return false; //Tower wasn´t moved.
        if(canMoveTo(Chessman.Color.WHITE,4))return false;    //King must be safe the whole journey.
        if(canMoveTo(Chessman.Color.WHITE,5))return false;
        if(canMoveTo(Chessman.Color.WHITE,6))return false;
        return true;
    }

    private boolean getKingSideWhiteMove() {
        if(!isPathFree(60,62))return false; //Nothing in between.
        if(board[60].hasBeenMoved())return false; //King wasn´t moved.
        if(board[63].hasBeenMoved())return false; //Tower wasn´t moved.
        if(canMoveTo(Chessman.Color.BLACK,60))return false;    //King must be safe the whole journey.
        if(canMoveTo(Chessman.Color.BLACK,61))return false;
        if(canMoveTo(Chessman.Color.BLACK,62))return false;
        return true;
    }

    private boolean getQueenSideWhiteMove() {
        if(!isPathFree(60,58))return false; //Nothing in between.
        if(board[60].hasBeenMoved())return false; //King wasn´t moved.
        if(board[56].hasBeenMoved())return false; //Tower wasn´t moved.
        if(canMoveTo(Chessman.Color.BLACK,60))return false;    //King must be safe the whole journey.
        if(canMoveTo(Chessman.Color.BLACK,59))return false;
        if(canMoveTo(Chessman.Color.BLACK,58))return false;
        return true;
    }

    /**
     * This method checks if the player with color playerColor can somehow move
     * with any chessman to the goalSquare.
     * @param playerColor
     * @param goalSquare
     * @return if the player can move there.
     */
    private boolean canMoveTo(Chessman.Color playerColor, int goalSquare){
        for(int i = 0;i<64;i++){
            if(board[i]!=null && board[i].getColor() == playerColor){
                if(ruleBook.getPossibleMoves(i,board)[goalSquare])return true;
            }
        }
        return false;
    }

    /**
     * The method checks if the path from "from" to "to" is free
     * @param from  the position of the selected chessman
     * @param to    where it wants to move
     * @return      when blocked false, otherwise true
     */
    private boolean isPathFree(int from, int to) {
        //TODO simplify this. Doesnt need to check diagonal movements
        byte xDirection = (byte)Math.signum(to%8-from%8);
        byte yDirection = (byte)Math.signum(to/8-from/8);

        byte xCurrent = (byte) (from%8+xDirection);
        byte yCurrent = (byte) (from/8+yDirection);

        byte xDest = (byte) (to%8);
        byte yDest = (byte) (to/8);

        while(!(xCurrent==xDest && yCurrent==yDest)) {
            if (board[xCurrent+(yCurrent*8)]!=null){
                return false;
            }
            xCurrent = (byte) (xCurrent + xDirection);
            yCurrent = (byte) (yCurrent + yDirection);
        }
        return true;
    }

    //Some simple setters.
    public void setKingSideWhite(boolean kingSideWhite) {
        this.kingSideWhite = kingSideWhite;
    }

    public void setKingSideBlack(boolean kingSideBlack) {
        this.kingSideBlack = kingSideBlack;
    }

    public void setQueenSideWhite(boolean queenSideWhite) {
        this.queenSideWhite = queenSideWhite;
    }

    public void setQueenSideBlack(boolean queenSideBlack) {
        this.queenSideBlack = queenSideBlack;
    }

    private void resetPossibleMoves() {
        for (int i=0;i<64;i++){
            possibleMoves[i]=false;
        }
    }
}
