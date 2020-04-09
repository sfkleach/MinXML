package com.steelypip.powerups.util;

import java.util.Iterator;

public class EmptyMutatingMultiMap< K, V > extends AbsEmptyMutatingMultiMap< K, V > {
	
	public static EmptyMutatingMultiMap< Object, Object > INSTANCE = new EmptyMutatingMultiMap< Object, Object >();
	
	@SuppressWarnings("unchecked")
	public static < K, V > EmptyMutatingMultiMap< K, V > getInstance() { return (EmptyMutatingMultiMap< K, V >) INSTANCE; }

	@Override
	public EmptyMutatingMultiMap< K, V > clearAllEntries() {
		return this;
	}


	@Override
	public SingleEntryMutatingMultiMap< K, V > add( K key, V value ) {
		return new SingleEntryMutatingMultiMap< K, V >( key, value );
	}

	@Override
	public MutatingMultiMap< K, V > addAll( K key, Iterable< ? extends V > values ) {
		final Iterator< ? extends V > it = values.iterator();
		if ( ! it.hasNext() ) return this;
		V value = it.next();
		if ( it.hasNext() ) {
			return new SharedKeyMutatingMultiMap< K, V >( key ).add( value ).addAll( values );
		} else {
			return new SingleEntryMutatingMultiMap< K, V >( key, value );
		}
	}

	@Override
	public EmptyMutatingMultiMap< K, V > removeEntry( Object key, Object value ) {
		return this;
	}

	@Override
	public EmptyMutatingMultiMap< K, V > removeEntryAt( Object key, int N ) {
		return this;
	}

	@Override
	public EmptyMutatingMultiMap< K, V > removeEntries( Object key ) {
		return this;
	}

	@Override
	public MutatingMultiMap< K, V > setValues( K key, Iterable< ? extends V > values ) {
		return this.addAll( key, values );
	}

	@Override
	public MutatingMultiMap< K, V > setSingletonValue( K key, V value ) {
		return new SingleEntryMutatingMultiMap< K, V >( key, value );
	}

	@Override
	public MutatingMultiMap< K, V > updateValue( K key, int n, V value ) throws IllegalArgumentException {
		throw new IllegalArgumentException();
	}

	@SuppressWarnings("unchecked")
	@Override
	public MutatingMultiMap< K, V > freezeByMutation() {
		return EmptyFrozenMultiMap.INSTANCE;
	}

	
	
}
