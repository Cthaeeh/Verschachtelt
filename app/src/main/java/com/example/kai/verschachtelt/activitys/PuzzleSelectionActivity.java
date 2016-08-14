package com.example.kai.verschachtelt.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ListView;

import com.example.kai.verschachtelt.puzzleGame.PuzzleParser;
import com.example.kai.verschachtelt.R;
import com.example.kai.verschachtelt.puzzleGame.Puzzle;
import com.example.kai.verschachtelt.puzzleGame.PuzzlesAdapter;

import java.util.ArrayList;

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

        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

        setUpUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //Use custom actionBar
        inflater.inflate(R.menu.menu_puzzle_selection, menu);
        return true;
    }

    private void setUpUI() {
        puzzleList = (ListView) findViewById(R.id.puzzleListView);
        ArrayList<Puzzle> puzzleArray = new ArrayList<Puzzle>();
        PuzzlesAdapter adapter = new PuzzlesAdapter(this, puzzleArray);
        puzzleList.setAdapter(adapter);
        //Take JSONArray from ressource txt file convert it to Puzzles.
        puzzleArray.addAll(Puzzle.fromJson(PuzzleParser.getJSONArray()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.update_puzzles){
            //TODO downlaod new puzzles from internet and add them.
        }
        return true;
    }

}
