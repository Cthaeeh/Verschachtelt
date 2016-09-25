package com.example.kai.verschachtelt.chessLogic;

import com.example.kai.verschachtelt.chessLogic.Chessman.Color;

import java.util.Arrays;

/**
 * Created by Kai on 04.09.2016.
 * A Class for handling check and check-Mate.
 */
public class CheckTester {
    private static RuleBook rulebook = new RuleBook();

    /**
     * Removes suicidal Moves from the Moves that are otherwise legal (pseudoLegal).
     * @param startingPosition  the Chessman that was selected by the user.
     * @param currentBoard      the current board.
     * @param pseudoMoves       the otherwise legal Moves.
     * @return                  the legal moves where you don´t kill your self.
     */
    public static boolean[] removeSuicidalMoves(int startingPosition, final Chessman[] currentBoard,final boolean[] pseudoMoves) {
        boolean[] legalMoves = new boolean[64];
        final boolean[] pseudoLegalMoves = Arrays.copyOf(pseudoMoves,pseudoMoves.length);
        for(int i = 0; i<64; i++){  //Iterate through fields.
            if(pseudoLegalMoves[i]){   //If this is a possible move destination.
                legalMoves[i] = isLegalMove(startingPosition,i,currentBoard);    //check if its a suicidal move.
            }
        }
        return legalMoves;
    }

    /**
     * Checks if a Move is legal regarding check.
     * @param startingPosition
     * @param endPosition
     * @param currentBoard
     * @return  true if legal Move, false if illegal Move.
     */
    private static boolean isLegalMove(int startingPosition, int endPosition, Chessman[] currentBoard) {
        Chessman[] hypotheticalBoard = Chessman.deepCopy(currentBoard);
        hypotheticalBoard[endPosition] = hypotheticalBoard[startingPosition];
        hypotheticalBoard[startingPosition] = null;                             // Make the move on a hypothetical ChessBoard.
        Chessman.Color colorOfMovingPlayer = hypotheticalBoard[endPosition].getColor();
        return !isCheck(hypotheticalBoard, colorOfMovingPlayer);
    }

    /**
     * Checks is a given player on a given board is in check. (E.g the King can be Killed by the other player.)
     * @param board
     * @param color
     * @return
     */
    private static boolean isCheck(Chessman[] board, Color color) {
        int kingPosition = findKing(board,color);
        if(kingPosition == -1) return true;
        Chessman.Color enemyColor;
        if(color == Color.BLACK)enemyColor = Color.WHITE;
        else enemyColor = Color.BLACK;
        return rulebook.getAllPossibleMoves(enemyColor,board)[kingPosition];
    }

    /**
     *
     * @param board
     * @param color
     * @return the position of the King on the board.
     */
    private static int findKing(Chessman[] board, Color color) {
        for(int i = 0; i<64;i++){
            if(board[i]!= null && board[i].getPiece() == Chessman.Piece.KING && board[i].getColor() == color){
                return i;
            }
        }
        return -1;  //King must be dead.
    }

    /**
     * Method checks if player with Color (parameter color) hast lost the game.
     * @param color
     * @param board
     * @return
     */
    public static boolean isMate(Color color,final Chessman[] board){
        if(isCheck(board , color)){ // checks, if the player with the given color is checked
            for(int i = 0; i < 64; i++){    //searches for all pieces with the given color
                if(board[i]!=null && board[i].getColor() == color){
                    // checks, if there are moves with this piece
                    // if there is even one possible  move left, the player is not mated
                    if(hasMoveLeft(rulebook.getPossibleMoves(i,board))) return false;
                }
            }
            return true;   // we get here, if no piece has a possible move AND the player is checked. this indicates checkmate!
        }
        return false;   //if there is no check, there can´t be mate of course
    }

    /**
     * A method, which checks, if there are any possible moves, a certain figure can make
     * @return if the player has any possible move left with a figure
     */
    private static boolean hasMoveLeft(boolean[] possibleDestinations){
        for(int i = 0; i < 64; i++){
            if(possibleDestinations[i]==true) return true;
        }
        return false;
    }

    /**
     * Determines if the given player on the board has no moves left and is not in check -> its a Draw
     * @param board
     * @param playerOnTurn
     * @return true if it is a draw, false if not.
     */
    public static boolean isDraw(Chessman[] board, Color playerOnTurn) {
        if(isCheck(board,playerOnTurn))return false;    //If player is in check he is mate or has moves left.(so no draw)
        for(int i = 0; i < 64; i++){    //Iterate over Board
            if(board[i]!=null && board[i].getColor() == playerOnTurn){
                if(hasMoveLeft(rulebook.getPossibleMoves(i,board))) return false;   //If he has any moves left its not a draw
            }
        }
        return true;    //No moves left -> its a draw.
    }
}
