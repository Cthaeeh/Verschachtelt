package com.example.kai.verschachtelt.pvAIGame.ai;

/**
 * Created by Kai on 15.09.2016.
 * Like a MoveAsInt the additional info to describe a chessboard, such as castling rights and en passant
 * and the half Move clock are encoded here as an int.
 * bit  0-4     = castling rights
 *      5-15    = half-move clock
 *      16-31   = en Passant
 */
public final class BoardInfoAsInt {

    private static final int castlingQueenBlackShift = 0;   //Position of the value
    private static final int CASTLING_QUEEN_BLACK_MASK = 0b1 << castlingQueenBlackShift; //Mask for access

    private static final int castlingKingBlackShift = 1;
    private static final int CASTLING_KING_BLACK_MASK = 0b1 << castlingKingBlackShift;

    private static final int castlingQueenWhiteShift = 2;
    private static final int CASTLING_QUEEN_WHITE_MASK = 0b1 << castlingQueenWhiteShift;

    private static final int castlingKingWhiteShift = 3;
    private static final int CASTLING_KING_WHITE_MASK = 0b1 << castlingKingWhiteShift;

    private static final int halfMoveClockShift = 5;
    private static final int HALF_MOVE_CLOCK_MASK = 0b1111111111 << halfMoveClockShift;
    //TODO en passant

    /**
     * creates an extra info int, that encodes castling rights, halfmoveClock.
     * @param castlingQueenBlack can black castle queen side ?
     * @param castlingKingBlack  can black castle king side ?
     * @param castlingQueenWhite can white castle queen side ?
     * @param castlingKingWhite  can white castle king side ?
     * @param halfMoveClock
     * @return that info encoded as a single primitive int.
     */
    public static int encodeExtraInfo(boolean castlingQueenBlack, boolean castlingKingBlack,
                                      boolean castlingQueenWhite, boolean castlingKingWhite,
                                      int halfMoveClock){

        int extraInfo  = 0; //Start with a 0 e.g a blank canvas
        extraInfo|= (castlingQueenBlack? 1 : 0)<<castlingQueenBlackShift;   //Set the bits "|" works because extraInfo is completly 0.
        extraInfo|= (castlingKingBlack ? 1 : 0)<<castlingKingBlackShift;
        extraInfo|= (castlingQueenWhite? 1 : 0)<<castlingQueenWhiteShift;
        extraInfo|= (castlingKingWhite? 1 : 0)<<castlingKingWhiteShift;
        extraInfo|= halfMoveClock<<halfMoveClockShift;
        return extraInfo;
    }

    public static boolean getQueenSideBlackCastlingRight(int extraInfo){
        return (extraInfo & CASTLING_QUEEN_BLACK_MASK)!= 0; //No shifting needed because its the first bit
    }

    /**
     * Sets the castling right new.
     * @param canCastle if the king can castle queen side.
     * @param extraInfo the int we want to manipulate and change the castling-right
     * @return the manipulated int.
     */
    public static int     setQueenSideBlackCastlingRight(boolean canCastle, int extraInfo){
        extraInfo = (extraInfo & (~CASTLING_QUEEN_BLACK_MASK))|(canCastle? 1 : 0)<<castlingQueenBlackShift;
        return extraInfo;
    }

    public static boolean getKingSideBlackCastlingRight(int extraInfo){
        return ((extraInfo & CASTLING_KING_BLACK_MASK)>>>castlingKingBlackShift )!= 0;
    }

    public static int     setKingSideBlackCastlingRight(boolean canCastle, int extraInfo){
        extraInfo = (extraInfo & (~CASTLING_KING_BLACK_MASK))|(canCastle? 1 : 0)<<castlingKingBlackShift;
        return extraInfo;
    }

    public static boolean getQueenSideWhiteCastlingRight(int extraInfo){
        return ((extraInfo & CASTLING_QUEEN_WHITE_MASK)>>>castlingQueenWhiteShift )!= 0;
    }

    public static int     setQueenSideWhiteCastlingRight(boolean canCastle, int extraInfo){
        extraInfo = (extraInfo & (~CASTLING_QUEEN_WHITE_MASK))|(canCastle? 1 : 0)<<castlingQueenWhiteShift;
        return extraInfo;
    }

    public static boolean getKingSideWhiteCastlingRight(int extraInfo){
        return ((extraInfo & CASTLING_KING_WHITE_MASK)>>>castlingKingWhiteShift)!= 0;
    }

    public static int     setKingSideWhiteCastlingRight(boolean canCastle, int extraInfo){
        extraInfo = (extraInfo & (~CASTLING_KING_WHITE_MASK))|(canCastle? 1 : 0)<<castlingKingWhiteShift;
        return extraInfo;
    }

    public static int getHalfMoveClock(int extraInfo){
        return ((extraInfo & HALF_MOVE_CLOCK_MASK)>>>halfMoveClockShift);
    }

}
