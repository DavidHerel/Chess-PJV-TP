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
public class Rook extends Piece implements java.io.Serializable {
    int size;
        Piece king;

    public Rook(int x, int y, boolean black) {
        super(x, y, black);
    }
    
    
    @Override
    public ArrayList<Coords> getPossibleMoves(Board board) {
        
        for (int i = 0; i< 8; i++){
            for (int j = 0; j < 8; j++){
                if (board.getTiles(i, j).getPiece()!= null && board.getTiles(i, j).getPiece().black == black && board.getTiles(i, j).getPiece() instanceof King){
                    size = board.getTiles(i, j).getPiece().getPossibleMoves(board).size();
                    king = board.getTiles(i, j).getPiece();
                   // System.out.println("Rook color"+black);
                }
            }
        }
        ArrayList<Coords> moves = new ArrayList<>();
        int r=-1;
        int sign = -1;
        for (int j = 0; j < 4; j++){
            if (j < 2){
                r = -1 *r;
                sign = sign *-1;
                addToList(board, moves, r, true, sign);
            } else{
                r = -1 *r;
                sign = sign * -1;
                addToList(board, moves, r, false, sign);
            }        
        }
        return moves;
    }
    
    
    public void addToList(Board board, ArrayList<Coords> moves, int i, boolean side, int sign){
        Piece puvod;
        if (side){
            i = y + i;
            while (i > -1 && i <8){
                king.check(board);
                if (board.getTiles(x, i).isEmpty() && board.isOnBoard(x, i)){
                   if (black == board.isKngw() || black == board.isKngb()){
                       puvod = null;
                        if (board.getTiles(x, i).getPiece() != null){
                            puvod = board.getTiles(x, i).getPiece();
                        }
                        board.getTiles(x, i).putPiece(new Rook(x, i, black));
                        board.getTiles(this.x, this.y).killPiece();
                        if (king != null&&king.check(board)){
                         //   System.out.println("Dostal se sem");
                                moves.add(new Coords(x, i));

                        }
                        board.getTiles(x, y).putPiece(board.getTiles(x, i).getPiece());
                        board.getTiles(x, i).killPiece();
                        if (puvod != null){
                            board.getTiles(x,i).putPiece(puvod);
                        }
                        
                    } else{
                       //System.out.println("i sem");
                        puvod = null;
                        if (board.getTiles(x, i).getPiece() != null){
                            puvod = board.getTiles(x, i).getPiece();
                        }
                        board.getTiles(x, i).putPiece(new Rook(x, i, black));
                        board.getTiles(this.x, this.y).killPiece();
                        if (king != null&&king.check(board)){
                         //   System.out.println("Dostal se sem");
                                moves.add(new Coords(x, i));

                        }
                        board.getTiles(x, y).putPiece(board.getTiles(x, i).getPiece());
                        board.getTiles(x, i).killPiece();
                        if (puvod != null){
                            board.getTiles(x,i).putPiece(puvod);
                        }
                    }
                } else if (!board.getTiles(x, i).isEmpty() && board.getTiles(x, i).getPiece().black != black && board.isOnBoard(x, i)){
                    if (black == board.isKngw() || black == board.isKngb()){
                        
                        puvod = null;
                        if (board.getTiles(x, i).getPiece() != null){
                            puvod = board.getTiles(x, i).getPiece();
                        }
                        board.getTiles(x, i).putPiece(new Rook(x, i, black));
                        board.getTiles(this.x, this.y).killPiece();
                        if (king != null&&king.check(board)){
                                moves.add(new Coords(x, i));
                                   

                            }
                        board.getTiles(x, y).putPiece(board.getTiles(x, i).getPiece());
                        board.getTiles(x, i).killPiece();
                        if (puvod != null){
                            board.getTiles(x,i).putPiece(puvod);
                        }
                         break;
                    
                    } else{
                        
                        puvod = null;
                        if (board.getTiles(x, i).getPiece() != null){
                            puvod = board.getTiles(x, i).getPiece();
                        }
                        board.getTiles(x, i).putPiece(new Rook(x, i, black));
                        board.getTiles(this.x, this.y).killPiece();
                        if (king != null&&king.check(board)){
                                moves.add(new Coords(x, i));
                                   

                            }
                        board.getTiles(x, y).putPiece(board.getTiles(x, i).getPiece());
                        board.getTiles(x, i).killPiece();
                        if (puvod != null){
                            board.getTiles(x,i).putPiece(puvod);
                        }
                         break;
                    }
                } else if(!board.getTiles(x, i).isEmpty() && board.isOnBoard(x, i) && board.getTiles(x, i).getPiece().black == black){
                    break;
                }
                i+= sign;
            }
        } else{
            i = x + i;
            while (i > -1 && i <8){
                king.check(board);
                if (board.getTiles(i, y).isEmpty() && board.isOnBoard(i, y)){
  
                   
                        if (black == board.isKngw() || black == board.isKngb()){
                            puvod = null;
                        if (board.getTiles(i, y).getPiece() != null){
                            puvod = board.getTiles(i, y).getPiece();
                        }
                        board.getTiles(i, y).putPiece(new Rook(i,y,black));
                        board.getTiles(this.x, this.y).killPiece();
                        if (king != null&&king.check(board)){
                                moves.add(new Coords(i, y));

                            }
                        board.getTiles(x, y).putPiece(board.getTiles(i, y).getPiece());
                        board.getTiles(i, y).killPiece();
                        if (puvod != null){
                            board.getTiles(i,y).putPiece(puvod);
                        }
                        
                    } else{
                        puvod = null;
                        if (board.getTiles(i, y).getPiece() != null){
                            puvod = board.getTiles(i, y).getPiece();
                        }
                        board.getTiles(i, y).putPiece(new Rook(i,y,black));
                        board.getTiles(this.x, this.y).killPiece();
                        if (king != null&&king.check(board)){
                                moves.add(new Coords(i, y));

                            }
                        board.getTiles(x, y).putPiece(board.getTiles(i, y).getPiece());
                        board.getTiles(i, y).killPiece();
                        if (puvod != null){
                            board.getTiles(i,y).putPiece(puvod);
                        }
                       
                    }
                    
                } else if ( !board.getTiles(i, y).isEmpty() && board.getTiles(i, y).getPiece().black != black && board.isOnBoard(i, y)){
                    if (black == board.isKngw() || black == board.isKngb()){
                            puvod = null;
                        if (board.getTiles(i, y).getPiece() != null){
                            puvod = board.getTiles(i, y).getPiece();
                        }
                        board.getTiles(i, y).putPiece(new Rook(i,y,black));
                        board.getTiles(this.x, this.y).killPiece();
                        if (king != null&&king.check(board)){
                                moves.add(new Coords(i, y));
                                  

                            }
                        board.getTiles(x, y).putPiece(board.getTiles(i, y).getPiece());
                        board.getTiles(i, y).killPiece();
                        if (puvod != null){
                            board.getTiles(i,y).putPiece(puvod);
                        }
                          break;
                      
                    } else{
                        puvod = null;
                        if (board.getTiles(i, y).getPiece() != null){
                            puvod = board.getTiles(i, y).getPiece();
                        }
                        board.getTiles(i, y).putPiece(new Rook(i,y,black));
                        board.getTiles(this.x, this.y).killPiece();
                        if (king != null&&king.check(board)){
                                moves.add(new Coords(i, y));
                                  

                            }
                        board.getTiles(x, y).putPiece(board.getTiles(i, y).getPiece());
                        board.getTiles(i, y).killPiece();
                        if (puvod != null){
                            board.getTiles(i,y).putPiece(puvod);
                        }
                          break;
                    }
                    
                } else if(!board.getTiles(i, y).isEmpty() && board.isOnBoard(i, y) && board.getTiles(i, y).getPiece().black == black){
                    break;
                }
                i += sign;
            }
        }
    }
    
    @Override
    public String toString() {
        return "Rook";
    }


    
}
