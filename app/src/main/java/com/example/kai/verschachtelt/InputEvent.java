package com.example.kai.verschachtelt;

import com.example.kai.verschachtelt.chessLogic.Chessman;

/**
 * Created by Kai on 11.08.2016.
 * An interface to collect all possible input events the GamePanel concerning.
 */
public interface InputEvent {

    void handleTouchOnSquare(Integer position);

    void handlePromotion(Chessman.Piece chessman);

    void handleUndoButton();

    void handleShowNextMoveButton();
}
