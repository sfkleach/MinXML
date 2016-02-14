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

import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
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
import com.steelypip.powerups.common.StdIndenter;
import com.steelypip.powerups.util.StarMap;
import com.steelypip.powerups.util.StdStarMap;

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
public class FlexiFusion extends LiteralConstantsFusion implements MutableFusion {
	
	protected @NonNull String name;
	protected TreeMap< @NonNull String, ArrayList< @NonNull String > > attributes = null;
	protected TreeMap< @NonNull String, ArrayList< @NonNull Fusion > > links = null;
	
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
	public FlexiFusion( final @NonNull String name ) {
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
	public @NonNull TreeMap< @NonNull String, ArrayList< @NonNull String > > getNonNullAttributes() {
		if ( this.attributes == null ) {
			this.attributes = new TreeMap<>();
		}
		return this.attributes;
	}

	@SuppressWarnings("null")
	public @NonNull TreeMap< @NonNull String, ArrayList< @NonNull Fusion > > getNonNullLinks() {
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
			for ( ArrayList< String > values : this.attributes.values() ) {
				values.trimToSize();
			}
		}
		if ( this.links != null ) {
			for ( ArrayList< Fusion > children : this.links.values() ) {
				children.trimToSize();
			}
		}
	}

	@Override
	public @NonNull String getName() {
		return this.name;
	}

	@Override
	public @NonNull String getInternedName() {
		return this.name;
	}

	@Override
	public boolean hasName( @Nullable String name ) {
		return name != null && this.name.equals( name );
	}

	@Override
	public void setName( @NonNull String x ) throws UnsupportedOperationException {
		this.name = x;
	}

	@Override
	public String getValue( @NonNull String key ) throws IllegalArgumentException {
		if ( this.attributes != null ) {
			final ArrayList< String > values = this.attributes.get( key );
			if ( values != null ) {
				return values.get( 0 );
			}
		}
		throw new IllegalArgumentException( String.format(  "No attributes with this key (%s)", key ) );			
		
	}

	@Override
	public String getValue( @NonNull String key, int index ) throws IllegalArgumentException {
		if ( this.attributes != null ) {
			final ArrayList< String > values = this.attributes.get( key );
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
	public @Nullable String getValue( @NonNull String key, @Nullable String otherwise ) {
		if ( this.attributes != null ) {
			final ArrayList< String > values = this.attributes.get( key );
			if ( values != null ) {
				return values.get( 0 );
			}
		}
		return otherwise;
	}

	@Override
	public @Nullable String getValue( @NonNull String key, int index, @Nullable String otherwise ) {
		if ( this.attributes != null ) {
			final ArrayList< String > values = this.attributes.get( key );
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
	public void setValue( @NonNull String key, @NonNull String value ) throws UnsupportedOperationException {
		ArrayList< @NonNull String > values = this.getNonNullAttributes().get( key );
		if ( values == null ) {
			values = new ArrayList<>();
			values.add( value );
			this.attributes.put( key, values );
		} else {
			values.set( 0, value );
		}
	}

	@Override
	public void setValue( @NonNull String key, int index, @NonNull String value ) throws IllegalArgumentException, UnsupportedOperationException {
		ArrayList< @NonNull String > values = this.getNonNullAttributes().get( key );
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
	public void setAllValues( @NonNull String key, Iterable< @NonNull String > values ) throws UnsupportedOperationException {
		final ArrayList< @NonNull String > new_values = new ArrayList<>();
		for ( @NonNull String x : values ) {
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
	public void addValue( @NonNull String key, @NonNull String value ) throws UnsupportedOperationException {
		ArrayList< @NonNull String > values = this.getNonNullAttributes().get( key );
		if ( values == null ) {
			values = new ArrayList<>();
			this.attributes.put( key, values );
		}
		values.add( value );
	}

	@Override
	public void removeValue( @NonNull String key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		if ( this.attributes != null ) {
			final ArrayList< @NonNull String > values = this.attributes.get( key );
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
	public void removeValue( @NonNull String key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		if ( this.attributes != null ) {
			final ArrayList< @NonNull String > values = this.attributes.get( key );
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
	public void clearAttributes( @NonNull String key ) throws UnsupportedOperationException {
		if ( this.attributes != null ) {
			final ArrayList< @NonNull String > values = this.attributes.get( key );
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
	public boolean hasAttribute( @NonNull String key ) {
		if ( this.attributes != null ) {
			return this.attributes.containsKey( key );
		} else {
			return false;
		}
	}

	@Override
	public boolean hasValueAt( @NonNull String key, int index ) {
		if ( this.attributes != null ) {
			final ArrayList< @NonNull String > values = this.attributes.get( key );
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
	public boolean hasAttribute( @NonNull String key, @Nullable String value ) {
		if ( this.attributes != null && value != null ) {
			final ArrayList< @NonNull String > values = this.attributes.get( key );
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
	public boolean hasAttribute( @NonNull String key, int index, @Nullable String value ) {
		if ( this.attributes != null && value != null ) {
			final ArrayList< @NonNull String > values = this.attributes.get( key );
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
	public boolean hasOneValue( @NonNull String key ) {
		if ( this.attributes != null ) {
			final ArrayList< @NonNull String > values = this.attributes.get( key );
			return values != null && values.size() == 1;
		} else {
			return false;
		}
	}
	
	

	@Override
	public int sizeAttributes() {
		if ( this.attributes != null ) {
			int sum = 0;
			for ( ArrayList< @NonNull String > values : this.attributes.values() ) {
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
	public int sizeValues( @NonNull String key ) {
		if ( this.attributes != null ) {
			final ArrayList< @NonNull String > values = this.attributes.get( key );
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
	public boolean hasSizeValues( @NonNull String key, int n ) {
		if ( this.attributes != null ) {
			final ArrayList< @NonNull String > values = this.attributes.get( key );
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
	public boolean hasNoValues( @NonNull String key ) {
		if ( this.attributes != null ) {
			final ArrayList< @NonNull String > values = this.attributes.get( key );
			if ( values == null ) {
				return true;
			} else {
				return values.isEmpty();
			}
		} else {
			return true;
		}
	}

	static class Attr implements Fusion.Attr {

		private @NonNull String key;
		private int keyIndex;
		private @NonNull String value;
		
		public Attr( @NonNull String key, int keyIndex, @NonNull String value ) {
			super();
			this.key = key;
			this.keyIndex = keyIndex;
			this.value = value;
		}

		public @NonNull String getKey() {
			return key;
		}

		public int getKeyIndex() {
			return keyIndex;
		}

		public @NonNull String getValue() {
			return value;
		}
		
	}
	
	@Override
	public @NonNull List< Fusion.Attr > attributesToList() {
		final List< Fusion.Attr > list = new ArrayList<>();
		if ( this.attributes != null ) {
			for ( Map.Entry< @NonNull String, ArrayList< @NonNull String > > e : this.attributes.entrySet() ) {
				int n = 0;
				for ( @NonNull String v : e.getValue() ) {
					list.add( new Attr( e.getKey(), n++, v ) );
				}
			}
		}
		return list;
	}

	@Override
	public @NonNull List< @NonNull String > valuesToList( @NonNull String key ) {
		final @NonNull List< @NonNull String > list = new ArrayList< @NonNull String >();
		if ( this.attributes != null ) {
			final List< @NonNull String > values = this.attributes.get( key );
			if ( values != null ) {
				list.addAll( values );
			}
		}
		return list;	
	}

	@SuppressWarnings("null")
	@Override
	public @NonNull Map< @NonNull String, String > firstValuesToMap() {
		final TreeMap< @NonNull String, @NonNull String > sofar = new TreeMap<>();
		if ( this.attributes != null ) {
			for ( Map.Entry< String, ArrayList< @NonNull String > > e : this.attributes.entrySet() ) {
				if ( ! e.getValue().isEmpty() ) {
					sofar.put( e.getKey(), e.getValue().get( 0 ) );
				}
			}
		}
		return sofar;
	}

	@Override
	public @NonNull StarMap< @NonNull String, @Nullable String > attributesToStarMap() {
		return new StdStarMap< @NonNull String, @Nullable String >( this.attributesToList() );
	}

	@SuppressWarnings("null")
	@Override
	public @NonNull Map< Pair< @NonNull String, @NonNull Integer >, String > attributesToPairMap() {
		final TreeMap< Pair< @NonNull String, @NonNull Integer >, @NonNull String > sofar = new TreeMap<>();
		if ( this.attributes != null ) {
			for ( Map.Entry< String, ArrayList< @NonNull String > > e : this.attributes.entrySet() ) {
				int n = 0;
				for ( @NonNull String x : e.getValue() ) {
					sofar.put( new CmpPair< @NonNull String, @NonNull Integer >( e.getKey(), n++ ), x );
				}
			}
		}
		return sofar;
	}
	
	@Override
	@NonNull
	public Fusion getChild() throws IllegalArgumentException {
		if ( links != null ) {
			final ArrayList< @NonNull Fusion > flinks = this.links.get( "" );
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
	public @NonNull Fusion getChild( int index ) throws IllegalArgumentException {
		if ( links != null ) {
			final ArrayList< @NonNull Fusion > flinks = this.links.get( "" );
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
	public @NonNull Fusion getChild( @NonNull String field ) throws IllegalArgumentException {
		if ( links != null ) {
			final ArrayList< @NonNull Fusion > flinks = this.links.get( field );
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
	public @NonNull Fusion getChild( @NonNull String field, int index ) throws IllegalArgumentException {
		if ( links != null ) {
			final ArrayList< @NonNull Fusion > flinks = this.links.get( field );
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
	public @Nullable Fusion getChild( @NonNull String field, @Nullable Fusion otherwise ) {
		if ( this.links == null ) return otherwise;
		final ArrayList< @NonNull Fusion > flinks = this.links.get( field );
		if ( flinks != null ) {
			return flinks.get( 0 );
		} else {
			return otherwise;
		}
	}

	@Override
	public @Nullable Fusion getChild( @NonNull String field, int index, @Nullable Fusion otherwise ) {
		if ( this.links == null ) return otherwise;
		final ArrayList< @NonNull Fusion > flinks = this.links.get( field );
		if ( flinks != null && index < flinks.size() && 0 <= index ) {
			return flinks.get( index );
		} else {
			return otherwise;
		}
	}

	@Override
	public void setChild( @NonNull String field, @NonNull Fusion value ) throws UnsupportedOperationException {
		ArrayList< @NonNull Fusion > flinks = this.getNonNullLinks().get( field );
		if ( flinks == null ) {
			flinks = new ArrayList<>();
		}
		final @NonNull Fusion fv = value;
		if ( flinks.isEmpty() ) {
			flinks.add( fv );
		} else {
			flinks.set( 0, fv );
		}
	}

	@Override
	public void setChild( @NonNull String field, int index, @NonNull Fusion value ) throws IllegalArgumentException, UnsupportedOperationException {
		ArrayList< @NonNull Fusion > flinks = this.getNonNullLinks().get( field );
		if ( flinks == null ) {
			flinks = new ArrayList<>();
		}
		final @NonNull Fusion fv = value;
		if ( index == flinks.size() ) {
			flinks.add( fv );
		} else if ( 0 <= index && index < flinks.size() ){
			flinks.set( 0, fv );
		} else {
			throw new IllegalArgumentException( String.format( "Bounds error while trying to set the Nth (%d) element of the field (%s)", index, field ) );
		}
	}
	

	@Override
	public void setAllChildren( @NonNull String field, Iterable< @NonNull Fusion > values ) throws UnsupportedOperationException {
		final ArrayList< @NonNull Fusion > array = new ArrayList<>();
		for ( @NonNull Fusion x : values ) {
			array.add( x );
		}
		if ( ! array.isEmpty() ) {
			this.getNonNullLinks().put( field, array );
		}
	}

	@Override
	public void addChild( @NonNull String field, @NonNull Fusion value ) throws UnsupportedOperationException {
		ArrayList< @NonNull Fusion > flinks = this.getNonNullLinks().get( field );
		if ( flinks == null ) {
			flinks = new ArrayList<>();
			this.links.put( field, flinks );
		}
		//final @NonNull AbsFlexiFusion fv = toFlexiMinXMLStar( value );
		flinks.add( value );
	}

	@Override
	public void removeChild( @NonNull String field ) throws UnsupportedOperationException, IllegalArgumentException {
		if ( this.links != null ) {
			ArrayList< @NonNull Fusion > flinks = this.links.get( field );
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
	public void removeChild( @NonNull String field, int index ) throws UnsupportedOperationException, IllegalArgumentException {
		if ( this.links != null ) {
			ArrayList< @NonNull Fusion > flinks = this.links.get( field );
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
	public void clearLinks( @NonNull String key ) throws UnsupportedOperationException {
		if ( this.links == null ) return;
		this.links.remove( key );
	}

	@Override
	public boolean hasNoLinks() {
		if ( this.links == null ) return true;
		return this.links.isEmpty();
	}
	

	@Override
	public boolean hasLink( @NonNull String field ) {
		if ( this.links == null ) return false;
		final ArrayList< Fusion > flinks = this.links.get( field );
		if ( flinks == null ) return false;
		return ! flinks.isEmpty();
	}

	@Override
	public boolean hasLink( @NonNull String field, int index ) {
		if ( this.links == null ) return false;
		final ArrayList< Fusion > flinks = this.links.get( field );
		if ( flinks == null ) return false;
		return 0 <= index && index < flinks.size();
	}

	@Override
	public boolean hasLink( @NonNull String field, @Nullable Fusion value ) {
		if ( this.links == null ) return false;
		final ArrayList< Fusion > flinks = this.links.get( field );
		if ( flinks == null ) return false;
		return flinks.contains( value );
	}

	@Override
	public boolean hasLink( @NonNull String field, int index, @Nullable Fusion value ) {
		if ( this.links == null ) return false;
		final ArrayList< Fusion > flinks = this.links.get( field );
		if ( flinks == null ) return false;
		if ( ! ( 0 <= index && index < flinks.size() ) ) return false;
		return flinks.get( index ).equals( value );
	}

	@Override
	public boolean hasOneChild( @NonNull String field ) {
		if ( this.links == null ) return false;
		final ArrayList< Fusion > flinks = this.links.get( field );
		if ( flinks == null ) return false;
		return flinks.size() == 1;
	}

	@Override
	public int sizeLinks() {
		int sofar = 0;
		if ( this.links != null ) {
			for ( Map.Entry< @NonNull String, ArrayList< @NonNull Fusion > > lentry : this.links.entrySet() ) {
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
		for ( ArrayList< @NonNull Fusion > value : this.links.values() ) {
			if ( ! value.isEmpty() ) return false;
		}
		return true;
	}

	@Override
	public int sizeChildren( @NonNull String field ) {
		if ( this.links == null ) return 0;
		final ArrayList< Fusion > flinks = this.links.get( field );
		if ( flinks == null ) return 0;
		return flinks.size();
	}

	@Override
	public boolean hasSizeChildren( @NonNull String field, int n ) {
		if ( this.links == null ) return n == 0;
		final ArrayList< Fusion > flinks = this.links.get( field );
		if ( flinks == null ) return n == 0;
		return flinks.size() == n;
	}

	@Override
	public boolean hasNoChildren( @NonNull String field ) {
		if ( this.links == null ) return true;
		final ArrayList< Fusion > flinks = this.links.get( field );
		if ( flinks == null ) return true;
		return flinks.isEmpty();
	}

	@Override
	public @NonNull List< @NonNull Fusion > childrenToList( @NonNull String field ) {
		final ArrayList< @NonNull Fusion > sofar = new ArrayList<>();
		if ( this.links != null ) {
			final ArrayList< @NonNull Fusion > flinks = this.links.get( field );
			if ( flinks != null ) {
				sofar.addAll( flinks );
			}
		}		
		return sofar;
	}

	@Override
	public Map< @NonNull String, Fusion > firstChildrenToMap() {
		final Map< @NonNull String, @NonNull Fusion > sofar = new TreeMap<>();
		if ( this.links != null ) {
			if ( this.links != null ) {
				for ( Map.Entry< @NonNull String, ArrayList< @NonNull Fusion > > lentry : this.links.entrySet() ) {
					final ArrayList< @NonNull Fusion > children = lentry.getValue();
					if ( children != null && ! children.isEmpty() ) {
						final @NonNull String field = lentry.getKey();
						sofar.put( field, children.get( 0 ) );
					}
				}
			}
		}		
		return sofar;
	}

	@SuppressWarnings("null")
	@Override
	public StarMap< @NonNull String, ? extends @NonNull Fusion > linksToStarMap() {
		if ( this.links != null ) {
			return new StdStarMap<>( this.links );
		} else {
			return new StdStarMap<>();
		}
	}

	@Override
	public Map< Pair< @NonNull String, @NonNull Integer >, ? extends @NonNull Fusion > linksToPairMap() {
		final Map< Pair< @NonNull String, @NonNull Integer >, @NonNull Fusion > sofar = new TreeMap<>();
		if ( this.links != null ) {
			for ( Map.Entry< @NonNull String, ArrayList< @NonNull Fusion > > lentry : this.links.entrySet() ) {
				final @NonNull String field = lentry.getKey();
				final ArrayList< @NonNull Fusion > children = lentry.getValue();
				int n = 0;
				for ( @NonNull Fusion child : children ) {
					final Pair< @NonNull String, @NonNull Integer >p = new CmpPair< @NonNull String, @NonNull Integer >( field, n++ );
					sofar.put( p, child );
				}
			}			
		}
		return sofar;
	}
	
	static class Link implements Fusion.Link {
		
		@NonNull String field;
		@NonNull Integer fieldIndex;
		@NonNull Fusion child;
		
		public Link( @NonNull String field, @NonNull Integer fieldIndex, @NonNull Fusion child ) {
			super();
			this.field = field;
			this.fieldIndex = fieldIndex;
			this.child = child;
		}

		public @NonNull String getField() {
			return field;
		}

		public @NonNull Integer getFieldIndex() {
			return fieldIndex;
		}

		public @NonNull Fusion getChild() {
			return child;
		}
		
	}

	@Override
	public Iterator< Fusion.Link > iterator() {
		final LinkedList< Fusion.Link > sofar = new LinkedList<>();
		if ( this.links != null ) {
			for ( Map.Entry< @NonNull String, ArrayList< @NonNull Fusion > > lentry : this.links.entrySet() ) {
				final @NonNull String field = lentry.getKey();
				final ArrayList< @NonNull Fusion > children = lentry.getValue();
				int n = 0;
				for ( @NonNull Fusion child : children ) {
					sofar.add( new Link( field, n++, child ) );
				}
			}			
		}
		return sofar.iterator();		
	}

	@Override
	public @NonNull List< Fusion.Link > linksToList() {
		final LinkedList< Fusion.Link > sofar = new LinkedList<>();
		if ( this.links != null ) {
			for ( Map.Entry< @NonNull String, ArrayList< @NonNull Fusion > > lentry : this.links.entrySet() ) {
				final @NonNull String field = lentry.getKey();
				final ArrayList< @NonNull Fusion > children = lentry.getValue();
				int n = 0;
				for ( @NonNull Fusion child : children ) {
					sofar.add( new Link( field, n++, child ) );
				}
			}			
		}
		return sofar;		
	}

//	@Override
//	public @NonNull Fusion shallowCopy() {
//		return shallowCopy( this );
//	}
//
//	@Override
//	public @NonNull Fusion deepCopy() {
//		return deepCopy( this );
//	}

	@Override
	public @NonNull Set< @NonNull String > keysToSet() {
		if ( this.attributes != null ) {
			return new TreeSet<>( this.attributes.keySet() );
		} else {
			return new TreeSet<>();
		}
	}

	@Override
	public @NonNull Set< @NonNull String > fieldsToSet() {
		if ( this.links != null ) {
			return new TreeSet<>( this.links.keySet() );
		} else {
			return new TreeSet<>();			
		}
	}

	@Override
	public void addChild( @NonNull Fusion value ) throws UnsupportedOperationException {
		this.addChild( "", value );
	}

//	/**
//	 * Creates a copy of the top-level node of the given element.
//	 * @param element the element to copy
//	 * @return the copy
//	 */
	public static @NonNull FlexiFusion shallowCopy( @NonNull Fusion element ) {
		return copy( true, element );	
	}
		
	public static @NonNull FlexiFusion deepCopy( @NonNull Fusion element ) {
		return copy( false, element );
	}
	
	private static @NonNull FlexiFusion copy( final boolean shallow, @NonNull Fusion element ) {
		final FlexiFusion result = new FlexiFusion( element.getName() );
		assignAttributes( element, result );
		assignLinks( shallow, element, result );
		return result;		
	}

	private static void assignLinks( boolean shallow, Fusion element, final FlexiFusion result ) {
		for ( Fusion.Link key_value : element.linksToList() ) {
			@NonNull String field = key_value.getField();
			@NonNull Fusion child = key_value.getChild();
			result.addChild( field, shallow ? child : FlexiFusion.deepCopy( child ) );
		}
	}

	private static void assignAttributes( Fusion element, final FlexiFusion result ) {
		for ( Fusion.Attr key_value : element.attributesToList() ) {
			@NonNull String key = key_value.getKey();
			@NonNull String value = key_value.getValue();
			result.addValue( key, value );
		}
	}

	@Override
	public @NonNull Fusion shallowCopy() {
		return copy( true, this );
	}

	@Override
	public @NonNull Fusion deepCopy() {
		return copy( false, this );
	}
}
