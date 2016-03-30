import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * SocketListener class that implements a Runnable.
 * Is used as a Thread that reads the messages from the server.
 * 
 * @author Tobias Schlichter
 */
public class SocketListener implements Runnable {
	
	private Socket socket;

	/**
	 * Constructor for the SocketListener object.
	 * 
	 * @param socket Socket where the messages are read from.
	 */
	public SocketListener(Socket socket) {
		this.socket = socket;
	}
	
	/**
	 * Runs until the Thread is killed.
	 * Keeps listening while there is more input reads the line and prints it.
	 */
	@Override
	public void run() {
		InputStream in;
		try {
			in = socket.getInputStream();
			BufferedReader bin = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = bin.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
