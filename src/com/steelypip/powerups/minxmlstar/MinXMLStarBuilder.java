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
package com.steelypip.powerups.minxmlstar;

import org.eclipse.jdt.annotation.NonNull;

/**
 * This interface linearises the construction of a MinXMLStar
 * tree. Start tags are constructed with at least two calls:
 * startTagOpen and startTagClose. In between these two calls
 * there can be any number of adds, which set up the 
 * attributes.
 * 
 * Note that startTagOpen, startTagClose and endTag all
 * take the element name as a parameter. When using this 
 * interface with an unknown implementation the same (equals) 
 * element name should be supplied for all three calls that 
 * construct a particular element.
 * 
 * However an implementation may elect to allow the
 * element name to be null on one or more of these calls
 * (or even all!). All non-null values should be
 * the same and that must be the final value of the 
 * element name.
 *
 */
public interface MinXMLStarBuilder {

	/**
	 * This method should be called to begin the construction of
	 * a start-tag with a particular element name. When the 
	 * element is linked to the parent element, the field is 
	 * used for the link.
	 * 
	 * An implementation may permit name and field to be null, 
	 * in which case they must be supplied as non-null later.
	 * 
	 * After this method, the next builder method should be
	 * add or startTagClose. 
	 * 
	 * @param name the name of the element to be constructed (or null).
	 * @param field the field to be used for the link to the parent (or null) 
	 */
	void startTagOpen( String name, String field ) throws NullPointerException;

	
	/**
	 * Shorthand for this.startTagOpen( name, null )
	 * 
	 * @param name the name of the element to be constructed (or null). 
	 */

	void startTagOpen( String name );
	
	/**
	 * Shorthand for this.startTagOpen( null, null )
	 */

	void startTagOpen();
	
	/**
	 * This method adds the attribute key=value to the start tag
	 * that is under construction. The builder method startTagOpen must
	 * have been the immediately previous method.
	 * @param key the attribute key 
	 * @param value the attribute value
	 */
	void add( @NonNull String key, @NonNull String value );
	
	/**
	 * This method finishes the construction of the current start tag.
	 * It may be followed by a call to endTag or startTagOpen. If the
	 * tag name is not-null it must agree with the previous value. If
	 * the previous value was null then it automatically is in agreement.
	 * 
	 * An implementation may choose to make startTagClose optional,
	 * implicitly closing it when the next startTagOpen is invoked.
	 * 
	 * When the element is linked to the parent element, the field is 
	 * used for the link.
	 * 
	 * An implementation may permit name and field to be null, 
	 * in which case they must be supplied as non-null elsewhere.
	 * 
	 * @param name the name of the element to be constructed (or null)
	 */
	void startTagClose( String name, String field );
	void startTagClose( String name );
	void startTagClose();
	
	/**
	 * This method finishes the construction of the current element.
	 * If the tag-name is non-null then it must be in agreement with the
	 * previous value. If the previous value is null then it is automatically
	 * in agreement. 
	 * 
	 * @param name the name of the element to be constructed (or null) 
	 */
	void endTag( String name, String field );
	void endTag( String name );
	void endTag();
	
	/**
	 * This method returns the constructed tree. An implementation may
	 * elect to make the builder reusable again.
	 *  
	 * @return the constructed tree
	 */
	MinXMLStar build(); 
}
