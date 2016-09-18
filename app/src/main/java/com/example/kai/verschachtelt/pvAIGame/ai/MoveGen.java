package com.example.kai.verschachtelt.pvAIGame.ai;

import java.util.Arrays;

/**
 * Created by Kai on 04.09.2016.
 * A speed relevant Class for generating possible ChessMoves.
 * See also: https://chessprogramming.wikispaces.com/10x12+Board
 * The code there (at the link) is really crazy short, but does the same what this Class does.
 */
public class MoveGen {
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
    protected static final int PLAYER_ON_TURN = 129;    //Don´t change unless you really know what your doing
    protected static final byte BLACK = -1;
    protected static final byte WHITE = 1;

    private static int[]   moves = new int[100];        //The moves we will calculate .
    private static int[] moveValues =  new int[100];    //The interestingness of the moves

    //Quick access for the mvvLVA See: https://chessprogramming.wikispaces.com/MVV-LVA
    private static final short[] victimScore = {600,500,400,300,200,100,0,100,200,300,400,500,600};
    private static short[][]     mvvLVAScore = new short[13][13];

    private static int[]  präCalcMoves = new int[100];  //Sometimes we want to calc the follwing moves beforehand to see if castling / check was legal.
    private static boolean wasPreCalculated = false;    //Then we can reuse them

    private static byte    moveCounter = 0;             //The number of moves we found for the current position.
    private static byte[] board;                        //HERE HAPPENS EVERYTHING
    private static boolean[] attackMap = new boolean[120];

    private static IntStack extraInfoStack;
    private static int currentDepth = 0;

    public static void initialise(byte[] root, int extraInfo) {
        board = root;
        for(int i = 0; i< victimScore.length;i++){
            for(int j = 0;j < victimScore.length; j++){
                mvvLVAScore[i][j] = (short) (victimScore[i] + victimScore[j]/10);//Setup the mvvLVA array
            }
        }
        extraInfoStack = new IntStack(10);  //TODO use the depth as stack size
        extraInfoStack.push(extraInfo);
    }

    /**
     * Method returns all possibleMoves at the current state of the board.
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
        return sortMoves(Arrays.copyOf(moves,moveCounter));
    }

    /**
     *
     * @param previousMove
     * @param depth
     * @return returns true if the move was legal in view of the follwing moves. false if not.
     */
    public static boolean wasLegalMove(int previousMove, int depth) {   //TODO make this work
        attackMap = new boolean[120];                //Reset attack map
        präCalcMoves = generatePossibleMoves();     //generate pseudo legal Possible Moves.
        wasPreCalculated = true;
        if(abs(board[MoveAsInt.getDest(previousMove)]) == KING_WHITE){  //If the moving piece is a King;
            if (MoveAsInt.getDest(previousMove) - MoveAsInt.getStart(previousMove) == 2) {   //And he did a jump to the right, e.g moved 2 pieces
                if(attackMap[MoveAsInt.getStart(previousMove)])return false;
                if(attackMap[MoveAsInt.getStart(previousMove)+1])return false;
            }
            if (MoveAsInt.getDest(previousMove) - MoveAsInt.getStart(previousMove) == -2) {   //And he did a jump to the left, e.g moved 2 pieces
                if(attackMap[MoveAsInt.getStart(previousMove)])return false;
                if(attackMap[MoveAsInt.getStart(previousMove)-1])return false;
            }
        }
        for(int i = 0; i< 99;i++){
            if((board[i]*(-board[PLAYER_ON_TURN]) == KING_WHITE) && (attackMap[i])){
                int x = 0;
                return false;
            }
        }
        return true;
    }

    private static byte abs(byte b) {
        byte value;
        if(b<0){
            return (byte) (b*-1);
        }
        return b;
    }

    /**
     * Very simple move ordering.
     * @param moves
     */
    private static int[] sortMoves(int[] moves) {
        /*
        insertion Sort descending order.
        The Moves are sorted by there interestingness, which can be found in moveValues.
        */
        for (int i = 0; i < moves.length-1; i++) {
            int j = i + 1;
            int toSort = moves[j];
            int tmp    = moveValues[j];
            while (j > 0 && tmp > moveValues[j-1]) {
                moveValues[j] = moveValues[j-1];
                moves[j] = moves[j-1];
                j--;
            }
            moveValues[j] = tmp;
            moves[j] = toSort;
        }
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
            attackMap[destinationPos]=true;
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
            attackMap[destinationPos]=true;
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
        if(BoardInfoAsInt.getQueenSideWhiteCastlingRight(extraInfoStack.peek())) addMove(startPos,startPos-2);
        if(BoardInfoAsInt.getQueenSideBlackCastlingRight(extraInfoStack.peek())) addMove(startPos,startPos-2);
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
        if(BoardInfoAsInt.getKingSideWhiteCastlingRight(extraInfoStack.peek())) addMove(startPos,startPos+2);
        if(BoardInfoAsInt.getKingSideBlackCastlingRight(extraInfoStack.peek())) addMove(startPos,startPos+2);
    }

    //Knight
    private static void generateBlackKnightMoves(int startPos) {
        for(int i = 0;i < 8;i++){   //Iterate through all 8 possible Moves
            int destinationPos = startPos + KNIGHT_OFFSETS[i];
            byte destinationValue = board[destinationPos];
            if(destinationValue>=0&&destinationValue!=INACCESSIBLE){    //Cant move to INACCESSIBLE Fields or friendly fire(Because white is always >=0).
                addMove(startPos,destinationPos);
            }
            attackMap[destinationPos]=true;
        }
    }

    private static void generateWhiteKnightMoves(int startPos) {
        for(int i = 0;i < 8;i++){   //Iterate through all 8 possible Moves
            int destinationPos = startPos + KNIGHT_OFFSETS[i];
            byte destinationValue = board[destinationPos];
            if(destinationValue<=0&&destinationValue!=INACCESSIBLE){    //Cant move to INACCESSIBLE Fields or friendly fire(Because white is always >=0).
                addMove(startPos,destinationPos);
            }
            attackMap[destinationPos]=true;
        }
    }


    //Queen
    private static void generateWhiteQueenMoves(int startPos) {
        for(int direction = 0;direction < 8;direction++){   //Iterate through all 8 possible Directions
            for(int distance = 1; distance<8;distance++) {  //Iterate through distances starting with small distance (max distance is 8!)
                int destinationPos = startPos+QUEEN_OFFSETS[direction]*distance;
                byte destinationValue = board[destinationPos];
                if(destinationValue==INACCESSIBLE)break;    //Stop when you hit a Wall
                if(destinationValue>0){
                    attackMap[destinationPos]=true;
                    break;                //Stop when you hit friendly chessman
                }
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
                if(destinationValue<0){
                    attackMap[destinationPos]=true;
                    break;                //Stop when you hit friendly chessman
                }
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
                if(destinationValue>0){
                    attackMap[destinationPos]=true;
                    break;                //Stop when you hit friendly chessman
                }
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
                if(destinationValue<0){
                    attackMap[destinationPos]=true;
                    break;                //Stop when you hit friendly chessman
                }
                if(destinationValue>0){
                    addMove(startPos,destinationPos);//Stop when you hit a enemy but add the kill move.
                    break;
                }
                addMove(startPos,destinationPos);
            }
        }
    }


    //Bishop
    private static void generateWhiteBishopMoves(int startPos) {
        for(int direction = 0;direction < 4;direction++){   //Iterate through all 4 possible Directions
            for(int distance = 1; distance<8;distance++) {  //Iterate through distances starting with small distance (max distance is 8!)
                int destinationPos = startPos+ BISHOP_OFFSETS[direction]*distance;
                byte destinationValue = board[destinationPos];
                if(destinationValue==INACCESSIBLE)break;    //Stop when you hit a Wall
                if(destinationValue>0){
                    attackMap[destinationPos]=true;
                    break;                //Stop when you hit friendly chessman
                }
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
                if(destinationValue<0){
                    attackMap[destinationPos]=true;
                    break;                //Stop when you hit friendly chessman
                }
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
                attackMap[startPos-20]=false;
            }
        }
        if(startPos/10==3){                                             //If pawn is near promotion
            if(board[startPos-10]==EMPTY){                              //If empty field ahead can move.
                addPromotionMove(startPos,startPos-10,QUEEN_WHITE);
                addPromotionMove(startPos,startPos-10,KNIGHT_WHITE);
                attackMap[startPos-10]=false;
            }
            if(board[startPos-11]<0&&board[startPos-11]!=INACCESSIBLE){ //If a enemy is diagonal can kill.
                addPromotionMove(startPos,startPos-11,QUEEN_WHITE);
                addPromotionMove(startPos,startPos-11,KNIGHT_WHITE);
            }
            if(board[startPos-9]<0 &&board[startPos-9]!=INACCESSIBLE){
                addPromotionMove(startPos,startPos-9,QUEEN_WHITE);
                addPromotionMove(startPos,startPos-9,KNIGHT_WHITE);
            }
        }else{
            if(board[startPos-10]==EMPTY){
                addMove(startPos,startPos-10);//If empty field ahead can move.
                attackMap[startPos-10]=false;
            }
            if(board[startPos-11]<0&&board[startPos-11]!=INACCESSIBLE)     addMove(startPos,startPos-11);//If a enemy is diagonal can kill.
            if(board[startPos-9]<0 &&board[startPos-9]!=INACCESSIBLE)      addMove(startPos,startPos-9);
        }
    }

    private static void generateBlackPawnMoves(int startPos) {
        if(startPos/10==3){             //Baseline
            if(board[startPos+10]==EMPTY&&board[startPos+20]==EMPTY){   //If fields in front are empty
                addMove(startPos,startPos+20);               //Add the "jump" Move.
                attackMap[startPos+20]=false;
            }
        }
        if(startPos/10==8){                                             //If pawn is near promotion
            if(board[startPos+10]==EMPTY){                              //If empty field ahead can move.
                addPromotionMove(startPos,startPos+10,QUEEN_WHITE);
                addPromotionMove(startPos,startPos+10,KNIGHT_WHITE);
                attackMap[startPos+10]=false;
            }
            if(board[startPos+11]>0){                                   //If a enemy is diagonal can kill.
                addPromotionMove(startPos,startPos+11,QUEEN_WHITE);
                addPromotionMove(startPos,startPos+11,KNIGHT_WHITE);
            }
            if(board[startPos+9]>0){
                addPromotionMove(startPos,startPos+9,QUEEN_WHITE);
                addPromotionMove(startPos,startPos+9,KNIGHT_WHITE);
            }
        }else{
            if(board[startPos+10]==EMPTY){
                addMove(startPos,startPos+10);//If empty field ahead can move.
                attackMap[startPos+10]=false;
            }
            if(board[startPos+11]>0)     addMove(startPos,startPos+11);//If a enemy is diagonal can kill.
            if(board[startPos+9]>0)      addMove(startPos,startPos+9);
        }
    }

    private static void addPromotionMove(int startPos, int destinationPos, byte promotedPiece){
        moves[moveCounter] = MoveAsInt.getPromotionMoveAsInt(startPos,destinationPos,board[destinationPos],promotedPiece);
        moveValues[moveCounter] = 590;  //TODO remove magic number
        attackMap[destinationPos]=true;
        moveCounter++;
    }

    /**
     * Method adds a Move to our possibleMovesList. Works for simple Moves, not En Passant, Pawn promoting.
     * Castling is here considered a simple move, because its just a king that moves 2 pieces
     * @param startPos the starting Position of a Move
     * @param destinationPos  the destination Position of a Move
     */
    private static void addMove(int startPos, int destinationPos) {
        moves[moveCounter] = MoveAsInt.getMoveAsInt(startPos,destinationPos,board[destinationPos]);
        if(board[destinationPos]!=EMPTY){
            moveValues[moveCounter] =mvvLVAScore[6+board[destinationPos]][6+board[startPos]];  //MVV-LVA Scores
        }else { //Quiet move.
            if(moves[moveCounter]==Search.killerMoves[currentDepth-1][0]){  //Killer History choice number 1
                moveValues[moveCounter] = 90;
            }else {
                moveValues[moveCounter] = 0;                                //Quiet-Move that is not in History (therefore shitty and not interesting)
            }
            if(moves[moveCounter]==Search.killerMoves[currentDepth-1][1]){  //Killer History choice number 2
                moveValues[moveCounter] = 80;
            }
        }
        attackMap[destinationPos]=true;     //Normally the square is attacked then
        moveCounter++;
    }

    public static byte[] makeMove(int move){
        int newExtraInfo =  extraInfoStack.peek();
        if(MoveAsInt.getPromotedPiece(move)!=0){
            board[MoveAsInt.getDest(move)] = (byte) (MoveAsInt.getPromotedPiece(move)*board[PLAYER_ON_TURN]);
        }
        else {
            board[MoveAsInt.getDest(move)] = board[MoveAsInt.getStart(move)];   //Make regular movement
        }
        board[MoveAsInt.getStart(move)] = EMPTY;
        //Make castling
        if(abs(board[MoveAsInt.getDest(move)]) == KING_WHITE){  //If the moving piece is a King (*PLAYER_ON_TURN always gives the positive value;
            if(MoveAsInt.getDest(move)-MoveAsInt.getStart(move)==2) {   //And he did a jump to the right, e.g moved 2 pieces
                board[MoveAsInt.getDest(move)-1] = board[MoveAsInt.getDest(move)+1];    //Move the rook accordingly
                board[MoveAsInt.getDest(move)+1] = EMPTY;                                   //Let him jump over the King
            }
            if(MoveAsInt.getDest(move)-MoveAsInt.getStart(move)==-2) {   //And he did a jump to the left, e.g moved 2 pieces
                board[MoveAsInt.getDest(move)+1] = board[MoveAsInt.getDest(move)-2];    //Move the rook accordingly
                board[MoveAsInt.getDest(move)-2] = EMPTY;                                   //Let him jump over the King
            }
            if(board[PLAYER_ON_TURN]==WHITE){
                newExtraInfo = BoardInfoAsInt.setQueenSideWhiteCastlingRight(false,newExtraInfo);
                newExtraInfo = BoardInfoAsInt.setKingSideWhiteCastlingRight(false,newExtraInfo);
            }else {
                newExtraInfo = BoardInfoAsInt.setQueenSideBlackCastlingRight(false,newExtraInfo);
                newExtraInfo = BoardInfoAsInt.setKingSideBlackCastlingRight(false,newExtraInfo);
            }
        }
        if(board[MoveAsInt.getDest(move)] == ROOK_WHITE){
            if(MoveAsInt.getStart(move)==98)BoardInfoAsInt.setKingSideWhiteCastlingRight(false,newExtraInfo);
            if(MoveAsInt.getStart(move)==91)BoardInfoAsInt.setKingSideWhiteCastlingRight(false,newExtraInfo);
        }
        if(board[MoveAsInt.getDest(move)] == ROOK_BLACK){
            if(MoveAsInt.getStart(move)==28)BoardInfoAsInt.setKingSideBlackCastlingRight(false,newExtraInfo);
            if(MoveAsInt.getStart(move)==21)BoardInfoAsInt.setKingSideBlackCastlingRight(false,newExtraInfo);
        }
        //TODO  en passant
        //Change player on turn.
        board[PLAYER_ON_TURN]*=-1;
        extraInfoStack.push(newExtraInfo);
        return board;
    }

    public static void unMakeMove(int move){
        extraInfoStack.pop();       //Go back in the history of extra Info.
        wasPreCalculated = false;   //If we go back in "time" the präCalculated moves aren´t applicable anymore.
        if(MoveAsInt.getPromotedPiece(move)!=0){
            board[MoveAsInt.getStart(move)] = (byte) (PAWN_WHITE*board[PLAYER_ON_TURN]);    //Unmake Promotion.
        }else {//Unmake regular movement
            board[MoveAsInt.getStart(move)] = board[MoveAsInt.getDest(move)];   //Take chessman back
        }
        board[MoveAsInt.getDest(move)] = MoveAsInt.getCapture(move);        //Take eventually captured piece back.
        //Unmake eventual castling
        if(board[MoveAsInt.getStart(move)]*PLAYER_ON_TURN == KING_WHITE){  //If the moving piece is a King (*PLAYER_ON_TURN always gives the positive value;
            if(MoveAsInt.getDest(move)-MoveAsInt.getStart(move)==2) {   //And he did a jump to the right, e.g moved 2 pieces
                board[MoveAsInt.getDest(move)+1] = board[MoveAsInt.getDest(move)-1];    //Move the rook accordingly
                board[MoveAsInt.getDest(move)-1] = EMPTY;                                   //Let him jump over the King
            }
            if(MoveAsInt.getDest(move)-MoveAsInt.getStart(move)==-2) {   //And he did a jump to the left, e.g moved 2 pieces
                board[MoveAsInt.getDest(move)-2] = board[MoveAsInt.getDest(move)+1];    //Move the rook accordingly
                board[MoveAsInt.getDest(move)+1] = EMPTY;                                   //Let him jump over the King
            }
        }
        //TODO en passant
        board[PLAYER_ON_TURN]*=-1;  //Change player on turn.
    }

    public static byte[] getBoard() {
        return board;
    }

    public static void setCurrentDepth(int depth){
        currentDepth = depth;
    }

}
