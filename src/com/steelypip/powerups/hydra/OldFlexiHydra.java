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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.common.CmpPair;
import com.steelypip.powerups.common.Pair;
import com.steelypip.powerups.util.StarMap;
import com.steelypip.powerups.util.TreeStarMap;

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
public abstract class OldFlexiHydra< Key extends Comparable< Key >, AttrValue, Field extends Comparable< Field >, ChildValue > implements Hydra< Key, AttrValue, Field, ChildValue >, MutableHydraXML {
	
	protected @NonNull String name;
	protected TreeMap< @NonNull Key, ArrayList< @NonNull AttrValue > > attributes = null;
	protected TreeMap< @NonNull Field, ArrayList< @NonNull ChildValue > > links = null;
		
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
	public OldFlexiHydra( final @NonNull String name ) {
		this.name = name.intern(); 
	}
	
//	public AbsFlexiFusion( final @NonNull Fusion element ) {
//		this.name = element.getInternedName();
//		assignAttributes( element, this );
//		assignLinks( element, this );
//	}
	
//	public @NonNull AbsFlexiFusion toFlexiMinXMLStar( @NonNull Fusion that ) {
//		if ( that instanceof AbsFlexiFusion ) {
//			return (AbsFlexiFusion)that;
//		} else {
//			return new AbsFlexiFusion( that );
//		}
//	}
	
	@SuppressWarnings("null")
	public @NonNull TreeMap< @NonNull Key, ArrayList< @NonNull AttrValue > > getNonNullAttributes() {
		if ( this.attributes == null ) {
			this.attributes = new TreeMap<>();
		}
		return this.attributes;
	}

	@SuppressWarnings("null")
	public @NonNull TreeMap< @NonNull Field, ArrayList< @NonNull ChildValue > > getNonNullLinks() {
		if ( this.links == null ) {
			this.links = new TreeMap<>();
		}
		return this.links;
	}
	


	//////////////////////////////////////////////////////////////////////////////////
	//	Overrides that avoid allocating the TreeMap
	//////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void trimToSize() {
		if ( this.attributes != null ) {
			for ( ArrayList< AttrValue > values : this.attributes.values() ) {
				values.trimToSize();
			}
		}
		if ( this.links != null ) {
			for ( ArrayList< ChildValue > children : this.links.values() ) {
				children.trimToSize();
			}
		}
	}

	@Override
	public @NonNull String getInternedName() {
		return this.name;
	}


	@Override
	public void setName( @NonNull String x ) throws UnsupportedOperationException {
		this.name = x;
	}

	@Override
	public AttrValue getValue( @NonNull Key key ) throws IllegalArgumentException {
		if ( this.attributes != null ) {
			final ArrayList< AttrValue > values = this.attributes.get( key );
			if ( values != null ) {
				return values.get( 0 );
			}
		}
		throw new IllegalArgumentException( String.format(  "No attributes with this key (%s)", key ) );			
		
	}

	@Override
	public AttrValue getValue( @NonNull Key key, int index ) throws IllegalArgumentException {
		if ( this.attributes != null ) {
			final ArrayList< AttrValue > values = this.attributes.get( key );
			if ( values != null ) {
				try {
					return values.get( index );
				} catch ( IndexOutOfBoundsException _e ) {
				}
			}
		}
		throw new IllegalArgumentException( String.format( "No attribute with this key (%s)", key ) );			
	}

	@Override
	public @Nullable AttrValue getValue( @NonNull Key key, @Nullable AttrValue otherwise ) {
		if ( this.attributes != null ) {
			final ArrayList< AttrValue > values = this.attributes.get( key );
			if ( values != null ) {
				return values.get( 0 );
			}
		}
		return otherwise;
	}

	@Override
	public @Nullable AttrValue getValue( @NonNull Key key, int index, @Nullable AttrValue otherwise ) {
		if ( this.attributes != null ) {
			final ArrayList< AttrValue > values = this.attributes.get( key );
			if ( values != null ) {
				try {
					return values.get( index );
				} catch ( IndexOutOfBoundsException _e ) {
				}
			}
		}
		return otherwise;				
		
	}

	@Override
	public void setValue( @NonNull Key key, @NonNull AttrValue value ) throws UnsupportedOperationException {
		ArrayList< @NonNull AttrValue > values = this.getNonNullAttributes().get( key );
		if ( values == null ) {
			values = new ArrayList<>();
			values.add( value );
			this.attributes.put( key, values );
		} else {
			values.set( 0, value );
		}
	}

	@Override
	public void setValue( @NonNull Key key, int index, @NonNull AttrValue value ) throws IllegalArgumentException, UnsupportedOperationException {
		ArrayList< @NonNull AttrValue > values = this.getNonNullAttributes().get( key );
		if ( values == null ) {
			if ( index == 0 ) {
				values = new ArrayList<>();
				this.attributes.put( key,  values );
			} else {
				throw new IllegalArgumentException( String.format( "No attribute with this key (%s) to support assignment at this position (%d)", key, index ) ); 
			}
		}
		final int L = values.size();
		if ( index < L ) {
			try {
				values.set( index, value );
			} catch ( IndexOutOfBoundsException _e ) {
				throw new IllegalArgumentException( String.format( "Cannot assign to this position (%d) for this key (%s)", index, key ) ); 
			}
		} else if ( index == L ) {
			values.add( value );
		} else {
			throw new IllegalArgumentException( String.format( "Cannot assign to this position (%d) for this key (%s)", index, key ) );
		}
	}

	@Override
	public void setAllValues( @NonNull Key key, Iterable< @NonNull AttrValue > values ) throws UnsupportedOperationException {
		final ArrayList< @NonNull AttrValue > new_values = new ArrayList<>();
		for ( @NonNull AttrValue x : values ) {
			new_values.add( x );
		}
		if ( new_values.isEmpty() ) {
			if ( this.attributes != null ) {
				this.attributes.remove( key );
			}
		} else {
			this.getNonNullAttributes().put( key, new_values );
		}
	}

	@Override
	public void addValue( @NonNull Key key, @NonNull AttrValue value ) throws UnsupportedOperationException {
		ArrayList< @NonNull AttrValue > values = this.getNonNullAttributes().get( key );
		if ( values == null ) {
			values = new ArrayList<>();
			this.attributes.put( key, values );
		}
		values.add( value );
	}

	@Override
	public void removeValue( @NonNull Key key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		if ( this.attributes != null ) {
			final ArrayList< @NonNull AttrValue > values = this.attributes.get( key );
			if ( values == null ) {
				throw new IndexOutOfBoundsException( String.format( "No values for this key (%s)", key ) );
			}
			try {
				values.remove( 0 );
				if ( values.isEmpty() ) {
					this.attributes.remove( key );
				}
			} catch ( IndexOutOfBoundsException e ) {
				//	Should never happen because 0-length values should be eliminated.
				throw new IndexOutOfBoundsException( String.format( "No values for this key (%s)", key ) );
			}
		} else {
			throw new IndexOutOfBoundsException( String.format( "No values for this key (%s)", key ) );			
		}
	}

	@Override
	public void removeValue( @NonNull Key key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		if ( this.attributes != null ) {
			final ArrayList< @NonNull AttrValue > values = this.attributes.get( key );
			if ( values == null ) {
				throw new IndexOutOfBoundsException( String.format( "No values for this key (%s)", key ) );
			}
			try {
				values.remove( index );
				if ( values.isEmpty() ) {
					this.attributes.remove( key );
				}
			} catch ( IndexOutOfBoundsException e ) {
				throw new IndexOutOfBoundsException( String.format( "Index (%d) out of range for this key (%s)", index, key ) );
			}
		} else {
			throw new IndexOutOfBoundsException( String.format( "Index (%d) out of range for this key (%s)", index, key ) );
		}
	}
	

	@Override
	public void clearAllAttributes() throws UnsupportedOperationException {
		if ( this.attributes != null ) {
			this.attributes = null;
		}
	}

	@Override
	public void clearAttributes( @NonNull Key key ) throws UnsupportedOperationException {
		if ( this.attributes != null ) {
			final ArrayList< @NonNull AttrValue > values = this.attributes.get( key );
			if ( values != null ) {
				this.attributes.remove( key );
			}
		}
	}

	@Override
	public boolean hasNoAttributes() {
		if ( this.attributes != null ) {
			return this.attributes.isEmpty();
		} else {
			return true;
		}
	}

	@Override
	public boolean hasAttribute( @NonNull Key key ) {
		if ( this.attributes != null ) {
			return this.attributes.containsKey( key );
		} else {
			return false;
		}
	}

	@Override
	public boolean hasValueAt( @NonNull Key key, int index ) {
		if ( this.attributes != null ) {
			final ArrayList< @NonNull AttrValue > values = this.attributes.get( key );
			if ( values != null ) {
				return index < values.size() && 0 <= index; 
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean hasAttribute( @NonNull Key key, @Nullable String value ) {
		if ( this.attributes != null && value != null ) {
			final ArrayList< @NonNull AttrValue > values = this.attributes.get( key );
			if ( values != null ) {
				return values.contains( value );
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean hasAttribute( @NonNull Key key, int index, @Nullable String value ) {
		if ( this.attributes != null && value != null ) {
			final ArrayList< @NonNull AttrValue > values = this.attributes.get( key );
			if ( values != null && 0 <= index && index < values.size() ) {
				return value.equals( values.get( index ) );
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean hasOneValue( @NonNull Key key ) {
		if ( this.attributes != null ) {
			final ArrayList< @NonNull AttrValue > values = this.attributes.get( key );
			return values != null && values.size() == 1;
		} else {
			return false;
		}
	}
	
	

	@Override
	public int sizeAttributes() {
		if ( this.attributes != null ) {
			int sum = 0;
			for ( ArrayList< @NonNull AttrValue > values : this.attributes.values() ) {
				sum += values.size();
			}
			return sum;
		} else {
			return 0;
		}
	}

	@Override
	public int sizeKeys() {
		if ( this.attributes != null ) {
			return this.attributes.size();
		} else {
			return 0;
		}	
	}

	@Override
	public boolean hasNoKeys() {
		if ( this.attributes != null ) {
			return this.attributes.isEmpty();
		} else {
			return true;
		}	
	}

	@Override
	public int sizeValues( @NonNull Key key ) {
		if ( this.attributes != null ) {
			final ArrayList< @NonNull AttrValue > values = this.attributes.get( key );
			if ( values == null ) {
				return 0;
			} else {
				return values.size();
			}
		} else {
			return 0;
		}
	}

	@Override
	public boolean hasSizeValues( @NonNull Key key, int n ) {
		if ( this.attributes != null ) {
			final ArrayList< @NonNull AttrValue > values = this.attributes.get( key );
			if ( values == null ) {
				return n == 0;
			} else {
				return n == values.size();
			}
		} else {
			return n == 0;
		}
	}

	@Override
	public boolean hasNoValues( @NonNull Key key ) {
		if ( this.attributes != null ) {
			final ArrayList< @NonNull AttrValue > values = this.attributes.get( key );
			if ( values == null ) {
				return true;
			} else {
				return values.isEmpty();
			}
		} else {
			return true;
		}
	}


	
	@Override
	public @NonNull List< Attribute< Key, AttrValue > > attributesToList() {
		final List< Attribute< Key, AttrValue > > list = new ArrayList<>();
		if ( this.attributes != null ) {
			for ( Map.Entry< @NonNull Key, ArrayList< @NonNull AttrValue > > e : this.attributes.entrySet() ) {
				int n = 0;
				for ( @NonNull AttrValue v : e.getValue() ) {
					list.add( new StdAttribute< Key, AttrValue >( e.getKey(), n++, v ) );
				}
			}
		}
		return list;
	}

	@Override
	public @NonNull List< @NonNull AttrValue > valuesToList( @NonNull Key key ) {
		final @NonNull List< @NonNull AttrValue > list = new ArrayList< @NonNull AttrValue >();
		if ( this.attributes != null ) {
			final List< @NonNull AttrValue > values = this.attributes.get( key );
			if ( values != null ) {
				list.addAll( values );
			}
		}
		return list;	
	}

	@SuppressWarnings("null")
	@Override
	public @NonNull Map< @NonNull Key, AttrValue > firstValuesToMap() {
		final TreeMap< @NonNull Key, @NonNull AttrValue > sofar = new TreeMap<>();
		if ( this.attributes != null ) {
			for ( Map.Entry< Key, ArrayList< @NonNull AttrValue > > e : this.attributes.entrySet() ) {
				if ( ! e.getValue().isEmpty() ) {
					sofar.put( e.getKey(), e.getValue().get( 0 ) );
				}
			}
		}
		return sofar;
	}

	@Override
	public @NonNull StarMap< @NonNull Key, @Nullable AttrValue > attributesToStarMap() {
		return new TreeStarMap< @NonNull Key, @Nullable AttrValue >( this.attributesToList() );
	}

	@SuppressWarnings("null")
	@Override
	public @NonNull Map< Pair< @NonNull Key, @NonNull Integer >, AttrValue > attributesToPairMap() {
		final TreeMap< Pair< @NonNull Key, @NonNull Integer >, @NonNull AttrValue > sofar = new TreeMap<>();
		if ( this.attributes != null ) {
			for ( Map.Entry< Key, ArrayList< @NonNull AttrValue > > e : this.attributes.entrySet() ) {
				int n = 0;
				for ( @NonNull AttrValue x : e.getValue() ) {
					sofar.put( new CmpPair< @NonNull Key, @NonNull Integer >( e.getKey(), n++ ), x );
				}
			}
		}
		return sofar;
	}
	
	@Override
	@NonNull
	public ChildValue getChild() throws IllegalArgumentException {
		if ( links != null ) {
			final ArrayList< @NonNull ChildValue > flinks = this.links.get( "" );
			if ( flinks != null ) {
				return flinks.get( 0 );
			} else {
				throw new IllegalArgumentException( "No children (of the default field '')" );
			}
		} else {
			throw new IllegalArgumentException( "No children" );			
		}
	}
	
	@Override
	public @NonNull ChildValue getChild( int index ) throws IllegalArgumentException {
		if ( links != null ) {
			final ArrayList< @NonNull ChildValue > flinks = this.links.get( "" );
			if ( flinks != null && index < flinks.size() && 0 <= index ) {
				return flinks.get( index );
			} else {
				throw new IllegalArgumentException( String.format(  "No field at this position (%s) with the default field ('')", index ) );
			}
		} else {
			throw new IllegalArgumentException( "No children" );			
		}
	}


	@Override
	public @NonNull ChildValue getChild( @NonNull Field field ) throws IllegalArgumentException {
		if ( links != null ) {
			final ArrayList< @NonNull ChildValue > flinks = this.links.get( field );
			if ( flinks != null ) {
				return flinks.get( 0 );
			} else {
				throw new IllegalArgumentException( String.format( "No field with this field (%s)", field ) );
			}
		} else {
			throw new IllegalArgumentException( "No children" );			
		}
	}

	@Override
	public @NonNull ChildValue getChild( @NonNull Field field, int index ) throws IllegalArgumentException {
		if ( links != null ) {
			final ArrayList< @NonNull ChildValue > flinks = this.links.get( field );
			if ( flinks != null && index < flinks.size() && 0 <= index ) {
				return flinks.get( index );
			} else {
				throw new IllegalArgumentException( String.format( "No field at this position (%s) with this field (%s)", index, field ) );
			}
		} else {
			throw new IllegalArgumentException( "No children" );			
		}
	}

	@Override
	public @Nullable ChildValue getChild( @NonNull Field field, @Nullable ChildValue otherwise ) {
		if ( this.links == null ) return otherwise;
		final ArrayList< @NonNull ChildValue > flinks = this.links.get( field );
		if ( flinks != null ) {
			return flinks.get( 0 );
		} else {
			return otherwise;
		}
	}

	@Override
	public @Nullable ChildValue getChild( @NonNull Field field, int index, @Nullable ChildValue otherwise ) {
		if ( this.links == null ) return otherwise;
		final ArrayList< @NonNull ChildValue > flinks = this.links.get( field );
		if ( flinks != null && index < flinks.size() && 0 <= index ) {
			return flinks.get( index );
		} else {
			return otherwise;
		}
	}

	@Override
	public void setChild( @NonNull Field field, @NonNull ChildValue value ) throws UnsupportedOperationException {
		ArrayList< @NonNull ChildValue > flinks = this.getNonNullLinks().get( field );
		if ( flinks == null ) {
			flinks = new ArrayList<>();
		}
		final @NonNull ChildValue fv = value;
		if ( flinks.isEmpty() ) {
			flinks.add( fv );
		} else {
			flinks.set( 0, fv );
		}
	}

	@Override
	public void setChild( @NonNull Field field, int index, @NonNull ChildValue value ) throws IllegalArgumentException, UnsupportedOperationException {
		ArrayList< @NonNull ChildValue > flinks = this.getNonNullLinks().get( field );
		if ( flinks == null ) {
			flinks = new ArrayList<>();
		}
		final @NonNull ChildValue fv = value;
		if ( index == flinks.size() ) {
			flinks.add( fv );
		} else if ( 0 <= index && index < flinks.size() ){
			flinks.set( 0, fv );
		} else {
			throw new IllegalArgumentException( String.format( "Bounds error while trying to set the Nth (%d) element of the field (%s)", index, field ) );
		}
	}
	

	@Override
	public void setAllChildren( @NonNull Field field, Iterable< @NonNull ChildValue > values ) throws UnsupportedOperationException {
		final ArrayList< @NonNull ChildValue > array = new ArrayList<>();
		for ( @NonNull ChildValue x : values ) {
			array.add( x );
		}
		if ( ! array.isEmpty() ) {
			this.getNonNullLinks().put( field, array );
		}
	}

	@Override
	public void addChild( @NonNull Field field, @NonNull ChildValue value ) throws UnsupportedOperationException {
		ArrayList< @NonNull ChildValue > flinks = this.getNonNullLinks().get( field );
		if ( flinks == null ) {
			flinks = new ArrayList<>();
			this.links.put( field, flinks );
		}
		//final @NonNull AbsFlexiFusion fv = toFlexiMinXMLStar( value );
		flinks.add( value );
	}

	@Override
	public void removeChild( @NonNull Field field ) throws UnsupportedOperationException, IllegalArgumentException {
		if ( this.links != null ) {
			ArrayList< @NonNull ChildValue > flinks = this.links.get( field );
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
	public void removeChild( @NonNull Field field, int index ) throws UnsupportedOperationException, IllegalArgumentException {
		if ( this.links != null ) {
			ArrayList< @NonNull ChildValue > flinks = this.links.get( field );
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
	public void clearLinks( @NonNull Field key ) throws UnsupportedOperationException {
		if ( this.links == null ) return;
		this.links.remove( key );
	}

	@Override
	public boolean hasNoLinks() {
		if ( this.links == null ) return true;
		return this.links.isEmpty();
	}
	

	@Override
	public boolean hasLink( @NonNull Field field ) {
		if ( this.links == null ) return false;
		final ArrayList< ChildValue > flinks = this.links.get( field );
		if ( flinks == null ) return false;
		return ! flinks.isEmpty();
	}

	@Override
	public boolean hasLink( @NonNull Field field, int index ) {
		if ( this.links == null ) return false;
		final ArrayList< ChildValue > flinks = this.links.get( field );
		if ( flinks == null ) return false;
		return 0 <= index && index < flinks.size();
	}

	@Override
	public boolean hasLink( @NonNull Field field, @Nullable ChildValue value ) {
		if ( this.links == null ) return false;
		final ArrayList< ChildValue > flinks = this.links.get( field );
		if ( flinks == null ) return false;
		return flinks.contains( value );
	}

	@Override
	public boolean hasLink( @NonNull Field field, int index, @Nullable ChildValue value ) {
		if ( this.links == null ) return false;
		final ArrayList< ChildValue > flinks = this.links.get( field );
		if ( flinks == null ) return false;
		if ( ! ( 0 <= index && index < flinks.size() ) ) return false;
		return flinks.get( index ).equals( value );
	}

	@Override
	public boolean hasOneChild( @NonNull Field field ) {
		if ( this.links == null ) return false;
		final ArrayList< ChildValue > flinks = this.links.get( field );
		if ( flinks == null ) return false;
		return flinks.size() == 1;
	}

	@Override
	public int sizeLinks() {
		int sofar = 0;
		if ( this.links != null ) {
			for ( Map.Entry< @NonNull Field, ArrayList< @NonNull ChildValue > > lentry : this.links.entrySet() ) {
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
		for ( ArrayList< @NonNull ChildValue > value : this.links.values() ) {
			if ( ! value.isEmpty() ) return false;
		}
		return true;
	}

	@Override
	public int sizeChildren( @NonNull Field field ) {
		if ( this.links == null ) return 0;
		final ArrayList< ChildValue > flinks = this.links.get( field );
		if ( flinks == null ) return 0;
		return flinks.size();
	}

	@Override
	public boolean hasSizeChildren( @NonNull Field field, int n ) {
		if ( this.links == null ) return n == 0;
		final ArrayList< ChildValue > flinks = this.links.get( field );
		if ( flinks == null ) return n == 0;
		return flinks.size() == n;
	}

	@Override
	public boolean hasNoChildren( @NonNull Field field ) {
		if ( this.links == null ) return true;
		final ArrayList< ChildValue > flinks = this.links.get( field );
		if ( flinks == null ) return true;
		return flinks.isEmpty();
	}

	@Override
	public @NonNull List< @NonNull ChildValue > childrenToList( @NonNull String field ) {
		final ArrayList< @NonNull ChildValue > sofar = new ArrayList<>();
		if ( this.links != null ) {
			final ArrayList< @NonNull ChildValue > flinks = this.links.get( field );
			if ( flinks != null ) {
				sofar.addAll( flinks );
			}
		}		
		return sofar;
	}

	@Override
	public Map< @NonNull Field, ChildValue > firstChildrenToMap() {
		final Map< @NonNull Field, @NonNull ChildValue > sofar = new TreeMap<>();
		if ( this.links != null ) {
			if ( this.links != null ) {
				for ( Map.Entry< @NonNull Field, ArrayList< @NonNull ChildValue > > lentry : this.links.entrySet() ) {
					final ArrayList< @NonNull ChildValue > children = lentry.getValue();
					if ( children != null && ! children.isEmpty() ) {
						final @NonNull Field field = lentry.getKey();
						sofar.put( field, children.get( 0 ) );
					}
				}
			}
		}		
		return sofar;
	}

	@SuppressWarnings("null")
	@Override
	public StarMap< @NonNull Field, @NonNull ChildValue > linksToStarMap() {
		if ( this.links != null ) {
			return new TreeStarMap<>( this.links );
		} else {
			return new TreeStarMap<>();
		}
	}

	@Override
	public Map< Pair< @NonNull Field, @NonNull Integer >, @NonNull ChildValue > linksToPairMap() {
		final Map< Pair< @NonNull Field, @NonNull Integer >, @NonNull ChildValue > sofar = new TreeMap<>();
		if ( this.links != null ) {
			for ( Map.Entry< @NonNull Field, ArrayList< @NonNull ChildValue > > lentry : this.links.entrySet() ) {
				final @NonNull Field field = lentry.getKey();
				final ArrayList< @NonNull ChildValue > children = lentry.getValue();
				int n = 0;
				for ( @NonNull ChildValue child : children ) {
					final Pair< @NonNull Field, @NonNull Integer >p = new CmpPair< @NonNull Field, @NonNull Integer >( field, n++ );
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
			for ( Map.Entry< @NonNull Field, ArrayList< @NonNull ChildValue > > lentry : this.links.entrySet() ) {
				final @NonNull Field field = lentry.getKey();
				final ArrayList< @NonNull ChildValue > children = lentry.getValue();
				int n = 0;
				for ( @NonNull ChildValue child : children ) {
					sofar.add( new StdLink< Field, ChildValue >( field, n++, child ) );
				}
			}			
		}
		return sofar.iterator();		
	}

	@Override
	public @NonNull List< Link< Field, ChildValue > > linksToList() {
		final LinkedList< Link< Field, ChildValue > > sofar = new LinkedList<>();
		if ( this.links != null ) {
			for ( Map.Entry< @NonNull Field, ArrayList< @NonNull ChildValue > > lentry : this.links.entrySet() ) {
				final @NonNull Field field = lentry.getKey();
				final ArrayList< @NonNull ChildValue > children = lentry.getValue();
				int n = 0;
				for ( @NonNull ChildValue child : children ) {
					sofar.add( new StdLink< Field, ChildValue >( field, n++, child ) );
				}
			}			
		}
		return sofar;		
	}


	@Override
	public @NonNull Set< @NonNull Key > keysToSet() {
		if ( this.attributes != null ) {
			return new TreeSet<>( this.attributes.keySet() );
		} else {
			return new TreeSet<>();
		}
	}

	@Override
	public @NonNull Set< @NonNull Field > fieldsToSet() {
		if ( this.links != null ) {
			return new TreeSet<>( this.links.keySet() );
		} else {
			return new TreeSet<>();			
		}
	}

	@Override
	public void addChild( @NonNull ChildValue value ) throws UnsupportedOperationException {
		this.addChild( this.defaultField(), value );
	}

//	/**
//	 * Creates a copy of the top-level node of the given element.
//	 * @param element the element to copy
//	 * @return the copy
//	 */
//	public static @NonNull FlexiHydraXML shallowCopy( @NonNull Fusion element ) {
//		return copy( true, element );	
//	}
//		
//	public static @NonNull FlexiHydraXML deepCopy( @NonNull Fusion element ) {
//		return copy( false, element );
//	}
//	
//	private static @NonNull FlexiHydraXML copy( final boolean shallow, @NonNull Fusion element ) {
//		final FlexiHydraXML result = new FlexiHydraXML( element.getName() );
//		assignAttributes( element, result );
//		assignLinks( shallow, element, result );
//		return result;		
//	}
//
//	private static void assignLinks( boolean shallow, Fusion element, final FlexiHydraXML result ) {
//		for ( Fusion.Link key_value : element.linksToList() ) {
//			@NonNull String field = key_value.getField();
//			@NonNull Fusion child = key_value.getChild();
//			result.addChild( field, shallow ? child : FlexiHydraXML.deepCopy( child ) );
//		}
//	}
//
//	private static void assignAttributes( Fusion element, final FlexiHydraXML result ) {
//		for ( Fusion.Attribute key_value : element.attributesToList() ) {
//			@NonNull String key = key_value.getKey();
//			@NonNull String value = key_value.getValue();
//			result.addValue( key, value );
//		}
//	}

//	@Override
//	public @NonNull Fusion shallowCopy() {
//		return copy( true, this );
//	}
//
//	@Override
//	public @NonNull Fusion deepCopy() {
//		return copy( false, this );
//	}
}
