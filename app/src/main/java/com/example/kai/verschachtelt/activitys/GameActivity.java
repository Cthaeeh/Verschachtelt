package com.example.kai.verschachtelt.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import com.example.kai.verschachtelt.GamePanel;

/**
 * Created by Kai on 28.07.2016.
 * This class is for showing the chessBoard and sorounding UI-Elements
 *
 */
public class GameActivity extends Activity implements View.OnClickListener{

    private FrameLayout layout;     //The layout that holds the gamePanel, Buttons, etc.
    private GamePanel gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupUI();


        setContentView(layout); //Instead of an XML File just the GamePanel
    }

    private void setupUI() {
        layout = new FrameLayout(this);
        gameView = new GamePanel(this);

        LinearLayout gameWidgets = new LinearLayout (this);

        Button undoButton = new Button(this);
        undoButton.setWidth(400);
        undoButton.setText("Undo Move!");
        undoButton.setBottom(0);

        gameWidgets.addView(undoButton);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

        layout.addView(gameView);
        layout.addView(gameWidgets,layoutParams);
    }

    @Override
    public void onClick(View view) {
        //can access gameView here to redo ...
    }
}