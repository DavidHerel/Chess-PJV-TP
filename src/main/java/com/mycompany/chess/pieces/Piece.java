/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chess.pieces;

import com.mycompany.chess.board.Board;
import com.mycompany.chess.board.Coords;
import java.util.ArrayList;

/**
 *
 * @author fuji
 */
public abstract class Piece implements java.io.Serializable {
    int x;
    int y;
    public boolean black;

    /**
     *
     * @param x
     * @param y
     * @param black
     */
    public Piece(int x, int y, boolean black) {
        this.x = x;
        this.y = y;
        this.black = black;
    }


    
    /**
     *Return Map of all possible moves
     * @param board - current stage of board
     * @param piece - current piece
     * @return Arraylist of valid moves
     */
    public abstract ArrayList<Coords> getPossibleMoves(Board board);

    @Override
    public String toString() {
        return "Piece{" + "x=" + x + ", y=" + y + ", black=" + black + '}';
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }  
    
    public boolean check(Board board){
        return false;
    }
    
}
