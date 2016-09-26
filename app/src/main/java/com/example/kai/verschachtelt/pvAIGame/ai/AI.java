package com.example.kai.verschachtelt.pvAIGame.ai;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Vibrator;

import com.example.kai.verschachtelt.activitys.MainActivity;
import com.example.kai.verschachtelt.chessLogic.ChessBoardComplex;
import com.example.kai.verschachtelt.chessLogic.Chessman;
import com.example.kai.verschachtelt.pvAIGame.ChessGamePvAI;

/**
 * Created by Kai on 10.08.2016.
 * This is the starting point for all AI development.
 *
 */
public class AI implements AI_Listener {

    private AsyncTask<byte[], Integer, Move> ai_task;

    private final  int difficulty;
    private final  Chessman.Color aiColor;  //The color of the pieces the ai moves
    private ChessGamePvAI boardComplex;

    private final int mailbox64[] = {                 //For transferring a 8x8 to a 10x12 board. See: https://chessprogramming.wikispaces.com/10x12+Board
                21, 22, 23, 24, 25, 26, 27, 28,
                31, 32, 33, 34, 35, 36, 37, 38,
                41, 42, 43, 44, 45, 46, 47, 48,
                51, 52, 53, 54, 55, 56, 57, 58,
                61, 62, 63, 64, 65, 66, 67, 68,
                71, 72, 73, 74, 75, 76, 77, 78,
                81, 82, 83, 84, 85, 86, 87, 88,
                91, 92, 93, 94, 95, 96, 97, 98
    };


    /**
     * @param difficulty    How good the ai plays.
     * @param aiColor       The color of the chessman the ai will move.
     */
    public AI(int difficulty, Chessman.Color aiColor){
        this.difficulty = difficulty;
        this.aiColor = aiColor;
    }

    /**
     * The ai calculates a move, makes it in the boardCurrent, and gives it back.
     * @param game The Chess game you want the ai to calculate a move for.
     */
    public void calculateMove(ChessGamePvAI game) {
        this.boardComplex = game;
        byte[] byteBoard = toByteArray(game.getComplexBoard()); //Inside the ai a boardCurrent is represented by byte array
        int boardAdditionalInfo = extractExtraInfo(game.getComplexBoard());   //The AI needs to know about castling states and such.
        ai_task = new AI_Task(this,difficulty,boardAdditionalInfo).execute(byteBoard);
    }

    /**
     * The method takes a boardCurrent object and extracts the extra Info,
     * such as Castling Rights, (later en passant ..) //TODO enpassant
     * @return the extra Info encoded as a int.
     */
    private int extractExtraInfo(ChessBoardComplex complexBoard) {
        boolean queenSideBlackCastling = complexBoard.getCastlingManager().getQueenSideBlack();
        boolean kingSideBlackCastling = complexBoard.getCastlingManager().getKingSideBlack();
        boolean queenSideWhiteCastling = complexBoard.getCastlingManager().getQueenSideWhite();
        boolean kingSideWhiteCastling = complexBoard.getCastlingManager().getKingSideWhite();
        int halfMoveClock = 0;  //TODO implement
        return BoardInfoAsInt.encodeExtraInfo(queenSideBlackCastling,kingSideBlackCastling,queenSideWhiteCastling,kingSideWhiteCastling,halfMoveClock);
    }

    /**
     * The method takes a boardCurrent object and translates it to a byte array of length 130.
     * See: https://chessprogramming.wikispaces.com/10x12+Board
     * the 10 extra values are fore storing the player on turn.
     * to represent different chessmen it uses constants.
     * @param board
     * @return
     */
    private byte[] toByteArray(ChessBoardComplex board) {
        byte[] byteBoard = getEmptyByteBoard();
        for (int i = 0;i<64;i++){                       //Iterate over board.
            if(board.getChessManAt(i)!=null){
                Chessman chessman = board.getChessManAt(i);
                switch (chessman.getColor()){
                    case BLACK:                         //Depending on the chessman a constant is chosen.
                        switch (chessman.getPiece()){
                            case KING:  byteBoard[mailbox64[i]] = MoveGen.KING_BLACK;break;
                            case QUEEN: byteBoard[mailbox64[i]] = MoveGen.QUEEN_BLACK;break;
                            case ROOK:  byteBoard[mailbox64[i]] = MoveGen.ROOK_BLACK;break;
                            case KNIGHT:byteBoard[mailbox64[i]] = MoveGen.KNIGHT_BLACK;break;
                            case BISHOP:byteBoard[mailbox64[i]] = MoveGen.BISHOP_BLACK;break;
                            case PAWN:  byteBoard[mailbox64[i]] = MoveGen.PAWN_BLACK;break;
                        }
                        break;
                    case WHITE:
                        switch (chessman.getPiece()){
                            case KING:  byteBoard[mailbox64[i]] = MoveGen.KING_WHITE;break;
                            case QUEEN: byteBoard[mailbox64[i]] = MoveGen.QUEEN_WHITE;break;
                            case ROOK:  byteBoard[mailbox64[i]] = MoveGen.ROOK_WHITE;break;
                            case KNIGHT:byteBoard[mailbox64[i]] = MoveGen.KNIGHT_WHITE;break;
                            case BISHOP:byteBoard[mailbox64[i]] = MoveGen.BISHOP_WHITE;break;
                            case PAWN:  byteBoard[mailbox64[i]] = MoveGen.PAWN_WHITE;break;
                        }
                        break;
                }
            }else {
                byteBoard[mailbox64[i]]= MoveGen.EMPTY;
            }
        }
        if(aiColor== Chessman.Color.WHITE)byteBoard[MoveGen.PLAYER_ON_TURN]= MoveGen.WHITE;
        else byteBoard[MoveGen.PLAYER_ON_TURN]= MoveGen.BLACK;
        return byteBoard;
    }

    /**
     * Method creates an empty ByteBoard with only inaccessible Squares.
     * @return
     */
    private byte[] getEmptyByteBoard() {
        byte[] byteBoard = new byte[130];
        for(int i = 0;i<120;i++){
            byteBoard[i] = MoveGen.INACCESSIBLE;
        }
        return byteBoard;
    }


    public Chessman.Color getColor() {
        return aiColor;
    }

    /**
     * The AsyncTask calculating the moves can call this method when finished with calculations.
     * @param move  the move the asyncTask calculated.
     */
    @Override
    public void onMoveCalculated(Move move) {
        if(move == null) return;
        boardComplex.moveByAi(move);
        if(difficulty >= 2) vibrate(300);   //TODO make this a setting
    }

    /**
     * Try to cancel the current calculations.
     */
    public void cancel() {
        try {
            ai_task.cancel(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * lets the phone vibrate for "ms" milliseconds.
     * @param ms
     */
    private void vibrate(int ms){
        Vibrator v = (Vibrator) MainActivity.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(ms);
    }
}
