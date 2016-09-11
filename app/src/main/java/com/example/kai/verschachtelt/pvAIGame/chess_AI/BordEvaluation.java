package com.example.kai.verschachtelt.pvAIGame.chess_AI;

import android.util.Log;

/**
 * Created by Kai on 07.09.2016.
 * The purpose of this class is solely to evaluate a Board.
 */
public class BordEvaluation {

    private static final double  KING_WHITE_VALUE   = 200;
    private static final double  QUEEN_WHITE_VALUE  = 9.6;
    private static final double  ROOK_WHITE_VALUE   = 5.2;
    private static final double  KNIGHT_WHITE_VALUE = 3.2;
    private static final double  BISHOP_WHITE_VALUE = 3.3;
    private static final double  PAWN_WHITE_VALUE   = 1;

    //Following must be negative !
    private static final double  KING_BLACK_VALUE   = -200;
    private static final double  QUEEN_BLACK_VALUE  = -9.6;
    private static final double  ROOK_BLACK_VALUE   = -5.2;
    private static final double  KNIGHT_BLACK_VALUE = -3.2;
    private static final double  BISHOP_BLACK_VALUE = -3.3;
    private static final double  PAWN_BLACK_VALUE   = -1;

    private static final double  PAWN_BLOCK_PENALTY = 0.5;
    private static final double  PAWN_SUPPORT_BONUS = 0.25;

    private static final int  NORTH = -10;
    private static final int  SOUTH = 10;
    private static final int  EAST  = 1;
    private static final int  WEST  = -1;

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
        mobilityValue -= MoveGenerator.getMobility(board)*0.12;
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
        for(int position = 21;position<99;position++){   //Iterate through board.
            switch (board[position]){      //TODO make this if else because little faster!
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
                    boardValue += getBlackPawnValue(position,board);
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
                    boardValue += getWhitePawnValue(position,board);
                    break;
            }
        }
        return boardValue;
    }

    /**
     * Evaluates the value of a Pawn.
     * Penalty for double pawn
     * Bonus for supported pawn
     * @param position
     * @param board
     * @return
     */
    private static double getWhitePawnValue(int position, byte[] board) {
        double pawnVal = 0.0;
        if(board[position+NORTH]==MoveGenerator.PAWN_WHITE){    //double pawn
            pawnVal-=PAWN_BLOCK_PENALTY;
        }
        if(board[position+NORTH]==MoveGenerator.PAWN_BLACK){    //opposed by enemy
            pawnVal-=PAWN_BLOCK_PENALTY/2;
        }
        if(board[position+SOUTH+EAST]==MoveGenerator.PAWN_WHITE){
            pawnVal+=PAWN_SUPPORT_BONUS;
        }
        if(board[position+SOUTH+WEST]==MoveGenerator.PAWN_WHITE){
            pawnVal+=PAWN_SUPPORT_BONUS;
        }
        return pawnVal;
    }

    /**
     * Evaluates the value of a Pawn.
     * Penalty for double pawn
     * Bonus for supported pawn
     * @param position
     * @param board
     * @return
     */
    private static double getBlackPawnValue(int position, byte[] board) {
        double pawnVal = 0.0;
        if(board[position+NORTH]==MoveGenerator.PAWN_BLACK){    //double pawn
            pawnVal+=PAWN_BLOCK_PENALTY;
        }
        if(board[position+NORTH]==MoveGenerator.PAWN_WHITE){    //opposed by enemy
            pawnVal+=PAWN_BLOCK_PENALTY/2;
        }
        if(board[position+NORTH+EAST]==MoveGenerator.PAWN_BLACK){
            pawnVal-=PAWN_SUPPORT_BONUS;
        }
        if(board[position+NORTH+WEST]==MoveGenerator.PAWN_BLACK){
            pawnVal-=PAWN_SUPPORT_BONUS;
        }
        return pawnVal;
    }

    /**
     * Evaluates the board for King-Safety.
     * @param board
     * @return
     */
    private static double getKingSafety(int position, byte[] board){
        double boardValue = 0.0;


        return boardValue;
    }
}
