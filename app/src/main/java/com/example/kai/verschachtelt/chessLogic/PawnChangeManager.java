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

    /**
     * Checks where the pawn must be changed.
     * @param chessmen the board where we look for that situation.
     * @return the position of the pawn that must be changed.
     */
    public int getPawnChangePosition(Chessman[] chessmen) {
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

}
