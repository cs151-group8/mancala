package mancala;

import java.util.ArrayList;

public class Player {

    public int numOfStoneInMancala;
    public int numOfUndos;
    public String name; //Player 1, Player 2
    private ArrayList<Pit> playerPits;
    private int Mancala = 0;// will keep the index of the player's Mancala

    /**
     * Constructs the Player's Class
     *
     * @param n
     */
    public Player(String n, int mancala) {
        name = n;
        numOfUndos = 0;
        numOfStoneInMancala = 0;
        playerPits = new ArrayList<Pit>();// this will find if a particular pit is belong to which player
        Mancala = mancala;
    }


    /**
     * This method return the Mancala index of the player
     */
    public int getMancala() {

        return Mancala;
    }


    /**
     * this increases the number of stone in a player's mancala by one.
     *
     * @param
     */
    public void addStoneToMancala() {
        numOfStoneInMancala++;
    }

    /**
     * this method gets the number of stone in each players mancala.
     * it will be used to find the winner.
     *
     * @return the number of stone in the mancala
     */
    public int getNumOfStones() {
        return numOfStoneInMancala;
    }

    /**
     * Counts number of undos a player has made. (can have up to three for each move)
     */
    public void addUndo() {
        numOfUndos++;
    }

    public int getnumOfUndos() {
        return numOfUndos;
    }

    /**
     * Returns the name of the player
     *
     * @return the name of the player. eg. Player 1 or Player 2
     */
    public String getName() {
        return name;
    }

}
