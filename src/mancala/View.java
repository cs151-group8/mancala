package mancala;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class View implements ChangeListener {
	
	private MancalaModel model;
	private ArrayList<JButton> pits;
	private JFrame mainMenu;
	private JFrame board;
	private JLabel title;
	


	public View(MancalaModel model){
		this.model = model;
		mainMenu();

	}
	
	public void mainMenu(){
		mainMenu = new JFrame("Main Menu");
		mainMenu.setSize(400,400);
		mainMenu.setLayout(new BorderLayout());
		
		title = new JLabel("MANCALA");
		title.setFont(new Font("Serif", Font.PLAIN, 30));
		title.setHorizontalAlignment(JTextField.CENTER);	
		
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		JPanel PickStonePanel = new JPanel();
		PickStonePanel.setPreferredSize(new Dimension(10, 20));
		JLabel PickStoneLabel = new JLabel("Choose the number of stones");
		JButton threeStones = new JButton("3");
		JButton fourStones = new JButton("4");
		PickStonePanel.add(PickStoneLabel);
		PickStonePanel.add(threeStones);
		PickStonePanel.add(fourStones);
		
		threeStones.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				// INSERT FUNCTION HERE
				
				
			}
		});
		
		fourStones.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				//INSERT FUNCTION HERE
				
			}
		});
		
		
		JPanel PickLayoutPanel = new JPanel();
		PickLayoutPanel.setPreferredSize(new Dimension(10, 20));
		JLabel layoutLabel = new JLabel ("Choose a Theme:");
		
		JButton blue = new JButton("Blue");
		JButton red = new JButton("Red");
		PickLayoutPanel.add(layoutLabel);
		PickLayoutPanel.add(blue);
		PickLayoutPanel.add(red);

		blue.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				// INSERT FUNCTION HERE
				
				
			}
		});
		
		red.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				//INSERT FUNCTION HERE
				
			}
		});
		
		
		
		JPanel buttonPanel = new JPanel();
		JButton startButton = new JButton("START");
		JButton quitButton = new JButton("QUIT");
		
		startButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			mainMenu.dispose();
			startGame();
			}
		});
		
		
		quitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			mainMenu.dispose();
			}
		});
		
		buttonPanel.add(startButton);
		buttonPanel.add(quitButton);
		
		container.add(PickStonePanel);
		container.add(PickLayoutPanel);
	
		
		mainMenu.add(title, BorderLayout.NORTH);
		mainMenu.add(container, BorderLayout.CENTER);
		mainMenu.add(buttonPanel, BorderLayout.SOUTH);
		mainMenu.setVisible(true);
		mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public void startGame(){
		
		
	}
	
	public void stateChanged(ChangeEvent e){
	
	
	}


}
