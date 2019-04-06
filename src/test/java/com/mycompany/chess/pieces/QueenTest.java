/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chess.pieces;

import com.mycompany.chess.board.Board;
import com.mycompany.chess.board.Coords;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fuji
 */
public class QueenTest {
    
    public QueenTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getPossibleMoves method, of class Queen.
     */
    @Test
    public void testGetPossibleMoves() {
        System.out.println("toString");
        Queen instance = new Queen(0,0, false);
        String expResult = "Queen";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Queen.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Queen instance = new Queen(0,0,false);
        String expResult = "Queen";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of addMoves method, of class Queen.
     */
    @Test
    public void testAddMoves() {
        Queen instance = new Queen(0,0, false);
        assertEquals(0, instance.getPossibleMoves(new Board()).size());
        instance.setX(1);
        instance.setY(1);
        assertEquals(11, instance.getPossibleMoves(new Board()).size());
        instance.setX(5);
        instance.setY(2);
        assertEquals(17, instance.getPossibleMoves(new Board()).size());
        instance.setX(4);
        instance.setY(4);
        assertEquals(19, instance.getPossibleMoves(new Board()).size());
        instance.setX(7);
        instance.setY(7);
        assertEquals(3, instance.getPossibleMoves(new Board()).size());
    }

 
}
