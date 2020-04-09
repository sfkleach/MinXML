package com.steelypip.powerups.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FlexiMutatingMultiMap< Key, Value > extends AbsFlexiMutatingMultiMap< Key, Value > {

	private static final long serialVersionUID = 7434046523595764233L;

	public FlexiMutatingMultiMap( MutatingMultiMap< Key, Value > mmm ) {
		mmm.entriesToList().forEach( ( Map.Entry< Key, Value > p ) -> add( p ) );
	}

	public FlexiMutatingMultiMap() {
	}

	@Override
	public MutatingMultiMap< Key, Value > clearAllEntries() {
		return EmptyMutatingMultiMap.getInstance();
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
	public MutatingMultiMap< Key, Value > setSingletonValue( Key key, Value value ) {
		List< Value > list = this.get( key );
		if ( list == null ) {
			list = new ArrayList<>();
			this.put( key, list );
		} else {
			list.clear();
		}
		list.add( value );
		return this;
	}

	@Override
	public MutatingMultiMap< Key, Value > updateValue( Key key, int n, Value value ) throws IllegalArgumentException {
		List< Value > list = this.get( key );
		if ( list == null ) throw new IllegalArgumentException();
		try {
			list.set( n, value );
		} catch ( IndexOutOfBoundsException e ) {
			throw new IllegalArgumentException();
		}
		return this;
	}

	@Override
	public MutatingMultiMap< Key, Value > freezeByMutation() {
		return new FlexiFrozenMultiMap<>( this );
	}


}
