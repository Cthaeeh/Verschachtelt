package com.example.kai.verschachtelt.pvAIGame.chess_AI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kai on 04.09.2016.
 * A speed relevant Class for generating possible ChessMoves.
 * See also: https://chessprogramming.wikispaces.com/10x12+Board
 * The code there is really crazy short, but does the same what this Class does.
 */
public class MoveGenerator {
    //Following must be positive !
    protected static final byte KING_WHITE = 10;
    protected static final byte QUEEN_WHITE = 9;
    protected static final byte ROOK_WHITE =5;
    protected static final byte KNIGHT_WHITE =4;
    protected static final byte BISHOP_WHITE =3;
    protected static final byte PAWN_WHITE =1;
    //Following must be negative !
    protected static final byte KING_BLACK = -10;
    protected static final byte QUEEN_BLACK = -9;
    protected static final byte ROOK_BLACK = -5;
    protected static final byte KNIGHT_BLACK = -4;
    protected static final byte BISHOP_BLACK = -3;
    protected static final byte PAWN_BLACK = - 1;

    //Offsets are always the delta-values for a specific chessman on a 10x12-Board.
    protected static final byte[] KING_OFFSETS =    { -11, -10, -9, -1, 1,  9, 10, 11 };
    protected static final byte[] KNIGHT_OFFSETS =  { -21, -19,-12, -8, 8, 12, 19, 21 };
    protected static final byte[] QUEEN_OFFSETS =   { -11, -10, -9, -1, 1,  9, 10, 11 };
    protected static final byte[] ROOK_OFFSETS =    { -10,  -1,  1, 10};
    protected static final byte[] BISHOP_OFFSETS =  { -11,  -9,  9, 11};

    protected static final byte INACCESSIBLE = -111;
    protected static final byte EMPTY = 0;

    //Constants for extra Information encoded in the board.
    protected static final int PLAYER_ON_TURN_EXTRA_FIELD = 129;
    protected static final byte BLACK = -112;
    protected static final byte WHITE = 112;

    static ArrayList<byte[]> moves = new ArrayList<>();
    static byte[]       board;

    /**
     * Method takes a byteBoard and returns all possibleMoves
     * @param boardToCalc
     */
    public static ArrayList<byte[]> generatePossibleMoves(byte[] boardToCalc){
        moves.clear();
        board = boardToCalc;
        if(board[PLAYER_ON_TURN_EXTRA_FIELD] == BLACK){
            for(int i = 21;i<99;i++){   //Iterate through board.
                switch (board[i]){  //Find chessman of the player on turn
                    case KING_BLACK:
                        generateBlackKingMoves(i);
                        break;
                    case QUEEN_BLACK:
                        generateBlackQueenMoves(i);
                        break;
                    case ROOK_BLACK:
                        generateBlackRookMoves(i);
                        break;
                    case KNIGHT_BLACK:
                        generateBlackKnightMoves(i);
                        break;
                    case BISHOP_BLACK:
                        generateBlackBishopMoves(i);
                        break;
                    case PAWN_BLACK:
                        generateBlackPawnMoves(i);
                        break;
                }
            }
        }else{
            for(int i = 21;i<99;i++){   //Iterate through board.
                switch (board[i]){  //Find chessman of the player on turn
                    case KING_WHITE:
                        generateWhiteKingMoves(i);
                        break;
                    case QUEEN_WHITE:
                        generateWhiteQueenMoves(i);
                        break;
                    case ROOK_WHITE:
                        generateWhiteRookMoves(i);
                        break;
                    case KNIGHT_WHITE:
                        generateWhiteKnightMoves(i);
                        break;
                    case BISHOP_WHITE:
                        generateWhiteBishopMoves(i);
                        break;
                    case PAWN_WHITE:
                        generateWhitePawnMoves(i);
                        break;
                }
            }
        }
        return moves;
    }


    //King
    private static void generateWhiteKingMoves(int startPos) {
        for(int i = 0;i < 8;i++){   //Iterate through all 8 possible Moves
            int destinationPos = startPos + KING_OFFSETS[i];
            byte destinationValue = board[destinationPos];
            if(destinationValue<=0&&destinationValue!=INACCESSIBLE){    //Cant move to INACCESSIBLE Fields or friendly fire(Because white is always >=0).
                addMove(startPos,destinationPos,KING_WHITE);
            }
        }
        //TODO eventually ad Castling possibility.
    }

    private static void generateBlackKingMoves(int startPos) {
        for(int i = 0;i < 8;i++){   //Iterate through all 8 possible Moves
            int destinationPos = startPos + KING_OFFSETS[i];
            byte destinationValue = board[destinationPos];
            if(destinationValue>=0&&destinationValue!=INACCESSIBLE){    //Cant move to INACCESSIBLE Fields or friendly fire(Because white is always >=0).
                addMove(startPos,destinationPos,KING_BLACK);
            }
        }
        //TODO eventually ad Castling possibility.
    }


    //Knight
    private static void generateBlackKnightMoves(int startPos) {
        for(int i = 0;i < 8;i++){   //Iterate through all 8 possible Moves
            int destinationPos = startPos + KNIGHT_OFFSETS[i];
            byte destinationValue = board[destinationPos];
            if(destinationValue>=0&&destinationValue!=INACCESSIBLE){    //Cant move to INACCESSIBLE Fields or friendly fire(Because white is always >=0).
                addMove(startPos,destinationPos,KNIGHT_BLACK);
            }
        }
    }

    private static void generateWhiteKnightMoves(int startPos) {
        for(int i = 0;i < 8;i++){   //Iterate through all 8 possible Moves
            int destinationPos = startPos + KNIGHT_OFFSETS[i];
            byte destinationValue = board[destinationPos];
            if(destinationValue<=0&&destinationValue!=INACCESSIBLE){    //Cant move to INACCESSIBLE Fields or friendly fire(Because white is always >=0).
                addMove(startPos,destinationPos,KNIGHT_WHITE);
            }
        }
    }


    //Queen
    private static void generateWhiteQueenMoves(int startPos) {
        for(int direction = 0;direction < 8;direction++){   //Iterate through all 8 possible Directions
            for(int distance = 1; distance<8;distance++) {  //Iterate through distances starting with small distance (max distance is 8!)
                int destinationPos = startPos+QUEEN_OFFSETS[direction]*distance;
                byte destinationValue = board[destinationPos];
                if(destinationValue==INACCESSIBLE)break;    //Stop when you hit a Wall
                if(destinationValue>0)break;                //Stop when you hit friendly chessman
                addMove(startPos,destinationPos,QUEEN_WHITE);
            }
        }
    }

    private static void generateBlackQueenMoves(int startPos) {
        for(int direction = 0;direction < 8;direction++){   //Iterate through all 8 possible Directions
            for(int distance = 1; distance<8;distance++) {  //Iterate through distances starting with small distance (max distance is 8!)
                int destinationPos = startPos+QUEEN_OFFSETS[direction]*distance;
                byte destinationValue = board[destinationPos];
                if(destinationValue==INACCESSIBLE)break;    //Stop when you hit a Wall
                if(destinationValue<0)break;                //Stop when you hit friendly chessman
                addMove(startPos,destinationPos,QUEEN_BLACK);
            }
        }
    }


    //Rook
    private static void generateWhiteRookMoves(int startPos) {
        for(int direction = 0;direction < 4;direction++){   //Iterate through all 4 possible Directions
            for(int distance = 1; distance<8;distance++) {  //Iterate through distances starting with small distance (max distance is 8!)
                int destinationPos = startPos+ROOK_OFFSETS[direction]*distance;
                byte destinationValue = board[destinationPos];
                if(destinationValue==INACCESSIBLE)break;    //Stop when you hit a Wall
                if(destinationValue>0)break;                //Stop when you hit friendly chessman
                addMove(startPos,destinationPos,ROOK_WHITE);
            }
        }
    }

    private static void generateBlackRookMoves(int startPos) {
        for(int direction = 0;direction < 4;direction++){   //Iterate through all 4 possible Directions
            for(int distance = 1; distance<8;distance++) {  //Iterate through distances starting with small distance (max distance is 8!)
                int destinationPos = startPos+ROOK_OFFSETS[direction]*distance;
                byte destinationValue = board[destinationPos];
                if(destinationValue==INACCESSIBLE)break;    //Stop when you hit a Wall
                if(destinationValue<0)break;                //Stop when you hit friendly chessman
                addMove(startPos,destinationPos,ROOK_BLACK);
            }
        }
    }


    //Bhishop
    private static void generateWhiteBishopMoves(int startPos) {
        for(int direction = 0;direction < 4;direction++){   //Iterate through all 4 possible Directions
            for(int distance = 1; distance<8;distance++) {  //Iterate through distances starting with small distance (max distance is 8!)
                int destinationPos = startPos+ BISHOP_OFFSETS[direction]*distance;
                byte destinationValue = board[destinationPos];
                if(destinationValue==INACCESSIBLE)break;    //Stop when you hit a Wall
                if(destinationValue>0)break;                //Stop when you hit friendly chessman
                addMove(startPos,destinationPos,BISHOP_WHITE);
            }
        }
    }

    private static void generateBlackBishopMoves(int startPos) {
        for(int direction = 0;direction < 4;direction++){   //Iterate through all 4 possible Directions
            for(int distance = 1; distance<8;distance++) {  //Iterate through distances starting with small distance (max distance is 8!)
                int destinationPos = startPos+ BISHOP_OFFSETS[direction]*distance;
                byte destinationValue = board[destinationPos];
                if(destinationValue==INACCESSIBLE)break;    //Stop when you hit a Wall
                if(destinationValue<0)break;                //Stop when you hit friendly chessman
                addMove(startPos,destinationPos,BISHOP_BLACK);
            }
        }
    }

    //Pawn
    private static void generateWhitePawnMoves(int startPos) {
        if(startPos/10==8){             //Baseline
            if(board[startPos-10]==EMPTY&&board[startPos-20]==EMPTY){   //If fields in front are empty
                addMove(startPos,startPos-20,PAWN_WHITE);               //Add the Move.
            }
        }
        if(startPos/10==3){                                             //If pawn is near promotion
            if(board[startPos-10]==EMPTY)addMove(startPos,startPos-10,QUEEN_WHITE);//If empty field ahead can move.
            if(board[startPos-11]<0)     addMove(startPos,startPos-11,QUEEN_WHITE);//If a enemy is diagonal can kill.
            if(board[startPos-9]<0)      addMove(startPos,startPos-9,QUEEN_WHITE);
        }else{
            if(board[startPos-10]==EMPTY)addMove(startPos,startPos-10,PAWN_WHITE);//If empty field ahead can move.
            if(board[startPos-11]<0)     addMove(startPos,startPos-11,PAWN_WHITE);//If a enemy is diagonal can kill.
            if(board[startPos-9]<0)      addMove(startPos,startPos-9,PAWN_WHITE);
        }
    }

    private static void generateBlackPawnMoves(int startPos) {
        if(startPos/10==3){             //Baseline
            if(board[startPos+10]==EMPTY&&board[startPos+20]==EMPTY){   //If fields in front are empty
                addMove(startPos,startPos+20,PAWN_BLACK);               //Add the "jump" Move.
            }
        }
        if(startPos/10==8){                                             //If pawn is near promotion
            if(board[startPos+10]==EMPTY)addMove(startPos,startPos+10,QUEEN_BLACK);//If empty field ahead can move.
            if(board[startPos+11]>0)     addMove(startPos,startPos+11,QUEEN_BLACK);//If a enemy is diagonal can kill.
            if(board[startPos+9]>0)      addMove(startPos,startPos+9, QUEEN_BLACK);
        }else{
            if(board[startPos+10]==EMPTY)addMove(startPos,startPos+10,PAWN_BLACK);//If empty field ahead can move.
            if(board[startPos+11]>0)     addMove(startPos,startPos+11,PAWN_BLACK);//If a enemy is diagonal can kill.
            if(board[startPos+9]>0)      addMove(startPos,startPos+9, PAWN_BLACK);
        }
    }

    /**
     * Method adds a Move to our possibleMovesList. Works for simple Moves, not Castling, En Passant, Pawn promoting.
     * @param startPos the starting Position of a Move
     * @param destinationPos  the destination Position of a Move
     * @param chessmanValue  the chessman that is moved.
     */
    private static void addMove(int startPos, int destinationPos, byte chessmanValue) {
        byte[] boardAfterMove = board.clone();
        boardAfterMove[destinationPos]=chessmanValue;   //Make move
        boardAfterMove[startPos]=0;
        moves.add(boardAfterMove);                  //Add to possible Moves
    }

}
