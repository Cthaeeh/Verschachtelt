package com.example.kai.verschachtelt.puzzleGame;

import com.example.kai.verschachtelt.chessLogic.ChessBoardComplex;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kai on 12.08.2016.
 * Stores a Puzzle (Name, Difficulty, Description; full Moves, An Array of Chessboards with the positions.)
 * It implements Parcelable so it can be put into an Intent.
 */
public class Puzzle implements Comparable<Puzzle>{
    private String  description = "Default";
    private String  name        = "Default";
    private String  difficultyAsText = "Default";
    private Integer difficultyAsInt = 0;
    private int id = 0;
    private boolean isSolved = false; //By default not solved.

    private ChessBoardComplex[] positions;
    private JSONArray JSONPositions;        //If a puzzle is transformed back into JSON-Format its easier to just reuse this JSONArray with the FEN-Strings

    /**
     * creates a Puzzle from a JSONObject
     * @param jsonObject
     */
    public Puzzle(JSONObject jsonObject) {
        try {
            this.name = jsonObject.getString("Name");
            this.description = jsonObject.getString("Description");
            this.difficultyAsText = jsonObject.getString("DifficultyAsString");
            this.difficultyAsInt = jsonObject.getInt("DifficultyAsInt");
            this.isSolved = jsonObject.getBoolean("Solved");
            this.id = jsonObject.getInt("Id");
            this.JSONPositions = jsonObject.getJSONArray("Positions");
            this.positions = new ChessBoardComplex[JSONPositions.length()];
            for(int i = 0; i < positions.length; i++){
                positions[i] = new ChessBoardComplex(JSONPositions.get(i).toString());
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    /**
     *  Factory method to convert an array of JSON objects into a list of objects
     */
    public static ArrayList<Puzzle> fromJson(JSONArray jsonObjects) {
        ArrayList<Puzzle> puzzles = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                puzzles.add(new Puzzle(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return puzzles;
    }

    public void updateSolved() {
        isSolved = true;
    }

    /**
     * To make a Puzzle comparable to another.
     * Since we are only interested in sorting them by difficulty it only compares the difficulty.
     * @param puzzle The puzzle to compare this one with
     * @return -1,0,1 depending if its equal, more ore less difficult.
     */
    @Override
    public int compareTo(Puzzle puzzle) {
        return  this.difficultyAsInt.compareTo(puzzle.difficultyAsInt);
    }


    //Getters, Getters, Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDifficultyAsText() {
        return difficultyAsText;
    }

    public ChessBoardComplex getStartPosition() {
        return positions[0];
    }

    public ChessBoardComplex getPosition(int i) {
        if(i>=positions.length)return null;
        return positions[i];
    }

    /**
     * Gets the number of moves needed to solve the puzzle.
     * @return
     */
    public int getNeededSteps() {
        return positions.length-1;
    }

    public boolean getSolved() {
        return isSolved;
    }

    public int getId() {
        return id;
    }

    public Integer getDifficultyAsInt() {
        return difficultyAsInt;
    }

    public JSONArray getPositionsAsFEN() {
        return JSONPositions;
    }


}
