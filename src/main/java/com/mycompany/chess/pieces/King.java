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
public class King extends Piece implements java.io.Serializable {
    //all king possible moves
    int X[] = { 1, 0, -1, 0, 1, -1, -1, 1};
    int Y[] = { 0, 1, 0, -1, 1, 1, -1, -1};
    
    int XC[] = { 2, 0, -2, 0, 2, -2, -2, 2, 2, 1, -2, -1, 2, 1};
    int YC[] = { 0, 2, 0, -2, 2, 2, -2, -2, 1, 2, 1, 2, -1, -2};

    /**
     *
     * @param x
     * @param y
     * @param black
     */
    public King(int x, int y, boolean black) {
        super(x, y, black);
    }

    @Override
    public String toString() {
        return "King";
    }
   
    @Override
    public ArrayList<Coords> getPossibleMoves(Board board) {
        ArrayList<Coords> moves = new ArrayList<>();
        //check for each move if is valid or not
        for (int i = 0; i < 8; i++){
            int xc = x + X[i];
            int yc = y + Y[i];
            
            int xcc = x + XC[i];
            int ycc = y + YC[i];
            
            
            boolean rook = CheckRook(xc, yc, board);
            boolean pawn = CheckPawn(xc, yc, board);
            boolean bishop = BishopCheck(xc,yc,board);
            boolean king = CheckKing(xc, yc, board);
            boolean knight = CheckKnight(xc, yc, board);
   
            
            
            //check if they are on board
            if (board.isOnBoard(xc, yc)&&rook&&pawn && bishop && king && knight&&(board.getTiles(xc, yc).isEmpty()|| board.getTiles(xc, yc).getPiece().black != black)){
                moves.add(new Coords(xc, yc));
            }
        }
        return moves;
    }
    
    /**
     *
     * @param board
     * @return
     */
    @Override
    public boolean check(Board board){
        boolean rook1 = CheckRook(x, y, board);
       // System.out.println("1"+rook1);
        boolean pawn1 = CheckPawn(x, y, board);
       // System.out.println("2"+pawn1);
        boolean bishop1 = BishopCheck(x,y,board);
       // System.out.println("3"+bishop1);
        boolean king1 = CheckKing(x, y, board);
       // System.out.println("4"+king1);
        boolean knight1 = CheckKnight(x, y, board);
        //System.out.println("5"+knight1);
        if(!rook1 || !pawn1 || !bishop1 || !king1 || !knight1){
                if (black == false){
                    
                    board.setKngw(black);
                    return false;
                }else{
                    
                    board.setKngb(black);
                    return false;
                }
            
        }else{
            board.setKngw(!black);
            board.setKngb(!black);
            return true;
    
        }
    }
    /*
    Check if king is in bishop check
    */
    boolean BishopCheck(int x, int y, Board board){
        int x1 = x+1;
        int y1 = y+1;
        board.getTiles(this.x, this.y).killPiece();
        while (board.isOnBoard(x1, y1)){
            if (board.getTiles(x1, y1).getPiece() != null && board.getTiles(x1, y1).getPiece().black != black && (board.getTiles(x1, y1).getPiece() instanceof Bishop || board.getTiles(x1, y1).getPiece() instanceof Queen)){
                board.getTiles(this.x, this.y).putPiece(this);
                return false;
                
            } if(board.getTiles(x1, y1).getPiece() != null && board.getTiles(x1, y1).getPiece().black == black){
                break;
            }
            x1++;
            y1++;
        }
        
        x1 = x+1;
        y1 = y-1;
        while (board.isOnBoard(x1, y1)){
            if (board.getTiles(x1, y1).getPiece() != null && board.getTiles(x1, y1).getPiece().black != black && (board.getTiles(x1, y1).getPiece() instanceof Bishop || board.getTiles(x1, y1).getPiece() instanceof Queen)){
                board.getTiles(this.x, this.y).putPiece(this);
                return false;
            } if(board.getTiles(x1, y1).getPiece() != null && board.getTiles(x1, y1).getPiece().black == black){
                break;
            }
            x1++;
            y1--;
        }
        
        x1 = x-1;
        y1 = y-1;
        while (board.isOnBoard(x1, y1)){
            if (board.getTiles(x1, y1).getPiece() != null && board.getTiles(x1, y1).getPiece().black != black && (board.getTiles(x1, y1).getPiece() instanceof Bishop || board.getTiles(x1, y1).getPiece() instanceof Queen)){
              //  System.out.println("Bishop unvaliable");
              board.getTiles(this.x, this.y).putPiece(this);  
              return false;
            } if(board.getTiles(x1, y1).getPiece() != null && board.getTiles(x1, y1).getPiece().black == black){
                break;
            }
            x1--;
            y1--;
        }
        
        x1 = x-1;
        y1 = y+1;
        while (board.isOnBoard(x1, y1)){
            if (board.getTiles(x1, y1).getPiece() != null && board.getTiles(x1, y1).getPiece().black != black && (board.getTiles(x1, y1).getPiece() instanceof Bishop || board.getTiles(x1, y1).getPiece() instanceof Queen)){
               // System.out.println("Bishop unvaliable");
               board.getTiles(this.x, this.y).putPiece(this); 
               return false;
            } if(board.getTiles(x1, y1).getPiece() != null && board.getTiles(x1, y1).getPiece().black == black){
                break;
            }
            x1--;
            y1++;
        }
        board.getTiles(this.x, this.y).putPiece(this);
        return true;
    }
    
    /*
    Check if king is in king check
    */
    boolean CheckKing(int x, int y, Board board){
        if (board.isOnBoard(x, y)){
            for (int i = 0; i < 8; i++){
                int xc = x + X[i];
                int yc = y + Y[i];

                //check if they are on board
                if (board.isOnBoard(xc, yc)){
                    if (board.getTiles(xc, yc).getPiece() != null && board.getTiles(xc, yc).getPiece().black != black  && (board.getTiles(xc, yc).getPiece() instanceof King)){
                       // System.out.println("King unvaliable");
                       
                        return false;
                    }
                }
            }
        }
     
        return true;
    }
    
    /*
    Check if king is in king check
    */
    boolean CheckKnight(int x, int y, Board board){
        int X1[] = { 2, 1, -1, -2, -2, -1, 1, 2 };
        int Y1[] = { 1, 2, 2, 1, -1, -2, -2, -1 };
        if(board.isOnBoard(x, y)){
            for (int i = 0; i < 8; i++){
                int xc = x + X1[i];
                int yc = y + Y1[i];

                //check if they are on board
                if (board.isOnBoard(xc, yc) && board.getTiles(xc, yc).getPiece() != null && board.getTiles(xc, yc).getPiece().black != black &&board.getTiles(xc, yc).getPiece() instanceof Knight){
                    return false;
                }
            }
        }
        return true;
    }
    
    /*
    Check if king is in pawn check
    */
    boolean CheckPawn(int x, int y, Board board){
        int move = black ? -1 : 1;
        int ys = 1;
        int s=x+ys;
        int s2 = y+move;
        if (board.isOnBoard(x+ys, y+move)&&board.getTiles(x+ys, y+move).getPiece() != null && board.getTiles(x+ys, y+move).getPiece().black != black && board.getTiles(x+ys, y+move).getPiece() instanceof Pawn){
           // System.out.println("x  is" + s+ " y is" + s2);
           // System.out.println("FIRST IS "+board.getTiles(x+ys, y+move).getPiece());
            return false;
        }
        ys =-1;
        s = x+ys;
        s2 = y+move;
        if (board.isOnBoard(x+ys, y+move)&&board.getTiles(x+ys, y+move).getPiece() != null && board.getTiles(x+ys, y+move).getPiece().black != black && board.getTiles(x+ys, y+move).getPiece() instanceof Pawn){
           // System.out.println("x  is" + s+ " y is" + s2);
           // System.out.println("SECOND IS "+board.getTiles(x+ys, y+move).getPiece());
            return false;
        }
        return true;
    }
    
    
    /*
    Check if king is in rook check
    */
    boolean CheckRook(int x, int y, Board board){
        board.getTiles(this.x, this.y).killPiece();
        int i = y + 1;
            while (i > -1 && i <8){
                if (board.isOnBoard(x, i)&&board.getTiles(x, i).getPiece() != null && board.getTiles(x, i).getPiece().black != black){
                    if (board.getTiles(x, i).getPiece() instanceof Rook ||board.getTiles(x, i).getPiece() instanceof Queen){
                        board.getTiles(this.x, this.y).putPiece(this);
                        return false;
                    }
                    break;
                } if (board.isOnBoard(x, i)&&board.getTiles(x, i).getPiece() != null && board.getTiles(x, i).getPiece().black == black){
           
                    break;
                }
                i++;
            }
        i = y - 1;
            while (i > -1 && i <8){
                if (board.isOnBoard(x, i)&&board.getTiles(x, i).getPiece() != null && board.getTiles(x, i).getPiece().black != black){
                    if (board.getTiles(x, i).getPiece() instanceof Rook ||board.getTiles(x, i).getPiece() instanceof Queen){
                        board.getTiles(this.x, this.y).putPiece(this);
                        return false;
                    }
                    break;
                } if (board.isOnBoard(x, i)&&board.getTiles(x, i).getPiece() != null && board.getTiles(x, i).getPiece().black == black){
                 
                    break;
                }
                i--;
            }
        i = x +1;
            while (i > -1 && i <8){
                if (board.isOnBoard(i, y)&&board.getTiles(i, y).getPiece() != null && board.getTiles(i, y).getPiece().black != black){
                    if (board.getTiles(i, y).getPiece() instanceof Rook ||board.getTiles(i, y).getPiece() instanceof Queen){
                        board.getTiles(this.x, this.y).putPiece(this);
                        return false;
                    }
                    break;
                } if (board.isOnBoard(i, y)&&board.getTiles(i, y).getPiece() != null && board.getTiles(i, y).getPiece().black == black){
                    break;
                }
                i++;
            }
        i = x -1;
            while (i > -1 && i <8){
                if (board.isOnBoard(i, y)&&board.getTiles(i, y).getPiece() != null && board.getTiles(i, y).getPiece().black != black){
                    if (board.getTiles(i, y).getPiece() instanceof Rook ||board.getTiles(i, y).getPiece() instanceof Queen){
                        board.getTiles(this.x, this.y).putPiece(this);
                        return false;
                    }
                    break;
                } if (board.isOnBoard(i, y)&&board.getTiles(i, y).getPiece() != null && board.getTiles(i, y).getPiece().black == black){
                    break;
                }
                i--;
            }
        board.getTiles(this.x, this.y).putPiece(this);
        return true;
    }
    
}
