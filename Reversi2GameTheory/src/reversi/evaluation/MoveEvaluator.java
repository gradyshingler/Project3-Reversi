package reversi.evaluation;
import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;

import reversi.move.Move;
import reversi.move.MoveComparator;
import reversi.pieces.Board;
import reversi.pieces.Disk;

/******************************Discovering Future States*****************************/
/*Steps
 *  1) Choose Top k states at every level
 *  2) Only leaves will hold the final scores
 *  3)Bubble up the best choice for P1 by alternating between choosing children
 *  that are best for either P1 or P2 all the way until P1 decides
 *  
 *  Explanation: leaves contain a score which shows how good that state is for 
 *  P1. Higher, the better. Let's assume last row, the leaves, is P2's turn. P2 would 
 *  want the smallest score value. So every parent node of a leave assumes the score
 *  of the lowest score value. Then, we move up and it's P1's turn for this row,
 *  and every parent of of this assumes the highest score it's children. This down back
 *  and forth between P1 and P2 until P1 gets the final choice of choosing the initial 
 *  moves we were given to choose from for P1's turn.  
 *  
 *  We're essentially choosing between
 *  the best future assuming P2 is just as rational as us. If P2 ever messes up, and does 
 *  something irrational like not correctly choosing a move that makes the score smaller, 
 *  we'll only end up with a better score.
 */

public class MoveEvaluator {
	
	/*VARIALBES*/
	private State[] states;//Serves as our "tree" but in array form
	private int numParents;
	private List possibleMoves;
	private Board board;
	int MovesDeep;
	int choices;

	/*CONSTRUCTOR*/
	public MoveEvaluator(int choices, int MovesDeep,  Board board)
	{
		/*FOR SIZE: This is how you calculate the size of full k-ary tree... (K^n - 1)/(K-1) ... we'll 
		 * probably use 4 here for K, but I made it so it work for any k-ary tree of varying height so we
		 *  can change things if needed.   
		 */
		this.MovesDeep = MovesDeep;
		this.choices = choices;
		int size = (int)(    (Math.pow(choices, MovesDeep +1) - 1)   /   (choices - 1)      );
		this.states =new State[size];
		this.board = board;
		
		//Need to iterate though parents later: all the states with children;
		numParents = (int)(Math.pow(choices, MovesDeep)/(choices -1));
		
	}
	private int getNumParents(){
		return this.numParents;
	}
	private int size(){
		return this.states.length;
	}
	//Returns numeric value of the state the move creates
	public void BestMove()
	{
		if(board.possibleMoves == null){
			return;//Ideally we'd want to create some sort of pass Move
		}
		predict();
		getScores();
	}
	private int calcIndex(int Level){
		return (int)(    (Math.pow(choices, Level ) - 1)   /   (choices - 1)      ) ;
	}
	//reseting the board for the next state. Executes and Undo's moves;
	private void next(State current, State next){
		int forward = 0;
		int undo = 0;
		ArrayList<Move> path = new ArrayList<Move>();
		do{
			if(current.getMove() == null){
				continue;
			}
			path.add(next.getMove());
			board.undo(current.getMove());
			undo++;
			current = current.previous;
			next = next.previous;
		}while(current!= next && current.previous!= null); 
		//Stops at common ancestor OR if next is on a new level, stops when at root.
		if(current.previous == null && next.previous != null){
			//account for next, being on next level by executing left of root.
			board.execute(current.children[0].getMove());
			forward++;
		}
		for(int i = path.size() -1; i >= 0; i--){
			board.execute(path.get(i));
			forward++;
		}
		//////////System.out.println("undo: " + undo + "forward " + forward);
	}
	private void predict(){
		int level = 0;
		int state = 0;
		int last = 0;
		int next = 1;
		
		this.states[0] = new State();
	
		//Loop to iterate to every level of the tree
		for(int i = 0; i < this.MovesDeep; i++){
			//////////System.out.println("level "+ i);
			//Loop for iterating through every node at that level
			int limit = calcIndex(i + 1);
			for(state = state; state < limit ; state ++){
				if(states[state] == null){
					//////////System.out.print("wait why");
					continue;
					
				}else if(state > 0){
					next(this.states[last], this.states[state]);
					last = state;
				}
				//sort possible moves so best 4 are in the front;
				java.util.List<Move> moves;
				if(state!=0){
				moves= board.computePossibleMoves((i%2)+1);
				}else{
				moves=board.possibleMoves;
				}
				Collections.sort(moves, new MoveComparator());
				//System.out.println(moves);
				//board.showBoard();
				
				
				State s = this.states[state];
				if(moves.size() < this.choices){
					s.children = new State[moves.size()];
				}else{
					s.children = new State[this.choices];
				}
				for(int c = 0; c < s.children.length; c ++){
					int childIndex = state * this.choices + 1 +c;
					//calc position score and the place best 4 in the state.children
					this.board.execute(moves.get(c));
					this.board.getMoveScore(moves.get(c));
					//System.out.println(state + "," + c + "," + moves.get(c));
					this.board.undo(moves.get(c));
					State child = new State(moves.get(c));
					child.previous =this.states[state];
					//System.out.println(state + ", " + childIndex);
					
					this.states[state].children[c] = child;
					this.states[childIndex] = child;
				}
				
			}
		}
	}
	private void getScores(){
			for(int state =calcIndex(this.MovesDeep+1)-1; state > 0 ; state --){
				
					State s = this.states[state];
					if(s != null){
						//System.out.print("true score for state:" +state+ ", " + "(" + this.states[state].getMove().row +","+ this.states[state].getMove().col +")");
						s.trueScore();
					}else{
						//System.out.println(state +" woah there buddy");
					}
					
			}
			int max = Integer.MIN_VALUE;
			State MAX = null;
			for(State winner: states[0].children){
				int num = winner.getMove().positionScore + winner.getMove().consequenceScore;
				if( num > max){
					max = num;
					MAX = winner;
				}else if(num == max){
					if(winner.getMove().positionScore >= MAX.getMove().positionScore){
						MAX = winner;
					}
				}
			}
			System.out.println(MAX.getMove());
	}
	
	public static void main(String[] args){
		
		//TEST THAT DATA STRUCTURE SIZE IS CORRECT
		MoveEvaluator moveEv = new MoveEvaluator(4, 4, null);
		//////////System.out.println("Full Size: "+ moveEv.size());
		//////////System.out.println("Number of parents: " + moveEv.getNumParents());
		int level = 3;
		int index = moveEv.calcIndex(level);
		//////////System.out.println("Given the level: " + level);
		//////////System.out.println("The starting index is: " + index);
		//moveEv.BestMove();
		moveEv.getScores();
	}

}
