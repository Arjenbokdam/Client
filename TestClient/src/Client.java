import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Client that uses the protocol of GameServer 1.0.
 * 
 * @author Tobias Schlichter
 */
public class Client {
	
	private static final String IPADDRESS = "82.73.252.248";
	private static final int PORTNUMBER = 7789;
	private static Socket socket;
	private static Scanner scanner;
	private static Thread listener;
	
	/**
	 * Constructor of the new Client object.
	 * Creates the Socket and a listener for the socket.
	 * Creates and starts a Thread for the listener.
	 * Creates a scanner that reads the input from the user.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			createSocket();
			listener = new Thread(new SocketListener(socket));
			listener.start();
			scanner = new Scanner(System.in);
			
			while(scanner.hasNextLine()) {
				sendToSocket(scanner.nextLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a Socket that is connected with the server.
	 * 
	 * @throws UnknownHostException when the host is unknown.
	 * @throws IOException when the connection fails.
	 */
	private static void createSocket() throws UnknownHostException, IOException {
		socket = new Socket(IPADDRESS,PORTNUMBER);
	}
	
	/**
	 * Sends a message to the Socket.
	 * 
	 * @param message the message that is send to the Socket.
	 * @throws IOException when the message failed to send.
	 */
	private static void sendToSocket(String message) throws IOException {
		PrintWriter pout = new PrintWriter(socket.getOutputStream(), true);
		pout.println(message);
	}
}
