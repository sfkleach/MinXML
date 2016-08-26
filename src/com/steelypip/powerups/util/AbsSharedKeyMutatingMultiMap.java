package com.steelypip.powerups.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.steelypip.powerups.common.StdPair;

public abstract class AbsSharedKeyMutatingMultiMap< Key, Value > implements MutatingMultiMap< Key, Value > {
	
	Key key;
	List< Value > values_list = new ArrayList<>();

	public AbsSharedKeyMutatingMultiMap() {
		super();
	}

	@Override
	public boolean hasEntry( Key _key, Value value ) {
		return (
			this.hasKey( _key ) && 
			this.hasValue( value )
		);
	}

	@Override
	public boolean hasKey( Key _key ) {
		return _key == null ? this.key == null : _key.equals(  this.key  );
	}

	@Override
	public boolean hasValue( Value value ) {
		return this.values_list.contains( value );
	}

	@Override
	public List< Map.Entry< Key, Value > > entriesToList() {
		return this.values_list.stream().map( ( Value v ) -> new StdPair< Key, Value >( this.key, v ) ).collect( Collectors.toList() );
	}

	@Override
	public List< Value > getAll( Key _key ) {
		if ( this.hasKey( _key ) ) {
			return Collections.unmodifiableList( this.values_list );
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public Value getOrFail( Key _key ) throws IllegalArgumentException {
		if ( this.hasKey( _key ) && ! this.values_list.isEmpty() ) {
			return this.values_list.get( 0 );
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Value getOrFail( Key _key, int N ) throws IllegalArgumentException {
		if ( this.hasKey( _key ) ) {
			try {
				return this.values_list.get( N );
			} catch ( IndexOutOfBoundsException _e ) {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public boolean isEmpty() {
		return this.values_list.isEmpty();
	}

	@Override
	public Set< Key > keySet() {
		return Collections.singleton( this.key );
	}

	@Override
	public int sizeEntries() {
		return this.values_list.size();
	}

	@Override
	public int sizeKeys() {
		return 1;
	}

	@Override
	public int sizeEntriesWithKey( Key _key ) {
		if ( this.hasKey( _key ) ) {
			return this.values_list.size();
		} else {
			return 0;
		}
	}

	@Override
	public List< Value > valuesList() {
		return Collections.unmodifiableList( this.values_list );
	}

	@Override
	public Value getElse( Key _key, Value otherwise ) throws IllegalArgumentException {
		if ( this.hasKey( _key ) && ! this.values_list.isEmpty() ) {
			return this.values_list.get( 0 );
		} else {
			return otherwise;
		}
	}

	@Override
	public Value getElse( Key _key, int N, Value otherwise ) throws IllegalArgumentException {
		if ( this.hasKey( _key ) && ! this.values_list.isEmpty() && 0 <= N && N < this.values_list.size() ) {
			return this.values_list.get( N );
		} else {
			return otherwise;
		}
	}

}
