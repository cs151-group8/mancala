
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MancalaModel {

	private int StoneInHand = 0;
	private ArrayList<Pit> board = new ArrayList<Pit>();
	private ArrayList<Pit> copyOfBoard;
	private Player player1;
	private Player player2;
	private int numOfStones;
	private boolean freeTurn = false;
	private ArrayList<Pit> player1Pits;
	private ArrayList<Pit> player2Pits;
	private static ArrayList<ChangeListener> listeners;
	private int turnCount = 0;
	private int lastTurn = 0;
	private int playersTurn;
	private int lastStonePos = 0;

	public MancalaModel() {
		listeners = new ArrayList<ChangeListener>();
	}

	public MancalaModel(int stone) {
		numOfStones = stone;
		player1 = new Player("Player 1", 6);
		player2 = new Player("Player 2", 13);
		populateBoard(board, stone); // used to add number of stones to each
		copyOfBoard = new ArrayList<Pit>();
		// pit;
		player1Pits = new ArrayList<Pit>(board.subList(0, 6));
		player2Pits = new ArrayList<Pit>(board.subList(7, 13));
		listeners = new ArrayList<ChangeListener>();

	}

	public void attach(ChangeListener l) {
		listeners.add(l);
	}

	/**
	 * this will add 3 stones to each pit in the arraylist except pit 6 and 13.
	 * 
	 * @param board
	 *            to populate
	 * @param n
	 *            is the number of stones
	 */
	public void populateBoard(ArrayList<Pit> b, int n) {
		for (int i = 0; i <= 13; i++) {
			if (i == 6 || i == 13) {
				b.add(new Pit(0));
			} else {
				b.add(new Pit(n));
			}
		}

	}

	/**
	 * checks if a certain pit is empty
	 * 
	 * @param pit
	 *            is the pit that is checked
	 * @return true if pit is empty;
	 */
	public boolean isEmptyPit(int pit) {
		return board.get(pit).getNumbOfStones() == 0;
	}

	/**
	 * Gets the number of stone in a players hand.
	 * 
	 * @param pit
	 *            is the pit that is chosen
	 * @return
	 */
	public int getHand(int pit) {
		return board.get(pit).getNumbOfStones();
	}

	/**
	 * this method will overwrite current board with clone
	 * 
	 * @param clone
	 */
	public void overwriteBoard(ArrayList<Pit> clone) {
		board = new ArrayList<Pit>();
		for (Pit p : clone) {
			board.add(p);
		}

	}

	public void makeCopy(ArrayList<Pit> b) {
		copyOfBoard = new ArrayList<Pit>();
		for (int i = 0; i <= 13; i++) {
			copyOfBoard.add(new Pit(b.get(i).getNumbOfStones()));
		}

	}

	/**
	 * returns the board
	 * 
	 * @return board
	 */
	public ArrayList<Pit> getBoard() {
		return board;
	}

	public void move(int pitSelected, Player player) {
		makeCopy(board);

		lastTurn = turnCount;

		if (turnCount != playersTurn) {
			player.setnumOfUndos(0);
		}

		// move number of stones to hand and set the put to 0
		StoneInHand = board.get(pitSelected).getNumbOfStones();
		board.get(pitSelected).setNumbOfStone(0);

		if (getsWhoTurn() == 0 && getsPlayerTurn() == player1) {
			for (int j = 1; j <= StoneInHand; j++) {
				if ((pitSelected + j)%14 == 13) {
					StoneInHand = StoneInHand + 1;
				}
				else{
				board.get((pitSelected + j) % 14).addAStone();
				}
			}
		}
		
		if (getsWhoTurn() == 1 && getsPlayerTurn() == player2) {
			for (int i = 1; i <= StoneInHand; i++) {
				if ((pitSelected + i)%14 == 6) {
					StoneInHand = StoneInHand + 1;
				}
				else{
				board.get((pitSelected + i) % 14).addAStone();
				}
			}
		}

		lastStonePos = pitSelected + StoneInHand % 14;

		freeTurn();
		// players 1 turn
		if (getsWhoTurn() == 0 && getsPlayerTurn() == player1) {
			// if stone lands in an empty pit, it will get all the opponents
			// stone on opposite side
			if (lastStonePos >= 0 && lastStonePos <= 5 && board.get(lastStonePos).getNumbOfStones() == 1) {
				board.get(6).addMoreStones(board.get(12 - lastStonePos).getNumbOfStones());
				board.get(12 - lastStonePos).setNumbOfStone(0);
			}
		}

		// players2 turn
		if (getsWhoTurn() == 1 && getsPlayerTurn() == player2) {
			// if stone lands in an empty pit, it will get all the opponents
			// stone on opposite side
			if (lastStonePos >= 7 && lastStonePos <= 12 && board.get(lastStonePos).getNumbOfStones() == 1) {
				board.get(13).addMoreStones(board.get(12 - lastStonePos).getNumbOfStones());
				board.get(12 - lastStonePos).setNumbOfStone(0);
			}
		}

		if (freeTurn) {
			turnCount = turnCount + 1; // gets free turn
		}

		turnCount++;

		System.out.println("turn count:" + turnCount);

		player1Pits = new ArrayList<Pit>(board.subList(0, 6));
		player2Pits = new ArrayList<Pit>(board.subList(7, 13));

		ChangeEvent event = new ChangeEvent(this);
		for (ChangeListener l : listeners)
			l.stateChanged(event);

	}

	public void freeTurn() {
		if (lastStonePos == 6 && getsWhoTurn() == 0) {
			freeTurn = true;
		} else if (lastStonePos == 13 && getsWhoTurn() == 1) {
			freeTurn = true;
		} else {
			freeTurn = false;
		}
	}

	public boolean getFreeTurn() {
		return freeTurn;
	}

	public void undo(Player p) {
		if (p.numOfUndos < 3 && copyOfBoard != null) {
			p.numOfUndos++;
			overwriteBoard(copyOfBoard);
			turnCount = lastTurn;
			playersTurn = getTurnCount();
			if (freeTurn) {
				resetFreeTurnStatus();
			}

			System.out.println("Name: " + p.getName() + "& Num Of Undos " + p.numOfUndos);

			ChangeEvent event = new ChangeEvent(this);
			for (ChangeListener l : listeners)
				l.stateChanged(event);
		}
	}

	public void resetFreeTurnStatus() {
		freeTurn = false;
	}

	/***
	 * used for testing
	 * 
	 * @param b
	 */
	public void printBoard(ArrayList<Pit> b) {
		String print = "";
		for (Pit p : b) {
			print = print + p.getNumbOfStones() + "   ";
		}
		System.out.println(print);
	}

	public int getsWhoTurn() {
		return (turnCount % 2);
		// 0 for player 1
		// 1 for player 2
	}

	public int getTurnCount() {
		return turnCount;
	}

	public void addTurnCount() {
		turnCount++;
	}

	public Player getsPlayerTurn() {
		if (getsWhoTurn() == 0) {
			return player1;
		}
		return player2;
	}

	public Player getOtherPlayers() {
		if (getsWhoTurn() == 1) {
			return player1;
		}
		return player2;
	}

	/**
	 * set to 0 for player 1 first or 1 for player 2 first
	 * 
	 * @return
	 */
	public void whoGoesFirst(int n) {
		turnCount = n;
	}

	/**
	 * Counts how many stones are in players 1 row
	 * 
	 * @return number of stones
	 */
	public int getStonesInPlayer1Row() {
		int numOfStonesInRow = 0;
		for (int i = 0; i < player1Pits.size(); i++) {
			numOfStonesInRow = numOfStonesInRow + player1Pits.get(i).getNumbOfStones();
		}
		return numOfStonesInRow;

	}

	/**
	 * Counts how many stones are in players 2 Row
	 * 
	 * @return number of stones
	 */
	public int getStonesInPlayer2Row() {
		int numOfStonesInRow = 0;
		for (int i = 0; i < player2Pits.size(); i++) {
			numOfStonesInRow = numOfStonesInRow + player2Pits.get(i).getNumbOfStones();
		}
		return numOfStonesInRow;
	}

	public boolean gameOver() {
		if (getStonesInPlayer1Row() == 0 || getStonesInPlayer2Row() == 0) {
			board.get(6).addMoreStones(getStonesInPlayer1Row());
			board.get(13).addMoreStones(getStonesInPlayer2Row());
			return true;
		} else {
			return false;
		}
	}
	

	public Player getWinner() {
		if (board.get(6).getNumbOfStones() > board.get(13).getNumbOfStones()) {
			return player1;
		} else if (board.get(6).getNumbOfStones() < board.get(13).getNumbOfStones()) {
			return player2;
		} else {
			return null;
		}

	}

}
