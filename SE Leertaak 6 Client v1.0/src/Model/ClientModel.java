package Model;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import Controller.ClientController;

/**
 * Client that uses the protocol of GameServer 1.0.
 * 
 * @author Tobias Schlichter
 * @version 1.1
 */
public class ClientModel {
	
	//private final String IPADDRESS = "127.0.0.1";
	private final String IPADDRESS = "82.73.252.248";
	private final int PORTNUMBER = 7789;
	private Socket socket;
	private Thread listener;
	private ClientController controller;
	
	/**
	 * Constructor of the new Client object.
	 * Creates the Socket and a listener for the socket.
	 * Creates and starts a Thread for the listener.
	 * 
	 * @param args
	 */
	public ClientModel(ClientController controller) {
		this.controller = controller;
	}
	
	/**
	 * Creates a Socket that is connected with the server.
	 * 
	 * @throws UnknownHostException when the host is unknown.
	 * @throws IOException when the connection fails.
	 */
	private void createSocket() throws UnknownHostException, IOException {
		socket = new Socket(IPADDRESS,PORTNUMBER);
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
	
	public boolean connectToServer() {
		try {
			createSocket();
			listener = new Thread(new SocketListener(socket, controller));
			listener.start();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("-- LOG: Connection refused, try again later --");
			return false;
		}
	}
}
