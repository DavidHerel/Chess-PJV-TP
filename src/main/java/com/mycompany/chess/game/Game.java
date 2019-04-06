/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chess.game;

import com.mycompany.chess.board.Board;
import com.mycompany.chess.board.Coords;
import com.mycompany.chess.board.Tile;
import com.mycompany.chess.pieces.King;
import com.mycompany.chess.pieces.Pawn;
import com.mycompany.chess.pieces.Piece;
import com.mycompany.chess.pieces.Queen;
import com.mycompany.chess.pieces.Rook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Platform.exit;

/**
 *
 * @author fuji
 */
public class Game implements java.io.Serializable {
    Board board;
    public boolean turn;
    boolean ai;
    boolean over;

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
    private Random randomGenerator;
    boolean color;
    int countT = 0;
    long whiteT;
    long blackT;
    int livess = 0;

           

    /**
     *Get white TIme
     * @return
     */
    public long getWhiteT() {
        return whiteT;
    }

    /**
     *Set WHite TIme
     * @param whiteT
     */
    public void setWhiteT(long whiteT) {
        this.whiteT = whiteT;
        
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if(board.getTiles(i, j).getPiece() != null && board.getTiles(i, j).getPiece() instanceof King){
                    board.getTiles(i, j).getPiece().check(board);
                }
            }
        }
    }

    /**
     *Get black time
     * @return
     */
    public long getBlackT() {
        return blackT;
        
    }

    /**
     *Set black time
     * @param blackT
     */
    public void setBlackT(long blackT) {
        this.blackT = blackT;
        
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if(board.getTiles(i, j).getPiece() != null && board.getTiles(i, j).getPiece() instanceof King){
                    board.getTiles(i, j).getPiece().check(board);
                }
            }
        }
    }
    
    /**
     *
     * @param board
     */
    public Game(Board board) {
        this.board = board;
        this.turn = true;
        this.ai = true;
        this.over = false;
    }

    /**
     * IF the game is over
     * @return
     */
    public boolean isOver() {
        return over;
    }

    /**
     * Sets over
     * @param over
     */
    public void setOver(boolean over) {
        this.over = over;
    }
    
    /**
     *Makes move from coordinates 
     * also checking if there is enpassant and checking if move is ai or not ai
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     */
    public void makeMove(int fromX, int fromY, int toX, int toY){
        if (board.tiles[fromX][fromY].piece == null){
            return;
        }
        if (!ai){
            //chyba kdyz se klikne na pawna
            //System.out.println("QUEEN IS on" + board.getTiles(3, 3).getPiece());
            makeNoAiMove(fromX,fromY, toX, toY);
            
        }
        if(ai){
            makeAiMove(fromX,fromY, toX, toY);
           
        }
        
        
        
        }

    /**
     *If ai is set
     * @return
     */
    public boolean isAi() {
        return ai;
    }

    /**
     * Sets ai
     * @param ai
     */
    public void setAi(boolean ai) {
        this.ai = ai;
        turn ^= true;
    }

    /**
     * Return board
     * @return board
     */
    public Board getBoard() {
        return board;
    }
    
    /**
     *CHecking who is on turn
     * @return turn
     */
    public boolean getTurn(){
        return turn;
    }

    /**
     * Sets board
     * @param board
     */
    public void setBoard(Board board) {
        this.board = board;
    }
    
    /**
     *Saves pieces on board
     */
    public void saveBoard(){
        FileOutputStream f;
        try {
            f = new FileOutputStream(new File("savedGame.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(getBoard()); 
            o.close();
            f.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
        
    /**
     *Load board from file
     * @param file
     */
    public void loadBoard(File file){
        
        FileInputStream fi;
        try {
            fi = new FileInputStream(file);
            ObjectInputStream oi = new ObjectInputStream(fi);
            setBoard((Board) oi.readObject()); 
            oi.close();
            fi.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
    
    public void putFig(File file){
        board.setFile(file);
        board.setPieces();
    }
    
    /**
     * Makes move from coordinates - checking who is on turn, en passant, promotion etc..
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     */
    public void makeNoAiMove(int fromX, int fromY, int toX, int toY){
        if (board.tiles[fromX][fromY].piece.black == turn){
            ArrayList<Coords> moves = board.tiles[fromX][fromY].piece.getPossibleMoves(board);
            for (int i =0; i< moves.size();i++){
                if (moves.get(i).getX() == toX && moves.get(i).getY() == toY){
                    if (board.tiles[toX][toY].piece instanceof King){
                        setOver(true);
                    }
                    int sizz = 0;
                        Piece piece = null;
                        for (int ik = 0; ik < 8; ik++){
                            for (int j = 0; j < 8; j++){
                                if(board.getTiles(ik, j).getPiece() != null && board.getTiles(ik, j).getPiece().black == turn && board.getTiles(ik, j).getPiece().getPossibleMoves(board).size()>0){
                                    piece = board.getTiles(ik, j).getPiece();
                                    sizz++;
                                }
                            }
                        }
                        //System.out.println("SIZE IS "+sizz);
                        if (piece == null){
                            setOver(true);
                            return;
                        }
                    int startRow = board.getTiles(fromX, fromY).getPiece().black ? 6 : 1;
                    int move = board.getTiles(fromX, fromY).getPiece().black ? -1 : 1;
                    if (board.getTiles(fromX, fromY).getPiece() != null && board.getTiles(fromX, fromY).getPiece() instanceof Pawn && board.getTiles(fromX, fromY).getPiece().getY() == startRow){
                        board.setEnPassant(new Coords(fromX, fromY+move));
                        livess = 2;
                    }
                    board.tiles[toX][toY].putPiece(board.tiles[fromX][fromY].piece);
                    if (board.getEnPassant().size() > 0&&board.tiles[fromX][fromY].piece!= null &&board.tiles[fromX][fromY].piece.black == true && board.getEnPassant().get(0).getX() == toX){
                        board.tiles[toX][toY+1].killPiece();
                    }
                    if (board.getEnPassant().size() > 0&&board.tiles[fromX][fromY].piece!= null &&board.tiles[fromX][fromY].piece.black == false && board.getEnPassant().get(0).getX() == toX){
                        board.tiles[toX][toY-1].killPiece();
                    }
                    color = board.tiles[toX][toY].piece.black;
                    board.tiles[fromX][fromY].killPiece();
                    if (board.tiles[toX][toY].piece.black == false){
                        board.getAllPieces().remove(board.tiles[toX][toY].piece);
                        //System.out.println(board.getAllPieces().size());
                    }
                    if(board.tiles[toX][toY].isPromotion()){
                        board.tiles[toX][toY].putPiece(new Queen(toX, toY, color));
                    }
                    
                    break;
        //                moveHistory.put(player, (List<Coords>) moves.get(i));

                }
            }
            countT++;
            
            turn ^= true;
            livess--;
            if (board.getEnPassant().size() > 0 && livess <= 0){
                        System.out.println(" TED JSEM VYMAZAL TENHLE INDEX"+board.getEnPassant().get(0));
                        board.getEnPassant().remove(0);
            }
            System.out.println("Enpassant size "+board.getEnPassant().size());
            if (board.getEnPassant().size() >0){
                System.out.println("Coords of enps x : "+ board.getEnPassant().get(0).getX() + " y: "+board.getEnPassant().get(0).getY());
            }

            }
    }
    
    /**
     * Makes move from coordinates, second turn is always ai, checking all rules
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     */
    public void makeAiMove(int fromX, int fromY, int toX, int toY){
        turn = true;
        if (board.tiles[fromX][fromY].piece.black == true){
            ArrayList<Coords> moves = board.tiles[fromX][fromY].piece.getPossibleMoves(board);
                for (int i =0; i< moves.size();i++){
                    if (moves.get(i).getX() == toX && moves.get(i).getY() == toY){
                        if (board.tiles[toX][toY].piece instanceof King){
                            setOver(true);
                        }
                        int sizz = 0;
                        Piece piece = null;
                        for (int ik = 0; ik < 8; ik++){
                            for (int j = 0; j < 8; j++){
                                if(board.getTiles(ik, j).getPiece() != null && board.getTiles(ik, j).getPiece().black == true && board.getTiles(ik, j).getPiece().getPossibleMoves(board).size()>0){
                                    piece = board.getTiles(ik, j).getPiece();
                                    sizz++;
                                }
                            }
                        }
                        //System.out.println("SIZE IS "+sizz);
                        if (piece == null){
                            setOver(true);
                            return;
                        }
                        int startRow = board.getTiles(fromX, fromY).getPiece().black ? 6 : 1;
                        int move = board.getTiles(fromX, fromY).getPiece().black ? -1 : 1;
                        if (board.getTiles(fromX, fromY).getPiece() != null && board.getTiles(fromX, fromY).getPiece() instanceof Pawn && board.getTiles(fromX, fromY).getPiece().getY() == startRow){
                            board.setEnPassant(new Coords(fromX, fromY+move));
                            livess = 2;
                        }
                        //board.getAllPieces().remove(board.tiles[toX][toY].piece);
                        //System.out.println(board.tiles[toX][toY].piece);
                        board.tiles[toX][toY].putPiece(board.tiles[fromX][fromY].piece);
                            if (board.getEnPassant().size() > 0&&board.tiles[fromX][fromY].piece!= null &&board.tiles[fromX][fromY].piece.black == true && board.getEnPassant().get(0).getX() == toX){
                            board.tiles[toX][toY+1].killPiece();
                        }
                        if (board.getEnPassant().size() > 0&&board.tiles[fromX][fromY].piece!= null &&board.tiles[fromX][fromY].piece.black == false && board.getEnPassant().get(0).getX() == toX){
                            board.tiles[toX][toY-1].killPiece();
                        }
                        color = board.tiles[toX][toY].piece.black;
                        board.tiles[fromX][fromY].killPiece();
                        if(board.tiles[toX][toY].isPromotion() && board.tiles[toX][toY].piece instanceof Pawn){
                            board.tiles[toX][toY].putPiece(new Queen(toX, toY, color));
                        }
                        break;
        //                moveHistory.put(player, (List<Coords>) moves.get(i));

                    }
                }
                
                countT++;
                livess--;
                if (board.getEnPassant().size() > 0 && livess <= 0){
                        System.out.println(board.getEnPassant().get(0));
                        board.getEnPassant().remove(0);
            }
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        int sizz = 0;
        ArrayList<Piece> randomaci = new ArrayList<Piece>();
        Piece piece = null;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if(board.getTiles(i, j).getPiece() != null && board.getTiles(i, j).getPiece().black == false && board.getTiles(i, j).getPiece().getPossibleMoves(board).size()>0){
                    piece = board.getTiles(i, j).getPiece();
                    randomaci.add(piece);
                    sizz++;
                }
            }
        }
        //System.out.println("SIZE IS "+sizz);
        if (piece == null){
            setOver(true);
            return;
        }
        Random randomGenerator = new Random();
        int indexx = randomGenerator.nextInt(randomaci.size());
        int x1 = randomaci.get(indexx).getX();
        int y1 = randomaci.get(indexx).getY();
        ArrayList<Coords> movesAI = randomaci.get(indexx).getPossibleMoves(board);
        if (movesAI.size() == 0){
            setOver(true);
            return;
        }
        if (board.tiles[movesAI.get(0).getX()][movesAI.get(0).getY()].piece instanceof King){
            //System.out.println("X COORDS"+movesAI.get(0).getX());
           // System.out.println("y COORDS"+movesAI.get(0).getY());
            setOver(true);
        }
        board.tiles[movesAI.get(0).getX()][movesAI.get(0).getY()].putPiece(board.tiles[x1][y1].piece);
        board.tiles[x1][y1].killPiece();
        
        if (board.tiles[movesAI.get(0).getX()][movesAI.get(0).getY()].getPiece() !=null){
            color = board.tiles[movesAI.get(0).getX()][movesAI.get(0).getY()].getPiece().black;
        }
        if(board.tiles[movesAI.get(0).getX()][movesAI.get(0).getY()].isPromotion() && board.tiles[movesAI.get(0).getX()][movesAI.get(0).getY()].piece instanceof Pawn){
            board.tiles[movesAI.get(0).getX()][movesAI.get(0).getY()].putPiece(new Queen(movesAI.get(0).getX(), movesAI.get(0).getY(), color));
        }
        
        countT++;
        livess--;
        if (board.getEnPassant().size() > 0 && livess <= 0){
                        System.out.println(board.getEnPassant().get(0));
                        board.getEnPassant().remove(0);
            }
        
    }
    
    /**
     * Checking if small Castle is possible
     * if is makes it
     */
    public void sCastle(){
        boolean possible = true;
        if (board.getTiles(4, 7).piece instanceof King && board.getTiles(7, 7).piece instanceof Rook){
            for (int i = 1; i< 3; i++){
                if (board.getTiles(4+i, 7).piece != null){
                    possible = false;
                }
            }
            if (possible && board.getTiles(4, 7).piece.black == turn){
                board.getTiles(6, 7).putPiece(new King(6, 7, true));
                board.getTiles(5, 7).putPiece(new Rook(5, 7, true));
                board.getTiles(4, 7).killPiece();
                board.getTiles(7, 7).killPiece();
                turn ^= true;
            }
        }
        possible = true;
        if (board.getTiles(4, 0).piece instanceof King && board.getTiles(7, 0).piece instanceof Rook){
            for (int i = 1; i< 3; i++){
               if (board.getTiles(4+i, 0).piece != null){
                    
                    possible = false;
                }
            }
            //System.out.println(" JEN SEM BLACK SMALL CASTLE" + (board.getTiles(4, 0).piece.black == turn));
            if (possible && board.getTiles(4, 0).piece.black == turn){
               // System.out.println("BLACK SMALL CASTLE");
                board.getTiles(6, 0).putPiece(new King(6, 0, false));
                board.getTiles(5, 0).putPiece(new Rook(5, 0, false));
                board.getTiles(4, 0).killPiece();
                board.getTiles(7, 0).killPiece();
                turn ^= true;
            }
        }
    }
    
    public void blackOnline(int fromX, int fromY, int toX, int toY){
            if (board.tiles[fromX][fromY].piece == null){
                return;
            }
            ArrayList<Coords> moves = board.tiles[fromX][fromY].piece.getPossibleMoves(board);
            for (int i =0; i< moves.size();i++){
                if (moves.get(i).getX() == toX && moves.get(i).getY() == toY){
                    if (board.tiles[toX][toY].piece instanceof King){
                        setOver(true);
                    }
                    /*
                    int sizz = 0;
                        Piece piece = null;
                        for (int ik = 0; ik < 8; ik++){
                            for (int j = 0; j < 8; j++){
                                if(board.getTiles(ik, j).getPiece() != null && board.getTiles(ik, j).getPiece().black == turn && board.getTiles(ik, j).getPiece().getPossibleMoves(board).size()>0){
                                    piece = board.getTiles(ik, j).getPiece();
                                    sizz++;
                                }
                            }
                        }
                        //System.out.println("SIZE IS "+sizz);
                        if (piece == null){
                            setOver(true);
                            return;
                        }*/
                    int startRow = board.getTiles(fromX, fromY).getPiece().black ? 6 : 1;
                    int move = board.getTiles(fromX, fromY).getPiece().black ? -1 : 1;
                    if (board.getTiles(fromX, fromY).getPiece() != null && board.getTiles(fromX, fromY).getPiece() instanceof Pawn && board.getTiles(fromX, fromY).getPiece().getY() == startRow){
                        board.setEnPassant(new Coords(fromX, fromY+move));
                        livess = 2;
                    }
                    board.tiles[toX][toY].putPiece(board.tiles[fromX][fromY].piece);
                    if (board.getEnPassant().size() > 0&&board.tiles[fromX][fromY].piece!= null &&board.tiles[fromX][fromY].piece.black == true && board.getEnPassant().get(0).getX() == toX){
                        board.tiles[toX][toY+1].killPiece();
                    }
                    if (board.getEnPassant().size() > 0&&board.tiles[fromX][fromY].piece!= null &&board.tiles[fromX][fromY].piece.black == false && board.getEnPassant().get(0).getX() == toX){
                        board.tiles[toX][toY-1].killPiece();
                    }
                    color = board.tiles[toX][toY].piece.black;
                    board.tiles[fromX][fromY].killPiece();
                    if (board.tiles[toX][toY].piece.black == false){
                        board.getAllPieces().remove(board.tiles[toX][toY].piece);
                        //System.out.println(board.getAllPieces().size());
                    }
                    if(board.tiles[toX][toY].isPromotion()){
                        board.tiles[toX][toY].putPiece(new Queen(toX, toY, color));
                    }
                    
                    break;
        //                moveHistory.put(player, (List<Coords>) moves.get(i));

                }
            }
            countT++;
            
            livess--;
            if (board.getEnPassant().size() > 0 && livess <= 0){
                        System.out.println(" TED JSEM VYMAZAL TENHLE INDEX"+board.getEnPassant().get(0));
                        board.getEnPassant().remove(0);
            }
            System.out.println("Enpassant size "+board.getEnPassant().size());
            if (board.getEnPassant().size() >0){
                System.out.println("Coords of enps x : "+ board.getEnPassant().get(0).getX() + " y: "+board.getEnPassant().get(0).getY());
            }
        
    }
    
    public void whiteOnline(int fromX, int fromY, int toX, int toY){
       if (board.tiles[fromX][fromY].piece.black == true){
            ArrayList<Coords> moves = board.tiles[fromX][fromY].piece.getPossibleMoves(board);
            for (int i =0; i< moves.size();i++){
                if (moves.get(i).getX() == toX && moves.get(i).getY() == toY){
                    if (board.tiles[toX][toY].piece instanceof King){
                        setOver(true);
                    }
                    int sizz = 0;
                        Piece piece = null;
                        for (int ik = 0; ik < 8; ik++){
                            for (int j = 0; j < 8; j++){
                                if(board.getTiles(ik, j).getPiece() != null && board.getTiles(ik, j).getPiece().black == turn && board.getTiles(ik, j).getPiece().getPossibleMoves(board).size()>0){
                                    piece = board.getTiles(ik, j).getPiece();
                                    sizz++;
                                }
                            }
                        }
                        //System.out.println("SIZE IS "+sizz);
                        if (piece == null){
                            setOver(true);
                            return;
                        }
                    int startRow = board.getTiles(fromX, fromY).getPiece().black ? 6 : 1;
                    int move = board.getTiles(fromX, fromY).getPiece().black ? -1 : 1;
                    if (board.getTiles(fromX, fromY).getPiece() != null && board.getTiles(fromX, fromY).getPiece() instanceof Pawn && board.getTiles(fromX, fromY).getPiece().getY() == startRow){
                        board.setEnPassant(new Coords(fromX, fromY+move));
                        livess = 2;
                    }
                    board.tiles[toX][toY].putPiece(board.tiles[fromX][fromY].piece);
                    if (board.getEnPassant().size() > 0&&board.tiles[fromX][fromY].piece!= null &&board.tiles[fromX][fromY].piece.black == true && board.getEnPassant().get(0).getX() == toX){
                        board.tiles[toX][toY+1].killPiece();
                    }
                    if (board.getEnPassant().size() > 0&&board.tiles[fromX][fromY].piece!= null &&board.tiles[fromX][fromY].piece.black == false && board.getEnPassant().get(0).getX() == toX){
                        board.tiles[toX][toY-1].killPiece();
                    }
                    color = board.tiles[toX][toY].piece.black;
                    board.tiles[fromX][fromY].killPiece();
                    if (board.tiles[toX][toY].piece.black == false){
                        board.getAllPieces().remove(board.tiles[toX][toY].piece);
                        //System.out.println(board.getAllPieces().size());
                    }
                    if(board.tiles[toX][toY].isPromotion()){
                        board.tiles[toX][toY].putPiece(new Queen(toX, toY, color));
                    }
                    
                    break;
        //                moveHistory.put(player, (List<Coords>) moves.get(i));

                }
            }
            countT++;
            
            turn ^= true;
            livess--;
            if (board.getEnPassant().size() > 0 && livess <= 0){
                        System.out.println(" TED JSEM VYMAZAL TENHLE INDEX"+board.getEnPassant().get(0));
                        board.getEnPassant().remove(0);
            }
            System.out.println("Enpassant size "+board.getEnPassant().size());
            if (board.getEnPassant().size() >0){
                System.out.println("Coords of enps x : "+ board.getEnPassant().get(0).getX() + " y: "+board.getEnPassant().get(0).getY());
            }

       }
        
    }
    
    
    /**
     *CHecking if BIg castle is possible
     * if is - makes it
     */
    public void bCastle(){
        boolean possible = true;
        if (board.getTiles(4, 7).piece instanceof King && board.getTiles(0, 7).piece instanceof Rook){
            for (int i = 1; i< 4; i++){
                if (board.getTiles(4-i, 7).piece != null){
                    possible = false;
                }
            }
            if (possible && turn == board.getTiles(4, 7).piece.black){
                board.getTiles(2, 7).putPiece(new King(2, 7, true));
                board.getTiles(3, 7).putPiece(new Rook(3, 7, true));
                board.getTiles(0, 7).killPiece();
                board.getTiles(4, 7).killPiece();
                turn ^= true;
            }
        }
        possible = true;
        if (board.getTiles(4, 0).piece instanceof King && board.getTiles(0, 0).piece instanceof Rook){
            for (int i = 1; i< 4; i++){
               if (board.getTiles(4-i, 0).piece != null){
                    possible = false;
                }
            }
            if (possible && board.getTiles(4, 0).piece.black == turn){
                board.getTiles(2, 0).putPiece(new King(2, 0, false));
                board.getTiles(3, 0).putPiece(new Rook(3, 0, false));
                board.getTiles(4, 0).killPiece();
                board.getTiles(0, 0).killPiece();
                turn ^= true;
            }
        }
        
    }
}
