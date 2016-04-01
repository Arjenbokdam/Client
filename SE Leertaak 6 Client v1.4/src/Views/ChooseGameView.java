package Views;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The view for the choose game screen.
 * 
 * @author Tobias Schlichter
 * @version 1.0
 */
public class ChooseGameView extends JPanel implements View {

	private static final long serialVersionUID = 1L;
	private JButton logoutButton;
	private JButton tictactoeButton;
	private JButton othelloButton;

	/**
	 * Constructor for a ChooseGameView object.
	 * Calls the createObjectsOfPanel method.
	 * Adds the buttons to this.
	 */
	public ChooseGameView() {
		createObjectsOfPanel();
		add(logoutButton);
		add(tictactoeButton);
		add(othelloButton);
	}
	
	/**
	 * Creates the objects that need to be on this screen.
	 */
	@Override
	public void createObjectsOfPanel() {
		logoutButton = new JButton("Logout");
		logoutButton.setActionCommand("USERLOGOUT");
		
		tictactoeButton = new JButton("Tic Tac Toe");
		tictactoeButton.setActionCommand("TICTACTOEGAME");
		
		othelloButton = new JButton("Othello");
		othelloButton.setActionCommand("OTHELLOGAME");
	}

	/**
	 * Sets the ActionListener to the buttons from this screen.
	 */
	@Override
	public void setActionListener(ActionListener a) {
		logoutButton.addActionListener(a);
		tictactoeButton.addActionListener(a);
		othelloButton.addActionListener(a);
	}

}
