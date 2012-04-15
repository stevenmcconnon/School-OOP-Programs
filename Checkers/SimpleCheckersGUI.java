//	Steven McConnon
//	COP 3330 - 001
//	This class puts together all of the other classes to create the Checkers game.

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

/**
 * This class puts together all of the other classes to create the Checkers game.
 * @author Steven McConnon
 *
 */
public class SimpleCheckersGUI implements MouseListener, EventListener {
	private static JDialog frame;
	private static JPanel panel;
	private static JPanel panel2;
	private static Canvas canvas;
	private static JLabel status;
	private static Board board;
	private static JMenuBar menuBar;
	private static JMenu menu;
	private static JMenuItem quit;
	private static JMenuItem newGame;
	private static final int BOARD_SIZE = 800;
	private static final int NUM_PIECES = 8;
	private static final int WIDTH = BOARD_SIZE/NUM_PIECES, HEIGHT = WIDTH;
	private static Graphics g;
	

	
	/**
	 * This simple constructor simply creates a new GUI for the checkers game.
	 * 
	 * @see createGUI()
	 */
	public SimpleCheckersGUI() {
		createGUI();
	}
	
	
	
	/**
	 * The main method of this class just creates a new SimpleCheckersGUI object.
	 * 
	 * @param args
	 * @see SimpleCheckersGUI()
	 */
	public static void main(String[] args) {
		new SimpleCheckersGUI();	
	}

	
	/**
	 * This class creates the JDialog and components for the GUI. <br><br> It also calls the startGame() method.
	 * 
	 * @see startGame()
	 */
	public void createGUI() {
		frame = new JDialog();
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		panel2 = new JPanel();
		canvas = new Canvas();
		status = new JLabel();
		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		quit = new JMenuItem("Quit");
		newGame = new JMenuItem("New Game");
		
		menu.add(newGame);
		menu.add(quit);
		
		
		quit.setMnemonic(KeyEvent.VK_Q);
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		
		newGame.setMnemonic(KeyEvent.VK_N);
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		
		
		//closes the window
		quit.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
					}
				}
		);
		
		
		// re-starts the game
		newGame.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						startGame();
					}
				}
		);
		
		
		menuBar.add(menu);

		canvas.setSize(BOARD_SIZE,BOARD_SIZE);
		canvas.setBackground(Color.WHITE);
		canvas.addMouseListener(this);

		panel.setLayout(new BorderLayout());
		panel.add(panel2, BorderLayout.SOUTH);
		panel.add(canvas, BorderLayout.CENTER);

		panel2.add(status);

		status.setText("To play, select a checker by clicking on it. Then click on where you want to make your move. Black turn first.");			

		frame.add(panel);
		frame.setJMenuBar(menuBar);
		frame.setSize(BOARD_SIZE,BOARD_SIZE+71);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable( false );

		g = canvas.getGraphics();

		//start the game and draw the checkered pieces on the board.
		startGame();
	}
	
	
	
	
	/**
	 * This actually starts the game, it creates a Board object, and calls other methods addBoard() and addPieces()
	 * @see addBoard()
	 * @see addPieces()
	 */
	public static void startGame() {
		//create a new game board
		board = new Board(NUM_PIECES, NUM_PIECES);	

		frame.paintComponents(g);

		//pause the game to let the window resize before starting to draw
		try {
			Thread.sleep(60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//add the board and pieces 
		addBoard();
		addPieces();
	}
	
	
	
	/**
	 * This draws the checkered pieces to the Canvas.
	 */
	public static void addBoard() {
		int x = 0, y = 0;

		//draw the appropriate square for each square object
		for(Square[] squaresRow: board.getBoard()) {
			for(Square square: squaresRow) {
				//if light then draw a light square
				if(square.getColor() == Colors.Color.LIGHT)
					g.setColor(Color.WHITE);
				else 
					g.setColor(Color.BLACK);

				g.fillRect(x, y, WIDTH, HEIGHT);

				//advance the x position
				x = x + WIDTH;
			}

			//advance the y position and reset the x position
			x = 0;
			y = y + HEIGHT;
		}
	}

	
	
	
	/**
	 * This adds draws all of the pieces needed to start the game. This also adds pieces to the Square Objects as well.
	 */
	public static void addPieces() {
		//reset the y variable 
		int x = 0, y = 0;

		//draw in the light pieces
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 8; j++) {


				//draws an outline if necessary
				if(board.getBoard()[i][j].getColor() == Colors.Color.DARK) {

					//add a light piece
					board.getSquare(i,j).addPiece(Colors.Color.LIGHT);

					//draws a light circle
					g.setColor(Color.RED);
					g.fillOval(x + 10, y + 10, WIDTH - 20, HEIGHT - 20);

					//draw a light outline
					g.setColor(Color.WHITE);
					g.drawOval(x + 10, y + 10, WIDTH - 20, HEIGHT - 20);
				}

				//increment the x value
				x = x + WIDTH;
			}

			//resets the x variable and increment the y variable
			x = 0;
			y = y + HEIGHT;
		}

		//reset the y variable 
		y = HEIGHT * 5;

		//draw in the dark pieces
		for (int i = 5; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				//draws an outline if necessary
				if(board.getBoard()[i][j].getColor() == Colors.Color.DARK) {
					//add a dark piece
					board.getSquare(i,j).addPiece(Colors.Color.DARK);

					//draws a dark circle
					g.setColor(Color.BLACK);
					g.fillOval(x + 10, y + 10, WIDTH - 20, HEIGHT - 20);

					//draw a light outline
					g.setColor(Color.WHITE);
					g.drawOval(x + 10, y + 10, WIDTH - 20, HEIGHT - 20);
				}

				//Increment the x value
				x = x + WIDTH;
			}

			//resets the x variable and increment the y variable
			x = 0;
			y = y + HEIGHT;
		}
	}



	/**
	 * This draws the updated game board on to the canvas.
	 */
	public void updatePieces() {
		int x = 0;
		int y = 0;

		//go through each and every square and draw it's piece on the board
		for (Square[] squaresRow: board.getBoard()) {
			for (Square square: squaresRow) {
				if (square.getPieceColor() == Colors.Color.DARK) {
					//draws a dark circle
					g.setColor(Color.BLACK);
					g.fillOval(x + 10, y + 10, WIDTH - 20, HEIGHT - 20);

					//draw a light outline
					g.setColor(Color.WHITE);
					g.drawOval(x + 10, y + 10, WIDTH - 20, HEIGHT - 20);

				} else if (square.getPieceColor() == Colors.Color.LIGHT) {
					//draws a light circle
					g.setColor(Color.RED);
					g.fillOval(x + 10, y + 10, WIDTH - 20, HEIGHT - 20);

					//draw a light outline
					g.setColor(Color.WHITE);
					g.drawOval(x + 10, y + 10, WIDTH - 20, HEIGHT - 20);

				}



				x = x + WIDTH;
			}

			//reset the x value and increment the y value
			x = 0;
			y = y + HEIGHT;
		}

	}



	
	/**
	 * This will highlight any square with the specified color. 
	 * 
	 * @param row This is the row of the place to be highlighted.
	 * @param col	This is the column of the place to be highlighted.
	 * @param color This is the Color object to set the color for highlighting.
	 */
	public void highlightSquare(int row, int col, Color color) {
		g.setColor(color);

		//draw a couple rectangles to look like a thick outline
		g.drawRect(col * 100, row * 100, 100 , 100);
		g.drawRect((col * 100) + 1, (row * 100) + 1, 98 , 98);
		g.drawRect((col * 100) + 2, (row * 100) + 2, 96 , 96);
		g.drawRect((col * 100) + 3, (row * 100) + 3, 94 , 94);
		g.drawRect((col * 100) + 4, (row * 100) + 4, 92 , 92);
		g.drawRect((col * 100) - 1, (row * 100) - 1, 102 , 102);
	}




	/**
	 * This translates the (x,y) position of the mouse-click and returns a Square object where the user clicked.
	 * 
	 * @param x	This is the x position
	 * @param y	This is the y position
	 * @return This returns the Square that was located at the (x, y) position.
	 */
	public Square getPlaceClicked (int x, int y) {
		//a very elegant way of detecting which piece was clicked.
		for(int i = 0; i < NUM_PIECES; i++) {
			for(int j = 0; j < NUM_PIECES; j++) {

				if((x > j*100 || x == 0) && x <= (j+1)*100 && (y > i*100 || y == 0) && y <= (i+1)*100)
					return board.getSquare(i, j);
			}
		}

		//default action
		return board.getSquare(0, 0);
	}


	
	
	
	/**
	 * Shows all of the possible moves for a piece. 
	 * @param square A square object that contains a piece.
	 */
	public void showPossibleMoves(Square square) {
		//if the selected piece is the current turn and it's occupied
		if (square.getPieceColor() == board.getTurn() && square.isOccupied()) {
			int x = square.getLocation()[0];
			int y = square.getLocation()[1];

			int leftPossible = board.leftPossible(x, y);
			int rightPossible = board.rightPossible(x, y);

			int dx = 1;
			int dy = -1;

			//change the direction if it's a dark turn
			if (square.getPieceColor() == Colors.Color.DARK) {
				dx = -1;
				dy = -1;
			}

			//handle all the left hand moves
			if (leftPossible == 1) {
				//a regular move is possible
				highlightSquare(x + dx, y + dy, Color.BLUE);

			} else if (leftPossible == -1) {
				//a jump is possible
				highlightSquare(x + 2 * dx, y + 2 * dy, Color.GREEN);
			}




			//set dx and dy for the right hand moves
			dx = 1;
			dy = 1;

			//change the direction if it's a dark turn
			if (square.getPieceColor() == Colors.Color.DARK) {
				dx = -1;
				dy = 1;
			}

			//handle all the right hand moves
			if (rightPossible == 1) {
				//a regular move is possible
				highlightSquare(x + dx, y + dy, Color.BLUE);
			} else if (rightPossible == -1) {
				//a jump is possible
				highlightSquare(x + 2 * dx, y + 2 * dy, Color.GREEN);
			}


		}
	}


	/**
	 * empty
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	
	/**
	 * empty
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	
	/**
	 * empty
	 */
	@Override
	public void mouseExited(MouseEvent e) {
	}

	
	/**
	 * empty
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	
	/**
	 * This gets called whenever the user clicks on the canvas. Based on the state of the game it calls methods to highlight pieces, 
	 * select and deselect pieces, and make moves. This also contains logic for alerting who won the game.
	 * 
	 * @see addBoard()
	 * @see updatePieces()
	 * @see Board
	 * @see showPossibleMoves()
	 * @see highlightSquare()
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		Square square = getPlaceClicked(e.getX(), e.getY());
		int x = square.getLocation()[0];
		int y = square.getLocation()[1];
		boolean movePerformed = false;


		//redraw the board
		addBoard();

		//redraw the pieces 
		updatePieces();

		//if another square is selected, and this is a possible move, then make the move.
		if(square != board.getSelectedSquare() && !square.isOccupied() && board.getSelectedSquare() != null) {
			int selectedX = board.getSelectedSquare().getLocation()[0];
			int selectedY = board.getSelectedSquare().getLocation()[1];
			int dx, dy;

			if(board.getSelectedSquare().getPieceColor() == Colors.Color.DARK) {
				dx = -1;
				dy = -1;
			} else {
				dx = 1;
				dy = -1;
			}

			//the proposed move is to the left
			if (y < selectedY) {
				//now test if it's a possible move
				if(board.leftPossible(selectedX, selectedY) == 1 && x == selectedX + dx && y == selectedY + dy) {
					//it's a regular move
					board.moveLeft(selectedX, selectedY);
					movePerformed = true;
				} else if (board.leftPossible(selectedX, selectedY) == -1 && x == selectedX + 2 * dx && y == selectedY + 2 * dy) {
					//its a jump
					board.jumpLeft(selectedX, selectedY);
					movePerformed = true;
				}
			} else if (y > selectedY) {
				if(board.getSelectedSquare().getPieceColor() == Colors.Color.DARK) {
					dx = -1;
					dy = 1;
				} else {
					dx = 1;
					dy = 1;
				}

				//the proposed move is to the right
				if(board.rightPossible(selectedX, selectedY) == 1 && x == selectedX + dx && y == selectedY + dy) {
					//it's a regular move
					board.moveRight(selectedX, selectedY);
					movePerformed = true;
				} else if (board.rightPossible(selectedX, selectedY) == -1 && x == selectedX + 2 * dx && y == selectedY + 2 * dy) {
					//its a jump
					board.jumpRight(selectedX, selectedY);
					movePerformed = true;
				}
			}

			//redraw everything if a move was performed, and switch turns
			if (movePerformed) {
				addBoard();
				updatePieces();				
				board.switchTurns();

				if(board.getTurn() == Colors.Color.DARK) {
					status.setText("It is a black turn.");
				} else {
					status.setText("It is a red turn.");
				}
			} 

			//de-select all
			board.deSelectAll();	
		}


		//highlight and select the place clicked, if it's occupied, and it's their turn, oh and it can't already be selected either!
		if(square.isOccupied() && square.getPieceColor() == board.getTurn() && !movePerformed && !square.isSelected()) {
			board.deSelectAll();

			
			highlightSquare(x, y, Color.YELLOW);
			
			
			
			//select the new square
			square.select();

			//highlight the possible moves
			showPossibleMoves(square);
			

		} else {
			//deselect all the places
			board.deSelectAll();
		}
		
		


		//displays the game over message
		if(board.findWinner() == 1)
			JOptionPane.showMessageDialog(frame, "BLACK PLAYER LOSES! BETTER LUCK NEXT TIME!");
		if(board.findWinner() == 2)
			JOptionPane.showMessageDialog(frame, "RED PLAYER LOSES! BETTER LUCK NEXT TIME!");


	}
	

}
