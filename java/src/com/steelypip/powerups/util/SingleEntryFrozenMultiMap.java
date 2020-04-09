package com.steelypip.powerups.util;

public class SingleEntryFrozenMultiMap< K, V > extends AbsSingleEntryMutatingMultiMap< K, V > {
	
	public SingleEntryFrozenMultiMap( K key, V value ) {
		super();
		this.key = key;
		this.value = value;
	}

	public SingleEntryFrozenMultiMap( AbsSingleEntryMutatingMultiMap< K, V > that ) {
		super();
		this.key = that.key;
		this.value = that.value;
	}
	@Override
	public MutatingMultiMap< K, V > clearAllEntries() {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< K, V > add( K _key, V _value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< K, V > removeEntry( K _key, V _value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< K, V > removeEntryAt( K _key, int N ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< K, V > removeEntries( K _key ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< K, V > setValues( K _key, Iterable< ? extends V > values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< K, V > setSingletonValue( K _key, V _value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutatingMultiMap< K, V > updateValue( K _key, int n, V _value ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}



	@Override
	public MutatingMultiMap< K, V > freezeByMutation() {
		return this;
	}
	
	
	
}
