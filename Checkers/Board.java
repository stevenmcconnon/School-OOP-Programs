//	Steven McConnon
//	COP 3330 - 001
//	The board class holds the current state of the checkers game.
/**
 * This class holds the current state of the checkers game.
 * @author Steven McConnon
 */
public class Board {
	private Square[][] boardArray;
	private int rows;
	private int cols;
	private Colors.Color turn;




	/**
	 * This constructor creates a new Board Object. 
	 * 
	 * @param rows - This is the number of rows for the checker board, preferably 8.
	 * @param cols - This is the number of columns for the checker board, preferably 8.
	 */
	public Board(int rows, int cols) {
		//start the turn on dark
		this.turn = Colors.Color.DARK;
		this.rows = rows;
		this.cols = cols;

		//initialize our array of squares
		this.boardArray = new Square[rows][cols];

		//this creates a blank board with the proper colors
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if((i%2 == 0 && j%2 == 0) || (i%2 != 0 && j%2 != 0)) {
					this.boardArray[i][j] = new Square(i, j, Colors.Color.LIGHT);
				} else {
					this.boardArray[i][j] = new Square(i, j, Colors.Color.DARK);
				}
			}
		}


	}




	/**
	 * This prints out a text representation of the colors of the board to the console for debugging purposes.
	 */
	public void printBoard() {
		for (Square[] x: boardArray) {
			for(Square y: x){
				System.out.print(y.getColor() + " ");
			}

			System.out.println("");
		}
	}




	/**
	 * This tests whether or not the current piece is on or off the board.
	 * @param row This is the row of the location in question.
	 * @param col This is the column of the location in question.
	 * @return Returns true if the move is off the board.
	 */
	public boolean moveOffBoard(int row, int col) {
		if( row < 0 || col < 0 || row >= this.rows || col >= this.cols)
			return true;
		else 
			return false;
	}





	/**
	 * This method de-selects all squares on the board.
	 */
	public void deSelectAll() {
		//this creates a blank board with the proper colors
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				this.boardArray[i][j].deSelect();
			}
		}
	}





	/**
	 * This function tests the possible movements in the left direction of the piece.
	 * 
	 * @param row This is the row of the current location of the piece.
	 * @param col This is the column for the current location of the piece.
	 * @return This returns 1 for OK to move, -1 for OK to jump, 2 for move off board, and 0 for not possible.
	 */
	public int leftPossible(int row, int col) {
		int dx = 1;
		int dy = -1;

		//switches the moves if it's a dark piece
		if(this.boardArray[row][col].getPieceColor() == Colors.Color.DARK) {
			dx = -1;
			dy = -1;
		}

		if(moveOffBoard(row+dx, col+dy)) {
			return 2;
		} else if (!this.boardArray[row+dx][col+dy].isOccupied()) {
			return 1;
		} else if (!moveOffBoard(row+2*dx, col+2*dy) && !this.boardArray[row+2*dx][col+2*dy].isOccupied() && this.boardArray[row+dx][col+dy].getPieceColor() != this.boardArray[row][col].getPieceColor()) {
			return -1;
		} else {
			return 0;
		}
	}





	/**
	 * This function tests the possible movements in the right direction of the piece.
	 * 
	 * @param row This is the row of the current location of the piece.
	 * @param col This is the column for the current location of the piece.
	 * @return This returns 1 for OK to move, -1 for OK to jump, 2 for move off board, and 0 for not possible.
	 */
	public int rightPossible(int row, int col) {
		int dx = 1;
		int dy = 1;

		//switches the moves if it's a dark piece
		if(this.boardArray[row][col].getPieceColor() == Colors.Color.DARK) {
			dx = -1;
			dy = 1;
		}

		if(moveOffBoard(row+dx, col+dy)) {
			return 2;
		} else if (!this.boardArray[row+dx][col+dy].isOccupied()) {
			return 1;
		} else if (!moveOffBoard(row+2*dx, col+2*dy) && !this.boardArray[row+2*dx][col+2*dy].isOccupied() && this.boardArray[row+dx][col+dy].getPieceColor() != this.boardArray[row][col].getPieceColor()) {
			return -1;
		} else {
			return 0;
		}
	}





	/**
	 * This function takes in the current piece, removes the piece, and adds the piece back in the new location to the left.
	 * 
	 * @param row This is the row of the current location of the piece.
	 * @param col This is the column for the current location of the piece.
	 * @return This returns true if the move was performed successfully, and false if not.
	 */
	public boolean moveLeft(int row, int col) {
		int dx = 1;
		int dy = -1;

		//switches the moves if it's a dark piece
		if(this.boardArray[row][col].getPieceColor() == Colors.Color.DARK) {
			dx = -1;
			dy = -1;
		}

		if(!moveOffBoard(row+dx,col+dy) && leftPossible(row, col) == 1) {

			//get the current piece's color
			Colors.Color color = this.boardArray[row][col].getPieceColor();

			//remove the piece from the square
			this.boardArray[row][col].removePiece();

			//deselect the current piece too
			this.boardArray[row][col].deSelect();

			//add the piece to the new square
			this.boardArray[row+dx][col+dy].addPiece(color);

			return true;
		}

		//the move was off the board
		return false;
	}





	/**
	 * This function takes in the current piece, removes the piece, and adds the piece back in the new location to the right.
	 * 
	 * @param row This is the row of the current location of the piece.
	 * @param col This is the column for the current location of the piece.
	 * @return This returns true if the move was performed successfully, and false if not.
	 */
	public boolean moveRight(int row, int col) {
		int dx = 1;
		int dy = 1;

		//switches the moves if it's a dark piece
		if(this.boardArray[row][col].getPieceColor() == Colors.Color.DARK) {
			dx = -1;
			dy = 1;
		}

		if(!moveOffBoard(row+dx,col+dy) && rightPossible(row, col) == 1) {

			//get the current piece's color
			Colors.Color color = this.boardArray[row][col].getPieceColor();

			//remove the piece from the square
			this.boardArray[row][col].removePiece();

			//deselect the current piece too
			this.boardArray[row][col].deSelect();

			//add the piece to the new square
			this.boardArray[row+dx][col+dy].addPiece(color);

			return true;
		}

		//the move was off the board
		return false;
	}





	/**
	 * This function takes in the current piece, removes the piece, and adds the piece back in the 
	 * left direction of a jump. It also removes the piece that it just jumped.
	 * 
	 * @param row This is the row of the current location of the piece.
	 * @param col This is the column for the current location of the piece.
	 * @return This returns true if the move was performed successfully, and false if not.
	 */
	public boolean jumpLeft(int row, int col) {
		int dx = 2;
		int dy = -2;

		//if it's a dark piece, switch direction
		if(this.boardArray[row][col].getPieceColor() == Colors.Color.DARK) {
			dx = -2;
			dy = -2;
		}


		if(!moveOffBoard(row+dx,col+dy) && leftPossible(row, col) == -1) {

			//get the current piece's color
			Colors.Color color = this.boardArray[row][col].getPieceColor();

			//remove the piece from the square
			this.boardArray[row][col].removePiece();

			//deselect the current piece too
			this.boardArray[row][col].deSelect();

			//add the piece to the new square
			this.boardArray[row+dx][col+dy].addPiece(color);

			//remove the piece in between
			this.boardArray[row+dx/2][col+dy/2].removePiece();


			return true;
		}

		//the move was off the board
		return false;
	}





	/**
	 * This function takes in the current piece, removes the piece, and adds the piece back in the 
	 * right direction of a jump. It also removes the piece that it just jumped.
	 * 
	 * @param row This is the row of the current location of the piece.
	 * @param col This is the column for the current location of the piece.
	 * @return This returns true if the move was performed successfully, and false if not.
	 */
	public boolean jumpRight(int row, int col) {
		int dx = 2;
		int dy = 2;

		//if it's a dark piece, switch direction
		if(this.boardArray[row][col].getPieceColor() == Colors.Color.DARK) {
			dx = -2;
			dy = 2;
		}


		if(!moveOffBoard(row+dx,col+dy) && rightPossible(row, col) == -1) {

			//get the current piece's color
			Colors.Color color = this.boardArray[row][col].getPieceColor();

			//remove the piece from the square
			this.boardArray[row][col].removePiece();

			//deselect the current piece too
			this.boardArray[row][col].deSelect();

			//add the piece to the new square
			this.boardArray[row+dx][col+dy].addPiece(color);

			//remove the piece in between
			this.boardArray[row+dx/2][col+dy/2].removePiece();


			return true;
		}

		//the move was off the board
		return false;
	}





	/**
	 * This adds a piece to the square here.
	 * 
	 * @param row This is the row where the piece is to be added.
	 * @param col This is the column where the piece is to be added.
	 * @param color This is the color of the piece to be added, light or dark.
	 */
	public void addPiece(int row, int col, Colors.Color color) {
		this.boardArray[row][col].addPiece(color);
	}





	/**
	 * This gets the entire board for use outside the class.
	 * @return Returns a two-dimensional array of Square Objects.
	 */
	public Square[][] getBoard() {
		return this.boardArray;
	}





	/**
	 * This gets a single Square object.
	 * 
	 * @param row The row of the square to get.
	 * @param col The column of the square to get.
	 * @return This returns a square object.
	 */
	public Square getSquare(int row, int col) {
		return this.boardArray[row][col];
	}





	/**
	 * This method finds out whose turn it is.
	 * @return Returns the color of whoever's turn it is.
	 */
	public Colors.Color getTurn() {
		return this.turn;
	}





	/**
	 * In this game, only one square can be selected. This method finds out which Square object is selected.
	 * @return Returns the selected square.
	 */
	public Square getSelectedSquare() {
		for (Square[] squareRow: this.boardArray) {

			for (Square square: squareRow) {
				//returns the selected square
				if(square.isSelected()) {
					return square;
				}
			}

		}

		//default
		return null;
	}





	/** 
	 * This function just switches turns from Dark to Light and vise-versa.
	 */
	public void switchTurns() {
		if (this.turn == Colors.Color.DARK)
			this.turn = Colors.Color.LIGHT;
		else
			this.turn = Colors.Color.DARK;
	}




	/**
	 * This basically gets the score of the game.
	 * 
	 * @return Returns an array of pieces left in the format of {dark, light}.
	 */
	public int[] checkPiecesLeft() {
		int pieces[] = {0,0};

		for (Square[] squareRow: this.boardArray) {

			for (Square square: squareRow) {
				//returns the selected square
				if(square.getPieceColor() == Colors.Color.DARK) {
					pieces[0] ++;
				} else if (square.getPieceColor() == Colors.Color.LIGHT) {
					pieces[1] ++;
				}
			}

		}

		return pieces;
	}

	
	
	
	
	/**
	 * This finds out if we have a winner and who it is.
	 * @return Returns 1 for LIGHT, 2 for DARK, 0 for no winner, and 3 for DRAW
	 */
	public int findWinner() {
		boolean notDark = false;
		boolean notLight = false;
		
		for (Square[] squareRow: this.boardArray) {

			for (Square square: squareRow) {
				int x = square.getLocation()[0];
				int y = square.getLocation()[1];
				
				if((this.leftPossible(x, y) == 1 || this.leftPossible(x, y) == -1 || this.rightPossible(x, y) == 1 || this.rightPossible(x, y) == -1) && square.getPieceColor() == Colors.Color.DARK) {
					notDark = true;
				}
				
				if((this.leftPossible(x, y) == 1 || this.leftPossible(x, y) == -1 || this.rightPossible(x, y) == 1 || this.rightPossible(x, y) == -1) && square.getPieceColor() == Colors.Color.LIGHT) {
					notLight = true;
				} 
			}

		}
		
		if(notDark == false && notLight == true)
			return 1;
		else if (notLight == false && notDark == true)
			return 2;
		else if (notDark == true && notDark == true)
			return 0;
		else 
			return 3;

	}


}
