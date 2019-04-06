/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chess.main;

import com.mycompany.chess.board.Board;
import com.mycompany.chess.game.Game;
import com.mycompany.chess.gui.Gui;

/**
 *
 * @author fuji
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Board board = new Board();
        Game game = new Game(board);
        Gui okno = new Gui(game);
        
    }
    
}
