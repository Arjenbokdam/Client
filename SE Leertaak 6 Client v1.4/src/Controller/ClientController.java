package Controller;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Enums.Game;
import Model.ClientModel;
import Views.ChooseGameView;
import Views.LobbyView;
import Views.LoginView;
import Views.OthelloView;
import Views.ServerMessagesView;
import Views.TicTacToeView;

/**
 * Controller for the Client application.
 * 
 * @author Tobias Schlichter
 * @version 1.1
 */
public class ClientController implements ActionListener {
	
	private JFrame frame;
	private ClientModel model;
	
	public ServerMessagesView serverMessagesView;
	private LoginView loginView;
	private ChooseGameView chooseGameView;
	public TicTacToeView tictactoeView;
	public OthelloView othelloView;
	private LobbyView lobbyView;
	
	private String CurrentGame ;
	
	private final static String TICTACTOEFILENAME = "Tic Tac Toe v1.4" ;
	private final static String OTHELLOFILENAME = "Othello v1.1" ;
	
	private final static String CLIENTVERSION = "Client v1.6" ;
	
	/**
	 * Constructor for a new ClientController object.
	 * Creates all the views of the application and sets the ActionListeners.
	 * Creates a frame for the different screens.
	 * Shows the login screen.
	 * 
	 * @param login LoginView that needs to be in the frame.
	 * @param chooseGame ChooseGameView that needs to be in the frame.
	 */
	public ClientController(){
		loginView = new LoginView();
		chooseGameView = new ChooseGameView();
		tictactoeView = new TicTacToeView();
		othelloView = new OthelloView();
		serverMessagesView = new ServerMessagesView();
		
		loginView.setActionListener(this);
		chooseGameView.setActionListener(this);	
		tictactoeView.setActionListener(this);
		othelloView.setActionListener(this);
		
		model = new ClientModel(this);
		CurrentGame = "";
		
		frame = new JFrame(CLIENTVERSION);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(2,2));
		frame.setVisible(true);

		showLoginScreen();
	}

	/**
	 * The ActionListeners for the different buttons.
	 * Creates actions for when buttons are called.
	 * 
	 * @param e ActionEvent the event that is called.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("TICTACTOEGAME")) {
			try {
				model.sendToSocket("subscribe Tic Tac Toe v1.4");
				System.out.println("-- LOG: Tic Tac Toe Game --");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getActionCommand().equals("OTHELLOGAME")) {
			try {
				model.sendToSocket("subscribe Othello v1.1");
				System.out.println("-- LOG: Othello Game --");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getActionCommand().equals("USERLOGIN")) {
			model.tryLogin(loginView.getLoginName());
		}
		if(e.getActionCommand().equals("USERLOGOUT")) {
			try {
				model.sendToSocket("logout");
				
				System.out.println("-- LOG: Logout --");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			showLoginScreen();
		}
		// TicTacToe Buttons
		if(e.getActionCommand().equals("TTTBUTTON0")) {
			model.setTTTMove(0);
		}
		if(e.getActionCommand().equals("TTTBUTTON1")) {
			model.setTTTMove(1);
		}
		if(e.getActionCommand().equals("TTTBUTTON2")) {
			model.setTTTMove(2);
		}
		if(e.getActionCommand().equals("TTTBUTTON3")) {
			model.setTTTMove(3);
		}
		if(e.getActionCommand().equals("TTTBUTTON4")) {
			model.setTTTMove(4);
		}
		if(e.getActionCommand().equals("TTTBUTTON5")) {
			model.setTTTMove(5);
		}
		if(e.getActionCommand().equals("TTTBUTTON6")) {
			model.setTTTMove(6);
		}
		if(e.getActionCommand().equals("TTTBUTTON7")) {
			model.setTTTMove(7);
		}
		if(e.getActionCommand().equals("TTTBUTTON8")) {
			model.setTTTMove(8);
		}
		
		//Othello buttons
		if(e.getActionCommand().substring(0,7).equals(Game.Othello.name())) {
			String move = e.getActionCommand().substring(7);
			int movePos;
		
			try {
				movePos = Integer.parseInt(move);
				model.setOthelloMove(movePos);
			} catch (NumberFormatException abc) {
				System.out.println("Parse Move error");
			}			
		}
		
		
		if(e.getActionCommand().equals("UPDATEPLAYERLIST")) {
			model.updateOnlinePlayers();
		}
		
		//challeng buttons
		if(e.getActionCommand().length()>9)
		if( e.getActionCommand().substring(0,9).equals("CHALLENGE")) {
			try {
				String command = e.getActionCommand();
				String player =   model.getOnlinePlayers().get(Integer.parseInt(command.substring(command.indexOf(":")+1,command.indexOf(":")+2)));
				String game =  command.substring(command.lastIndexOf(":")+1);
				
				if(game.equals(Game.Othello.name())){
					game = OTHELLOFILENAME;
				}else if(game.equals(Game.TicTacToe.name())){
					game = TICTACTOEFILENAME;
				}
				
				model.sendToSocket("challenge " + '"' + player + '"' + " " + '"' + game + '"');
				CurrentGame = game;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Removes all other frames and shows the login screen.
	 */
	private void showLoginScreen() {
		clearComponents();
		
		frame.add(loginView);
		frame.add(serverMessagesView);
		
		loginView.setVisible(true);
		serverMessagesView.setVisible(true);
		
		frame.pack();
	}
	
	/*
	/**
	 * Removes all other frames and shows the choose game screen.
	 
	private void showChooseGameScreen() {
		clearComponents();
		
		frame.add(chooseGameView);
		frame.add(serverMessagesView);
		
		chooseGameView.setVisible(true);
		serverMessagesView.setVisible(true);
		
		frame.pack();
	}
	*/
	
	public void setMove(int value, String move ){
		if(CurrentGame.equals(TICTACTOEFILENAME)){
			
			int temp = Integer.parseInt(move.substring(0,1));
			tictactoeView.setMove(value, temp);
			
		}else if(CurrentGame.equals(OTHELLOFILENAME)){
			
			int X = Integer.parseInt(move.substring(0,1));
			int Y = Integer.parseInt(move.substring(2,3));
			
			
			int temp = translateMove(X,Y);

			othelloView.setMove(value, temp);	
		}
	}
	/**
	 * 
	 * @param X row
	 * @param Y column
	 * @return number between 0 and 63
	 */
	private int translateMove(int X, int Y){
		int counter = 0;
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				if(X == i && Y == j){
					return counter;
				}
				counter++;
			}
		}
		return 0;
		
	}
	
	public void setYourTurn(boolean turn){
		if(CurrentGame.equals(TICTACTOEFILENAME)){
			tictactoeView.setYourTurn(turn);
		}else if(CurrentGame.equals(OTHELLOFILENAME)){
			othelloView.setYourTurn(turn);	
		}
	}
	
	/**
	 * Removes all other frames and shows the Othello screen.
	 */
	public void showOthelloScreen() {
		othelloView = new OthelloView();
		othelloView.setActionListener(this);
		
		clearComponents();
		
		frame.add(othelloView);
		frame.add(serverMessagesView);
		
		othelloView.setVisible(true);
		serverMessagesView.setVisible(true);
		
		frame.pack();
	}
	
	/**
	 * Removes all other frames and shows the Tic Tac Toe screen.
	 */
	public void showTicTacToeScreen() {
		tictactoeView = new TicTacToeView();
		tictactoeView.setActionListener(this);
		
		clearComponents();
		
		frame.add(tictactoeView);
		frame.add(serverMessagesView);
		
		tictactoeView.setVisible(true);
		serverMessagesView.setVisible(true);
		
		frame.pack();
	}
	
	public void showLobbyScreen() {
		clearComponents();
		
		lobbyView = new LobbyView(model.getOnlinePlayers());
		lobbyView.setActionListener(this);

		frame.add(lobbyView);
		frame.add(serverMessagesView);
		
		lobbyView.setVisible(true);
		serverMessagesView.setVisible(true);
		
		frame.pack();
	}	
	
	public void popupChallange(String name, String gameName, String challengeNumber) {
		Object[] options = {"Yes, I accept", "No, I refuse"};
        int n = JOptionPane.showOptionDialog(frame,
                        name + " challanges you for a game " + gameName,
                        "CHALLANGE",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);
        if (n == JOptionPane.YES_OPTION) {
            try {
				model.sendToSocket("challenge accept " + challengeNumber);
	            System.out.println("Yes I accept!");
	            CurrentGame = gameName;
			} catch (IOException e) {
				e.printStackTrace();
			}
        } else if (n == JOptionPane.NO_OPTION) {
            System.out.println("No I refuse!");
            CurrentGame = "";
        } else {
            System.out.println("NO OPTION");
        }
	}
	
	/**
	 * Clears the components of the frame so a clean screen is shown.
	 */
	private void clearComponents() {
		frame.remove(chooseGameView);
		frame.remove(loginView);
		frame.remove(tictactoeView);
		frame.remove(othelloView);
		frame.remove(serverMessagesView);
		
		if(lobbyView != null) {
			frame.remove(lobbyView);
		}
		
		chooseGameView.setVisible(false);
		loginView.setVisible(false);
		tictactoeView.setVisible(false);
		othelloView.setVisible(false);
	}

}
