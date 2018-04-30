package mancala;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Aye Swe
 *
 */

public class MancalaModel {

    private int StoneInHand = 0;// record the stone in hand of the player in
    // current
    private ArrayList<Pit> board = new ArrayList<Pit>();// representation of a
    // macala board with Pit
    // objs
    private Player player1 = null;
    private Player player2 = null;
    private int StonePerPit = 0;
    private ArrayList<Pit> copyOfBoardForUndo = null;
    private boolean freeTurn = false;
    private ArrayList<Pit> player1Pits = null;
    ArrayList<Pit> player2Pits = null;

    public MancalaModel() {

    }

    public MancalaModel(int Stone) {
        StonePerPit = Stone;
        player1 = new Player("Player1", 6);// object of player with their
        // desinated Macala
        player2 = new Player("Player2", 13);// object of player with their
        // desinated Macala
        copyOfBoardForUndo = new ArrayList<Pit>();// a copy of board to tract at
        // least last step of the
        // board
        populateBoard(board, StonePerPit);// fill the board with object of pit
        // and stone inside
        player1Pits = new ArrayList<Pit>(board.subList(0, 6));
        player2Pits = new ArrayList<Pit>(board.subList(7, 13));

        copyTheBoard(copyOfBoardForUndo, board);// This array copy the original
        // board

    }

    public void getPlayer1Mancala() {

        System.out.println("player1 Mancala is: " + player1.getMancala());

    }

    public void getPlayer2Mancala() {

        System.out.println("player2 Manacala is: " + player2.getMancala());
    }

    public ArrayList<Pit> getBoard() {

        return board;
    }


    public Player CheckWhichPlayer(String players) {

        if (players.equalsIgnoreCase("player1")) {

            return player1;

        }
        if (players.equalsIgnoreCase("player2")) {

            return player2;
        }
        return null;
    }

    /*
     * This function will copy the original board before the game start
     * just to use as initial game
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
     * This method will keep overiding the temp board that is saved for the undo functions
     * @param copy
     * @param original
     */
    public void OverideTheTempBoard(ArrayList<Pit> copy, ArrayList<Pit> original) {
        for (int i = 0; i < 14; i++) {
            if (i == 6) {
                copy.set(i, (new Pit(original.get(i).getNumbOfStone())));
                i++;

            }
            if (i == 13) {
                copy.set(i, (new Pit(original.get(i).getNumbOfStone())));

                break;
            }
            copy.set(i, (new Pit(original.get(i).getNumbOfStone())));
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
     * This function test the # of stones in the pit, after using move the game
     * function
     *
     * @param
     */
    public void printBoard() {

        for (Pit p : board)
            System.out.print("[" + board.indexOf(p) + "] " + p.getNumbOfStone() + " -->");

    }

    /**
     * Print with parameter
     *
     */

    public void printBoard(ArrayList<Pit> array) {

        for (Pit p : array)
            System.out.print("[" + array.indexOf(p) + "] " + p.getNumbOfStone() + " -->");

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
     * This function will select the desire pit from the player, will substitute
     * with Action Listner from user input
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
        }

        else
            return otherPlayerMacala = player1.getMancala();

    }


    /**
     * This function will loop the arraylist until no more stone in hand or move
     * function
     *
     * @param pit
     * @param player
     */

    public void moveGame(Pit pit, Player player) {// assume pit is the right pit

        int peakNextPit = 0; // back to the beginning of the Array
        int otherMacala = getOtherPlayerMacala(player);// get the other
        // opponent's Macala not
        // to drop the stone
        StoneInHand = pit.getNumbOfStone();// grap the stone from the pit
        pit.setNumbOfStone(0);// empty the pit
        int CurrentIndex = board.indexOf(pit);
        int StartIndex = CurrentIndex;// this will replaced in the loop to go
        // back to the beginning of the Array
        // index
        int loopCount = StartIndex + 1;

        while (StoneInHand != 0 && loopCount < board.size()) {// if there is no
            // stone in the

            // check if loopCount is other player's Macalar
            if (loopCount == otherMacala) {// otherMacala can be 7 or 13
                // check if the last index of array
                if (loopCount == board.size() - 1) {
                    loopCount = 0; // reset the loopCount to the beginning of
                    // the array, notice at the end of the while
                    // there is +1 added
                }
                loopCount = loopCount + 1;// if it is pass that index, if not
                // drop the stone in the Macala

            }

            if (loopCount == player.getMancala()) {// player1 Macalar
                player.addStoneToMancala();
                Pit currentPit = board.get(loopCount);// get current pit's obj
                // in this index

            }
            StoneInHand = StoneInHand - 1;
            peakNextPit = loopCount + 1;
       //     System.out.println("player.getMancala is : " + player.getMancala());
            setLastStonePit(peakNextPit, StoneInHand, player.getMancala());// this
            // will
            // keep
            // track
            // of
            // last
            // stone

            Pit currentPit = board.get(loopCount);// get current pit's obj in
            // this index
            currentPit.addAStone();// add a stone to the pit

            // check if the last index of array
            if (loopCount == board.size() - 1) {
                // System.out.println("This is end of the array");
                loopCount = -1; // reset the loopCount to the beginning of the
                // array, notice at the end of the while there
                // is +1 added
            }

            loopCount = loopCount + 1;

        }

    }

    public void setLastStonePit(int peakNextPit, int Stone, int mancala) {

        //System.out.println("In the setLastStonePint function: ");
       // System.out.println("Stone is: " + Stone);
        //System.out.println("peakNextPit: " + peakNextPit);
        //System.out.println("mancala is : " + mancala);

        if (Stone == 1 & peakNextPit == mancala) {// if the last stone is last
            // one in the hand check if
            // the next pit is player's
            // Macala

            freeTurn = true;
        }


    }

    public void reSetFreeTurnStatus() {

        freeTurn = false;
    }

    public boolean getFreeTurnStatus() {

        return freeTurn;
    }

    public ArrayList<Pit> getPlayer1Pits() {
        return player1Pits;
    }

    public ArrayList<Pit> getPlayer2Pits() {
        return player2Pits;
    }

}// class done
