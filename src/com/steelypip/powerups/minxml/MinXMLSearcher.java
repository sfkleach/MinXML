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

import java.util.Iterator;

import com.steelypip.powerups.common.EmptyIterator;

public abstract class MinXMLSearcher {

	/**
	 * startSearch is called at the start of the tree-walk of the subject and its children. 
	 * Return false if you want the the child elements to be visited, true otherwise.
	 * 
	 * @param subject the MinXML element to be visited
	 * @return flag saying if the children were cutoff.
	 */
	public abstract boolean startSearch( MinXML subject );

	
	/**
	 * endSearch is called at the end of the tree-walk of the subject and its children.
	 * Return false normally but true to signal to the parent that the iteration over
	 * the child nodes can be stopped early. The found flag indicates if
	 * any child search found the goal, it is the short-circuit OR of all the
	 * flags returned by the children's endVisits.
	 * 
	 * @param subject the MinXML element to be visited.
	 * @param cutoff flag indicating if any child search was cutoff.
	 * @return boolean a flag saying if sibling-search should be cutoff.
	 */
	public abstract boolean endSearch( MinXML subject, boolean cutoff );
	
	private static final Iterator< MinXML > empty = new EmptyIterator< MinXML >();
	
	/**
	 * The search method is used to implement basic recursive scans over a tree
	 * of elements. It is typically used to search a tree or to implement a series of
	 * in-place updates.
	 * 
	 * @param subject
	 * @return true indicates the search was cutoff. 
	 */
	public boolean search( final MinXML subject ) {
		boolean cutoff = this.startSearch( subject );
		final Iterator< MinXML > kids = cutoff ? empty : subject.iterator();
		while ( ! cutoff && kids.hasNext() ) {
			cutoff = this.search( kids.next() );
		}
		return this.endSearch( subject, cutoff );
	}
	
}
