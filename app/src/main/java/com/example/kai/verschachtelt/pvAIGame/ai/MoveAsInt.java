package com.example.kai.verschachtelt.pvAIGame.ai;

/**
 * Created by Kai on 15.09.2016.
 * Here you can see the third attempt to make the Move-Generation faster.
 * This Time by Encoding a Move in single Integer (thats just 32 bit)
 *
 * bit  0-7     = start position
 *      8-14    = destination position
 *      15-23   = captured Piece encoded as a Byte
 *      24-27   = promoted Piece
 *      25-32   = en Passant, Castling.
 */
public final class MoveAsInt {

    private static final int startShift = 0;
    private static final int START_MASK = 0b1111111 << startShift;

    private static final int destShift  = 7;
    private static final int DEST_MASK = 0b1111111 << destShift;

    private static final int captureShift = 14;
    private static final int CAPTURE_MASK = 0b11111111 << captureShift;

    private static final int promotedPieceShift = 23;
    private static final int PROMOTED_PIECE_MASK = 0b111 << promotedPieceShift;

    /**
     * Returns a standart Move encoded as an int
     * @param start the start position
     * @param dest  the destination position
     * @param capture the captured piece and if non 0
     * @return the move encoded as int.
     */
    public static int getMoveAsInt(int start, int dest, byte capture){
        start|= dest<<destShift;
        start|= (capture&0xFF)<<captureShift;   //&0xFF gives us an unsigned byte
        return start;
    }

    /**
     *
     * @param start the start position
     * @param dest the destination position
     * @param capture the captured piede and if non 0
     * @param newPiece the promotion piece ONLY POSITIVE VALUES 0-6
     * @return the move encoded as int.
     */
    public static int getPromotionMoveAsInt(int start, int dest, byte capture, byte newPiece){
        start|= dest<<destShift;
        start|= (capture&0xFF)<<captureShift;       //&0xFF gives us an unsigned byte
        start|= (newPiece&0xFF)<<promotedPieceShift;//
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

    public static byte getPromotedPiece(int move){
        return (byte)((move & PROMOTED_PIECE_MASK) >>> promotedPieceShift);
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

    final  static String mailBox[] = {
            "X", "X", "X", "X", "X", "X", "X", "X", "X","X",
            "X", "X", "X", "X", "X", "X", "X", "X", "X","X",
            "X","a8","b8","c8","d8","e8","f8","g8","h8","X",
            "X","a7","b7","c7","d7","e7","f7","g7","h7","X",
            "X","a6","b6","c6","d6","e6","f6","g6","h6","X",
            "X","a5","b5","c5","d5","e5","f5","g5","h5","X",
            "X","a4","b4","c4","d4","e4","f4","g4","h4","X",
            "X","a3","b3","c3","d3","e3","f3","g3","h3","X",
            "X","a2","b2","c2","d2","e2","f2","g2","h2","X",
            "X","a1","b1","c1","d1","e1","f1","g1","h1","X",
            "X", "X", "X", "X", "X", "X", "X", "X", "X","X",
            "X", "X", "X", "X", "X", "X", "X", "X", "X","X"
    };

    public static String toReadableString(int move){
        String moveAsString = "Move  ";
        moveAsString+= mailBox[getStart(move)];
        moveAsString+=" -> ";
        moveAsString+=mailBox[getDest(move)];
        moveAsString+=" Capture:";
        moveAsString+=getCapture(move);
        return moveAsString;
    }

    /**
     * A method that turns an array of moves encoded as ints into a human readable String.
     * @param moves
     * @return
     */
    public static String toReadableString(int[] moves) {
        String movesAsString = "best Line: ";
        for (int move : moves) {
            movesAsString += toReadableString(move) + "  ";
        }
        return movesAsString;
    }
}
