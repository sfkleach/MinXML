package com.steelypip.powerups.minxml;

public interface MinXMLSearcher {

	/**
	 * startSearch is called at the start of the tree-walk of the subject and its children. 
	 * Return false if you want the the child elements to be visited, true otherwise.
	 * 
	 * @param subject the MinXML element to be visited
	 * @return flag saying if the goal was found
	 */
	boolean startSearch( MinXML subject );

	
	/**
	 * endSearch is called at the end of the tree-walk of the subject and its children.
	 * Return false normally but true to signal to the parent that the iteration over
	 * the child nodes can be stopped early. The found flag indicates if
	 * any child search found the goal, it is the short-circuit OR of all the
	 * flags returned by the children's endVisits.
	 * 
	 * @param subject the MinXML element to be visited
	 * @param found flag indicating if the goal was found
	 * @return flag saying if the goal was found
	 */
	boolean endSearch( MinXML subject, boolean found );
	
}
