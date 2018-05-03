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


public class MancalaTest {
	
	private static MancalaModel model;
	private static ArrayList<JButton> pits = new ArrayList<JButton>();
	private static JFrame mainMenu;
	private static JFrame boardFrame;
	private static JLabel title;
	 private ArrayList<Pit> board = new ArrayList<Pit>();
	 private static ArrayList<Pit> tempBoard = new ArrayList<Pit>();

	public static void main(String args[]){
		mainMenu();
	}
	
	
	/**
	 * this method will create the main menu for the game.. where players pick number of stones and theme..
	 */
	public static void mainMenu(){
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
				MancalaModel mm = new MancalaModel(3);
				mm.copyTheBoard(tempBoard, mm.getBoard());
				if (e.getSource() == threeStones) {
			        threeStones.setEnabled(false);
				    fourStones.setEnabled(true);
				  }
			}
		});
		
		fourStones.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				MancalaModel mm = new MancalaModel(4);
				mm.copyTheBoard(tempBoard, mm.getBoard());
				if (e.getSource() == fourStones) {
			        fourStones.setEnabled(false);
				    threeStones.setEnabled(true);
			}
		}
		});
		
		
		JPanel playerPanel = new JPanel();
		playerPanel.setPreferredSize(new Dimension(10, 20));
		JLabel playerLabel = new JLabel ("Who Goes First:");
		
		JButton playerone = new JButton("Player 1");
		JButton playertwo = new JButton("Player 2");
		playerPanel.add(playerLabel);
		playerPanel.add(playerone);
		playerPanel.add(playertwo);
		
		playerone.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//add function
				
				if (e.getSource() == playerone) {
			        playerone.setEnabled(false);
				    playertwo.setEnabled(true);
				}
			}
		});
		
		playertwo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//add function
				
				if (e.getSource() == playertwo) {
			        playertwo.setEnabled(false);
				    playerone.setEnabled(true);
				}
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
				//add function
				
				if (e.getSource() == blue) {
			        blue.setEnabled(false);
				    red.setEnabled(true);
				}
			}
		});
		
		red.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//add function
				if (e.getSource() == red) {
			        red.setEnabled(false);
				    blue.setEnabled(true);
				}
				
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
		container.add(playerPanel);
		container.add(PickLayoutPanel);
	
		
		mainMenu.add(title, BorderLayout.NORTH);
		mainMenu.add(container, BorderLayout.CENTER);
		mainMenu.add(buttonPanel, BorderLayout.SOUTH);
		mainMenu.setVisible(true);
		mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	/**
	 * this method starts when the player press starts.. it will draw the board
	 */
	public static void startGame(){
		boardFrame = new JFrame();
		boardFrame.setSize(600, 300);
		boardFrame.setLayout(new BorderLayout());
		
		JPanel undoPanel = new JPanel();
		JButton undoButton = new JButton("Undo");
		undoPanel.add(undoButton);
		 
		undoButton.addActionListener(
					new ActionListener() {
					  public void actionPerformed(ActionEvent e) {
					    //model.undo();				
					  }		
				    });
		
		
		//CENTER PANEL
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(6, 0));
		
		JPanel pitPanel1 = new JPanel();
		//players 1 pits
		
		for(int i=0; i<6 ;i++){
			JButton pit = new JButton(""+tempBoard.get(i).getNumbOfStone());
			pit.setBackground(Color.WHITE);
			pitPanel1.add(pit);
			
			pit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					
				}
				
			});
			pits.add(pit);
		}
		
		JPanel pitPanel2 = new JPanel();
		//players 2 pits
		
		for(int i=7; i<13 ;i++){
			JButton pit = new JButton(""+tempBoard.get(i).getNumbOfStone());
			pit.setBackground(Color.WHITE);
			pitPanel2.add(pit);
			
			pit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					
				}
				
			});
			pits.add(pit);
		}
		
		centerPanel.add(pitPanel2);
		centerPanel.add(pitPanel1);
		
		
		
		
		//left panel
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(0,1));
		JLabel player2Mancala = new JLabel("Player 2's Pit");
		player2Mancala.setFont(new Font("Serif", Font.PLAIN, 20));
		JButton player2Score = new JButton("");
		leftPanel.add(player2Mancala);
		leftPanel.add(player2Score);
		
		
		//right panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(0,1));
		JLabel player1Mancala = new JLabel("Player 1's Pit");
		player1Mancala.setFont(new Font("Serif", Font.PLAIN, 20));
		JButton player1Score = new JButton("");
		rightPanel.add(player1Mancala);
		rightPanel.add(player1Score);
		
		//top panel
		JPanel topPanel = new JPanel();
		JLabel gameTitle = new JLabel("MANCALA");
		gameTitle.setFont(new Font("Serif", Font.PLAIN, 30));
		topPanel.add(gameTitle);
		
		
		boardFrame.add(undoPanel, BorderLayout.SOUTH);
		boardFrame.add(leftPanel, BorderLayout.WEST);
		boardFrame.add(rightPanel, BorderLayout.EAST);
		boardFrame.add(centerPanel, BorderLayout.CENTER);
		boardFrame.add(topPanel, BorderLayout.NORTH);
		
		
		boardFrame.setVisible(true);
        boardFrame.setTitle("Mancala");
        boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
