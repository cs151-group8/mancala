
public class Pit {

    private int numberOfStone = 0;

    /**
     * constructs the pit class
     * @param numberOfStone
     */
    public Pit(int numberOfStone) {

        this.numberOfStone = numberOfStone;
    }

    /**
     * get number of stones in a certain pit
     * @return
     */
    public int getNumbOfStones() {
        return numberOfStone;
    }

    /**
     * set the number of stone in the pit
     * @param n
     */
    public void setNumbOfStone(int n) {
        this.numberOfStone = n;
    }

    /**
     * add a stone in a pit
     */
    public void addAStone() {

        numberOfStone = numberOfStone + 1;
    }
    
    /**
     * add a certain number of stone in a pit
     * @param n is number to add to pit
     */
    public void addMoreStones(int n){
    	numberOfStone = numberOfStone + n;
    }

}
