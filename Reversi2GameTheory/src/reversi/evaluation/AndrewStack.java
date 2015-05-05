package reversi.evaluation;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import reversi.move.Move;
import reversi.move.MoveComparator;
import reversi.pieces.Board;
import reversi.pieces.Disk;

public class AndrewStack {
final static int CUT_VAL = 4; 
public static  void calculateConsequencesIt(Board board, List<Move> moves, int depth, int pruneVal, int player){
		// (Setup 1) currList is going to be a stack with the current moves we have available
		Stack<Move> currList = new Stack<Move>();
		for(Move currMove: moves) currList.push(currMove);
		
		// (Setup 2) Create this stackList for moving to the bottom of the tree
		Stack<Stack<Move>> stackList = new Stack<Stack<Move>>();
		stackList.add(currList);
		
		//(Setup 3) Create Double array for storing best moves and pushing to the top;
		int[][] BestMove = new int[depth][CUT_VAL];
			
		//(Setup4) Create Stack to keep track of moves we can effectively undo 
		Stack<Move> backStack = new Stack<Move>();
		
		int currDepth = 0;
		
		/*----------------------------- START! --------------------------------*/
		while( stackList.size() > 0 ){
			//Loop for retreaving leaf nodes
			while( currDepth < depth ){
				//if move list on top of stack is empty, pop off the empty list
				if(stackList.peek().isEmpty()){
					stackList.pop();
				}
				//Pop off "tempMove"  from move list on top of stack and execute
				Move tempMove = stackList.peek().pop();
				System.out.println("tempMove: "+tempMove);
				board.execute(tempMove);
				
				//Store move in backStack "history"
				backStack.push(tempMove);
				
				//Compute best 4 moves after executing "tempMove" as List
				List<Move> nextMoves = board.computePossibleMoves((currDepth%2)+1);	
				for(int i=0; i<nextMoves.size(); i++){ board.getMoveScore(nextMoves.get(i)); }
				Collections.sort(nextMoves, new MoveComparator());
				int small = Math.min(CUT_VAL, nextMoves.size());
				nextMoves = nextMoves.subList(0, small);
				
				//Makes List of 4 best moves after executing "tempMove" a Stack
				Stack<Move> nextStack = new Stack<Move>();
				for(Move currMove: nextMoves) nextStack.push(currMove);
				stackList.push(nextStack);
		
				//Increment Depth
				currDepth++;
			}
			System.out.println("BackList: "+ backStack);
			//Figure out best leaf move score here. whatever hueristic you want
			Stack<Move> LeafMoves = stackList.pop();
			int BestScore = 0;
			for(Move move: LeafMoves ){
				board.execute(move);
				//Calculate Score of Leaf
				
			
			}
			
			/*Move tempMove = backStack.pop();
			tempMove.consequenceScore = tempCons;
			board.undo(tempMove);
			//printStackList(stackList);
			return;
			currDepth--;*/
		}
	}
}
