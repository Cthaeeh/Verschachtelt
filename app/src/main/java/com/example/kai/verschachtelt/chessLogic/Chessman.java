package com.example.kai.verschachtelt.chessLogic;

/**
 * Created by Kai on 10.08.2016.
 */
public class Chessman {

    private final Piece piece;
    private final Color color;

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

}
