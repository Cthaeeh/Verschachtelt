package com.example.kai.verschachtelt.activitys;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import com.example.kai.verschachtelt.GamePanel;
import com.example.kai.verschachtelt.RetainedFragment;
import com.example.kai.verschachtelt.puzzleGame.GamePanelPuzzle;
import com.example.kai.verschachtelt.R;
import com.example.kai.verschachtelt.pvAIGame.chess_AI.GamePanelPvAI;
import com.example.kai.verschachtelt.pvpGame.GamePanelPvP;

/**
 * Created by Kai on 28.07.2016.
 * This class is for showing the chessBoard and surrounding UI-Elements
 *
 */
public class GameActivity extends Activity implements View.OnClickListener{

    private FrameLayout layout;     //The layout that holds the gamePanel, Buttons, etc.
    private LinearLayout gameWidgets;
    private GamePanel gameView;
    private RetainedFragment dataFragment;

    Button undoButton,redoButton,showNextMoveButton;

    public enum GameType{
        CHESS_PvP,CHESS_PvAI,PUZZLE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDataFragment();
        Intent intent = getIntent();
        GameType gameType = (GameType) intent.getSerializableExtra("GameType");
        switch (gameType){  //depeding on the type of game different UIs are created
            case CHESS_PvP:
                setupPvP();
                break;
            case CHESS_PvAI:
                setupPvAI();
                break;
            case PUZZLE:
                setupPuzzle();
                break;
        }
        setContentView(layout);
        if(dataFragment.getData()!=null){   //If this activity was restarted (we saved the game previously)
            gameView.setGame(dataFragment.getData());   //continue with this game.
        }
    }

    private void createDataFragment() {
        FragmentManager fm = getFragmentManager();  //Try to find the fragment if it was previously instantiated.
        dataFragment = (RetainedFragment) fm.findFragmentByTag("data");

        // if the activity is new create the fragment.
        if (dataFragment == null) {
            // add the fragment
            dataFragment = new RetainedFragment();
            fm.beginTransaction().add(dataFragment, "data").commit();
        }
    }

    private void setupPuzzle() {
        gameView = new GamePanelPuzzle(this);
        setupUI();
        setupPuzzleButtons();
    }

    private void setupPvAI() {
        gameView = new GamePanelPvAI(this);
        setupUI();
        setupUndoRedo();
    }

    private void setupPvP() {
        gameView = new GamePanelPvP(this);
        setupUI();
        setupUndoRedo();
    }

    private void setupPuzzleButtons() {
        showNextMoveButton = new Button(this);
        showNextMoveButton.setWidth(400);
        showNextMoveButton.setText(R.string.show_next_move_button);
        showNextMoveButton.setBottom(0);
        gameWidgets.addView(showNextMoveButton);
        showNextMoveButton.setOnClickListener(this);
    }

    /**
     * sets up two Buttons, undo and redo namely
     */
    private void setupUndoRedo() {
        undoButton = new Button(this);
        redoButton = new Button(this);

        undoButton.setWidth(400);
        redoButton.setWidth(400);

        undoButton.setText(R.string.undo_button);
        redoButton.setText(R.string.redo_button);
        undoButton.setBottom(0);
        redoButton.setBottom(0);

        gameWidgets.addView(undoButton);
        gameWidgets.addView(redoButton);

        undoButton.setOnClickListener(this);
        redoButton.setOnClickListener(this);
    }

    /**
     * sets up UI elements that all Game modes share
     */
    private void setupUI() {
        layout = new FrameLayout(this);
        gameWidgets = new LinearLayout (this);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

        layout.addView(gameView);
        layout.addView(gameWidgets,layoutParams);
    }

    @Override
    public void onClick(View view) {
        if(view == undoButton){
            gameView.inputHandler.processUndoButton();
        }
        if(view == redoButton){
            gameView.inputHandler.processRedoButton();
        }
        if(view == showNextMoveButton){
            gameView.inputHandler.processShowNextMoveButton();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // store the data in the fragment
        dataFragment.setData(gameView.getGame());
    }
}