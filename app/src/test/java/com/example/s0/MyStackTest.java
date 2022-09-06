package com.example.s0;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyStackTest {

    @Test
    public void testFirstPush() {
        // Given an empty stack and a string
        MyStack stack = new MyStack();
        String testString = "hello world!";

        // When I push the string onto the stack
        stack.push(testString);

        // Then it should be at the top of the stack
        assertEquals(testString, stack.top());
    }
}