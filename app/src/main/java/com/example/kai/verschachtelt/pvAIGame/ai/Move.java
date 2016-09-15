package com.example.kai.verschachtelt.pvAIGame.ai;

/**
 * Created by Kai on 04.09.2016.
 * This class represents a Move. It can be simple move from one to another place. But it
 * also can be a pawn promotion (later maybe castling, en passant).
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

    public int from = 0;
    public int to   = 0;

    private int from10x12Val = 0;
    private int to10x12Val = 0;

    private boolean isPromotion = false;

    public Move(int from, int to) {
        this.from = from;
        this.to = to;
    }

    /**
     * This constructor creates a Move-Object from two byteBoards
     * (the first one previous to the move, the second one after the move)
     */
    public Move(int move) {
        if(move == 0){
            return;
        }
        from10x12Val = MoveAsInteger.getStart(move);
        to10x12Val = MoveAsInteger.getDest(move);
        from = mailbox[from10x12Val ];
        to = mailbox[to10x12Val];
        //TODO handle promotion, en passant, castling
    }

    /**
     * Returns if this Move resulted in a pawn promotion.
     * @return
     */
    public boolean isPromotion(){
        return isPromotion;
    }
}
