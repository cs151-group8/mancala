package mancala;
import javax.swing.JButton;
import javax.swing.JPanel;

public interface Strategy {

	public void setPitColor(JButton button);
	
	public void setStoneColor(JButton b);
	
	public void setPanelColor(JPanel p);
	
}
