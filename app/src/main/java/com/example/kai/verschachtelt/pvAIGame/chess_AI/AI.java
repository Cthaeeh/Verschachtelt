package com.example.kai.verschachtelt.pvAIGame.chess_AI;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.util.Log;
import android.os.Vibrator;

import com.example.kai.verschachtelt.activitys.MainActivity;
import com.example.kai.verschachtelt.chessLogic.ChessBoardComplex;
import com.example.kai.verschachtelt.chessLogic.Chessman;

/**
 * Created by Kai on 10.08.2016.
 * This is the starting point for all AI development.
 *
 */
public class AI implements AI_Listener {

    private static final String TAG = "AI";

    private AsyncTask<byte[], Integer, Move> ai_task;

    private final  int difficulty;          //TODO implement this.
    private final  Chessman.Color aiColor;  //The color of the pieces the ai moves
    private byte[] byteBoard ;              //Inside the ai a boardCurrent is represented by byte array
    private ChessGamePvAI boardComplex;

    int mailbox64[] = {                 //For transferring a 8x8 to a 10x12 board. See: https://chessprogramming.wikispaces.com/10x12+Board
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
     * @return The board after the ai did its move.
     */
    public void calculateMove(ChessGamePvAI game) {
        this.boardComplex = game;
        byteBoard = toByteArray(game.getComplexBoard());
        ai_task = new AI_Task(this,difficulty).execute(byteBoard);
    }

    /**
     * The method takes a boardCurrent object and translates it to a byte array of length 130.
     * See: https://chessprogramming.wikispaces.com/10x12+Board
     * the 10 extra values are fore stroring the player on turn.
     * to represent different chessmen it uses constants.
     * @param board
     * @return
     */
    public byte[] toByteArray(ChessBoardComplex board) {
        byte[] byteBoard = getEmptyByteBoard();
        for (int i = 0;i<64;i++){                       //Iterate over board.
            if(board.getChessManAt(i)!=null){
                Chessman chessman = board.getChessManAt(i);
                switch (chessman.getColor()){
                    case BLACK:                         //Depending on the chessman a constant is choosen.
                        switch (chessman.getPiece()){
                            case KING:  byteBoard[mailbox64[i]] = MoveGenerator.KING_BLACK;break;
                            case QUEEN: byteBoard[mailbox64[i]] = MoveGenerator.QUEEN_BLACK;break;
                            case ROOK:  byteBoard[mailbox64[i]] = MoveGenerator.ROOK_BLACK;break;
                            case KNIGHT:byteBoard[mailbox64[i]] = MoveGenerator.KNIGHT_BLACK;break;
                            case BISHOP:byteBoard[mailbox64[i]] = MoveGenerator.BISHOP_BLACK;break;
                            case PAWN:  byteBoard[mailbox64[i]] = MoveGenerator.PAWN_BLACK;break;
                        }
                        break;
                    case WHITE:
                        switch (chessman.getPiece()){
                            case KING:  byteBoard[mailbox64[i]] = MoveGenerator.KING_WHITE;break;
                            case QUEEN: byteBoard[mailbox64[i]] = MoveGenerator.QUEEN_WHITE;break;
                            case ROOK:  byteBoard[mailbox64[i]] = MoveGenerator.ROOK_WHITE;break;
                            case KNIGHT:byteBoard[mailbox64[i]] = MoveGenerator.KNIGHT_WHITE;break;
                            case BISHOP:byteBoard[mailbox64[i]] = MoveGenerator.BISHOP_WHITE;break;
                            case PAWN:  byteBoard[mailbox64[i]] = MoveGenerator.PAWN_WHITE;break;
                        }
                        break;
                }
            }else {
                byteBoard[mailbox64[i]]= MoveGenerator.EMPTY;
            }
        }
        if(aiColor== Chessman.Color.WHITE)byteBoard[MoveGenerator.PLAYER_ON_TURN_EXTRA_FIELD]=MoveGenerator.WHITE;
        else byteBoard[MoveGenerator.PLAYER_ON_TURN_EXTRA_FIELD]=MoveGenerator.BLACK;
        return byteBoard;
    }

    /**
     * Method creates an empty ByteBoard with only inaccessible Squares.
     * @return
     */
    private byte[] getEmptyByteBoard() {
        byte[] byteBoard = new byte[130];
        for(int i = 0;i<120;i++){
            byteBoard[i] = MoveGenerator.INACCESSIBLE;
        }
        return byteBoard;
    }


    public Chessman.Color getColor() {
        return aiColor;
    }

    /**
     * The AsyncTask calculating the moves can call this method when finnished with calculations.
     * @param move  the move the asyncTask calculated.
     */
    @Override
    public void onMoveCalculated(Move move) {
        if(move == null) return;
        boardComplex.moveByAi(move);
        if(difficulty > 2) vibrate(300);   //TODO make this a setting
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

    public int getDifficulty(){
        return difficulty;
    }
}
