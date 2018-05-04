// Original source: https://stackoverflow.com/a/4687759

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

public class Chess2 {
    private static void createAndShowUI() {
        JFrame frame = new JFrame("Chess 2");
        frame.getContentPane().add(new Chess2Gui().getMainComponent());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                createAndShowUI();
            }
        });
    }
}

class Chess2Gui {
    private static final int PITS_PER_ROW = 6;  //pits in a row, NOT counting mancala
    private static final int PIT_ROWS = 2;         //some variants have more than 2 rows, but only 2 rows supported at this time
    private static final int PIT_TOTAL_COUNT = PITS_PER_ROW * PIT_ROWS + 2;
    private static final Color DARK_COLOR = new Color(0, 100, 0);
    private static final Color LIGHT_COLOR = new Color(200, 200, 200);
    private static final Color DARK_PIECE_COLOR = Color.black;
    private static final int SQR_WIDTH = 80;
    private static final int PIECE_WIDTH = 60;
    private static final Dimension SQR_SIZE = new Dimension(SQR_WIDTH, SQR_WIDTH);

    private MancalaModel model;

    private JLayeredPane mainLayeredPane = new JLayeredPane();
    private JPanel board = new JPanel(new GridBagLayout());
    //private JPanelSquare[][] jPanelSquareGrid = new JPanelSquare[RANKS][FILES];
    private ArrayList<PitPanel> pitPanels = new ArrayList<>();

    public Chess2Gui() {
        GridBagConstraints gridPanelGBC = new GridBagConstraints();
        gridPanelGBC.fill = GridBagConstraints.BOTH;
        gridPanelGBC.weightx = .5;
        gridPanelGBC.weighty = .5;
        gridPanelGBC.insets = new Insets(5, 5, 5, 5);
        for (int i = 0; i < PIT_TOTAL_COUNT; i++){
            gridPanelGBC.gridy = pitToPoint(i).y;
            gridPanelGBC.gridx = pitToPoint(i).x;
            gridPanelGBC.gridheight = (i == 6 || i == 13) ? 2 : 1; // Mancala are extra high

            //Create pit (panel)
            PitPanel pit = new PitPanel(LIGHT_COLOR);

            //Calculate label for pit button
            String side = (i <= 6) ? "A" : "B";
            int num = (i <= 6) ? i + 1 : i - 6;
            final String pitLabel = side + ((i == 6 || i == 13) ? "" : num);

            //Create button
            RoundedButton pitButton = new RoundedButton(pitLabel);
            pitButton.setBackground(Color.WHITE);

            //Define constraints for components being added to pit panel
            GridBagConstraints pitComponentGBC = new GridBagConstraints();
            pitComponentGBC.fill = GridBagConstraints.BOTH;
            pitComponentGBC.weightx = .5;
            pitComponentGBC.weighty = .5;

            //add button to pit
            pit.add(pitButton, pitComponentGBC, JLayeredPane.DEFAULT_LAYER);
            board.add(pit, gridPanelGBC);
            pitPanels.add(i, pit);
        }
        board.setSize(board.getPreferredSize());
        board.setLocation(0, 0);
        mainLayeredPane.add(board, JLayeredPane.DEFAULT_LAYER);
        mainLayeredPane.setPreferredSize(board.getPreferredSize());

        ImageIcon icon = new ImageIcon(ImageUtils2.createImage(PIECE_WIDTH, DARK_PIECE_COLOR));
        JLabel chessPiece = new JLabel(icon, SwingConstants.CENTER);

        pitPanels.get(3).add(chessPiece);

        MyMouseAdapter mouseAdapter = new MyMouseAdapter();
        mainLayeredPane.addMouseListener(mouseAdapter);
        mainLayeredPane.addMouseMotionListener(mouseAdapter);

    }

    public JComponent getMainComponent() {
        return mainLayeredPane;
    }

    private class MyMouseAdapter extends MouseAdapter {
        private JLabel piece = null;
        private Point delta = null;
        private int oldRank = -1;
        private int oldFile = -1;

        @Override
        public void mousePressed(MouseEvent e) {
            Point p = e.getPoint();
            System.out.println(p);
            Component c = board.getComponentAt(p);
            System.out.println(c);
//            for (int rank = 0; rank < jPanelSquareGrid.length; rank++) {
//                for (int file = 0; file < jPanelSquareGrid[rank].length; file++) {
//                    if (jPanelSquareGrid[rank][file] == c) {
//                        if (jPanelSquareGrid[rank][file].getChessPiece() != null) {
//
//                            // the jPanelSquares are derived from JPanel but have a
//                            // few of their own methods
//                            piece = jPanelSquareGrid[rank][file].getChessPiece();
//                            jPanelSquareGrid[rank][file].remove(piece);
//                            oldRank = rank;
//                            oldFile = file;
//                            mainLayeredPane.add(piece, JLayeredPane.DRAG_LAYER);
//                            int x = p.x - PIECE_WIDTH/2;
//                            int y = p.y - PIECE_WIDTH/2;
//                            piece.setLocation(x, y);
//
//                            delta = new Point(p.x - x, p.y - y);
//                            board.revalidate();
//                            mainLayeredPane.repaint();
//                            return;
//                        }
//                    }
//                }
//            }

            oldFile = -1;
            oldRank = -1;
        }

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
}

@SuppressWarnings("serial")
class PitPanel extends JPanel {
    private int rank;
    private int file;
    private JLabel chessPiece = null;

    public PitPanel(Color bkgrnd) {
        setBackground(bkgrnd);
        setLayout(new GridBagLayout());
    }

    public int getRank() {
        return rank;
    }
    public int getFile() {
        return file;
    }

    @Override
    public Component add(Component c) {
        chessPiece = (JLabel)c;
        return super.add(c);
    }

    @Override
    public void remove(Component comp) {
        chessPiece = null;
        super.remove(comp);
    }

    public JLabel getChessPiece() {
        return chessPiece;
    }
}

class ImageUtils2 {

    public static BufferedImage createImage(int size, Color color) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(color);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillOval(size/3, 0, size/3, size/3);
        //g2.fillOval(size/4, size/4, size/2, 2*size/3);
        //g2.fillOval(size/6, 2*size/3, 2*size/3, size/2);
        g2.dispose();
        return img;
    }

}
