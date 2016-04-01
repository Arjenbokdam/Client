package MVC;

import Controller.ClientController;

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
		setController(new ClientController());
	}

	public static ClientController getController() {
		return controller;
	}

	public static void setController(ClientController controller) {
		ClientMVC.controller = controller;
	}

}
