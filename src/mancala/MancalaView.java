import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Mancala game UI
 */
public class MancalaView {
    private MancalaModel model;
    private ArrayList<JLayeredPane> pitPanes = new ArrayList<>();

    //todo: These should be defined by and retrieved from the model
    private static final int PITS_PER_ROW = 6;  //pits in a row, NOT counting mancala
    private static final int PIT_ROWS = 2;         //some variants have more than 2 rows, but only 2 rows supported at this time
    private static final int PIT_TOTAL_COUNT = PITS_PER_ROW * PIT_ROWS + 2;

    public void setModel(MancalaModel model) {
        this.model = model;
    }

    /**
     * Presents a Mancala game GUI
     */
    /* UI layers:
     * JFrame gameFrame - GridBagLayout (1x3)
     * +-- JLabel Title
     * +-- JPanel gridPanel - GridBagLayout (8x2)
     *   +-- JLayeredPanel pitContainer
     *     +-- RoundedButton pitButton
     *       +-- MouseListener
     *     +-- Stones
     *   +-- ArrayList<Pit>
     * +-- JPanel statusPanel
     */
    public MancalaView(){
        /*/**************************************************
         * Game JFrame (parent window)
         ***************************************************/
        JFrame gameFrame = new JFrame();
        gameFrame.setTitle("Mancala");
        gameFrame.setLayout(new GridBagLayout());

        //gameFrameGBC is used when adding every section.
        GridBagConstraints gameFrameGBC = new GridBagConstraints();
        //Constraints below are the same for all cells
        gameFrameGBC.fill = GridBagConstraints.BOTH;
        gameFrameGBC.weightx = .5;
        gameFrameGBC.gridx = 0;    // Outer structure of game window is only one column

        /*/**************************************************
         * Game title
         ***************************************************/
        gameFrameGBC.weighty = 0.0;
        gameFrameGBC.gridy = 0;  //Assign for each section
        gameFrameGBC.insets = new Insets(5, 0, 10, 0); // add blank space under title

        // Components ------------------------------
        JLabel gameTitle = new JLabel("MANCALA", JLabel.CENTER);
        gameTitle.setFont(new Font("Serif", Font.PLAIN, 30));

        // Add components to game frame ------------------------------
        gameFrame.add(gameTitle, gameFrameGBC);

        /*/**************************************************
         * Game board
         ***************************************************/
        gameFrameGBC.weighty = 1.0;
        gameFrameGBC.gridy += 1;
        gameFrameGBC.insets = new Insets(0,0,0,0);  // Reset insets

        //Move these to be class variables?
        //LayeredPane -> JPanel (whole board) -> JPanelSquare x1000
        JLayeredPane layeredPane = new JLayeredPane();
        JPanel gridPanel = new JPanel(new GridBagLayout());  //The board itself, 8x2 grid
        ArrayList<JPanel> pitPanels = new ArrayList<>();


        GridBagConstraints gridPanelGBC;
        gridPanelGBC = new GridBagConstraints();
        gridPanelGBC.fill = GridBagConstraints.BOTH;
        gridPanelGBC.weightx = .5;
        gridPanelGBC.weighty = .5;
        gridPanelGBC.insets = new Insets(5, 5, 5, 5);

        // For each pit, including mancala
        for (int i = 0; i < PIT_TOTAL_COUNT; i++){
            gridPanelGBC.gridy = pitToPoint(i).y;
            gridPanelGBC.gridx = pitToPoint(i).x;
            gridPanelGBC.gridheight = (i == 6 || i == 13) ? 2 : 1; // Mancala are extra high

            JPanel pitPanel = new PitPanel(i);      //Create new pit JPanel
            pitPanels.add(i, pitPanel);             //Add pit panel to arraylist
            gridPanel.add(pitPanel, gridPanelGBC);  //Add pit panel to game board grid panel
        }

        layeredPane.add(gridPanel, JLayeredPane.DEFAULT_LAYER);
        // pitPanels.get(3).add(???);

        // Add components to game frame
        gameFrame.add(layeredPane, gameFrameGBC);

        /*/**************************************************
         * Game status panel
         ***************************************************/
        gameFrameGBC.weighty = 0.0;
        gameFrameGBC.gridy += 1;
        gameFrameGBC.insets = new Insets(5,0,0,0);  // Reset insets
        JPanel statusPanel = new JPanel(new GridBagLayout());
        gameFrame.add(statusPanel, gameFrameGBC);

        GridBagConstraints statusPanelGBC = new GridBagConstraints();
        statusPanelGBC.insets = new Insets(5, 5, 5, 5);
        statusPanelGBC.fill = GridBagConstraints.BOTH;
        statusPanelGBC.weightx = .5;
        statusPanelGBC.weighty = .5;

        statusPanelGBC.gridx = 0;

//        Player p = this.model.getsPlayerTurn();
//        String name = p.getName();
        statusPanelGBC.weightx = 1.0;  // Take all available space so that label centers in empty space
        statusPanelGBC.anchor = GridBagConstraints.CENTER;
        statusPanel.add(new JLabel("_'s Turn", JLabel.CENTER), statusPanelGBC);
        //undoCount = new JLabel(p.getName() + "'s Undo Count:"  + p.getnumOfUndos());

//        gbc.gridx++;
//        gbc.ipadx = 100;
//        statusPanel.add(new JLabel(""), gbc);
//        gbc.ipadx = 0;

        statusPanelGBC.gridx++;
        statusPanelGBC.weightx = 0.0;  // Don't take any extra space
        statusPanelGBC.fill = GridBagConstraints.NONE;
        statusPanelGBC.anchor = GridBagConstraints.LINE_END;
        statusPanel.add(new JButton("Player _ Undo"), statusPanelGBC);


        // --------------------------------------------------
        // Final gameFrame setup
        // --------------------------------------------------
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.pack();
        gameFrame.setVisible(true);
    }

    /**
     * Determines the pit's position in the game board grid (# 6 and 13 are double height, taking up 2 rows)
     *         0    1    2    3    4    5    6    7 <-- gridx
     * gridy +----+----+----+----+----+----+----+----+
     *   0   | 13 | 12 | 11 | 10 | 9  | 8  | 7  | 6  | <-- pits 6 and 13 are at gridy = 0
     *       |    +----+----+----+----+----+----+    |
     *   1   |    | 0  | 1  | 2  | 3  | 4  | 5  |    |
     *       +----+----+----+----+----+----+----+----+
     * @param pit index of pit to get coordinates for
     * @return Point representing gridx and gridy for pit
     */
    private Point pitToPoint(int pit){
        return new Point(
            pit <= PITS_PER_ROW ? pit + 1 : (PIT_TOTAL_COUNT / 2) - pit + PITS_PER_ROW,  // y coordinate
                pit < PITS_PER_ROW ? 1 : 0   // x coordinate
        );
    }

    /**
     * Helper function for observer/MVC pattern update function.
     * Draws stones on all pits based on game state data provided by model.
     * @param pits
     */
    public void drawStones(ArrayList<Pit> pits) {
        for (int p = 0; p < PIT_TOTAL_COUNT; p++) {  // For each pit on the game board
            Pit pit = pits.get(p);  // Get pit object
            int stoneCount = pit.getNumbOfStones();  // Get # of stones in this pit
            JLayeredPane pitPane = pitPanes.get(p);
            for (int i = 0; i < stoneCount; i++) {
                pitPane.add(new JLabel(new PitIcon(10, 10, new Color(0xF5, 0xF5, 0xDC), new Color(0xF5, 0xF5, 0xDC))));
            }

        }
    }

    /**
     * This method is called whenever the observed object (model) is changed, to
     * perform the
     *
     * @param model   the observable object.
     */
    public void update(MancalaModel model) {
        ArrayList<Pit> pits = model.getBoard();
        drawStones(pits);
    }

    class PitPanel extends JPanel{
        private int index;
        private int stones;

        public PitPanel(int index) {
            this.index = index;
            this.stones = 0;
            this.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            //Create a button for the current pit ---------------------
            //Calculate label
            String side = (index <= 6) ? "A" : "B";
            int num = (index <= 6) ? index + 1 : index - 6;
            final String pitLabel = side + ((index == 6 || index == 13) ? "" : num);

            //Create button
            RoundedButton pitButton = new RoundedButton(pitLabel);
            pitButton.setBackground(Color.WHITE);
            this.add(pitButton, gbc);

            //todo: Make pits belonging to other player unclickable
//            final int selectedPit = i;
//            //if(t==1){pit.setEnabled(false);}
//            pitButton.addActionListener(e -> {
//                Player p = model.getsPlayerTurn();
//                model.move(selectedPit, p);
//            });
        }

        @Override
        public Component add(Component comp) {
            stones++;
            return super.add(comp);
        }

        @Override
        public void remove(Component comp) {
            stones--;
            super.remove(comp);
        }
    }

}
