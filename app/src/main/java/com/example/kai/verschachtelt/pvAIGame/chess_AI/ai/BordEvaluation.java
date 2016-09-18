package com.example.kai.verschachtelt.pvAIGame.chess_AI.ai;

import android.support.v7.app.NotificationCompat;

/**
 * Created by Kai on 07.09.2016.
 * The purpose of this class is solely to evaluate a Board.
 */
public class BordEvaluation {

    private static final short  KING_WHITE_VALUE   = 10000;
    private static final short  QUEEN_WHITE_VALUE  = 960;
    private static final short  ROOK_WHITE_VALUE   = 520;
    private static final short  KNIGHT_WHITE_VALUE = 320;
    private static final short  BISHOP_WHITE_VALUE = 330;
    private static final short  PAWN_WHITE_VALUE   = 100;

    //Following must be negative !
    private static final short  KING_BLACK_VALUE   = -10000;
    private static final short  QUEEN_BLACK_VALUE  = -960;
    private static final short  ROOK_BLACK_VALUE   = -520;
    private static final short  KNIGHT_BLACK_VALUE = -320;
    private static final short  BISHOP_BLACK_VALUE = -330;
    private static final short  PAWN_BLACK_VALUE   = -100;

    //Piece Square Tables
    private static final short[] KnightTable = new short[]
            {
                    0, 0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0,
                    0, 0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0,
                    0, -50,-40,-30,-30,-30,-30,-40,-50,0,
                    0, -40,-20,  0,  0,  0,  0,-20,-40,0,
                    0, -30,  0, 10, 15, 15, 10,  0,-30,0,
                    0, -30,  5, 15, 20, 20, 15,  5,-30,0,
                    0, -30,  0, 15, 20, 20, 15,  0,-30,0,
                    0, -30,  5, 10, 15, 15, 10,  5,-30,0,
                    0, -40,-20,  0,  5,  5,  0,-20,-40,0,
                    0, -50,-40,-20,-30,-30,-20,-40,-50,0,
                    0, 0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0,
                    0, 0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0
            };
    private static final short[] BishopTable = new short[]
            {
                    0, 0  ,0  ,0  ,0  ,0  ,0  ,0  ,0   ,0,
                    0, 0  ,0  ,0  ,0  ,0  ,0  ,0  ,0   ,0,
                    0, -20,-10,-10,-10,-10,-10,-10,-20 ,0,
                    0, -10,  0,  0,  0,  0,  0,  0,-10 ,0,
                    0, -10,  0,  5, 10, 10,  5,  0,-10 ,0,
                    0, -10,  5,  5, 10, 10,  5,  5,-10 ,0,
                    0, -10,  0, 10, 10, 10, 10,  0,-10 ,0,
                    0, -10, 10, 10, 10, 10, 10, 10,-10 ,0,
                    0, -10,  5,  0,  0,  0,  0,  5,-10 ,0,
                    0, -20,-10,-40,-10,-10,-40,-10,-20 ,0,
                    0, 0  ,0  ,0  ,0  ,0  ,0  ,0  ,0   ,0,
                    0, 0  ,0  ,0  ,0  ,0  ,0  ,0  ,0   ,0
            };

    private static final short[] PawnTable = new short[]
            {
                    0,   0,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0,
                    0,   0,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0,
                    0,   0,  0,  0,  0,  0,  0,  0,0  ,0,
                    0,  50, 50, 50, 50, 50, 50, 50, 50,0,
                    0,  10, 10, 20, 30, 30, 20, 10, 10,0,
                    0,   5,  5, 10, 27, 27, 10,  5,  5,0,
                    0,   0,  0,  0, 25, 25,  0,  0,  0,0,
                    0,   5, -5,-10,  0,  0,-10, -5,  5,0,
                    0,   5, 10, 10,-25,-25, 10, 10,  5,0,
                    0,   0,  0,  0,  0,  0,  0,  0,0  ,0,
                    0,   0,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0,
                    0,   0,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0,
            };

    private static final short[] RookTable = new short[]
            {
                    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
                    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
                    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
                    0,  5, 10, 10, 10, 10, 10, 10,  5,  0,
                    0, -5,  0,  0,  0,  0,  0,  0, -5,  0,
                    0, -5,  0,  0,  0,  0,  0,  0, -5,  0,
                    0, -5,  0,  0,  0,  0,  0,  0, -5,  0,
                    0, -5,  0,  0,  0,  0,  0,  0, -5,  0,
                    0, -5,  0,  0,  0,  0,  0,  0, -5,  0,
                    0,  0,  0,  0,  5,  5,  5,  0,  0,  0,
                    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
                    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,

            };

    private static final short[] QueenTable = new short[]
            {
                    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
                    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
                    0,-20,-10,-10, -5, -5,-10,-10,-20,  0,
                    0,-10,  0,  0,  0,  0,  0,  0,-10,  0,
                    0,-10,  0,  5,  5,  5,  5,  0,-10,  0,
                    0, -5,  0,  5,  5,  5,  5,  0, -5,  0,
                    0,  0,  0,  5,  5,  5,  5,  0, -5,  0,
                    0,-10,  5,  5,  5,  5,  5,  0,-10,  0,
                    0,-10,  0,  5,  0,  0,  0,  0,-10,  0,
                    0,-20,-10,-10, -5, -5,-10,-10,-20,  0,
                    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
                    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
            };

    private static final short[] KingTableMiddleGame = new short[]
            {
                    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
                    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
                    0,-30,-40,-40,-50,-50,-40,-40,-30,  0,
                    0,-30,-40,-40,-50,-50,-40,-40,-30,  0,
                    0,-30,-40,-40,-50,-50,-40,-40,-30,  0,
                    0,-30,-40,-40,-50,-50,-40,-40,-30,  0,
                    0,-20,-30,-30,-40,-40,-30,-30,-20,  0,
                    0,-10,-20,-20,-20,-20,-20,-20,-10,  0,
                    0, 20, 20,  0,  0,  0,  0, 20, 20,  0,
                    0, 20, 30, 10,  0,  0, 10, 30, 20,  0,
                    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
                    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,

            };

    private static final short[] KingTableEndGame = new short[]
            {
                    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
                    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
                    0,-50,-40,-30,-20,-20,-30,-40,-50,  0,
                    0,-30,-20,-10,  0,  0,-10,-20,-30,  0,
                    0,-30,-10, 20, 30, 30, 20,-10,-30,  0,
                    0,-30,-10, 30, 40, 40, 30,-10,-30,  0,
                    0,-30,-10, 30, 40, 40, 30,-10,-30,  0,
                    0,-30,-10, 20, 30, 30, 20,-10,-30,  0,
                    0,-30,-30,  0,  0,  0,  0,-30,-30,  0,
                    0,-50,-30,-30,-30,-30,-30,-30,-50,  0,
                    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
                    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
            };


    private static final double  PAWN_BLOCK_PENALTY = 50;
    private static final double  PAWN_SUPPORT_BONUS = 15;

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
    public static short evaluate(final byte[] board) {
        short boardValue = 0;
        boardValue+=getMaterialValue(board);
        return boardValue;
    }

    private static short getMaterialValue(byte[] board) {
        short boardValue = 0;
        int pieceNumber = 0;
        for(int position = 21;position<99;position++){   //Iterate through board.
            switch (board[position]){      //TODO make this if else because little faster!
                case MoveGenerator.KING_BLACK:
                    boardValue += KING_BLACK_VALUE;
                    pieceNumber++;
                    break;
                case MoveGenerator.QUEEN_BLACK:
                    boardValue += QUEEN_BLACK_VALUE;
                    boardValue -= QueenTable[119 - position];
                    pieceNumber++;
                    break;
                case MoveGenerator.ROOK_BLACK:
                    boardValue += ROOK_BLACK_VALUE;
                    boardValue -= RookTable[119 - position];
                    pieceNumber++;
                    break;
                case MoveGenerator.KNIGHT_BLACK:
                    boardValue -= KnightTable[119-position];    //because the tables always adward positive values for better positions we need to use -=
                    boardValue += KNIGHT_BLACK_VALUE;
                    pieceNumber++;
                    break;
                case MoveGenerator.BISHOP_BLACK:
                    boardValue -= BishopTable[119-position];    //because the tables always adward positive values for better positions we need to use -=
                    boardValue += BISHOP_BLACK_VALUE;
                    pieceNumber++;
                    break;
                case MoveGenerator.PAWN_BLACK:
                    boardValue -= PawnTable[119-position];      //because the tables always adward positive values for better positions we need to use -=
                    boardValue += PAWN_BLACK_VALUE;
                    boardValue += getBlackPawnValue(position,board);
                    pieceNumber++;
                    break;
                case MoveGenerator.KING_WHITE:
                    boardValue += KING_WHITE_VALUE;
                    pieceNumber++;
                    break;
                case MoveGenerator.QUEEN_WHITE:
                    boardValue += QUEEN_WHITE_VALUE;
                    boardValue += QueenTable[position];
                    pieceNumber++;
                    break;
                case MoveGenerator.ROOK_WHITE:
                    boardValue += ROOK_WHITE_VALUE;
                    boardValue += RookTable[position];
                    pieceNumber++;
                    break;
                case MoveGenerator.KNIGHT_WHITE:
                    boardValue += KnightTable[position];
                    boardValue += KNIGHT_WHITE_VALUE;
                    pieceNumber++;
                    break;
                case MoveGenerator.BISHOP_WHITE:
                    boardValue += BishopTable[position];
                    boardValue += BISHOP_WHITE_VALUE;
                    pieceNumber++;
                    break;
                case MoveGenerator.PAWN_WHITE:
                    boardValue += PawnTable[position];
                    boardValue += PAWN_WHITE_VALUE;
                    boardValue += getWhitePawnValue(position,board);
                    pieceNumber++;
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
    private static short getWhitePawnValue(int position, byte[] board) {
        short pawnVal = 0;
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
    private static short getBlackPawnValue(int position, byte[] board) {
        short pawnVal = 0;
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
    private static short getKingSafety(int position, byte[] board){
        short boardValue = 0;
        if(board[position] == MoveGenerator.PAWN_BLACK) boardValue += evaluateBlackPawnShield(position,board);
        if(board[position] == MoveGenerator.PAWN_WHITE) boardValue += evaluateWhitePawnShield(position,board);
        return boardValue;
    }

    private static short evaluateBlackPawnShield(int kingPosition, byte[] board) {
        short shieldValue = 0;
        if(board[kingPosition + SOUTH] == MoveGenerator.PAWN_BLACK){
            shieldValue -= 30;
        }
        if(board[kingPosition+SOUTH + EAST] == MoveGenerator.PAWN_BLACK){
            shieldValue -= 30;
        }
        if(board[kingPosition + SOUTH + WEST]== MoveGenerator.PAWN_BLACK){
            shieldValue -=30;
        }
        if(board[kingPosition + SOUTH + SOUTH] == MoveGenerator.PAWN_BLACK){
            shieldValue -= 20;
        }
        if(board[kingPosition + SOUTH + SOUTH + EAST] == MoveGenerator.PAWN_BLACK){
            shieldValue -= 20;
        }
        if(board[kingPosition + SOUTH + SOUTH + WEST] == MoveGenerator.PAWN_BLACK){
            shieldValue -= 20;
        }
        // it is not good to have three pawns in a row in front of the king or no pawn in front
        if(board[kingPosition + SOUTH] == board[kingPosition + SOUTH + WEST] & board[kingPosition + SOUTH + WEST] == board[kingPosition + SOUTH + EAST]) {
            shieldValue += 45;
        }


        return shieldValue;
    }

    private static short evaluateWhitePawnShield(int kingPosition, byte[] board) {
        short shieldValue = 0;

        if(board[kingPosition + NORTH] == MoveGenerator.PAWN_WHITE){
            shieldValue += 30;
        }
        if(board[kingPosition+NORTH + EAST] == MoveGenerator.PAWN_WHITE){
            shieldValue += 30;
        }
        if(board[kingPosition + NORTH + WEST]== MoveGenerator.PAWN_WHITE){
            shieldValue +=30;
        }
        if(board[kingPosition + NORTH + NORTH] == MoveGenerator.PAWN_WHITE){
            shieldValue += 20;
        }
        if(board[kingPosition + NORTH + NORTH + EAST] == MoveGenerator.PAWN_WHITE){
            shieldValue += 20;
        }
        if(board[kingPosition + NORTH + NORTH + WEST] == MoveGenerator.PAWN_WHITE){
            shieldValue += 20;
        }
        // it is not good to have three pawns in a row in front of the king or no pawn in front
        if(board[kingPosition + NORTH] == board[kingPosition + NORTH + WEST] & board[kingPosition + NORTH + WEST] == board[kingPosition + SOUTH + EAST]) {
            shieldValue -= 45;
        }
        return shieldValue;
    }

}
