
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class MancalaTest {
	
	private static ArrayList<JButton> pits = new ArrayList<JButton>();
	private static MancalaModel model;
	private static JFrame mainMenu;
	private static JFrame boardFrame;
	private static JLabel title;
	private static JLabel whosTurn;
	private static ArrayList<Pit> board = new ArrayList<Pit>();
	private static JPanel centerPanel= new JPanel();
	private static JButton player1Score;
	private static JButton player2Score;
	private static JLabel undoCount;
	private static ArrayList<Pit> copyOfBoard = new ArrayList<Pit>();
	private static JButton undoButton;
	private static JFrame winnerFrame;
	
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
				model = new MancalaModel(3);
				model.populateBoard(board, 3);
				if (e.getSource() == threeStones) {
			        threeStones.setEnabled(false);
				    fourStones.setEnabled(true);
				  }
			}
		});
		
		fourStones.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				model = new MancalaModel(4);
				model.populateBoard(board, 4);
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
					model.whoGoesFirst(0);
				
				if (e.getSource() == playerone) {
			        playerone.setEnabled(false);
				    playertwo.setEnabled(true);
				}
			}
		});
		
		playertwo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					model.whoGoesFirst(1);
				
				if (e.getSource() == playertwo) {
			        playertwo.setEnabled(false);
				    playerone.setEnabled(true);
				}
			}
		});

		JPanel PickLayoutPanel = new JPanel();
		PickLayoutPanel.setPreferredSize(new Dimension(10, 20));
		JLabel layoutLabel = new JLabel ("Choose a Theme:");
		
		JButton light = new JButton("Light");
		JButton dark = new JButton("Dark");
		PickLayoutPanel.add(layoutLabel);
		PickLayoutPanel.add(light);
		PickLayoutPanel.add(dark);

		light.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//add function
				
				if (e.getSource() == light) {
			        light.setEnabled(false);
				    dark.setEnabled(true);
				}
			}
		});
		
		dark.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//add function
				if (e.getSource() == dark) {
			        dark.setEnabled(false);
				    light.setEnabled(true);
				}
				
			}
		});
		
		
		
		JPanel buttonPanel = new JPanel();
		JButton startButton = new JButton("START");
		JButton quitButton = new JButton("QUIT");
		
		startButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			mainMenu.dispose();
			startGame(!dark.isEnabled() ? new DarkStyleStrategy() : new LightStyleStrategy());
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

	static StyleStrategy selectedStrategy;
	/**
	 * this method starts when the player press starts.. it will draw the board
	 */
	public static void startGame(StyleStrategy initialStrategy){
        selectedStrategy = initialStrategy;
		boardFrame = new JFrame();
		boardFrame.setSize(700, 400);
		boardFrame.setLayout(new BorderLayout());

		getTurn();
		
		JPanel undoPanel = new JPanel();
		undoButton = new JButton("Undo");
		undoPanel.add(undoButton);
		undoPanel.add(undoCount);
		 
		undoButton.addActionListener(
					new ActionListener() {
					  public void actionPerformed(ActionEvent e) {
						if(!model.getFreeTurn()){
						Player p = model.getOtherPlayers();
					    model.undo(p);}
						else{
						Player p = model.getsPlayerTurn();
						model.undo(p);
						}
					  }
				    });
		
		
		centerPanel.setLayout(new GridBagLayout());

//		for (int i = 0; i < PIT_TOTAL_COUNT; i++){
//		    //if (i == 6 || i == 13) {continue;}  // This line prevents mancala labels from being drawn
//            //Create a button for the current pit ---------------------
//            //Calculate label
//            String side = (i <= 6) ? "A" : "B";
//            int num = (i <= 6) ? i + 1 : i - 6;
//            final String labelText = side + ((i == 6 || i == 13) ? "" : num);
//            JLabel pitLabel = new JLabel(labelText);
//            if (i == 6 || i == 13) {pitLabel.setFont(new Font("Serif", Font.PLAIN, 30));}
//
//            GridBagConstraints gbc = new GridBagConstraints();
//            gbc.weightx = .5;
//            gbc.weighty = .5;
//            gbc.anchor = i < 6 ? GridBagConstraints.PAGE_START : GridBagConstraints.PAGE_END;
//            gbc.gridy = i < 6 ? 3 : 0;
//            gbc.gridx = pitToPoint(i).x;
//
//            centerPanel.add(pitLabel, gbc);
//
//        }

        updateBoard();
	
		
//		centerPanel.add(pitPanel2);
//		centerPanel.add(pitPanel1);
		
		getScore();
		
		//left panel
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(0,1));

        //leftPanel.add(player2Mancala);
        //leftPanel.add(player2Score);


        //right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(0,1));
		//rightPanel.add(player1Mancala);
        //rightPanel.add(player1Score);

        //top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        JLabel gameTitle = new JLabel("MANCALA", JLabel.CENTER);
        gameTitle.setFont(new Font("Serif", Font.PLAIN, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = .5;
        gbc.weighty = .5;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 5, 0); // add blank space around title
        topPanel.add(gameTitle, gbc);

        gbc = new GridBagConstraints();
        gbc.weightx = .5;
        gbc.weighty = .5;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        topPanel.add(whosTurn, gbc);

//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        gbc.anchor = GridBagConstraints.LAST_LINE_START;
//        gbc.gridwidth = 150;
//        gbc.insets = new Insets(0, 0, 0, 0);
//        JLabel player2Mancala = new JLabel("Player 2's Pit");
//        player2Mancala.setFont(new Font("Serif", Font.PLAIN, 20));
//        topPanel.add(player2Mancala, gbc);
//
//
//        gbc.gridx = 2;
//        gbc.gridy = 1;
//        gbc.anchor = GridBagConstraints.LAST_LINE_END;
//        gbc.insets = new Insets(0, 0, 0, 0);
//        gbc.gridwidth = 150;
//        JLabel player1Mancala = new JLabel("Player 1's Pit");
//        player1Mancala.setFont(new Font("Serif", Font.PLAIN, 20));
//        topPanel.add(player1Mancala, gbc);

        model.attach(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
            	if(!model.gameOver()){
            		board= model.getBoard();
            		topPanel.remove(whosTurn);
//            		leftPanel.remove(player2Score);
//            		rightPanel.remove(player1Score);
            		undoPanel.remove(undoCount);
                	centerPanel.removeAll();
                	pits.clear();
//                	pitPanel1.removeAll();
//                	pitPanel2.removeAll();
                	updateBoard();
                	getTurn();
                	getScore();
//                	centerPanel.add(pitPanel2);
//                	centerPanel.add(pitPanel1);

                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.weightx = .5;
                    gbc.weighty = .5;
                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    gbc.anchor = GridBagConstraints.CENTER;
                    gbc.insets = new Insets(0, 0, 0, 0);
                	topPanel.add(whosTurn, gbc);

//                	leftPanel.add(player2Score);
//            		rightPanel.add(player1Score);
            		undoPanel.add(undoCount);
                	boardFrame.revalidate();
            	}else{
            		boardFrame.dispose();
            		winnerPanel();
            	}

            }
        });


        boardFrame.add(undoPanel, BorderLayout.SOUTH);
        boardFrame.add(leftPanel, BorderLayout.WEST);
        boardFrame.add(rightPanel, BorderLayout.EAST);
        boardFrame.add(centerPanel, BorderLayout.CENTER);
        boardFrame.add(topPanel, BorderLayout.NORTH);


        boardFrame.setVisible(true);
        boardFrame.setTitle("Mancala");
        boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	public static void updateBoard(){
		int t = model.getsWhoTurn();

        GridBagConstraints gbc;

        // For each pit, including mancala
        for (int i = 0; i < PIT_TOTAL_COUNT; i++){
            // ------------------------------------
            // Labels
            // ------------------------------------
            //if (i == 6 || i == 13) {continue;}  // This line prevents mancala labels from being drawn
            //Create a button for the current pit ---------------------
            //Calculate label
            String side = (i <= 6) ? "A" : "B";
            int num = (i <= 6) ? i + 1 : i - 6;
            final String labelText = side + ((i == 6 || i == 13) ? "" : num);
            JLabel pitLabel = new JLabel(labelText);
            if (i == 6 || i == 13) {pitLabel.setFont(new Font("Serif", Font.PLAIN, 30));}

            gbc = new GridBagConstraints();
            gbc.weightx = .5;
            gbc.weighty = .5;
            gbc.anchor = i < 6 ? GridBagConstraints.PAGE_START : GridBagConstraints.PAGE_END;
            gbc.gridy = i < 6 ? 3 : 0;
            gbc.gridx = pitToPoint(i).x;
            centerPanel.add(pitLabel, gbc);

            // ------------------------------------
            // Buttons
            // ------------------------------------
            int stones = board.get(i).getNumbOfStones();
            PitButton pit = new PitButton(String.valueOf(stones), selectedStrategy);
            final int selectedPit = i;
            pit.setEnabled(i < 6 ? t==0 : t==1);
            pit.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    Player p = model.getsPlayerTurn();
                    model.move(selectedPit, p);
                }
            });
            gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = .5;
            gbc.weighty = .5;
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.gridy = pitToPoint(i).y + 1;
            gbc.gridx = pitToPoint(i).x;
            gbc.gridheight = (i == 6 || i == 13) ? 2 : 1; // Mancala are extra high

            if (i == 6 || i == 13){
                pit.setEnabled(stones > 0);  //Override above calculation and disable if mancala
            }
            pits.add(pit);
            centerPanel.add(pit, gbc);  //Add pit panel to game board grid panel
        }
		
	}
	
	
	public static void winnerPanel(){
		winnerFrame = new JFrame();
		winnerFrame.setSize(300, 200);
		String getWin ="" ;
		if(model.getWinner()== null){
			getWin = "Game is a Draw";
		}
		else{
			getWin = "Winner is " + model.getWinner().getName();
		}

		JLabel winner = new JLabel(getWin, JLabel.CENTER);
		
		winnerFrame.add(winner);
		winnerFrame.setVisible(true);
        winnerFrame.setTitle("Winner");
        winnerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	
	public static void getTurn(){
		Player p = model.getsPlayerTurn();
		whosTurn = new JLabel(p.getName()+ "'s Turn", JLabel.CENTER);
		undoCount = new JLabel(p.getName() + "'s Undo Count:"  + p.getnumOfUndos());
	}
	
	public static void getScore(){
		player1Score = new JButton(""+board.get(6).getNumbOfStones());
		player1Score.setBackground(Color.WHITE);
		player1Score.setEnabled(false);
		
		player2Score = new JButton(""+board.get(13).getNumbOfStones());
		player2Score.setBackground(Color.WHITE);
		player2Score.setEnabled(false);
	}

    //todo: These should be defined by and retrieved from the model
    private static final int PITS_PER_ROW = 6;  //pits in a row, NOT counting mancala
    private static final int PIT_ROWS = 2;         //some variants have more than 2 rows, but only 2 rows supported at this time
    private static final int PIT_TOTAL_COUNT = PITS_PER_ROW * PIT_ROWS + 2;
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
    private static Point pitToPoint(int pit){
        return new Point(
                pit <= PITS_PER_ROW ? pit + 1 : (PIT_TOTAL_COUNT / 2) - pit + PITS_PER_ROW,  // y coordinate
                pit < PITS_PER_ROW ? 1 : 0   // x coordinate
        );
    }
}

class PitButton extends RoundedButton {
    public PitButton(String text, StyleStrategy initialStrategy){
        this.setStyleStrategy(initialStrategy);
        this.setText(text);
    }

    public PitButton(String text){
        this.setText(text);
    }

    public PitButton(StyleStrategy initialStrategy){
        this.setStyleStrategy(initialStrategy);
    }

    public void setStyleStrategy(StyleStrategy strategy){
        this.setBackground(strategy.getPitColor());
        this.setForeground(strategy.getPitTextColor());
    }
}

interface StyleStrategy{
    //Color getBackgroundColor();
    //Color getTextColor();
    Color getPitColor();
    Color getPitTextColor();
}

class LightStyleStrategy implements StyleStrategy{

//        @Override
//        public Color getBackgroundColor() {
//            return Color.GRAY;
//        }
//
//        @Override
//        public Color getTextColor() {
//            return Color.BLACK;
//        }

    @Override
    public Color getPitColor() {
        return Color.LIGHT_GRAY.brighter().brighter();
    }

    @Override
    public Color getPitTextColor() {
        return Color.BLACK;
    }
}

class DarkStyleStrategy implements StyleStrategy{

    @Override
    public Color getPitColor() {
        return Color.DARK_GRAY.darker();
    }

    @Override
    public Color getPitTextColor() {
        return Color.LIGHT_GRAY.brighter();
    }
}