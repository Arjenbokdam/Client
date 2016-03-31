package MVC;

import Controller.ClientController;
import Views.ChooseGameView;
import Views.LoginView;
import Views.ServerMessagesView;
import Views.TicTacToeView;

/**
 * Client written in MVC Structure.
 * 
 * @author Tobias Schlichter
 * @version 1.0
 *
 */
public class ClientMVC {
	
	private static ClientController controller;

	public static void main(String[] args) {
		
		controller = new ClientController();
	}

}
