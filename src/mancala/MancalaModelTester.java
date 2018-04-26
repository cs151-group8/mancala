package mancala;
import java.util.ArrayList;

public class MancalaModelTester {


    public static void main(String[] args) {

        MancalaModel mm = new MancalaModel("Player1",3);


        System.out.println();
        mm.printBoard();
        System.out.println();
        System.out.println("Copy of Array\n");
        mm.printCopyOfBoard();
        System.out.println();
        System.out.println("State of Game board after one Player turn \n");
        mm.printBoard();

    }// main done here



}