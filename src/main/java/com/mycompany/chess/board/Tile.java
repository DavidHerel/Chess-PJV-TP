/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chess.board;

import com.mycompany.chess.pieces.Pawn;
import com.mycompany.chess.pieces.Piece;

/**
 *Tile class:
 * 
 * @author fuji
 */
public class Tile implements java.io.Serializable {
    int x;
    int y;

    /**
     *
     */
    public Piece piece;
    boolean promotion;
    boolean enpassant;
    /**
     *Constructor for intializing Tile
     * coordinates x, y
     * and piece
     * @param x
     * @param y
     */
    public Tile(int x, int y){
        this.x = x;
        this.y = y;
        this.piece = null;
    }
    
    /**
     *Checking if Tile has Piece
     * If not return false
     * If yes return true
     * @return true or false
     */
    public boolean isEmpty(){
        return (piece == null);        
    }
    
    /**
     *Puts piecec on tile
     * @param piece
     */
    public void putPiece(Piece piece){
        this.piece = null;
        this.piece = piece;
        if (this.piece == null){
            return;
        }
        //System.out.println(this.piece);
        //System.out.println("Piece coord " + this.piece.getX() + "tile coord " + x);
        this.piece.setX(x);
        this.piece.setY(y);
        if ((y ==7 && this.piece instanceof Pawn) || ((y ==0 && this.piece instanceof Pawn))){
            setPromotion(true);
        }
    }

    /**
     * Check if Tile is enPassant
     * @return
     */
    public boolean isEnpassant() {
        return enpassant;
    }

    /**
     *Set tile enPassant
     * @param enpassant
     */
    public void setEnpassant(boolean enpassant) {
        this.enpassant = enpassant;
    }

    /**
     *CHeck if PIece on tile can be romoted
     * @return
     */
    public boolean isPromotion() {
        return promotion;
    }

    /**
     *Set piece to promotion
     * @param promotion
     */
    public void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }
    /**
     *Return piece
     * @return piece
     */
    public Piece getPiece() {
        return piece;
    }
    
    /**
     * KIlls piece
     */
    public void killPiece(){
        this.piece = null;
    }

    @Override
    public String toString() {
        return "x = " + x + "y = " + y + "piece = " + piece;
    }

    /**
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public int getY() {
        return y;
    }
    

    
    
    
            
}
