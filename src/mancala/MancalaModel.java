package mancala;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Aye Swe
 */

public class MancalaModel {

    private int StoneInHand = 0;// record the stone in hand of the player in current
    private ArrayList<Pit> board = new ArrayList<Pit>();// representation of a macala board with Pit objs
    private Player player1 = null;
    private Player player2 = null;
    private final int NUMBER_OF_STONE_PER_PIT = 3;
    private ArrayList<Pit> copyOfBoardBeforeAPlayer = null;
    private String getPlayerName = null;


    public MancalaModel(String name, int pit) {
        player1 = new Player("Player1", 7);// object of player with their desinated Macala
        player2 = new Player("Player2", 13);// object of player with their desinated Macala
        copyOfBoardBeforeAPlayer = new ArrayList<Pit>();// a copy of board to tract at least last step of the board
        populateBoard(board, NUMBER_OF_STONE_PER_PIT);// fill the board with object of pit and stone inside
        copyTheBoard(copyOfBoardBeforeAPlayer, board);//This array copy the original board
        Pit selectedPit = selectPit(pit);// This is just testing this should come from the view user input( Rember array start from 0 so, pit # 1 in array is pit # 2)
        getPlayerName = name;

        moveGame(selectedPit, CheckWhichPlayer(getPlayerName));
        //Pit selectedPit = selectPit(5);// This is just testing this should come from the view user input( Rember array start from 0 so, pit # 1 in array is pit # 2)

    }

    public Player CheckWhichPlayer(String players) {


        if (players.equalsIgnoreCase("player1")) {
            System.out.println("I am player 1");
            return player1;

        }
        if (players.equalsIgnoreCase("player2")) {
            System.out.println("I am player 2");
            return player2;
        }
        return null;
    }


    /*
     * This function will copy the original board before the game start
     * @para Pit board
     */
    public void copyTheBoard(ArrayList<Pit> copy, ArrayList<Pit> original) {

        for (int i = 0; i < 14; i++) {
            if (i == 6) {
                copy.add(new Pit(0));
                i++;

            }
            if (i == 13) {
                copy.add(new Pit(0));

                break;
            }
            copy.add(new Pit(original.get(i).getNumbOfStone()));
        }
    }

    /**
     * This function poupulate with Pit objects and choice of number of stone
     *
     * @param starter
     * @param numOfStone
     */

    public void populateBoard(ArrayList<Pit> starter, int numOfStone) {

        for (int i = 0; i < 14; i++) {
            if (i == 6) {
                starter.add(new Pit(0));
                i++;
            }
            if (i == 13) {
                starter.add(new Pit(0));
                break;
            }
            starter.add(new Pit(numOfStone));
        }
    }

    /**
     * This function test the # of stones in the pit, after using move the game function
     *
     * @param
     */
    public void printBoard() {

        for (Pit p : board)
            System.out.print("[" + board.indexOf(p) + "] " + p.getNumbOfStone() + " -->");

    }

    /**
     * This function will print the copy of board for state tracking
     */
    public void printCopyOfBoard() {
        for (Pit p : copyOfBoardBeforeAPlayer)
            System.out.print("[" + copyOfBoardBeforeAPlayer.indexOf(p) + "] " + p.getNumbOfStone() + " -->");


    }

    /**
     * a funtion to check a hand is empty
     *
     * @param ThePit
     * @return
     */

    public boolean IsEmptyPit(int ThePit) {
        return board.get(ThePit).getNumbOfStone() == 0;// not an empty hand

    }

    /**
     * This function will select the desire pit from the player, will substitute with Action Listner from user input
     *
     * @param index
     * @return
     */

    public Pit selectPit(int index) {
        Pit p = board.get(index); // just tempory hard code for the index of pit

        return p;

    }

    /**
     * This will check how many stone in the hand of the player
     *
     * @param p
     * @return
     */

    public int getHand(Pit p) {

        return p.getNumbOfStone();

    }

    /**
     * This function will inform the opponent player's Macala
     *
     * @param player
     * @return
     */


    public int getOtherPlayerMacala(Player player) {
        String name = player.getName();
        int otherPlayerMacala = 0;
        if (name == "Player1") {
            return otherPlayerMacala = player2.getMancala();
        } else
            return otherPlayerMacala = player1.getMancala();

    }

    /**
     * This function will loop the arraylist until no more stone in hand or move function
     *
     * @param pit
     * @param player
     */

    public void moveGame(Pit pit, Player player) {// assume pit is the right pit for the player , program will not check its player's pit

        int otherMacala = getOtherPlayerMacala(player);// get the other opponent's Macala not to drop the stone
        StoneInHand = pit.getNumbOfStone();//grap the stone from the pit
        pit.setNumbOfStone(0);//empty the pit
        boolean freeTurn = false;//set the free turn for the player
        int CurrentIndex = board.indexOf(pit);
        int StartIndex = CurrentIndex;// this will replaced in the loop to go back to the beginning of the Array index
        int loopCount = StartIndex + 1;

        while (StoneInHand != 0 && loopCount < board.size()) {// if there is no stone in the

            // check if loopCount is other player's Macalar
            if (loopCount == otherMacala) {// otherMacala can be 7 or 13
                // check if the last index of array
                if (loopCount == board.size() - 1) {
                    loopCount = 0; // reset the loopCount to the beginning of the array, notice at the end of the while there is +1 added
                }
                loopCount = loopCount + 1;// if it is pass that index, if not drop the stone in the Macala

            }

            if (loopCount == player.getMancala()) {// player1 Macalar
                player.addStoneToMancala();
                Pit currentPit = board.get(loopCount);// get current pit's obj in this index

            }

            // check  if the last stone is drop to the empty pit or the current player's pit, player can take the opponent's opposite direction pit's all stone

            if (StoneInHand == 1 & loopCount == player.getMancala()) {// if the last stone is last one in the hand check if the next pit is player's Macala

                freeTurn = true; //set free turn flag
            }

            // add one more stone in the current pit

            Pit currentPit = board.get(loopCount);// get current pit's obj in this index
            currentPit.addAStone();// add a stone to the pit

            // check if the last index of array
            if (loopCount == board.size() - 1) {
                System.out.println("This is end of the array");
                loopCount = -1; // reset the loopCount to the beginning of the array, notice at the end of the while there is +1 added
            }
            StoneInHand = StoneInHand - 1;
            loopCount = loopCount + 1;

        }

    }

}
