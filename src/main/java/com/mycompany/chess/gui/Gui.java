/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chess.gui;

import com.mycompany.chess.board.Board;
import com.mycompany.chess.board.Coords;
import com.mycompany.chess.board.Tile;
import com.mycompany.chess.game.Game;
import com.mycompany.chess.pieces.King;
import com.mycompany.chess.pieces.Piece;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.PrintWriter;
import java.util.Arrays;


/**
 * Whole GUI
 * @author fuji
 */
public class Gui extends JFrame{
    private JFrame gameFrame;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private BufferedReader dataRec;
    private PrintWriter dataSend;
    static final Logger logger = Logger.getLogger(Gui.class.getName());
    private BoardGui boardGui;
    private Board chessBoard;
    private Tile fromTile;
    boolean isOnline = false;
    private Tile toTile;
    private Piece movedPiece;
    List<TileGui> boardTiles;
      int counter = 0;
    Clock clockW;
    Clock clockB;
    
    Game game;
    String[] choice = {"Play again","Exit"};
    int again = 3;

    /**
     *Check if player wants to play again
     * @return
     */
    public int getAgain() {
        return again;
    }

    /**
     *Sets option of the player - to play or not to play
     * @param again
     */
    public void setAgain(int again) {
        this.again = again;
    }
    
    /**
     * Makes a new game - basically resets board and timers
     */
    public void newGame(){
        Board board = new Board();
        game = new Game(board);
        this.chessBoard = game.getBoard();
        game.setOver(false);
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                boardTiles.get((j*8)+i).tilePiece = board.getTiles(i, j).getPiece();
                boardTiles.get((j*8)+i).x = board.getTiles(i, j).getX();
                boardTiles.get((j*8)+i).y = board.getTiles(i, j).getY();
            }
        }
        clockW.setStartedAt(System.currentTimeMillis());
        clockW.setStopAt(System.currentTimeMillis());
        clockW.setSum(0);
        clockB.setStartedAt(System.currentTimeMillis());
        clockB.setStopAt(System.currentTimeMillis());
        clockB.setSum(0);
        
    }
    
    /**
     * Loads game from file - puts pieces on board as it was in file
     * also changes time as it was in saved game
     */
    public void loadGame(){
        chessBoard = game.getBoard();
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                boardTiles.get((j*8)+i).tilePiece = chessBoard.getTiles(i, j).getPiece();
                boardTiles.get((j*8)+i).x = chessBoard.getTiles(i, j).getX();
                boardTiles.get((j*8)+i).y = chessBoard.getTiles(i, j).getY();
            }
        }
        clockW.setSumI(2);
        clockW.setSum(0);
        clockW.setCca(0);
        clockW.setStartedAt(System.currentTimeMillis());
        clockW.setStopAt(0);
        clockW.setCalW(game.getWhiteT());
        clockB.setSum(0);
        clockB.setSumI(2);
        clockB.setCca(0);
        clockB.setStartedAt(System.currentTimeMillis());
        clockB.setCalB(game.getBlackT());
        clockB.setStopAt(0);
    }
    
    public void setPiecs(){
        chessBoard = game.getBoard();
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                boardTiles.get((j*8)+i).tilePiece = chessBoard.getTiles(i, j).getPiece();
                boardTiles.get((j*8)+i).x = chessBoard.getTiles(i, j).getX();
                boardTiles.get((j*8)+i).y = chessBoard.getTiles(i, j).getY();
            }
        }
        clockW.setStartedAt(System.currentTimeMillis());
        clockW.setStopAt(System.currentTimeMillis());
        clockW.setSum(0);
        clockB.setStartedAt(System.currentTimeMillis());
        clockB.setStopAt(System.currentTimeMillis());
        clockB.setSum(0);
    }
    
    private boolean highlitedLegalMoves;

    /**
     * Constructor for a game
     * @param game
     */
    public Gui(Game game) {
        clockW = new Clock(true);
        clockB = new Clock(false);
        this.game = game;
        game.setOver(false);
        this.highlitedLegalMoves = true;
        this.gameFrame = new JFrame("Chess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar menuBar = createMenuBar();
        this.gameFrame.setJMenuBar(menuBar);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameFrame.setSize(new Dimension(600, 600));
        this.chessBoard = game.getBoard();
        this.boardGui = new BoardGui();
        this.gameFrame.add(this.boardGui, BorderLayout.CENTER);
        this.gameFrame.add(clockW, BorderLayout.LINE_END);
        this.gameFrame.add(clockB, BorderLayout.LINE_START);
        this.gameFrame.setVisible(true);
        setAgain(1);
        
    }
    /**
     * Makes menu bar, where we put options and filemenu
     * @return 
     */
    private JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createOptionsMenu());
        menuBar.add(createOnlineMenu());
        return menuBar;
    }
    
    /**
     * Makes online menu, where is server and client
     * @return 
     */
    private JMenu createOnlineMenu(){
        final JMenu onlineMenu = new JMenu("Online");
        final JMenuItem server = new JMenuItem("Create server");
        server.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                createOnlineGame();
            }
        });
        onlineMenu.add(server);
        final JMenuItem client = new JMenuItem("Join to server");
        final JFileChooser fc2 = new JFileChooser();
        client.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               int returnVal = fc2.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file1 = fc2.getSelectedFile();
                    try (BufferedReader br = new BufferedReader(new FileReader(file1)))  {
                        String line;
                        line = br.readLine();
                           joinOnlineGame(line);
                        
                    } catch (FileNotFoundException ex) {
                       Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                   } catch (IOException ex) {
                       Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                   }
                    //This is where a real application would open the file.
                    //log.append("Opening: " + file.getName() + "." + "\n");
                } else {
                   // log.append("Open command cancelled by user." + "\n");
                }
            }
        });
        onlineMenu.add(client);
        return onlineMenu;
        
    }
    /*
    Creates client
    */
    void joinOnlineGame(String ip){
    
           try{
               // System.out.println("pripojuji se");
               clientSocket = new Socket(ip, 4444);
               dataSend = new PrintWriter(clientSocket.getOutputStream(), true);
               dataRec = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
               isOnline = true;
               game.setAi(false);
               
               
               
           } catch (IOException ex){
               Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
           }
           /*
           ip adresu budu brat ze souboru
           Vytvorim novou hru
           new game create - nove rozpolozeni
           pak receiveboardstate
           */   
    }
    /*
    Creates server
    */
    void createOnlineGame(){
        try{
            serverSocket = new ServerSocket(4444);
            Runnable waitForClient = new Runnable(){
                @Override
                public void run(){
                    logger.log(Level.FINER, "Searching for opponent");
                    //System.out.println("HLedam oponenta");
                    while(clientSocket ==null && isOnline == true){
                        //System.out.println("Spojeni navazano");
                        try{
                        clientSocket = serverSocket.accept();
                        dataSend = new PrintWriter(clientSocket.getOutputStream(), true);
                      
                        dataRec = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        } catch (IOException ex){
                            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                    
                    logger.log(Level.FINER, "Searching stopped");
                    isOnline = true;
                    game.setAi(false);
                    
                    
            }
        };
            new Thread(waitForClient).start();
            
    } catch (IOException ex){
        Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
    }
        isOnline = true;
        game.setAi(false);
    }
    
    private void sendCoords(){
        logger.log(Level.FINER,"SENDING");
        String ses = "";
        ses += "\n"+Integer.toString(fromTile.getX()) + "\n";
        ses += Integer.toString(fromTile.getY()) + "\n";
        ses += Integer.toString(toTile.getX()) + "\n";
        ses += Integer.toString(toTile.getY());
       // System.out.println(dataSend);
       
        if (dataSend != null){
            //game.turn ^= true;
          //  System.out.println(ses);
            dataSend.println(ses);
        }
        
    }
    
    private void receiveCoords(){
        counter =0;
        Runnable waitForData;
        waitForData = new Runnable(){
            
            @Override
            public void run() {
                try{
                   // System.out.println("DOstal jsem data");
                    
                    if (dataRec != null && counter ==0){
                        int fromx,fromy,tox,toy;
                        try{
                            counter++;
                            String xs =dataRec.readLine();
                            xs =dataRec.readLine();
                           // System.out.println("1"+xs);
                            if ("0".equals(xs) || "1".equals(xs) || "2".equals(xs)||"3".equals(xs)||"4".equals(xs)||"5".equals(xs)||"6".equals(xs)||"7".equals(xs)){
                                fromx = Integer.parseInt(xs);
                            }else{
                                fromx=0;

                            }
                           //System.out.println("Real" + fromx);
                            xs =dataRec.readLine();
                           //System.out.println("2"+xs);
                            if ("0".equals(xs) || "1".equals(xs) || "2".equals(xs)||"3".equals(xs)||"4".equals(xs)||"5".equals(xs)||"6".equals(xs)||"7".equals(xs)){
                                fromy = Integer.parseInt(xs);
                            }else{
                                fromy =0;
                            }
                           // System.out.println("Real" + fromy);
                            xs =dataRec.readLine();
                            //System.out.println("3"+xs);
                            if ("0".equals(xs) || "1".equals(xs) || "2".equals(xs)||"3".equals(xs)||"4".equals(xs)||"5".equals(xs)||"6".equals(xs)||"7".equals(xs)){
                                tox = Integer.parseInt(xs);
                            }else{
                                tox =0;
                            }
                          //  System.out.println("Real" + fromx);
                            xs =dataRec.readLine();
                           // System.out.println("4"+xs);
                            if ("0".equals(xs) || "1".equals(xs) || "2".equals(xs)||"3".equals(xs)||"4".equals(xs)||"5".equals(xs)||"6".equals(xs)||"7".equals(xs)){
                                toy = Integer.parseInt(xs);
                            }else{
                                toy=0;
                            }
                           // System.out.println("Real" + toy);
                            if ("0".equals(xs) || "1".equals(xs) || "2".equals(xs)||"3".equals(xs)||"4".equals(xs)||"5".equals(xs)||"6".equals(xs)||"7".equals(xs)){
                                //System.out.println("moved maked");
                               // System.out.println("on 7,1 "+game.getBoard().getTiles(7, 1).getPiece());
                                game.blackOnline(fromx, fromy, tox, toy);
                                chessBoard = game.getBoard();
                                for(int i = 0; i < 8; i++){
                                    for (int j = 0; j < 8; j++){
                                        boardTiles.get((j*8)+i).tilePiece = chessBoard.getTiles(i, j).getPiece();
                                        boardTiles.get((j*8)+i).x = chessBoard.getTiles(i, j).getX();
                                        boardTiles.get((j*8)+i).y = chessBoard.getTiles(i, j).getY();
                                    }
                                }
                                //System.out.println("on 7,1 again "+game.getBoard().getTiles(7, 1).getPiece());
                               // System.out.println("on 7,2 again "+game.getBoard().getTiles(7, 2).getPiece());
                               
                            }
                        }catch (NumberFormatException ex){
                            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                } catch(IOException ex){
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        new Thread(waitForData).start();
    }
    /**
     * Creates options menu where user can change parameters of game as player vs player or vs ai and etc..
     * @return 
     */
    private JMenu createOptionsMenu(){
        final JMenu optionsMenu = new JMenu("Options");
        final JCheckBoxMenuItem ai = new JCheckBoxMenuItem("Ai mode", game.isAi());
        ai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isOnline){
                    game.setAi(!game.isAi());
                }
                
            }
        });
        optionsMenu.add(ai);
        final JMenuItem sCastle = new JMenuItem("Small castle");
        sCastle.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                game.sCastle();
            }
        });
        optionsMenu.add(sCastle);
        final JMenuItem bCastle = new JMenuItem("Big castle");
        bCastle.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                game.bCastle();
            }
        });
        optionsMenu.add(bCastle);
        return optionsMenu;
    }
    /**
     * Creates menu, where user can make a new game, save game, load game etc..
     * @return 
     */
    private JMenu createFileMenu(){
        final JMenu fileMenu = new JMenu("File");     
        
        final JMenuItem putMenuItem = new JMenuItem("Put figures");
        final JFileChooser fc1 = new JFileChooser();

        putMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc1.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc1.getSelectedFile();
                    game.putFig(file);
                    setPiecs();
                    //This is where a real application would open the file.
                    //log.append("Opening: " + file.getName() + "." + "\n");
                } else {
                   // log.append("Open command cancelled by user." + "\n");
                }
            }
        });
        fileMenu.add(putMenuItem);
        fileMenu.addSeparator();
        
        final JMenuItem newMenuItem = new JMenuItem("New game");
        newMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        fileMenu.add(newMenuItem);
        fileMenu.addSeparator();
        
        final JMenuItem saveMenuItem = new JMenuItem("Save game");
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.saveBoard();
            }
        });
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        
        final JMenuItem loadMenuItem = new JMenuItem("Load game");
        final JFileChooser fc = new JFileChooser();

        loadMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    game.loadBoard(file);
                    loadGame();
                    //This is where a real application would open the file.
                    //log.append("Opening: " + file.getName() + "." + "\n");
                } else {
                   // log.append("Open command cancelled by user." + "\n");
                }
            }
        });
        fileMenu.add(loadMenuItem);
        fileMenu.addSeparator();
        
        
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);
        
        
        return fileMenu;
    }
    
    /**
     *Makes 64 tiles
     */
    public class TileGui extends JPanel{
    private int x;
    private int y;
    private Piece tilePiece;
    
    TileGui(final BoardGui boardGui, int x, int y){
        super(new GridBagLayout());
        this.x = x;
        this.y = y;

        setPreferredSize(new Dimension(10, 10));
        assingTileColor();
        assignTilePieceIcon(chessBoard);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent event) {
                
                if (SwingUtilities.isRightMouseButton(event)){
                   // System.out.println("LEFT MOUSE ");
                    fromTile = null;
                    toTile = null;
                    movedPiece = null;
     
                } else if(SwingUtilities.isLeftMouseButton(event)){
                    if(fromTile == null){
                        receiveCoords();

                        for(int i = 0; i < 8; i++){
                            for (int j = 0; j < 8; j++){
                                boardTiles.get((j*8)+i).tilePiece = chessBoard.getTiles(i, j).getPiece();
                                boardTiles.get((j*8)+i).x = chessBoard.getTiles(i, j).getX();
                                boardTiles.get((j*8)+i).y = chessBoard.getTiles(i, j).getY();
                            }
                        }
                        fromTile = chessBoard.getTiles(x, y);
                        movedPiece = fromTile.getPiece();
                      
                        if (movedPiece == null){
                            fromTile = null;
                        }
                    } else {
                        
                        int suc = 0;
                        toTile = chessBoard.getTiles(x, y);
                        if (toTile.getPiece() != null && fromTile.getPiece()!=null&&toTile.getPiece().black == fromTile.getPiece().black){
                            fromTile = null;
                            return;
                        }
                        if (fromTile.getPiece()!=null){
                            for (int i = 0; i < fromTile.getPiece().getPossibleMoves(chessBoard).size(); i++){

                                if (fromTile.getPiece().getPossibleMoves(chessBoard).get(i).getX() == toTile.getX() &&
                                fromTile.getPiece().getPossibleMoves(chessBoard).get(i).getY() == toTile.getY()){
                                    suc++;
                                }
                            }
                        }
                        if (suc == 0){
                            fromTile = null;
                            return;
                        }
                        if (!(fromTile.getX() == toTile.getX() && fromTile.getY() == toTile.getY()) || !(fromTile.piece.black == toTile.piece.black)){
                            
                            
                            if (isOnline){
                                game.blackOnline(fromTile.getX(), fromTile.getY(),toTile.getX(), toTile.getY()); 
                                sendCoords();
                            }else{
                                game.makeMove(fromTile.getX(), fromTile.getY(),toTile.getX(), toTile.getY());  
                            }
                            fromTile = null;
                            toTile = null;
                            movedPiece = null;
                            if(game.isOver()){
                                Container pane = getContentPane();
                                pane.setLayout(new FlowLayout());
                                again = JOptionPane.showOptionDialog(pane, "Do you want to?",
                                "Game Over",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE, null,
                                choice, choice[1]);                 
                                if (again==JOptionPane.OK_OPTION)
                                {
                                    newGame();
                                }
                                else{
                                    System.exit(0);
                                }
                            }
                            
                        }else{
                            fromTile = null;
                            return;
                        }
                        
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            boardGui.drawBoard(chessBoard);
                        }
                    });
                    

                }
        }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }       
    
        });
    }
    /**
     * Puts color on each Tile
     */
    private void assingTileColor() {
        if ((x%2 == 0) && (y %2 == 0)){
            setBackground(Color.decode("#33322f"));
        }else if ((x%2 != 0 )&& (y%2 == 0)){
            setBackground(Color.decode("#e5e3d5"));
        } else if (x%2 != 0 && y%2 != 0){
            setBackground(Color.decode("#33322f"));
        } else if (x%2 == 0 && y%2 != 0){
            setBackground(Color.decode("#e5e3d5"));
        }
    }
    
    /**
     * Puts piece on each tile
     * @param board 
     */
    private void assignTilePieceIcon(Board board){
        this.removeAll();
        if(!board.getTiles(x, y).isEmpty()){
            String pieceIconPath = "src/main/resources/";

            try {
                final BufferedImage image;
                image = ImageIO.read(new File(pieceIconPath + Boolean.toString(!board.getTiles(x, y).piece.black) + board.getTiles(x, y).piece.toString() + ".gif"));
                add(new JLabel(new ImageIcon(image)));
            } catch (IOException ex) {
                Logger.getLogger(TileGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
       
    }
    
        /**
         * Puts green button on valiable moves
         * @param board
         */
        public void highlightMoves(final Board board){
        if(movedPiece == null){
                return;
            }
        if (highlitedLegalMoves && movedPiece.black == game.getTurn()){
          // System.out.println("Kde je chyba" + fromTile.piece);
           if (fromTile.piece == null){
               return;
           }
           if (fromTile.piece.getPossibleMoves(board).size() < 1){
               return;
           }
            for (final Coords coords : fromTile.piece.getPossibleMoves(board)){
                if(coords.getX() == this.x && coords.getY() == this.y){
                    try {
                        add(new JLabel(new ImageIcon(ImageIO.read(new File("src/main/resources/green_dot.png")))));
                    } catch (IOException ex) {
                        Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
        /**
         * Draw each tile
         * @param board
         */
        public void drawTile(final Board board){
        assingTileColor();
        assignTilePieceIcon(board);
        highlightMoves(board);
        validate();
        repaint();
        }

    }

    /**
     * Makes board from 64 tiles
     */
    public class BoardGui extends JPanel{

        /**
         *Constructor
         */
        public BoardGui() {
        super(new GridLayout(8,8));
        boardTiles = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                final TileGui tileGui = new TileGui(this, j, i);
                boardTiles.add(tileGui);
                add(tileGui);
            }
        }
        setPreferredSize(new Dimension(400, 350));
        validate();
    }
    
        /**
         * Draws board
         * @param board
         */
        public void drawBoard(final Board board){
        removeAll();
        for(final TileGui tileGui : boardTiles){
            tileGui.drawTile(board);
            add(tileGui);
            
        }
        validate();
        repaint();
    }
    }

    /**
     *Makes clock using timer
     */
    public class Clock extends JPanel {
        private JLabel label;
        //private JButton button;

        private Timer timer;
        private long startedAt;
        private long stopAt;
        private long cca = 0;
        private long sum = 0;
        int index = 0;
        int sumI;

        /**
         * Corrector for loading time
         * @param sumI
         */
        public void setSumI(int sumI) {
            this.sumI = sumI;
        }

        /**
         *Starting time
         * @return
         */
        public long getStartedAt() {
            return startedAt;
        }

        /**
         * Starting time
         * @param startedAt
         */
        public void setStartedAt(long startedAt) {
            this.startedAt = startedAt;
        }

        /**
         *Stop time - to know the difference
         * @return
         */
        public long getStopAt() {
            return stopAt;
        }

        /**
         *Stop time - to know the difference
         * @param stopAt
         */
        public void setStopAt(long stopAt) {
            this.stopAt = stopAt;
        }

        /**
         * FOr counting time
         * @return
         */
        public long getSum() {
            return sum;
        }

        /**
         *
         * @param sum
         */
        public void setSum(long sum) {
            this.sum = sum;
        }
        int swap1 =0;
        long calB = 0;
        long calW = 0;

        /**
         *
         * @return
         */
        public long getCalB() {
            return calB;
        }

        /**
         *
         * @param calB
         */
        public void setCalB(long calB) {
            this.calB = calB;
        }

        /**
         *
         * @return
         */
        public long getCalW() {
            return calW;
        }

        /**
         *
         * @param calW
         */
        public void setCalW(long calW) {
            this.calW = calW;
        }

        /**
         *
         * @param cca
         */
        public void setCca(long cca) {
            this.cca = cca;
        }

        /**
         *
         */
        public static final int RUN_TIME = 1200000;
        long sec;
        int swap;
        long diff;

        /**
         * COnstructor
         * @param color
         */
        public Clock(boolean color) {
            label = new JLabel("20:00");
            //button = new JButton("Start");
            setLayout(new GridBagLayout());
            swap = 0;
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            add(label, gbc);
            startedAt = System.currentTimeMillis();
            timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(color == true){
                    diff = System.currentTimeMillis() - startedAt +calW;
                }else{
                    diff = System.currentTimeMillis() - startedAt + calB;
                }
                if (diff >= RUN_TIME) {
                    label.setText("Out of time");
                    game.setOver(true);
                } else if (diff < RUN_TIME && game.getTurn() == color){
                    if (swap == 0 && game.getTurn() == false){
                        startedAt = System.currentTimeMillis();
                        swap++;

                    }
                    if(index == 1){
                        sum += cca;
                    }
                    if (sumI == 2){
                        sum = 0;
                        sumI++;
                    }
                    swap1 = 1;
                    index = 0;
                    sec =  ((remainingTime()+sum)%60);
                    label.setText("Time:" +(remainingTime()+sum)/60 + ":" + sec);
                    stopAt = System.currentTimeMillis();
                    if (game.getTurn() == true){
                        game.setWhiteT(remainingTime()+sum);
                    }else{
                        game.setBlackT(remainingTime()+sum);
                    }
                }else if (game.getTurn() != color){
                    cca = usedTime();
                    index = 1;
                    if (game.getTurn() ==true && swap1 == 0){
                        cca = 0;
                    }
                }
            }
            });
        timer.start();


        }
        
        /**
         * Counts remaining time
         * @return
         */
        public int remainingTime() {
            long diff = System.currentTimeMillis() - startedAt;
            return (int) Math.round((RUN_TIME - diff) / 1000d);
        }

        /**
         * Counts used time
         * @return
         */
        public int usedTime() {
            long diff = System.currentTimeMillis() - stopAt;
            return (int) Math.round(diff / 1000d);
        }
/*
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(300, 200);
        }
*/

    }

    
}
