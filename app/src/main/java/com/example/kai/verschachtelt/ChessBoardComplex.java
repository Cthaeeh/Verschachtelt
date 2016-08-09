package com.example.kai.verschachtelt;

/**
 * Created by Kai on 09.08.2016.
 * This class extends the normal Chessboard class and adds basic move funtionality, possible move functionality.
 */
public class ChessBoardComplex extends ChessBoardSimple{
    private int moveFrom = -1;  //position to move a chessman from. By default no real position.

    public void setMoveFrom(int position) {
        moveFrom = position;
        if(position>=0&&position<64&&board[position]!=null){        //If there is a chessman at this square
            markPossibleMoves();                                    //Then mark possible moves.
        }
    }

    private void markPossibleMoves() {
        switch (board[moveFrom]){
            case BLACK_ROOK:
            case WHITE_ROOK:
                markPossibleRookMoves();
                break;
            case BLACK_KNIGHT:
            case WHITE_KNIGHT:

                break;
            case BLACK_BISHOP:
            case WHITE_BISHOP:

                break;
            case BLACK_QUEEN:
            case WHITE_QUEEN:

                break;
            case BLACK_KING:
            case WHITE_KING:

                break;
            case BLACK_PAWN:

                break;
            case WHITE_PAWN:

                break;
        }
    }

    private void markPossibleRookMoves() {
        for(int i=0;i<64;i++){                                      //Nested if statements TODO remove nested if statements
            if(i!=moveFrom){
                if(i%8==moveFrom%8){
                    boardRepresentingStates[i]=SquareState.POSSIBLE;
                    if(board[i]!=null)boardRepresentingStates[i]=SquareState.POSSIBLE_KILL;
                }
                if(i/8==moveFrom/8){
                    boardRepresentingStates[i]=SquareState.POSSIBLE;
                    if(board[i]!=null)boardRepresentingStates[i]=SquareState.POSSIBLE_KILL;
                }
            }
        }
    }

    public void moveTo(int position){
        if(moveFrom>=0){                    //If we try to move from a legit position
            board[position]=board[moveFrom];//Set the chessman to its new position.
            board[moveFrom]=null;           //Remove the chessman from its originally square.
        }
        resetFrames();
    }
}
