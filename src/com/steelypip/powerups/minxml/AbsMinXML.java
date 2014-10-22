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
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import com.steelypip.powerups.common.EmptyIterator;
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
	public abstract Map< String, String > asMap();
	
	/**
	 * This method will return a map representing the attributes of the
	 * element that is safe to use until the first update of the original element.
	 * Any update to the original element invalidates this object. 
	 * 
	 * @return map of attributes that is only safe until the next update
	 */
	public abstract Map< String, String > quickGetAttributes();
		
	public abstract List< MinXML > toList();
	

	//////////////////////////////////////////////////////////////////////////////////
	//	Methods built on the essential foundations.
	//////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Map< String, String > getAttributes() {
		return new TreeMap< String, String >( this.quickGetAttributes() );
	}
	
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
		return this.quickGetAttributes().get( key );
	}

	@Override
	public String getAttribute( String key, String default_value ) {
		final String value = this.getAttribute( key );
		return value != null ? value : default_value;
	}

	@Override
	public boolean hasAttribute() {
		return ! this.quickGetAttributes().isEmpty();
	}

	@Override
	public boolean hasAttribute( String key ) {
		return this.quickGetAttributes().containsKey( key );
	}

	@Override
	public boolean hasAttribute( String key, String value ) {
		String v = this.quickGetAttributes().get( key );
		return v == null ? value == null : v.equals( value );
	}

	@Override
	public boolean hasntAttribute() {
		return this.quickGetAttributes().isEmpty();
	}

	@Override
	public int sizeAttributes() {
		return this.quickGetAttributes().size();
	}

	@Override
	public Iterable< String > keys() {
		return new TreeSet< String >( this.quickGetAttributes().keySet() );
	}

	@Override
	public Iterable< String > asMapKeys() {
		return this.asMap().keySet();
	}

	@Override
	public Iterable< Map.Entry< String, String >> entries() {
		return this.getAttributes().entrySet();
	}

	@Override
	public Iterable< Map.Entry< String, String >> asMapEntries() {
		return this.asMap().entrySet();
	}

	@Override
	public void putAttribute( String key, String value ) throws UnsupportedOperationException {
		this.asMap().put( key, value );
	}

	@Override
	public void putAllAttributes( Map< String, String > map ) throws UnsupportedOperationException {
		this.asMap().putAll( map );
	}

	@Override
	public void clearAttributes() throws UnsupportedOperationException {
		this.asMap().clear();
	}

	@Override
	public boolean isntEmpty() {
		return ! this.isEmpty();
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	//	Printing
	//////////////////////////////////////////////////////////////////////////////////

	@Override
	public void print( final Writer w ) {
		new MinXMLWriter( new PrintWriter( w, true ), NullIndenter.FACTORY ).print( this );
	}
	
	@Override
	public void prettyPrint( final Writer w ) {
		new MinXMLWriter( new PrintWriter( w, true ), new StdIndenter.Factory() ).print( this );
	}
	
	@Override
	public void print( final PrintWriter pw ) {
		new MinXMLWriter( pw, NullIndenter.FACTORY ).print( this );
		pw.flush();
	}
	
	@Override
	public void prettyPrint( final PrintWriter pw ) {
		new MinXMLWriter( pw, new StdIndenter.Factory() ).print( this );
		pw.flush();
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

	//////////////////////////////////////////////////////////////////////////////////
	//	Tree Iterations
	//////////////////////////////////////////////////////////////////////////////////

	private static final Iterator< MinXML > empty = new EmptyIterator< MinXML >();
	
	@Override
	public boolean search( final MinXMLSearcher visitor ) {
		boolean found = visitor.startSearch( this );
		final Iterator< MinXML > kids = found ? empty : this.iterator();
		while ( ! found && kids.hasNext() ) {
			found = kids.next().search( visitor );
		}
		return visitor.endSearch( this, found );
	}
	
	@Override
	public void walk( final MinXMLWalker walker ) {
		walker.startWalk( this );
		for ( MinXML kid : this ) {
			kid.walk( walker );
		}
		walker.endWalk( this );
	}

	//////////////////////////////////////////////////////////////////////////////////
	//	Copying
	//////////////////////////////////////////////////////////////////////////////////

	@Override
	public MinXML shallowCopy() {
		return FlexiMinXML.shallowCopy( this );
	}

	@Override
	public MinXML deepCopy() {
		return FlexiMinXML.deepCopy( this );
	}
	
}
