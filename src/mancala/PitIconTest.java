import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PitIconTest {
    public static void main(String[] args) {

        JFrame frame = new JFrame();

        Color shadowColor = new Color(0xE8, 0x68, 0x10);
        PitIcon pi = new PitIcon(80, 100, Color.ORANGE, shadowColor);
        JComponent pitLabel = new JLabel(pi);  //Why does an icon need to be in a label?

        frame.setLayout(new FlowLayout());
        frame.add(pitLabel);

        // Let's go all out and animate the clock
        ActionListener l = event -> pitLabel.repaint();

        // Update every 10 ms so we can show off second hand tick/heartbeat of 1/6 second like the real watch
        Timer t = new Timer(10, l);
        t.start();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
