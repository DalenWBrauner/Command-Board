package interfacedemo.GUI;

import javafx.scene.Node;
import javafx.scene.Parent;

public class Utility {

	/** Prevents a pane (and all of its children) from blocking
	 * clicks to panes underneath it.
	 *  
	 * Recursively calls 'setPickOnBounds(false)' on the children
	 * of our children, of their children, and so on.
	 * 
	 */
	public static void giveChildrenClickTransparency(Node n) {
		n.setPickOnBounds(false);
		if (n instanceof Parent) {
			for (Node c: ((Parent) n).getChildrenUnmodifiable()) {
				giveChildrenClickTransparency(c);
			}
		}
	}
}
