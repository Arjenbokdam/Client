package Views;

import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class LobbyView extends JPanel implements View{
	
	private JTextField playerField;
	private String[] players;
	private String playerList;
	private String game;

	public LobbyView(String game, String[] players) {
		this.game = game;
		this.players = players;
		
	}
	
	private void createTable(int length) {
		for(int i = 0; i < length; i++) {
			playerList += players[i] + "\n";
		}
	}
	
	@Override
	public void createObjectsOfPanel() {
		createTable(players.length);
		playerField = new JTextField(playerList);
	}

	@Override
	public void setActionListener(ActionListener a) {
		
	}

}
