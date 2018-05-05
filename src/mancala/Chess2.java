import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
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
    private static final int RANKS = 8;
    private static final int FILES = 8;
    private static final Color DARK_COLOR = new Color(0, 100, 0);
    private static final Color LIGHT_COLOR = new Color(200, 200, 200);
    private static final Color DARK_PIECE_COLOR = Color.black;
    private static final int SQR_WIDTH = 80;
    private static final int PIECE_WIDTH = 60;
    private static final Dimension SQR_SIZE = new Dimension(SQR_WIDTH, SQR_WIDTH);

    //LayeredPane -> JPanel (whole board) -> JPanelSquare x1000
    private JLayeredPane mainLayeredPane = new JLayeredPane();
    private JPanel board = new JPanel(new GridLayout(RANKS, FILES));
    private JPanelSquare[][] jPanelSquareGrid = new JPanelSquare[RANKS][FILES];

    public Chess2Gui() {
        for (int rank = 0; rank < RANKS; rank++) {
            for (int file = 0; file < FILES; file++) {
                Color bkgd = DARK_COLOR;
                if (rank % 2 == file % 2) {
                    bkgd = LIGHT_COLOR;
                }
                jPanelSquareGrid[rank][file] = new JPanelSquare(rank, file, bkgd);
                jPanelSquareGrid[rank][file].setPreferredSize(SQR_SIZE);
                board.add(jPanelSquareGrid[rank][file]);
            }
        }
        board.setSize(board.getPreferredSize());
        board.setLocation(0, 0);
        mainLayeredPane.add(board, JLayeredPane.DEFAULT_LAYER);
        mainLayeredPane.setPreferredSize(board.getPreferredSize());

        ImageIcon icon = new ImageIcon(ImageUtils2.createImage(PIECE_WIDTH, DARK_PIECE_COLOR));
        JLabel chessPiece = new JLabel(icon, SwingConstants.CENTER);

        jPanelSquareGrid[1][3].add(chessPiece);

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
            Component c = board.getComponentAt(p);
            for (int rank = 0; rank < jPanelSquareGrid.length; rank++) {
                for (int file = 0; file < jPanelSquareGrid[rank].length; file++) {
                    if (jPanelSquareGrid[rank][file] == c) {
                        if (jPanelSquareGrid[rank][file].getChessPiece() != null) {

                            // the jPanelSquares are derived from JPanel but have a
                            // few of their own methods
                            piece = jPanelSquareGrid[rank][file].getChessPiece();
                            jPanelSquareGrid[rank][file].remove(piece);
                            oldRank = rank;
                            oldFile = file;
                            mainLayeredPane.add(piece, JLayeredPane.DRAG_LAYER);
                            int x = p.x - PIECE_WIDTH/2;
                            int y = p.y - PIECE_WIDTH/2;
                            piece.setLocation(x, y);

                            delta = new Point(p.x - x, p.y - y);
                            board.revalidate();
                            mainLayeredPane.repaint();
                            return;
                        }
                    }
                }
            }

            oldFile = -1;
            oldRank = -1;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (piece != null) {
                Point p = e.getPoint();
                int x = p.x - delta.x;
                int y = p.y - delta.y;
                piece.setLocation(x, y);
                mainLayeredPane.revalidate();
                mainLayeredPane.repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (piece != null) {
                JPanelSquare sqr = (JPanelSquare) board.getComponentAt(e.getPoint());
                mainLayeredPane.remove(piece);
                if (sqr == null || !validMove(sqr)) {
                    jPanelSquareGrid[oldRank][oldFile].add(piece);
                } else {
                    sqr.add(piece);
                }

                piece = null;
                delta = null;

                oldRank = -1;
                oldFile = -1;

                board.revalidate();
                mainLayeredPane.repaint();
            }
        }

        // just a pawn's moves
        private boolean validMove(JPanelSquare sqr) {
            int rank = sqr.getRank();
            int file = sqr.getFile();

            if (file != oldFile) {
                return false;
            }
            if (oldRank == 1 && (rank != 2 && rank != 3)) {
                return false;
            }
            if (oldRank != 1 && rank != oldRank + 1) {
                return false;
            }
            return true;
        }
    }
}

@SuppressWarnings("serial")
class JPanelSquare extends JPanel {
    private int rank;
    private int file;
    private JLabel chessPiece = null;

    public JPanelSquare(int rank, int file, Color bkgrnd) {
        this.rank = rank;
        this.file = file;
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
        g2.fillOval(size/4, size/4, size/2, 2*size/3);
        g2.fillOval(size/6, 2*size/3, 2*size/3, size/2);
        g2.dispose();
        return img;
    }

}
