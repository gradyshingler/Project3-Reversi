package reversi.pieces;


public enum Cell {
	EMPTY(0), MINE(1), OPPONENT(2), WALL(3);

	public int cellType;

	private Cell(int n) {
		cellType = n;
	}

	public static Cell getCell(int i) {
		switch (i) {
		case 0:
			return Cell.EMPTY;
		case 1:
			return Cell.MINE;
		case 2:
			return Cell.OPPONENT;
		default:
			return Cell.WALL;
		}
	}

	public int getInt() {
		return cellType;
	}

	public String getVal() {
		return String.valueOf(cellType);
	}
}
