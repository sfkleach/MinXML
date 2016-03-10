package com.steelypip.powerups.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.common.Pair;
import com.steelypip.powerups.common.StdPair;

public class FlexiMutatingMultiMap< Key, Value > extends TreeMap< Key, List< Value > > implements MutatingMultiMap< Key, Value > {

	private static final long serialVersionUID = 7434046523595764233L;

	public FlexiMutatingMultiMap( MutatingMultiMap< Key, Value > mmm ) {
		mmm.entriesAsList().forEach( ( Pair< Key, Value > p ) -> add( p ) );
	}

	public FlexiMutatingMultiMap() {
	}

	@Override
	public MutatingMultiMap< Key, Value > clearAllEntries() {
		return EmptyMutatingMultiMap.getInstance();
	}

	@Override
	public List< Pair< Key, Value > > entriesAsList() {
		final List< Pair< Key, Value > > list = new ArrayList<>();
		for ( Map.Entry< Key, List< Value > > e : this.entrySet() ) {
			for ( Value v : e.getValue() ) {
				list.add( new StdPair<>( e.getKey(), v ) );
			}
		}
		return list;
	}

	@Override
	public List< Value > getAll( Key key ) {
		final List< Value > list = this.get( key );
		if ( list == null ) {
			return Collections.emptyList();
		} else {
			return Collections.unmodifiableList( list );			
		}
	}

	@Override
	public Value getOrFail( Key key ) throws IllegalArgumentException {
		final List< Value > list = this.get( key );
		if ( list == null ) throw new IllegalArgumentException();
		return list.get( 0 );
	}

	@Override
	public Value getElse( Key key, Value otherwise ) throws IllegalArgumentException {
		final List< Value > list = this.get( key );
		if ( list == null ) return otherwise;
		return list.get( 0 );
	}

	@Override
	public Value getOrFail( Key key, int N ) throws IllegalArgumentException {
		final List< Value > list = this.get( key );
		try {
			return list.get( N );
		} catch ( IndexOutOfBoundsException e ) {
			throw new IllegalArgumentException( e );
		}
	}

	@Override
	public Value getElse( Key key, int N, Value otherwise ) throws IllegalArgumentException {
		final List< Value > list = this.get( key );
		try {
			return list.get( N );
		} catch ( IndexOutOfBoundsException e ) {
			return otherwise;
		}
	}

	@Override
	public MutatingMultiMap< Key, Value > add( Key key, Value value ) {
		List< Value > list = this.get( key );
		if ( list == null ) {
			list = new ArrayList<>( 1 );
			this.put( key, list );
		}
		list.add( value );
		return this;
	}

	@Override
	public MutatingMultiMap< Key, Value > removeEntry( Key key, Value value ) {
		final List< Value > list = this.get( key );
		if ( list != null ) {
			list.remove( value );
		}
		return this;
	}

	@Override
	public MutatingMultiMap< Key, Value > removeEntryAt( Key key, int N ) {
		final List< Value > list = this.get( key );
		if ( list != null ) {
			list.remove( N );
		}
		return this;
	}

	@Override
	public MutatingMultiMap< Key, Value > removeEntries( Key key ) {
		this.remove( key );
		return this;
	}

	@Override
	public MutatingMultiMap< Key, Value > setValues( Key key, Iterable< ? extends Value > values ) {
		List< Value > list = this.get( key );
		if ( list == null ) {
			list = new ArrayList<>();
			this.put( key, list );
		} else {
			list.clear();
		}
		for ( Value v : values ) {
			list.add( v );
		}
		return this;
	}

	@Override
	public int sizeEntries() {
		int n = 0;
		for ( Map.Entry< Key, List< Value > > e : this.entrySet() ) {
			n += e.getValue().size();
		}
		return n;
	}

	@Override
	public int sizeEntries( Key _key ) {
		List< Value > list = this.get( _key );
		return list == null ? 0 : list.size();
	}

	@Override
	public int sizeKeys() {
		return this.size();
	}

}
