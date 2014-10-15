package com.steelypip.powerups.minxml;

public interface MinXMLWalker {
	/**
	 * startWalk is called at the start of the tree-walk of the subject and its children. 
	 * It will be called once for each node in the tree.
	 * 
	 * @param subject the MinXML element to be visited
	 */
	void startWalk( MinXML subject );

	
	/**
	 * endWalk is called at the end of the tree-walk of the subject and its children.
	 * It will be called once for each node in the tree.
	 * 
	 * @param subject the MinXML element to be visited
	 */
	void endWalk( MinXML subject );
}
