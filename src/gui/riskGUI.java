/*
 * 	Authors: 	Dylan Tobia, Abigail Dodd, Sydney Komro, Jewell Finder
 * 	File:		riskGUI.java
 * 	Purpose:	GUI for visual implementation of RISK
 */


/*
 * To-Dos for Iteration 3
 * 
 * Work on our wow factor - Jewell
 * Make card images - Jewell
 * Polish the GUI/Make sure everything is displayed - Abigail
 * Set up flags for state changes during game - Abigail
 * Adjust reinforcement phase adjustment - Abigail
 * Decide order of players based on dice roll - Abigail
 * Implement rules for army placement - Sydney
 * Write some sort of attack (can be hard coded for now) - Sydney
 * Change AIs during gameplay - Dylan
 * Write AI strategies - Dylan
 *
 */




package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.AncestorListener;

import Model.AI;
import Model.Card;
import Model.Country;
import Model.Faction;
import Model.Game;
import Model.Map;
import songplayer.SongPlayer;
import Model.Player;

//just a simple GUI to start, with a drawingPanel for map stuff
public class riskGUI extends JFrame {

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		new riskGUI().setVisible(true);
	}

	private static BoardPanel drawingPanel;
	private JMenuBar menu;
	private int width = java.awt.GraphicsEnvironment
			.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	private int height = java.awt.GraphicsEnvironment
			.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	private int xWidth = 0;
	private int yHeight = 0;
	// private Map map; dont think this is needed anymore cause it is stored
	// within theGame
	private Game theGame;
	private ImageIcon gameBoard, stark, targaryen, lannister, whiteWalkers,
			dothraki, wildlings;
	private JButton checkButton;
	private CountryPanel currCountryPanel;
	private JButton moveButton;
	private boolean splash;
	private ImageIcon splashScreen;
	private JPanel splashInfo;
	// my new favorite font...
	private Font goudyFontBig = new Font("Goudy Old Style", Font.BOLD, 40);
	private Font gotFontHeader;
	private Font gotFontBody;
	
	
	
	
	private String gameType;
	private Player nextPlayer, currPlayer;
	private int humans;
	private int ai = -1;
	private int numOfUnitsToMove = 0;
	private ArrayList<String> houses;
	private ArrayList<String> playerNames;
	private ArrayList<String> possHouses;
	private boolean decisionMakingPhase = false, moveUnitsFlag = false;

	public riskGUI() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			gotFontHeader = Font.createFont(Font.TRUETYPE_FONT, new File("TrajanusBricks.ttf"));
			gotFontHeader = gotFontHeader.deriveFont(36f);
			gotFontBody = Font.createFont(Font.TRUETYPE_FONT, new File("LibreBaskerville-Regular.otf"));
			gotFontBody = gotFontBody.deriveFont(24f);
		} catch (FontFormatException e) {
			System.out.println("What'd you do???");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("What'd you do???");
			e.printStackTrace();
		}
		
		
		ge.registerFont(gotFontHeader);

		ge.registerFont(gotFontBody);
		
		
		System.out.println("Width = " + width + " Height = " + height);
		//splash = true;  //comment me out for default mode
		splash = false; //comment me out for splash screens
		setUpImages();
		setUpGui();
		setUpMenu();
		setUpHouseArray();
		//setUpSplash();  //comment me out for default mode
		defaultMode();  //comment me out for splash screens

	}

	private void defaultMode() {
		drawingPanel = new BoardPanel();
		theGame = Game.getInstance(2, 2);
		setUpDrawingPanel();
		ArrayList<Player> players = theGame.getPlayers();
		players.get(0).setFaction("Stark");
		players.get(1).setFaction("Dothraki");
		players.get(2).setFaction("White Walkers");
		players.get(3).setFaction("Lannister");
		players.get(0).setName("Player1");
		players.get(1).setName("Player2");
		drawingPanel.repaint();
	}

	private void setUpHouseArray() {
		possHouses = new ArrayList<String>();
		possHouses.add("Stark");
		possHouses.add("Lannister");
		possHouses.add("Targaryen");
		possHouses.add("White Walkers");
		possHouses.add("Dothraki");
		possHouses.add("Wildlings");
		
	}

	private void setUpSplash() {
		// Still messing around with this.
		splashScreen = new ImageIcon("SplashScreen.jpg");
		drawingPanel = new BoardPanel();
		drawingPanel.setLayout(null);
		drawingPanel.setSize(width - 40, height - 70);
		drawingPanel.setLocation(10, 10);
		drawingPanel.setBackground(Color.LIGHT_GRAY);
		this.add(drawingPanel);
		this.setVisible(true);
		drawingPanel.repaint();
		splashLoading1();
	}

	private void splashLoading2() {
		// TODO Auto-generated method stub
		System.out.println("Brace Yourselves, RISK is Coming...");
		splash = false;
		drawingPanel.removeAll();
		this.remove(drawingPanel);
		// creates or grabs an instance of the game, first variable is number of
		// human players, second is total number of players
		theGame = Game.getInstance(humans, ai);
		setUpDrawingPanel();

		ArrayList<Player> players = theGame.getPlayers();
		for (int i = 0; i < humans; i++) {
			players.get(i).setFaction(houses.get(i));
			players.get(i).setName(playerNames.get(i));
		}
		for(String h : houses){
			possHouses.remove(h);
		}
		int i = 0;
		for (int j = humans; j < (humans + ai); j++) {
			players.get(j).setFaction(possHouses.get(i));
			i++;
		}
	}

	private void splashNames() {
		// TODO Auto-generated method stub
		System.out.println("What are the players names?");
		playerNames = new ArrayList<String>();
		for (int i = 0; i < humans; i++) {
			playerNames.add(JOptionPane.showInputDialog("What will be Player "
					+ (i + 1) + "'s Name?"));
		}
		splashLoading2();
	}

	private void splashHouses() {
		drawingPanel.remove(splashInfo);
		// TODO Auto-generated method stub
		System.out.println("What will be your houses?");
		houses = new ArrayList<String>();
		for(int i=0; i<humans; i++){
			Boolean illegalName=true;
			String house="";
			while(illegalName==true){
				Boolean check=true;
				int num = 0;
				house =JOptionPane.showInputDialog("What will be Player "+ (i+1)+ "'s House? \n Choose: Targaryen, Stark, Lannister, White Walkers, Dothraki, or Wildlings");
				for(String s : possHouses){
					if(s.compareTo(house)!=0){
						num++;
					}
				}
				if(num==6){
					check=false;
					JOptionPane.showMessageDialog(riskGUI.this, "Illegal Faction name. Please try again.");
				}
				
				for(int j=0; j<houses.size();j++){
					if(houses.get(j).compareTo(house)==0){
						check=false;
						JOptionPane.showMessageDialog(riskGUI.this, "Faction has already been chosen. Please pick another.");
					}
				}
				if (check)
					illegalName = false;
			}
			houses.add(house);
		}
		splashNames();
	}

	private void splashNumPlayers() {
		drawingPanel.remove(splashInfo);
		// TODO Auto-generated method stub
		System.out.println("How many players?");

		humans = Integer.parseInt(JOptionPane.showInputDialog("How Many Human Players?"));
		while (ai == -1){
			ai = Integer.parseInt(JOptionPane.showInputDialog("How Many AI Players?"));
			if((ai + humans) >6 || ai+humans<3){
				JOptionPane.showMessageDialog(riskGUI.this, "Illegal number of players. Must have between 3 and 6 total players");
				ai=-1;

			}

		}
		splashHouses();
	}

	private void splashChooseGame() {
		drawingPanel.remove(splashInfo);
		System.out.println("New Game or Load Game?");
		splashInfo = new JPanel();
		splashInfo.setLayout(null);
		splashInfo.setSize(700, 400);
		splashInfo.setLocation(width / 2 - 350, height / 2 - 200);
		JLabel load = new JLabel("New Game or Load Game?");
		load.setFont(gotFontHeader.deriveFont(24));
		load.setLocation(150, 5);
		load.setSize(600, 150);
		JButton newG = new JButton("New Game!");
		newG.setFont(gotFontHeader.deriveFont(24));
		newG.setLocation(50, 200);
		newG.addActionListener(new GameTypeListener());
		newG.setSize(300, 100);
		JButton loadG = new JButton("Load Game!");
		loadG.setFont(gotFontHeader.deriveFont(24));
		loadG.setLocation(375, 200);
		loadG.addActionListener(new GameTypeListener());
		loadG.setSize(300, 100);
		splashInfo.add(newG);
		splashInfo.add(loadG);
		splashInfo.add(load);
		drawingPanel.add(splashInfo);
		drawingPanel.repaint();
	}

	/*
	 * SplashLoading1 is the first loading page. Sets up the background image
	 * and JPanel for information. Starts Theme Song, which will play until it
	 * ends. This screen is shown for 10 seconds.
	 */
	private void splashLoading1() {
		splashInfo = new JPanel();
		splashInfo.setLayout(null);
		splashInfo.setSize(500, 150);
		splashInfo.setLocation(width / 2 - 250, height / 2 - 75);
		JLabel load = new JLabel("LOADING...");
		load.setFont(gotFontHeader);
		load.setLocation(150, 5);
		load.setSize(300, 150);
		splashInfo.add(load);
		drawingPanel.add(splashInfo);
		drawingPanel.repaint();

		// play the song! Commented out for now in order to test without losing
		// my mind
		// SongPlayer.playFile("Game_Of_Thrones_Official_Show_Open_HBO_.wav");

		// pause on this screen for 10 seconds. Set to 5 seconds during testing.
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			System.out.println("nahhh");
		}
		// move on to splash screen #2, choosing game play
		splashChooseGame();
	}

	private void setUpGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new BorderLayout());
		setTitle("GoT Risk");
		setSize(width, height);
		this.setVisible(true);
	}

	private void setUpMenu() {
		JMenu file = new JMenu("File");
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.addActionListener(new NewGameListener());
		file.add(newGame);
		JMenu help = new JMenu("Help");
		menu = new JMenuBar();
		menu.add(file);
		JMenuItem about = new JMenuItem("About");
		menu.add(help);

		JMenuItem rules = new JMenuItem("Rules");
		help.add(rules);
		help.add(about);
		this.setJMenuBar(menu);

		rules.setActionCommand("rules");
		about.setActionCommand("about");
		rules.addActionListener(new helpListener());
		about.addActionListener(new helpListener());

	}

	private void setUpDrawingPanel() {
		// if(drawingPanel==null)
		

		// System.out.println(gameBoard.toString());
		drawingPanel = new BoardPanel();
		drawingPanel.setLayout(null);
		drawingPanel.setSize(width - 40, height - 70);
		drawingPanel.setLocation(10, 10);
		drawingPanel.setBackground(Color.LIGHT_GRAY);
		drawingPanel.repaint();

		// Prepare to draw the buttons!
		Dimension drawD = drawingPanel.getSize();
		xWidth = (int) (drawD.getWidth() / 40);
		yHeight = (int) (drawD.getHeight() / 40);
		drawCountryButtons();

		// Draw country panel
		currCountryPanel = new CountryPanel();
		currCountryPanel.setSize(10 * xWidth, 10 * yHeight);
		currCountryPanel.setLocation(17 * xWidth, 3 * yHeight);
		// currCountryPanel.setBackground(Color.BLUE);
		// currCountryPanel.setLayout(new BorderLayout());

		drawingPanel.add(currCountryPanel);
		this.add(drawingPanel, BorderLayout.CENTER);
		drawingPanel.repaint();

	}

	private void setUpImages() {

		gameBoard = new ImageIcon("GoTMapRisk.jpg");
		stark = new ImageIcon("stark.jpg");
		targaryen = new ImageIcon("targaryen.jpg");
		lannister = new ImageIcon("lannister.jpg");
		whiteWalkers = new ImageIcon("whiteWalkers.jpg");
		dothraki = new ImageIcon("dothraki.jpg");
		wildlings = new ImageIcon("wildlings.jpg");
	}

	// draws buttons over the name of all of the countries
	private void drawCountryButtons() {
		for (Country country : theGame.getGameMap().getCountries()) {
			// The Make button method has the same logic that was previously
			// here
			country.makeButton(xWidth, yHeight, new CountryClickListener());
			drawingPanel.add(country.getButton());
		} // end for

		// Manually adjusts the size and shape of a few of the weirder
		// shaped country buttons

	}// end drawCountryButtons

	// Updates those buttons if the size of the panel changes

	private void updateCountryButtons() {
		for (Country country : theGame.getGameMap().getCountries()) {
			country.updateButton(xWidth, yHeight);
		}
	}

	private class BoardPanel extends JPanel implements Observer {
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.white);
			super.paintComponent(g2);

			Image tmp;
			if (splash)
				tmp = splashScreen.getImage();
			else
				tmp = gameBoard.getImage();
			g2.drawImage(tmp, 0, 0, drawingPanel.getWidth(),
					drawingPanel.getHeight(), null);

			Dimension drawD = drawingPanel.getSize();
			xWidth = (int) (drawD.getWidth() / 40);
			yHeight = (int) (drawD.getHeight() / 40);

			if (!splash) {
				updateCountryButtons();
				currCountryPanel.updatePanel();
			}

			drawFactions(g2);
			if(theGame != null)
				drawCurrentPlayer(g2);
			// drawGridAndNumbers(g2);

		}

		private void drawCurrentPlayer(Graphics2D g2) {
			Player currentPlayer = theGame.getCurrentPlayer();
			if(currentPlayer != null)
			{
				Faction playersFact = theGame.getCurrentPlayer().getFaction();
				switch (playersFact) {
				case STARK:
					g2.drawImage(stark.getImage(), 0, 0, 100, 100, null);
					break;
				case TARGARYEN:
					g2.drawImage(targaryen.getImage(), 0, 0, 100, 100, null);
					break;
				case LANNISTER:
					g2.drawImage(lannister.getImage(), 0, 0, 100, 100, null);
					break;
				case DOTHRAKI:
					g2.drawImage(dothraki.getImage(), 0, 0, 100, 100, null);
					break;
				case WHITEWALKERS:
					g2.drawImage(whiteWalkers.getImage(), 0, 0, 100, 100,null);
					break;
				case WILDLINGS:
					g2.drawImage(wildlings.getImage(), 0, 0, 100, 100, null);
					break;
					default:
					break;
				}
			}
			g2.setColor(Color.WHITE);
			g2.setFont(gotFontBody.deriveFont(20f));
//			g2.setFont(new Font("Courier",Font.BOLD,20));
			g2.drawString("Current Player: " + currentPlayer.getName(), 110, 35);

		}

		// draws factions if a country is occupied
		private void drawFactions(Graphics2D g2) {
			
			Map temp = Map.getInstance();
			Country[] allCountries = temp.getCountries();
			for (Country country : allCountries) {
				if (country.getOccupier() != null) {
					Faction ownerFaction = country.returnMyOwnersFaction();
					switch (ownerFaction) {
					case STARK:
						g2.drawImage(stark.getImage(), (int) country.getX()
								* xWidth, (int) country.getY() * yHeight + 5,
								30, 30, null);
						break;
					case TARGARYEN:
						g2.drawImage(targaryen.getImage(), (int) country.getX()
								* xWidth, (int) country.getY() * yHeight + 5,
								30, 30, null);
						break;
					case LANNISTER:
						g2.drawImage(lannister.getImage(), (int) country.getX()
								* xWidth, (int) country.getY() * yHeight + 5,
								30, 30, null);
						break;
					case DOTHRAKI:
						g2.drawImage(dothraki.getImage(), (int) country.getX()
								* xWidth, (int) country.getY() * yHeight + 5,
								30, 30, null);
						break;
					case WHITEWALKERS:
						g2.drawImage(whiteWalkers.getImage(),
								(int) country.getX() * xWidth,
								(int) country.getY() * yHeight + 5, 30, 30,
								null);
						break;
					case WILDLINGS:
						g2.drawImage(wildlings.getImage(), (int) country.getX()
								* xWidth, (int) country.getY() * yHeight + 5,
								30, 30, null);

					}

				}
			}

		}
		// draws a 40X40 grid over the risk map. Used for determining where to
		// place buttons.


		// draws a 40X40 grid over the risk map. Used for determining where to
		// place buttons.


		private void drawGridAndNumbers(Graphics2D g2) {
			for (int i = xWidth; i < width - 40; i += xWidth) {
				g2.drawLine(i, 0, i, height - 70);
			}

			for (int i = yHeight; i < height - 70; i += yHeight) {
				g2.drawLine(0, i, width - 40, i);
			}

			int xCount = xWidth / 2;
			int yCount = yHeight / 2;

			int startX = xCount;
			int startY = yCount;
			int y = 0;
			// int x = 0;

			for (int i = 1; i < 40; i++) {
				int x = 1;
				y++;
				startY = yCount;

				for (int j = 1; j < 40; j++) {
					g2.drawString(Integer.toString(x), startX, startY);
					startY += yHeight;

					x++;
				}
				startX += xWidth;
			}
		}

		// update for drawing factions over occupied functions
		@Override
		public void update(Observable arg0, Object arg1) {
			repaint();
		}

	}

	public static BoardPanel getBoardPanel() {
		return drawingPanel;
	}

	private class CountryPanel extends JPanel {
		private JPanel centerPanel;
		private JButton makeAMoveButton;
		private Country curr;
		
		/*
		 * public void PaintComponent(Graphics g){ update(g);
		 * super.paintComponent(g);
		 * 
		 * }//end
		 */

		public CountryPanel() {
			this.setLayout(new BorderLayout());
			curr = theGame.getSelectedCountry();
			updatePanel();
	/*		centerPanel = new JPanel();
	//		this.setOpaque(false);
			this.setLocation(12 * xWidth, 3 * yHeight);
			this.setSize(xWidth * 18, yHeight * 12);
			centerPanel.add(new JLabel("Select a Country"));
			makeAMoveButton = new JButton("Make your move!");
			makeAMoveButton.addActionListener(new makeMoveListener());
			this.add(centerPanel);*/
		}//end constructor
		
		//Displays country's name and owner
		public void makeTopLabel(){
			JPanel top = new JPanel();
			top.setLayout(new BorderLayout());
			JLabel country = new JLabel(curr.getName());
			country.setFont(gotFontHeader);
			country.setHorizontalAlignment(JLabel.CENTER);
			top.add(country, BorderLayout.CENTER);
			JLabel owner = new JLabel();
			owner.setFont(gotFontHeader.deriveFont(28f));
			owner.setHorizontalAlignment(JLabel.CENTER);
			if (curr.getOccupier()!=null)
				owner.setText(curr.getOccupier().getName() + " " + curr.getOccupier().getFaction().getName());
			else
				owner.setText("None");
			top.add(owner, BorderLayout.SOUTH);
			
			this.add(top, BorderLayout.NORTH);
			top.revalidate();	
		}//end makeTopLabel
		
		//will display a list of neighbors and the strength of the country's armies
		public void makePlayingCenterPanel(){
			JPanel center = new JPanel();
			center.setLayout(new GridLayout(0,2));
			JPanel neighbors = new JPanel();
			//Make neighbors panel
			ArrayList<Country> neighs = curr.getNeighbors();
			neighbors.setLayout(new GridLayout(neighs.size(),0));
			
			//add all of the neighbors to that panel
			for (int i=0; i < neighs.size(); i++){
				JLabel lab = new JLabel();
		//		lab.setForeground(Color.white);
				lab.setFont(gotFontBody);
				lab.setText(neighs.get(i).getName());
				neighbors.add(lab);
			}//end addneighbors loop
			
			center.add(neighbors);
			
			//Now, add the strength
			JLabel streng = new JLabel();
			streng.setFont(gotFontHeader);
			streng.setText(""+curr.getForcesVal());
			
			center.add(streng);
			
			this.add(center, BorderLayout.CENTER);
			center.revalidate();
		}//end makeCenterPanel
		
		//displays the cards and has a button for trading in cards
		public void makePlayingCardPanel(){
			ArrayList<Card> myCards = theGame.getCurrentPlayer().getCards();
			
			JPanel cards = new JPanel();
			cards.setLayout(new GridLayout(2,0));
			JPanel showCards = new JPanel();
			/*
			 * TODO : Display card image icons
			 */
			cards.add(showCards);
			JButton trade = new JButton("Trade in Cards");
			trade.addActionListener(new TradeClickListener());
			cards.add(trade);
			this.add(cards, BorderLayout.EAST);
			cards.revalidate();
		}//end makeCardPanel
		
		public void makePlayingMyCountryBottomLabel(){
			JPanel bott = new JPanel();
			bott.setLayout(new BorderLayout(0,2));
			JButton transfer = new JButton("Transfer Troops");
			JButton war = new JButton("Go to war!");
			bott.add(transfer);
			bott.add(war);
			this.add(bott, BorderLayout.SOUTH);
			bott.revalidate();
		}//end makeBottomLabel
		
		
		public void makePlayingYourCountryBottomLabel(){
			JButton attack = new JButton("Attack");
			this.add(attack, BorderLayout.SOUTH);
			this.revalidate();
		}//end makePlayingYourCountryBottomLabel
		
		
		
		public void makePlacementBottomLabel(){
			JButton place = new JButton("Place Troops Here");
			place.addActionListener(new makeMoveListener());
			this.add(place, BorderLayout.SOUTH);
			this.revalidate();
		}//end makeplacementbottom
		
		//Only displays neighbors
		public void makePlacementCenterPanel(){
			JPanel neighbors = new JPanel();
			ArrayList<Country> neighs = curr.getNeighbors();
			neighbors.setLayout(new GridLayout(neighs.size(),0));
			
			//add all of the neighbors to that panel
			for (int i=0; i < neighs.size(); i++){
				JLabel lab = new JLabel();
				lab.setFont(gotFontBody);
				lab.setText(neighs.get(i).getName());
				neighbors.add(lab);
			}//end addneighbors loop
			this.add(neighbors, BorderLayout.CENTER);
			neighbors.revalidate();
		}
		
		

		public void updatePanel() {
			curr = theGame.getSelectedCountry();
			this.removeAll();

			this.setLocation(12 * xWidth, 3 * yHeight);
			this.setSize(xWidth * 18, yHeight * 12);
			
			Iterator itr;
			Card card; 
			/*for(itr = cards.listIterator(); itr.hasNext(); ){ 
				card = (Card) itr.next();
				//Add the JCheckBox for the card
				System.out.println("Card: " + card.getCountry());
			}
			*/
			if (curr == null) {
//				centerPanel.add(new JLabel("Select a Country"));
				JLabel directions = new JLabel();
				directions.setHorizontalAlignment(JLabel.CENTER);
//				Font labFont = gotFontBody.deriveFont(Font.BOLD, 32);
//				directions.setFont(labFont);
				
				if (theGame.isPlacePhase()){
					directions.setFont(gotFontHeader.deriveFont(Font.BOLD, 34));
					directions.setText("Choose a Country to Claim");
					this.add(directions, BorderLayout.CENTER);
					
				}//end if
				else if (theGame.isReinforcePhase()){
					directions.setFont(gotFontHeader.deriveFont(Font.BOLD, 30));
					directions.setText("Choose a Country to Reinforce");
					this.add(directions, BorderLayout.CENTER);
				}//end else if
				else {
					JLabel dir2 = new JLabel();
					directions.setFont(gotFontHeader.deriveFont(Font.BOLD, 34));
					dir2.setFont(gotFontHeader.deriveFont(Font.BOLD, 34));
					directions.setText("Choose a Country to Attack"); //or Reinforce");
					dir2.setText("Or to Reinforce");
					dir2.setHorizontalAlignment(JLabel.CENTER);
					this.add(directions, BorderLayout.CENTER);
					this.add(dir2, BorderLayout.SOUTH);
					makePlayingCardPanel();
				}//end else
				
				this.revalidate();
				this.repaint();
			} // end if
			else {
			/*	centerPanel.setLayout(new BorderLayout());
				centerPanel.add(new JLabel(curr.getName()), BorderLayout.NORTH);

				centerPanel.add(new JLabel("" + curr.getForcesVal()), BorderLayout.SOUTH);

				centerPanel.add(tradeButton, BorderLayout.WEST);

				ArrayList<Country> neighs = curr.getNeighbors();
				JPanel neighPanel = new JPanel();
				neighPanel.setLayout(new GridLayout(neighs.size(), 0));
				for (int i = 0; i < neighs.size(); i++)
					neighPanel.add(new JLabel(neighs.get(i).getName()));
				centerPanel.add(neighPanel, BorderLayout.CENTER);
				this.add(centerPanel);
				this.add(makeAMoveButton, BorderLayout.SOUTH);*/
				
				//This never changes - always displayed if a country is displayed!
				makeTopLabel();
				
				if (theGame.isPlacePhase()){
					makePlacementCenterPanel();
					makePlacementBottomLabel();
				}//end if
				else if (theGame.isReinforcePhase()){
					makePlayingCenterPanel();
					makePlacementBottomLabel();
				}//end else if
				else{
					makePlayingCenterPanel();
					makePlayingCardPanel();
					
					if (currPlayer.equals(curr.getOccupier())){
						makePlayingMyCountryBottomLabel();
					}//end if
					else {
						makePlayingYourCountryBottomLabel();
					}//end else
				}//end outer else
				
				
			}//end updatePanel


		}// end

		private class TradeClickListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("You wish to trade.");
				
				ArrayList cards = new ArrayList<Card>();
				theGame.redeemCards(nextPlayer, cards);
				//centerPanel.updatePanel();
				repaint();
			}
		}
		
	}// end countryPanel

	// help button listener for opening the about
	private class helpListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().compareTo("rules") == 0) {

				JOptionPane
						.showMessageDialog(
								riskGUI.this,
								"Fill this out later, maybe with a hyperlink to the rules",
								"Rules", JOptionPane.INFORMATION_MESSAGE);
			} else
				JOptionPane
						.showMessageDialog(
								riskGUI.this,
								"This version of Risk was created by Dylan Tobia,\nAbigail Dodd, Sydney Komro, and Jewell Finder."
										+ "\nCreated for our CS335 class as our final project.",
								"About", JOptionPane.INFORMATION_MESSAGE);

		}

	}// end helpListener

	private class CountryClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(e.getActionCommand() + " pressed.");
			// step through all countries until the same name as the
			// actionCommand, then return that country

			for (Country country : theGame.getGameMap().getCountries()) {
				if (country.getName().compareTo(e.getActionCommand()) == 0)
					theGame.setSelectedCountry(country);
			}
			drawingPanel.repaint();
		}
	}// end class

	private class makeMoveListener implements ActionListener {

	
		@Override
		public void actionPerformed(ActionEvent arg0) {
			theGame.placeArmies(theGame.getSelectedCountry());
			drawingPanel.repaint();
			if (theGame.isPlacePhase()) {
				
				// next player place army
				if (theGame.getCurrentPlayer() instanceof AI) {
					while (theGame.isPlacePhase() && theGame.getCurrentPlayer() instanceof AI) {
						theGame.aiChoicePlacement();
						System.out.println("hi");
					}
				}
				
			} else if (theGame.isPlayPhase()) {
				//for now just allow players to move armies
				if(theGame.getSelectedCountry().getOccupier().equals(theGame.getCurrentPlayer()))
				{
					
					if(!moveUnitsFlag)
					{ 
						numOfUnitsToMove = theGame.getUnitsToMove(theGame.getSelectedCountry());
						if(numOfUnitsToMove > 0)
							moveUnitsFlag = true;
					}
					else
					{
						theGame.getSelectedCountry().setForcesVal(numOfUnitsToMove);
						moveUnitsFlag = false;
					}

				}
			} else if (theGame.isReinforcePhase()) {

				// player can reinforce countries

				if (theGame.getCurrentPlayer() instanceof AI) {
					while (theGame.getCurrentPlayer() instanceof AI) {
						theGame.aiReinforcePlacement();
					}// end while
				}// end if
			}// end else if
			theGame.setSelectedCountry(null);
			drawingPanel.repaint();

		}// end actionPerformed

	}//end class

	
	private class NewGameListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			theGame.newGame();

		}//end action performed

	}//end game listener

	private class GameTypeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			gameType = arg0.getActionCommand();
			System.out.println(gameType);
			splashNumPlayers();

		}
	}
}

