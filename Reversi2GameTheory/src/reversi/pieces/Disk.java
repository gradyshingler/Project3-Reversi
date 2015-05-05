package reversi.pieces;

/**********************************************************
* Disk Class:
* 	Holds data for a disk object which contains a
* 	xPos
* 	yPos
* 	cell
* 
* 	Even an empty position on a board has a disk with cell type empty or wall 
* 	depending on if index is contained within the game board
 **********************************************************/

public class Disk {
	int xPos, yPos;
	Cell cell;
	
	/**********************************************************
	 * Constructor
	 **********************************************************/
	public Disk(int x, int y, Cell type){
		xPos = x;
		yPos = y;
		cell = type;
	}
	
	/**********************************************************
	 * Getters, Setters, toString, and equals
	 **********************************************************/
	public int getxPos() {
		return xPos;
	}
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	public int getyPos() {
		return yPos;
	}
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	public Cell getCell() {
		return cell;
	}
	public void setCell(Cell cell) {
		this.cell = cell;
	}
	public String toString(){
		return "("+xPos+","+yPos+"):"+cell.getVal();
	}
	public boolean equals(Disk o){
		if(this.getxPos() == o.getxPos() && this.getyPos() == o.getyPos() &&
				this.getCell() == o.getCell()){
			return true;
		}
		return false;
	}
	
	/**********************************************************
	 * Flip method toggles cell type
	 **********************************************************/
	public void flip(){
		if(cell == Cell.MINE) {
			cell = Cell.OPPONENT;
		}
		else if(cell == Cell.OPPONENT){
			cell = Cell.MINE;
		} else {
			System.out.println("Disk type error: expecting MINE or OPPONENENT, recieved: "+cell.getVal());
		}
	}
}