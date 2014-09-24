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

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public interface MinXML extends List< MinXML > {
	String getName();
	boolean hasName( String name );
	void setName( String name ) throws UnsupportedOperationException;
		
	String getAttribute( String key );
	boolean hasAttribute();
	boolean hasAttribute( String key );
	boolean hasAttribute( String key, String value );
	boolean hasntAttribute();
	int sizeAttributes();
	Iterable< String > keys();
	Iterable< Map.Entry< String, String > > entries();
	Map< String, String > getAttributes();
	void putAttribute( String key, String value ) throws UnsupportedOperationException;
	void putAllAttributes( Map< String, String > map ) throws UnsupportedOperationException;
	void clearAttributes() throws UnsupportedOperationException;

	void trimToSize();
	
	boolean isntEmpty();
	boolean equals( Object obj );
	
	void print( PrintWriter pw );
	void prettyPrint( PrintWriter pw );
}
