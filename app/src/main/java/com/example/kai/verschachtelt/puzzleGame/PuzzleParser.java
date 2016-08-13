package com.example.kai.verschachtelt.puzzleGame;

import android.util.Log;

import com.example.kai.verschachtelt.R;
import com.example.kai.verschachtelt.activitys.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Kai on 13.08.2016.
 */
public class PuzzleParser {
    /**
     * Method takes puzzles txt file from ressources and converts it to a JSONObject.
     * @return
     */
    public static JSONArray getJSONArray(){
        //Get Data From Text Resource File Contains Json Data.
        InputStream inputStream = MainActivity.getContext().getResources().openRawResource(R.raw.puzzles);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Parse the data into jsonobject to get original data in form of json.
            JSONArray jsonArray = new JSONArray(
                    byteArrayOutputStream.toString());
            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
