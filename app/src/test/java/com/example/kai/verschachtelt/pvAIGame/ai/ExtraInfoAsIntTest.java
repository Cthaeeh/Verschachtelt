package com.example.kai.verschachtelt.pvAIGame.ai;

import org.junit.Test;

/**
 * Created by Kai on 16.09.2016.
 */
public class ExtraInfoAsIntTest {
    @Test
    public void testExtractExtraInfo() throws Exception {
        int testExtraInfo = ExtraInfoAsInt.encodeExtraInfo(true,true,false,false,220);
        boolean queenSideBlackResult = ExtraInfoAsInt.getQueenSideBlackCastlingRight(testExtraInfo);
        boolean kingSideBlackResult = ExtraInfoAsInt.getKingSideBlackCastlingRight(testExtraInfo);
        boolean queenSideWhiteResult = ExtraInfoAsInt.getQueenSideWhiteCastlingRight(testExtraInfo);
        boolean kingSideWhiteResult = ExtraInfoAsInt.getKingSideWhiteCastlingRight(testExtraInfo);

        assert(queenSideBlackResult==true);

        assert(kingSideBlackResult==true);

        assert(queenSideWhiteResult==false);

        assert(kingSideWhiteResult==false);

        assert(ExtraInfoAsInt.getHalfMoveClock(testExtraInfo)==220);
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