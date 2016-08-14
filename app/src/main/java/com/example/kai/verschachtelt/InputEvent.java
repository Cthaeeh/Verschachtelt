package com.example.kai.verschachtelt;

/**
 * Created by Kai on 11.08.2016.
 * An interface to collect all possible input events the GamePanel concerning.
 */
public interface InputEvent {

    void handleTouchOnSquare(Integer position);

    void handleUndoButton();

    void handleRedoButton();

    void handleShowNextMoveButton();
}
