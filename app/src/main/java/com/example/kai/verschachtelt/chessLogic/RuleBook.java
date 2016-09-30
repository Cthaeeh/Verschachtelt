package com.example.kai.verschachtelt.chessLogic;


import com.example.kai.verschachtelt.graphics.VictoryScreenGraphic;
import com.example.kai.verschachtelt.chessLogic.Chessman.Color;

import static java.lang.Math.signum;

/**
 * Created by Kai on 10.08.2016.
 * this class can look for a selectedPosition, where the chessman on the this position can move to.
 * it takes a chessman array e.g. a board and a selected position and tells you where it is possible to move.
 */
public class RuleBook {

    private boolean[] possibleMoves = new boolean[64];

    /**
     * Calculates the possible move destinations for the chessman in the selectedPosition,
     * @param selectedPosition  the position of the chessman you want to look for where it can move.
     * @param board             the current state of the board the chessman is on
     * @return                  a array of boolean values where the chessman can possibly move.
     */
    public boolean[] getPossibleMoves(int selectedPosition, final Chessman[] board){
        possibleMoves = new boolean[64];
        if(board[selectedPosition]==null){         //If you try to move from an empty Position.
            return possibleMoves;
        }
        getPieceSpecificMoves(board, selectedPosition);
        removeFriendlyFireMoves(board[selectedPosition].getColor(),board);
        if(board[selectedPosition].getPiece()!= Chessman.Piece.KNIGHT)collisionDetection(selectedPosition,board); //Check collisions for chessman that arent horses.
        return CheckTester.removeSuicidalMoves(selectedPosition,Chessman.deepCopy(board),possibleMoves);
    }

    /**
     * Calculates all possible Moves for the player with the color of parameter color.
     * Doesn´t check for suicidal Moves.
     * @param color
     * @param board the current state of the board.
     * @return a boolean[64] array with all possible moves marked.
     */
    public boolean[] getAllPossibleMoves(Color color, Chessman[] board) {
        boolean[] allPossibleMoves = new boolean[64];
        for(int i = 0;i<64;i++){
            if(board[i]!=null && board[i].getColor() ==color){
                getPieceSpecificMoves(board,i);
                removeFriendlyFireMoves(board[i].getColor(),board);
                if(board[i].getPiece()!= Chessman.Piece.KNIGHT)collisionDetection(i,board);
                allPossibleMoves = combine(allPossibleMoves,possibleMoves);
            }
        }
        return allPossibleMoves;
    }

    private void collisionDetection(int selectedPosition, Chessman[] board) {
        for(int i = 0; i<64;i++){
            if(possibleMoves[i]){
                possibleMoves[i]= isPathFree(selectedPosition,i,board);
            }
        }
    }

    /**
     * The method checks if the path from "from" to "to" is free
     * @param from  the position of the selected chessman
     * @param to    where it wants to move
     * @return      when blocked false, otherwise true
     */
    private boolean isPathFree(int from, int to,  Chessman[] board) {
        int xDirection = (int) signum(to%8-from%8);
        int yDirection = (int) signum(to/8-from/8);

        int xCurrent =  (from%8+xDirection);
        int yCurrent =  (from/8+yDirection);

        int xDest =  (to%8);
        int yDest =  (to/8);
        int position = xCurrent+(yCurrent*8);

        while(!(xCurrent==xDest && yCurrent==yDest) && position<64 && position>-1) {
            if (board[xCurrent+(yCurrent*8)]!=null){
                return false;
            }
            xCurrent =  (xCurrent + xDirection);
            yCurrent =  (yCurrent + yDirection);
            position =  xCurrent+(yCurrent*8);
        }
        return true;
    }

    /**
     * The method ensures that you can not beat your own figures
     */
    private void removeFriendlyFireMoves(Chessman.Color color, Chessman[] board) {
        for (int i = 0;i<64;i++){
            if(board[i]!=null){  //Check if you want to move on your own piece
                if(color==board[i].getColor())possibleMoves[i]=false;
            }
        }
    }

    /**
     * Depending on the chessman on the selectedPosition the correct Method is called.
     */
    private void getPieceSpecificMoves(Chessman[] board, int selectedPosition) {
        resetPossibleMoves();
        switch (board[selectedPosition].getPiece()){
            case ROOK:
                possibleMoves = getPossibleRookMoves(selectedPosition);
                break;
            case KNIGHT:
                possibleMoves = getPossibleKnightMoves(selectedPosition);
                break;
            case BISHOP:
                possibleMoves = getPossibleBishopMoves(selectedPosition);
                break;
            case QUEEN:
                possibleMoves = getPossibleQueenMoves(selectedPosition);
                break;
            case KING:
                possibleMoves = getPossibleKingMoves(selectedPosition);
                break;
            case PAWN:
                switch (board[selectedPosition].getColor()){
                    case WHITE:
                        possibleMoves = getPossibleLowerPawnMoves(selectedPosition,board);
                        break;
                    case BLACK:
                        possibleMoves = getPossibleUpperPawnMoves(selectedPosition,board);
                        break;
                }
            default:
        }
    }

    private boolean[] getPossibleRookMoves(int selectedPosition){
        resetPossibleMoves();
        for(int i=0;i<64;i++){
            if(i!=selectedPosition){
                if(i%8==selectedPosition%8 || i/8==selectedPosition/8){   //If squareStates is in the same row or column.
                    possibleMoves[i]=true;
                }
            }
        }
        return possibleMoves;
    }

    private boolean[] getPossibleKnightMoves(int selectedPosition){
        resetPossibleMoves();
        if(selectedPosition+10<64&&selectedPosition%8<6 )possibleMoves[selectedPosition+10]=true;
        if(selectedPosition+6<64 &&selectedPosition%8>1 )possibleMoves[selectedPosition+6]=true;
        if(selectedPosition+17<64&&selectedPosition%8<7 )possibleMoves[selectedPosition+17]=true;
        if(selectedPosition+15<64&&selectedPosition%8>0 )possibleMoves[selectedPosition+15]=true;
        if(selectedPosition-10>=0 &&selectedPosition%8>1 )possibleMoves[selectedPosition-10]=true;
        if(selectedPosition-6>0  &&selectedPosition%8<6 )possibleMoves[selectedPosition-6]=true;
        if(selectedPosition-17>=0 &&selectedPosition%8>0 )possibleMoves[selectedPosition-17]=true;
        if(selectedPosition-15>0 &&selectedPosition%8<7 )possibleMoves[selectedPosition-15]=true;
        return possibleMoves;
    }

    private boolean[] getPossibleBishopMoves(int selectedPosition){
        resetPossibleMoves();
        for (int i =0;i<64;i++){
            //If the deltaX (the distance it moves in x-Direction) equals deltaY then it is a legit move
            if(Math.abs(i%8-selectedPosition%8)==Math.abs(i/8-selectedPosition/8))possibleMoves[i]=true;
            if(i==selectedPosition)possibleMoves[i]=false;
        }
        return possibleMoves;
    }

    private boolean[] getPossibleQueenMoves(int selectedPosition){
        boolean[] rookMoves = getPossibleRookMoves(selectedPosition).clone();
        boolean[] bishopMoves = getPossibleBishopMoves(selectedPosition).clone();
        resetPossibleMoves();
        for (int i = 0;i<64;i++){
            //Just overlap the possible rook and bishop moves.
            if(rookMoves[i]||bishopMoves[i])possibleMoves[i]=true;
        }
        return possibleMoves;
    }

    private boolean[] getPossibleKingMoves(int selectedPosition){
        resetPossibleMoves();   //Disallow glitching, and moves out of the board.
        if(selectedPosition+1<64 && selectedPosition/8 == (selectedPosition+1)/8)possibleMoves[selectedPosition+1]=true;
        if(selectedPosition-1>=0 && selectedPosition/8 == (selectedPosition-1)/8)possibleMoves[selectedPosition-1]=true;
        if(selectedPosition+8<64)possibleMoves[selectedPosition+8]=true;
        if(selectedPosition-8>=0)possibleMoves[selectedPosition-8]=true;

        if(selectedPosition+9<64 && selectedPosition/8 == ((selectedPosition+9)/8)-1 )possibleMoves[selectedPosition+9]=true;
        if(selectedPosition-9>=0 && selectedPosition/8 == ((selectedPosition-9)/8)+1 )possibleMoves[selectedPosition-9]=true;
        if(selectedPosition+7<64&&selectedPosition!=0 && selectedPosition/8 == ((selectedPosition+7)/8)-1 )possibleMoves[selectedPosition+7]=true;
        if(selectedPosition-7>=0&&selectedPosition%8!=7)possibleMoves[selectedPosition-7]=true;
        return possibleMoves;
    }

    /**
     * Calculates the move possibilitys for the pawn (that spawned in upper area of the boardCurrent)
     * @return the possible destinations for the pawn to move.
     */
    private boolean[] getPossibleUpperPawnMoves(int selectedPosition, Chessman[] board){
        resetPossibleMoves();
        if(selectedPosition+8<64&&board[selectedPosition+8]==null)possibleMoves[selectedPosition+8]=true;  //Can always move one step forward.
        if(selectedPosition+9<64&&board[selectedPosition+9]!=null&&((selectedPosition+9)/8==(selectedPosition/8)+1))possibleMoves[selectedPosition+9]=true;   //IF there is a piece he can move diagonal
        if(selectedPosition+7<64&&board[selectedPosition+7]!=null&&((selectedPosition+7)/8==(selectedPosition/8)+1))possibleMoves[selectedPosition+7]=true;
        if(selectedPosition/8==1&&board[selectedPosition+16]==null)possibleMoves[selectedPosition+16]=true; //If he is on starting Position he can move 2
        return possibleMoves;
    }

    /**
     * Calculates the move possibilitys for the pawn (that spawned in lower area of the boardCurrent)
     * @return the possible destinations for the pawn to move.
     */
    private boolean[] getPossibleLowerPawnMoves(int selectedPosition, Chessman[] board){
        resetPossibleMoves();
        if(selectedPosition-8>=0&&board[selectedPosition-8]==null)possibleMoves[selectedPosition-8]=true;  //Can always move one step forward.
        if(selectedPosition-9>=0&&board[selectedPosition-9]!=null&&((selectedPosition-9)/8==(selectedPosition/8)-1))possibleMoves[selectedPosition-9]=true;     //IF there is a piece he can move diagonal
        if(selectedPosition-7>=0&&board[selectedPosition-7]!=null&&((selectedPosition-7)/8==(selectedPosition/8)-1))possibleMoves[selectedPosition-7]=true;
        if(selectedPosition/8==6&&board[selectedPosition-16]==null)possibleMoves[selectedPosition-16]=true; //If he is on starting Position he can move 2
        return possibleMoves;
    }

    private void resetPossibleMoves() {
        for (int i=0;i<64;i++){
            possibleMoves[i]=false;
        }
    }

    public VictoryScreenGraphic.VictoryState getWinner(Chessman[] board, Color playerOnTurn) {
        if(CheckTester.isMate(Chessman.Color.BLACK,board)) return VictoryScreenGraphic.VictoryState.WHITEWIN;
        if(CheckTester.isMate(Chessman.Color.WHITE,board)) return VictoryScreenGraphic.VictoryState.BLACKWIN;
        if(CheckTester.isDraw(board,playerOnTurn))return VictoryScreenGraphic.VictoryState.DRAW;
        return null;
    }

    /**
     * Adds (combines) two boolean array in a way: result[i] = array1[i]||array2[i] (OR)
     * @param array1 the first array to combine
     * @param array2 the second array to combine
     * @return
     */
    private boolean[] combine(boolean[] array1, boolean[] array2) {
        if(array1.length!=array2.length)throw new IllegalArgumentException("Arrays have different sizes");
        //Combine both possibleDestinations
        for(int i = 0;i<array1.length;i++){
            if(array1[i])array2[i]=true;
        }
        return array2.clone();
    }

}
