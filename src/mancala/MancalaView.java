import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MancalaView {
    private MancalaModel model;

    //todo: These should be defined by and retrieved from the model
    private int pitsPerRow = 6;  //pits in a row, NOT counting mancala
    private int pitRows = 2;         //some variants have more than 2 rows, but only 2 rows supported at this time
    private int pitTotalCount = pitsPerRow * pitRows + 2;

    public void setModel(MancalaModel model) {
        this.model = model;
    }

    /**
     * Presents a Mancala game GUI
     */
    public MancalaView(){
        // --------------------------------------------------
        // Game JFrame (parent window)
        // --------------------------------------------------
        JFrame gameFrame = new JFrame();
        gameFrame.setTitle("Mancala");
        gameFrame.setLayout(new GridBagLayout());

        //gameFrameGBC is used when adding every section.  Generic gbc object is reused per section.
        GridBagConstraints gameFrameGBC = new GridBagConstraints();
        GridBagConstraints gbc;
        //Constraints are the same for all cells
        gameFrameGBC.fill = GridBagConstraints.BOTH;
        gameFrameGBC.weightx = .5;
        gameFrameGBC.gridx = 0;    // Outer structure of game window is only one column

        // --------------------------------------------------
        // Game title
        // --------------------------------------------------
        gameFrameGBC.weighty = 0.0;
        gameFrameGBC.gridy = 0;  //Assign for each section
        gameFrameGBC.insets = new Insets(5, 0, 10, 0); // add blank space under title
        JLabel gameTitle = new JLabel("MANCALA", JLabel.CENTER);
        gameFrame.add(gameTitle, gameFrameGBC);

        gameTitle.setFont(new Font("Serif", Font.PLAIN, 30));


        // --------------------------------------------------
        // Game board
        // --------------------------------------------------
        gameFrameGBC.weighty = 1.0;
        gameFrameGBC.gridy += 1;
        gameFrameGBC.insets = new Insets(0,0,0,0);  // Reset insets
        JPanel gamePanel = new JPanel(new GridBagLayout());
        gameFrame.add(gamePanel, gameFrameGBC);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = .5;
        gbc.weighty = .5;
        gbc.insets = new Insets(5, 5, 5, 5);

        // For each pit, including mancala
        for (int i = 0; i < pitTotalCount; i++){
            //Determine the pit's position in the grid (# 6 and 13 are double height, taking up 2 rows)
            // 13 12 11 10 9  8  7  6    <-- columns go from 0 to 7 (that is, from pitsPerRow+1 to 0)
            //    0  1  2  3  4  5       <-- pitsPerRow = 6
//            gbc.gridy = (i < pitsPerRow) ? 1 : 0;  //Only pits 0-5 are in the bottom row, else top row
//            gbc.gridx = (i <= pitsPerRow) ? i + 1 : (pitTotalCount / 2) - i + pitsPerRow;  //7-1, 7-2...
            gbc.gridy = pitToPoint(i).y;
            gbc.gridx = pitToPoint(i).x;
            gbc.gridheight = (i == 6 || i == 13) ? 2 : 1; // Mancala are extra high

            //Add a button to the current pit
            //JButton pit = new JButton(i+" ["+board.get(i).getNumbOfStones()+"]");
            String side = (i <= 6) ? "A" : "B";
            int num = (i <= 6) ? i + 1 : i - 6;
            RoundedButton pit = new RoundedButton(side + ((i == 6 || i == 13) ? "" : num ));
            pit.setBackground(Color.WHITE);
            // Disable pits belonging to other player
            final int selectedPit = i;
            //if(t==1){pit.setEnabled(false);}
            pit.addActionListener(e -> {
                Player p = model.getsPlayerTurn();
                model.move(selectedPit, p);
            });
            gamePanel.add(pit, gbc);
            //pits.add(pit);  //Add button to ArrayList
        }

        // --------------------------------------------------
        // Game status panel
        // --------------------------------------------------
        gameFrameGBC.weighty = 0.0;
        gameFrameGBC.gridy += 1;
        gameFrameGBC.insets = new Insets(5,0,0,0);  // Reset insets
        JPanel statusPanel = new JPanel(new GridBagLayout());
        gameFrame.add(statusPanel, gameFrameGBC);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = .5;
        gbc.weighty = .5;

        gbc.gridx = 0;


//        Player p = this.model.getsPlayerTurn();
//        String name = p.getName();
        gbc.weightx = 1.0;  // Take all available space so that label centers in empty space
        gbc.anchor = GridBagConstraints.CENTER;
        statusPanel.add(new JLabel("_'s Turn", JLabel.CENTER), gbc);
        //undoCount = new JLabel(p.getName() + "'s Undo Count:"  + p.getnumOfUndos());

//        gbc.gridx++;
//        gbc.ipadx = 100;
//        statusPanel.add(new JLabel(""), gbc);
//        gbc.ipadx = 0;

        gbc.gridx++;
        gbc.weightx = 0.0;  // Don't take any extra space
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        statusPanel.add(new JButton("Player _ Undo"), gbc);


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
            pit <= pitsPerRow ? pit + 1 : (pitTotalCount / 2) - pit + pitsPerRow,  // y coordinate
                pit < pitsPerRow  ? 1 : 0   // x coordinate
        );
    }

    private void drawStonesInPits(ArrayList<Pit> pits) {
        for (int p = 0; p < pitTotalCount; p++) {  // For each pit on the game board
            Pit pit = pits.get(p);  // Get pit object
            pit.getNumbOfStones();  // Get # of stones in this pit


        }
    }

    /**
     * This method is called whenever the observed object is changed.
     * Stone counts in each pit are updated.
     *
     * @param model   the observable object.
     */
    public void update(MancalaModel model) {
        ArrayList<Pit> pits = model.getBoard();

    }
}


