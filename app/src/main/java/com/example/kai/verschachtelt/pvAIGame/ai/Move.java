package com.example.kai.verschachtelt.pvAIGame.ai;

import com.example.kai.verschachtelt.chessLogic.Chessman;

/**
 * Created by Kai on 04.09.2016.
 * This class represents a Move. It can be simple move from one to another place. But it
 * also can be a pawn promotion or TODO en passant
 */
public class Move {
    //For transferring a 10x12 to a 8x8 board. See: https://chessprogramming.wikispaces.com/10x12+Board
    final int mailbox[] = {
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1,  0,  1,  2,  3,  4,  5,  6,  7, -1,
                -1,  8,  9, 10, 11, 12, 13, 14, 15, -1,
                -1, 16, 17, 18, 19, 20, 21, 22, 23, -1,
                -1, 24, 25, 26, 27, 28, 29, 30, 31, -1,
                -1, 32, 33, 34, 35, 36, 37, 38, 39, -1,
                -1, 40, 41, 42, 43, 44, 45, 46, 47, -1,
                -1, 48, 49, 50, 51, 52, 53, 54, 55, -1,
                -1, 56, 57, 58, 59, 60, 61, 62, 63, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
    };

    private boolean nullMove = false;
    public int from = 0;
    public int to   = 0;

    private boolean isPromotion = false;
    private Chessman.Piece promotedPiece = null;

    /**
     * This constructor creates a Move-Object from two byteBoards
     * (the first one previous to the move, the second one after the move)
     */
    public Move(int move) {
        if(move == 0){
            nullMove = true;
            return;
        }
        int from10x12Val = MoveAsInt.getStart(move);
        int to10x12Val = MoveAsInt.getDest(move);
        from = mailbox[from10x12Val ];
        to = mailbox[to10x12Val];
        if(from == -1 || to == -1){
            nullMove = true;
        }
        isPromotion = (MoveAsInt.getPromotedPiece(move)!=0);
        if(isPromotion){
            promotedPiece = extractPromotedPiece(move);
        }
        //TODO handle en passant,
    }

    /**
     * Takes a move encoded as an int and extracts the promoted Piece as an enum.0
     * @param move  the move as an int.
     * @return  piece as enum
     */
    private Chessman.Piece extractPromotedPiece(int move) {
        switch (MoveAsInt.getPromotedPiece(move)){
            case MoveGen.QUEEN_WHITE:
                return Chessman.Piece.QUEEN;
            case MoveGen.KNIGHT_WHITE:
                return Chessman.Piece.KNIGHT;
            case MoveGen.BISHOP_WHITE:
                return Chessman.Piece.BISHOP;
            case MoveGen.ROOK_WHITE:
                return Chessman.Piece.ROOK;
            default:
                return null;
        }
    }
    public boolean isNullMove(){
        return nullMove;
    }

    /**
     * Returns if this Move resulted in a pawn promotion.
     * @return
     */
    public boolean isPromotion(){
        return isPromotion;
    }

    /**
     *  @return the promoted piece if the move was a promotion otherwise null.
     */
    public Chessman.Piece getPromotedPiece() {
        return promotedPiece;
    }
}
