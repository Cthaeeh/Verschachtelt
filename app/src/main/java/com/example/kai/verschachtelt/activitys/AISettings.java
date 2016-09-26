package com.example.kai.verschachtelt.activitys;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.kai.verschachtelt.R;

/**
 * Created by Kai on 10.08.2016.
 * This Activity is shown when you want to start a Game vs the AI.
 */
public class AISettings extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView difficulty;
    private int difficultyValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_settings);
        setUpUI();
        setUpSeekBar();
    }

    private void setUpSeekBar() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                difficultyValue = progresValue;
                difficulty.setText(getString(R.string.difficulty_text) +" "+ difficultyValue + "/" + seekBar.getMax());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                difficulty.setText(getString(R.string.difficulty_text) +" "+ difficultyValue + "/" + seekBar.getMax());
            }
        });

    }

    private void setUpUI(){
        //Setup the UI-Elements of The AISettingsActivity
        TextView aboutAI = (TextView) findViewById(R.id.aboutAIInfoText);   //Make about ai text scrollable
        aboutAI.setMovementMethod(new ScrollingMovementMethod());
        aboutAI.setText(Html.fromHtml(getString(R.string.aboutAI)));
        aboutAI.setTextColor(Color.BLACK);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        difficulty = (TextView) findViewById(R.id.textView_difficulty);
        difficulty.setText(getString(R.string.difficulty_text) + " 0/6");
        Button launchPvAI = (Button) findViewById(R.id.button_start_Game);
        launchPvAI.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        launchPvAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If the button is pressed launch the Game-Activity
                Intent startPvAIGame = new Intent(AISettings.this, GameActivity.class);
                startPvAIGame.putExtra("GameType",GameActivity.GameType.CHESS_PvAI);      //Tell the Game Activity to start a PvP Game.
                startPvAIGame.putExtra("Difficulty",difficultyValue);
                startActivity(startPvAIGame);
            }
        });
    }
}
