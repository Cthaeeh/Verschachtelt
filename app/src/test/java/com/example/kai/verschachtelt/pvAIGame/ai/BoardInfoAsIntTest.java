package com.example.kai.verschachtelt.pvAIGame.ai;

import org.junit.Test;

/**
 * Created by Kai on 16.09.2016.
 */
public class BoardInfoAsIntTest {
    @Test
    public void testExtractExtraInfo() throws Exception {
        int testExtraInfo = BoardInfoAsInt.encodeExtraInfo(true,true,false,false,220);
        boolean queenSideBlackResult = BoardInfoAsInt.getQueenSideBlackCastlingRight(testExtraInfo);
        boolean kingSideBlackResult = BoardInfoAsInt.getKingSideBlackCastlingRight(testExtraInfo);
        boolean queenSideWhiteResult = BoardInfoAsInt.getQueenSideWhiteCastlingRight(testExtraInfo);
        boolean kingSideWhiteResult = BoardInfoAsInt.getKingSideWhiteCastlingRight(testExtraInfo);

        assert(queenSideBlackResult==true);

        assert(kingSideBlackResult==true);

        assert(queenSideWhiteResult==false);

        assert(kingSideWhiteResult==false);

        assert(BoardInfoAsInt.getHalfMoveClock(testExtraInfo)==220);
    }

    @Test
    public void testGetQueenSideBlackCastlingRight() throws Exception {

    }

    @Test
    public void testGetKingSideBlackCastlingRight() throws Exception {

    }

    @Test
    public void testGetQueenSideWhiteCastlingRight() throws Exception {

    }

    @Test
    public void testGetKingSideWhiteCastlingRight() throws Exception {

    }

    @Test
    public void testGetHalfMoveClock() throws Exception {

    }
}