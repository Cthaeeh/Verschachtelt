package com.example.kai.verschachtelt.pvAIGame.ai;

import android.util.Log;

/**
 * Created by Kai on 16.09.2016.
 * Since java doesn´t support stack of primitives but i want one:
 * Credits go to: http://www.tutorialspoint.com/javaexamples/data_stack.htm
 */
public class IntStack {
    private final int maxSize;
    private int[] stackArray;
    private int top;        //The pointer if you want so.
    private final String TAG = "IntStack";

    /**
     * Creates a new empty stack
     * @param size the max size of the stack
     */
    public IntStack(int size) {
        maxSize = size;
        stackArray = new int[maxSize];
        top = -1;
    }

    /**
     * Puts something on the top of the stack.
     * @param val
     */
    public void push(int val) {
        stackArray[++top] = val;
        if(top>9)Log.d(TAG,"i was pushed to much");
    }

    /**
     * gets the first element of the stack and removes it
     * @return the element
     */
    public int pop() {
        return stackArray[top--];
    }

    /**
     * Like pop but it doesn´t remove it
     * @return
     */
    public int peek() {
        return stackArray[top];
    }


    public boolean isEmpty() {
        return (top == -1);
    }

    public boolean isFull() {
        return (top == maxSize - 1);
    }


}
