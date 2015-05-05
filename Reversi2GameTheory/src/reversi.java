//GoshDangItandWin3000: Grady Shingler (112700508), Andrew Browning (112458331), Jesse Averbukh (112324599)

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import reversi.pieces.Board;


public class reversi {

	final static boolean LOCAL_TEST = true;
	private final static String[] testCases = {"StartBoard"};
	private final static String testName = testCases[0] ;
	//final static File testFile1 = new File("src/testFile.readBoard");
	//final static File testFile2 = new File("src/cornerCase.readBoard");
	//final static File fileToTest = testFile1;

	public static void main(String[] args) {
		//long timeThen = System.currentTimeMillis();
		int[][] rawBoard = new int[8][];
		Board myBoard = null;

		rawBoard = parseBoard();
		
		System.out.println("********** Raw Matrix **********");
		printMatrix(rawBoard);

		myBoard = new Board(rawBoard);
		myBoard.makeMove();
	}

	/*
	 * Prints a 2d int array matrix for testing purposes
	 */
	static void printMatrix(int[][] matrix) {
		int rows = matrix.length;
		System.out.println("Rows:" + rows);
		int cols = matrix[0].length;
		System.out.println("Cols:" + cols);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(String.format("%3d ",matrix[i][j]));
			}
			System.out.println(" ");
		}
	}

	/*
	 * Creates a 2D int array from the Standard input or from the specified test
	 * File
	 */
	private static int[][] parseBoard() {
		Scanner sc;
		if (LOCAL_TEST) {
			try {
				File file =  new File("src/tests/"+ testName + "");
				sc = new Scanner(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				sc = null;
				e.printStackTrace();
			}
		} else {
			sc = new Scanner(System.in);
		}
		
		int[][] boardNums = new int[10][];
		
		for(int i=0;i<10;i++){
			int rowSize = (i+1)*2;
			boardNums[i] = new int[rowSize];
			for(int j=0;j<rowSize;j++){
				boardNums[i][j] = sc.nextInt();
			}
		}
		
		/*
		boardNums[0] = new int[8];
		for (int i = 0; i < 8; i++) {
			boardNums[0][i] = sc.nextInt();
		}
		boardNums[1] = new int[10];
		for (int i = 0; i < 10; i++) {
			boardNums[1][i] = sc.nextInt();
		}
		boardNums[2] = new int[12];
		for (int i = 0; i < 12; i++) {
			boardNums[2][i] = sc.nextInt();
		}
		boardNums[3] = new int[14];
		for (int i = 0; i < 14; i++) {
			boardNums[3][i] = sc.nextInt();
		}
		boardNums[4] = new int[14];
		for (int i = 0; i < 14; i++) {
			boardNums[4][i] = sc.nextInt();
		}
		boardNums[5] = new int[12];
		for (int i = 0; i < 12; i++) {
			boardNums[5][i] = sc.nextInt();
		}
		boardNums[6] = new int[10];
		for (int i = 0; i < 10; i++) {
			boardNums[6][i] = sc.nextInt();
		}
		boardNums[7] = new int[8];
		for (int i = 0; i < 8; i++) {
			boardNums[7][i] = sc.nextInt();
		}*/

		sc.close();

		return boardNums;
	}

}
