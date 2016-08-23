package com.example.kai.verschachtelt;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by Kai on 23.08.2016.
 * This class is for storing the ChessGame Object in the GameActivity.
 * This Fragment will not be destroyed when the screen Orientation is changed.
 * See: https://developer.android.com/guide/topics/resources/runtime-changes.html
 */
public class RetainedFragment extends Fragment {

    // data object we want to retain
    private ChessGame data;

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public void setData(ChessGame data) {
        this.data = data;
    }

    public ChessGame getData() {
        return data;
    }
}