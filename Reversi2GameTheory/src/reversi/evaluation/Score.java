package reversi.evaluation;
import reversi.pieces.Board;
import reversi.pieces.Cell;
import reversi.pieces.Disk;

/**********************************************************
 * Score Class:
 * 		contains a 2d array holding generalized position scores for the game reversi
 * 		getRawScore method returns the current score in the score table
 **********************************************************/

public class Score {
	int[][] scores = new int[12][22];
	
	public Score(Board board){
		setScoreTable(); //Sets the raw score board
		for(Disk current: board.whiteDisks){
			adjustChart(current.getxPos(),current.getyPos());
		}
		for(Disk current: board.blackDisks){
			adjustChart(current.getxPos(),current.getyPos());
		}
		//reversi.printMatrix(scores);
	}
	
	public int[][] getChart(){
		return scores;
	}
	
	public int getRawScore(int row, int col){
		return scores[row][col];
	}
	
	public void adjustChart(int row, int col){
		return;
		//throw new UnsupportedOperationException("adjust chart has been deprecated");
		/****************************************
		 * Adjust score chart based on corners
		 ****************************************/
		/*switch (row) {
			case 1:	
				if(col == 4) {
					scores[2][3]*=-1;
					scores[2][4]*=-1;
					scores[2][5]*=-1;
					scores[1][5]*=-1;
				} else if(col == 6){
					scores[2][6]*=-1;
				} else if(col == 7){
					scores[2][7]*=-1;
				} else if(col == 8){
					scores[2][8]*=-1;
				} else if(col == 9){
					scores[2][9]*=-1;
				} else if(col == 11) {
					scores[2][10]*=-1;
					scores[2][11]*=-1;
					scores[2][12]*=-1;
					scores[1][10]*=-1;
				} break;
			case 4: 
				if(col == 1){
					scores[3][2]*=-1;
					scores[4][2]*=-1;
				} else 	if(col == 14){
					scores[3][13]*=-1;
					scores[4][13]*=-1;
				} break;
			case 5: 
				if(col == 1){
					scores[5][2]*=-1;
					scores[6][2]*=-1;
				} else 	if(col == 14){
					scores[5][13]*=-1;
					scores[6][13]*=-1;
				} break;
			case 8: 
				if(col == 4) {
					scores[7][3]*=-1;
					scores[7][4]*=-1;
					scores[7][5]*=-1;
					scores[8][5]*=-1;
				} else if(col == 6){
					scores[7][6]*=-1;
				} else if(col == 7){
					scores[7][7]*=-1;
				} else if(col == 8){
					scores[7][8]*=-1;
				} else if(col == 9){
					scores[7][9]*=-1;
				} else if(col == 11) {
					scores[7][10]*=-1;
					scores[7][11]*=-1;
					scores[7][12]*=-1;
					scores[8][10]*=-1;
				} break;
		}*/
	}
	
	//This is where we will have check for how the score should change based on the current board input
	public int getAdjustScore(int row, int col, Board board){
		return getRawScore(row, col);
	}
	
	private void setScoreTable(){
		int rowLength = 2;
		int wallLength = 10;
		
		for (int row = 0; row < 12; row++) {
			//If above or below board, set just walls
			if (row == 0 || row == 11) {
				for (int col = 0; col < 22; col++) {
					scores[row][col] = 0;
				}
			} else { //Else set walls for wall length value
				for (int col = 0; col < wallLength; col++) {
					scores[row][col] = 0;
				}
				//Then set rest of board to correct input based on 0,1, or 2
				for (int col = wallLength; col < rowLength + wallLength; col++) {
					if(row == 1){//corner piece
						scores[row][col] = 999;
					} else if(row == 10 && (col == 1 || col == 20)){
						scores[row][col] = 999;
					} else {
						scores[row][col] = 1;
					}
				}
				for (int col = (wallLength) + rowLength; col < 22; col++) {
					scores[row][col] = 0;
				}
				rowLength += 2;
				wallLength -= 1;
			}
		}
		/*
		int rowLength = 8;
		int wallLength = 4;
		boolean top = true;
		for (int i = 0; i < 10; i++) {
			if (i == 0 || i == 9) {
				for (int j = 0; j < 16; j++) {
					scores[i][j] = 0;
	
				}
			} else {
				for (int j = 0; j < wallLength; j++) {
					scores[i][j] = 0;
				}
				for (int j = wallLength; j < rowLength + wallLength; j++) {
					//spaces where moves are possible
					if(i == 1 || i == 8){
						if(j == 4 || j == 11) scores[i][j] = 150;
						else if(j==5 || j==10) scores[i][j] = -20;
						else if(j==6 || j==9) scores[i][j] = 15;
						else if(j==7||j==8)	scores[i][j] = 10;
					}
					else if( i==2 || i==7 ){
						if(j==3 || j==12) scores[i][j] = -20;
						else if(j==4 || j==11) scores[i][j] = -25;
						else if(j==5 || j==10) scores[i][j] = -30;
						else if(j==6 || j==9) scores[i][j] = -10;
						else if(j==7 || j==8) scores[i][j] = -5;
					}
					else if(i==3 || i == 6){
						if(j==2 || j==13) scores[i][j] = -15;
						else if(j==3 || j==12) scores[i][j] = 15;
						else if(j==4 || j==11) scores[i][j] = 10;
						else if(j==5 || j==10) scores[i][j] = 12;
						else if(j==6 || j==9) scores[i][j] = 7;
						else if(j==7 || j==8) scores[i][j] = 5;
					}
					else if(i==4 || i==5){
						if(j==1 || j==14) scores[i][j] = 120;
						else if(j==2 || j==13) scores[i][j] = -30;
						else if(j==3 || j==12) scores[i][j] = 10;
						else if(j==4 || j==11) scores[i][j] = 9;
						else if(j==5 || j==10) scores[i][j] = 7;
						else if(j==6 || j==9) scores[i][j] = 5;
						else if(j==7 || j==8) scores[i][j] = 2;
					}
				}
				for (int j = (wallLength) + rowLength; j < 16; j++) {
					scores[i][j] = 0;
				}
				if (top) {
					rowLength += 2;
					if (wallLength == 1)
						top = false;
					wallLength -= 1;
				}
				if (!top) { // not top
					rowLength -= 2;
					wallLength += 1;
				}
			}
		}*/
	}
}