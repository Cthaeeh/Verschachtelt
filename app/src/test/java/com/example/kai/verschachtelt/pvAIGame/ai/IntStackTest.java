package com.example.kai.verschachtelt.pvAIGame.ai;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kai on 16.09.2016.
 */
public class IntStackTest {



    @Test
    public void testPush() throws Exception {
        IntStack testStack = new IntStack(10);
        testStack.push(13);
        assert(testStack.peek() == 13);
    }

    @Test
    public void testPop() throws Exception {
        IntStack testStack = new IntStack(10);
        testStack.push(13);
        int result = testStack.pop();
        assert(result == 13);
        assert(testStack.isEmpty()==true);
    }

    @Test
    public void testPeek() throws Exception {

    }
}