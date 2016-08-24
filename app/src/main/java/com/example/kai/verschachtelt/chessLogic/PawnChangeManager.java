package com.example.kai.verschachtelt.chessLogic;

/**
 * Created by Kai on 24.08.2016.
 */
public class PawnChangeManager {

    private Chessman[] chessmen;

    /**
     * Copy constructor, now not necessary.
     * @param toCopy
     */
    public PawnChangeManager(PawnChangeManager toCopy) {
        chessmen = Chessman.deepCopy(toCopy.chessmen);
    }

    public PawnChangeManager(Chessman[] chessmen) {
        this.chessmen = chessmen;
    }

    /**
     * Method checks if a pawn went to the other side of the board, e.g a pawn change is possible.
     * @param chessmen the board where we look for that situation.
     * @return true if a pawn must be exchanged, false if not.
     */
    public boolean isPawnChangePossible(Chessman[] chessmen) {
        for(int j = 0; j < 8; j++) {
            if (chessmen[j] != null) {
                if (chessmen[j].getPiece() == Chessman.Piece.PAWN) return true;
            }
            if (chessmen[56+j] !=null){
                if (chessmen[56+j].getPiece() == Chessman.Piece.PAWN) return true;
            }
        }
        return false;   //false, if there is no pawn in first/last row
    }

    /**
     * Checks where the pawn must be changed.
     * @param chessmen the board where we look for that situation.
     * @return the position of the pawn that must be changed.
     */
    public int getPawnChangePosition(Chessman[] chessmen) {
        for(int j = 0; j < 8; j++) {
            if (chessmen[j] != null) {
                if (chessmen[j].getPiece() == Chessman.Piece.PAWN) return j;
            }
            if (chessmen[56+j] !=null){
                if (chessmen[56+j].getPiece() == Chessman.Piece.PAWN) return 56+j;
            }
        }
        return -1;  // -1, if there is no such position

    }

}
