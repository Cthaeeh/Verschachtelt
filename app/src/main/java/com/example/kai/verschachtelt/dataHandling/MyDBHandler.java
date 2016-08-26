package com.example.kai.verschachtelt.dataHandling;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kai.verschachtelt.puzzleGame.Puzzle;
import com.example.kai.verschachtelt.puzzleGame.PuzzleParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kai on 24.08.2016.
 * See: https://developer.android.com/training/basics/data-storage/databases.html
 * This class is for storing puzzles in a Database and returning them if needed.
 * Puzzles can as well be updated.
 */
public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "puzzles.db";
    public static final String TABLE_PUZZLES  = "puzzles";

    //Column names
    public static final String COL_1_ID = "_id";
    public static final String COL_2_NAME = "NAME";
    public static final String COL_3_DESCRIPTION = "DESCRIPTION";
    public static final String COL_4_DiFFICULTY_INT = "DIFFICULTY_INT";
    public static final String COL_5_DIFFICULTY_STRING = "DIFFICULTY_STRING";
    public static final String COL_6_POSITIONS = "POSITIONS";
    public static final String COL_7_SOLVED = "SOLVED";

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getCreationQuery()); //Create DB
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PUZZLES); //delete old table
        db.execSQL(getCreationQuery());                    //create new one
        insertPuzzle(Puzzle.fromJson(PuzzleParser.getJSONArrayFromRes())); //put puzzles from txt file here.
    }

    /**
     * Method takes a puzzle and writes it in the DB.
     * If a Puzzle with the same id is there it overwrites it.
     * See: http://stackoverflow.com/questions/13311727/android-sqlite-insert-or-update
     * @param puzzle the puzzle to write in the DB
     * @return if this worked.
     */
    public boolean updatePuzzle(Puzzle puzzle){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getContentValues(puzzle);
        long result;
        try{        //Safely try to update
            result = db.replaceOrThrow(TABLE_PUZZLES, null, values);
        }catch (SQLiteException e){
            e.printStackTrace();
            db.close();
            return false;
        }
        db.close();
        if(result == -1)return false;
        else return true;
    }

    /**
     * Inserts a puzzle into the database.
     * If it is already existing (same id) then nothing happens.
     * At least thats what I want it to do.
     * @param puzzle the puzzle to be inserted
     * @return if this was sucessfull
     */
    public boolean insertPuzzle(Puzzle puzzle){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getContentValues(puzzle);
        long result;
        try{
            result = db.insertWithOnConflict(TABLE_PUZZLES, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        }catch (SQLiteException e){
            e.printStackTrace();
            db.close();
            return false;
        }
        db.close();
        if(result == -1)return false;
        else return true;
    }

    /**
     * Inserts multiple puzzles into the database.
     * Returns true if something was inserted.
     * Returns false if nothing could be inserted.
     * @param puzzles the puzzles to be inserted.
     */
    public boolean insertPuzzle(ArrayList<Puzzle> puzzles){
        boolean insertedSomething = false;
        for(int i = 0; i<puzzles.size();i++){
            boolean result = insertPuzzle(puzzles.get(i));
            if(result)insertedSomething = true;
        }
        this.close();
        return insertedSomething;
    }

    /**
     * A method that returns all puzzles from the dataBase
     * @return a List of puzzles
     */
    public ArrayList<Puzzle> getAllPuzzles(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_PUZZLES,null);   //Cursor is something like a Pointer.
        ArrayList<Puzzle> puzzleArray = new ArrayList<>();
        if(cursor.getCount() == 0){
            db.close();
            return null;
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            JSONObject puzzleAsJSON = rowToJSON(cursor);
            Puzzle puzzle = new Puzzle(puzzleAsJSON);
            puzzleArray.add(puzzle);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return puzzleArray;
    }

    /**
     * Method takes a cursor at a certain position e.g a row
     * and translates it to a database entry.
     * @param cursor
     * @return the JSONObject containing the puzzle from the row the cursor points to.
     */
    private JSONObject rowToJSON(Cursor cursor) {
        try {
            JSONObject jo = new JSONObject()        //New JSON object
                    .put("Id", cursor.getInt(0))    //Insert key value pairs
                    .put("Name", cursor.getString(1))
                    .put("Description", cursor.getString(2))
                    .put("DifficultyAsString", cursor.getString(4))
                    .put("DifficultyAsInt", cursor.getInt(3))
                    .put("Positions", new JSONArray(cursor.getString(5)))
                    .put("Solved", ( getBooleanVal(cursor.getInt(6))));
            return jo;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Takes an int ant converts it to a boolean value.
     * After the convention 0 = false, 1 = true.
     * Because SQLite doesnt know booleans but JSON does.
     * @param anInt the int that you want to convert to a boolean
     * @return the boolean value
     */
    private boolean getBooleanVal(int anInt) {
        if( anInt == 1)return true;
        else return false;
    }

    private int toInt(boolean b){
        if(b) return 1;
        return 0;
    }

    private ContentValues getContentValues(Puzzle puzzle) {
        ContentValues values = new ContentValues();
        values.put(COL_1_ID,puzzle.getId());
        values.put(COL_2_NAME,puzzle.getName());
        values.put(COL_3_DESCRIPTION,puzzle.getDescription());
        values.put(COL_4_DiFFICULTY_INT,puzzle.getDifficultyAsInt());
        values.put(COL_5_DIFFICULTY_STRING,puzzle.getDifficultyAsText());
        values.put(COL_6_POSITIONS,puzzle.getPositionsAsFEN().toString());
        values.put(COL_7_SOLVED,toInt(puzzle.getSolved())); //SQL only accepts int values / text no boolean
        return values;
    }

    /**
     * Creates an String with a query to create a DB.
     * In other words it creates a SQL-command as a String
     * @return SQLITE-Command as String
     */
    private String getCreationQuery() {
        String query = "CREATE TABLE "+TABLE_PUZZLES + " (" +
                COL_1_ID + " INTEGER PRIMARY KEY," +
                COL_2_NAME + " TEXT," +
                COL_3_DESCRIPTION + " TEXT," +
                COL_4_DiFFICULTY_INT + " INTEGER," +
                COL_5_DIFFICULTY_STRING + " TEXT," +
                COL_6_POSITIONS + " TEXT,"+
                COL_7_SOLVED + " INTEGER)";
        return query;
    }
}
