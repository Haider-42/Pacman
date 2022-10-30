/*****************************************************************************************************
 * Author:  Haider Saleem
 * Date:  March 14, 2022
 * Description:  This is a java based pacman game. The objective of this game is to win by collecting
 *               all the points under 60 seconds. This game is made by using if statements and while 
 *               loops. The board is constructed by using 2d Arrays.
 ****************************************************************************************************/
import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.BasicStroke;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Image;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import javax.sound.sampled.*;


/**
* Main Class
* Objective: The Main class includes the main method which allows the game to run
*/ 
public class HaiderPacman {
  static final long serialVersionUID = 2;

  public static void main(String args[]) throws Exception {
    PacMan gameBoard = new PacMan();
    gameBoard.setLocationRelativeTo(null);
    gameBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gameBoard.init();
  }// end main method
}// end class


/**
* Method: main<BR>
* Objective: Instataniates the PacMan class and call init method
* @param String[] args
* @throws Exception
*/
class PacMan extends JFrame implements KeyListener {
  static final long serialVersionUID = 2;
  BufferedImage buffer;
  char[][] board = {
      { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
      { 'X', 'P', '*', '*', '*', '*', '*', '*', '*', '*', '*', 'X', 'X', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', 'X' },
      { 'X', '*', 'X', 'X', 'X', 'X', '*', '*', 'X', 'X', '*', '*', '*', '*', 'X', 'X', '*', '*', 'X', 'X', 'X', 'X', '*', 'X' },
      { 'X', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', 'X' },
      { 'X', '*', 'X', 'X', 'X', '*', '*', '*', '*', 'X', 'X', '-', '-', 'X', 'X', '*', '*', '*', '*', 'X', 'X', 'X', '*', 'X' },
      { 'X', '*', 'X', '*', '*', '*', '*', 'X', '*', 'X', '4', '3', '1', '2', 'X', '*', 'X', '*', '*', '*', '5', 'X', '*', 'X' },
      { 'X', '*', 'X', 'X', 'X', '*', '*', 'X', '*', 'X', 'X', 'X', 'X', 'X', 'X', '*', 'X', '*', '*', 'X', 'X', 'X', '*', 'X' },
      { 'X', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', 'X' },
      { 'X', '*', 'X', 'X', 'X', 'X', '*', '*', 'X', 'X', '*', '*', '*', '*', 'X', 'X', '*', '*', 'X', 'X', 'X', 'X', '*', 'X' },
      { 'X', '5', '*', '*', '*', '*', '*', '*', '*', '*', '*', 'X', 'X', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', 'X' },
      { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };

  char pacmanShape = 'R';
  int pacmanC = '1';
  int pacmanR = '1';
  char direction = 'R';
  int points = 0;
  long timer;
  long time = 0;
  long clock = System.currentTimeMillis();
  final int WIDTH = board[0].length * 30, HEIGHT = board.length * 30;
  JPanel boardPanel;
   
   
/**
* PacMan Class
* Objective: This class stores all the code for the pacman game
*/
  public PacMan() {
    super("Pacman Game");

    buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    // set up board
    boardPanel = new JPanel();
    boardPanel.setDoubleBuffered(true);
    boardPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    boardPanel.setMinimumSize(new Dimension(WIDTH, HEIGHT));

    JPanel pane = (JPanel) getContentPane();
    addKeyListener(this);
    pane.add(boardPanel);
    pane.setDoubleBuffered(true);
    setSize(WIDTH + 40, HEIGHT + 40);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }


/**
* Method: keyTyped
* Objective: Detects the input of when the key is typed
* @Param (KeyEvent e)
*/
  public void keyTyped(KeyEvent e) {

  }


/**
* Method: keyPressed
* Objective: Detects the input of when the key is pressed
* @Param (KeyEvent e)
*/
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    if (key == KeyEvent.VK_LEFT)
      direction = 'L';
    if (key == KeyEvent.VK_RIGHT)
      direction = 'R';
    if (key == KeyEvent.VK_UP)
      direction = 'U';
    if (key == KeyEvent.VK_DOWN)
      direction = 'D';
  }
  
  
/**
* Method: keyRealeased
* Objective: Detects the input of when the key is released
* @Param (KeyEvent e)
*/
  public void keyReleased(KeyEvent e) {
  }


/**
* Method: drawBoard
* Objective: Displaying items on the screen by reading the Board array.
*/
  public void drawBoard() {
    Graphics2D b = buffer.createGraphics();
    BufferedImage boardImage = null;
    b.setColor(Color.black);
    b.fillRect(0, 25, WIDTH, HEIGHT);
    for (int row = 0; row < board.length; row++) {
      for (int column = 0; column < board[row].length; column++) {
        if (board[row][column] == 'X') // barrier
        {
          b.setColor(new Color(0, 0, 255));
          b.fillRect((column) * 30, (row) * 30, 30, 30);
        } else if (board[row][column] == '*') { // point
          b.setColor(new Color(0, 128, 0));
          b.fillOval((column) * 30 + 9, (row) * 30 + 9, 10, 10);
        } else if (board[row][column] == '-') {
          b.setColor(new Color(255, 255, 255));
          b.setStroke(new BasicStroke(6));
          b.drawLine(column * 30 + 3, row * 30 + 12, column * 30 + 27, row * 30 + 12);
        } else if (board[row][column] >= '1' && board[row][column] <= '6') {  
          addGhost(b, board[row][column], column * 30, row * 30);
        } else if (board[row][column] == 'P') { // Pacman color and shape
          pacmanR = row;
          pacmanC = column;
          if(System.currentTimeMillis() -time < 5000)
          b.setColor(Color.red);
          else
          b.setColor(Color.yellow);
          if (pacmanShape == 'O')
            b.fillArc((column) * 30 + 3, (row) * 30 + 3, 24, 24, 0, 365);
          else if (pacmanShape == 'R')
            b.fillArc((column) * 30 + 3, (row) * 30 + 3, 24, 24, 45, 270);
          else if (pacmanShape == 'L')
            b.fillArc((column) * 30 + 3, (row) * 30 + 3, 24, 24, 180 + 45, 270);
          else if (pacmanShape == 'U')
            b.fillArc((column) * 30 + 3, (row) * 30 + 3, 24, 24, 90 + 45, 270);
          else if (pacmanShape == 'D')
            b.fillArc((column) * 30 + 3, (row) * 30 + 3, 24, 24, 270 + 45, 270);
            
        }

      }
      
    }

    b.setColor(new Color(255, 255, 255));
    b.setFont(new Font("DialogInput", Font.PLAIN, 18));
    b.drawString("Score: " + points, 20, HEIGHT - 12);
    timer = (System.currentTimeMillis() - clock)/1000;
    b.setColor(new Color(255, 255, 255));
    b.setFont(new Font("DialogInput", Font.PLAIN, 18));
    b.drawString("Time: " + timer, 625, HEIGHT - 310);
    
    b.drawImage(boardImage, 0, 15, this);
    b.dispose();
    drawScreen();
}


/**
* Method: drawScreen
* Objective: Display picture onto the screen from buffer (memory)
*/
  public void drawScreen() {
    Graphics2D g = (Graphics2D) this.getGraphics();
    g.drawImage(buffer, 6, 30, this);
    Toolkit.getDefaultToolkit().sync();
    g.dispose();
  }


/**
* Method: init
* Objective: Starting the game and display screen and draw's the Board
*/
  public void init() {
    Graphics2D b = buffer.createGraphics();
    play("pacman.wav");
    clock = System.currentTimeMillis();
    drawBoard();
    b.dispose();
    drawScreen();
    gameLoop();

  }
  
  
/**
* Method: play(String song) 
* Objective: plays a song stored from the file using wav method
*/
public void play(String song)
  {
    try{ 
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(song));
      Clip myClip = AudioSystem.getClip();
      myClip.open(audioInputStream);

      FloatControl gainControl = (FloatControl) myClip.getControl(FloatControl.Type.MASTER_GAIN);
      gainControl.setValue(-15.0f); // Reduce volume by 15 decibels 
      myClip.start();
      
   } catch(Exception ex){
      System.out.print("Caught error in play method" + ex);     
  }
  }


/*
* Method : gameLoop
* Objective: Loops the game using the while(true) loop
*/
  public void gameLoop() {
    while (true) {
      try {
        move();
        win();
        loser();
        drawBoard();
        Thread.sleep(300);
      } catch (Exception e) {
        e.printStackTrace();
      }
      while(System.currentTimeMillis() - time<5000)
      try{
      move();
      drawBoard();
      Thread.sleep(200);
      } catch(Exception e){
      e.printStackTrace();
      }     
}
}


/*
* Method: addGhost
* Objective: Display the ghost pictures onto the board from the file
* @Param Graphics 2D b: Gets object from the Graphic 2D
* @Param int x: Column of ghost
* @Param int y: Row of ghost
*/
  public void addGhost(Graphics2D b, char ghost, int x, int y) {
    BufferedImage imgs = null;
    Image newImage = null;
    try {
      if (ghost == '1')   
        imgs = ImageIO.read(new File("pacmanghost.png"));
      else if (ghost == '2')
        imgs = ImageIO.read(new File("pacmanghost.png"));
      else if (ghost == '3')
        imgs = ImageIO.read(new File("pacmanghost.png"));
      else if (ghost == '4')
        imgs = ImageIO.read(new File("pacmanghost.png"));
      else if (ghost == '5')
        imgs = ImageIO.read(new File("watermelon.png"));
  
      newImage = imgs.getScaledInstance(24, 30, Image.SCALE_DEFAULT);
      b.drawImage(newImage, x + 3, y, this);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Runtime error in addGhost: picture for ghost" + ghost + " not found");
      System.exit(0);
    }
  }
  
/* 
* Method: move
* Objective: Allows the player to move the pacman in all four directions
*/ 
  public void move() {
    int row = 0;
    int column = 0;
    if (pacmanShape == 'O')
      pacmanShape = direction;
    else
      pacmanShape = 'O';

    if (direction == 'R') {
      row = pacmanR;
      column = pacmanC + 1;
    }
    if (direction == 'U') {
      row = pacmanR - 1;
      column = pacmanC;
    }
    if (direction == 'L') {
      row = pacmanR;
      column = pacmanC - 1;
    }
    if (direction == 'D') {
      row = pacmanR + 1;
      column = pacmanC;
    }
    if (board[row][column] != 'X')
   {  
      if(board[row][column] == '*') // Increasing the score by 1 each time the pacman touches the dot
        points++;
       if(board[row][column] == '5')
      time = System.currentTimeMillis();
      board[pacmanR][pacmanC] = ' ';
      board[row][column] = 'P';
    }
    }

    
/* 
* Method: resetBoard
* Objective: Restart the game if the player agree's to play again
*/
    public void resetBoard(){
     board = new char[][]{
      { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
      { 'X', 'P', '*', '*', '*', '*', '*', '*', '*', '*', '*', 'X', 'X', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', 'X' },
      { 'X', '*', 'X', 'X', 'X', 'X', '*', '*', 'X', 'X', '*', '*', '*', '*', 'X', 'X', '*', '*', 'X', 'X', 'X', 'X', '*', 'X' },
      { 'X', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', 'X' },
      { 'X', '*', 'X', 'X', 'X', '*', '*', '*', '*', 'X', 'X', '-', '-', 'X', 'X', '*', '*', '*', '*', 'X', 'X', 'X', '*', 'X' },
      { 'X', '*', 'X', '*', '*', '*', '*', 'X', '*', 'X', '4', '3', '1', '2', 'X', '*', 'X', '*', '*', '*', '5', 'X', '*', 'X' },
      { 'X', '*', 'X', 'X', 'X', '*', '*', 'X', '*', 'X', 'X', 'X', 'X', 'X', 'X', '*', 'X', '*', '*', 'X', 'X', 'X', '*', 'X' },
      { 'X', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', 'X' },
      { 'X', '*', 'X', 'X', 'X', 'X', '*', '*', 'X', 'X', '*', '*', '*', '*', 'X', 'X', '*', '*', 'X', 'X', 'X', 'X', '*', 'X' },
      { 'X', '5', '*', '*', '*', '*', '*', '*', '*', '*', '*', 'X', 'X', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', 'X' },
      { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };
    
    points = 0;
    clock = System.currentTimeMillis();
    timer = clock - timer;
    }
  
    
/*
* Method: win
* Objective: Detects if the player has won and has completed the game under 60 seconds
*/
         public void win()
   {
      if (points == 131 && timer <= 60) {
         Object[] answer = {" Yes ", " No "};
         int f = JOptionPane.showOptionDialog(null, "Congratulations, You have completed the game. Would you like to play again?", "Option", JOptionPane.YES_NO_OPTION, 
         JOptionPane.QUESTION_MESSAGE, null, answer, answer[1]);

         if (f == 0)
            resetBoard();
         if (f ==1)
            System.exit(0); 
       }
       }
       
       
/*
* Method: Loser
* Objective: Detects if the player has failed to collect all the dot's under 60 seconds
*/
         public void loser()
   {

      if (timer == 60 && points <= 131) {
         Object[] answer = {"Yes", "No"};
         int f = JOptionPane.showOptionDialog(null, "You ran out of time. Would you like to play again?", "Option", JOptionPane.YES_NO_OPTION, 
         JOptionPane.QUESTION_MESSAGE, null, answer, answer[1]);

         if (f == 0)
            resetBoard();
         if (f ==1)
            System.exit(0);
   }
}  


   }


  