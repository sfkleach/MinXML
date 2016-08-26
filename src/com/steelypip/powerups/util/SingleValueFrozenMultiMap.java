package com.steelypip.powerups.util;

public class SingleValueFrozenMultiMap< Key, Value > extends AbsSingleValueMutatingMultiMap< Key, Value > implements MutatingMultiMap< Key, Value > {
	
	private static final long serialVersionUID = -2697005333497258559L;
	
	public SingleValueFrozenMultiMap( AbsSingleValueMutatingMultiMap< Key, Value > that ) {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public MutatingMultiMap< Key, Value > clearAllEntries() {
		return (EmptyMutatingMultiMap< Key, Value >)EmptyMutatingMultiMap.INSTANCE;
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
