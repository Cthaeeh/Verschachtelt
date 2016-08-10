package com.example.kai.verschachtelt.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kai.verschachtelt.GamePanel;

/**
 * Created by Kai on 28.07.2016.
 * This class is for showing the chessBoard and sorounding UI-Elements
 *
 */
public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GamePanel(this));            //Instead of an XML File just the GamePanel
    }

}