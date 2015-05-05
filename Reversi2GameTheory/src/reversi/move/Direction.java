package reversi.move;

public enum Direction {
	N(0), NE(1), E(2), SE(3), S(4), SW(5), W(6), NW(7);
		
	public int dir;

	Direction(int n) {
		dir = n;
	}
	
	public static Direction getDir(int i) {
		switch (i) {
		case 0:
			return Direction.N;
		case 1:
			return Direction.NE;
		case 2:
			return Direction.E;
		case 3:
			return Direction.SE;
		case 4:
			return Direction.S;
		case 5:
			return Direction.SW;
		case 6:
			return Direction.W;
		case 7:
			return Direction.NW;
		default:
			return Direction.N;
		}
	}
}
