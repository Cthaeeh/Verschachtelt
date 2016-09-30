package com.example.kai.verschachtelt.chessLogic;

/**
 * Created by Kai on 30.09.2016.
 */
public class EnPassantManager {

    private int enPassantPossibility = -1; // a square a pawn jumped over in the last move, e.g a possibility for an en passant move.
    private boolean[] possibleMoves = new boolean[64];
    private Chessman[] chessmen;

    //Constructors
    /**
     * The standard en-passant constructor
     * @param chessmen the current chessman-array
     */
    public EnPassantManager(Chessman[] chessmen){
        this.chessmen = chessmen;
    }

    /**
     * A constructor to be used if the game has already begun, like in puzzles or when you save a game.
     * @param chessmen the current chessman-array
     * @param enPassantPossibility a square a pawn jumped over in the last move, e.g a possibility for an en passant move.
     */
    public EnPassantManager(Chessman[] chessmen, int enPassantPossibility) {
        this.chessmen = chessmen;
        this.enPassantPossibility = enPassantPossibility;
    }

    /**
     * A copy constructor
     * @param toCopy the constructor to be copied
     */
    public EnPassantManager(EnPassantManager toCopy){
        chessmen = Chessman.deepCopy(toCopy.chessmen);
        enPassantPossibility = toCopy.enPassantPossibility;
    }

    //Methods
    /**
     * Gets the possible en passant moves for the piece at selectedPosition on the current board.
     * @param selectedPosition
     * @param chessmen
     * @return the possible en passant movement possibilitys
     */
    public boolean[] getPossibleMoves(int selectedPosition, Chessman[] chessmen) {
        this.chessmen = chessmen; //Save the new chessmen array, you got from ChessBoardComplex.
        resetPossibleMoves();
        if(chessmen[selectedPosition].getPiece() == Chessman.Piece.PAWN){       //If its a pawn
            if (chessmen[selectedPosition].getColor() == Chessman.Color.WHITE) {
                if(selectedPosition-9>=0&&selectedPosition-9==enPassantPossibility)possibleMoves[selectedPosition-9]=true;    //If a pawn can reach the enPassantPossibility field
                if(selectedPosition-7>=0&&selectedPosition-7==enPassantPossibility)possibleMoves[selectedPosition-7]=true;
            } else {
                if(selectedPosition+9<64&&selectedPosition+9==enPassantPossibility)possibleMoves[selectedPosition+9]=true;
                if(selectedPosition+7<64&&selectedPosition+7==enPassantPossibility)possibleMoves[selectedPosition+7]=true;
            }
        }
        return CheckTester.removeSuicidalMoves(selectedPosition,Chessman.deepCopy(chessmen),possibleMoves);
    }

    /**
     * To be called after every move (by human), handles en passant stuff.
     * Like opening an en passant possibility or removing a pawn if an en passant capture happend.
     *
     * @param destPos   destination of the move
     * @param startPos  starting Point of the move
     * @param chessmen  current chessman array
     */
    public void handleEnPassant(int destPos, int startPos, Chessman[] chessmen) {
        //If an en passant Capture happend :
        if(chessmen[destPos].getPiece() == Chessman.Piece.PAWN && destPos == enPassantPossibility){
            //is the move direction south or north ?
            int capturedPawnPos = (destPos>startPos) ? destPos -8 : destPos + 8 ;
            chessmen[capturedPawnPos] = null;
        }
        enPassantPossibility = -1;  //Reset en Passant Possibility
        //Maybe invoke a new Possibility
        if(chessmen[destPos].getPiece() == Chessman.Piece.PAWN && Math.abs(destPos-startPos) == 16){
            enPassantPossibility = (destPos>startPos) ? destPos -8 : startPos - 8 ; //The larger number -8 gives the square over which the pawn jumped
        }
    }

    /**
     * Resets the possibleMove array e.g all fields are false and you can´t move anywhere.
     */
    private void resetPossibleMoves() {
        for (int i=0;i<64;i++){
            possibleMoves[i]=false;
        }
    }
}
