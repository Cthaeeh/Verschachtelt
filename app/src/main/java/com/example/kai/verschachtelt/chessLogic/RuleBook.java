package com.example.kai.verschachtelt.chessLogic;


/**
 * Created by Kai on 10.08.2016.
 * this class can look for a selectedPosition, where the chessman on the this position can move to.
 * it takes a chessman array e.g. a board and a selected position and tells you where it is possible to move.
 */
public class RuleBook {

    private boolean[] possibleMoves = new boolean[64];
    private int selectedPosition;
    private Chessman[] board;

    /**
     *
     * @param selectedPosition  the position of the chessman you want to look for where it can move.
     * @param board             the current state of the board the chessman is on
     * @return                  a array of boolean values where the chessman can possibly move.
     */
    public boolean[] getPossibleMoves(int selectedPosition, Chessman[] board){
        //Copy stuff for easier access
        this.selectedPosition = selectedPosition;
        this.board = board;
        getPieceSpecificMoves();
        removeFriendlyFireMoves();
        //Check collisions for chessman that arent horses.
        if(board[selectedPosition].getPiece()!= Chessman.Piece.KNIGHT)collisionDetection();
        return possibleMoves;
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
    private boolean isPathFree(int from, int to) {
        byte xDirection = (byte)Math.signum(to%8-from%8);
        byte yDirection = (byte)Math.signum(to/8-from/8);

        byte xCurrent = (byte) (from%8+xDirection);
        byte yCurrent = (byte) (from/8+yDirection);

        byte xDest = (byte) (to%8);
        byte yDest = (byte) (to/8);

        while(!(xCurrent==xDest && yCurrent==yDest)) {
            if (board[xCurrent+(yCurrent*8)]!=null){
                return false;
            }
            xCurrent = (byte) (xCurrent + xDirection);
            yCurrent = (byte) (yCurrent + yDirection);
        }
        return true;
    }

    /**
     * The method ensures that you can not beat your own figures
     */
    private void removeFriendlyFireMoves() {
        for (int i = 0;i<64;i++){
            if(board[i]!=null){  //Check if you want to move on your own piece
                if(board[selectedPosition].getColor()==board[i].getColor())possibleMoves[i]=false;
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

    private void resetPossibleMoves() {
        for (int i=0;i<64;i++){
            possibleMoves[i]=false;
        }
    }

    public Chessman.Color getWinner(Chessman[] board) {
        boolean whiteWin = true;
        boolean blackWin = true;
        for (int i = 0;i<64;i++){
            //If the King of the other player is still there you have not won.
            if(board[i]!=null){
                if(board[i].getPiece()== Chessman.Piece.KING && board[i].getColor() == Chessman.Color.BLACK) whiteWin = false;
                if(board[i].getPiece()== Chessman.Piece.KING && board[i].getColor() == Chessman.Color.WHITE) blackWin = false;
            }
        }
        if(whiteWin&&!blackWin)return Chessman.Color.WHITE;
        if(!whiteWin&&blackWin)return Chessman.Color.BLACK;
        return null;
    }

}
