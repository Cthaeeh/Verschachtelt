package com.example.kai.verschachtelt.chessLogic;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by ivayl on 29.09.2016.
 * This class if for mapping human readable chess notatio, like e4 to positions used in the chessLogicPackage
 * e.g addresses in a length 64 array.
 * For now just the enPassant-Fields are supported !
 */
public class ChessNotationMapper {

    private HashMap <String,Integer> map = new HashMap<>();

    public ChessNotationMapper(){
        createEnPassantHashMap();
    }

    /**
     * Creates a hashmap that connects normal chess notation with addresses in a array of length 64
     * z.B. a8->0 or h1->63
     * Warning: currently only supports en passant fields.
     */
    private void createEnPassantHashMap(){
        map.put("a3",48);
        map.put("b3",49);
        map.put("c3",50);
        map.put("d3",51);
        map.put("e3",52);
        map.put("f3",53);
        map.put("g3",54);
        map.put("h3",55);

        map.put("a6",16);
        map.put("b6",17);
        map.put("c6",18);
        map.put("d6",19);
        map.put("e6",20);
        map.put("f6",21);
        map.put("g6",22);
        map.put("h6",23);
    }

    /**
     * method for translating chess noation to the position in a array with length 64
     *
     * Warning ! Currently only enPassant Fields supported!
     * @param positionAsString the position over which the pawn jumped as a string in the official notation
     * @return the position on in a array of length 64
     */
    public int getEnPassantPosition(String positionAsString) {
        if(!map.containsKey(positionAsString)){
            Log.e("ChessNotationMapper", "No such HashMap-Entry!");
            return -1;
        }
        return map.get(positionAsString);
    }

}

