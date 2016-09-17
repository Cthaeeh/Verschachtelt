package com.example.kai.verschachtelt.pvAIGame.ai;

import java.util.Arrays;

/**
 * Created by Kai on 04.09.2016.
 * A speed relevant Class for generating possible ChessMoves.
 * See also: https://chessprogramming.wikispaces.com/10x12+Board
 * The code there is really crazy short, but does the same what this Class does.
 */
public class MoveGenerator {
    //Constants for representing certain Chessman:
    //Following must be positive !
    protected static final byte KING_WHITE      = 6;
    protected static final byte QUEEN_WHITE     = 5;
    protected static final byte ROOK_WHITE      = 4;
    protected static final byte KNIGHT_WHITE    = 3;
    protected static final byte BISHOP_WHITE    = 2;
    protected static final byte PAWN_WHITE      = 1;
    //Following must be negative !
    protected static final byte KING_BLACK      = -KING_WHITE;
    protected static final byte QUEEN_BLACK     = -QUEEN_WHITE;
    protected static final byte ROOK_BLACK      = -ROOK_WHITE;
    protected static final byte KNIGHT_BLACK    = -KNIGHT_WHITE;
    protected static final byte BISHOP_BLACK    = -BISHOP_WHITE;
    protected static final byte PAWN_BLACK      = -PAWN_WHITE;

    //Offsets are always the delta-values for a specific chessman on a 10x12-Board.
    protected static final byte[] KING_OFFSETS =    { -11, -10, -9, -1, 1,  9, 10, 11 };
    protected static final byte[] KNIGHT_OFFSETS =  { -21, -19,-12, -8, 8, 12, 19, 21 };
    protected static final byte[] QUEEN_OFFSETS =   { -11, -10, -9, -1, 1,  9, 10, 11 };
    protected static final byte[] ROOK_OFFSETS =    { -10,  -1,  1, 10};
    protected static final byte[] BISHOP_OFFSETS =  { -11,  -9,  9, 11};

    protected static final byte INACCESSIBLE = -111;
    protected static final byte EMPTY = 0;

    //Constants for extra Information encoded in the board.
    protected static final int GAME_HAS_ENDED_EXTRA_FIELD = 127;
    protected static final byte FALSE = -112;
    protected static final byte TRUE = 112;

    protected static final int PLAYER_ON_TURN = 129;
    protected static final byte BLACK = -1;
    protected static final byte WHITE = 1;

    private static int[]  moves = new int[100];
    private static int[]  präCalcMoves = new int[100];
    private static byte    moveCounter = 0;
    private static byte[] board;
    private static boolean[] attackMap = new boolean[99];
    private static boolean wasPreCalculated = false;
    private static IntStack extraInfoStack;

    public static void setRoot(byte[] root, int extraInfo) {
        board = root;
        extraInfoStack = new IntStack(10);  //TODO use the depth as stack size
        extraInfoStack.push(extraInfo);
    }

    /**
     * Method returns all possibleMoves
     */
    public static int[] generatePossibleMoves(){
        if(wasPreCalculated){
            wasPreCalculated = false;
            return präCalcMoves;
        }
        moveCounter = 0;
        if(board[PLAYER_ON_TURN] == BLACK){     //TODO make this if else
            for(int i = 21;i<99;i++){   //Iterate through board.
                switch (board[i]){  //Find chessman of the player on turn
                    case PAWN_BLACK:
                        generateBlackPawnMoves(i);
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
                    case KING_BLACK:
                        generateBlackKingMoves(i);
                        break;
                    case QUEEN_BLACK:
                        generateBlackQueenMoves(i);
                        break;
                }
            }
        }else{
            for(int i = 21;i<99;i++){   //Iterate through board.
                switch (board[i]){  //Find chessman of the player on turn
                    case PAWN_WHITE:
                        generateWhitePawnMoves(i);
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
                    case KING_WHITE:
                        generateWhiteKingMoves(i);
                        break;
                    case QUEEN_WHITE:
                        generateWhiteQueenMoves(i);
                        break;
                }
            }
        }
        return Arrays.copyOf(moves,moveCounter);
    }

    /**
     *
     * @param previousMove
     * @return returns true if the move was legal in view of the follwing moves. false if not.
     */
    public static boolean wasLegalMove(int previousMove) {
        attackMap = new boolean[99];        //Reset attack map
        präCalcMoves = generatePossibleMoves();    //generate pseudo legal Possible Moves.
        wasPreCalculated = true;
        if(abs(board[MoveAsInteger.getDest(previousMove)]) == KING_WHITE){  //If the moving piece is a King;
            if (MoveAsInteger.getDest(previousMove) - MoveAsInteger.getStart(previousMove) == 2) {   //And he did a jump to the right, e.g moved 2 pieces
                if(attackMap[MoveAsInteger.getStart(previousMove)])return false;
                if(attackMap[MoveAsInteger.getStart(previousMove)+1])return false;
            }
            if (MoveAsInteger.getDest(previousMove) - MoveAsInteger.getStart(previousMove) == -2) {   //And he did a jump to the left, e.g moved 2 pieces
                if(attackMap[MoveAsInteger.getStart(previousMove)])return false;
                if(attackMap[MoveAsInteger.getStart(previousMove)-1])return false;
            }
        }
        //Check if King is in check.
        for(int i = 0; i< 99;i++){
            if(abs(board[i])*-PLAYER_ON_TURN== KING_WHITE && attackMap[i]){
                return false;
            }
        }
        return true;
    }

    /**
     * Very simple move ordering.
     * @param moves
     */
    private static int[] sortMoves(int[] moves) {
        return moves;
    }

    //King
    private static void generateWhiteKingMoves(int startPos) {
        for(int i = 0;i < 8;i++){   //Iterate through all 8 possible Moves
            int destinationPos = startPos + KING_OFFSETS[i];
            byte destinationValue = board[destinationPos];
            if(destinationValue<=0&&destinationValue!=INACCESSIBLE){    //Cant move to INACCESSIBLE Fields or friendly fire(Because white is always >=0).
                addMove(startPos,destinationPos);
            }
        }
        generateKingSideCastlingMoves(startPos);
        generateQueenSideCastlingMoves(startPos);
    }

    private static void generateBlackKingMoves(int startPos) {
        for(int i = 0;i < 8;i++){   //Iterate through all 8 possible Moves
            int destinationPos = startPos + KING_OFFSETS[i];
            byte destinationValue = board[destinationPos];
            if(destinationValue>=0&&destinationValue!=INACCESSIBLE){    //Cant move to INACCESSIBLE Fields or friendly fire(Because white is always >=0).
                addMove(startPos,destinationPos);
            }
        }
        generateKingSideCastlingMoves(startPos);
        generateQueenSideCastlingMoves(startPos);
    }

    //Castling
    /**
     * Adds pseudo legal castling moves queen side.
     * Checks if the path is free and the pieces have been moved before,
     * BUT NOT if the king is in check or one of the fields are attacked.
     * @param startPos position of the king
     */
    private static void generateQueenSideCastlingMoves(int startPos) {
        //Two neighboring fields must be empty.
        if(board[startPos-1]!=EMPTY)return;
        if(board[startPos-2]!=EMPTY)return;
        if(board[startPos-3]!=EMPTY)return;
        //Castling must be allowed (chessman have not been moved before.)
        if(ExtraInfoAsInt.getQueenSideWhiteCastlingRight(extraInfoStack.peek())){
            moves[moveCounter] = MoveAsInteger.getMoveAsInt(startPos,startPos-2,board[startPos-2]);
            moveCounter++;
        }
        if(ExtraInfoAsInt.getQueenSideBlackCastlingRight(extraInfoStack.peek())){
            moves[moveCounter] = MoveAsInteger.getMoveAsInt(startPos,startPos-2,board[startPos-2]);
            moveCounter++;
        }
    }

    /**
     * Adds pseudo legal castling moves king side.
     * Checks if the path is free and the pieces have been moved before,
     * BUT NOT if the king is in check or one of the fields are attacked.
     * @param startPos position of the king
     */
    private static void generateKingSideCastlingMoves(int startPos) {
        //Two neighboring fields must be empty.
        if(board[startPos+1]!=EMPTY)return;
        if(board[startPos+2]!=EMPTY)return;
        //Castling must be allowed (chessman have not been moved before.)
        if(ExtraInfoAsInt.getKingSideWhiteCastlingRight(extraInfoStack.peek())){
            addMove(startPos,startPos+2);

        }
        if(ExtraInfoAsInt.getKingSideBlackCastlingRight(extraInfoStack.peek())) addMove(startPos,startPos+2);
    }

    //Knight
    private static void generateBlackKnightMoves(int startPos) {
        for(int i = 0;i < 8;i++){   //Iterate through all 8 possible Moves
            int destinationPos = startPos + KNIGHT_OFFSETS[i];
            byte destinationValue = board[destinationPos];
            if(destinationValue>=0&&destinationValue!=INACCESSIBLE){    //Cant move to INACCESSIBLE Fields or friendly fire(Because white is always >=0).
                addMove(startPos,destinationPos);
            }
        }
    }

    private static void generateWhiteKnightMoves(int startPos) {
        for(int i = 0;i < 8;i++){   //Iterate through all 8 possible Moves
            int destinationPos = startPos + KNIGHT_OFFSETS[i];
            byte destinationValue = board[destinationPos];
            if(destinationValue<=0&&destinationValue!=INACCESSIBLE){    //Cant move to INACCESSIBLE Fields or friendly fire(Because white is always >=0).
                addMove(startPos,destinationPos);
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
                if(destinationValue<0){
                    addMove(startPos,destinationPos);//Stop when you hit a enemy but add the kill move.
                    break;
                }
                addMove(startPos,destinationPos);
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
                if(destinationValue>0){
                    addMove(startPos,destinationPos);//Stop when you hit a enemy but add the kill move.
                    break;
                }
                addMove(startPos,destinationPos);
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
                if(destinationValue<0){
                    addMove(startPos,destinationPos);//Stop when you hit a enemy but add the kill move.
                    break;
                }
                addMove(startPos,destinationPos);
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
                if(destinationValue>0){
                    addMove(startPos,destinationPos);//Stop when you hit a enemy but add the kill move.
                    break;
                }
                addMove(startPos,destinationPos);
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
                if(destinationValue<0){
                    addMove(startPos,destinationPos);
                    break;
                }
                addMove(startPos,destinationPos);
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
                if(destinationValue>0){
                    addMove(startPos,destinationPos);
                    break;
                }
                addMove(startPos,destinationPos);
            }
        }
    }

    //Pawn
    private static void generateWhitePawnMoves(int startPos) {
        if(startPos/10==8){             //Baseline
            if(board[startPos-10]==EMPTY&&board[startPos-20]==EMPTY){   //If fields in front are empty
                addMove(startPos,startPos-20);               //Add the Move.
            }
        }
        if(startPos/10==3){                                             //If pawn is near promotion
            if(board[startPos-10]==EMPTY)addMove(startPos,startPos-10);//If empty field ahead can move.
            if(board[startPos-11]<0)     addMove(startPos,startPos-11);//If a enemy is diagonal can kill.
            if(board[startPos-9]<0)      addMove(startPos,startPos-9);
        }else{
            if(board[startPos-10]==EMPTY)addMove(startPos,startPos-10);//If empty field ahead can move.
            if(board[startPos-11]<0)     addMove(startPos,startPos-11);//If a enemy is diagonal can kill.
            if(board[startPos-9]<0)      addMove(startPos,startPos-9);
        }
    }

    private static void generateBlackPawnMoves(int startPos) {
        if(startPos/10==3){             //Baseline
            if(board[startPos+10]==EMPTY&&board[startPos+20]==EMPTY){   //If fields in front are empty
                addMove(startPos,startPos+20);               //Add the "jump" Move.
            }
        }
        if(startPos/10==8){                                             //If pawn is near promotion
            if(board[startPos+10]==EMPTY)addMove(startPos,startPos+10);//If empty field ahead can move.
            if(board[startPos+11]>0)     addMove(startPos,startPos+11);//If a enemy is diagonal can kill.
            if(board[startPos+9]>0)      addMove(startPos,startPos+9);
        }else{
            if(board[startPos+10]==EMPTY)addMove(startPos,startPos+10);//If empty field ahead can move.
            if(board[startPos+11]>0)     addMove(startPos,startPos+11);//If a enemy is diagonal can kill.
            if(board[startPos+9]>0)      addMove(startPos,startPos+9);
        }
    }

    /**
     * Method adds a Move to our possibleMovesList. Works for simple Moves, not Castling, En Passant, Pawn promoting.
     * Or if we just want to count the number of possible moves, it adds them up to mobility
     * @param startPos the starting Position of a Move
     * @param destinationPos  the destination Position of a Move
     */
    private static void addMove(int startPos, int destinationPos) {
        //Add to possible Moves
        moves[moveCounter] = MoveAsInteger.getMoveAsInt(startPos,destinationPos,board[destinationPos]);
        attackMap[destinationPos]=true;
        moveCounter++;
    }

    public static byte[] makeMove(int move){
        int newExtraInfo =  extraInfoStack.peek();
        //Make regular movement
        board[MoveAsInteger.getDest(move)] = board[MoveAsInteger.getStart(move)];
        board[MoveAsInteger.getStart(move)] = EMPTY;
        //Make castling
        if(board[MoveAsInteger.getDest(move)]*PLAYER_ON_TURN == KING_WHITE){  //If the moving piece is a King (*PLAYER_ON_TURN always gives the positive value;
            if(MoveAsInteger.getDest(move)-MoveAsInteger.getStart(move)==2) {   //And he did a jump to the right, e.g moved 2 pieces
                board[MoveAsInteger.getDest(move)-1] = board[MoveAsInteger.getDest(move)+1];    //Move the rook accordingly
                board[MoveAsInteger.getDest(move)+1] = EMPTY;                                   //Let him jump over the King
            }
            if(MoveAsInteger.getDest(move)-MoveAsInteger.getStart(move)==-2) {   //And he did a jump to the left, e.g moved 2 pieces
                board[MoveAsInteger.getDest(move)+1] = board[MoveAsInteger.getDest(move)-2];    //Move the rook accordingly
                board[MoveAsInteger.getDest(move)-2] = EMPTY;                                   //Let him jump over the King
            }
            if(board[PLAYER_ON_TURN]==WHITE){
                newExtraInfo = ExtraInfoAsInt.setQueenSideWhiteCastlingRight(false,newExtraInfo);
                newExtraInfo = ExtraInfoAsInt.setKingSideWhiteCastlingRight(false,newExtraInfo);
            }else {
                newExtraInfo = ExtraInfoAsInt.setQueenSideBlackCastlingRight(false,newExtraInfo);
                newExtraInfo = ExtraInfoAsInt.setKingSideBlackCastlingRight(false,newExtraInfo);
            }
        }
        if(board[MoveAsInteger.getDest(move)] == ROOK_WHITE){
            if(MoveAsInteger.getStart(move)==98)ExtraInfoAsInt.setKingSideWhiteCastlingRight(false,newExtraInfo);
            if(MoveAsInteger.getStart(move)==91)ExtraInfoAsInt.setKingSideWhiteCastlingRight(false,newExtraInfo);
        }
        if(board[MoveAsInteger.getDest(move)] == ROOK_BLACK){
            if(MoveAsInteger.getStart(move)==28)ExtraInfoAsInt.setKingSideBlackCastlingRight(false,newExtraInfo);
            if(MoveAsInteger.getStart(move)==21)ExtraInfoAsInt.setKingSideBlackCastlingRight(false,newExtraInfo);
        }

        //TODO make promotion, en passant

        //Change player on turn.
        board[PLAYER_ON_TURN]*=-1;
        extraInfoStack.push(newExtraInfo);
        return board;
    }

    public static void unMakeMove(int move){
        extraInfoStack.pop();       //Go back in the history of extra Info.
        wasPreCalculated = false;   //If we go back in "time" the präCalculated moves aren´t applicable anymore.
        //Unmake regular movement
        board[MoveAsInteger.getStart(move)] = board[MoveAsInteger.getDest(move)];   //Take chessman back
        board[MoveAsInteger.getDest(move)] = MoveAsInteger.getCapture(move);        //Take eventually captured piece back.
        //Unmake eventual castling
        if(board[MoveAsInteger.getStart(move)]*PLAYER_ON_TURN == KING_WHITE){  //If the moving piece is a King (*PLAYER_ON_TURN always gives the positive value;
            if(MoveAsInteger.getDest(move)-MoveAsInteger.getStart(move)==2) {   //And he did a jump to the right, e.g moved 2 pieces
                board[MoveAsInteger.getDest(move)+1] = board[MoveAsInteger.getDest(move)-1];    //Move the rook accordingly
                board[MoveAsInteger.getDest(move)-1] = EMPTY;                                   //Let him jump over the King
            }
            if(MoveAsInteger.getDest(move)-MoveAsInteger.getStart(move)==-2) {   //And he did a jump to the left, e.g moved 2 pieces
                board[MoveAsInteger.getDest(move)-2] = board[MoveAsInteger.getDest(move)+1];    //Move the rook accordingly
                board[MoveAsInteger.getDest(move)+1] = EMPTY;                                   //Let him jump over the King
            }
        }
        //TODO unmake promotion, en passant
        board[PLAYER_ON_TURN]*=-1;  //Change player on turn.
    }

    public static byte[] getBoard() {
        return board;
    }


    private static byte abs(byte b) {
        if(b<0)return (byte) (b*-1);
        return b;
    }
}
