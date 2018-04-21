/**
 * Mancala Game!
 * A simple two-row Mancala game
 */
public class MancalaTest
{
    /**
     * Creates a mancala game UI
     */
    public static void main(String[] args)
    {
        Mancala model = new Mancala();
        MancalaView view = new MancalaView();
        MancalaController controller = new MancalaController(model, view);

        model.addObserver(view);
    }

}
