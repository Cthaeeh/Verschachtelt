package com.example.kai.verschachtelt.chessLogic;

import com.example.kai.verschachtelt.chessLogic.Chessman.Color;

/**
 * Created by Kai on 04.09.2016.
 */
public class CheckTester {

    private static RuleBook ruleBook = new RuleBook();

    /**
     * Removes suicical Moves from the Moves that are otherwise legal.
     * @param selectedPosition the Chessman that was selected by the user.
     * @param currentBoard     the current board.
     * @param possibleMoves    the otherwise legal Moves.
     * @return                 the legal moves where you don´t kill your self.
     */
    public static boolean[] removeSuicidalMoves(int selectedPosition, Chessman[] currentBoard, boolean[] possibleMoves) {
        for(int i = 0; i<64; i++){  //Iterate through fields.
            if(possibleMoves[i]){   //If this is a possible move destination.
                possibleMoves[i] = isLegalMove(selectedPosition,i,currentBoard);    //check if its a suicidal move.
            }
        }
        return possibleMoves;
    }

    /**
     * Method checks if a Move
     * (creates a hypothetical board were the move was really made)
     * @param from
     * @param to
     * on the
     * @param currentBoard
     * is legal.
     * @return
     */
    private static boolean isLegalMove(int from, int to, Chessman[] currentBoard) {
        Chessman[] boardAfterMove = Chessman.deepCopy(currentBoard);    //Very important, make a deep copy.
        boardAfterMove[to]=boardAfterMove[from];    //Make the move
        boardAfterMove[from]=null;
        return !isCheck(boardAfterMove[to].getColor(),boardAfterMove);  //If it is check afterwards it was a illegal move.
    }

    /**
     *
     * @param color the color of the player we want to know if he is in check.
     * @param board
     * @return
     */
    private static boolean isCheck(Color color, Chessman[] board) {
        for(int i = 0;i<64;i++){
            if(board[i]!=null){
                if(board[i].getPiece()== Chessman.Piece.KING && board[i].getColor()== color){   //Search for the King
                    return squareInDanger(board,i);                                             //If King is in danger it is check.
                }
            }
        }
        return false;
    }

    /**
     * Method checks if a square is in danger e.g the opposing player can somehow move there.
     * @param squarePosition the position we want to check if it is in danger.
     * @return if the player can move there.
     */
    private static boolean squareInDanger(Chessman[] board, int squarePosition){
        Chessman.Color enemyColor;
        if(board[squarePosition]==null)return false;
        if(board[squarePosition].getColor()==Color.WHITE)enemyColor = Color.BLACK;  //Who is the enemy ?
        else enemyColor = Color.WHITE;

        for(int i = 0;i<64;i++){    //Iterate through fields.
            if(board[i]!=null && board[i].getColor() == enemyColor){    //If we found a square with an enemy chessman.
                if(ruleBook.getPossibleMovesLight(i, board)[squarePosition])return true; //And this enemy chessman can move to the squarePosition this square is in danger.
            }
        }
        return false;
    }

    public static boolean isMate(Color color, Chessman[] board){
        // checks, if the player with the given color is checked
        if(isCheck(color,board)){
            for(int i = 0; i < 64; i++){
                //searches for all pieces with the given color
                if(board[i]!=null && board[i].getColor() == color){
                    // checks, if there are moves with this piece
                    // if there is even one possible  move left, the player is not mated
                    if(hasMoves(ruleBook.getPossibleMoves(i,board))) return false;
                }
            }
            // we get here, if no piece has a possible move AND the player is checked. this indicates checkmate!
            return true;
        }

        //if there is no check, there can´t be mate of course
        return false;
    }
    /**
     * A method, which checks, if there are any possible moves, a certain figure can make
     * @return if the player has any possible move left with a figure
     */
    public static boolean hasMoves (boolean[] moves){
        for(int i = 0; i < moves.length; i++){
            if(moves[i]) return true;
        }
        return false;
    }



}
