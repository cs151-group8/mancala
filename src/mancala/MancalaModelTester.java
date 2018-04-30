package mancala;
import java.util.ArrayList;
import java.util.Scanner;

public class MancalaModelTester {



    private static ArrayList<Pit> tempBoard = new ArrayList<Pit>();
    private static String  players=null;
    private static int Stone = 0;
    private static int pitNumber =0;
    private static int turnCount =0;
    private static Player p = null;

    private static boolean undoFlag = false;


    public static void main(String[] args) {

        getStone();
        MancalaModel mm = new MancalaModel(Stone);// get object of MancalaModel

        getPlayer();
        p = mm.CheckWhichPlayer(players);
        int loop= 2;//temp looper to stimulate the game ( Later will perform as Game is over when one of the player's pit shone are all gone
        mm.copyTheBoard(tempBoard, mm.getBoard());// this will overwrite in every hand.
        boolean gameOverFlag = false;

        while (!gameOverFlag){      //(!freeTurn && i<loop)
            mm.OverideTheTempBoard(tempBoard, mm.getBoard());// this will overwrite in every hand.
            System.out.println();
            System.out.println("Player "+ p.getName() + " ");
            getPitNumber();

            Pit selectedPit = mm.selectPit(pitNumber);// This is just testing this should come from the view user input( Rember array start from 0 so, pit # 1 in array is pit # 2)
            //System.out.println("freeTurn is : " + freeTurn);
            mm.moveGame(selectedPit, p);
            mm.printBoard();

            System.out.println();
            System.out.println("This is tempBoard just after copying");
            mm.printBoard(tempBoard);
            /**
             * Here check if UndoFlage true , undoFunction will take over
             *
             */
            System.out.println("");

            UndoController(mm,p);


            turnCount = turnCount +1;// must be here if user did the undo ( will not do anything yet,until it is done to the main)
            System.out.println();
            /**
             * if freeTurn is true, transfering the program control to
             * the freeTurnController with current player and transfering the reference of CurentGameBoard
             */

            if(mm.getFreeTurnStatus()){

                freeTurnController(p,mm.getBoard(),mm);

            }

            String name = p.getName();

            name.toLowerCase();

            // Here just for testing purpose
            System.out.println();
            getWinner(mm);

            p =  mm.CheckWhichPlayer(PlayerTurnGenerator());// here turn of the player is modified


        }// while loop end here

        //if undo is flag is found call this undoFunction to process


        if (IsPlayerPitEmpty(mm,p )){// check if Game is over
            gameOverFlag = true;
            System.out.println("GAME IS OVER");
        }

        System.out.println("Done");

    }// main done here
    /**
     * This method used to reset the flag
     * @param undo
     */
    public static void setUndoFlag(boolean undo){

        // get the event from the undo function button to set up this flag;
        // notify the event to the move function with this flag
        // for tempory testing this will be triggered from the controller class
        undoFlag = undo;

    }

    private static int getPlayer1Mancala(ArrayList<Pit> gameBoard){
        return gameBoard.get(6).getNumbOfStone();

    }

    private static int getPlayer2Mancala(ArrayList<Pit> gameBoard){
        return gameBoard.get(13).getNumbOfStone();

    }


    private static void getWinner(MancalaModel MM ){
        int person1 = getPlayer1Mancala(MM.getBoard());
        int person2 = getPlayer2Mancala(MM.getBoard());

        if (person1 < person2 ){

            System.out.println("Player2 Win");
        }

        if (person1 == person2){
            System.out.println("Game is draw");// no winner

        }
        else System.out.println("Player1 Win");



    }
    /**
     * This method will decide the Player1's pits are empty yet
     * @param MCM
     * @param CuPlayer
     * @return
     */

    private static boolean IsPlayerPitEmpty(MancalaModel MCM,Player CuPlayer ){
        boolean gameOver = false;
        int count1 =0;
        int count2 =0;
        String name = CuPlayer.getName();
        System.out.println("name is " + name);
        name.toLowerCase();
        if (name.equals("Player1")){
            for (Pit p: MCM.getPlayer1Pits()){

                if (p.getNumbOfStone() ==0){
                    count1 ++;

                }

            }
        }
        else{

            for (Pit p: MCM.getPlayer2Pits()){

                if (p.getNumbOfStone() ==0){
                    count2 ++ ;

                }

            }
        }

        if (count1 == 6 || count2 == 6){

            System.out.println("Game over");
            return gameOver = true;
        }
        else System.out.println("Game is not over yet");

        return gameOver;

    }

    /**This method will decide the Players' pits are empty yet
     *
     * @param MCM
     */
    /**
     * This method will take care of the Free Turns
     * @param freeTurnPlayer
     * @param CurrentBoard
     * @param MCL
     */

    private static void freeTurnController(Player freeTurnPlayer, ArrayList<Pit> CurrentBoard, MancalaModel MCL) {

        System.out.println( freeTurnPlayer.getName() + " ");
        getPitNumber();
        System.out.println("PitNumbe is : " + pitNumber);
        Pit selectedPit = MCL.selectPit(pitNumber);// This is just testing this should come from the view user input( Rember array start from 0 so, pit # 1 in array is pit # 2)
        MCL.reSetFreeTurnStatus();
        MCL.moveGame(selectedPit, freeTurnPlayer);
        System.out.println();
        UndoController(MCL,freeTurnPlayer);

    }

    /**
     * This method generate even and odd turn for the regular game (except free turn and undo games)
     * @return
     */
    private static String PlayerTurnGenerator(){
        if (turnCount % 2 == 0){
            return "player1";
        }

        else return "player2";
    }
    /**
     * This method is just getting information from the user
     */
    private static void getPitNumber(){

        System.out.print("please enter pit number such as 0,1,2,3: " );
        Scanner scanPitNumber = new Scanner(System.in);
        pitNumber = (int)scanPitNumber.nextInt();


    }

    private static void getPlayer(){

        Scanner scanPlayer = new Scanner(System.in);
        System.out.print("Enter player 1 or 2:");
        int player = scanPlayer.nextInt();


        if (player == 1){

            players = "Player1";

        }

        else players = "player2";

    }

    private static void getStone(){

        System.out.println("Enter Number of Stone for the Game: " );
        Scanner stone = new Scanner(System.in);
        Stone = stone.nextInt();

    }

    /**
     * this method must be substitude with the event listner button later
     */


    private static void UndoController(MancalaModel MM, Player currentPlayer){

        Scanner undo = new Scanner(System.in);
        System.out.println();
        String undoTheHand = null;
        System.out.println("Do you want Undo your steps: y or n");// These follow 5 sentence must replace with event Listner from the button

        undoTheHand = undo.next();
        undoTheHand = undoTheHand.toLowerCase();
        System.out.println("choice is : "+ undoTheHand);

        while(undoTheHand.equals("y") &(currentPlayer.getnumOfUndos()<4)){

            {	MM.reSetFreeTurnStatus();// this will reset the freeTurn flag if user decided to undo the step

                undoFlag = true;// This would need here but will need in
                currentPlayer.addUndo();
                MM.OverideTheTempBoard(MM.getBoard(),tempBoard );// Curent game board is overridden with the tempBoard(thePrevious board)before this player start
                System.out.println("now the borad is changed back to previous Stage: ");
                System.out.println();
                MM.printBoard(MM.getBoard());
                System.out.println();
                getPitNumber();
                Pit selectedPit = MM.selectPit(pitNumber);// This is just testing this should come from the view user input( Rember array start from 0 so, pit # 1 in array is pit # 2)
                MM.moveGame(selectedPit, currentPlayer);

                setUndoFlag(false); //reset the undoFlag

                MM.printBoard(MM.getBoard());;
                System.out.println();
                System.out.println("Do you want Undo your steps: y or n");// These follow 5 sentence must replace with event Listner from the button

                undoTheHand = undo.next();
                undoTheHand = undoTheHand.toLowerCase();
                System.out.println("choice is : "+ undoTheHand);

            }
            System.out.println("Player is not allowed to undo anymore");

            return;

        }// keep checking for the event listner when the player turn is done

    }

}

