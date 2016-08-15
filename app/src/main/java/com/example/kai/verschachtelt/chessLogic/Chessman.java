package com.example.kai.verschachtelt.chessLogic;

/**
 * Created by Kai on 10.08.2016.
 */
public class Chessman {

    private final Piece piece;
    private final Color color;
    private boolean hasBeenMoved = false;

    public static Chessman[] getStandartSetup() {
        return new Chessman[]{
                new Chessman(Piece.ROOK,Color.BLACK),new Chessman(Piece.KNIGHT,Color.BLACK),new Chessman(Piece.BISHOP,Color.BLACK),new Chessman(Piece.QUEEN,Color.BLACK),
                new Chessman(Piece.KING,Color.BLACK),new Chessman(Piece.BISHOP,Color.BLACK),new Chessman(Piece.KNIGHT,Color.BLACK),new Chessman(Piece.ROOK,Color.BLACK),
                new Chessman(Piece.PAWN,Color.BLACK),new Chessman(Piece.PAWN,Color.BLACK)  ,new Chessman(Piece.PAWN,Color.BLACK)  ,new Chessman(Piece.PAWN,Color.BLACK),
                new Chessman(Piece.PAWN,Color.BLACK),new Chessman(Piece.PAWN,Color.BLACK)  ,new Chessman(Piece.PAWN,Color.BLACK)  ,new Chessman(Piece.PAWN,Color.BLACK),
                null,null,null,null,null,null,null,null,
                null,null,null,null,null,null,null,null,
                null,null,null,null,null,null,null,null,
                null,null,null,null,null,null,null,null,
                new Chessman(Piece.PAWN,Color.WHITE),new Chessman(Piece.PAWN,Color.WHITE)  ,new Chessman(Piece.PAWN,Color.WHITE)  ,new Chessman(Piece.PAWN,Color.WHITE),
                new Chessman(Piece.PAWN,Color.WHITE),new Chessman(Piece.PAWN,Color.WHITE)  ,new Chessman(Piece.PAWN,Color.WHITE)  ,new Chessman(Piece.PAWN,Color.WHITE),
                new Chessman(Piece.ROOK,Color.WHITE),new Chessman(Piece.KNIGHT,Color.WHITE),new Chessman(Piece.BISHOP,Color.WHITE),new Chessman(Piece.QUEEN,Color.WHITE),
                new Chessman(Piece.KING,Color.WHITE),new Chessman(Piece.BISHOP,Color.WHITE),new Chessman(Piece.KNIGHT,Color.WHITE),new Chessman(Piece.ROOK,Color.WHITE),};
    }

    public Piece getPiece() {
        return piece;
    }

    public Color getColor() {
        return color;
    }

    public boolean hasBeenMoved(){
        return hasBeenMoved;
    }

    /**
     * lets the chessmen know if it was moved once in the game.
     */
    public void notifyMove() {
        hasBeenMoved = true;
    }

    /**
     Representation of all chess man as enums
     **/
    public enum Piece {
        ROOK, KNIGHT,  BISHOP,  QUEEN,  KING,  PAWN
    }

    public enum Color {
        WHITE, BLACK
    }

    public Chessman(Piece piece, Color color) {
        this.piece = piece;
        this.color = color;
    }

    /**
     * Custom equals method, that does what you would kinda expect of equals in this case.
     * @param obj   The object to compare this one with
     * @return      True if same color AND piece, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        final Chessman other = (Chessman) obj;
        if (this.getPiece() != other.getPiece()) {
            return false;
        }
        if (this.getColor() != other.getColor()) {
            return false;
        }
        return true;
    }
}
