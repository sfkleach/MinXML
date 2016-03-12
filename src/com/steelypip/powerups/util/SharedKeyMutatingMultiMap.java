package com.steelypip.powerups.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.steelypip.powerups.common.StdPair;

public class SharedKeyMutatingMultiMap< Key, Value > implements MutatingMultiMap< Key, Value > {
	
	private Key key;
	private List< Value > values_list = new ArrayList<>();
	
	public SharedKeyMutatingMultiMap( Key key ) {
		super();
		this.key = key;
	}
	
	@Override
	public MutatingMultiMap< Key, Value > clearAllEntries() {
		return EmptyMutatingMultiMap.getInstance();
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
		if ( N == 0 && this.hasKey( _key ) && ! this.values_list.isEmpty() ) {
			return this.values_list.get( 0 );
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
	public MutatingMultiMap< Key, Value > add( Key _key, Value _value ) {
		if ( this.hasKey( _key ) ) {
			this.values_list.add( _value );
			return this;
		} else {
			return new FlexiMutatingMultiMap< Key, Value >().addAll( this.key, this.values_list ).add( _key, _value );
		}
	}
	
	public SharedKeyMutatingMultiMap< Key, Value > add( Value value ) {
		this.values_list.add( value );
		return this;
	}
	

	@Override
	public MutatingMultiMap< Key, Value > addAll( Key _key, Iterable< ? extends Value > _values ) {
		return new FlexiMutatingMultiMap< Key, Value >().addAll( this.key, this.values_list ).addAll( _key, _values );
	}
	
	public SharedKeyMutatingMultiMap< Key, Value > addAll( Iterable< ? extends Value > values ) {
		for ( Value v : values ) {
			this.values_list.add( v );
		}
		return this;
	}

	@Override
	public MutatingMultiMap< Key, Value > addAllEntries( Iterable< ? extends Entry< Key, Value > > values ) {
		return new FlexiMutatingMultiMap< Key, Value >().addAll( this.key, this.values_list ).addAllEntries( values );
	}

	@Override
	public MutatingMultiMap< Key, Value > addAll( MutatingMultiMap< ? extends Key, ? extends Value > multimap ) {
		return new FlexiMutatingMultiMap< Key, Value >().addAll( this.key, this.values_list ).addAll( multimap );
	}

	@Override
	public MutatingMultiMap< Key, Value > removeEntry( Key _key, Value _value ) {
		if ( this.hasKey( _key ) ) {
			this.values_list.remove( _value );
		}
		return this;
	}

	@Override
	public MutatingMultiMap< Key, Value > removeEntryAt( Key _key, int N ) {
		if ( this.hasKey( _key ) ) {
			this.values_list.remove( N );
		}
		return this;
	}

	@Override
	public MutatingMultiMap< Key, Value > removeEntries( Key _key ) {
		if ( this.hasKey( _key ) ) {
			return this.clearAllEntries();
		} else {
			return this;
		}
	}

	@Override
	public MutatingMultiMap< Key, Value > setValues( Key _key, Iterable< ? extends Value > _values ) {
		if ( this.hasKey( _key ) ) {
			this.values_list.clear();
			for ( Value v : _values ) {
				this.values_list.add( v );
			}
			return this;
		} else {
			return new FlexiMutatingMultiMap< Key, Value >().addAll( this.key, this.values_list ).setValues( _key, _values );
		}
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

	@Override
	public MutatingMultiMap< Key, Value > setSingletonValue( Key _key, Value _value ) {
		if ( this.hasKey( _key ) ) {
			this.values_list.clear();
			this.values_list.add( _value );
			return this;
		} else {
			return new FlexiMutatingMultiMap<>( this ).add( _key, _value );
		}
	}

	@Override
	public MutatingMultiMap< Key, Value > updateValue( Key _key, int n, Value _value ) throws IllegalArgumentException {
		if ( this.hasKey( _key ) ) {
			try {
				this.values_list.set( n, _value );
				return this;
			} catch ( IndexOutOfBoundsException _e ) {
				throw new IllegalArgumentException( _e );
			}
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	

}
