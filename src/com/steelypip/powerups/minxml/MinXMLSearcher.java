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
