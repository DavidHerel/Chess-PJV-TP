/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chess.pieces;

import com.mycompany.chess.board.Board;
import com.mycompany.chess.board.Coords;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author fuji
 */
public class Pawn extends Piece implements java.io.Serializable {
    int size;
    int move;
        Piece king;
        

    public Pawn(int x, int y, boolean black) {
        super(x, y, black);
    }

    @Override
    public String toString() {
        return "Pawn";
    }

 
    private void addMoveIfPossible(Board board, int x, int y, ArrayList<Coords> moves) {
        Piece puvod;
       king.check(board);
        if ((board.isOnBoard(x, y)) && (board.getTiles(x, y).getPiece() == null)){
         //   System.out.println(king.x);
        //    System.out.println(king.y);
            king.check(board);
            
            if (black == board.isKngw() || black == board.isKngb()){
                puvod = null;
                if (board.getTiles(x, y).getPiece() != null){
                    puvod = board.getTiles(x, y).getPiece();
                }
            board.getTiles(x, y).putPiece(new Pawn(x,y,black));
                board.getTiles(this.x, this.y).killPiece();
                if (king != null && king.check(board)){
                        moves.add(new Coords(x, y));

                }
                board.getTiles(this.x, this.y).putPiece(board.getTiles(x, y).getPiece());
                board.getTiles(x, y).killPiece();
                if (puvod != null){
                    board.getTiles(x, y).putPiece(puvod);
                }
            } else{
                puvod = null;
                if (board.getTiles(x, y).getPiece() != null){
                    puvod = board.getTiles(x, y).getPiece();
                }
            board.getTiles(x, y).putPiece(new Pawn(x,y,black));
                board.getTiles(this.x, this.y).killPiece();
                if (king != null && king.check(board)){
                        moves.add(new Coords(x, y));

                }
                board.getTiles(this.x, this.y).putPiece(board.getTiles(x, y).getPiece());
                board.getTiles(x, y).killPiece();
                if (puvod != null){
                    board.getTiles(x, y).putPiece(puvod);
                }
            }
        }
       king.check(board);
        if((board.isOnBoard(x+1, y)) && board.getTiles(x+1, y).getPiece() != null && board.getTiles(x+1, y).getPiece().black != black){
          if (black == board.isKngw() || black == board.isKngb()){
              puvod = null;
                if (board.getTiles(x+1, y).getPiece() != null){
                    puvod = board.getTiles(x+1, y).getPiece();
                }
            board.getTiles(x+1, y).putPiece(new Pawn(x+1,y,black));
                board.getTiles(this.x, this.y).killPiece();
                if (king != null && king.check(board)){
                        moves.add(new Coords(x+1, y));

                }
                board.getTiles(this.x, this.y).putPiece(board.getTiles(x+1, y).getPiece());
                board.getTiles(x+1, y).killPiece();
                if (puvod != null){
                    board.getTiles(x+1, y).putPiece(puvod);
                }
            } else{
                puvod = null;
                if (board.getTiles(x+1, y).getPiece() != null){
                    puvod = board.getTiles(x+1, y).getPiece();
                }
            board.getTiles(x+1, y).putPiece(new Pawn(x+1,y,black));
                board.getTiles(this.x, this.y).killPiece();
                if (king != null && king.check(board)){
                        moves.add(new Coords(x+1, y));

                }
                board.getTiles(this.x, this.y).putPiece(board.getTiles(x+1, y).getPiece());
                board.getTiles(x+1, y).killPiece();
                if (puvod != null){
                    board.getTiles(x+1, y).putPiece(puvod);
                }
            }
        } 
       king.check(board);
        if((board.isOnBoard(x-1, y)) && board.getTiles(x-1, y).getPiece() != null && board.getTiles(x-1, y).getPiece().black != black){
              if (black == board.isKngw() || black == board.isKngb()){
                  puvod = null;
                if (board.getTiles(x-1, y).getPiece() != null){
                    puvod = board.getTiles(x-1, y).getPiece();
                }
            board.getTiles(x-1, y).putPiece(new Pawn(x-1,y,black));
                board.getTiles(this.x, this.y).killPiece();
                if (king != null && king.check(board)){
                        moves.add(new Coords(x-1, y));

                }
                board.getTiles(this.x, this.y).putPiece(board.getTiles(x-1, y).getPiece());
                board.getTiles(x-1, y).killPiece();
                if (puvod != null){
                    board.getTiles(x-1, y).putPiece(puvod);
                }
            } else{
                puvod = null;
                if (board.getTiles(x-1, y).getPiece() != null){
                    puvod = board.getTiles(x-1, y).getPiece();
                }
            board.getTiles(x-1, y).putPiece(new Pawn(x-1,y,black));
                board.getTiles(this.x, this.y).killPiece();
                if (king != null && king.check(board)){
                        moves.add(new Coords(x-1, y));

                }
                board.getTiles(this.x, this.y).putPiece(board.getTiles(x-1, y).getPiece());
                board.getTiles(x-1, y).killPiece();
                if (puvod != null){
                    board.getTiles(x-1, y).putPiece(puvod);
                }
            }
        }
      king.check(board);
      
      
        if ((board.getEnPassant().size())>0&&board.getEnPassant().get(0).getX() == x+1&&board.getEnPassant().get(0).getY() == y && board.getTiles(x+1 > 7 ? 7 : x+1, y-move).getPiece() != null&&board.getTiles(x+1 > 7 ? 7 : x+1, y-move).getPiece().black != black && (board.getTiles(x+1 > 7 ? 7 : x+1, y-move).getPiece() instanceof Pawn) ){
            if (black == board.isKngw() || black == board.isKngb()){
                puvod = null;
                if (board.getTiles(x+1, y).getPiece() != null){
                    puvod = board.getTiles(x+1, y).getPiece();
                }
            board.getTiles(x+1, y).putPiece(new Pawn(x+1,y,black));
                board.getTiles(this.x, this.y).killPiece();
                if (king != null && king.check(board)){
                        moves.add(new Coords(x+1, y));

                }
                board.getTiles(this.x, this.y).putPiece(board.getTiles(x+1, y).getPiece());
                board.getTiles(x+1, y).killPiece();
                if (puvod != null){
                    board.getTiles(x+1, y).putPiece(puvod);
                }
            } else{
                puvod = null;
                if (board.getTiles(x+1, y).getPiece() != null){
                    puvod = board.getTiles(x+1, y).getPiece();
                }
            board.getTiles(x+1, y).putPiece(new Pawn(x+1,y,black));
                board.getTiles(this.x, this.y).killPiece();
                if (king != null && king.check(board)){
                        moves.add(new Coords(x+1, y));

                }
                board.getTiles(this.x, this.y).putPiece(board.getTiles(x+1, y).getPiece());
                board.getTiles(x+1, y).killPiece();
                if (puvod != null){
                    board.getTiles(x+1, y).putPiece(puvod);
                }
            }
        }
king.check(board);
      //  System.out.println(move);
        
        
        if (board.getEnPassant().size()>0){
            //System.out.println("Board size" + (board.getEnPassant().size()>0));
           // System.out.println("druha podminka"+(board.getEnPassant().get(0).getX() == x-1)); 
       // System.out.println("Treti podminka"+(board.getEnPassant().get(0).getY() == y));
        }
        
         
        if (board.getTiles(x-1 < 0 ? 0 : x-1, y-move).getPiece() != null){
           // System.out.println("Podminka pro black "+(board.getTiles(x-1 < 0 ? 0 : x-1, y-move).getPiece().black == black));
        }
        //System.out.println("Podminka pro black "+(board.getTiles(x-1, y+move).getPiece().black != black));
        //System.out.println("Podminka pro pawn "+ (board.getTiles(x-1 < 0 ? 0 : x-1, y-move).getPiece() instanceof Pawn));
        if ((board.getEnPassant().size())>0 &&board.getEnPassant().get(0).getX() == x-1 && board.getEnPassant().get(0).getY() == y&& board.getTiles(x-1 < 0 ? 0 : x-1, y-move).getPiece() != null&&board.getTiles(x-1 < 0 ? 0 : x-1, y-move).getPiece().black != black && (board.getTiles(x-1 < 0 ? 0 : x-1, y-move).getPiece() instanceof Pawn) ){
             if (black == board.isKngw() || black == board.isKngb()){
                 puvod = null;
                if (board.getTiles(x-1, y).getPiece() != null){
                    puvod = board.getTiles(x-1, y).getPiece();
                }
            board.getTiles(x-1, y).putPiece(new Pawn(x-1,y,black));
                board.getTiles(this.x, this.y).killPiece();
                if (king != null && king.check(board)){
                        moves.add(new Coords(x-1, y));

                }
                board.getTiles(this.x, this.y).putPiece(board.getTiles(x-1, y).getPiece());
                board.getTiles(x-1, y).killPiece();
                if (puvod != null){
                    board.getTiles(x-1, y).putPiece(puvod);
                }
            } else{
                puvod = null;
                if (board.getTiles(x-1, y).getPiece() != null){
                    puvod = board.getTiles(x-1, y).getPiece();
                }
            board.getTiles(x-1, y).putPiece(new Pawn(x-1,y,black));
                board.getTiles(this.x, this.y).killPiece();
                if (king != null && king.check(board)){
                        moves.add(new Coords(x-1, y));

                }
                board.getTiles(this.x, this.y).putPiece(board.getTiles(x-1, y).getPiece());
                board.getTiles(x-1, y).killPiece();
                if (puvod != null){
                    board.getTiles(x-1, y).putPiece(puvod);
                }
            }
        }
    }

    @Override
    public ArrayList<Coords> getPossibleMoves(Board board) {
      Piece puvod;
        for (int i = 0; i< 8; i++){
            for (int j = 0; j < 8; j++){
                if (board.getTiles(i, j).getPiece()!= null && board.getTiles(i, j).getPiece().black == black && board.getTiles(i, j).getPiece() instanceof King){
                    size = board.getTiles(i, j).getPiece().getPossibleMoves(board).size();
                    king = board.getTiles(i, j).getPiece();
                }
            }
        }
        ArrayList<Coords> moves = new ArrayList<>();
        int startRow = black ? 6 : 1;
        move = black ? -1 : 1;
        if (y != startRow){
            addMoveIfPossible(board, x, y + move, moves);
        } else{
            addMoveIfPossible(board, x, y + move, moves);
            if ((board.isOnBoard(x, y + 2*move)) && (board.getTiles(x, y + 2*move).getPiece() == null) && (board.getTiles(x, y + move).getPiece() == null)){
          king.check(board);
                if (black == board.isKngw() || black == board.isKngb()){
                    puvod = null;
                if (board.getTiles(x, y+2*move).getPiece() != null){
                    puvod = board.getTiles(x, y+2*move).getPiece();
                }
                    board.getTiles(x, y+2*move).putPiece(new Pawn(x,y+2*move,black));
                    board.getTiles(this.x, this.y).killPiece();
                    if (king != null && king.check(board)){
                            moves.add(new Coords(x, 2*move +y));

                    }
                    board.getTiles(this.x, this.y).putPiece(board.getTiles(x, 2*move+y).getPiece());
                    board.getTiles(x, y+2*move).killPiece();
                    if (puvod != null){
                    board.getTiles(x, y+2*move).putPiece(puvod);
                    }
                        if (board.getTiles(x, y + move).getPiece() == null){
                        board.setEnPassant(new Coords(x, y + move));
                        if (board.getEnPassant().get(0) instanceof Coords && board.getEnPassant().size() > 1){
                            board.getEnPassant().remove(0);
                        }
                    }
               
                      

                    
                } else{
                    puvod = null;
                if (board.getTiles(x, y+2*move).getPiece() != null){
                    puvod = board.getTiles(x, y+2*move).getPiece();
                }
                    board.getTiles(x, y+2*move).putPiece(new Pawn(x,y+2*move,black));
                    board.getTiles(this.x, this.y).killPiece();
                    if (king != null && king.check(board)){
                            moves.add(new Coords(x, 2*move +y));

                    }
                    board.getTiles(this.x, this.y).putPiece(board.getTiles(x, 2*move+y).getPiece());
                    board.getTiles(x, y+2*move).killPiece();
                    if (puvod != null){
                    board.getTiles(x, y+2*move).putPiece(puvod);
                    }/*
                    if (board.getTiles(x, y + move).getPiece() == null){
                        board.setEnPassant(new Coords(x, y + move));
                        
                        if (board.getEnPassant().get(0) instanceof Coords && board.getEnPassant().size() > 1){
                            board.getEnPassant().remove(0);
                        }
                    }*/
                }
                    
                }
            
        }
        return moves;
    } 
}

