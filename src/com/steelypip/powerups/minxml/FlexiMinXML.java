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

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.steelypip.powerups.common.EmptyIterable;
import com.steelypip.powerups.common.EmptyIterator;
import com.steelypip.powerups.common.EmptyList;

public class FlexiMinXML extends AbsFlexiMinXML {
	
	public FlexiMinXML( final String name ) {
		//	We intern the name purely to help save space. Unfortunately we
		//	can't really take advantage of it in any other way.
		this.name = name.intern();
		//  Note that the attributes and children can be left as null. 
	}

	//////////////////////////////////////////////////////////////////////////////////
	//	Overrides that avoid allocating the TreeMap
	//////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void trimToSize() {
		if ( this.children != null ) this.children.trimToSize();
	}
	
	@Override
	public String getAttribute( String key ) {
		return this.attributes == null ? null : super.getAttribute( key );
	}

	@Override
	public boolean hasAttribute() {
		return this.attributes != null && super.hasAttribute();
	}

	@Override
	public boolean hasAttribute( String key ) {
		return this.attributes != null && super.hasAttribute( key );
	}

	@Override
	public boolean hasAttribute( String key, String value ) {
		return this.attributes == null ? value == null : super.hasAttribute( key, value );
	}

	@Override
	public boolean hasntAttribute() {
		return this.attributes == null || super.hasntAttribute();
	}

	@Override
	public Iterable< String > keys() {
		if ( this.attributes == null ) {
			return new EmptyList< String >();
		} else {
			return super.keys();
		}
	}

	@Override
	public int sizeAttributes() {
		return this.attributes == null ? 0 : super.sizeAttributes();
	}
	
	static Iterable< Map.Entry< String, String > > empty_entries_iterable = new EmptyIterable< Map.Entry< String, String > >();
	
	@Override
	public Iterable< Entry< String, String >> entries() {
		if ( this.attributes == null ) {
			return empty_entries_iterable;
		} else {
			return super.entries();
		}
	}	

	
	//////////////////////////////////////////////////////////////////////////////////
	//	Overrides that avoid allocating the ArrayList
	//////////////////////////////////////////////////////////////////////////////////

	@Override
	public void clear() {
		if ( this.attributes != null ) {
			super.clear();
		}
	}

	@Override
	public boolean contains( Object o ) {
		return this.children != null && super.contains( o );
	}

	@Override
	public boolean containsAll( Collection< ? > c ) {
		return this.children != null && super.containsAll( c );
	}

	@Override
	public int indexOf( Object o ) {
		return this.children == null ? -1 : super.indexOf( o );
	}

	@Override
	public boolean isEmpty() {
		return this.children == null || this.children.isEmpty();
	}
	
	static Iterator< MinXML > empty_iterator = new EmptyIterator< MinXML >();

	@Override
	public Iterator< MinXML > iterator() {
		if ( this.children == null ) {
			return empty_iterator;
		} else {
			return super.iterator();
		}
	}

	@Override
	public int lastIndexOf( Object o ) {
		return this.children == null ? -1 : super.lastIndexOf( o );
	}

	@Override
	public int size() {
		return this.children == null ? 0 : super.size();
	}


	@Override
	public boolean isntEmpty() {
		return this.children == null && ! this.children.isEmpty();
	}
	
}