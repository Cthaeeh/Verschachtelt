package com.example.kai.verschachtelt.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.kai.verschachtelt.dataHandling.DownloadListener;
import com.example.kai.verschachtelt.dataHandling.MyDBHandler;
import com.example.kai.verschachtelt.dataHandling.PuzzleDownloadTask;
import com.example.kai.verschachtelt.puzzleGame.ChessGamePuzzle;
import com.example.kai.verschachtelt.R;
import com.example.kai.verschachtelt.puzzleGame.Puzzle;
import com.example.kai.verschachtelt.puzzleGame.PuzzleParser;
import com.example.kai.verschachtelt.puzzleGame.PuzzlesAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Kai on 10.08.2016.
 * This Activity is for selecting the different chess puzzles.
 */
public class PuzzleSelectionActivity extends AppCompatActivity implements DownloadListener{

    private GridView puzzleGrid ;
    private ArrayList<Puzzle> puzzleArray;
    private PuzzlesAdapter adapter;
    private MyDBHandler myDBHandler;

    //Web Address where you can find new puzzles
    private final static String WEBADRESS = "http://verschachtelt.bplaced.net/puzzles.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_selection);
        getPuzzlesFromDB();
        setUpUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //Use custom actionBar
        inflater.inflate(R.menu.menu_puzzle_selection, menu);
        return true;
    }

    /**
     * Method is called when Method gets visible again.
     * That can happen if we return from a puzzle -> maybe it was solved ->
     * must synchronise with database.
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        puzzleArray.clear();
        puzzleArray.addAll(myDBHandler.getAllPuzzles()); //synchronise db with puzzleArray
        adapter.notifyDataSetChanged();
    }

    private void setUpUI() {
        puzzleGrid = (GridView) findViewById(R.id.puzzleGridView);
        adapter = new PuzzlesAdapter(this, puzzleArray);
        puzzleGrid.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
     * Method for getting Puzzles from DB.
     */
    private void getPuzzlesFromDB() {
        myDBHandler = new MyDBHandler(this);       //Create database or get connection to database.
        myDBHandler.insertPuzzle(Puzzle.fromJson(PuzzleParser.getJSONArrayFromRes())); //Fill with puzzles from txt.
        puzzleArray = myDBHandler.getAllPuzzles(); //synchronise db with puzzleArray
        Collections.sort(puzzleArray);             //Sort them by difficulty.
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
                myDBHandler.insertPuzzle( Puzzle.fromJson(new JSONArray(result))); //Insert all puzzles that we got from internet into DB
                puzzleArray.clear();    //Reasons can be found here: http://stackoverflow.com/questions/15422120/notifydatasetchange-not-working-from-custom-adapter
                puzzleArray.addAll(myDBHandler.getAllPuzzles()); //synchronise db with puzzleArray
                Collections.sort(puzzleArray); //Sort puzzleArray
                adapter.notifyDataSetChanged(); //Inform adapter that shit changed.
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
     * If someone touches an the refresh Button in the actionBar refresh puzzles.
     * @param item what item in the actionBar was touched.
     * @return if we took care of the touch.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.update_puzzles){
            new PuzzleDownloadTask(this).execute(WEBADRESS);
        }
        return true;
    }
}
