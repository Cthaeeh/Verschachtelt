package com.example.kai.verschachtelt.chessLogic;

/**
 * Created by Kai on 09.08.2016.
 * A Class for representing the chess boardCurrent outside of the AI-Part.
 * No Chess Logic,no Graphics here.
 */
public class ChessBoardSimple {

    protected Chessman[] chessmen;           //where are the pieces on the board ?
    protected SquareState[] squareStates;    //marks (e.g frames )for selected chessman.
    protected Chessman.Color playerOnTurn;   // marks if black or white is on turn

    public void resetFrames() {
        for(int i =0;i<64;i++){
            squareStates[i]=SquareState.NORMAL;
        }
    }

    public void setSquareStateAt(int position,SquareState squareState){
        squareStates[position]=squareState;
    }

    /**
     * Representation of the states of a squareStates. E.g it can be selected, it is possible to move there from a selected field ...
     */
    public enum SquareState {
        NORMAL,SELECTED,POSSIBLE,POSSIBLE_KILL
    }

    public ChessBoardSimple(){
        chessmen = new Chessman[64];
        chessmen = getStandartSetup();
        squareStates = new SquareState[64];
        squareStates = getNormalState();
        playerOnTurn = Chessman.Color.WHITE;    //Always white when start
    }

    private SquareState[] getNormalState() {
        SquareState[] allSquareStates = new SquareState[64];
        for (int i =0;i<64;i++){
            allSquareStates[i]= SquareState.NORMAL;
        }
        return allSquareStates;
    }

    private Chessman[] getStandartSetup(){
        Chessman[] standartBoard = Chessman.getStandartSetup();
        return standartBoard;
    }

    public Chessman getChessManAt(int position){
        return chessmen[position];
    }

    public Chessman getChessManAt(int x, int y){
        return chessmen[x+8*y];
    }

    public SquareState getSquareStateAt(int position){
        return squareStates[position];
    }

    public Chessman.Color getPlayerOnTurn() {
        return playerOnTurn;
    }


}
