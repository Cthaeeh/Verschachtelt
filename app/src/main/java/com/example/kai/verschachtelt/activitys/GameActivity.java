package com.example.kai.verschachtelt.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kai.verschachtelt.GamePanel;
import com.example.kai.verschachtelt.R;
import com.example.kai.verschachtelt.puzzleGame.ChessGamePuzzle;

/**
 * Created by Kai on 28.07.2016.
 * This class is for showing the chessBoard and surrounding UI-Elements
 *
 */
public class GameActivity extends Activity implements View.OnClickListener{

    private GamePanel gamePanel;
    private RetainedFragment dataFragment;      //A Fragment to store data, because this Activity is destroyed when the screen orientation changes

    Button undoButton,redoButton,showNextMoveButton,surrenderButton;
    TextView description;
    GameType gameType;

    public enum GameType{
        CHESS_PvP,CHESS_PvAI,PUZZLE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game); //Use an (way better) xml layout, instead of in code layout
        createDataFragment();
        gamePanel = (GamePanel) findViewById(R.id.surfaceViewForGame); //Now get the GamePanel UI-Element just like a Button.
        Intent intent = getIntent();
        gameType = (GameType) intent.getSerializableExtra("GameType");
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
        if(dataFragment.getData()!=null){   //If this activity was restarted (we saved the game previously)
            gamePanel.setGame(dataFragment.getData());   //continue with this saved game.
        }
    }

    private void setupPuzzle() {
        gamePanel.setGame(GameType.PUZZLE); //Tell the gamePanel what mode we want to play in.
        showNextMoveButton = (Button) findViewById(R.id.button1);
        showNextMoveButton.setText(R.string.show_next_move_button);
        showNextMoveButton.setOnClickListener(this);
        surrenderButton = (Button) findViewById(R.id.button2);
        surrenderButton.setText(R.string.surrender_button);
        surrenderButton.setOnClickListener(this);
        description = (TextView) findViewById(R.id.gameDescription);
        description.setText(((ChessGamePuzzle) gamePanel.getGame()).getPuzzleDescription());
    }

    private void setupPvAI() {
        gamePanel.setGame(GameType.CHESS_PvAI); //Tell the gamePanel what mode we want to play in.
        setupUndoRedo();
        description = (TextView) findViewById(R.id.gameDescription);
        description.setText(R.string.ai_mode_description);
    }

    private void setupPvP() {
        gamePanel.setGame(GameType.CHESS_PvP); //Tell the gamePanel what mode we want to play in.
        setupUndoRedo();
        description = (TextView) findViewById(R.id.gameDescription);
        description.setText(R.string.pvp_mode_description);
    }


    /**
     * sets up two Buttons, undo and redo namely
     */
    private void setupUndoRedo() {
        undoButton = (Button) findViewById(R.id.button1);
        redoButton = (Button) findViewById(R.id.button2);
        undoButton.setText(R.string.undo_button);
        redoButton.setText(R.string.redo_button);
        undoButton.setOnClickListener(this);
        redoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == undoButton){
            gamePanel.inputHandler.processUndoButton();
        }
        if(view == redoButton){
            gamePanel.inputHandler.processRedoButton();
        }
        if(view == showNextMoveButton){
            gamePanel.inputHandler.processShowNextMoveButton();
        }
        if(view == surrenderButton){
            onBackPressed();
        }
    }

    /**
     * Creates a DataFragment that can store a ChessGame and isnt destroyed when the screen orientation changes.
     */
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

    /**
     * Simple method to warn the user if he really wants to leave the game
     */
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_warning_black_36dp)
                .setTitle("Exit the Game")
                .setMessage("Are you sure you want to quit this game?" +
                            " It will not be saved!")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // store the data in the fragment
        dataFragment.setData(gamePanel.getGame());
    }
}