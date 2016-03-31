package Views;

import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * View to show the messages from the server.
 * 
 * @author Tobias Schlichter
 * @version 1.0
 */
public class ServerMessagesView extends JPanel implements View {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JTextArea serverMessages;
	private String serverText = "";
	
	/**
	 * Constructor for a new ServerMessagesView.
	 */
	public ServerMessagesView() {
		createObjectsOfPanel();
		add(scrollPane);
	}
	
	/**
	 * Creates the objects for this View.
	 */
	@Override
	public void createObjectsOfPanel() {
		serverMessages = new JTextArea(10, 50);
		scrollPane = new JScrollPane(serverMessages);
		serverMessages.setEditable(false);
	}

	/**
	 * Sets ActionListeners for the View.
	 */
	@Override
	public void setActionListener(ActionListener a) {	
	}
	
	/**
	 * Adds to the server text.
	 * @param text String that needs to be added.
	 */
	public void setServerText(String text) {
		serverText = text + "\n" + serverText;
		serverMessages.setText(serverText);
	}
}
