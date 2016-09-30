package com.example.kai.verschachtelt.chessLogic;

import com.example.kai.verschachtelt.graphics.VictoryScreenGraphic;
import com.example.kai.verschachtelt.pvAIGame.ai.Move;

/**
 * Created by Kai on 09.08.2016.
 * This class extends the normal Chessboard class and adds basic move functionality, possible move functionality.
 */
public class ChessBoardComplex extends ChessBoardSimple {

    private static RuleBook ruleBook = new RuleBook();                  //There is just one Rulebook for every game of chess.
    private boolean[] possibleDestinations = new boolean[64];           //It is either possible to move there or not.
    private int selectedPosition = -1;                                  //position to move a chessman from. By default no real position.

    private Chessman.Color playerOnTurn;

    private CastlingManager castlingManager;                            //Is used to encapsule the logic for castling
    private EnPassantManager enPassantManager;                          //Is used to encapsule the logic for en-passant
    private PawnPromotionManager pawnPromotionManager;                  //Is used to encapsule the logic for pawn changing.

    private VictoryScreenGraphic.VictoryState winner;

    //Constructors.
    public ChessBoardComplex(){
        super();
        playerOnTurn = Chessman.Color.WHITE;    //Always white when start
        castlingManager = new CastlingManager(chessmen);
        pawnPromotionManager = new PawnPromotionManager(chessmen);
        enPassantManager = new EnPassantManager(chessmen);
    }

    /**
     * A copy constructor. To ensure the object can be copied correctly.
     * @param board The object to make a copy of.
     */
    public ChessBoardComplex(ChessBoardComplex board) {
        chessmen = Chessman.deepCopy(board.chessmen);
        selectedPosition = board.selectedPosition;
        playerOnTurn = board.playerOnTurn;
        castlingManager = new CastlingManager(board.castlingManager);
        pawnPromotionManager = new PawnPromotionManager(board.pawnPromotionManager);
        enPassantManager = new EnPassantManager(board.enPassantManager);
    }

    /**
     * Method takes a String with Forsyth Edwards Notation and converts it to ChessBoardComplex Object.
     * @param fenNotation See: https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
     */
    public ChessBoardComplex(String fenNotation) {
        super();    //create Normal chessBoard.
        chessmen = FENParser.getChessmen(fenNotation);      //Set the chessmen to the positions from the notation.
        playerOnTurn = FENParser.getColor(fenNotation);     //Get the color of the player that has to move.
        castlingManager = FENParser.getCastlingState(fenNotation);
        enPassantManager = FENParser.getEnPassantState(fenNotation);
        pawnPromotionManager = new PawnPromotionManager(chessmen);//There is no info about pawn changes in the FEN
    }


    //Methods.
    /**
     * If the user selected a square this is saved in selectedPosition and possible Destinations are marked.
     * @param position  where the user touched.
     */
    public void handleSquareSelection(int position) {
        selectedPosition = position;
        if(position>=0&&position<64&& chessmen[position]!=null){        //If there is a chessman at this squareStates
            if(chessmen[position].getColor().equals(playerOnTurn)){     //If the player on turn wants to move.
                getPossibleDestinations();                              //get the possible destinations
                markPossibleDestinations();                             //and mark them.
            }
        }
    }

    /**
     * Depending on the selected chessman the method acceses the rulebook to see where it can move.
     */
    private void getPossibleDestinations() {
        possibleDestinations=ruleBook.getPossibleMoves(selectedPosition,chessmen);
        // add castling moves
        boolean[] possibleCastlingDestinations = castlingManager.getPossibleMoves(selectedPosition,chessmen);
        possibleDestinations= combine(possibleCastlingDestinations,possibleDestinations);
        // add en-passant moves
        boolean[] possibleEnPassantDestinations = enPassantManager.getPossibleMoves(selectedPosition,chessmen);
        possibleDestinations = combine(possibleEnPassantDestinations,possibleDestinations);
    }

    /**
     * Depending on the possible move destination the frames are colored accordingly.
     */
    private void markPossibleDestinations(){
        for(int i = 0;i<64;i++){
            if(possibleDestinations[i]&& chessmen[i]==null) squareStates[i]=SquareState.POSSIBLE;
            if(possibleDestinations[i]&& chessmen[i]!=null) squareStates[i]=SquareState.POSSIBLE_KILL;
        }
    }


    /**
     * If there is a selectedPosition we can directly move the chessman on that square to position.
     * @param destPos  The position to move to.
     */
    public void handleMoveTo(int destPos){
        if(selectedPosition >=0 && chessmen[selectedPosition]!=null){                    //If we try to move from a legit position
            chessmen[selectedPosition].notifyMove();  //Tell the chessman that he was moved (Important for Castling)
            chessmen[destPos]= chessmen[selectedPosition];//Set the chessman to its new position.
            chessmen[selectedPosition]=null;           //Remove the chessman from its originally squareStates.
            //Castling extension
            if(chessmen[destPos].getPiece()== Chessman.Piece.KING&&Math.abs(destPos-selectedPosition)==2){//If a King did a jump
                castlingManager.handleCastling(chessmen,destPos);//The corresponding tower needs to move as well.
            }
            //En-Passant extension
            enPassantManager.handleEnPassant(destPos,selectedPosition,chessmen);
            if(pawnPromotionPossible()==null){
                switchPlayerOnTurn();  //Only switch Player on turn if it was not a pawn promotion move.
                winner = ruleBook.getWinner(chessmen, playerOnTurn);
            }
        }
        resetFrames();
    }

    /**
     * The Chessboard will rearrange the chessman.
     * This method is for the ai (at least for now ...)
     */
    public void handleMoveByAI(Move move) {
        if(move.from>=0 && chessmen[move.from]!=null){        //If we try to move from a legit position
            if(move.isNullMove()){
                switchPlayerOnTurn();
                return;
            }
            chessmen[move.from].notifyMove();                 //Tell the chessman that he was moved (Important for Castling)
            chessmen[move.to]= chessmen[move.from];           //Set the chessman to its new position.
            chessmen[move.from]=null;                         //Remove the chessman from its originally squareStates.
            if(chessmen[move.to].getPiece()== Chessman.Piece.KING&&Math.abs(move.to-move.from)==2)
                castlingManager.handleCastling(chessmen, move.to);//The corresponding tower needs to move as well.

            if(move.isPromotion()) chessmen[move.to] = new Chessman(move.getPromotedPiece(),chessmen[move.to].getColor());
            switchPlayerOnTurn();
        }
        winner = ruleBook.getWinner(chessmen,playerOnTurn);
    }

    /**
     * Since the ChessBoardComplex doesnt know what GameMode is played
     * it simply returns either white or black or draw or null if the game is still in progress.
     * @return the winner of the game
     */
    public VictoryScreenGraphic.VictoryState getWinner() {
        return winner;
    }

    private void switchPlayerOnTurn() {
        if(playerOnTurn== Chessman.Color.BLACK)playerOnTurn= Chessman.Color.WHITE;
        else playerOnTurn= Chessman.Color.BLACK;
    }

    //Pawn promotion methods:
    /**
     *     checks, if a pawn change is possible and returns the color of the pawn to promote if possible otherwise null.
     */
    public Chessman.Color pawnPromotionPossible() {
        return pawnPromotionManager.pawnChangeColor(chessmen);
    }

    /**
     *  check and return the position, where a pawn is waiting to be changed
      */
    public int pawnChangePosition(){
        return pawnPromotionManager.getPawnChangePosition(chessmen);
    }

    /**
     * Exchanges the pawn at position "position" with newMan.
     * @param position
     * @param newMan
     */
    public void promotePawn(int position, Chessman newMan) {
        chessmen[position] = newMan;
        chessmen[position].notifyMove();
        switchPlayerOnTurn();
        winner = ruleBook.getWinner(chessmen, playerOnTurn);
    }

    public CastlingManager getCastlingManager() {
        castlingManager.setChessMen(chessmen);
        return castlingManager;
    }

    public Chessman.Color getPlayerOnTurn() {
        return playerOnTurn;
    }

    public int getSelectedPosition(){
        return selectedPosition;
    }

    /**
     * Method checks if the Boards are same concerning position (of the chessmen).
     * @param boardToCompare    The board to compare with the one this method is called on.
     * @return
     */
    public boolean comparePositions(ChessBoardComplex boardToCompare) {
        for(int i = 0;i<64;i++){
            if(chessmen[i]!=null && boardToCompare.chessmen[i]!=null){  //If both are not null
                if(!(chessmen[i].equals(boardToCompare.chessmen[i]))){
                    return false;
                }
            }
            //If one is null but the other not return false.
            if(chessmen[i]==null&&boardToCompare.chessmen[i]!=null)return false;
            if(chessmen[i]!=null&&boardToCompare.chessmen[i]==null)return false;
        }
        return true;
    }

    /**
     * Adds (combines) two boolean array in a way: result[i] = array1[i]||array2[i]
     * @param array1 the first array
     * @param array2 the second array
     * @return the merged arrays
     */
    private boolean[] combine(boolean[] array1, boolean[] array2) {
        //Make sure they have the same length
        if(array1.length!=array2.length)return array1;
        //Combine both possibleDestinations
        for(int i = 0;i<64;i++){
            array2[i] = (array2[i]||array1[i]);
        }
        return array2;
    }
}
