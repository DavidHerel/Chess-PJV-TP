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
public class Knight extends Piece implements java.io.Serializable {
    //all knight possible moves
    int X[] = { 2, 1, -1, -2, -2, -1, 1, 2 };
    int Y[] = { 1, 2, 2, 1, -1, -2, -2, -1 };
    int size;
 
        Piece king;

    public Knight(int x, int y, boolean black) {
        super(x, y, black);
    }

    @Override
    public String toString() {
        return "Knight";
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
        Piece puvod;
        ArrayList<Coords> moves = new ArrayList<>();
        //check for each move if is valid or not
        for (int i = 0; i < 8; i++){
            int xc = x + X[i];
            int yc = y + Y[i];
           
            //check if they are on board
            if (board.isOnBoard(xc, yc) && board.getTiles(xc, yc).isEmpty()){
                king.check(board);
                if (black == board.isKngw() || black == board.isKngb()){
                    puvod = null;
                        if (board.getTiles(xc, yc).getPiece() != null){
                            puvod = board.getTiles(xc, yc).getPiece();
                        }
                        board.getTiles(xc, yc).putPiece(new Knight(xc, yc, black));
                        board.getTiles(this.x, this.y).killPiece();
                        if (king != null&&king.check(board)){
                                moves.add(new Coords(xc, yc));

                            }
                        board.getTiles(x, y).putPiece(board.getTiles(xc, yc).getPiece());
                        board.getTiles(xc, yc).killPiece();
                        if(puvod != null){
                            board.getTiles(xc, yc).putPiece(puvod);
                        }
                } else{
                    puvod = null;
                        if (board.getTiles(xc, yc).getPiece() != null){
                            puvod = board.getTiles(xc, yc).getPiece();
                        }
                        board.getTiles(xc, yc).putPiece(new Knight(xc, yc, black));
                        board.getTiles(this.x, this.y).killPiece();
                        if (king != null&&king.check(board)){
                                moves.add(new Coords(xc, yc));

                            }
                        board.getTiles(x, y).putPiece(board.getTiles(xc, yc).getPiece());
                        board.getTiles(xc, yc).killPiece();
                        if(puvod != null){
                            board.getTiles(xc, yc).putPiece(puvod);
                        }
                }
                
            } else if (board.isOnBoard(xc, yc) && board.getTiles(xc, yc).getPiece().black != black){
                king.check(board);
                if (black == board.isKngw() || black == board.isKngb()){
                    puvod = null;
                        if (board.getTiles(xc, yc).getPiece() != null){
                            puvod = board.getTiles(xc, yc).getPiece();
                        }
                        board.getTiles(xc, yc).putPiece(new Knight(xc, yc, black));
                        board.getTiles(this.x, this.y).killPiece();
                        if (king != null&&king.check(board)){
                                moves.add(new Coords(xc, yc));

                            }
                        board.getTiles(x, y).putPiece(board.getTiles(xc, yc).getPiece());
                        board.getTiles(xc, yc).killPiece();
                        if(puvod != null){
                            board.getTiles(xc, yc).putPiece(puvod);
                        }
                } else{
                    puvod = null;
                        if (board.getTiles(xc, yc).getPiece() != null){
                            puvod = board.getTiles(xc, yc).getPiece();
                        }
                        board.getTiles(xc, yc).putPiece(new Knight(xc, yc, black));
                        board.getTiles(this.x, this.y).killPiece();
                        if (king != null&&king.check(board)){
                                moves.add(new Coords(xc, yc));

                            }
                        board.getTiles(x, y).putPiece(board.getTiles(xc, yc).getPiece());
                        board.getTiles(xc, yc).killPiece();
                        if(puvod != null){
                            board.getTiles(xc, yc).putPiece(puvod);
                        }
                }
                
            }
        }
        return moves;
    }
    
}
