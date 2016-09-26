package com.example.kai.verschachtelt.pvAIGame.ai;

import org.junit.Test;

/**
 * Created by Kai on 18.09.2016.
 * For testing the move generation.
 * Hardcoded but human readable byte arrays are used for testing.
 */
public class MoveGenTest {

    private static final byte X = MoveGen.INACCESSIBLE;
    private static final byte p = MoveGen.PAWN_WHITE;
    private static final byte P = MoveGen.PAWN_BLACK;

    private static final byte r = MoveGen.ROOK_WHITE;
    private static final byte R = MoveGen.ROOK_BLACK;

    private static final byte b = MoveGen.BISHOP_WHITE;
    private static final byte B = MoveGen.BISHOP_BLACK;

    private static final byte kn = MoveGen.KNIGHT_WHITE;
    private static final byte KN = MoveGen.KNIGHT_BLACK;

    private static final byte q = MoveGen.QUEEN_WHITE;
    private static final byte Q = MoveGen.QUEEN_BLACK;

    private static final byte k = MoveGen.KING_WHITE;
    private static final byte K = MoveGen.KING_BLACK;

    private static final byte[] testBoard = new byte[]
            {
                    X, X  ,X  ,X  ,X  ,X  ,X  ,X  ,X  ,X,
                    X, X  ,X  ,X  ,X  ,X  ,X  ,X  ,X  ,X,
                    X, R  ,KN ,  B,  Q,  K,  B,KN ,R  ,X,
                    X, P  ,P  ,  P,  P,  P,  P, P ,P  ,X,
                    X, 0  ,0  ,  0,  0,  0,  0, 0 ,0  ,X,
                    X, 0  ,0  ,  0,  0,  0,  0, 0 ,0  ,X,
                    X, 0  ,0  ,  0,  0,  0,  0, 0 ,0  ,X,
                    X, 0  ,0  ,  0,  0,  0,  0, 0 ,0  ,X,
                    X, p  ,p  ,  p,  p,  p,  p, p ,p  ,X,
                    X, r  ,kn ,  b, q,  k,  b, kn ,r  ,X,
                    X, X  ,X  ,X  ,X  ,X  ,X  ,X  ,X  ,X,
                    X, X  ,X  ,X  ,X  ,X  ,X  ,X  ,X  ,X,
                    X, X  ,X  ,X  ,X  ,X  ,X  ,X  ,X  ,MoveGen.WHITE    //Whites turn
            };

    private static final byte[] testBoardAfterE4 = new byte[]
            {
                    X, X  ,X  ,X  ,X  ,X  ,X  ,X  ,X  ,X,
                    X, X  ,X  ,X  ,X  ,X  ,X  ,X  ,X  ,X,
                    X, R  ,KN ,  B,  Q,  K,  B,KN ,R  ,X,
                    X, P  ,P  ,  P,  P,  P,  P, P ,P  ,X,
                    X, 0  ,0  ,  0,  0,  0,  0, 0 ,0  ,X,
                    X, 0  ,0  ,  0,  0,  0,  0, 0 ,0  ,X,
                    X, 0  ,0  ,  0,  0,  p,  0, 0 ,0  ,X,
                    X, 0  ,0  ,  0,  0,  0,  0, 0 ,0  ,X,
                    X, p  ,p  ,  p,  p,  0,  p, p ,p  ,X,
                    X, r  ,kn ,  b, q,  k,  b, kn ,r  ,X,
                    X, X  ,X  ,X  ,X  ,X  ,X  ,X  ,X  ,X,
                    X, X  ,X  ,X  ,X  ,X  ,X  ,X  ,X  ,X,
                    X, X  ,X  ,X  ,X  ,X  ,X  ,X  ,X  ,MoveGen.BLACK   //Black turn
            };

    private static final byte[] testBoardPromotion = new byte[]
            {
                    X, X  ,X  ,X  ,X  ,X  ,X  ,X  ,X  ,X,
                    X, X  ,X  ,X  ,X  ,X  ,X  ,X  ,X  ,X,
                    X, 0  ,0  ,  0,  0,  K,  0,0  ,R  ,X,
                    X, 0  ,0  ,  0,  0,  0,  0, p ,0  ,X,
                    X, 0  ,0  ,  0,  0,  0,  0, 0 ,0  ,X,
                    X, 0  ,0  ,  0,  0,  0,  0, 0 ,0  ,X,
                    X, 0  ,0  ,  0,  0,  p,  0, 0 ,0  ,X,
                    X, 0  ,0  ,  0,  0,  0,  0, 0 ,0  ,X,
                    X, p  ,p  ,  0,  0,  0,  0, 0 ,0  ,X,
                    X, r  ,0  ,  0,  0,  0,  0, k ,0  ,X,
                    X, X  ,X  ,X  ,X  ,X  ,X  ,X  ,X  ,X,
                    X, X  ,X  ,X  ,X  ,X  ,X  ,X  ,X  ,X,
                    X, X  ,X  ,X  ,X  ,X  ,X  ,X  ,X  ,MoveGen.WHITE  //Whites turn
            };

    private static final byte[] testBoardAfterPromotion = new byte[]
            {
                    X, X  ,X  ,X  ,X  ,X  ,X  ,X  ,X  ,X,
                    X, X  ,X  ,X  ,X  ,X  ,X  ,X  ,X  ,X,
                    X, 0  ,0  ,  0,  0,  K,  0,0  ,q  ,X,
                    X, 0  ,0  ,  0,  0,  0,  0, 0 ,0  ,X,
                    X, 0  ,0  ,  0,  0,  0,  0, 0 ,0  ,X,
                    X, 0  ,0  ,  0,  0,  0,  0, 0 ,0  ,X,
                    X, 0  ,0  ,  0,  0,  p,  0, 0 ,0  ,X,
                    X, 0  ,0  ,  0,  0,  0,  0, 0 ,0  ,X,
                    X, p  ,p  ,  0,  0,  0,  0, 0 ,0  ,X,
                    X, r  ,0  ,  0,  0,  0,  0, k ,0  ,X,
                    X, X  ,X  ,X  ,X  ,X  ,X  ,X  ,X  ,X,
                    X, X  ,X  ,X  ,X  ,X  ,X  ,X  ,X  ,X,
                    X, X  ,X  ,X  ,X  ,X  ,X  ,X  ,X  ,MoveGen.BLACK  //Whites turn
            };

    @Test
    public void testInitialise() throws Exception {
        int extraInfo = BoardInfoAsInt.encodeExtraInfo(true,true,true,true,0);
        MoveGen.initialise(testBoard,extraInfo);
    }

    @Test
    public void testE4() throws Exception {
        int extraInfo = BoardInfoAsInt.encodeExtraInfo(true,true,true,true,0);
        MoveGen.initialise(testBoard,extraInfo);
        int testMove = MoveAsInt.getMoveAsInt(85,65,MoveGen.EMPTY); // normal e4 move
        MoveGen.makeMove(testMove);
        byte[] result = MoveGen.getBoard();
        if (!equals(result, testBoardAfterE4)) throw new AssertionError();
        MoveGen.unMakeMove(testMove);
        byte[] result2 = MoveGen.getBoard();
        if (!equals(result2, testBoard)) throw new AssertionError();
    }

    @Test
    public void testPromotion() throws Exception {
        int extraInfo = BoardInfoAsInt.encodeExtraInfo(false,false,false,false,50);
        MoveGen.initialise(testBoardPromotion,extraInfo);
        int promotion = MoveAsInt.getPromotionMoveAsInt(37,28,MoveGen.ROOK_BLACK,MoveGen.QUEEN_WHITE); // promote queen
        MoveGen.makeMove(promotion);
        if (!equals(MoveGen.getBoard(), testBoardAfterPromotion)) throw new AssertionError();
        short valAfterPromo = BordEvaluation.evaluate(MoveGen.getBoard());
        MoveGen.unMakeMove(promotion);
        if (!equals(MoveGen.getBoard(), testBoardPromotion)) throw new AssertionError();
        short valBeforPromo = BordEvaluation.evaluate(MoveGen.getBoard());
        if(!(valAfterPromo>valBeforPromo))throw new AssertionError();
    }

    /**
     * In order to see where is a difference between two arrays
     * @param a the first byte array
     * @param b the second
     * @return true if they contain the same values, otherwise false.
     */
    private boolean equals(byte[] a, byte[] b){
        if(a.length != b.length) return false;
        for(int i = 0; i< a.length; i++){
            if(a[i]!=b[i]){
                System.out.println("There is a difference at index: " +i);
                return  false;
            }
        }
        return true;
    }
}