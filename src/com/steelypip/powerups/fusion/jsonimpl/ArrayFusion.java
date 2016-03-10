package com.steelypip.powerups.fusion.jsonimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.common.CmpPair;
import com.steelypip.powerups.common.EmptyList;
import com.steelypip.powerups.common.EmptyMap;
import com.steelypip.powerups.common.EmptySet;
import com.steelypip.powerups.common.Pair;
import com.steelypip.powerups.fusion.Fusion;
import com.steelypip.powerups.fusion.LiteralConstants;
import com.steelypip.powerups.fusion.StdJSONFeatures;
import com.steelypip.powerups.hydra.Attribute;
import com.steelypip.powerups.hydra.Link;
import com.steelypip.powerups.hydra.StdLink;
import com.steelypip.powerups.util.EmptyStarMap;
import com.steelypip.powerups.util.StarMap;
import com.steelypip.powerups.util.TreeStarMap;

public class ArrayFusion implements Fusion, NullJSONFeatures, LiteralConstants {
	
	List< @NonNull Fusion > children = new ArrayList<>(); 
	
	public ArrayFusion( @NonNull Fusion... children ) {
		for ( @NonNull Fusion child : children ) {
			this.children.add(  child );
		}
	}

	public ArrayFusion( Collection< ? extends @NonNull Fusion > children ) {
		this.children.addAll( children );
	}

	public ArrayFusion( Iterable< ? extends @NonNull Fusion > children ) {
		for ( Fusion child : children ) {
			this.children.add( child );
		}
	}

	@Override
	public String getValue( @NonNull String key ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getValue( @NonNull String key, int index ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public @Nullable String getValue( @NonNull String key, @Nullable String otherwise ) {
		return otherwise;
	}

	@Override
	public @Nullable String getValue( @NonNull String key, int index, @Nullable String otherwise ) {
		return otherwise;
	}

	@Override
	public void setValue( @NonNull String key, @NonNull String value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValue( @NonNull String key, int index, @NonNull String value ) throws IllegalArgumentException, UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAllValues( @NonNull String key, Iterable< @NonNull String > values ) throws UnsupportedOperationException {
		if ( values.iterator().hasNext() ) {
			throw new UnsupportedOperationException();			
		}
	}

	@Override
	public void addValue( @NonNull String key, @NonNull String value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();			
	}

	@Override
	public void removeValue( @NonNull String key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
	}

	@Override
	public void removeValue( @NonNull String key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
	}

	@Override
	public void clearAttributes( @NonNull String key ) throws UnsupportedOperationException {
	}

	@Override
	public void clearAllAttributes() throws UnsupportedOperationException {
	}

	@Override
	public int sizeAttributes() {
		return 0;
	}

	@Override
	public boolean hasSizeAttributes( int n ) {
		return n == 0;
	}

	@Override
	public boolean hasAnyAttributes() {
		return false;
	}

	@Override
	public boolean hasNoAttributes() {
		return true;
	}

	@Override
	public boolean hasAttribute( @NonNull String key ) {
		return false;
	}

	@Override
	public boolean hasValueAt( @NonNull String key, int index ) {
		return false;
	}

	@Override
	public boolean hasAttribute( @NonNull String key, @Nullable String value ) {
		return false;
	}

	@Override
	public boolean hasAttribute( @NonNull String key, int index, @Nullable String value ) {
		return false;
	}

	@Override
	public boolean hasOneValue( @NonNull String key ) {
		return false;
	}

	@Override
	public int sizeKeys() {
		return 0;
	}

	@Override
	public boolean hasSizeKeys( int n ) {
		return n == 0;
	}

	@Override
	public boolean hasNoKeys() {
		return true;
	}

	@Override
	public boolean hasAnyKeys() {
		return false;
	}

	@Override
	public int sizeValues( @NonNull String key ) {
		return 0;
	}

	@Override
	public boolean hasSizeValues( @NonNull String key, int n ) {
		return n == 0;
	}

	@Override
	public boolean hasNoValues( @NonNull String key ) {
		return true;
	}

	@Override
	public boolean hasAnyValues( @NonNull String key ) {
		return false;
	}

	@Override
	public @NonNull Set< @NonNull String > keysToSet() {
		return new EmptySet<>();
	}

	@Override
	public @NonNull List< Attribute< String, String > > attributesToList() {
		return new EmptyList<>();
	}

	@Override
	public @NonNull List< @NonNull String > valuesToList( @NonNull String key ) {
		return new EmptyList<>();
	}

	@Override
	public @NonNull Map< @NonNull String, String > firstValuesToMap() {
		return new EmptyMap<>();
	}

	@Override
	public @NonNull StarMap< @NonNull String, @Nullable String > attributesToStarMap() {
		return new EmptyStarMap<>();
	}

	@Override
	public @NonNull Map< Pair< @NonNull String, @NonNull Integer >, String > attributesToPairMap() {
		return new EmptyMap<>();
	}

	@Override
	public boolean isArray() {
		return true;
	}


	@Override
	public Fusion getChild() throws IllegalArgumentException {
		try {
			return this.children.get( 0 );
		} catch ( IndexOutOfBoundsException _e ) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Fusion getChild( final int index ) throws IllegalArgumentException {
		try {
			return this.children.get( index );
		} catch ( IndexOutOfBoundsException _e ) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Fusion getChild( final @NonNull String field ) throws IllegalArgumentException {
		if ( field.equals( this.defaultField() ) ) {
			return this.getChild();
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Fusion getChild( @NonNull String field, int index ) throws IllegalArgumentException {
		if ( field.equals( this.defaultField() ) ) {
			return this.getChild( index );
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public @Nullable Fusion getChild( @NonNull String field, @Nullable Fusion otherwise ) {
		try {
			if ( field.equals( this.defaultField() ) ) {
				return this.children.get( 0 );
			} else {
				return otherwise;
			}
		} catch ( IndexOutOfBoundsException _e ) {
			return otherwise;
		}
	}

	@Override
	public @Nullable Fusion getChild( @NonNull String field, int index, @Nullable Fusion otherwise ) {
		try {
			if ( field.equals( this.defaultField() ) ) {
				return this.children.get( index );
			} else {
				return otherwise;
			}
		} catch ( IndexOutOfBoundsException _e ) {
			return otherwise;
		}
	}

	@Override
	public void setChild( @NonNull String field, @NonNull Fusion value ) throws UnsupportedOperationException {
		if ( field.equals( this.defaultField() ) ) {
			try {
				this.children.set( 0, value );
			} catch ( IndexOutOfBoundsException _e ) {
				throw new IllegalArgumentException( _e );				
			}
		} else {
			throw new IllegalArgumentException();			
		}
	}

	@Override
	public void setChild( @NonNull String field, int index, @NonNull Fusion value ) throws IllegalArgumentException, UnsupportedOperationException {
		if ( field.equals( this.defaultField() ) ) {
			try {
				this.children.set( index, value );
			} catch ( IndexOutOfBoundsException _e ) {
				throw new IllegalArgumentException( _e );				
			}
		} else {
			throw new IllegalArgumentException();			
		}
	}

	@Override
	public void setAllChildren( @NonNull String field, Iterable< @NonNull Fusion > values ) throws UnsupportedOperationException {
		if ( field.equals( this.defaultField() ) ) {
			try {
				this.children.clear();
				for ( Fusion child : values ) {
					this.children.add( child );
				}
			} catch ( IndexOutOfBoundsException _e ) {
				throw new IllegalArgumentException( _e );				
			}
		} else {
			throw new IllegalArgumentException();			
		}
	}

	@Override
	public void addChild( @NonNull String field, @NonNull Fusion value ) throws UnsupportedOperationException {
		if ( field.equals( this.defaultField() ) ) {
			this.children.add( value );
		} else {
			throw new IllegalArgumentException();			
		}
	}

	@Override
	public void addChild( @NonNull Fusion value ) throws UnsupportedOperationException {
		this.children.add( value );
	}

	@Override
	public void removeChild( @NonNull String field ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		if ( field.equals( this.defaultField() ) ) {
			this.children.remove( 0 );
		} else {
			throw new IllegalArgumentException();						
		}
	}

	@Override
	public void removeChild( @NonNull String field, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		if ( field.equals( this.defaultField() ) ) {
			this.children.remove( index );
		} else {
			throw new IllegalArgumentException();						
		}
	}

	@Override
	public void clearLinks( @NonNull String field ) throws UnsupportedOperationException {
		if ( field.equals( this.defaultField() ) ) {
			this.children.clear();
		}
	}

	@Override
	public void clearAllLinks() throws UnsupportedOperationException {
		this.children.clear();
	}

	@Override
	public boolean hasNoLinks() {
		return this.children.isEmpty();
	}

	@Override
	public boolean hasAnyLinks() {
		return ! this.children.isEmpty();
	}

	@Override
	public int sizeLinks() {
		return this.children.size();
	}

	@Override
	public boolean hasSizeLinks( final int n ) {
		return this.children.size() == n;
	}

	@Override
	public boolean hasLink( @NonNull String field ) {
		if ( field.equals( this.defaultField() ) ) {
			return ! this.children.isEmpty();
		} else {
			return false;
		}
	}

	@Override
	public boolean hasLink( @NonNull String field, int index ) {
		if ( field.equals( this.defaultField() ) ) {
			return 0 <= index && index < this.children.size(); 
		} else {
			return false;
		}
	}

	@Override
	public boolean hasLink( @NonNull String field, @Nullable Fusion value ) {
		if ( field.equals( this.defaultField() ) ) {
			return this.children.contains( value ); 
		} else {
			return false;
		}
	}

	@Override
	public boolean hasLink( @NonNull String field, int index, @Nullable Fusion value ) {
		if ( field.equals( this.defaultField() ) ) {
			try {
				return this.children.get( index ).equals( value );
			} catch ( IndexOutOfBoundsException _e ) {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean hasOneChild( @NonNull String field ) {
		if ( field.equals( this.defaultField() ) ) {
			return this.children.size() == 1;
		} else {
			return false;
		}
	}

	@Override
	public int sizeFields() {
		return this.children.isEmpty() ? 0 : 1;
	}

	@Override
	public boolean hasSizeFields( int n ) {
		return this.children.isEmpty() ? n == 0 : n == 1;
	}

	@Override
	public boolean hasNoFields() {
		return this.children.isEmpty();
	}

	@Override
	public boolean hasAnyFields() {
		return ! this.children.isEmpty();
	}

	@Override
	public int sizeChildren( @NonNull String field ) {
		if ( field.equals( this.defaultField() ) ) {
			return this.children.size();
		} else {
			return 0;
		}
	}

	@Override
	public boolean hasSizeChildren( @NonNull String field, int n ) {
		if ( field.equals( this.defaultField() ) ) {
			return this.children.size() == n;
		} else {
			return 0 == n;
		}
	}

	@Override
	public boolean hasNoChildren( @NonNull String field ) {
		return this.hasSizeChildren( field, 0 );
	}

	@Override
	public boolean hasChildren( @NonNull String field ) {
		return ! this.hasSizeChildren( field, 0 );
	}

	@Override
	public @NonNull Set< @NonNull String > fieldsToSet() {
		if ( this.children.isEmpty() ) {
			return new EmptySet<>();
		} else {
			Set< @NonNull String > set = new TreeSet<>();
			set.add( this.defaultField() );
			return set;
		}
	}

	@Override
	public @NonNull List< Link< String, Fusion > > linksToList() {
		final List< Link< String, Fusion > > list = new ArrayList<>();
		int n = 0;
		for ( Fusion f : this.children ) {
			list.add( new StdLink< String, Fusion >( this.defaultField(), n++, f ) );
		}
		return list; 
	}

	@Override
	public @NonNull List< @NonNull Fusion > childrenToList( @NonNull String field ) {
		if ( this.defaultField().equals( field ) ) {
			return new ArrayList< @NonNull Fusion >( this.children );
		} else {
			return new EmptyList<>();
		}
	}

	@Override
	public Map< @NonNull String, Fusion > firstChildrenToMap() {
		final Map< @NonNull String, Fusion > map = new TreeMap<>();
		if ( ! this.children.isEmpty() ) {
			map.put( this.defaultField(), this.children.get( 0 ) );
		}
		return map;
	}

	@Override
	public StarMap< @NonNull String, @NonNull Fusion > linksToStarMap() {
		final StarMap< @NonNull String, @NonNull Fusion > smap = new TreeStarMap<>();
		for ( @NonNull Fusion child : this.children ) {
			smap.add( this.defaultField(), child );
		}
		return smap;
	}

	@Override
	public Map< Pair< @NonNull String, @NonNull Integer >, Fusion > linksToPairMap() {
		final Map< Pair< @NonNull String, @NonNull Integer >, Fusion > map = new TreeMap<>();
		int n = 0;
		for ( @NonNull Fusion child : this.children ) {
			map.put( new CmpPair<>( this.defaultField(), n++ ), child );
		}		
		return map;
	}

	@Override
	public @NonNull String getInternedName() {
		return this.constTypeArray();
	}

	@Override
	public Iterator< Link< String, Fusion > > iterator() {
		final Iterator< @NonNull Fusion > iterator = this.children.iterator();
		final String def = this.defaultField();
		
		return new Iterator< Link< String, Fusion > >() {
			
			int n = 0;

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public Link< String, Fusion > next() {
				return new StdLink<>( def, n++, iterator.next() );
			}
			
		};
	}
	

	
}
