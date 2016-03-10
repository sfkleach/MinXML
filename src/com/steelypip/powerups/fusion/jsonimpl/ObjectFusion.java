package com.steelypip.powerups.fusion.jsonimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.common.CmpPair;
import com.steelypip.powerups.common.EmptyList;
import com.steelypip.powerups.common.EmptyMap;
import com.steelypip.powerups.common.EmptySet;
import com.steelypip.powerups.common.Pair;
import com.steelypip.powerups.fusion.Fusion;
import com.steelypip.powerups.fusion.LiteralConstants;
import com.steelypip.powerups.hydra.Attribute;
import com.steelypip.powerups.hydra.Link;
import com.steelypip.powerups.hydra.StdLink;
import com.steelypip.powerups.util.StarMap;
import com.steelypip.powerups.util.TreeStarMap;

public class ObjectFusion implements Fusion, NullJSONFeatures, LiteralConstants {
	
	private Map< @NonNull String, @NonNull Fusion > map = new TreeMap<>();

	@Override
	public @NonNull String getInternedName() {
		return this.constTypeObject();
	}

	@Override
	public String getValue( @NonNull String key ) throws IllegalArgumentException {
		throw new IllegalArgumentException();
	}

	@Override
	public String getValue( @NonNull String key, int index ) throws IllegalArgumentException {
		throw new IllegalArgumentException();
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
		throw new UnsupportedOperationException();				
	}

	@Override
	public void removeValue( @NonNull String key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new UnsupportedOperationException();				
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
	public int sizeKeys() {
		return 0;
	}

	@Override
	public boolean hasNoKeys() {
		return true;
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
		return new TreeStarMap<>();
	}

	@Override
	public @NonNull Map< Pair< @NonNull String, @NonNull Integer >, String > attributesToPairMap() {
		return new EmptyMap<>();
	}

	@SuppressWarnings("unused")
	@Override
	public Fusion getChild() throws IllegalArgumentException {
		Fusion x = this.map.get( this.defaultField() );
		if ( x == null ) {
			throw new IllegalArgumentException();
		}
		return x;
	}

	@Override
	public Fusion getChild( int index ) throws IllegalArgumentException {
		Fusion x = this.map.get( this.defaultField() );
		if ( x == null || index != 0 ) {
			throw new IllegalArgumentException();
		} else {
			return x;
		}
	}

	@SuppressWarnings("unused")
	@Override
	public Fusion getChild( @NonNull String field ) throws IllegalArgumentException {
		Fusion x = this.map.get( field );
		if ( x == null ) {
			throw new IllegalArgumentException();
		}
		return x;
	}

	@Override
	public Fusion getChild( @NonNull String field, int index ) throws IllegalArgumentException {
		Fusion x = this.map.get( field );
		if ( x == null || index != 0 ) {
			throw new IllegalArgumentException();
		}
		return x;
	}

	@SuppressWarnings("unused")
	@Override
	public @Nullable Fusion getChild( @NonNull String field, @Nullable Fusion otherwise ) {
		Fusion x = this.map.get( field );
		if ( x == null ) {
			return otherwise;
		}
		return x;
	}

	@Override
	public @Nullable Fusion getChild( @NonNull String field, int index, @Nullable Fusion otherwise ) {
		Fusion x = this.map.get( field );
		if ( index != 0 ) {
			throw new IllegalArgumentException();
		}
		return x != null ? x : otherwise;
	}

	@Override
	public void setChild( @NonNull String field, @NonNull Fusion value ) throws UnsupportedOperationException {
		this.map.put( field, value );
	}

	@Override
	public void setChild( @NonNull String field, int index, @NonNull Fusion value ) throws IllegalArgumentException, UnsupportedOperationException {
		if ( index == 0 ) {
			this.map.put( field, value );			
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void setAllChildren( @NonNull String field, Iterable< @NonNull Fusion > values ) throws UnsupportedOperationException {
		final Iterator< @NonNull Fusion > iterator = values.iterator();
		if ( ! iterator.hasNext() ) {
			this.map.remove( field );
		} else {
			@NonNull Fusion x = iterator.next();
			if ( ! iterator.hasNext() ) {
				this.map.put( field, x );
			} else {
				throw new UnsupportedOperationException();
			}
		}
	}

	@SuppressWarnings("unused")
	@Override
	public void addChild( @NonNull String field, @NonNull Fusion value ) throws UnsupportedOperationException {
		if ( this.map.get( field ) == null ) {
			this.map.put( field, value );
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void addChild( @NonNull Fusion value ) throws UnsupportedOperationException {
		this.addChild( this.defaultField(), value );
	}

	@SuppressWarnings("unused")
	@Override
	public void removeChild( @NonNull String field ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		if ( this.map.get( field ) != null ) {
			this.map.remove( field );
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public void removeChild( @NonNull String field, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		if ( index == 0 && this.map.get( field ) != null ) {
			this.map.remove( field );
		} else {
			throw new IndexOutOfBoundsException();
		}
		
	}

	@Override
	public void clearLinks( @NonNull String field ) throws UnsupportedOperationException {
		this.map.remove( field );
	}

	@Override
	public void clearAllLinks() throws UnsupportedOperationException {
		this.map.clear();
	}

	@Override
	public boolean hasNoLinks() {
		return this.map.isEmpty();
	}

	@Override
	public int sizeLinks() {
		return this.map.size();
	}

	@Override
	public boolean hasLink( @NonNull String field ) {
		return this.map.get( field ) != null;
	}

	@Override
	public boolean hasLink( @NonNull String field, int index ) {
		return index == 0 && this.map.get( field ) != null;
	}

	@Override
	public boolean hasLink( @NonNull String field, @Nullable Fusion value ) {
		return value != null && value.equals( this.map.get( field ) );
	}

	@Override
	public boolean hasLink( @NonNull String field, int index, @Nullable Fusion value ) {
		return index == 0 && value != null && value.equals( this.map.get( field ) );
	}

	@Override
	public int sizeFields() {
		return this.map.size();
	}

	@Override
	public boolean hasNoFields() {
		return this.map.isEmpty();
	}

	@Override
	public int sizeChildren( @NonNull String field ) {
		return this.map.get( field ) == null ? 0 : 1;
	}

	@Override
	public boolean hasNoChildren( @NonNull String field ) {
		return this.map.get( field ) == null;
	}

	@SuppressWarnings("null")
	@Override
	public @NonNull Set< @NonNull String > fieldsToSet() {
		return this.map.keySet();
	}

	@Override
	public @NonNull List< Link< String, Fusion > > linksToList() {
		final List< Link< String, Fusion > > list = new ArrayList<>( this.map.size() );
		for ( Map.Entry< @NonNull String, @NonNull Fusion > entry : this.map.entrySet() ) {
			list.add( new StdLink< String, Fusion >( entry.getKey(), 0, entry.getValue() ) );
		}
		return list;
	}

	@Override
	public @NonNull List< @NonNull Fusion > childrenToList( @NonNull String field ) {
		return new ArrayList<>( this.map.values() );
	}

	@Override
	public Map< @NonNull String, Fusion > firstChildrenToMap() {
		return new TreeMap<>( this.map );
	}

	@Override
	public StarMap< @NonNull String, @NonNull Fusion > linksToStarMap() {
		return new TreeStarMap< @NonNull String, @NonNull Fusion >( this.map.entrySet() );
	}

	@Override
	public Map< Pair< @NonNull String, @NonNull Integer >, Fusion > linksToPairMap() {
		final Map< Pair< @NonNull String, @NonNull Integer >, Fusion > result = new TreeMap<>();
		for ( Map.Entry< @NonNull String, @NonNull Fusion > entry : this.map.entrySet() ) {
			result.put( new CmpPair<@NonNull String, @NonNull Integer>( entry.getKey(), 0 ), entry.getValue() );
		}
		return result;
	}

	@Override
	public Iterator< Link< String, Fusion > > iterator() {
		final Iterator< Map.Entry< @NonNull String, @NonNull Fusion > > iterator = this.map.entrySet().iterator();
		return new Iterator< Link< String, Fusion > >() {

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public Link< String, Fusion > next() {
				Map.Entry< @NonNull String, @NonNull Fusion > e = this.next();
				return new StdLink<>( e.getKey(), 0, e.getValue() );
			}
			
		};
	}

	@Override
	public boolean isObject() {
		return true;
	}
	
	

}
