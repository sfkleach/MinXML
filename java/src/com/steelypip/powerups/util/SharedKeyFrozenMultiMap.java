package com.steelypip.powerups.util;

import java.util.Map.Entry;

public class SharedKeyFrozenMultiMap< Key, Value > extends AbsSharedKeyMutatingMultiMap< Key, Value > {
	
	public SharedKeyFrozenMultiMap( Key key ) {
		super();
		this.key = key;
	}
	

	@Override
	public MutatingMultiMap< Key, Value > clearAllEntries() {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< Key, Value > add( Key _key, Value _value ) {
		throw new UnsupportedOperationException();
	}
	
	public SharedKeyFrozenMultiMap< Key, Value > add( Value value ) {
		throw new UnsupportedOperationException();
	}
	

	@Override
	public MutatingMultiMap< Key, Value > addAll( Key _key, Iterable< ? extends Value > _values ) {
		throw new UnsupportedOperationException();
	}
	
	public SharedKeyFrozenMultiMap< Key, Value > addAll( Iterable< ? extends Value > values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< Key, Value > addAllEntries( Iterable< ? extends Entry< Key, Value > > values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< Key, Value > addAll( MutatingMultiMap< ? extends Key, ? extends Value > multimap ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< Key, Value > removeEntry( Key _key, Value _value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< Key, Value > removeEntryAt( Key _key, int N ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< Key, Value > removeEntries( Key _key ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< Key, Value > setValues( Key _key, Iterable< ? extends Value > _values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< Key, Value > setSingletonValue( Key _key, Value _value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< Key, Value > updateValue( Key _key, int n, Value _value ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< Key, Value > freezeByMutation() {
		return this;
	}



}
