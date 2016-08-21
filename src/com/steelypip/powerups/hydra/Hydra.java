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
package com.steelypip.powerups.hydra;

import java.util.Map;

/**
 * MinXMLStar is a multi-valued extension of MinXML that, in its 
 * turn, is a cleaner, leaner, cut-down version of XML with only the
 * absolute essentials. An MinXMLStar object is a single element that 
 * represents a tree of nodes, where each node is a named, multi-valued 
 * dictionary. The MinXMLStar interface defines a standard interface 
 * for all classes representing MinXMLStar elements.
 * 
 * @author Stephen Leach
 */
public interface Hydra< Key extends Comparable< Key >, AttrValue, Field extends Comparable< Field >, ChildValue > 
extends 
	Named, 
	MultiAttributes< Key, AttrValue >, 
	MultiLinks< Field, ChildValue >, 
	Iterable< Map.Entry< Field, ChildValue > > 
{
	
	/**
	 * This method signals that there will be no further updates to an element, at least for a while, 
	 * and the implementation should consider this a good opportunity to compact the space used
	 * by this element and all child elements, including any shared elements. Implementations
	 * must be clear on whether any subsequent updates are allowed or forbidden. If subsequent updates
	 * are forbidden, the implementation must throw an {@link IllegalStateException}.
	 */
	default void trimToSize() {
		//	Skip.
	}
	
	boolean equals( Object obj );
	
	

	
//	/**
//	 * shallowCopy makes a copy of the topmost node but shares the children. The
//	 * implementation of the new node must be at least as general as the implementation
//	 * of this node i.e. implement all the methods that do not raise 
//	 * {@link java.lang.UnsupportedOperationException} 
//	 * @return a shallow copy
//	 */
//	@NonNull Hydra< Key, AttrValue, Field, ChildValue > shallowCopy();
//	 
//	/**
//	 * deepCopy makes a copy of the topmost node and all the children. The
//	 * implementation of the new nodes must be at least as general as the implementation
//	 * of this node i.e. implement all the methods that do not raise 
//	 * {@link java.lang.UnsupportedOperationException} 
//	 * @return a deep copy
//	 */
//	@NonNull Hydra< Key, AttrValue, Field, ChildValue > deepCopy();
//	
//	public static HydraXML< ? extends Comparable< ? > > fromReader( final Reader reader ) {
//		return new FusionParser( reader ).readElement();
//	}
//	
//	public static HydraXML< Key > fromString( final String string ) {
//		return fromReader( new StringReader( string ) );
//	}
	

	
}
