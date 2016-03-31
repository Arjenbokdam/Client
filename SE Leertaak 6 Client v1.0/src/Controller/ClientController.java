package Controller;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Stack;

import javax.swing.JFrame;

import Model.ClientModel;
import Views.ChooseGameView;
import Views.LoginView;
import Views.ServerMessagesView;
import Views.TicTacToeView;

/**
 * Controller for the Client application.
 * 
 * @author Tobias Schlichter
 * @version 1.0
 */
public class ClientController implements ActionListener {
	
	private JFrame frame;
	private ClientModel model;
	
	private String playerName = "";
	
	private ServerMessagesView serverMessagesView;
	private LoginView loginView;
	private ChooseGameView chooseGameView;
	private TicTacToeView tictactoeView;
	
	private Stack<String> serverMessages = new Stack<>();
	
	
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
		serverMessagesView = new ServerMessagesView();
		
		loginView.setActionListener(this);
		chooseGameView.setActionListener(this);	
		tictactoeView.setActionListener(this);
		
		model = new ClientModel(this);
		
		frame = new JFrame("Client v1.0");
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
				model.sendToSocket("subscribe Othello");
				System.out.println("-- LOG: Othello Game --");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getActionCommand().equals("USERLOGIN")) {
			if(model.connectToServer()) {
				String loginName = loginView.getLoginName();
				playerName = loginName;
				System.out.println("-- LOG: Login try | username: " + loginName + " -- ");
				if(!loginName.isEmpty()) {
					try {
						model.sendToSocket("login " + loginName);
						showChooseGameScreen();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					System.out.println("-- LOG: No username entered -- ");
				}
			}
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
			setTTTMove(0);
		}
		if(e.getActionCommand().equals("TTTBUTTON1")) {
			setTTTMove(1);
		}
		if(e.getActionCommand().equals("TTTBUTTON2")) {
			setTTTMove(2);
		}
		if(e.getActionCommand().equals("TTTBUTTON3")) {
			setTTTMove(3);
		}
		if(e.getActionCommand().equals("TTTBUTTON4")) {
			setTTTMove(4);
		}
		if(e.getActionCommand().equals("TTTBUTTON5")) {
			setTTTMove(5);
		}
		if(e.getActionCommand().equals("TTTBUTTON6")) {
			setTTTMove(6);
		}
		if(e.getActionCommand().equals("TTTBUTTON7")) {
			setTTTMove(7);
		}
		if(e.getActionCommand().equals("TTTBUTTON8")) {
			setTTTMove(8);
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
	
	/**
	 * Removes all other frames and shows the choose game screen.
	 */
	private void showChooseGameScreen() {
		clearComponents();
		
		frame.add(chooseGameView);
		frame.add(serverMessagesView);
		
		chooseGameView.setVisible(true);
		serverMessagesView.setVisible(true);
		
		frame.pack();
	}
	
	/**
	 * Removes all other frames and shows the Tic Tac Toe screen.
	 */
	private void showTicTacToeScreen() {
		tictactoeView = new TicTacToeView();
		tictactoeView.setActionListener(this);
		
		clearComponents();
		
		frame.add(tictactoeView);
		frame.add(serverMessagesView);
		
		tictactoeView.setVisible(true);
		serverMessagesView.setVisible(true);
		
		frame.pack();
	}
	
	/**
	 * Sends a move for the Tic Tac Toe game.
	 * 
	 * @param i int that indicates the position on the board.
	 */
	private void setTTTMove(int i) {
		try {
			model.sendToSocket("move " + i);
			tictactoeView.setMove(0, i);
			System.out.println("-- LOG: Move " + i + " --");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Is called to recieve and process messages from the server.
	 * 
	 * @param text String input.
	 */
	public void writeToServerMessages(String text) {
		if(!text.isEmpty()) {
			serverMessages.push(text);
			serverMessagesView.setServerText(handleServerMessage());
		}
	}
	
	/**
	 * Handles commands from the server.
	 * 
	 * @return String input command.
	 */
	private String handleServerMessage() {
		String tempString = serverMessages.pop();
		
		System.out.println("Pop : " + tempString);
		
		if(tempString.contains("OK")) {
			return "Action OK";
		}
		if(tempString.contains("Tic Tac Toe")) {
			showTicTacToeScreen();
		}
		if(tempString.contains("SVR GAME") && tempString.contains(" MOVE:") && !tempString.contains(playerName)) {
			tictactoeView.setMove(1, Integer.parseInt(tempString.substring((tempString.indexOf("MOVE: ") + 7), (tempString.indexOf("MOVE: ") + 8))));
		}
		if(tempString.contains("YOURTURN")) {
			tictactoeView.setYourTurn(true);
		}
		if(!tempString.contains("YOURTURN")) {
			tictactoeView.setYourTurn(false);
		}		
		if(tempString.contains("SVR GAME") && tempString.contains("Win")) {
			showChooseGameScreen();
		}
		if(tempString.contains("SVR GAME") && tempString.contains("Draw")) {
			showChooseGameScreen();
		}
		
		return tempString;
	}
	
	/**
	 * Clears the components of the frame so a clean screen is shown.
	 */
	private void clearComponents() {
		frame.remove(chooseGameView);
		frame.remove(loginView);
		frame.remove(tictactoeView);
		frame.remove(serverMessagesView);
		
		chooseGameView.setVisible(false);
		loginView.setVisible(false);
		tictactoeView.setVisible(false);
	}

}
