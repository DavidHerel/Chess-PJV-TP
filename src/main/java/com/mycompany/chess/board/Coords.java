/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chess.board;

/**
 *
 * @author fuji
 */
public class Coords implements java.io.Serializable {
    int x;
    int y;

    /**
     * Storing coordinates
     * @param x
     * @param y
     */
    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coords{" + "x=" + x + ", y=" + y + '}';
    }
    
    
}
