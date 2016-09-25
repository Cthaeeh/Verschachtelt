package com.example.kai.verschachtelt.puzzleGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kai.verschachtelt.R;
import com.example.kai.verschachtelt.activitys.MainActivity;

import java.util.ArrayList;

/**
 * Created by Kai on 12.08.2016.
 */
public class PuzzlesAdapter extends ArrayAdapter<Puzzle> {

    public PuzzlesAdapter(Context context, ArrayList<Puzzle> puzzles) {
        super(context,0,puzzles);
    }

    //TODO divide this into smaller chunks
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Get the puzzle item for this position
        Puzzle puzzle= getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_puzzle, parent, false);
        }
        // Lookup view for data population
        TextView  puzzleName = (TextView) convertView.findViewById(R.id.puzzleName);
        TextView  puzzleDescription = (TextView) convertView.findViewById(R.id.puzzleDescription);
        TextView  puzzleDifficulty = (TextView) convertView.findViewById(R.id.puzzleDifficulty);
        ImageView puzzleSolved = (ImageView) convertView.findViewById(R.id.doneIcon);

        // Populate the data into the template view using the data object
        puzzleName.setText(puzzle.getName());
        puzzleDescription.setText(puzzle.getDescription());
        puzzleDifficulty.setText(puzzle.getDifficultyAsText());
        if(puzzle.getSolved()){ //If the puzzle is displayed set the solved icon to ic_done_black
            Bitmap bitmap = (BitmapFactory.decodeResource(MainActivity.getContext().getResources(),R.mipmap.ic_done_black_48dp));
            puzzleSolved.setImageBitmap(bitmap);
        }else {
            puzzleSolved.setImageResource(android.R.color.transparent);
            //We always have to set the puzzleSolved ImageView because
            // otherwise the ListView will recycle the one from the last screen (6/5 puzzles before)
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
