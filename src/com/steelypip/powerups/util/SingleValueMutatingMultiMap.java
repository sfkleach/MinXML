package com.steelypip.powerups.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.common.EmptyList;
import com.steelypip.powerups.common.Pair;
import com.steelypip.powerups.common.SingletonList;
import com.steelypip.powerups.common.StdPair;

public class SingleValueMutatingMultiMap< Key, Value > extends TreeMap< Key, Value > implements MutatingMultiMap< Key, Value > {
	
	private static final long serialVersionUID = -2697005333497258559L;

	@SuppressWarnings("unchecked")
	@Override
	public MutatingMultiMap< Key, Value > clearAllEntries() {
		return (EmptyMutatingMultiMap< Key, Value >)EmptyMutatingMultiMap.INSTANCE;
	}

	@Override
	public List< Map.Entry< Key, Value > > entriesToList() {
		return 
			this.entrySet().
			stream().
			map( ( Map.Entry< Key, Value > e ) -> new StdPair< Key, Value >( e.getKey(), e.getValue() ) ).
			collect( Collectors.toList() );
	}

	@Override
	public List< Value > getAll( Key key ) {
		Value v = this.get( key );
		if ( v == null && ! this.containsKey( key ) ) {
			return new EmptyList<>();
		} else {
			return new SingletonList<>( v );
		}
	}

	@Override
	public Value getOrFail( Key key ) throws IllegalArgumentException {
		Value v = this.get( key );
		if ( v == null && ! this.containsKey( key ) ) {
			throw new IllegalArgumentException();
		} else {
			return v;
		}
	}

	@Override
	public Value getElse( Key key, Value otherwise ) throws IllegalArgumentException {
		return this.getOrDefault( key, otherwise );
	}

	@Override
	public Value getOrFail( Key key, int N ) throws IllegalArgumentException {
		Value v = this.get( key );
		if ( N != 0 || ( v == null && ! this.containsKey( key ) ) ) {
			throw new IllegalArgumentException();
		} else {
			return v;
		}
	}

	@Override
	public Value getElse( Key key, int N, Value otherwise ) throws IllegalArgumentException {
		if ( N == 0 ) {
			return this.getOrDefault( key, otherwise );
		} else {
			throw new IllegalArgumentException();			
		}
	}

	@Override
	public MutatingMultiMap< Key, Value > add( Key key, Value value ) {
		if ( this.containsKey( key ) ) {
			return new FlexiMutatingMultiMap< Key, Value >( this ).add( key, value );
		} else {
			this.put( key, value );
			return this;
		}
	}

	@Override
	public MutatingMultiMap< Key, Value > removeEntry( Key key, Value value ) {
		if ( this.getOrDefault( key, value ).equals( value ) ) {
			this.remove( key );
		}
		return this;
	}

	@Override
	public MutatingMultiMap< Key, Value > removeEntryAt( Key key, int N ) {
		if ( N == 0 ) {
			return this.removeEntries( key );
		} else {
			return this;
		}
	}

	@Override
	public MutatingMultiMap< Key, Value > removeEntries( Key key ) {
		this.remove( key );
		return this;
	}

	@Override
	public MutatingMultiMap< Key, Value > setValues( Key key, Iterable< ? extends Value > values ) {
		Iterator< ? extends Value > it = values.iterator();
		if ( ! it.hasNext() ) return this;
		Value v = it.next();
		if ( it.hasNext() ) {
			return new FlexiMutatingMultiMap< Key, Value >( this ).setValues( key, values );
		} else {
			this.put( key, v );
			return this;
		}
	}

	@Override
	public int sizeEntries() {
		return this.size();
	}
	
	@Override
	public int sizeKeys() {
		return this.size();
	}
	
	@Override
	public int sizeEntriesWithKey( Key _key ) {
		return this.hasKey( _key ) ? 1 : 0;
 	}

	@Override
	public MutatingMultiMap< Key, Value > setSingletonValue( Key key, Value value ) {
		this.put( key, value );
		return this;
	}

	@Override
	public MutatingMultiMap< Key, Value > updateValue( Key key, int n, Value value ) throws IllegalArgumentException {
		if ( n != 0 ) throw new IllegalArgumentException();
		if ( this.hasKey( key ) ) {
			this.put( key, value );
			return this;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	

}
