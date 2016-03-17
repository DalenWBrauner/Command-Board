package interfacedemo.questions;

import shared.interfaces.PlayerRepresentative;

/** A GUI element that presents a question to
 *  the user through which they can control their Player.
 * e.g.
 * Do you want to move left or right?
 * Do you want to buy this tile?
 * etc.
 * @author Dalen W. Brauner
 */
public interface PlayerQuestion<T> {
	
	/** */
	public void setRepresentative(PlayerRepresentative rep);
	
	/** Set the text of the question being asked.
	 * This shouldn't make it into the final version,
	 * As not all of these should be dialogues that ask specific questions.
	 */
	public void setQuestion(String q);
	
	/** Set the options for the user to choose from. */
	public void setOptions(T[] options);
	
	/** Show the question on the GUI. */
	public void show();
	
	/** Hide the question on the GUI. */
	public void hide();
	
	/** Toggle whether the question can be seen on the GUI. */
	public void toggleVisibility();
	
	/** Reset all publicly-editable data, including
	 * the user's choices and answers. */
	public void reset();
	
	/** Forget the user's previous answer. */
	public void resetAnswer();
	
	/** Retrieve the user's selection. */
	public T getAnswer();
}
