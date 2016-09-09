package com.example.kai.verschachtelt.pvAIGame.chess_AI;

import android.util.Log;

/**
 * Created by Kai on 07.09.2016.
 * The purpose of this class is solely to evaluate a Board.
 */
public class BordEvaluation {

    private static final double  KING_WHITE_VALUE   = 100;
    private static final double  QUEEN_WHITE_VALUE  = 10;
    private static final double  ROOK_WHITE_VALUE   = 5;
    private static final double  KNIGHT_WHITE_VALUE = 3;
    private static final double  BISHOP_WHITE_VALUE = 3;
    private static final double  PAWN_WHITE_VALUE   = 1;

    //Following must be negative !
    private static final double  KING_BLACK_VALUE   = -100;
    private static final double  QUEEN_BLACK_VALUE  = -10;
    private static final double  ROOK_BLACK_VALUE   = -5;
    private static final double  KNIGHT_BLACK_VALUE = -3;
    private static final double  BISHOP_BLACK_VALUE = -3;
    private static final double  PAWN_BLACK_VALUE   = -1;

    private static final String TAG = "BordEvaluation";

    /**
     * Evaluates a Board. E.g it looks for what player this board is favorable.
     * If it is favorable for BLACK the return value is NEGATIVE. Otherwise, if the board
     * is favorable for white it returns a POSITIVE value.
     * @param board
     * @return
     */
    public static double evaluate(final byte[] board) {
        double boardValue = 0.0;
        boardValue+=getMaterialValue(board);
        boardValue+=getCenterControl(board);
        boardValue+=getMobility(board);
        //TODO evaluate King safety, etc
        return boardValue;
    }

    /**
     * Evaluates the mobility.
     * @param board
     * @return
     */
    private static double getMobility(byte[] board) {
        double mobilityValue = 0.0;
        mobilityValue -= MoveGenerator.getMobility(board)*0.1;
        return mobilityValue;
    }

    /**
     * Evaluates a board for center control.
     *
     * @param board
     * @return
     */
    private static double getCenterControl(byte[] board) {
        double boardValue = 0.0;
        //If black controls center that is better for black so return a more negative value.
        if(board[54]<0)boardValue-=0.2;
        if(board[55]<0)boardValue-=0.2;
        if(board[64]<0)boardValue-=0.2;
        if(board[65]<0)boardValue-=0.2;

        if(board[54]>0)boardValue+=0.2;
        if(board[55]>0)boardValue+=0.2;
        if(board[64]>0)boardValue+=0.2;
        if(board[65]>0)boardValue+=0.2;

        return boardValue;
    }


    private static double getMaterialValue(byte[] board) {
        double boardValue = 0.0;
        for(int i = 21;i<99;i++){   //Iterate through board.
            switch (board[i]){      //TODO make this if else because little faster!
                case MoveGenerator.KING_BLACK:
                    boardValue += KING_BLACK_VALUE;
                    break;
                case MoveGenerator.QUEEN_BLACK:
                    boardValue += QUEEN_BLACK_VALUE;
                    break;
                case MoveGenerator.ROOK_BLACK:
                    boardValue += ROOK_BLACK_VALUE;
                    break;
                case MoveGenerator.KNIGHT_BLACK:
                    boardValue += KNIGHT_BLACK_VALUE;
                    break;
                case MoveGenerator.BISHOP_BLACK:
                    boardValue += BISHOP_BLACK_VALUE;
                    break;
                case MoveGenerator.PAWN_BLACK:
                    boardValue += PAWN_BLACK_VALUE;
                    break;
                case MoveGenerator.KING_WHITE:
                    boardValue += KING_WHITE_VALUE;
                    break;
                case MoveGenerator.QUEEN_WHITE:
                    boardValue += QUEEN_WHITE_VALUE;
                    break;
                case MoveGenerator.ROOK_WHITE:
                    boardValue += ROOK_WHITE_VALUE;
                    break;
                case MoveGenerator.KNIGHT_WHITE:
                    boardValue += KNIGHT_WHITE_VALUE;
                    break;
                case MoveGenerator.BISHOP_WHITE:
                    boardValue += BISHOP_WHITE_VALUE;
                    break;
                case MoveGenerator.PAWN_WHITE:
                    boardValue += PAWN_WHITE_VALUE;
                    break;
            }
        }
        return boardValue;
    }


}
