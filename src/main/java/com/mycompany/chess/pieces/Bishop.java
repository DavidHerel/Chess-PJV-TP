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
public class Bishop extends Piece implements java.io.Serializable {
        int size;
        Piece king;
    public Bishop(int x, int y, boolean black) {
        super(x, y, black);
    }

    @Override
    public String toString() {
        return "Bishop";
    }

    

    @Override
    public ArrayList<Coords> getPossibleMoves(Board board) {
        for (int i = 0; i< 8; i++){
            for (int j = 0; j < 8; j++){
                if (board.getTiles(i, j).getPiece()!= null && board.getTiles(i, j).getPiece().black == black && board.getTiles(i, j).getPiece() instanceof King){
                    size = board.getTiles(i, j).getPiece().getPossibleMoves(board).size();
                    king = board.getTiles(i, j).getPiece();
                }
            }
        }
        ArrayList<Coords> moves = new ArrayList<>();
        int rY = 1;
        int rX = 1;
        int x1;
        int y1;
        for (int i = 0; i < 4; i++){
            if (i < 2){
                rX = -1 *rX;
                rY = -1 * rY;
                x1 = x + rX;
                y1 = y + rY;
       
                addMoves(board, moves, x1, y1, rX, rY);
            } else{
                rX = -1 * rX;
                rY = rX * -1;
                x1 = x + rX;
                y1 = y + rY;
              
                addMoves(board, moves, x1, y1, rX, rY);
            }        
        }
        return moves;
    }
    
   public void addMoves(Board board, ArrayList<Coords> moves, int x1, int y1, int rX, int rY){
        Piece puvod;
        while (board.isOnBoard(x1, y1) && (board.getTiles(x1, y1).isEmpty() || board.getTiles(x1, y1).getPiece().black != black)){
            king.check(board);
            if (board.getTiles(x1, y1).getPiece() != null){
                if (black == board.isKngw() || black == board.isKngb()){
                        puvod = null;
                        if (board.getTiles(x1, y1).getPiece() != null){
                            puvod = board.getTiles(x1, y1).getPiece();
                        }
                        board.getTiles(x1, y1).putPiece(new Bishop(x1, y1, black));
                        board.getTiles(this.x, this.y).killPiece();
                        if (king != null&&king.check(board)){
                                moves.add(new Coords(x1, y1));
                                   

                            }
                        board.getTiles(x, y).putPiece(board.getTiles(x1, y1).getPiece());
                        board.getTiles(x1, y1).killPiece();
                        if(puvod != null){
                            board.getTiles(x1, y1).putPiece(puvod);
                        }
                         break;
                    } else{
                        puvod = null;
                        if (board.getTiles(x1, y1).getPiece() != null){
                            puvod = board.getTiles(x1, y1).getPiece();
                        }
                        board.getTiles(x1, y1).putPiece(new Bishop(x1, y1, black));
                        board.getTiles(this.x, this.y).killPiece();
                        if (king != null&&king.check(board)){
                                moves.add(new Coords(x1, y1));
                                   

                        }
                        board.getTiles(x, y).putPiece(board.getTiles(x1, y1).getPiece());
                        board.getTiles(x1, y1).killPiece();
                        if(puvod != null){
                            board.getTiles(x1, y1).putPiece(puvod);
                        }
                         break;
                       
                    }
            } else{
                if (black == board.isKngw() || black == board.isKngb()){
                    puvod = null;
                        if (board.getTiles(x1, y1).getPiece() != null){
                            puvod = board.getTiles(x1, y1).getPiece();
                        }
                        board.getTiles(x1, y1).putPiece(new Bishop(x1, y1, black));
                        board.getTiles(this.x, this.y).killPiece();
                        if (king != null&&king.check(board)){
                                moves.add(new Coords(x1, y1));

                            }
                        board.getTiles(x, y).putPiece(board.getTiles(x1, y1).getPiece());
                        board.getTiles(x1, y1).killPiece();
                        if(puvod != null){
                            board.getTiles(x1, y1).putPiece(puvod);
                        }
                    } else{
                        puvod = null;
                        if (board.getTiles(x1, y1).getPiece() != null){
                            puvod = board.getTiles(x1, y1).getPiece();
                        }
                        board.getTiles(x1, y1).putPiece(new Bishop(x1, y1, black));
                        board.getTiles(this.x, this.y).killPiece();
                        if (king != null&&king.check(board)){
                                moves.add(new Coords(x1, y1));

                            }
                        board.getTiles(x, y).putPiece(board.getTiles(x1, y1).getPiece());
                        board.getTiles(x1, y1).killPiece();
                        if(puvod != null){
                            board.getTiles(x1, y1).putPiece(puvod);
                        }
                       
                    }
                x1 += rX;
                y1 += rY;
            }
        }
    }
    
}
