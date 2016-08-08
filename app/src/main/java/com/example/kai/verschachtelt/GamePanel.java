package com.example.kai.verschachtelt;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Kai on 28.07.2016.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private MainThread thread;
    private Background background;
    private ChessBoardGraphic chessBoardGraphic;
    private ChessManGraphic chessManGraphic;

    //The position of the touch/ for development
    private int xTouch = 0;
    private int yTouch = 0;

    //Resolution of Background Image
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 1920;

    public GamePanel(Context context){
        super(context);

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(),this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        background = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.test_background));
        chessBoardGraphic = new ChessBoardGraphic();
        chessManGraphic = new ChessManGraphic();

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
        return super.onTouchEvent(event);
    }
    public void update(double avgFPS){          //Show some dev info
        background.update(String.valueOf(avgFPS)+" fps " + String.valueOf(xTouch)+"|" +String.valueOf(yTouch) );
    }
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        //Calculate Scale Factors
        final float scaleFactorX = canvas.getWidth()/(WIDTH*1.f);
        final float scaleFactorY = canvas.getHeight()/(HEIGHT*1.f);

        if(canvas !=null) {
            final int savedState = canvas.save();       //This has to be done so is scales only one time (for now)
            canvas.scale(scaleFactorX, scaleFactorY);

            background.draw(canvas);
            canvas.restoreToCount(savedState);

            //Draw all the components that dont need scaling (scale them self)
            chessBoardGraphic.draw(canvas);
            chessManGraphic.draw(canvas);
        }
    }
}
