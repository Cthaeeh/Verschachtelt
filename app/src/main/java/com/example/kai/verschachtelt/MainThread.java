package com.example.kai.verschachtelt;

import android.graphics.Canvas;
import android.provider.Settings;
import android.view.SurfaceHolder;

/**
 * Created by Kai on 08.08.2016.
 */
public class MainThread extends Thread{
    private int FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;

    }
    @Override
    public void run(){
        long startTime;
        long timeItTookMillis;
        long waitTime;
        long totalTime =0;
        int frameCount = 0;
        long targetTime = 1000/FPS; //Time on Gameloop takes (one frame is displayed)

        while(running){
            startTime = System.nanoTime();
            canvas = null;

            //try locking the canvas for pixel editing
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            }catch (Exception e){
            }
            finally {
                if(canvas!=null){
                    try{surfaceHolder.unlockCanvasAndPost(canvas);}
                    catch (Exception e){e.printStackTrace();}
                }
            }
            timeItTookMillis = (System.nanoTime()-startTime)/1000000;
            waitTime = targetTime -timeItTookMillis;

            try{
                this.sleep(waitTime);
            }catch (Exception e){
            }
            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == FPS){
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }

    }

    public void setRunning(boolean running){
        this.running = running;
    }


}
