package Views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LobbyView extends JPanel implements View {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<String> players;
	
	private JButton updatePlayerListButton;
	
	private ArrayList<JLabel> playerNames;
	private ArrayList<JButton> playerButtons;
	
	private ArrayList<Player> allPlayers = new ArrayList<Player>();
	
	private JPanel playerPanel = new JPanel();

	public LobbyView(ArrayList<String> players) {
		this.players = players;
		this.setLayout(new BorderLayout(1, 2));
		
		playerPanel = new JPanel();
		playerPanel.setLayout(new GridLayout(players.size() + 1, 2));
		
		playerNames = new ArrayList<>();
		playerButtons = new ArrayList<>();
		
		createObjectsOfPanel();
	}
	
	@Override
	public void createObjectsOfPanel() {
		createPlayers();
		updatePlayerListButton = new JButton("Update playerlist");
		updatePlayerListButton.setActionCommand("UPDATEPLAYERLIST");
		add(playerPanel, BorderLayout.NORTH);
		add(updatePlayerListButton, BorderLayout.SOUTH);
	}

	@Override
	public void setActionListener(ActionListener a) {
		for(int i = 0; i < allPlayers.size(); i++) {
			Player current = allPlayers.get(i);
			
			for (JButton button : current.buttons) {
				button.addActionListener(a);
			}
			
		}
		updatePlayerListButton.addActionListener(a);
	}
	
	private void createPlayers(){
			for(int i = 0; i < players.size(); i++) {
				
				JPanel panel = new JPanel();
				panel.setLayout(new FlowLayout());
				
				JLabel label = new JLabel(players.get(i));
				panel.add(label);
				JPanel grid = new JPanel();
				grid.setLayout(new GridLayout(1,2));
				
				
				
				JButton button1 = new JButton("TicTacToe");
				button1.setActionCommand("CHALLENGE" +":"+ i + ":" + "Tic Tac Toe v1.4");
				JButton button2 = new JButton("Othello");
				button2.setActionCommand("CHALLENGE" +":"+ i +":"+"Othello v1.1");
				grid.add(button1);
				grid.add(button2);
					
				playerButtons.add(button1);
				playerButtons.add(button2);

				panel.add(grid);
				playerPanel.add(panel);
				
				ArrayList<JButton> tempbuttons = new ArrayList<JButton>();
				tempbuttons.add(button1);
				tempbuttons.add(button2);
				
				allPlayers.add(new Player(label,tempbuttons));
		
		}
	}

}


class Player{
	public JLabel name;
	public ArrayList<JButton> buttons;
	
	public Player(JLabel name, ArrayList<JButton> buttons){
		this.name = name;
		this.buttons = buttons;
	}
	
}
