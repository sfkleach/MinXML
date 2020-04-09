package com.steelypip.powerups.util;

public class EmptyFrozenMultiMap< K, V > extends AbsEmptyMutatingMultiMap< K, V > {
	
	@SuppressWarnings("rawtypes")
	public static EmptyFrozenMultiMap INSTANCE = new EmptyFrozenMultiMap< Object, Object >();
	
	@SuppressWarnings("unchecked")
	public static < K, V > EmptyFrozenMultiMap< K, V > getInstance() { return INSTANCE; }

	@Override
	public EmptyFrozenMultiMap< K, V > clearAllEntries() {
		throw new UnsupportedOperationException();
	}

	@Override
	public SingleEntryMutatingMultiMap< K, V > add( K key, V value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< K, V > addAll( K key, Iterable< ? extends V > values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public EmptyFrozenMultiMap< K, V > removeEntry( Object key, Object value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public EmptyFrozenMultiMap< K, V > removeEntryAt( Object key, int N ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public EmptyFrozenMultiMap< K, V > removeEntries( Object key ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< K, V > setValues( K key, Iterable< ? extends V > values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< K, V > setSingletonValue( K key, V value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< K, V > updateValue( K key, int n, V value ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< K, V > freezeByMutation() {
		return this;
	}

	
	
}
