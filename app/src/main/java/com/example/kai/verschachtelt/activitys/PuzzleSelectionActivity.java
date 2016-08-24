package com.example.kai.verschachtelt.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kai.verschachtelt.DownloadListener;
import com.example.kai.verschachtelt.PuzzleDownloadTask;
import com.example.kai.verschachtelt.puzzleGame.ChessGamePuzzle;
import com.example.kai.verschachtelt.puzzleGame.PuzzleParser;
import com.example.kai.verschachtelt.R;
import com.example.kai.verschachtelt.puzzleGame.Puzzle;
import com.example.kai.verschachtelt.puzzleGame.PuzzlesAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by Kai on 10.08.2016.
 * This Activity is for selecting the different chess puzzles.
 */
public class PuzzleSelectionActivity extends AppCompatActivity implements DownloadListener{

    GridView puzzleGrid ;
    ArrayList<Puzzle> puzzleArray;
    PuzzlesAdapter adapter;

    //Web Address where you can find new puzzles
    private final static String WEBADRESS = "http://verschachtelt.bplaced.net/puzzles.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_selection);
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
        puzzleGrid = (GridView) findViewById(R.id.puzzleGridView);
        puzzleArray = new ArrayList<Puzzle>();
        adapter = new PuzzlesAdapter(this, puzzleArray);
        puzzleGrid.setAdapter(adapter);
        //Take JSONArray from ressource txt file convert it to Puzzles.
        puzzleArray.addAll(Puzzle.fromJson(PuzzleParser.getJSONArray()));
        Collections.sort(puzzleArray);  //Sort them by difficulty.

        puzzleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //If the button is pressed launch the Game-Activity
                Intent startPuzzle = new Intent(PuzzleSelectionActivity.this, GameActivity.class);
                startPuzzle.putExtra("GameType",GameActivity.GameType.PUZZLE);      //Tell the Game Activity to start a Puzzle Game.
                ChessGamePuzzle.PUZZLE = puzzleArray.get(position);
                startActivity(startPuzzle);
            }
        });
    }

    /**
     * when PuzzleDownloadTask is finnished downloading it calls back
     * @param result a String e.g the HTML file from the Webaddress.
     */
    @Override
    public void onDownloadFinished(String result) {
        if(result.equals("")){
            showNoInetToast();
        }else{
            try {
                puzzleArray.addAll(Puzzle.fromJson(new JSONArray(result)));
                Collections.sort(puzzleArray);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Shows a toast that informs the user that something with the Internet went wrong.
     */
    private void showNoInetToast() {
        CharSequence text = "Something with the Internet went wrong :(";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }

    /**
     * If someone touches an item in the actionBar refresh puzzles.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.update_puzzles){
            new PuzzleDownloadTask(this).execute(WEBADRESS);
        }
        return true;
    }
}
