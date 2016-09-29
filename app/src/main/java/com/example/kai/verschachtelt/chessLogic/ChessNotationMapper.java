package com.example.kai.verschachtelt.chessLogic;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by ivayl on 29.09.2016.
 */
public class ChessNotationMapper {

    private static HashMap <String,Integer> map = new HashMap<>();

    public ChessNotationMapper(){
        map = createEnPassantHashMap();
    }



    /**
     * creating a hashMap for all the fields, a pawn can jump over and invoke enPassant - possibilities
     * @return hashmap with all the possible fields over which a pawn can jump to invoke an enPassant possibility
     */
    private static HashMap createEnPassantHashMap(){
        map.put("a3",1);
        map.put("b3",2);
        map.put("c3",3);
        map.put("d3",4);
        map.put("e3",5);
        map.put("f3",6);
        map.put("g3",7);
        map.put("h3",8);

        map.put("a6",9);
        map.put("b6",10);
        map.put("c6",11);
        map.put("d6",12);
        map.put("e6",13);
        map.put("f6",14);
        map.put("g6",15);
        map.put("h6",16);

        return map;
    }

    /**
     * method for evaluating the part of a FEN-String involving EnPassant
     * @param position the position over which the pawn jumped as a string in the official notation
     * @return the position over which the pawn jumped on our 8 x 8 board
     */
    public static int getEnPassantPosition(String position) {

        if(!map.containsKey(position)) Log.e("ChessNotationMapper", "No such HashMap-Entry!");
        if(map.get(position) < 9) return map.get(position) + 15;
        else return map.get(position) + 39;
    }


}

