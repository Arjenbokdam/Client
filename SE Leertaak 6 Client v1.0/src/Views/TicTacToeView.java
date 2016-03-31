package Views;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class TicTacToeView extends JPanel implements View {
	
	private static final long serialVersionUID = 1L;
	private TicTacToeButton[] buttons = new TicTacToeButton[9];
	private boolean[] board = new boolean[9];
	
	public TicTacToeView() {
		setLayout(new GridLayout(3, 3));
		setPreferredSize(new Dimension(400, 400));
		
		createObjectsOfPanel();
	}

	@Override
	public void createObjectsOfPanel() {
		for(int i = 0; i < 9; i++) {
			buttons[i] = new TicTacToeButton(i);
			buttons[i].setActionCommand("TTTBUTTON" + i);
			add(buttons[i]);
		}

	}

	@Override
	public void setActionListener(ActionListener a) {
		for(int i = 0; i < 9; i++) {
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
		for(int i = 0; i < 9; i++) {
			buttons[i].setEnabled(false);
		}
	}
	
	private void unlockButtons() {
		for(int i = 0; i < 9; i++) {
			if(board[i]) {
				buttons[i].setEnabled(true);
			}
		}
	}
}
