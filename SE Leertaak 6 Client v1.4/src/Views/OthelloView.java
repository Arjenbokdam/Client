package Views;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import Enums.Game;

public class OthelloView extends JPanel implements View {
	
	private static final long serialVersionUID = 1L;
	private OthelloButton[] buttons = new OthelloButton[64];
	private boolean[] board = new boolean[64];
	
	public OthelloView() {
		setLayout(new GridLayout(8, 8));
		setPreferredSize(new Dimension(400, 400));
		
		createObjectsOfPanel();
	}

	@Override
	public void createObjectsOfPanel() {
		for(int i = 0; i < 64; i++) {
				buttons[i] = new OthelloButton();
				buttons[i].setActionCommand(Game.Othello.name() + i);
				add(buttons[i]);
		}
	}
	

	@Override
	public void setActionListener(ActionListener a) {
		for(int i = 0; i < 64; i++) {
				buttons[i].addActionListener(a);
				board[i] = true;
		}
	}
	
	public void setMove(int value,int place) {
		buttons[place].setMove(value);
		board[place] = false;
	}
	
	public void setYourTurn(boolean yourTurn) {
		if(yourTurn) {
			unlockButtons();
		}
		else {
			lockButtons();
		}
	}
	
	private void lockButtons() {
		for(int i = 0; i < 64; i++) {
			buttons[i].setEnabled(false);
		}
	}
	
	private void unlockButtons() {
		for(int i = 0; i < 64; i++) {
			if(board[i]) {
				buttons[i].setEnabled(true);
			}
		}
	}
}

