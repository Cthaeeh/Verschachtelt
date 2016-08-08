package com.example.kai.verschachtelt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Kai on 28.07.2016.
 * This class is for showing the chessBoard and sorrounding UI-Elements
 *
 */
public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GamePanel(this));
    }

}