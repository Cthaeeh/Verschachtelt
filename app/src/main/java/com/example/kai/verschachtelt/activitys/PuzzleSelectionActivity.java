package com.example.kai.verschachtelt.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kai.verschachtelt.R;

/**
 * Created by Kai on 10.08.2016.
 * This Activity is for selecting the different chess puzzles.
 */
public class PuzzleSelectionActivity extends AppCompatActivity {

    ListView puzzleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_selection);
        setUpUI();
    }

    private void setUpUI() {
        puzzleList = (ListView) findViewById(R.id.puzzleListView);
        // Defined Array values to show in ListView
        String[] values = new String[] { "Puzzle A",
                "Puzzle B",
                "Puzzle C",
        };


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        puzzleList.setAdapter(adapter);
    }

}
