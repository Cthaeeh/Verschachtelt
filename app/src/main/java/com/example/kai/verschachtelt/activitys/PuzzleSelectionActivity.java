package com.example.kai.verschachtelt.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kai.verschachtelt.R;

/**
 * Created by Kai on 10.08.2016.
 * This Activity is for selecting the different chess puzzles.
 */
public class PuzzleSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_selection);
        setUpUI();
    }

    private void setUpUI() {
    }

}
