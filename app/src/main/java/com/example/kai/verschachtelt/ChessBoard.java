package com.example.kai.verschachtelt;

/**
 * Created by Kai on 09.08.2016.
 * A Class for representing the chess board outside of the AI-Part.
 * No Chess Logic, Graphics here.
 */
public class ChessBoard {

    private chessMan[] board;
    private squareState[] boardRepresentingStates;

    /**
        Representation of all chess man as enums
     **/
    public enum chessMan{
        WHITE_ROOK,  WHITE_KNIGHT,  WHITE_BISHOP,  WHITE_QUEEN,  WHITE_KING,  WHITE_PAWN,
        BLACK_ROOK,  BLACK_KNIGHT,  BLACK_BISHOP,  BLACK_QUEEN,  BLACK_KING,  BLACK_PAWN
    }

    /**
     * Representation of the states of a square. E.g it can be selected, it is possible to move there from a selected field ...
     */
    public enum squareState{
        NORMAL,SELECTED,POSSIBLE,POSSIBLE_KILL
    }

    public ChessBoard (){
        board = new chessMan[64];
        board = getStandartSetup();
        boardRepresentingStates = new squareState[64];
        boardRepresentingStates = getNormalState();
    }

    private squareState[] getNormalState() {
        squareState[] allSquareStates = new squareState[64];
        for (int i =0;i<64;i++){
            allSquareStates[i]=squareState.NORMAL;
        }
        return allSquareStates;
    }

    private chessMan[] getStandartSetup(){
        chessMan[] standartBoard = {chessMan.WHITE_ROOK,chessMan.WHITE_KNIGHT,chessMan.WHITE_BISHOP,chessMan.WHITE_QUEEN,chessMan.WHITE_KING,chessMan.WHITE_BISHOP,chessMan.WHITE_KNIGHT,chessMan.WHITE_ROOK,
                                    chessMan.WHITE_PAWN,chessMan.WHITE_PAWN  ,chessMan.WHITE_PAWN  ,chessMan.WHITE_PAWN ,chessMan.WHITE_PAWN,chessMan.WHITE_PAWN  ,chessMan.WHITE_PAWN  ,chessMan.WHITE_PAWN,
                                    null               ,null                 ,null                 ,null                ,null               ,null                 ,null                 ,null,
                                    null               ,null                 ,null                 ,null                ,null               ,null                 ,null                 ,null,
                                    null               ,null                 ,null                 ,null                ,null               ,null                 ,null                 ,null,
                                    null               ,null                 ,null                 ,null                ,null               ,null                 ,null                 ,null,
                                    chessMan.BLACK_PAWN,chessMan.BLACK_PAWN  ,chessMan.BLACK_PAWN  ,chessMan.BLACK_PAWN ,chessMan.BLACK_PAWN,chessMan.BLACK_PAWN  ,chessMan.BLACK_PAWN  ,chessMan.BLACK_PAWN,
                                    chessMan.BLACK_ROOK,chessMan.BLACK_KNIGHT,chessMan.BLACK_BISHOP,chessMan.BLACK_QUEEN,chessMan.BLACK_KING,chessMan.BLACK_BISHOP,chessMan.BLACK_KNIGHT,chessMan.BLACK_ROOK};
        return standartBoard;
    }
    public chessMan getChessManAt(int position){
        return board[position];
    }
    public chessMan getChessManAt(int x, int y){
        return board[x+8*y];
    }
    public squareState getSquareStateAt(int position){
        return boardRepresentingStates[position];
    }
}
