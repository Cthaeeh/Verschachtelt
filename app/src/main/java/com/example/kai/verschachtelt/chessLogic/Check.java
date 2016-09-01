package com.example.kai.verschachtelt.chessLogic;

/**
 * A class created to check, if a king is under attack, e.g. checked
 * Created by ivayl on 01.09.2016.
 */
public class Check {

    // the one and only rulebook for the game
    private static RuleBook ruleBook = new RuleBook();

    // using the ChessBoardComplex
    protected ChessBoardComplex board;

    /**
     * boolean value, indicating whether white king is checked
     */
    private boolean whiteIsChecked;

    /**
     * boolean value, indicating whether black king is checked
     */
    private boolean blackIsChecked;

    /**
     * an array, indicating which positions on the board, represented by the arrays´ ,
     * are threatened by white
     */
    public boolean[] threatenedByWhite;

    /**
     * an array, indicating which positions on the board, represented by the arrays´ ,
     * are threatened by black
     */
    public boolean[] threatenedByBlack;

    public Check(ChessBoardComplex board){
        this.board = board;

        // initialize without any threatened field, will be already
        //changed after first move
        threatenedByWhite = new boolean[64];
        for(int i = 0; i< threatenedByWhite.length; i++) {
            threatenedByWhite[i] = false;
        }
        threatenedByBlack = new boolean[64];
        for(int i = 0; i< threatenedByBlack.length; i++) {
            threatenedByBlack[i] = false;
        }



    }

    private void setWhiteIsChecked(boolean bool) {
        whiteIsChecked = bool;
    }

    private void setBlackIsChecked(boolean bool){
        blackIsChecked = bool;
    }


    private boolean isWhiteChecked() {
       if(threatenedByBlack[board.getWhiteKingPosition()]){
           return true;
       } else {
           return false;
       }
    }

    private boolean isBlackChecked(){
        if(threatenedByWhite[board.getBlackKingPosition()]){
            return true;
        } else {
            return false;
        }
    }


    // MAYBE NOT NEEDED
    private void initBlackThreat(){
        // positions of black rooks are not threatened in the beginning
        threatenedByBlack[0] = false;
        threatenedByBlack[7] = false;
        // mark 1 to 6 as threatened
        for(int i = 1; i< 7; i++){
            threatenedByBlack[i] = true;
        }

        // mark 8 to 23 as threatened
        for(int i = 8; i< 24; i++){
            threatenedByBlack[i] = true;
        }

        for(int i = 24; i<64 ; i++){
            threatenedByBlack[i] = false;
        }

    }

    private void initWhiteThreat() {

        for(int i = 0; i < 40;i++) {
            threatenedByWhite[i] = false;
        }
        for(int i = 40; i < 56; i++) {
            threatenedByWhite[i] = true;
        }


    }
}
