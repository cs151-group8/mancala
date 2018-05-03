

public class Pit {

    private int numberOfStone = 0;

    public Pit(int numberOfStone) {

        this.numberOfStone = numberOfStone;
    }

    public int getNumbOfStones() {
        return numberOfStone;
    }

    public void setNumbOfStone(int n) {
        this.numberOfStone = n;
    }

    public void addAStone() {

        numberOfStone = numberOfStone + 1;
    }
    
    public void addMoreStones(int n){
    	numberOfStone = numberOfStone + n;
    }

}
