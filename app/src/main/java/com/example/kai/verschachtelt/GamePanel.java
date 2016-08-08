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

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry){
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

        return super.onTouchEvent(event);
    }
    public void update(){
        background.update();
    }
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        background.draw(canvas);
    }
}
