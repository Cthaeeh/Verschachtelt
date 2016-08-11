package com.example.kai.verschachtelt.pvAIGame.chess_AI;

import com.example.kai.verschachtelt.chessLogic.ChessBoardComplex;
import com.example.kai.verschachtelt.chessLogic.Chessman;

/**
 * Created by Kai on 10.08.2016.
 * This is the starting point for all AI development.
 *
 */
public class AI {

    private final  int difficulty;
    private final  Chessman.Color aiColor;  //The color of the pieces the ai moves
    private byte[] byteBoard ;           //Inside the ai a board is represented by byte array

    private static final byte VALUE_KING_WHITE = 100;
    private static final byte VALUE_QUEEN_WHITE = 9;
    private static final byte VALUE_ROOK_WHITE =5;
    private static final byte VALUE_KNIGHT_WHITE =4;
    private static final byte VALUE_BISHOP_WHITE =3;
    private static final byte VALUE_PAWN_WHITE =1;

    private static final byte VALUE_KING_BLACK = -100;
    private static final byte VALUE_QUEEN_BLACK = -9;
    private static final byte VALUE_ROOK_BLACK = -5;
    private static final byte VALUE_KNIGHT_BLACK = -4;
    private static final byte VALUE_BISHOP_BLACK = -3;
    private static final byte VALUE_PAWN_BLACK = 1;

    /**
     * @param difficulty    How good the ai plays.
     * @param aiColor       The color of the chessman the ai will move.
     */
    public AI(int difficulty, Chessman.Color aiColor){
        this.difficulty = difficulty;
        this.aiColor = aiColor;
    }

    /**
     * The ai calculates a move, makes it in the board, and gives it back.
     * @param board The board you want the ai to calculate a move for.
     * @return
     */
    public ChessBoardComplex calculateMove(ChessBoardComplex board) {
        byteBoard = toByteArray(board);
        board.handleMoveFromTo(1,2);
        return board;
    }

    /**
     * The method takes a board object and translates it to a byte array of length 64.
     * to represent different chessmen it uses constants.
     * @param board
     * @return
     */
    public byte[] toByteArray(ChessBoardComplex board) {
        byte[] byteBoard = new byte[64];
        for (int i = 0;i<64;i++){
            if(board.getChessManAt(i)!=null){
                Chessman chessman = board.getChessManAt(i);
                switch (chessman.getColor()){
                    case BLACK:
                        switch (chessman.getPiece()){
                            case KING:  byteBoard[i] = VALUE_KING_BLACK;break;
                            case QUEEN: byteBoard[i] = VALUE_QUEEN_BLACK;break;
                            case ROOK:  byteBoard[i] = VALUE_ROOK_BLACK;break;
                            case KNIGHT:byteBoard[i] = VALUE_KNIGHT_BLACK;break;
                            case BISHOP:byteBoard[i] = VALUE_BISHOP_BLACK;break;
                            case PAWN:  byteBoard[i] = VALUE_PAWN_BLACK;break;
                        }
                        break;
                    case WHITE:
                        switch (chessman.getPiece()){
                            case KING:  byteBoard[i] = VALUE_KING_WHITE;break;
                            case QUEEN: byteBoard[i] = VALUE_QUEEN_WHITE;break;
                            case ROOK:  byteBoard[i] = VALUE_ROOK_WHITE;break;
                            case KNIGHT:byteBoard[i] = VALUE_KNIGHT_WHITE;break;
                            case BISHOP:byteBoard[i] = VALUE_BISHOP_WHITE;break;
                            case PAWN:  byteBoard[i] = VALUE_PAWN_WHITE;break;
                        }
                        break;
                }
            }else {
                byteBoard[i]=0;
            }
        }
        return byteBoard;
    }

    public Chessman.Color getColor() {
        return aiColor;
    }
}
