import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.*;

public class MancalaView {
    private int mancalaWidth = 200;
    private int pitWidth = mancalaWidth;
    private static final Color GREEN = new Color(200, 255, 200);
    private static final Color BLUE = new Color(200, 200, 255);

    public MancalaView() {
        JFrame frame = new JFrame("Mancala");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // note that a JFrame's contentPane uses BorderLayout by default
        frame.getContentPane().add(new Pit(Color.pink, mancalaWidth, 400), BorderLayout.WEST);
        frame.getContentPane().add(new RowsContainer(GREEN, pitWidth * 6, pitWidth * 2), BorderLayout.CENTER);
        frame.getContentPane().add(new Pit(BLUE, mancalaWidth, 400), BorderLayout.EAST);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

}

class Pit extends JPanel {
    private static final float FONT_POINTS = 24f;
    private int prefW;
    private int prefH;

    public Pit(Color color, int prefW, int prefH) {
        setBackground(color);
        this.prefW = prefW;
        this.prefH = prefH;

        // GBL can be useful for simply centering components
        setLayout(new GridBagLayout());
        String text = String.format("%d x %d", prefW, prefH);
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(FONT_POINTS));
        label.setForeground(Color.gray);
        add(label);
    }

class MultiRowContainer extends JPanel {
    private static final float FONT_POINTS = 24f;
    private int prefW;
    private int prefH;

    public MultiRowContainer(int rows, Color color, int prefW, int prefH) {
        setBackground(color);
        this.prefW = prefW;
        this.prefH = prefH;

        // GBL can be useful for simply centering components
        setLayout(new GridBagLayout());
        String text = String.format("%d x %d", prefW, prefH);
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(FONT_POINTS));
        label.setForeground(Color.gray);
        add(label);
    }



    @Override
    public Dimension getPreferredSize() {
        return new Dimension(prefW, prefH);
    }
}
