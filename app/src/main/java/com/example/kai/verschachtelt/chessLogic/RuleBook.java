package com.example.kai.verschachtelt.chessLogic;


import com.example.kai.verschachtelt.graphics.VictoryScreenGraphic;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;
import static java.lang.Math.signum;

/**
 * Created by Kai on 10.08.2016.
 * this class can look for a selectedPosition, where the chessman on the this position can move to.
 * it takes a chessman array e.g. a board and a selected position and tells you where it is possible to move.
 */
public class RuleBook {

    private boolean[] possibleMoves = new boolean[64];
    private int selectedPosition;
    private Chessman[] board;

    // new Rulebook beginning

    private boolean isDiagonalPathfree(int from, int to){
        // moves from bottom left to top right
        if((to-from) % 7 == 0 & (to-from) < 0 ) {
            int currentPos = from - 7;
            while(currentPos != to){
                if(board[currentPos] != null) return false;
                currentPos = currentPos - 7;
            }
            return true;
        }
        // moves from top right to bottom left
        if((to - from) % 7 == 0 & (to - from) > 0){
            int currentPos = from + 7;
            while(currentPos != to){
                if(board[currentPos] != null) return false;
                currentPos = currentPos + 7;
            }
            return true;
        }
        //moves from bottom right to top left
        if((to-from) % 9 == 0 & (to - from) < 0){
            int currentPos = from - 9;
            while(currentPos != to){
                if(board[currentPos] != null) return false;
                currentPos = currentPos - 9;
            }
            return true;
        }
        // moves from top left to bottom right
        else{// if((to-from) % 9 == 0 & (to - from) > 0){
            int currentPos = from + 9;
            while(currentPos != to){
                if(board[currentPos] != null) return false;
                currentPos = currentPos + 9;
            }
            return true;
        }
    }

    private boolean isSidePathFree(int from,int to) {
        // move right
        if (to > from ) {
            int currentPos = from + 1;
            while (currentPos != to) {
                if (board[currentPos] != null) return false;
                currentPos++;
            }
            return true;
        }
        // move left
        else {
            int currentPos = from-1;
            while (currentPos != to) {
                if (board[currentPos] != null) return false;
                currentPos--;
            }
            return true;
        }
    }

    private boolean isUpDownPathFree(int from, int to){
        // move down
        if(to > from){
            int currentPos = from + 8;
            while(currentPos != to){
                if(board[currentPos] != null) return false;
                currentPos = currentPos + 8;
            }
            return true;
        }
        else{
            int currentPos = from - 8;
            while(currentPos != to){
                if(board[currentPos] != null) return false;
                currentPos = currentPos - 8;
            }
            return true;
        }
    }

    private boolean isPathFree(int from, int to) {
        byte xDirection = (byte) signum(to%8-from%8);
        byte yDirection = (byte) signum(floor(to/8)-floor(from/8));

        if(xDirection == 0) return isUpDownPathFree(from,to);
        if(yDirection == 0) return isSidePathFree(from,to);
        else                return isDiagonalPathfree(from,to);
    }



    // new RuleBook ending


    /**
     * Calculates the possible move destinations for the chessman in the selectedPosition,
     * @param selectedPosition  the position of the chessman you want to look for where it can move.
     * @param board             the current state of the board the chessman is on
     * @return                  a array of boolean values where the chessman can possibly move.
     */
    public boolean[] getPossibleMoves(int selectedPosition, Chessman[] board){
        this.selectedPosition = selectedPosition;
        this.board = board;
        if(board[selectedPosition]==null){
            resetPossibleMoves();
            return possibleMoves;
        }
        //Copy stuff for easier access
        getPieceSpecificMoves();
        removeFriendlyFireMoves(board[selectedPosition].getColor());
        //Check collisions for chessman that arent horses.
        if(board[selectedPosition].getPiece()!= Chessman.Piece.KNIGHT)collisionDetection();
        possibleMoves = CheckTester.removeSuicidalMoves(selectedPosition,board,possibleMoves);
        return possibleMoves;
    }

    /**
     * Calculates the possible move destinations for the chessman in the selectedPosition,
     * BUT whithout checking if the moves would result in check (which is illegal).
     * @param selectedPosition  the position of the chessman you want to look for where it can move.
     * @param board             the current state of the board the chessman is on
     * @return                  a array of boolean values where the chessman can possibly move.
     */
    public boolean[] getPossibleMovesLight(int selectedPosition, Chessman[] board) {
        try {
            resetPossibleMoves();
            this.selectedPosition = selectedPosition;
            this.board = board;
            Chessman asshole = board[selectedPosition];
            if(board[selectedPosition]==null){
                resetPossibleMoves();
                return possibleMoves;
            }else {
                //Copy stuff for easier access
                getPieceSpecificMoves();
                removeFriendlyFireMoves(asshole.getColor());
                //Check collisions for chessman that arent horses.
                if(asshole.getPiece()!= Chessman.Piece.KNIGHT)collisionDetection();
                return possibleMoves;
            }
        }catch (Exception e){
            e.printStackTrace();
            return possibleMoves;
        }

    }

    private void collisionDetection() {
        for(int i = 0; i<64;i++){
            if(possibleMoves[i]){
                possibleMoves[i]= isPathFree(selectedPosition,i);
            }
        }
    }

    /**
     * The method checks if the path from "from" to "to" is free
     * @param from  the position of the selected chessman
     * @param to    where it wants to move
     * @return      when blocked false, otherwise true
     */


    /**
     * The method ensures that you can not beat your own figures
     */
    private void removeFriendlyFireMoves(Chessman.Color color) {
        for (int i = 0;i<64;i++){
            if(board[i]!=null){  //Check if you want to move on your own piece
                if(color==board[i].getColor())possibleMoves[i]=false;
            }
        }
    }

    /**
     * Depending on the chessman on the selectedPosition the correct Method is called.
     */
    private void getPieceSpecificMoves() {
        switch (board[selectedPosition].getPiece()){
            case ROOK:
                possibleMoves = getPossibleRookMoves();
                break;
            case KNIGHT:
                possibleMoves = getPossibleKnightMoves();
                break;
            case BISHOP:
                possibleMoves = getPossibleBishopMoves();
                break;
            case QUEEN:
                possibleMoves = getPossibleQueenMoves();
                break;
            case KING:
                possibleMoves = getPossibleKingMoves();
                break;
            case PAWN:
                switch (board[selectedPosition].getColor()){
                    case WHITE:
                        possibleMoves = getPossibleLowerPawnMoves();
                        break;
                    case BLACK:
                        possibleMoves = getPossibleUpperPawnMoves();
                        break;
                }
            default:
        }
    }

    private boolean[] getPossibleRookMoves(){
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

    private boolean[] getPossibleKnightMoves(){
        resetPossibleMoves();
        if(selectedPosition+10<64&&selectedPosition%8<6 )possibleMoves[selectedPosition+10]=true;
        if(selectedPosition+6<64 &&selectedPosition%8>1 )possibleMoves[selectedPosition+6]=true;
        if(selectedPosition+17<64&&selectedPosition%8<7 )possibleMoves[selectedPosition+17]=true;
        if(selectedPosition+15<64&&selectedPosition%8>0 )possibleMoves[selectedPosition+15]=true;
        if(selectedPosition-10>0 &&selectedPosition%8>1 )possibleMoves[selectedPosition-10]=true;
        if(selectedPosition-6>0  &&selectedPosition%8<6 )possibleMoves[selectedPosition-6]=true;
        if(selectedPosition-17>0 &&selectedPosition%8>0 )possibleMoves[selectedPosition-17]=true;
        if(selectedPosition-15>0 &&selectedPosition%8<7 )possibleMoves[selectedPosition-15]=true;
        return possibleMoves;
    }

    private boolean[] getPossibleBishopMoves(){
        resetPossibleMoves();
        for (int i =0;i<64;i++){
            //If the deltaX (the distance it moves in x-Direction) equals deltaY then it is a legit move
            if(Math.abs(i%8-selectedPosition%8)==Math.abs(i/8-selectedPosition/8))possibleMoves[i]=true;
            if(i==selectedPosition)possibleMoves[i]=false;
        }
        return possibleMoves;
    }

    private boolean[] getPossibleQueenMoves(){
        boolean[] rookMoves = getPossibleRookMoves().clone();
        boolean[] bishopMoves = getPossibleBishopMoves().clone();
        resetPossibleMoves();
        for (int i = 0;i<64;i++){
            //Just overlap the possible rook and bishop moves.
            if(rookMoves[i]||bishopMoves[i])possibleMoves[i]=true;
        }
        return possibleMoves;
    }

    private boolean[] getPossibleKingMoves(){
        resetPossibleMoves();
            if(selectedPosition+1<64)possibleMoves[selectedPosition+1]=true;
            if(selectedPosition-1>=0)possibleMoves[selectedPosition-1]=true;
            if(selectedPosition+8<64)possibleMoves[selectedPosition+8]=true;
            if(selectedPosition-8>=0)possibleMoves[selectedPosition-8]=true;

            if(selectedPosition+9<64)possibleMoves[selectedPosition+9]=true;
            if(selectedPosition-9>=0)possibleMoves[selectedPosition-9]=true;
            if(selectedPosition+7<64&&selectedPosition!=0)possibleMoves[selectedPosition+7]=true;
            if(selectedPosition-7>=0&&selectedPosition%8!=7)possibleMoves[selectedPosition-7]=true;
        return possibleMoves;
    }

    /**
     * Calculates the move possibilitys for the pawn (that spawned in upper area of the boardCurrent)
     * @return the possible destinations for the pawn to move.
     */
    private boolean[] getPossibleUpperPawnMoves(){
        resetPossibleMoves();
        if(selectedPosition+8<64&&board[selectedPosition+8]==null)possibleMoves[selectedPosition+8]=true;  //Can always move one step forward.
        if(selectedPosition+9<64&&board[selectedPosition+9]!=null)possibleMoves[selectedPosition+9]=true;   //IF there is a piece he can move diagonal
        if(selectedPosition+7<64&&board[selectedPosition+7]!=null)possibleMoves[selectedPosition+7]=true;
        if(selectedPosition/8==1&&board[selectedPosition+16]==null)possibleMoves[selectedPosition+16]=true; //If he is on starting Position he can move 2
        return possibleMoves;
    }

    /**
     * Calculates the move possibilitys for the pawn (that spawned in lower area of the boardCurrent)
     * @return the possible destinations for the pawn to move.
     */
    private boolean[] getPossibleLowerPawnMoves(){
        resetPossibleMoves();
        if(selectedPosition-8>=0&&board[selectedPosition-8]==null)possibleMoves[selectedPosition-8]=true;  //Can always move one step forward.
        if(selectedPosition-9>=0&&board[selectedPosition-9]!=null)possibleMoves[selectedPosition-9]=true;     //IF there is a piece he can move diagonal
        if(selectedPosition-7>=0&&board[selectedPosition-7]!=null)possibleMoves[selectedPosition-7]=true;
        if(selectedPosition/8==6&&board[selectedPosition-16]==null)possibleMoves[selectedPosition-16]=true; //If he is on starting Position he can move 2
        return possibleMoves;
    }

    //COMMENT TO TEST BRANCH

    private void resetPossibleMoves() {
        for (int i=0;i<64;i++){
            possibleMoves[i]=false;
        }
    }

    public VictoryScreenGraphic.VictoryState  getWinner(Chessman[] board) {
        if(CheckTester.isMate(Chessman.Color.BLACK,board)) return VictoryScreenGraphic.VictoryState.WHITEWIN;
        if(CheckTester.isMate(Chessman.Color.WHITE,board)) return VictoryScreenGraphic.VictoryState.BLACKWIN;
        return null;
    }


}
