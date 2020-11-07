package fr.dauphine.javaavance.phineloops.model;

import fr.dauphine.javaavance.phineloops.view.Observer;
/**
 * 	keeps a list of its dependents, called observers, 
 * and automatically informs them of any change in state, usually by calling update().
 */
public interface Observable {
		public void addObserver(Observer obs);
		public void removeObserver();
		public void notifyObserver();
}
