package com.example.kai.verschachtelt.pvAIGame.ai;

import org.junit.Test;

/**
 * Created by Kai on 15.09.2016.
 */
public class MoveAsIntTest {

    private final int start = 12;
    private final int dest = 23;
    private final byte capture = MoveGen.BISHOP_BLACK;
    private final byte promotedPiece = MoveGen.BISHOP_WHITE;

    @Test
    public void testGetMoveAsInt() throws Exception {
        int testMove = MoveAsInt.getMoveAsInt(start,dest,capture);
        String testResult = MoveAsInt.toString(testMove);
        if (!testResult.equals("Start:12 Dest:23 Capture:-2")) throw new AssertionError();
    }

    @Test
    public void testGetStart() throws Exception {
        int testMove = MoveAsInt.getMoveAsInt(start,dest,capture);
        if (MoveAsInt.getStart(testMove) != start) throw new AssertionError();
    }

    @Test
    public void testGetDest() throws Exception {
        int testMove = MoveAsInt.getMoveAsInt(start,dest,capture);
        if (MoveAsInt.getDest(testMove) != dest) throw new AssertionError();
    }

    @Test
    public void testGetCapture() throws Exception {
        int testMove = MoveAsInt.getMoveAsInt(start,dest,capture);
        if (MoveAsInt.getCapture(testMove) != capture) throw new AssertionError();
    }

    @Test
    public void testGetPromotedPiece() throws Exception {
        int testMove = MoveAsInt.getPromotionMoveAsInt(start,dest,capture,promotedPiece);
        if (MoveAsInt.getPromotedPiece(testMove) != promotedPiece) throw new AssertionError();

        int testMove2 = MoveAsInt.getPromotionMoveAsInt(start,dest,capture,(byte) 0);
        if (MoveAsInt.getPromotedPiece(testMove2) != 0) throw new AssertionError();

        int testMove3 = MoveAsInt.getMoveAsInt(start,dest,capture);
        if (MoveAsInt.getPromotedPiece(testMove3) != 0) throw new AssertionError();

        int testMove4 = MoveAsInt.getPromotionMoveAsInt(start,dest,capture,MoveGen.QUEEN_WHITE);
        if (MoveAsInt.getPromotedPiece(testMove4) != MoveGen.QUEEN_WHITE) throw new AssertionError();

        int testMove5 = MoveAsInt.getPromotionMoveAsInt(start,dest,capture,MoveGen.KNIGHT_WHITE);
        if (MoveAsInt.getPromotedPiece(testMove5) != MoveGen.KNIGHT_WHITE) throw new AssertionError();
    }
}