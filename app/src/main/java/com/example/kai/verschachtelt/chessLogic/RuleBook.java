package com.example.kai.verschachtelt.chessLogic;

import java.lang.reflect.Array;

/**
 * Created by Kai on 10.08.2016.
 * this class contains all move possibilitys. But no collision detection
 */
public class RuleBook {

    private boolean[] possibleMoves = new boolean[64];

    public boolean[] getPossibleRookMoves(int currentPosition){
        resetPossibleMoves();
        for(int i=0;i<64;i++){
            if(i!=currentPosition){
                if(i%8==currentPosition%8 || i/8==currentPosition/8){   //If square is in the same row or column.
                    possibleMoves[i]=true;
                }
            }
        }
        return possibleMoves;
    }

    public boolean[] getPossibleKnightMoves(int currentPosition){
        resetPossibleMoves();
        if(currentPosition+10<64&&currentPosition%8<6 )possibleMoves[currentPosition+10]=true;
        if(currentPosition+6<64 &&currentPosition%8>1 )possibleMoves[currentPosition+6]=true;
        if(currentPosition+17<64&&currentPosition%8<7 )possibleMoves[currentPosition+17]=true;
        if(currentPosition+15<64&&currentPosition%8>0 )possibleMoves[currentPosition+15]=true;
        if(currentPosition-10>0 &&currentPosition%8>1 )possibleMoves[currentPosition-10]=true;
        if(currentPosition-6>0  &&currentPosition%8<6 )possibleMoves[currentPosition-6]=true;
        if(currentPosition-17>0 &&currentPosition%8>0 )possibleMoves[currentPosition-17]=true;
        if(currentPosition-15>0 &&currentPosition%8<7 )possibleMoves[currentPosition-15]=true;
        return possibleMoves;
    }

    public boolean[] getPossibleBishopMoves(int currentPosition){
        resetPossibleMoves();
        for (int i =0;i<64;i++){
            //If the deltaX (the distance it moves in x-Direction) equals deltaY then it is a legit move
            if(Math.abs(i%8-currentPosition%8)==Math.abs(i/8-currentPosition/8))possibleMoves[i]=true;
            if(i==currentPosition)possibleMoves[i]=false;
        }
        return possibleMoves;
    }

    public boolean[] getPossibleQueenMoves(int currentPosition){
        boolean[] rookMoves = getPossibleRookMoves(currentPosition).clone();
        boolean[] bishopMoves = getPossibleBishopMoves(currentPosition).clone();
        resetPossibleMoves();
        for (int i = 0;i<64;i++){
            //Just overlap the possible rook and bishop moves.
            if(rookMoves[i]||bishopMoves[i])possibleMoves[i]=true;
        }
        return possibleMoves;
    }

    public boolean[] getPossibleKingMoves(int currentPosition){
        resetPossibleMoves();
            if(currentPosition+1<64)possibleMoves[currentPosition+1]=true;
            if(currentPosition-1>=0)possibleMoves[currentPosition-1]=true;
            if(currentPosition+8<64)possibleMoves[currentPosition+8]=true;
            if(currentPosition-8>=0)possibleMoves[currentPosition-8]=true;
        return possibleMoves;
    }

    public boolean[] getPossibleUpperPawnMoves(int currentPosition){
        resetPossibleMoves();
        if(currentPosition+8<64)possibleMoves[currentPosition+8]=true;  //Can always move one step forward.
        if(currentPosition+9<64)possibleMoves[currentPosition+9]=true;
        if(currentPosition+7<64)possibleMoves[currentPosition+7]=true;
        if(currentPosition/8==1)possibleMoves[currentPosition+16]=true; //If he is on starting Position he can move 2
        return possibleMoves;
    }

    public boolean[] getPossibleLowerPawnMoves(int currentPosition){
        resetPossibleMoves();
        if(currentPosition-8>0)possibleMoves[currentPosition-8]=true;  //Can always move one step forward.
        if(currentPosition-9>0)possibleMoves[currentPosition-9]=true;
        if(currentPosition-7>0)possibleMoves[currentPosition-7]=true;
        if(currentPosition/8==6)possibleMoves[currentPosition-16]=true; //If he is on starting Position he can move 2
        return possibleMoves;
    }

    private void resetPossibleMoves() {
        for (int i=0;i<64;i++){
            possibleMoves[i]=false;
        }
    }
}
