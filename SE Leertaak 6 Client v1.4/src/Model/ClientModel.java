package Model;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Stack;

import Controller.ClientController;

/**
 * Client that uses the protocol of GameServer 1.0.
 * 
 * @author Tobias Schlichter
 * @version 1.1
 */
public class ClientModel {
	
	private ClientController controller;
	//private final String IPADDRESS = "127.0.0.1";
	private final String IPADDRESS = "82.73.252.248";
	
	private final int PORTNUMBER = 7789;
	private Socket socket;
	
	private String playerName;
	private Stack<String> serverMessages;
	private ArrayList<String> onlinePlayers = new ArrayList<>();	
	
	/**
	 * Constructor of the new Client object.
	 * Creates the Socket and a listener for the socket.
	 * Creates and starts a Thread for the listener.
	 * 
	 * @param args
	 */
	public ClientModel(ClientController controller) {
		this.controller = controller;
		serverMessages = new Stack<String>();
	}
	
	/**
	 * Creates a Socket that is connected with the server.
	 * 
	 * @throws UnknownHostException when the host is unknown.
	 * @throws IOException when the connection fails.
	 */
	private void createSocket() throws UnknownHostException, IOException {
		socket = new Socket(IPADDRESS, PORTNUMBER);
	}
	
	/**
	 * Sends a message to the Socket.
	 * 
	 * @param message the message that is send to the Socket.
	 * @throws IOException when the message failed to send.
	 */
	public void sendToSocket(String message) throws IOException {
		PrintWriter pout = new PrintWriter(socket.getOutputStream(), true);
		pout.println(message);
	}
	
	public void connectToServer() {
		try {
			createSocket();
			Thread listener = new Thread(new SocketListener(socket, this));
			listener.start();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("-- LOG: Connection refused, try again later --");
		}
	}
	
	public boolean tryLogin(String name) {
		connectToServer();
		String loginName = name;
		playerName = loginName;
		System.out.println("-- LOG: Login try | username: " + loginName + " -- ");
		if(!loginName.isEmpty()) {
			try {
				sendToSocket("login " + loginName);
				sendToSocket("get playerlist");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		return false;
	}
	
	public ArrayList<String> getOnlinePlayers() {
		return onlinePlayers;
	}
	
	private boolean parseOnlinePlayers(String input) {
		onlinePlayers = new ArrayList<>();
		boolean readingName = false;
		boolean skip = false;
		String playerName = "";
		for(int i = 0; i < input.length(); i++) {
			skip = false;
			if(readingName) {
				if(input.charAt(i) == '"') {
					if(!playerName.equals(this.playerName)) {
						onlinePlayers.add(playerName);
					}
					playerName = "";
					readingName = false;
					skip = true;
				} 
				else {
					playerName += input.charAt(i);
				}
				
			}
			if(input.charAt(i) == '"' && !skip) {
				readingName = true;
			}
		}
		for(int i = 0; i < onlinePlayers.size(); i++) {
			System.out.println("-- LOG: Online player " + i + ": " + onlinePlayers.get(i) + " --");
		}
		return true;
	}
	
	
	public void updateOnlinePlayers() {
		try {
			sendToSocket("get playerlist");
		} catch (IOException e) {
			e.printStackTrace();
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
			controller.serverMessagesView.setServerText(handleServerMessage());
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
		if(tempString.contains("Tic Tac Toe") && !tempString.contains("CHALLENGE")) {
			controller.showTicTacToeScreen();
		}
		if(tempString.contains("SVR GAME") && tempString.contains(" MOVE:") && !tempString.contains(playerName)) {
			controller.tictactoeView.setMove(1, Integer.parseInt(tempString.substring((tempString.indexOf("MOVE: ") + 7), (tempString.indexOf("MOVE: ") + 8))));
		}
		if(tempString.contains("YOURTURN")) {
			controller.tictactoeView.setYourTurn(true);
		}
		if(!tempString.contains("YOURTURN")) {
			controller.tictactoeView.setYourTurn(false);
		}		
		if(tempString.contains("SVR GAME DRAW") || tempString.contains("SVR GAME WIN") || tempString.contains("SVR GAME LOSS") ) {
			updateOnlinePlayers();
		}
		if(tempString.contains("CHALLENGE ") && tempString.contains("CHALLENGER")) {			
			String challanger = parseInput(tempString, "CHALLENGER: ");
			String gameName = parseInput(tempString, "GAMETYPE: ");
			String challengeNumber = parseInput(tempString, "CHALLENGENUMBER: ");
			
			controller.popupChallange(challanger, gameName, challengeNumber);
		}
		if(tempString.contains("PLAYERLIST")) {
			if(parseOnlinePlayers(tempString)) {
				controller.showLobbyScreen();
			}
		}
		
		return tempString;
	}
	
	private String parseInput(String full, String partBefore) {
		int startInt = full.indexOf(partBefore) + partBefore.length() + 1;
		
		int endInt = startInt;
		boolean searching = true;
		
		while(searching) {
			if(full.charAt(endInt) == '"') {
				searching = false;
			}
			else {
				endInt++;
			}
		}
		
		return full.substring(startInt, endInt);
	}
	
	/**
	 * Sends a move for the Tic Tac Toe game.
	 * 
	 * @param i int that indicates the position on the board.
	 */
	public void setTTTMove(int i) {
		try {
			sendToSocket("move " + i);
			controller.tictactoeView.setMove(0, i);
			System.out.println("-- LOG: Move " + i + " --");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	
}
