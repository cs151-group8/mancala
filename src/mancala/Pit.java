package mancala;


public class Pit {

    private int numbOfStone = 0;

    public Pit(int numberOfStone) {

        this.numbOfStone = numberOfStone;
    }

    public int getNumbOfStone() {
        return numbOfStone;
    }

    public void setNumbOfStone(int numbOfStone) {
        this.numbOfStone = numbOfStone;
    }

    public void addAStone() {

        numbOfStone = numbOfStone + 1;
    }

}
