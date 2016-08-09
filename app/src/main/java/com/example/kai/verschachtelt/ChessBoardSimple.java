package com.example.kai.verschachtelt;

/**
 * Created by Kai on 09.08.2016.
 * A Class for representing the chess board outside of the AI-Part.
 * No Chess Logic, Graphics here.
 */
public class ChessBoardSimple {

    private ChessMan[] board;
    private SquareState[] boardRepresentingStates;

    public void resetFrames() {
        for(int i =0;i<64;i++){
            boardRepresentingStates[i]=SquareState.NORMAL;
        }
    }

    public void setSquareStateAt(int position,SquareState squareState){
        boardRepresentingStates[position]=squareState;
    }
    /**
        Representation of all chess man as enums
     **/
    public enum ChessMan {
        WHITE_ROOK,  WHITE_KNIGHT,  WHITE_BISHOP,  WHITE_QUEEN,  WHITE_KING,  WHITE_PAWN,
        BLACK_ROOK,  BLACK_KNIGHT,  BLACK_BISHOP,  BLACK_QUEEN,  BLACK_KING,  BLACK_PAWN
    }

    /**
     * Representation of the states of a square. E.g it can be selected, it is possible to move there from a selected field ...
     */
    public enum SquareState {
        NORMAL,SELECTED,POSSIBLE,POSSIBLE_KILL
    }

    public ChessBoardSimple(){
        board = new ChessMan[64];
        board = getStandartSetup();
        boardRepresentingStates = new SquareState[64];
        boardRepresentingStates = getNormalState();
    }

    private SquareState[] getNormalState() {
        SquareState[] allSquareStates = new SquareState[64];
        for (int i =0;i<64;i++){
            allSquareStates[i]= SquareState.NORMAL;
        }
        return allSquareStates;
    }

    private ChessMan[] getStandartSetup(){
        ChessMan[] standartBoard = {ChessMan.WHITE_ROOK, ChessMan.WHITE_KNIGHT, ChessMan.WHITE_BISHOP, ChessMan.WHITE_QUEEN, ChessMan.WHITE_KING, ChessMan.WHITE_BISHOP, ChessMan.WHITE_KNIGHT, ChessMan.WHITE_ROOK,
                                    ChessMan.WHITE_PAWN, ChessMan.WHITE_PAWN  , ChessMan.WHITE_PAWN  , ChessMan.WHITE_PAWN , ChessMan.WHITE_PAWN, ChessMan.WHITE_PAWN  , ChessMan.WHITE_PAWN  , ChessMan.WHITE_PAWN,
                                    null               ,null                 ,null                 ,null                ,null               ,null                 ,null                 ,null,
                                    null               ,null                 ,null                 ,null                ,null               ,null                 ,null                 ,null,
                                    null               ,null                 ,null                 ,null                ,null               ,null                 ,null                 ,null,
                                    null               ,null                 ,null                 ,null                ,null               ,null                 ,null                 ,null,
                                    ChessMan.BLACK_PAWN, ChessMan.BLACK_PAWN  , ChessMan.BLACK_PAWN  , ChessMan.BLACK_PAWN , ChessMan.BLACK_PAWN, ChessMan.BLACK_PAWN  , ChessMan.BLACK_PAWN  , ChessMan.BLACK_PAWN,
                                    ChessMan.BLACK_ROOK, ChessMan.BLACK_KNIGHT, ChessMan.BLACK_BISHOP, ChessMan.BLACK_QUEEN, ChessMan.BLACK_KING, ChessMan.BLACK_BISHOP, ChessMan.BLACK_KNIGHT, ChessMan.BLACK_ROOK};
        return standartBoard;
    }
    public ChessMan getChessManAt(int position){
        return board[position];
    }
    public ChessMan getChessManAt(int x, int y){
        return board[x+8*y];
    }
    public SquareState getSquareStateAt(int position){
        return boardRepresentingStates[position];
    }
}
