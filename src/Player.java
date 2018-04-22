package mancala;

public class Player {
	
	public int numOfStoneInMancala;
	public int numOfUndos;
	public String name; //Player 1, Player 2
	
	/**
	 * Constructs the Player's Class
	 * @param n
	 */
	public Player(String n){
		name = n;
		numOfUndos=0;
		numOfStoneInMancala=0;
	}
	
	
	/**
	 * this increases the number of stone in a player's mancala by one.
	 * @param n
	 */
	public void addStone(){
		numOfStoneInMancala++;
	}
	System.out.println("This is a test");
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
	
	/**
	 * Returns the name of the player
	 * @return the name of the player. eg. Player 1 or Player 2
	 */
	public String getName(){
		return name;
	}
	
}
