package com.example.kai.verschachtelt;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.kai.verschachtelt.activitys.GameActivity;
import com.example.kai.verschachtelt.graphics.Background;
import com.example.kai.verschachtelt.graphics.ChessBoardGraphic;
import com.example.kai.verschachtelt.graphics.ChessmanGraphic;
import com.example.kai.verschachtelt.graphics.PawnChangeGraphic;
import com.example.kai.verschachtelt.graphics.VictoryScreenGraphic;
import com.example.kai.verschachtelt.puzzleGame.ChessGamePuzzle;
import com.example.kai.verschachtelt.pvAIGame.ChessGamePvAI;
import com.example.kai.verschachtelt.pvpGame.ChessGamePvP;

/**
 * Created by Kai on 28.07.2016.
 * This class allows to draw on a canvas, pass the canvas to other classes and react to Touch Events.
 * It starts or kills the Mainthread, which itselfs calls the draw and update on the canvas 30 times per sek.
 * It creates all necessary classes for drawing the Game(Background, ChessBoardGraphic,ChessManGraphic)
 * When it registrates a touch event it uses a TouchnInputHandler Object to change the Game accordingly.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private MainThread thread;
    protected ChessGame  game;

    private Background background;
    private ChessBoardGraphic chessBoardGraphic;
    private ChessmanGraphic chessmanGraphic;
    private VictoryScreenGraphic victoryScreenGraphic;
    private PawnChangeGraphic pawnChangeGraphic;

    public static int squareSize;               //The only global variable kind of. It represents the length and width of a square on the boardCurrent.

    //The position of the touch/ for development
    private int xTouch = 0;
    private int yTouch = 0;

    public InputHandler inputHandler;

    //Resolution of Background Image
    private static final int WIDTH = 1080;
    private static final int HEIGHT = 1920;

    public GamePanel(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(),this);
        setFocusable(true);
        inputHandler = new InputHandler();
        game = new ChessGame(inputHandler);
        setUpGraphics();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (thread.getState() == Thread.State.TERMINATED)   //See also: http://stackoverflow.com/questions/16381411/how-to-best-pause-and-resume-a-surfaceview-thread?rq=1
        {                                                   //I love the guy that answered the question.
            thread = new MainThread(getHolder(),this);
        }
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry){                      //Sometimes killing the thread doesnt work so retry it
            try {thread.setRunning(false);
                 thread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent (MotionEvent event){
        xTouch = (int)event.getX();
        yTouch = (int)event.getY();

        // Depending on whether the pawnChangeGraphic is activated a different inputHandler method is choosen.
        if(pawnChangeGraphic.isActivated){
            inputHandler.processPawnChangeEvent(event);
        }else{
            inputHandler.processTouchEvent(event);//Pass it to the inputHandler, so the logic is encapsuled.
        }
        return super.onTouchEvent(event);
    }

    public void update(double avgFPS){
        //Show some dev info
        background.update(String.valueOf(avgFPS)+" fps " + String.valueOf(xTouch)+"|" +String.valueOf(yTouch) );
        chessmanGraphic.update(game.getSimpleBoard());
        victoryScreenGraphic.update(game.getWinner());
        pawnChangeGraphic.update(game.getComplexBoard().pawnPromotionPossible());
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        //Calculate Scale Factors
        final float scaleFactorX = canvas.getWidth()/(WIDTH*1.f);
        final float scaleFactorY = canvas.getHeight()/(HEIGHT*1.f);

        final int savedState = canvas.save();       //This has to be done so is scales only one time (for now)
        canvas.scale(scaleFactorX, scaleFactorY);

        background.draw(canvas);
        canvas.restoreToCount(savedState);

        if(canvas.getHeight()<canvas.getWidth()) {
                squareSize = canvas.getHeight()/8;
        }else{
            squareSize = canvas.getWidth()/8;
        }
        //Draw all the components that dont need scaling (they scale them self)
        chessBoardGraphic.draw(canvas);
        chessmanGraphic.draw(canvas);
        if(pawnChangeGraphic.isActivated) pawnChangeGraphic.draw(canvas);
        if(game.getWinner()!=null)        victoryScreenGraphic.draw(canvas);
    }



    private void setUpGraphics() {
        background = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.test_background));
        chessBoardGraphic = new ChessBoardGraphic();
        chessmanGraphic = new ChessmanGraphic();
        victoryScreenGraphic = new VictoryScreenGraphic(BitmapFactory.decodeResource(getResources(),R.drawable.victory_screens));
        pawnChangeGraphic = new PawnChangeGraphic();
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
        game.setInputHandler(inputHandler); //If we reload a previous game we give it our InputHandler.
    }

    /**
     * Depending on the game type create a new game of this type.
     * @param gameType
     * @param difficulty is only used for GameType AI
     */
    public void setGame(GameActivity.GameType gameType, int difficulty) {
        switch (gameType){  //depeding on the type of game a game object is created
            case CHESS_PvP:
                game = new ChessGamePvP(inputHandler);
                break;
            case CHESS_PvAI:
                game = new ChessGamePvAI(inputHandler,difficulty);
                break;
            case PUZZLE:
                game = new ChessGamePuzzle(inputHandler);
                break;
        }
    }
}

