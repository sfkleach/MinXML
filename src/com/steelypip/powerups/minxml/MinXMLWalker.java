/**
 * Copyright Stephen Leach, 2014
 * This file is part of the MinXML for Java library.
 * 
 * MinXML for Java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MinXML for Java.  If not, see <http://www.gnu.org/licenses/>.
 *  
 */

package com.steelypip.powerups.minxml;

public abstract class MinXMLWalker {
	/**
	 * startWalk is called at the start of the tree-walk of the subject and its children. 
	 * It will be called once for each node in the tree.
	 * 
	 * @param subject the MinXML element to be visited
	 */
	public abstract void startWalk( MinXML subject );

	
	/**
	 * endWalk is called at the end of the tree-walk of the subject and its children.
	 * It will be called once for each node in the tree.
	 * 
	 * @param subject the MinXML element to be visited
	 */
	public abstract void endWalk( MinXML subject );
	

	
	/**
	 * The walk method is used to implement a depth-first, left-to-right recursive 
	 * scan over a tree. The startWalk and endWalk methods are invoked on the way
	 * down the tree and up the tree respectively. 
	 * 
	 * @param subject
	 */
	public void walk( final MinXML subject ) {
		this.startWalk( subject );
		for ( MinXML kid : subject ) {
			this.walk(  kid );
		}
		this.endWalk( subject );
	}
}
