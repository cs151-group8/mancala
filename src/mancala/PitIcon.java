import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

public class PitIcon implements Icon{
    private int width;
    private int height;
    private Color pitColor;
    private Color pitShadowColor;

    public PitIcon(int width, int height, Color pitColor, Color pitShadowColor) {
        this.width = width;
        this.height = height;
        this.pitColor = pitColor;
        this.pitShadowColor = pitShadowColor;
    }

    /**
     * Draw the icon at the specified location.  Icon implementations
     * may use the Component argument to get properties useful for
     * painting, e.g. the foreground or background color.
     *
     * @param c a {@code Component} to get properties useful for painting
     * @param g the graphics context
     * @param x the X coordinate of the icon's top-left corner
     * @param y the Y coordinate of the icon's top-left corner
     */
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g;
        double shadowPercent = .9;
        //---------------------------------------------------------------------------
        // Pit
        //---------------------------------------------------------------------------
        //Shadow / full pit size
        g2.setColor(pitColor);
        g2.fill(new RoundRectangle2D.Double(x, y, width, height, width * .2, width * .2));
        //g2.fill(new Ellipse2D.Double(x, y, width, height));
//        //Pit
//        g2.setColor(pitColor);
//        g2.fill(new Ellipse2D.Double(x, y, width*shadowPercent, height*shadowPercent));
    }

    /**
     * Returns the icon's width.
     *
     * @return an int specifying the fixed width of the icon.
     */
    @Override
    public int getIconWidth() {
        return width;
    }

    /**
     * Returns the icon's height.
     *
     * @return an int specifying the fixed height of the icon.
     */
    @Override
    public int getIconHeight() {
        return height;
    }
}
