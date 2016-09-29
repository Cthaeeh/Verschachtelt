package com.example.kai.verschachtelt.chessLogic;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Kai on 29.09.2016.
 * Class for testing the Fen-Parser, with some example FEN-Strings
 */
public class FENParserTest {

    private final String standartSetUp = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private final String afterE4 = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";
    private final String noCastlingPossible = "rnbq1bnr/ppppkppp/8/4p3/4P3/8/PPPPKPPP/RNBQ1BNR w - - 2 3";

    @Test
    public void testGetChessmen() throws Exception {
        Chessman[] chessmen = FENParser.getChessmen(standartSetUp);
        if (!Arrays.equals(chessmen,Chessman.getStandartSetup())) throw new AssertionError();
    }

    @Test
    public void testGetColor() throws Exception {
        Chessman.Color color = FENParser.getColor(standartSetUp);
        if (!(color.equals(Chessman.Color.WHITE))) throw new AssertionError();

        Chessman.Color color2 = FENParser.getColor(afterE4);
        if (!(color2.equals(Chessman.Color.BLACK))) throw new AssertionError();
    }

    @Test
    public void testGetCastlingState() throws Exception {
        CastlingManager castlingManager = FENParser.getCastlingState(standartSetUp);
        if (castlingManager.getKingSideBlack()==false) throw new AssertionError();
        if (castlingManager.getKingSideWhite()==false) throw new AssertionError();
        if (castlingManager.getQueenSideBlack()==false) throw new AssertionError();
        if (castlingManager.getQueenSideWhite()==false) throw new AssertionError();
        CastlingManager castlingManager2 = FENParser.getCastlingState(noCastlingPossible);
        if (castlingManager2.getKingSideBlack()==true) throw new AssertionError();
        if (castlingManager2.getKingSideWhite()==true) throw new AssertionError();
        if (castlingManager2.getQueenSideBlack()==true) throw new AssertionError();
        if (castlingManager2.getQueenSideWhite()==true) throw new AssertionError();
    }

    @Test
    public void testGetEnPassantState() throws Exception {
        //TODO implement
    }
}