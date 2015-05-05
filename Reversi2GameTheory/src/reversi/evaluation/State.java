package reversi.evaluation;
import reversi.move.Move;


public class State {
	public State previous;
	int score;
	public State[] children;
	private Move move;
	private boolean player1;
	public int choices;
	//Constructor
	public State(Move move){
		this.move = move;
		
	}
	/*Constructor for the actual state of the game where we will give the state its move options as the form of
	State[] children*/
	public State(){
		
	}
	public State getPrevious(){
		return previous;
	}
	public int getScore(){
		return score;
	}
	public Move getMove(){
		return move;
	}
	public State[] getChlildren(){
		return this.children;
	}
	public void trueScore(){
		//LEAF STATE
		if(children == null){
			////System.out.println("null children in trueScore");
			return;
		}
			
			int best = Integer.MIN_VALUE;
			for(int i = 0; i < this.children.length; i++){
				int s=0;
				State child= children[i];
				if(child != null){
					s = (children[i].move.positionScore - children[i].move.consequenceScore)*-1;
				}else{
					continue;
				}
				if(s>best){
					best = s;
				}
			}
			this.move.consequenceScore = best;
			//System.out.println(this.move);
			////System.out.println("consequence: " + best + ", " + "position" + this.move.positionScore);
			
	}
	
}
