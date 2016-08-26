package com.steelypip.powerups.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.steelypip.powerups.common.StdPair;

public abstract class AbsFlexiMutatingMultiMap< Key, Value > extends TreeMap< Key, List< Value > > implements MutatingMultiMap< Key, Value > {

	private static final long serialVersionUID = 2918737403536388504L;

	public AbsFlexiMutatingMultiMap() {
		super();
	}

	@Override
	public List< Map.Entry< Key, Value > > entriesToList() {
		final List< Map.Entry< Key, Value > > list = new ArrayList<>();
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
		if ( list == null ) throw new IllegalArgumentException();
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
			return list == null ? otherwise : list.get( N );
		} catch ( IndexOutOfBoundsException e ) {
			return otherwise;
		}
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
	public int sizeEntriesWithKey( Key _key ) {
		List< Value > list = this.get( _key );
		return list == null ? 0 : list.size();
	}

	@Override
	public int sizeKeys() {
		return this.size();
	}

}
