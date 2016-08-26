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
package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * This interface linearises the construction of a Fusion
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
public interface FusionBuilder {

	/**
	 * This method should be called to begin the construction of
	 * a start-tag with a particular element name. When the 
	 * element is linked to the parent element, the field is 
	 * used for the link.
	 * 
	 * Name and field may be null, 
	 * in which case they must be supplied as non-null later.
	 * If allow_repeats is specified, repeats are allowed.
	 * 
	 * After this method, the next builder method should be
	 * add or endTag. 
	 * 
	 * @param name the name of the element to be constructed (or null).
	 * @param field the field to be used for the link to the parent (or null) 
	 * @param allow_repeats 
	 */
	void startTag( String field, String name, Boolean allow_repeats ) throws NullPointerException;
	void startTag( String field, String name ) throws NullPointerException;

	
	
	/**
	 * Shorthand for this.startTagOpen( name, null )
	 * 
	 * @param name the name of the element to be constructed (or null). 
	 */

	void startTag( String name );
	
	/**
	 * Shorthand for this.startTagOpen( null, null )
	 */
	void startTag();
	
	/**
	 * This method adds the attribute key=value to the start tag
	 * that is under construction. 
	 * 
	 * @param key the attribute key 
	 * @param value the attribute value
	 */
	void add( @NonNull String key, @NonNull String value );
	
	/**
	 * This method adds the attribute key=value to the start tag
	 * that is under construction. There must be no previous
	 * attributes with the same key.
	 * 
	 * @param key the attribute key
	 * @param value the attribute value
	 */
	void addNew( @NonNull String key, @NonNull String value );
	
	/**
	 * This method adds the attribute key=value to the start tag
	 * that is under construction. This must be the first
	 * use of that attribute key. The boolean allow_repeats determines
	 * whether or not there may already be an attribute with the
	 * same key.
	 */
	default void add( @NonNull String key, @NonNull String value, boolean allow_repeats ) {
		if ( allow_repeats ) {
			this.add( key, value );
		} else {
			this.addNew( key, value );
		}
	}
	
	/**
	 * Adds a child element that corresponds to the null JSON value into the
	 * element under construction. The field defaults to  
	 */
	void addNull();
	void addNull( String field );
	
	void addInteger( long number );
	void addInteger( String field, long number );
	
	void addFloat( double number );
	void addFloat( String field, double number );
	
	void addString( @Nullable String string );
	void addString( String field, @Nullable String string );

	void addBoolean( boolean bool );
	void addBoolean( String field, boolean bool );
	
	void addChild( @Nullable Fusion x ); 
	void addChild( String field, @Nullable Fusion x ); 

	void startArray( String field );
	void endArray( String field );
	
	void startObject( String field );
	void endObject( String field );
	
	/**
	 * This method may be called at any time between startTag and 
	 * endTag in order to provide the name
	 * the currently constructing element. If the value of
	 * name is null, this method has no effect.
	 * 
	 * @param name the name of the element to be constructed (or null)
	 */
	void bindName( String name );
	
	/**
	 * This method may be called at any time between startTag and 
	 * endTag in order to provide the field
	 * of the currently constructing element. If the value
	 * of field is null, this method has no effect.
	 * 
	 * When the element is linked to the parent element, the field is 
	 * used for the link.
	 * 
	 * @param name the name of the element to be constructed (or null)
	 */
	void bindField( String field );
	
	/**
	 * This method may be called at any time between startTag and 
	 * endTag in order to provide the allow_repeats. If it is specified, 
	 * a repeated field is permitted. If the value is null the method
	 * has no effect.
	 * 
	 * @param name the name of the element to be constructed (or null)
	 */
	void bindAllowRepeats( Boolean allow_repeats );
	
	/**
	 * This method finishes the construction of the current element.
	 * If the tag-name is non-null then it must be in agreement with the
	 * previous value. If the previous value is null then it is automatically
	 * in agreement. 
	 * 
	 * @param name the name of the element to be constructed (or null) 
	 */
	void endTag( String field, String name, Boolean allow_repeats );
	void endTag( String field, String name );
	void endTag( String name );
	void endTag();
	
	/**
	 * This method returns the constructed tree. An implementation may
	 * elect to make the builder reusable again.
	 *  
	 * @return the constructed tree
	 */
	Fusion build();


}
