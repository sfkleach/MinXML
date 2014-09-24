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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.steelypip.powerups.common.NullIndenter;
import com.steelypip.powerups.common.StdIndenter;
import com.steelypip.powerups.io.StringPrintWriter;

public abstract class AbsMinXML implements MinXML {
	
	
	//////////////////////////////////////////////////////////////////////////////////
	//	Foundational abstract methods.
	//////////////////////////////////////////////////////////////////////////////////

	@Override
	public abstract String getName();
	
	@Override
	public abstract void setName( String name ) throws UnsupportedOperationException;

	@Override
	public abstract Map< String, String > getAttributes();
		
	public abstract List< MinXML > toList();
	

	//////////////////////////////////////////////////////////////////////////////////
	//	Methods built on the essential foundations.
	//////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void trimToSize() {
	}

	public void add( int index, MinXML element ) {
		toList().add( index, element );
	}

	public boolean add( MinXML e ) {
		return toList().add( e );
	}

	public boolean addAll( Collection< ? extends MinXML > c ) {
		return toList().addAll( c );
	}

	public boolean addAll( int index, Collection< ? extends MinXML > c ) {
		return toList().addAll( index, c );
	}

	public void clear() {
		toList().clear();
	}

	public boolean contains( Object o ) {
		return toList().contains( o );
	}

	public boolean containsAll( Collection< ? > c ) {
		return toList().containsAll( c );
	}

	public boolean equals( Object o ) {
		return toList().equals( o );
	}

	public MinXML get( int index ) {
		return toList().get( index );
	}

	public int hashCode() {
		return toList().hashCode();
	}

	public int indexOf( Object o ) {
		return toList().indexOf( o );
	}

	public boolean isEmpty() {
		return toList().isEmpty();
	}

	public Iterator< MinXML > iterator() {
		return toList().iterator();
	}

	public int lastIndexOf( Object o ) {
		return toList().lastIndexOf( o );
	}

	public ListIterator< MinXML > listIterator() {
		return toList().listIterator();
	}

	public ListIterator< MinXML > listIterator( int index ) {
		return toList().listIterator( index );
	}

	public MinXML remove( int index ) {
		return toList().remove( index );
	}

	public boolean remove( Object o ) {
		return toList().remove( o );
	}

	public boolean removeAll( Collection< ? > c ) {
		return toList().removeAll( c );
	}

	public boolean retainAll( Collection< ? > c ) {
		return toList().retainAll( c );
	}

	public MinXML set( int index, MinXML element ) {
		return toList().set( index, element );
	}

	public int size() {
		return toList().size();
	}

	public List< MinXML > subList( int fromIndex, int toIndex ) {
		return toList().subList( fromIndex, toIndex );
	}

	public Object[] toArray() {
		return toList().toArray();
	}

	public < T > T[] toArray( T[] a ) {
		return toList().toArray( a );
	}


	//////////////////////////////////////////////////////////////////////////////////
	//	Implementations built on the abstract methods.
	//////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public boolean hasName( String name ) {
		return this.getName().equals( name );
	}

	@Override
	public String getAttribute( String key ) {
		return this.getAttributes().get( key );
	}

	@Override
	public boolean hasAttribute() {
		return ! this.getAttributes().isEmpty();
	}

	@Override
	public boolean hasAttribute( String key ) {
		return this.getAttributes().containsKey( key );
	}

	@Override
	public boolean hasAttribute( String key, String value ) {
		String v = this.getAttributes().get( key );
		return v == null ? value == null : v.equals( value );
	}

	@Override
	public boolean hasntAttribute() {
		return this.getAttributes().isEmpty();
	}

	@Override
	public int sizeAttributes() {
		return this.getAttributes().size();
	}

	@Override
	public Iterable< String > keys() {
		return this.getAttributes().keySet();
	}

	@Override
	public Iterable< Map.Entry< String, String >> entries() {
		return this.getAttributes().entrySet();
	}

	@Override
	public void putAttribute( String key, String value ) throws UnsupportedOperationException {
		this.getAttributes().put( key, value );
	}

	@Override
	public void putAllAttributes( Map< String, String > map ) throws UnsupportedOperationException {
		this.getAttributes().putAll( map );
	}

	@Override
	public void clearAttributes() throws UnsupportedOperationException {
		this.getAttributes().clear();
	}

	@Override
	public boolean isntEmpty() {
		return ! this.isEmpty();
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	//	Printing
	//////////////////////////////////////////////////////////////////////////////////

	@Override
	public void print( final PrintWriter pw ) {
		new MinXMLWriter( pw, NullIndenter.FACTORY ).print( this );
	}
	
	@Override
	public void prettyPrint( final PrintWriter pw ) {
		new MinXMLWriter( pw, new StdIndenter.Factory() ).print( this );
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	//	Printing
	//////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		final StringPrintWriter pw = new StringPrintWriter();
		this.print( pw );
		return pw.toString();
	}
}