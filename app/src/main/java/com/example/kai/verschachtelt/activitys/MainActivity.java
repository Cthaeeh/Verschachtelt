package com.example.kai.verschachtelt.activitys;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kai.verschachtelt.R;

public class MainActivity extends AppCompatActivity {

    private static Context mContext;    //This is for accessing Ressources from everywhere without having to pass a Context everywhere.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        setContentView(R.layout.activity_main);
        setUpUI();
    }

    /**
     * This is for accessing ressources from everywhere without having to pass a Context everywhere.
     * @return the context
     */
    public static Context getContext(){
        return mContext;
    }

    private void setUpUI() {
        //Setup the UI-Elements of The MainActivity
        setUpPvPButton();

        setUpPuzzleButton();

        setUpPvAIButton();
    }

    /**
     * Sets up a Button that launches a Game vs AI.
     */
    private void setUpPvAIButton() {
        Button launchPvAI = (Button) findViewById(R.id.launch_PvAI_Button);
        launchPvAI.setBackgroundColor(getResources().getColor(R.color.mainActivityButtonsColor));
        launchPvAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startAIGame = new Intent(MainActivity.this, AISettings.class);
                startActivity(startAIGame);
            }
        });
    }

    /**
     * Sets up a Button that launches a PvP-Game
     */
    private void setUpPvPButton() {
        Button launchPVP = (Button) findViewById(R.id.launch_PvP_Button);
        launchPVP.setBackgroundColor(getResources().getColor(R.color.mainActivityButtonsColor));  //The suggested alternative method is minsdk 23 ...
        launchPVP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If the button is pressed launch the Game-Activity
                Intent startPvPGame = new Intent(MainActivity.this, GameActivity.class);
                startPvPGame.putExtra("GameType",GameActivity.GameType.CHESS_PvP);
                startActivity(startPvPGame);
            }
        });
    }

    /**
     * Sets up a Button that launches the Puzzle Selection
     */
    private void setUpPuzzleButton(){
        Button launchPuzzleSelection = (Button) findViewById(R.id.launch_Puzzle_Selection_Button);
        launchPuzzleSelection.setBackgroundColor(getResources().getColor(R.color.mainActivityButtonsColor));
        launchPuzzleSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showPuzzleSelection = new Intent(MainActivity.this, PuzzleSelectionActivity.class);
                startActivity(showPuzzleSelection);
            }
        });
    }
}
