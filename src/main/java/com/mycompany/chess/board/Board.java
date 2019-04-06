/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chess.board;

import com.mycompany.chess.pieces.Bishop;
import com.mycompany.chess.pieces.King;
import com.mycompany.chess.pieces.Knight;
import com.mycompany.chess.pieces.Pawn;
import com.mycompany.chess.pieces.Piece;
import com.mycompany.chess.pieces.Queen;
import com.mycompany.chess.pieces.Rook;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fuji
 */
public class Board implements java.io.Serializable {

    /**
     *
     */
    public final Tile[][] tiles;
    ArrayList<Piece> removedPieces = new ArrayList<>();
    boolean color = false;
    ArrayList<Piece> allPieces = new ArrayList<>();
    ArrayList<Coords> enPassant = new ArrayList<>();
    File files;
    boolean kngb;
    boolean kngw;

    public boolean isKngb() {
        return kngb;
    }

    public void setKngb(boolean kngb) {
        this.kngb = kngb;
    }

    public boolean isKngw() {
        return kngw;
    }

    public void setKngw(boolean kngw) {
        this.kngw = kngw;
    }

    public File getFile() {
        return files;
    }

    public void setFile(File file) {
        this.files = file;
    }

    /**
     *
     * @return
     */
    public ArrayList<Coords> getEnPassant() {
        return enPassant;
    }

    /**
     *
     * @param e
     */
    public void setEnPassant(Coords e) {
        this.enPassant.add(e);
    }

    /**
     *
     * @return
     */
    public ArrayList<Piece> getAllPieces() {
        return allPieces;
    }

    /**
     *Creates Board 8x8
     * Array[][] from 8x8 tiles
     */
    public Board() {
        files = new File("normal.txt");
        this.tiles = new Tile[8][8];
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                this.tiles[i][j] = new Tile(i, j);
            }
        }
        setPieces();
    }

    /**
     *Return Tile on coordinates x and y
     * @param x - coordinate
     * @param y - coordinate
     * @return Tile on coordinates x and y
     */
    public Tile getTiles(int x, int y) {
        return tiles[x][y];
    }
    
    /**
     *Check if is on board
     * @param x
     * @param y
     * @return
     */
    public boolean isOnBoard(int x, int y){
        return x >= 0 && y >=0 && y<8 && x <8;
    }

    /**
     *Remove piece
     * @param x
     * @param y
     */
    public void removePiece(int x, int y){
        removedPieces.add(tiles[x][y].piece);
        tiles[x][y].killPiece();
    }

    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                ret += this.tiles[i][j] + "\n";
            }
        }
        return ret;
    }

    /**
     * Resets the board
     */
    public void boardReset(){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                this.tiles[i][j] = new Tile(i, j);
            }
        }
        //intialize pawns
        for (int i = 0; i <8; i++){
            tiles[i][1].putPiece(new Pawn(i, 1, !color));
            tiles[i][6].putPiece(new Pawn(i, 6, color));
        }
        //now put rooks
        tiles[0][0].putPiece(new Rook(0, 0, !color));
        tiles[7][0].putPiece(new Rook(7, 0, !color));
        tiles[0][7].putPiece(new Rook(0, 7, color));
        tiles[7][7].putPiece(new Rook(7, 7, color));
        //now put knights
        tiles[1][0].putPiece(new Knight(1, 0, !color));
        tiles[6][0].putPiece(new Knight(6, 0, !color));
        tiles[1][7].putPiece(new Knight(1, 7, color));
        tiles[6][7].putPiece(new Knight(6, 7, color));
        //now put bishops
        tiles[2][0].putPiece(new Bishop(2, 0, !color));
        tiles[5][0].putPiece(new Bishop(5, 0, !color));
        tiles[2][7].putPiece(new Bishop(2, 7, color));
        tiles[5][7].putPiece(new Bishop(5, 7, color));
        //now queen and king
        tiles[4][0].putPiece(new King(4, 0, !color));
        tiles[3][0].putPiece(new Queen(3, 0, !color));
        tiles[4][7].putPiece(new Queen(4, 7, color));
        tiles[3][7].putPiece(new King(3, 7, color));
        
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (tiles[i][j].getPiece() != null && tiles[i][j].getPiece().black == false){
                    allPieces.add(tiles[i][j].getPiece());
                }
            }
        }
    }

    /**
     *
     */
    public void saveBoard(){
        FileOutputStream f;
        try {
            f = new FileOutputStream(new File("savedGame.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    o.writeObject(tiles[i][j].piece);
                }
            }   
            o.close();
            f.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
        
    /**
     *
     * @param file
     */
    public void loadBoard(File file){
        
        FileInputStream fi;
        try {
            fi = new FileInputStream(file);
            ObjectInputStream oi = new ObjectInputStream(fi);
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    tiles[i][j] = (Tile) oi.readObject();
                }
            } 
            oi.close();
            fi.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
    public void putFigures(File file){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (tiles[i][j].piece!=null){
                    tiles[i][j].killPiece();
                    //System.out.println("AAA");
                }
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals("WHITE PAWN")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    tiles[x][y].putPiece(new Pawn(x, y, !color));
                } if (line.equals("WHITE BISHOP")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    //System.out.println("x is " +x +" y is " + y);
                    tiles[x][y].putPiece(new Bishop(x, y, !color));
                } if (line.equals("WHITE KING")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    tiles[x][y].putPiece(new King(x, y, !color));
                } if (line.equals("WHITE KNIGHT")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    tiles[x][y].putPiece(new Knight(x, y, !color));
                } if (line.equals("WHITE QUEEN")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    tiles[x][y].putPiece(new Queen(x, y, !color));
                } if (line.equals("WHITE ROOK")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    tiles[x][y].putPiece(new Rook(x, y, !color));
                }  if (line.equals("BLACK PAWN")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    tiles[x][y].putPiece(new Pawn(x, y, color));
                }  if (line.equals("BLACK BISHOP")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                   // System.out.println("x is " +x +" y is " + y);
                    tiles[x][y].putPiece(new Bishop(x, y, color));
                }   if (line.equals("BLACK KING")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    tiles[x][y].putPiece(new King(x, y, color));
                }   if (line.equals("BLACK KNIGHT")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    tiles[x][y].putPiece(new Knight(x, y, color));
                }   if (line.equals("BLACK QUEEN")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    tiles[x][y].putPiece(new Queen(x, y, color));
                }   if (line.equals("BLACK ROOK")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                   // System.out.println("x is " +x +" y is " + y);
                    tiles[x][y].putPiece(new Rook(x, y, color));
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
   
    /**Set board from TEXT FILE
     *
     */
    public void setPieces(){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (tiles[i][j].getPiece()!=null){
                    tiles[i][j].killPiece();
                    if (allPieces.size() >0){
                        allPieces.remove(0);
                    }
                }
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(files))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals("WHITE PAWN")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    tiles[x][y].putPiece(new Pawn(x, y, !color));
                } if (line.equals("WHITE BISHOP")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    //System.out.println("x is " +x +" y is " + y);
                    tiles[x][y].putPiece(new Bishop(x, y, !color));
                } if (line.equals("WHITE KING")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    tiles[x][y].putPiece(new King(x, y, !color));
                } if (line.equals("WHITE KNIGHT")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    tiles[x][y].putPiece(new Knight(x, y, !color));
                } if (line.equals("WHITE QUEEN")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    tiles[x][y].putPiece(new Queen(x, y, !color));
                } if (line.equals("WHITE ROOK")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    tiles[x][y].putPiece(new Rook(x, y, !color));
                }  if (line.equals("BLACK PAWN")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    tiles[x][y].putPiece(new Pawn(x, y, color));
                }  if (line.equals("BLACK BISHOP")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                  //  System.out.println("x is " +x +" y is " + y);
                    tiles[x][y].putPiece(new Bishop(x, y, color));
                }   if (line.equals("BLACK KING")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    tiles[x][y].putPiece(new King(x, y, color));
                }   if (line.equals("BLACK KNIGHT")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    tiles[x][y].putPiece(new Knight(x, y, color));
                }   if (line.equals("BLACK QUEEN")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    tiles[x][y].putPiece(new Queen(x, y, color));
                }   if (line.equals("BLACK ROOK")){
                    int x =Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    //System.out.println("x is " +x +" y is " + y);
                    tiles[x][y].putPiece(new Rook(x, y, color));
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (tiles[i][j].getPiece() != null && tiles[i][j].getPiece().black == false){
                    allPieces.add(tiles[i][j].getPiece());
                }
            }
        }
    }
        
    
}
