package com.example.kai.verschachtelt.puzzleGame;

import com.example.kai.verschachtelt.chessLogic.ChessBoardComplex;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kai on 12.08.2016.
 */
public class Puzzle {
    private String description;
    private String name;
    private String difficulty;
    private int    fullMoves;
    private ChessBoardComplex[] positions;

    public Puzzle(String name){
        this.name = name;
    }

    /**
     * creates a Puzzle from a JSONObject
     * @param jsonObject
     */
    public Puzzle(JSONObject jsonObject) {
        try {
            this.name = jsonObject.getString("Name");
            this.description = jsonObject.getString("Description");
            this.difficulty = jsonObject.getString("Difficulty");
            this.fullMoves = jsonObject.getInt("Moves");
            JSONArray JSONPositions = jsonObject.getJSONArray("Positions");
            positions = new ChessBoardComplex[JSONPositions.length()];
            for(int i = 0; i < positions.length; i++){
                positions[i] = new ChessBoardComplex(JSONPositions.get(i).toString());
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }

    /**
     *  Factory method to convert an array of JSON objects into a list of objects
      */
    public static ArrayList<Puzzle> fromJson(JSONArray jsonObjects) {
        ArrayList<Puzzle> puzzles = new ArrayList<Puzzle>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                puzzles.add(new Puzzle(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return puzzles;
    }

    public String getDescription() {
        return description;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
