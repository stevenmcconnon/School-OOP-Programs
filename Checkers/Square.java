//	Steven McConnon
//	COP 3330 - 001
//	This class creates and stores information about each game square.
/**
 * This class creates and stores information about each game square.
 * @author stevenmcconnon
 *
 */
public class Square {
	private Colors.Color squareColor;
	private boolean hasLightPiece;
	private boolean hasDarkPiece;
	private int row;
	private int col;
	private boolean currentlySelected;

	
	
	
	
	/**
	 * Creates a new empty square. 
	 * 
	 * @param row The location of the row of this new square.
	 * @param col The location of the column of this new square.
	 * @param squareColor The color that we want this new square to be.
	 */
	public Square(int row, int col, Colors.Color squareColor) {
		this.row = row;
		this.col = col;
		this.squareColor = squareColor;
		this.hasLightPiece = false;
		this.hasDarkPiece = false;
		this.currentlySelected = false;
	}
	
	
	

	/**
	 * Gets the color of this piece.
	 * 
	 * @return Returns a the color of this square.
	 */
	public Colors.Color getColor() {
		return this.squareColor;
	}
	
	
	
	
	
	/**
	 * This returns whether or not the place is occupied
	 * @return Whether or not the square is occupied with a piece.
	 */
	public boolean isOccupied() {
		if(this.hasDarkPiece == true || this.hasLightPiece == true)
			return true;
		else 
			return false;
	}
	
	
	
	
	
	/**
	 * This returns an array(row, column) of the location of the current piece
	 * @return (row, column)
	 */
	public int[] getLocation() {
		int[] arr = {this.row, this.col};
		return arr;
	}
	
	
	
	
	/**
	 * Remove all pieces from the current square
	 */
	public void removePiece() {
		this.hasLightPiece = false;
		this.hasDarkPiece = false;
	}
	
	
	
	
	
	
	/**
	 * This adds a piece to the square.
	 * @param color This is a color from the Colors.Color enum
	 * @return 
	 */
	public void addPiece(Colors.Color color) {
		//first we clear all pieces from the square
		this.removePiece();
		
		//now we add the proper color piece to the square
		if(color == Colors.Color.LIGHT) 
			this.hasLightPiece = true;
		else
			this.hasDarkPiece = true;
	}
	
	
	
	
	/**
	 * Returns a string of information about this square. This is used for de-bugging purposes.
	 */
	public String toString(){
		return "Selected: " + this.isSelected() + ", Row: " + this.row + ", Column: " + this.col + ", Square Color: " + this.squareColor + ", hasLightPiece: " + this.hasLightPiece + ", hasDarkPiece: " + this.hasDarkPiece;
	}
	
	
	
	/**
	 * Returns the color of piece at the place.
	 * @return Returns a color if a piece exists at this square, but null if there is no piece on this square.
	 */
	public Colors.Color getPieceColor() {
		if(this.hasDarkPiece == true)
			return Colors.Color.DARK;
		else if (this.hasLightPiece == true)
			return Colors.Color.LIGHT;
		else
			return null;
	}
	
	
	
	
	/**
	 * Tells whether or not this place is selected.
	 * @return True if the piece is selected, false if not.
	 */
	public boolean isSelected() {
		return this.currentlySelected;
	}
	
	
	
	
	
	/**
	 * 
	 */
	public void select() {
		this.currentlySelected = true;
	}
	
	
	
	
	
	/**
	 * De-selects this square.
	 */
	public void deSelect() {
		this.currentlySelected = false;
	}
	
	
}
