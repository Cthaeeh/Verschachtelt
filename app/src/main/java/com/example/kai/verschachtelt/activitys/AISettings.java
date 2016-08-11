package com.example.kai.verschachtelt.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.kai.verschachtelt.R;

/**
 * Created by Kai on 10.08.2016.
 * This Activity is shown when you want to start a Game vs the AI.
 */
public class AISettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_settings);
        setUpUI();
    }

    private void setUpUI(){
        //Setup the UI-Elements of The AISettingsActivity
        Button launchPvAI = (Button) findViewById(R.id.button_start_Game);
        launchPvAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If the button is pressed launch the Game-Activity
                Intent startPvAIGame = new Intent(AISettings.this, GameActivity.class);
                startPvAIGame.putExtra("GameType",GameActivity.GameType.CHESS_PvAI);      //Tell the Game Activity to start a PvP Game.
                startActivity(startPvAIGame);
            }
        });
    }
}
