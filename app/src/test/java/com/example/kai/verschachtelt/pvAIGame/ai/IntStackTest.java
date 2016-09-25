package com.example.kai.verschachtelt.pvAIGame.ai;

import org.junit.Test;


/**
 * Created by Kai on 16.09.2016.
 */
public class IntStackTest {

    @Test
    public void testPush() throws Exception {
        IntStack testStack = new IntStack(10);
        testStack.push(13);
        if (testStack.peek() != 13) throw new AssertionError();
    }

    @Test
    public void testPop() throws Exception {
        IntStack testStack = new IntStack(10);
        testStack.push(13);
        int result = testStack.pop();
        if (result != 13) throw new AssertionError();
        if (testStack.isEmpty() != true) throw new AssertionError();
    }

}