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

import java.io.PrintWriter;
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
import com.steelypip.powerups.common.StdPair;
import com.steelypip.powerups.util.StarMap;
import com.steelypip.powerups.util.StdStarMap;

/**
 * This implementation provides a full implementation of all the
 * mandatory and optional methods of the MinXML interface. It aims
 * to strike a balance between fast access, update and reasonable
 * compactness in the most important cases. A TreeMap is used to
 * track attributes and an ArrayList to track children; these provide
 * good performance at the expense of space. However, when there
 * are no attributes the TreeMap is not allocated and when there are
 * no children the ArrayList is not allocated; these two cases are
 * so common that the reduction in space is (typically) significant. 
 */
public class FlexiMinXMLStar implements MinXMLStar {
	
	protected @NonNull String name;
	protected @NonNull TreeMap< @NonNull String, ArrayList< @NonNull String > > attributes = new TreeMap<>();
	protected @NonNull TreeMap< @NonNull String, ArrayList< @NonNull FlexiMinXMLStar > > links = new TreeMap<>();
	
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
	public FlexiMinXMLStar( final @NonNull String name ) {
		this.name = name.intern(); 
	}
	
	public FlexiMinXMLStar( final @NonNull MinXMLStar element ) {
		this.name = element.getInternedName();
		assignAttributes( element, this );
		assignLinks( element, this );
	}
	
	public @NonNull FlexiMinXMLStar toFlexiMinXMLStar( @NonNull MinXMLStar that ) {
		if ( that instanceof FlexiMinXMLStar ) {
			return (FlexiMinXMLStar)that;
		} else {
			return new FlexiMinXMLStar( that );
		}
	}

	//TODO implement parser
	/*public static MinXML fromString( final String input ) {
		return new MinXMLParser( new StringReader( input ) ).readElement();
	}*/
	
	/**
	 * Creates a copy of the top-level node of the given element.
	 * @param element the element to copy
	 * @return the copy
	 */
	public static @NonNull FlexiMinXMLStar shallowCopy( @NonNull MinXMLStar element ) {
		return new FlexiMinXMLStar( element );
	}
		
	public static @NonNull FlexiMinXMLStar deepCopy( @NonNull MinXMLStar element ) {
		final FlexiMinXMLStar result = new FlexiMinXMLStar( element.getName() );
		assignAttributes( element, result );
		assignLinks( element, result );
		return result;
	}

	private static void assignLinks( MinXMLStar element, final FlexiMinXMLStar result ) {
		for ( MinXMLStar.Link key_value : element.linksToList() ) {
			@NonNull String key = key_value.getField();
			@NonNull FlexiMinXMLStar value = deepCopy( key_value.getChild() );
			TreeMap< String, ArrayList< FlexiMinXMLStar > > attrs = result.links;
			if ( attrs.containsKey( key ) ) {
				attrs.get( key ).add( value );
			} else {
				ArrayList< FlexiMinXMLStar > values = new ArrayList<>();
				values.add( value );
				attrs.put( key, values );
			}
		}
	}

	private static void assignAttributes( MinXMLStar element, final FlexiMinXMLStar result ) {
		for ( MinXMLStar.Attr key_value : element.attributesToList() ) {
			@NonNull String key = key_value.getKey();
			@NonNull String value = key_value.getValue();
			TreeMap< String, ArrayList< @NonNull String > > attrs = result.attributes;
			if ( attrs.containsKey( key ) ) {
				attrs.get( key ).add( value );
			} else {
				ArrayList< @NonNull String > values = new ArrayList<>();
				values.add( value );
				attrs.put( key, values );
			}
		}
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
			for ( ArrayList< FlexiMinXMLStar > children : this.links.values() ) {
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
		final ArrayList< String > values = this.attributes.get( key );
		if ( values != null ) {
			return values.get( 0 );
		} else {
			throw new IllegalArgumentException( String.format(  "No attributes with this key (%s)", key ) );
		}
	}

	@Override
	public String getValue( @NonNull String key, int index ) throws IllegalArgumentException {
		final ArrayList< String > values = this.attributes.get( key );
		if ( values != null ) {
			try {
				return values.get( index );
			} catch ( IndexOutOfBoundsException _e ) {
				throw new IllegalArgumentException( String.format( "No attribute this key (%s) at this position (%d)", key, index ) );				
			}
		} else {
			throw new IllegalArgumentException( String.format(  "No attribute with this key (%s)", key ) );
		}
	}

	@Override
	public @Nullable String getValue( @NonNull String key, @Nullable String otherwise ) {
		final ArrayList< String > values = this.attributes.get( key );
		if ( values != null ) {
			return values.get( 0 );
		} else {
			return otherwise;
		}
	}

	@Override
	public @Nullable String getValue( @NonNull String key, int index, @Nullable String otherwise ) {
		final ArrayList< String > values = this.attributes.get( key );
		if ( values != null ) {
			try {
				return values.get( index );
			} catch ( IndexOutOfBoundsException _e ) {
				return otherwise;				
			}
		} else {
			return otherwise;				
		}
	}

	@Override
	public void setValue( @NonNull String key, @NonNull String value ) throws UnsupportedOperationException {
		ArrayList< @NonNull String > values = this.attributes.get( key );
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
		ArrayList< @NonNull String > values = this.attributes.get( key );
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
			this.attributes.remove( key );
		} else {		
			this.attributes.put( key, new_values );
		}
	}

	@Override
	public void addValue( @NonNull String key, @NonNull String value ) throws UnsupportedOperationException {
		ArrayList< @NonNull String > values = this.attributes.get( key );
		if ( values == null ) {
			values = new ArrayList<>();
			this.attributes.put( key, values );
		}
		values.add( value );
	}

	@Override
	public void removeValue( @NonNull String key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
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
	}

	@Override
	public void removeValue( @NonNull String key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
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
	}
	

	@Override
	public void clearAllAttributes() throws UnsupportedOperationException {
		if ( this.attributes != null ) {
			this.attributes.clear();
			//TODO: or simply nullify this.attributes.
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
	public boolean hasAnyAttributes() {
		//TODO implement nullable attributes.
		if ( this.attributes != null ) {
			return ! this.attributes.isEmpty();
		} else {
			return false;
		}
	}

	@Override
	public boolean hasNoAttributes() {
		//TODO implement nullable attributes.
		if ( this.attributes != null ) {
			return this.attributes.isEmpty();
		} else {
			return true;
		}
	}

	@Override
	public boolean hasAttribute( @NonNull String key ) {
		return this.attributes.containsKey( key );
	}

	@Override
	public boolean hasAttribute( @NonNull String key, int index ) {
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
	public boolean hasSingleValue( @NonNull String key ) {
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
	public boolean hasSizeKeys( final int n ) {
		if ( this.attributes != null ) {
			return n == this.attributes.size();
		} else {
			return n == 0;
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
	public boolean hasKeys() {
		return ! this.hasNoKeys();
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

	@Override
	public boolean hasValues( @NonNull String key ) {
		return ! this.hasNoValues( key );
	}

	static class Attr implements MinXMLStar.Attr {

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
	public @NonNull List< MinXMLStar.Attr > attributesToList() {
		final List< MinXMLStar.Attr > list = new ArrayList<>();
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
		final List< @NonNull String > values = this.attributes.get( key );
		final @NonNull List< @NonNull String > list = new ArrayList< @NonNull String >();
		if ( values != null ) {
			list.addAll( values );
		}
		return list;	
	}

	@SuppressWarnings("null")
	@Override
	public @NonNull Map< @NonNull String, @NonNull String > firstValuesToMap() {
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
	public @NonNull StarMap< @NonNull String, @NonNull String > attributesToStarMap() {
		return new StdStarMap< @NonNull String, @NonNull String >( this.attributesToList() );
	}

	@SuppressWarnings("null")
	@Override
	public @NonNull Map< Pair< @NonNull String, @NonNull Integer >, @NonNull String > attributesToPairMap() {
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
	public MinXMLStar getChild() throws IllegalArgumentException {
		if ( links != null ) {
			final ArrayList< @NonNull FlexiMinXMLStar > flinks = this.links.get( "" );
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
	public @NonNull MinXMLStar getChild( int index ) throws IllegalArgumentException {
		if ( links != null ) {
			final ArrayList< @NonNull FlexiMinXMLStar > flinks = this.links.get( "" );
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
	public @NonNull MinXMLStar getChild( @NonNull String field ) throws IllegalArgumentException {
		if ( links != null ) {
			final ArrayList< @NonNull FlexiMinXMLStar > flinks = this.links.get( field );
			if ( flinks != null ) {
				return flinks.get( 0 );
			} else {
				throw new IllegalArgumentException( String.format(  "No field with this field (%s)", field ) );
			}
		} else {
			throw new IllegalArgumentException( "No children" );			
		}
	}

	@Override
	public @NonNull MinXMLStar getChild( @NonNull String field, int index ) throws IllegalArgumentException {
		if ( links != null ) {
			final ArrayList< @NonNull FlexiMinXMLStar > flinks = this.links.get( field );
			if ( flinks != null && index < flinks.size() && 0 <= index ) {
				return flinks.get( index );
			} else {
				throw new IllegalArgumentException( String.format(  "No field at this position (%s) with this field (%s)", index, field ) );
			}
		} else {
			throw new IllegalArgumentException( "No children" );			
		}
	}

	@Override
	public @Nullable MinXMLStar getChild( @NonNull String field, @Nullable MinXMLStar otherwise ) {
		if ( this.links == null ) return otherwise;
		final ArrayList< @NonNull FlexiMinXMLStar > flinks = this.links.get( field );
		if ( flinks != null ) {
			return flinks.get( 0 );
		} else {
			return otherwise;
		}
	}

	@Override
	public @Nullable MinXMLStar getChild( @NonNull String field, int index, @Nullable MinXMLStar otherwise ) {
		if ( this.links == null ) return otherwise;
		final ArrayList< @NonNull FlexiMinXMLStar > flinks = this.links.get( field );
		if ( flinks != null && index < flinks.size() && 0 <= index ) {
			return flinks.get( index );
		} else {
			return otherwise;
		}
	}

	@Override
	public void setChild( @NonNull String field, @NonNull MinXMLStar value ) throws UnsupportedOperationException {
		if ( this.links == null ) {
			this.links = new TreeMap<>();
		}
		ArrayList< @NonNull FlexiMinXMLStar > flinks = this.links.get( field );
		if ( flinks == null ) {
			flinks = new ArrayList<>();
		}
		final @NonNull FlexiMinXMLStar fv = toFlexiMinXMLStar( value );
		if ( flinks.isEmpty() ) {
			flinks.add( fv );
		} else {
			flinks.set( 0, fv );
		}
	}

	@Override
	public void setChild( @NonNull String field, int index, @NonNull MinXMLStar value ) throws IllegalArgumentException, UnsupportedOperationException {
		if ( this.links == null ) {
			this.links = new TreeMap<>();
		}
		ArrayList< @NonNull FlexiMinXMLStar > flinks = this.links.get( field );
		if ( flinks == null ) {
			flinks = new ArrayList<>();
		}
		final @NonNull FlexiMinXMLStar fv = toFlexiMinXMLStar( value );
		if ( index == flinks.size() ) {
			flinks.add( fv );
		} else if ( 0 <= index && index < flinks.size() ){
			flinks.set( 0, fv );
		} else {
			throw new IllegalArgumentException( String.format( "Bounds error while trying to set the Nth (%d) element of the field (%s)", index, field ) );
		}
	}
	

	@Override
	public void setAllChildren( @NonNull String field, Iterable< @NonNull MinXMLStar > values ) throws UnsupportedOperationException {
		if ( this.links == null ) {
			this.links = new TreeMap<>();
		}
		final ArrayList< @NonNull FlexiMinXMLStar > array = new ArrayList<>();
		for ( @NonNull MinXMLStar x : values ) {
			array.add( toFlexiMinXMLStar( x ) );
		}
		this.links.put( field, array );
	}

	@Override
	public void addChild( @NonNull String field, @NonNull MinXMLStar value ) throws UnsupportedOperationException {
		if ( this.links == null ) {
			this.links = new TreeMap<>();
		}
		ArrayList< @NonNull FlexiMinXMLStar > flinks = this.links.get( field );
		if ( flinks == null ) {
			flinks = new ArrayList<>();
		}
		final @NonNull FlexiMinXMLStar fv = toFlexiMinXMLStar( value );
		flinks.add( fv );
	}

	@Override
	public void removeChild( @NonNull String field ) throws UnsupportedOperationException, IllegalArgumentException {
		if ( this.links != null ) {
			ArrayList< @NonNull FlexiMinXMLStar > flinks = this.links.get( field );
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
			ArrayList< @NonNull FlexiMinXMLStar > flinks = this.links.get( field );
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
		if ( this.links == null ) return;
		this.links.clear();
	}

	@Override
	public void clearLinks( @NonNull String key ) throws UnsupportedOperationException {
		if ( this.links == null ) return;
		this.links.remove( key );
	}

	@Override
	public boolean hasAnyLinks() {
		if ( this.links == null ) return false;
		return ! this.links.isEmpty();
	}

	@Override
	public boolean hasNoLinks() {
		if ( this.links == null ) return true;
		return this.links.isEmpty();
	}

	@Override
	public boolean hasLink( @NonNull String field ) {
		if ( this.links == null ) return false;
		final ArrayList< FlexiMinXMLStar > flinks = this.links.get( field );
		if ( flinks == null ) return false;
		return flinks.isEmpty();
	}

	@Override
	public boolean hasLink( @NonNull String field, int index ) {
		if ( this.links == null ) return false;
		final ArrayList< FlexiMinXMLStar > flinks = this.links.get( field );
		if ( flinks == null ) return false;
		return 0 <= index && index < flinks.size();
	}

	@Override
	public boolean hasLink( @NonNull String field, @Nullable MinXMLStar value ) {
		if ( this.links == null ) return false;
		final ArrayList< FlexiMinXMLStar > flinks = this.links.get( field );
		if ( flinks == null ) return false;
		return flinks.contains( value );
	}

	@Override
	public boolean hasLink( @NonNull String field, int index, @Nullable MinXMLStar value ) {
		if ( this.links == null ) return false;
		final ArrayList< FlexiMinXMLStar > flinks = this.links.get( field );
		if ( flinks == null ) return false;
		if ( ! ( 0 <= index && index < flinks.size() ) ) return false;
		return flinks.get( index ).equals( value );
	}

	@Override
	public boolean hasSingleChild( @NonNull String field ) {
		if ( this.links == null ) return false;
		final ArrayList< FlexiMinXMLStar > flinks = this.links.get( field );
		if ( flinks == null ) return false;
		return flinks.size() == 1;
	}

	@Override
	public int sizeLinks() {
		int sofar = 0;
		if ( this.links != null ) {
			for ( Map.Entry< @NonNull String, @NonNull ArrayList< @NonNull FlexiMinXMLStar > > lentry : this.links.entrySet() ) {
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
		//	Note that this depends on the list of children never being empty
		//	but being deleted when they are empty.
		return this.links.isEmpty();
	}

	@Override
	public boolean hasFields() {
		if ( this.links == null ) return false;
		return ! this.links.isEmpty();
	}

	@Override
	public int sizeChildren( @NonNull String field ) {
		if ( this.links == null ) return 0;
		final ArrayList< FlexiMinXMLStar > flinks = this.links.get( field );
		if ( flinks == null ) return 0;
		return flinks.size();
	}

	@Override
	public boolean hasSizeChildren( @NonNull String field, int n ) {
		if ( this.links == null ) return n == 0;
		final ArrayList< FlexiMinXMLStar > flinks = this.links.get( field );
		if ( flinks == null ) return n == 0;
		return flinks.size() == n;
	}

	@Override
	public boolean hasNoChildren( @NonNull String field ) {
		if ( this.links == null ) return true;
		final ArrayList< FlexiMinXMLStar > flinks = this.links.get( field );
		if ( flinks == null ) return true;
		return flinks.isEmpty();
	}

	@Override
	public boolean hasChildren( @NonNull String field ) {
		return ! this.hasNoChildren( field );
	}

	@Override
	public @NonNull List< @NonNull MinXMLStar > childrenToList( @NonNull String field ) {
		final ArrayList< @NonNull MinXMLStar > sofar = new ArrayList<>();
		if ( this.links != null ) {
			final ArrayList< @NonNull FlexiMinXMLStar > flinks = this.links.get( field );
			if ( flinks != null ) {
				sofar.addAll( flinks );
			}
		}		
		return sofar;
	}

	@Override
	public Map< @NonNull String, @NonNull MinXMLStar > firstChildrenToMap() {
		final Map< @NonNull String, @NonNull MinXMLStar > sofar = new TreeMap<>();
		if ( this.links != null ) {
			if ( this.links != null ) {
				for ( Map.Entry< @NonNull String, @NonNull ArrayList< @NonNull FlexiMinXMLStar > > lentry : this.links.entrySet() ) {
					final @NonNull ArrayList< @NonNull FlexiMinXMLStar > children = lentry.getValue();
					if ( ! children.isEmpty() ) {
						final @NonNull String field = lentry.getKey();
						sofar.put( field, children.get( 0 ) );
					}
				}
			}
		}		
		return sofar;
	}

	@Override
	public StarMap< @NonNull String, ? extends @NonNull MinXMLStar > linksToStarMap() {
		if ( this.links != null ) {
			return new StdStarMap<>( this.links );
		} else {
			return new StdStarMap<>();
		}
	}

	@Override
	public Map< Pair< @NonNull String, @NonNull Integer >, ? extends @NonNull MinXMLStar > linksToPairMap() {
		final Map< Pair< @NonNull String, @NonNull Integer >, @NonNull MinXMLStar > sofar = new TreeMap<>();
		if ( this.links != null ) {
			for ( Map.Entry< @NonNull String, ArrayList< @NonNull FlexiMinXMLStar > > lentry : this.links.entrySet() ) {
				final @NonNull String field = lentry.getKey();
				final ArrayList< @NonNull FlexiMinXMLStar > children = lentry.getValue();
				int n = 0;
				for ( @NonNull FlexiMinXMLStar child : children ) {
					final Pair< @NonNull String, @NonNull Integer >p = new CmpPair< @NonNull String, @NonNull Integer >( field, n++ );
					sofar.put( p, child );
				}
			}			
		}
		return sofar;
	}
	
	static class Link implements MinXMLStar.Link {
		
		@NonNull String field;
		@NonNull Integer fieldIndex;
		@NonNull MinXMLStar child;
		
		public Link( @NonNull String field, @NonNull Integer fieldIndex, @NonNull MinXMLStar child ) {
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

		public @NonNull MinXMLStar getChild() {
			return child;
		}
		
	}

	@Override
	public Iterator< MinXMLStar.Link > iterator() {
		final LinkedList< MinXMLStar.Link > sofar = new LinkedList<>();
		if ( this.links != null ) {
			for ( Map.Entry< @NonNull String, ArrayList< @NonNull FlexiMinXMLStar > > lentry : this.links.entrySet() ) {
				final @NonNull String field = lentry.getKey();
				final ArrayList< @NonNull FlexiMinXMLStar > children = lentry.getValue();
				int n = 0;
				for ( @NonNull FlexiMinXMLStar child : children ) {
					sofar.add( new Link( field, n++, child ) );
				}
			}			
		}
		return sofar.iterator();		
	}

	@Override
	public @NonNull List< MinXMLStar.Link > linksToList() {
		final LinkedList< MinXMLStar.Link > sofar = new LinkedList<>();
		if ( this.links != null ) {
			for ( Map.Entry< @NonNull String, ArrayList< @NonNull FlexiMinXMLStar > > lentry : this.links.entrySet() ) {
				final @NonNull String field = lentry.getKey();
				final ArrayList< @NonNull FlexiMinXMLStar > children = lentry.getValue();
				int n = 0;
				for ( @NonNull FlexiMinXMLStar child : children ) {
					sofar.add( new Link( field, n++, child ) );
				}
			}			
		}
		return sofar;		
	}

	@Override
	public void print( PrintWriter pw ) {
		new MinXMLStarWriter( pw ).print( this );
	}

	@Override
	public void print( Writer w ) {
		new MinXMLStarWriter( w ).print( this );
	}

	@Override
	public void prettyPrint( PrintWriter pw ) {
		// TODO Auto-generated method stub
		throw new RuntimeException( "TO BE DONE" );
	}

	@Override
	public void prettyPrint( Writer w ) {
		// TODO Auto-generated method stub
		throw new RuntimeException( "TO BE DONE" );		
	}

	@Override
	public @NonNull MinXMLStar shallowCopy() {
		return shallowCopy( this );
	}

	@Override
	public @NonNull MinXMLStar deepCopy() {
		return deepCopy( this );
	}

	@Override
	public @NonNull Set< @NonNull String > keysToSet() {
		return new TreeSet<>( this.attributes.keySet() );
	}

	@Override
	public @NonNull Set< @NonNull String > fieldsToSet() {
		return new TreeSet<>( this.links.keySet() );
	}

	
}
