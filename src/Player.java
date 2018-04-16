package mancala;

public class Player {
	
	public int numOfStoneInMancala;
	public int numOfUndos;
	
	public Player(){
		
	}
	
	
	/**
	 * this increases the number of stone in a player's mancala by one.
	 * @param n
	 */
	public void addStone(){
		numOfStoneInMancala++;
	}
	
	/**
	 * this method gets the number of stone in each players mancala.
	 * it will be used to find the winner.
	 * @return the number of stone in the mancala
	 */
	public int getNumOfStones(){
		return numOfStoneInMancala;
	}
	
	/**
	 * Counts number of undos a player has made. (can have up to three for each move)
	 */
	public void addUndo(){
		numOfUndos++;
	}
	
	public int getnumOfUndos(){
		return numOfUndos;
	}
	
}
