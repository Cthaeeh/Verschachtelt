package com.example.kai.verschachtelt.pvAIGame.ai;

/**
 * Created by Kai on 15.09.2016.
 * Here you can see the third attempt to make the Move-Generation faster.
 * This Time by Encoding a Move in single Integer (thats just 32 bit)
 *
 * bit  0-7     = start position
 *      8-14    = destination position
 *      15-23   = captured Piece encoded as a Byte
 *      24-32   = en Passant, Promotion, Castling.
 */
public final class MoveAsInteger {

    private static final int startShift = 0;
    private static final int START_MASK = 0b1111111 << startShift;

    private static final int destShift  = 7;
    private static final int DEST_MASK = 0b1111111 << destShift;

    private static final int captureShift = 14;
    private static final int CAPTURE_MASK = 0b11111111 << captureShift;

    public static int getMoveAsInt(int start, int dest, byte capture){
        start|= dest<<destShift;
        start|= (capture&0xFF)<<captureShift;
        return start;
    }

    public static int getStart(int move){
        return (move & START_MASK);
    }

    public static int getDest(int move){
        return (move & DEST_MASK)>>> destShift;
    }

    public static byte getCapture(int move){
        return (byte)((move & CAPTURE_MASK) >>> captureShift);
    }

    /**
     * To test that this class really works correctly.
     * @param move
     * @return
     */
    public static String toString(int move){
        String moveAsString = "Start:";
        moveAsString+=getStart(move);
        moveAsString+=" Dest:";
        moveAsString+=getDest(move);
        moveAsString+=" Capture:";
        moveAsString+=getCapture(move);
        return moveAsString;
    }

    public static int getVal(int move) {    //TODO somehow determine very fast if a move is interesting
        return 0;
    }
}
