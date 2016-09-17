package com.example.kai.verschachtelt.pvAIGame.ai;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kai on 15.09.2016.
 */
public class MoveAsIntegerTest {

    private final int start = 12;
    private final int dest = 23;
    private final byte capture = MoveGenerator.BISHOP_BLACK;

    @Test
    public void testGetMoveAsInt() throws Exception {
        int testMove = MoveAsInteger.getMoveAsInt(start,dest,capture);
        String testResult = MoveAsInteger.toString(testMove);
        assert (testResult.equals("Start:12 Dest:23 Capture:-2"));
    }

    @Test
    public void testGetStart() throws Exception {
        int testMove = MoveAsInteger.getMoveAsInt(start,dest,capture);
        assert (MoveAsInteger.getStart(testMove)==start);
    }

    @Test
    public void testGetDest() throws Exception {
        int testMove = MoveAsInteger.getMoveAsInt(start,dest,capture);
        assert (MoveAsInteger.getDest(testMove)==dest);
    }

    @Test
    public void testGetCapture() throws Exception {
        int testMove = MoveAsInteger.getMoveAsInt(start,dest,capture);
        assert (MoveAsInteger.getCapture(testMove)==capture);
    }
}