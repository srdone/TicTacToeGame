import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;

public class TicTacToeGame extends Applet {
  private final int START_X = 20;					//X coordinate of the gameboard.
  private final int START_Y = 90;					//Y coordinate of the gameboard.
  private final int ROWS = 3;						//Number of tic-tac-toe rows.
  private final int COLS = 3;						//Number of tic-tac-toe columns.
  private final int BOX_WIDTH = 20;					//width of one tic-tac-toe square.
  private final int BOX_HEIGHT = 20;				//height of one tic-tac-toe square.
  
  private TicTacToeBox boxes[][];					//a two-dimensional array containing the game squares.
  
  private Button resetButton;						//the button to begin a new game.
  private Label infoLabel;							//The label displaying all the game messaging.
  
  private Player players[];							//An array containing all the game players
  
  private Player currentPlayer;						//A variable pointing to the current player.
  
  private boolean gameOver;							//Boolean to keep track of whether the game has completed.

  private boolean doDebug = true;       //determines whether debug code is on or off.
  
  /*
   * Initializes the game board.
   * Creates a two-dimensional array of tictactoeboxes and a new game button.
   * Initializes the color of the boxes and sends a call to initialize and draw the boxes.
   * Sends call to create the players of the game.
   */
  public void init() {
    Panel nPanel = new Panel();
    nPanel.setLayout(new BoxLayout(nPanel,BoxLayout.Y_AXIS));
    boxes = new TicTacToeBox[ROWS][COLS];
    debugMe("boxes created");
    resetButton = new Button("New Game");
    debugMe("new game button created");
    resetButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) { //resets game board on click
        buildBoxes();
        createPlayers();
        gameOver = false;
        infoLabel.setText(currentPlayer.getName() + "'s Turn"); //clear the text saying who won.
        repaint();
      }
    });
    debugMe("action listeners added");
    nPanel.add(resetButton);
    debugMe("reset button added");
    createPlayers();
    gameOver = false;
    for (int i = 0; i < players.length; i++) {debugMe(players[i].getName() + "created");};
    buildBoxes();
    infoLabel = new Label(currentPlayer.getName() + "'s Turn");
    infoLabel.setSize(getWidth(),20);
    nPanel.add(infoLabel);
    add(nPanel);
    debugMe("buildBoxes call finished");
  }
  
  
  //TODO: break this down as far as possible. Right now is difficult to read.
  public void paint(Graphics g) {
    debugMe("Paint() called");
    for(int row = 0; row < ROWS; row++) {
      for(int col = 0; col < COLS; col++) {
        if(boxes[row][col].isClicked()) {
          debugMe("box[" + row + "][" + col + "clicked");
          boxes[row][col].setClicked(false);
          debugMe("is box picked already?");
          if (!boxes[row][col].isPicked() && !gameOver) { 
            debugMe("is picked is " + boxes[row][col].isPicked());
            boxes[row][col].setPicked(currentPlayer.getPlayerType());
            gameLogic(boxes[row][col]);
          } else {
            debugMe("is picked is " + boxes[row][col].isPicked());
          }
        } 
      }
    }
    for (int row = 0; row < boxes.length; row++) {
      for (int col = 0; col < boxes[row].length; col++) {
        boxes[row][col].draw(g);
      }
    }
  }
  
  private void removeMouseListeners() {
    for(int row = 0; row < boxes.length; row++) {
      for(int col = 0; col < boxes[row].length; col++) {
        removeMouseListener(boxes[row][col]);
      }
    }
  }
  
  //Initializes the individual tic tac toe boxes.
  private void buildBoxes() {
    removeMouseListeners();
    for(int row = 0; row < ROWS; row++) {
      for(int col = 0; col < COLS; col++) {
        boxes[row][col] = 
            new TicTacToeBox(START_X + col * BOX_WIDTH,
                             START_Y + row * BOX_HEIGHT,
                             BOX_WIDTH,
                             BOX_HEIGHT,
                             this); 
        addMouseListener(boxes[row][col]);
      }
    }
  }
  
  //initializes players
  private void createPlayers() {
    players = new Player[2];
    debugMe("Create player start");
    players[0] = new Player("Player 1", 0);
    debugMe("player 1 created");
    players[1] = new Player("Player 2", 1);
    this.currentPlayer = players[0];
    debugMe("createPlayers call complete");
  }
  
  /*
  *Pre-condition: A box was clicked and there are enough clicked boxes for the current player to make a winning set possible.
  *Post-condition: An action, depending on the game state, is taken. If there is a match, the game is ended. If there is no match and there are still
  *remaining squares, the game is turned over to the next player. If there are no matches and all the squares are clicked, a "cat's game" is declared.
  *Summary: Chooses the next phase in the game depending on which box was clicked and what boxes have already been clicked.
  */
  private void gameLogic(TicTacToeBox box) {
    debugMe("GameLogic() called");
    currentPlayer.addChosenBox(box);
    if (checkWinningCombo() == 1) {endGame();} else if (checkWinningCombo() == 0){nextPlayer();} else if (checkWinningCombo() == -1) {catsGame();};
    
  }
  
  /*
  *Pre-condition: The current player has a winning combination of boxes (straight line of 3 consecutive)
  *Post-Condition: Current player is declared the winner. Game state is set to game over so user must restart game to continue.
  *Summary: Handles steps that happen at the end of a game.
  */
  private void endGame() {
    debugMe("endGame() called");
    infoLabel.setText("Winner: " + currentPlayer.getName());
    gameOver = true;
  }
  
  //pre-condition: the last player to pick did not win and is the current player
  //post-condition: the current player is the opposite player from the one who just clicked a tile.
  //Summary: Switches back and forth between each player after the turn is over.
  private void nextPlayer() {
    if (currentPlayer.equals(players[0])) {
      currentPlayer = players[1];
      infoLabel.setText(currentPlayer.getName() + "'s Turn");
    } else {
      currentPlayer = players[0];
      infoLabel.setText(currentPlayer.getName() +"'s Turn");
    }
  }
  
  //Pre-condition: A player has selected a box.
  //Post-condition: Winning combo returns 1, no winning combo returns 0, no winning combo and all boxes selected returns -1 (cat's game)
  //Summary: Tests to see if the current player has a winning combo, none, or a cat's game
  private int checkWinningCombo() {
    debugMe("Checking winning combo for " + currentPlayer.getName());
    debugMe("Enough boxes?");
    if(currentPlayer.getNumberofBoxes() < 3) return 0; //if we don't have at least 3 chosen boxes, we don't have a winner
    if(playerHasWinningCombo()) return 1; //check each combination for winning
    if(allBoxesSelected()) return -1;
    return 0;
  }

  //Pre-condiion: A player has selected a box and has at least 3 boxes selected.
  //Post-condition: If there is a winning combination (straight line of three boxes selected by the player), returns true, else false
  //Summary: Tests to see if the player has a straight line of three boxes that constitutes a winning combination.
  private boolean playerHasWinningCombo() {
    if(rowSetMatches() | columnSetMatches() | leftDiagonalMatches() | rightDiagonalMatches()) {return true;} else {return false;}
  }
  
  //Tests to see if all boxes have been chosen yet.
  private boolean allBoxesSelected() {
    if ((players[0].getNumberofBoxes() + players[1].getNumberofBoxes())==9) {return true;} else {return false;}
  }

  private boolean rowSetMatches() {
    for (int row = 0; row < ROWS; row++) {
      int checkValue = 0;
      for (int col = 0; col < COLS; col++) {
        for (int playerbox = 0; playerbox < currentPlayer.getNumberofBoxes(); playerbox++) {
          if(currentPlayer.getChosenBox(playerbox).equals(boxes[row][col])) checkValue++; //if a chosen box and a game box match, up one value
          debugMe("checkValue = " + checkValue);
          if(checkValue == 3) return true; //because this is in the same grouping, we are checking one winning combo. if checkValue is three it means we have a winning combo.
        }
      }
    }
    return false;
  }


  private boolean columnSetMatches() {
    for (int col = 0; col < 3; col++) {
      int checkValue = 0;
      for (int row = 0; row < 3; row++) {
        for (int playerbox = 0; playerbox < currentPlayer.getNumberofBoxes(); playerbox++) {
          if(currentPlayer.getChosenBox(playerbox).equals(boxes[row][col])) checkValue++; //if a chosen box and a game box match, up one value
          debugMe("checkValue = " + checkValue);
          if(checkValue == 3) return true; //because this is in the same grouping, we are checking one winning combo. if checkValue is three it means we have a winning combo.
        }
      }
    }
    return false;
  }


  private boolean leftDiagonalMatches() {
    int checkValue = 0;
    for (int i = 0; i < currentPlayer.getNumberofBoxes(); i++) {
      debugMe("Checking box " + i + " of left diagonal");
      if(currentPlayer.getChosenBox(i).equals(boxes[0][0]) || 
          currentPlayer.getChosenBox(i).equals(boxes[1][1]) || 
          currentPlayer.getChosenBox(i).equals(boxes[2][2])) {
        checkValue++;
        debugMe("checkValue = " + checkValue);
      }
    }
    if (checkValue==3) {return true;} else {return false;}
  }


  private boolean rightDiagonalMatches() {
    int checkValue = 0;
    for (int i = 0; i < currentPlayer.getNumberofBoxes(); i++) {
      debugMe("Checking box " + i + " of right diagonal");
      if(currentPlayer.getChosenBox(i).equals(boxes[0][2]) || 
          currentPlayer.getChosenBox(i).equals(boxes[1][1]) || 
          currentPlayer.getChosenBox(i).equals(boxes[2][0])) {
         checkValue++; 
         debugMe("checkValue = " + checkValue);
         }
    }
    if (checkValue == 3) {return true;} else {return false;}
  }


  private void catsGame() {
    infoLabel.setText("Cat's Game");
  }

  private void debugMe(String string) {
    if(doDebug) System.out.println(string);
  }

  }
