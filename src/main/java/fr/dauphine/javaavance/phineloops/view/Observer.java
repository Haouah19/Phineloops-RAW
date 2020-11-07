package fr.dauphine.javaavance.phineloops.view;
/**
 * The responsibility of observers is to register  on a subject (to be informed of changes in state) 
 * and to update their state (synchronize their state with the state of the subject) when they are notified.
 */
public interface Observer {
	public void update();
}
