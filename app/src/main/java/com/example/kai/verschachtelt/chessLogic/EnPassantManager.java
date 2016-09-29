package com.example.kai.verschachtelt.chessLogic;

import java.util.Arrays;

/**
 * Created by ivayl on 12.09.2016.
 * For managing en-passant possibilities, and moves
 */
public class EnPassantManager {

    /**
     *boolean array indicating, the pawns on which positions have the possibility of
     *an en-passant move.
     * 0-15 are standing for the positions 24-39 on the Chessman-Array
     */
    private boolean[] enPassantPossibilities = new boolean[16];
    /**
     *the position of the opponent´s pawn, which invoked the en passant possibility
     */
    private int opponentPawn;

    private boolean[] possibleMoves = new boolean[64];
    private Chessman[] chessmen;

    /**
     * The standard en-passant constructor
     * @param chessmen
     */
    public EnPassantManager(Chessman[] chessmen){
        this.chessmen = chessmen;
    }

    /**
     * A copy constructor
     * @param toCopy the constructor to be copied
     */
    public EnPassantManager(EnPassantManager toCopy){
        chessmen = Chessman.deepCopy(toCopy.chessmen);
        enPassantPossibilities = Arrays.copyOf(toCopy.enPassantPossibilities, enPassantPossibilities.length);
        opponentPawn = toCopy.opponentPawn;
    }

    /**
     * A method, which checks, which en-passant moves are possible for a certain pawn
     * @param selectedPosition position of the pawn which is going to be moved
     * @param chessmen the current Chessman Array
     * @return the possible EnPassant Moves
     */
    public boolean[] getPossibleMoves(int selectedPosition, Chessman[] chessmen) {
        this.chessmen = chessmen; //Save the new chessmen array, you got from ChessBoardComplex.
        resetPossibleMoves();     //Clean up the return board.

        if (chessmen[selectedPosition].getColor() == Chessman.Color.WHITE) {
            if (chessmen[selectedPosition].getPiece() == Chessman.Piece.PAWN & selectedPosition < 40 & selectedPosition > 23) {
                if (enPassantPossibilities[selectedPosition - 24]) {
                    possibleMoves[opponentPawn + 8] = true;
                }
            }
        } else {
            if (chessmen[selectedPosition].getPiece() == Chessman.Piece.PAWN & selectedPosition < 40 & selectedPosition > 23) {
                if (enPassantPossibilities[selectedPosition - 24]) {
                    possibleMoves[opponentPawn - 8] = true;
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Resets the possibleMove array e.g all fields are false and you can´t move anywhere.
     */
    private void resetPossibleMoves() {
        for (int i=0;i<64;i++){
            possibleMoves[i]=false;
        }
    }

    /**
     * setting the positions from which an en-passant move is possible, if any pawn is on them
     * after a pawn jump
     * @param pawnPosition the position of the opponent´s pawn after the jump
     */
    private void setEnPassantPossibilities(int pawnPosition){
        /*
        there are max. two fields, for which en passant has to be enabled after a pawn jump,
        but they have to be in a row with the pawn, that has fulfilled a jump of course
        this is, what this method is looking for
        */
        int one = pawnPosition - 1;
        int two = pawnPosition + 1;
        if(pawnPosition/8 == one/8) enPassantPossibilities[one - 24] = true;
        if(pawnPosition/8 == two/8) enPassantPossibilities[two - 24] = true;
    }

    /**
     * resetting the en passant possibilities, which of course are possible only
     * within the one move after a pawn jump
     */
    private void resetEnPassantPossibilities(){
        for(int i = 0; i < 16;i++){
            enPassantPossibilities[i] = false;
        }
    }

    /**
     * For resetting en passant positions or enable a en passant possibility.
     * @param position what piece moves.
     */
    public void handleEnPassant(int position, int selectedPosition, Chessman[] chessmen) {
        if(chessmen[position].getPiece() == Chessman.Piece.PAWN && Math.abs(position-selectedPosition) == 16){
            opponentPawn = selectedPosition;
            setEnPassantPossibilities(position); // activate en passant possibilities after double jump
        } else resetEnPassantPossibilities();

        if(chessmen[position].getPiece() == Chessman.Piece.PAWN  && position == opponentPawn + 8 &&  chessmen[position].getColor() == Chessman.Color.WHITE) {
            chessmen[opponentPawn + 16] = null;
            resetEnPassantPossibilities();// en passant removal for white
        }
        if(chessmen[position].getPiece() == Chessman.Piece.PAWN && position == opponentPawn - 8 && chessmen[position].getColor() == Chessman.Color.BLACK) {
            chessmen[opponentPawn - 16] = null;
            resetEnPassantPossibilities();// en passant removal for black
        }
    }
}
