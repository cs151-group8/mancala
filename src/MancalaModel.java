package mancala;

public class MancalaModel {
	private int[] pits;
	private int[] pitsClone;
	private int counter;
	private Player player1;
	private Player player2;
	private int stonePerPits=0;
	private boolean isgameOver;
	private int turnCount;
	private ArrayList<Pit> Pits;

	
	
	public MancalaModel(int numOfStone){
		
		stonePerPits = numOfStone;
		Pits = new ArrayList<Pit>();

	}
	
	/**
	 * Move function in the mancala
	 */
	public void move(){
		
	}
	
	/**
	 * returns true if game is over
	 * @return
	 * this function check on if one player of the all pit are empty
	 */
	public boolean IsgameOver(){




	return
	}
		
	/**
	 * Return the winner of the game by counting num of stones in player's mancala
	 * @return the winner
	 */
	public Player getWinner(){


	}

	
	/**
	 * 
	 * @return
	 */
	public int getPitCount(){
		
	}
	
	/**
	 * Keeps track of player's turn
	 * @return 0 for player1's turn and 1 for player2's turn
	 */
	public int getPlayersTurn(){
		return turnCount%2;
	}
}
