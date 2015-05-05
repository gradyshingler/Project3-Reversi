package reversi.move;
import java.util.Comparator;

public class MoveComparator implements Comparator<Move> {

	public int compare(final Move one, final Move two) {	
		int oneTotal = one.positionScore+one.consequenceScore;
		int twoTotal = two.positionScore+two.consequenceScore;
		if(oneTotal < twoTotal) return 1;
		else if(oneTotal > twoTotal) return -1;
		else {
			if(one.row < two.row) return 1;
			else if(one.row > two.row) return -1;
			else {
				if(one.col < two.col) return 1;
				else if(one.col > two.col) return -1;
				else return 0;
			}
		}
	}
}