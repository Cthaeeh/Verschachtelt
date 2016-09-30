package com.example.kai.verschachtelt.chessLogic;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Kai on 30.09.2016.
 * For testing the enPassant Manager
 * This heavily depends on a correct implemented FENParser.
 */
public class EnPassantManagerTest {

    private static final String enPassantPossibleE6 = "rnbqkbnr/ppppp1p1/7p/4Pp2/8/8/PPPP1PPP/RNBQKBNR w KQkq f6 0 3";
    private static final String enPassantPossibleB6 = "rnbqkbnr/2ppppp1/p7/Pp5p/K7/4P3/1PPP1PPP/RNB1QBNR w q b6 0 10";
    private static final String enPassantNotPossibleAtAll = "rnb1kbnr/ppp1qppp/8/3pP3/5p2/6P1/PPPP3P/RNBQKBNR w KQkq d6 0 5";
    @Test
    public void testGetPossibleMoves() throws Exception {
        EnPassantManager manager = FENParser.getEnPassantState(enPassantPossibleE6);    //Create the manager
        boolean[] moves = manager.getPossibleMoves(28,FENParser.getChessmen(enPassantPossibleE6));  //Get the possible moves for white pawn at e5
        if(moves[21]==false)throw new AssertionError(); //Should be able to move to f6

        EnPassantManager manager2 = FENParser.getEnPassantState(enPassantPossibleB6);    //Create the manager
        boolean[] moves2 = manager2.getPossibleMoves(24,FENParser.getChessmen(enPassantPossibleB6));  //Get the possible moves for white pawn at e5
        if(moves2[17]==false)throw new AssertionError();

        EnPassantManager manager3 = FENParser.getEnPassantState(enPassantNotPossibleAtAll);    //Create the manager
        boolean[] moves3 = manager3.getPossibleMoves(28,FENParser.getChessmen(enPassantNotPossibleAtAll));  //Get the possible moves for white pawn at e5
        if(!allFalse(moves3))throw new AssertionError();
    }

    /**
     * Method checks if all entrys are false
     * @param array
     * @return true if all false
     */
    private boolean allFalse(boolean[] array) {
        for(boolean b : array){
            if(b)return false;
        }
        return true;
    }
}