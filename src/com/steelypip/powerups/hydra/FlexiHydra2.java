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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.common.CmpPair;
import com.steelypip.powerups.common.Pair;
import com.steelypip.powerups.common.StdPair;
import com.steelypip.powerups.util.EmptyMutatingMultiMap;
import com.steelypip.powerups.util.MutatingMultiMap;
import com.steelypip.powerups.util.StarMap;
import com.steelypip.powerups.util.TreeStarMap;

import sun.awt.SunHints.Value;

/**
 * This implementation provides a full implementation of all the
 * mandatory and optional methods of the MinXML interface. It aims
 * to strike a balance between fast access, update and reasonable
 * compactness in the most important cases. 
 * 
 * 
 * A TreeMap is used to track attributes/links and an ArrayList to track 
 * values/children; these provide good performance at the expense of space. 
 * However, when there are no attributes/children the TreeMap is not allocated 
 * and when there are no values/children the ArrayList is not allocated; these 
 * two cases are so common that the reduction in space is (typically) significant. 
 */
public abstract class FlexiHydra2< Key extends Comparable< Key >, AttrValue, Field extends Comparable< Field >, ChildValue > implements Hydra< Key, AttrValue, Field, ChildValue >, MutableHydraXML {
	
	protected @NonNull String name;
	protected MutatingMultiMap< Key, AttrValue > attributes = EmptyMutatingMultiMap.getInstance();
	protected MutatingMultiMap< Field, ChildValue > links = EmptyMutatingMultiMap.getInstance();
		
	//////////////////////////////////////////////////////////////////////////////////
	//	Constructors
	//////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs an element with a given name but no attributes or
	 * children.
	 * 
	 * @param name the name of the element
	 */
	@SuppressWarnings("null")
	public FlexiHydra2( final String name ) {
		this.name = name.intern(); 
	}
	
//	@SuppressWarnings("null")
//	public TreeMap< Key, ArrayList< AttrValue > > getNonNullAttributes() {
//		if ( this.attributes == null ) {
//			this.attributes = new TreeMap<>();
//		}
//		return this.attributes;
//	}
//
//	@SuppressWarnings("null")
//	public TreeMap< Field, ArrayList< ChildValue > > getNonNullLinks() {
//		if ( this.links == null ) {
//			this.links = new TreeMap<>();
//		}
//		return this.links;
//	}
	


	//////////////////////////////////////////////////////////////////////////////////
	//	Overrides that avoid allocating the TreeMap
	//////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void trimToSize() {
		this.attributes = this.attributes.trimToSize();
		this.links = this.links.trimToSize();
	}

	@Override
	public @NonNull String getInternedName() {
		return this.name;
	}


	@SuppressWarnings("null")
	@Override
	public void setName( @NonNull String x ) throws UnsupportedOperationException {
		this.name = x.intern();
	}

	@Override
	public AttrValue getValue( Key key ) throws IllegalArgumentException {
		return this.attributes.getOrFail( key );
	}

	@Override
	public AttrValue getValue( Key key, int index ) throws IllegalArgumentException {
		return this.attributes.getOrFail( key, index );
	}

	@Override
	public AttrValue getValue( Key key, AttrValue otherwise ) {
		return this.attributes.getElse( key, otherwise );
	}

	@Override
	public AttrValue getValue( Key key, int index, AttrValue otherwise ) {
		return this.attributes.getElse( key, index, otherwise );		
	}

	@Override
	public void setValue( Key key, AttrValue value ) throws UnsupportedOperationException {
		this.setValue( key, 0, value );
	}

	@Override
	public void setValue( Key key, int index, AttrValue value ) throws IllegalArgumentException, UnsupportedOperationException {
		final ArrayList< AttrValue > a = new ArrayList<>( this.attributes.getAll( key ) );
		try {
			a.set( index, value );
			this.attributes = this.attributes.setValues( key, a );
		} catch ( IndexOutOfBoundsException e ) {
			throw new IllegalArgumentException( e );
		}
	}

	@Override
	public void setAllValues( Key key, Iterable< AttrValue > values ) throws UnsupportedOperationException {
		this.attributes = this.attributes.setValues( key, values );
	}

	@Override
	public void addValue( Key key, AttrValue value ) throws UnsupportedOperationException {
		this.attributes = this.attributes.add( key, value );
	}

	@Override
	public void removeValue( Key key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		this.attributes = this.attributes.removeEntryAt( key, 0 );
	}

	@Override
	public void removeValue( Key key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		this.attributes = this.attributes.removeEntryAt( key, index );
	}

	@Override
	public void clearAllAttributes() throws UnsupportedOperationException {
		this.attributes = this.attributes.clearAllEntries();
	}

	@Override
	public void clearAttributes( Key key ) throws UnsupportedOperationException {
		this.attributes = this.attributes.removeEntries( key );
	}

	@Override
	public boolean hasNoAttributes() {
		return this.attributes.isEmpty();
	}

	@Override
	public boolean hasAttribute( Key key ) {
		return this.attributes.hasKey( key );
	}

	@Override
	public boolean hasValueAt( Key key, int index ) {
		return this.attributes.sizeEntries( key ) > index;
	}

	@Override
	public boolean hasAttribute( Key key, AttrValue value ) {
		return this.attributes.hasEntry( key, value );
	}

	@Override
	public boolean hasAttribute( Key key, int index, AttrValue value ) {
		return this.attributes.hasEntry( key, index, value );
	}

	@Override
	public boolean hasOneValue( Key key ) {
		return this.attributes.sizeEntries( key ) == 1;
	}

	@Override
	public int sizeAttributes() {
		return this.attributes.sizeEntries();
	}

	@Override
	public int sizeKeys() {
		return this.attributes.sizeKeys();	
	}

	@Override
	public boolean hasNoKeys() {
		return this.attributes.isEmpty();
	}

	@Override
	public int sizeValues( Key key ) {
		return this.attributes.sizeEntries( key );
	}

	@Override
	public boolean hasSizeValues( Key key, int n ) {
		return this.attributes.sizeEntries( key ) == n;
	}

	@Override
	public boolean hasNoValues( Key key ) {
		return this.attributes.sizeEntries( key ) == 0;
	}
	
	@Override
	public List< Attribute< Key, AttrValue > > attributesToList() {
		final List< Attribute< Key, AttrValue > > list = new ArrayList<>();
		
		int n = 0;
		for ( Pair< Key, AttrValue > p : this.attributes.entriesAsList() ) {
			list.add( new StdAttribute< Key, AttrValue >( p.getFirst(), n++, p.getSecond() ) );
		}
		
		return list;
	}

	@Override
	public List< AttrValue > valuesToList( Key key ) {
		return this.attributes.getAll( key );
	}

	@Override
	public Map< Key, AttrValue > firstValuesToMap() {
		final TreeMap< Key, AttrValue > sofar = new TreeMap<>();
		for ( Key k : this.attributes.keySet() ) {
			final List< AttrValue > list = this.attributes.getAll( k );
			if ( ! list.isEmpty() ) {
				sofar.put( k, list.get( 0 ) );
			}
		}
		return sofar;
	}

	@Override
	public StarMap< Key, AttrValue > attributesToStarMap() {
		return new TreeStarMap< Key, AttrValue >( this.attributes.entriesAsList() );
	}

	@Override
	public Map< Pair< Key, Integer >, AttrValue > attributesToPairMap() {
		final TreeMap< Pair< Key, Integer >, AttrValue > sofar = new TreeMap<>();
		for ( Key k : this.attributes.keySet() ) {
			int n = 0;
			for ( AttrValue v : this.attributes.getAll( k ) ) {
				sofar.put( new StdPair< Key, Integer >( k, n++ ), v );
			}
		}
		return sofar;
	}
	
	@Override
	public ChildValue getChild() throws IllegalArgumentException {
		return this.links.getOrFail( this.defaultField() );
	}
	
	@Override
	public ChildValue getChild( int index ) throws IllegalArgumentException {
		return this.links.getOrFail( this.defaultField(), index );
	}

	@Override
	public ChildValue getChild( Field field ) throws IllegalArgumentException {
		return this.links.getOrFail( field );
	}

	@Override
	public ChildValue getChild( Field field, int index ) throws IllegalArgumentException {
		return this.links.getOrFail( field, index );
	}

	@Override
	public ChildValue getChild( Field field, ChildValue otherwise ) {
		return this.links.getElse( field, otherwise );
	}

	@Override
	public ChildValue getChild( Field field, int index, ChildValue otherwise ) {
		return this.links.getElse( field, index, otherwise );
	}

	@Override
	public void setChild( Field field, ChildValue value ) throws UnsupportedOperationException {
		this.links.set
		ArrayList< ChildValue > flinks = this.getNonNullLinks().get( field );
		if ( flinks == null ) {
			flinks = new ArrayList<>();
		}
		final ChildValue fv = value;
		if ( flinks.isEmpty() ) {
			flinks.add( fv );
		} else {
			flinks.set( 0, fv );
		}
	}

	@Override
	public void setChild( Field field, int index, ChildValue value ) throws IllegalArgumentException, UnsupportedOperationException {
		ArrayList< ChildValue > flinks = this.getNonNullLinks().get( field );
		if ( flinks == null ) {
			flinks = new ArrayList<>();
		}
		final ChildValue fv = value;
		if ( index == flinks.size() ) {
			flinks.add( fv );
		} else if ( 0 <= index && index < flinks.size() ){
			flinks.set( 0, fv );
		} else {
			throw new IllegalArgumentException( String.format( "Bounds error while trying to set the Nth (%d) element of the field (%s)", index, field ) );
		}
	}
	

	@Override
	public void setAllChildren( Field field, Iterable< ChildValue > values ) throws UnsupportedOperationException {
		final ArrayList< ChildValue > array = new ArrayList<>();
		for ( ChildValue x : values ) {
			array.add( x );
		}
		if ( ! array.isEmpty() ) {
			this.getNonNullLinks().put( field, array );
		}
	}

	@Override
	public void addChild( Field field, ChildValue value ) throws UnsupportedOperationException {
		ArrayList< ChildValue > flinks = this.getNonNullLinks().get( field );
		if ( flinks == null ) {
			flinks = new ArrayList<>();
			this.links.put( field, flinks );
		}
		//final AbsFlexiFusion fv = toFlexiMinXMLStar( value );
		flinks.add( value );
	}

	@Override
	public void removeChild( Field field ) throws UnsupportedOperationException, IllegalArgumentException {
		if ( this.links != null ) {
			ArrayList< ChildValue > flinks = this.links.get( field );
			if ( flinks != null && ! flinks.isEmpty() ) {
				try {
					flinks.remove( 0 );
				} catch ( IndexOutOfBoundsException _e ) {
					throw new IllegalArgumentException( String.format( "No child at this position (%d) with this field (%s)", 0, field ), _e );
				}
			} else {
				throw new IllegalArgumentException( String.format( "No child at this position (%d) with this field (%s)", 0, field ) );				
			}
		} else {
			throw new IllegalArgumentException( String.format( "No child at this position (%d) with this field (%s)", 0, field ) );
		}
	}

	@Override
	public void removeChild( Field field, int index ) throws UnsupportedOperationException, IllegalArgumentException {
		if ( this.links != null ) {
			ArrayList< ChildValue > flinks = this.links.get( field );
			if ( flinks != null ) {
				try {
					flinks.remove( 0 );
				} catch ( IndexOutOfBoundsException _e ) {
					throw new IndexOutOfBoundsException( String.format( "No child at this position (%d) with this field (%s)", index, field ) );
				}
			} else {
				throw new IllegalArgumentException( String.format( "No child at this position (%d) with this field (%s)", index, field ) );				
			}
		} else {
			throw new IllegalArgumentException( String.format( "No child at this position (%d) with this field (%s)", index, field ) );
		}
	}


	@Override
	public void clearAllLinks() throws UnsupportedOperationException {
		this.links = null;
	}

	@Override
	public void clearLinks( Field key ) throws UnsupportedOperationException {
		if ( this.links == null ) return;
		this.links.remove( key );
	}

	@Override
	public boolean hasNoLinks() {
		if ( this.links == null ) return true;
		return this.links.isEmpty();
	}
	

	@Override
	public boolean hasLink( Field field ) {
		if ( this.links == null ) return false;
		final ArrayList< ChildValue > flinks = this.links.get( field );
		if ( flinks == null ) return false;
		return ! flinks.isEmpty();
	}

	@Override
	public boolean hasLink( Field field, int index ) {
		if ( this.links == null ) return false;
		final ArrayList< ChildValue > flinks = this.links.get( field );
		if ( flinks == null ) return false;
		return 0 <= index && index < flinks.size();
	}

	@Override
	public boolean hasLink( Field field, ChildValue value ) {
		if ( this.links == null ) return false;
		final ArrayList< ChildValue > flinks = this.links.get( field );
		if ( flinks == null ) return false;
		return flinks.contains( value );
	}

	@Override
	public boolean hasLink( Field field, int index, ChildValue value ) {
		if ( this.links == null ) return false;
		final ArrayList< ChildValue > flinks = this.links.get( field );
		if ( flinks == null ) return false;
		if ( ! ( 0 <= index && index < flinks.size() ) ) return false;
		return flinks.get( index ).equals( value );
	}

	@Override
	public boolean hasOneChild( Field field ) {
		if ( this.links == null ) return false;
		final ArrayList< ChildValue > flinks = this.links.get( field );
		if ( flinks == null ) return false;
		return flinks.size() == 1;
	}

	@Override
	public int sizeLinks() {
		int sofar = 0;
		if ( this.links != null ) {
			for ( Map.Entry< Field, ArrayList< ChildValue > > lentry : this.links.entrySet() ) {
				sofar += lentry.getValue().size();
			}
		}
		return sofar;
	}

	@Override
	public int sizeFields() {
		if ( this.links == null ) return 0;
		return this.links.size();
	}

	@Override
	public boolean hasSizeFields( int n ) {
		if ( this.links == null ) return n == 0;
		return this.links.size() == n;
	}

	@Override
	public boolean hasNoFields() {
		if ( this.links == null ) return true;
		for ( ArrayList< ChildValue > value : this.links.values() ) {
			if ( ! value.isEmpty() ) return false;
		}
		return true;
	}

	@Override
	public int sizeChildren( Field field ) {
		if ( this.links == null ) return 0;
		final ArrayList< ChildValue > flinks = this.links.get( field );
		if ( flinks == null ) return 0;
		return flinks.size();
	}

	@Override
	public boolean hasSizeChildren( Field field, int n ) {
		if ( this.links == null ) return n == 0;
		final ArrayList< ChildValue > flinks = this.links.get( field );
		if ( flinks == null ) return n == 0;
		return flinks.size() == n;
	}

	@Override
	public boolean hasNoChildren( Field field ) {
		if ( this.links == null ) return true;
		final ArrayList< ChildValue > flinks = this.links.get( field );
		if ( flinks == null ) return true;
		return flinks.isEmpty();
	}

	@Override
	public List< ChildValue > childrenToList( String field ) {
		final ArrayList< ChildValue > sofar = new ArrayList<>();
		if ( this.links != null ) {
			final ArrayList< ChildValue > flinks = this.links.get( field );
			if ( flinks != null ) {
				sofar.addAll( flinks );
			}
		}		
		return sofar;
	}

	@Override
	public Map< Field, ChildValue > firstChildrenToMap() {
		final Map< Field, ChildValue > sofar = new TreeMap<>();
		if ( this.links != null ) {
			if ( this.links != null ) {
				for ( Map.Entry< Field, ArrayList< ChildValue > > lentry : this.links.entrySet() ) {
					final ArrayList< ChildValue > children = lentry.getValue();
					if ( children != null && ! children.isEmpty() ) {
						final Field field = lentry.getKey();
						sofar.put( field, children.get( 0 ) );
					}
				}
			}
		}		
		return sofar;
	}

	@SuppressWarnings("null")
	@Override
	public StarMap< Field, ChildValue > linksToStarMap() {
		if ( this.links != null ) {
			return new TreeStarMap<>( this.links );
		} else {
			return new TreeStarMap<>();
		}
	}

	@Override
	public Map< Pair< Field, Integer >, ChildValue > linksToPairMap() {
		final Map< Pair< Field, Integer >, ChildValue > sofar = new TreeMap<>();
		if ( this.links != null ) {
			for ( Map.Entry< Field, ArrayList< ChildValue > > lentry : this.links.entrySet() ) {
				final Field field = lentry.getKey();
				final ArrayList< ChildValue > children = lentry.getValue();
				int n = 0;
				for ( ChildValue child : children ) {
					final Pair< Field, Integer >p = new CmpPair< Field, Integer >( field, n++ );
					sofar.put( p, child );
				}
			}			
		}
		return sofar;
	}
	

	@Override
	public Iterator< Link< Field, ChildValue > > iterator() {
		final LinkedList< Link< Field, ChildValue > > sofar = new LinkedList<>();
		if ( this.links != null ) {
			for ( Map.Entry< Field, ArrayList< ChildValue > > lentry : this.links.entrySet() ) {
				final Field field = lentry.getKey();
				final ArrayList< ChildValue > children = lentry.getValue();
				int n = 0;
				for ( ChildValue child : children ) {
					sofar.add( new StdLink< Field, ChildValue >( field, n++, child ) );
				}
			}			
		}
		return sofar.iterator();		
	}

	@Override
	public List< Link< Field, ChildValue > > linksToList() {
		final LinkedList< Link< Field, ChildValue > > sofar = new LinkedList<>();
		if ( this.links != null ) {
			for ( Map.Entry< Field, ArrayList< ChildValue > > lentry : this.links.entrySet() ) {
				final Field field = lentry.getKey();
				final ArrayList< ChildValue > children = lentry.getValue();
				int n = 0;
				for ( ChildValue child : children ) {
					sofar.add( new StdLink< Field, ChildValue >( field, n++, child ) );
				}
			}			
		}
		return sofar;		
	}


	@Override
	public Set< Key > keysToSet() {
		if ( this.attributes != null ) {
			return new TreeSet<>( this.attributes.keySet() );
		} else {
			return new TreeSet<>();
		}
	}

	@Override
	public Set< Field > fieldsToSet() {
		if ( this.links != null ) {
			return new TreeSet<>( this.links.keySet() );
		} else {
			return new TreeSet<>();			
		}
	}

	@Override
	public void addChild( ChildValue value ) throws UnsupportedOperationException {
		this.addChild( this.defaultField(), value );
	}

//	/**
//	 * Creates a copy of the top-level node of the given element.
//	 * @param element the element to copy
//	 * @return the copy
//	 */
//	public static FlexiHydraXML shallowCopy( Fusion element ) {
//		return copy( true, element );	
//	}
//		
//	public static FlexiHydraXML deepCopy( Fusion element ) {
//		return copy( false, element );
//	}
//	
//	private static FlexiHydraXML copy( final boolean shallow, Fusion element ) {
//		final FlexiHydraXML result = new FlexiHydraXML( element.getName() );
//		assignAttributes( element, result );
//		assignLinks( shallow, element, result );
//		return result;		
//	}
//
//	private static void assignLinks( boolean shallow, Fusion element, final FlexiHydraXML result ) {
//		for ( Fusion.Link key_value : element.linksToList() ) {
//			String field = key_value.getField();
//			Fusion child = key_value.getChild();
//			result.addChild( field, shallow ? child : FlexiHydraXML.deepCopy( child ) );
//		}
//	}
//
//	private static void assignAttributes( Fusion element, final FlexiHydraXML result ) {
//		for ( Fusion.Attribute key_value : element.attributesToList() ) {
//			String key = key_value.getKey();
//			String value = key_value.getValue();
//			result.addValue( key, value );
//		}
//	}

//	@Override
//	public Fusion shallowCopy() {
//		return copy( true, this );
//	}
//
//	@Override
//	public Fusion deepCopy() {
//		return copy( false, this );
//	}
}
