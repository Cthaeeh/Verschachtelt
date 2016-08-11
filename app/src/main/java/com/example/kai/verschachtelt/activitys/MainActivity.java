package com.example.kai.verschachtelt.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kai.verschachtelt.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpUI();
    }

    private void setUpUI() {
        //Setup the UI-Elements of The MainActivity
        Button launchPVP = (Button) findViewById(R.id.launch_PvP_Button);
        launchPVP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If the button is pressed launch the Game-Activity
                Intent startPvPGame = new Intent(MainActivity.this, GameActivity.class);
                startPvPGame.putExtra("GameType",GameActivity.GameType.CHESS_PvP);
                startActivity(startPvPGame);
            }
        });

        Button launchPuzzleSelection = (Button) findViewById(R.id.launch_Puzzle_Selection_Button);
        launchPuzzleSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showPuzzleSelection = new Intent(MainActivity.this, PuzzleSelectionActivity.class);
                startActivity(showPuzzleSelection);
            }
        });

        Button launchPvAI = (Button) findViewById(R.id.launch_PvAI_Button);
        launchPvAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startAIGame = new Intent(MainActivity.this, AISettings.class);
                startActivity(startAIGame);
            }
        });
    }
}
