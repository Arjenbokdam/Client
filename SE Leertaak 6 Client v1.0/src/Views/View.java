package Views;

import java.awt.event.ActionListener;

/**
 * Interface for a new View.
 * 
 * @author Tobias Schlichter
 * @version 1.0
 */
public interface View {
	
	/**
	 * Creates the objects that need to be on this screen.
	 */
	void createObjectsOfPanel();
	
	/**
	 * Sets the ActionListener to the buttons from this screen.
	 */
	void setActionListener(ActionListener a);
}
