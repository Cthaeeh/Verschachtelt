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
        assert (testResult.equals("Start:12 Dest:23 Capture:-2"));
    }

    @Test
    public void testGetStart() throws Exception {
        int testMove = MoveAsInt.getMoveAsInt(start,dest,capture);
        assert (MoveAsInt.getStart(testMove)==start);
    }

    @Test
    public void testGetDest() throws Exception {
        int testMove = MoveAsInt.getMoveAsInt(start,dest,capture);
        assert (MoveAsInt.getDest(testMove)==dest);
    }

    @Test
    public void testGetCapture() throws Exception {
        int testMove = MoveAsInt.getMoveAsInt(start,dest,capture);
        assert (MoveAsInt.getCapture(testMove)==capture);
    }

    @Test
    public void testGetPromotedPiece() throws Exception {
        int testMove = MoveAsInt.getPromotionMoveAsInt(start,dest,capture,promotedPiece);
        assert (MoveAsInt.getPromotedPiece(testMove) == promotedPiece);

        int testMove2 = MoveAsInt.getPromotionMoveAsInt(start,dest,capture,(byte) 0);
        assert (MoveAsInt.getPromotedPiece(testMove2) == 0);

        int testMove3 = MoveAsInt.getMoveAsInt(start,dest,capture);
        assert (MoveAsInt.getPromotedPiece(testMove3) == 0);
    }
}