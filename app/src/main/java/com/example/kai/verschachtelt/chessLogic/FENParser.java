package com.example.kai.verschachtelt.chessLogic;

import android.util.Log;

/**
 * Created by Kai on 14.08.2016.
 * This class is for translating Forsyth-Edwards-Notations to Java-Objects.
 */
public class FENParser {

    /**
     *
     * @param fenNotation   See https://de.wikipedia.org/wiki/Forsyth-Edwards-Notation#Figurenstellung
     * @return              a Chessman array with the cheesmen on the position as in the notation.
     */
    public static Chessman[] getChessmen(String fenNotation) {
        //TODO make this method a lot safer, because there could be a typo in the fenNoatation and the app should not crash then.
        Chessman[] chessmen = new Chessman[64];
        int boardPosition = 0;
        int charPosition = 0;
        while(boardPosition<64){ //As long is we haven´t got for every square the piece
            char ch = fenNotation.charAt(charPosition);
            charPosition++;
            //If there is a letter
            if (Character.isLetter(ch)){
                chessmen[boardPosition]= getChessmanFromChar(ch);
                boardPosition++;
            }
            //If there is a digit.
            if (Character.isDigit(ch)){
                for (int i = boardPosition; i<boardPosition+Character.getNumericValue(ch);i++){
                    chessmen[i]=null;
                }
                boardPosition+=Character.getNumericValue(ch);
            }
        }
        return chessmen;
    }

    /**
     *
     * @param fenNotation   See https://de.wikipedia.org/wiki/Forsyth-Edwards-Notation#Figurenstellung
     * @return              The color if the player that has to move.
     */
    public static Chessman.Color getColor(String fenNotation){
        if(fenNotation.split(" ")[1].contains("w"))return Chessman.Color.WHITE;
        else return Chessman.Color.BLACK;
    }

    /**
     * See https://de.wikipedia.org/wiki/Forsyth-Edwards-Notation#Figurenstellung
     * @param ch    the character to check which chessman is correspondingly
     * @return      the chessman. or null if a invalid character was checked.
     */
    private static Chessman getChessmanFromChar(char ch) {
        switch (ch){
            case 'r':
                return new Chessman(Chessman.Piece.ROOK, Chessman.Color.BLACK);
            case 'n':
                return new Chessman(Chessman.Piece.KNIGHT, Chessman.Color.BLACK);
            case 'b':
                return new Chessman(Chessman.Piece.BISHOP, Chessman.Color.BLACK);
            case 'q':
                return new Chessman(Chessman.Piece.QUEEN, Chessman.Color.BLACK);
            case 'k':
                return new Chessman(Chessman.Piece.KING, Chessman.Color.BLACK);
            case 'p':
                return new Chessman(Chessman.Piece.PAWN, Chessman.Color.BLACK);
            case 'R':
                return new Chessman(Chessman.Piece.ROOK, Chessman.Color.WHITE);
            case 'N':
                return new Chessman(Chessman.Piece.KNIGHT, Chessman.Color.WHITE);
            case 'B':
                return new Chessman(Chessman.Piece.BISHOP, Chessman.Color.WHITE);
            case 'Q':
                return new Chessman(Chessman.Piece.QUEEN, Chessman.Color.WHITE);
            case 'K':
                return new Chessman(Chessman.Piece.KING, Chessman.Color.WHITE);
            case 'P':
                return new Chessman(Chessman.Piece.PAWN, Chessman.Color.WHITE);
            default:
                Log.v("FENParser","could not find chessman correspondingly to this character");
                return null;
        }
    }

    public static CastlingManager getCastlingState(String fenNotation) {
        CastlingManager castlingManager = new CastlingManager(getChessmen(fenNotation));
        castlingManager.setKingSideWhiteFEN(fenNotation.split(" ")[2].contains("K"));
        castlingManager.setQueenSideWhiteFEN(fenNotation.split(" ")[2].contains("Q"));
        castlingManager.setKingSideBlackFEN(fenNotation.split(" ")[2].contains("k"));
        castlingManager.setQueenSideBlackFEN(fenNotation.split(" ")[2].contains("q"));
        return castlingManager;
    }

    public static EnPassant getEnPassantState(String fenNotation) {
        //TODO write this
        return new EnPassant(getChessmen(fenNotation));
    }
}
