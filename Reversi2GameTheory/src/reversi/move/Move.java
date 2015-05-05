package reversi.move;
/**********************************************************
 * Move Class:
 * 		Holds data representing a typical move on a reversi board
 * 			row
 * 			col
 * 			player
 * 			flips
 **********************************************************/

import java.util.List;
import java.util.TreeMap;

public class Move {
	public int row;
	public int col;
	public int player;// 1 is player1, 2 is player2
	public TreeMap<Direction, Integer> flips;
	public int positionScore = 0;
	public int consequenceScore = 0;
	
	/**********************************************************
	 * Constructor
	 **********************************************************/
	public Move(int p, int r, int c) {
		row = r;
		col= c;
		player = p;
		flips = new TreeMap<Direction, Integer>();
	}
	
	/**********************************************************
	 * Getters, Setters, toString and equals
	 **********************************************************/
	public void addFlips(Direction dir, int num){
		flips.put(dir, num);
	}
	
	public int getPlayer(){
		return player;
	}
	
	public TreeMap<Direction, Integer> getFlips(){
		return flips;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
	public int getConvertedRow() {
		return row;

	}

	public int getConvertedCol() {
		return (col - (10-row));
	}
	
	public String toString() {
		return "<("+row + "," + col+"): P["+positionScore+"] C["+consequenceScore+"]>";
		//return "<("+row + "," + col+"):"+(positionScore+consequenceScore)+"]>";
	}
	
	public boolean equals(Object o) {
		if (o instanceof Move) {
			Move tempMove = (Move) o;
			return (col == tempMove.col && row == tempMove.row);
		} else {
			return false;
		}
	}

	/**********************************************************
	 * compareTo
	 **********************************************************/
	public int compareTo(Move o) {
		if(this.positionScore < o.positionScore) return -1;
		else if(this.positionScore > o.positionScore) return 1;
		else {
			if(this.row < o.row) return -1;
			else if(this.row > o.row) return 1;
			else {
				if(this.col < o.col) return -1;
				else if(this.col > o.col) return 1;
				else return 0;
			}
		}
	}
	
	/**********************************************************
	 * Prints the final move to be received by the game server
	 **********************************************************/
	public void printMove() {
		System.out.println(getConvertedRow() + " " + getConvertedCol());
	}
	
	/**********************************************************
	 * returns the amount of flips this move generates
	 **********************************************************/
	public int getFlipScore(){
		int toReturn = 0;
		for(int curr: flips.values()){
			toReturn+=curr;
		}
		return toReturn;
	}
}
