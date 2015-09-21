package org.arquillian.example.twostacksmaven;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.junit.Test;

/**
 *
 * @author hom
 */
public class LinkedStackTest extends StackTestBase {

    @Override
    Stack<String> createInstance() {
        return new LinkedStack<>();
    }

    @Test( expected = NullPointerException.class )
    @Override
    public void empty_peek_should_bark() {
        super.empty_peek_should_bark();
    }

}
