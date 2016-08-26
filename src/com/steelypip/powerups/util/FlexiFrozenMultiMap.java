package com.steelypip.powerups.util;

import java.util.Map;

public class FlexiFrozenMultiMap< Key, Value > extends AbsFlexiMutatingMultiMap< Key, Value > {

	private static final long serialVersionUID = 7434046523595764233L;

	public FlexiFrozenMultiMap( MutatingMultiMap< Key, Value > mmm ) {
		mmm.entriesToList().forEach( ( Map.Entry< Key, Value > p ) -> add( p ) );
	}

	public FlexiFrozenMultiMap() {
	}

	@Override
	public MutatingMultiMap< Key, Value > clearAllEntries() {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< Key, Value > add( Key key, Value value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< Key, Value > removeEntry( Key key, Value value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< Key, Value > removeEntryAt( Key key, int N ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< Key, Value > removeEntries( Key key ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< Key, Value > setValues( Key key, Iterable< ? extends Value > values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< Key, Value > setSingletonValue( Key key, Value value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< Key, Value > updateValue( Key key, int n, Value value ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< Key, Value > freezeByMutation() {
		return this;
	}	

}
