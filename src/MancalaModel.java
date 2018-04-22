package mancala;

public class MancalaModel {
	private int[] pits;
	private int[] pitsClone;
	private int counter;
	private Player player1;
	private Player player2;
	private int stonePerPits;
	private boolean isgameOver;
	private int turnCount;
	
	
	public MancalaModel(){
		
		
	}
	
	/**
	 * Move function in the mancala
	 */
	public void move(){
		
	}
	
	/**
	 * returns true if game is over
	 * @return
	 */
	public boolean gameOver(){
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
