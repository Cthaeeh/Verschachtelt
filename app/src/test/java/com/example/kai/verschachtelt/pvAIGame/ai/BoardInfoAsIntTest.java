package com.example.kai.verschachtelt.pvAIGame.ai;

import org.junit.Test;

/**
 * Created by Kai on 16.09.2016.
 * Here we test if the boolean values e.g castling states
 * can be set correctly, extracted correctly and manipulated correctly.
 */
public class BoardInfoAsIntTest {
    @Test
    public void testExtractExtraInfo() throws Exception {
        int testExtraInfo = BoardInfoAsInt.encodeExtraInfo(true,true,false,false,220);

        boolean queenSideBlackResult = BoardInfoAsInt.getQueenSideBlackCastlingRight(testExtraInfo);
        boolean kingSideBlackResult = BoardInfoAsInt.getKingSideBlackCastlingRight(testExtraInfo);
        boolean queenSideWhiteResult = BoardInfoAsInt.getQueenSideWhiteCastlingRight(testExtraInfo);
        boolean kingSideWhiteResult = BoardInfoAsInt.getKingSideWhiteCastlingRight(testExtraInfo);

        if (queenSideBlackResult != true) throw new AssertionError();
        if (kingSideBlackResult != true) throw new AssertionError();
        if (queenSideWhiteResult != false) throw new AssertionError();
        if (kingSideWhiteResult != false) throw new AssertionError();

        if (BoardInfoAsInt.getHalfMoveClock(testExtraInfo) != 220) throw new AssertionError();
    }

    @Test
    public void testChangingCastlingRights() throws Exception {
        int testExtraInfo = BoardInfoAsInt.encodeExtraInfo(true,true,true,true,220);    //Everything true
        testExtraInfo = BoardInfoAsInt.setKingSideWhiteCastlingRight(false, testExtraInfo); //One false

        boolean kingSideWhiteResult = BoardInfoAsInt.getKingSideWhiteCastlingRight(testExtraInfo);
        boolean queenSideBlackResult = BoardInfoAsInt.getQueenSideBlackCastlingRight(testExtraInfo);
        boolean kingSideBlackResult = BoardInfoAsInt.getKingSideBlackCastlingRight(testExtraInfo);
        boolean queenSideWhiteResult = BoardInfoAsInt.getQueenSideWhiteCastlingRight(testExtraInfo);

        if (kingSideWhiteResult != false) throw new AssertionError();
        if (queenSideBlackResult != true) throw new AssertionError();
        if (kingSideBlackResult != true) throw new AssertionError();
        if (queenSideWhiteResult != true) throw new AssertionError();
    }

}