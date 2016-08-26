package com.steelypip.powerups.util;

import java.util.List;
import java.util.Map;

import com.steelypip.powerups.common.EmptyList;
import com.steelypip.powerups.common.SingletonList;
import com.steelypip.powerups.common.StdPair;

public abstract class AbsSingleEntryMutatingMultiMap< K, V > implements MutatingMultiMap< K, V > {

	protected K key;
	protected V value;

	public AbsSingleEntryMutatingMultiMap() {
		super();
	}

	@Override
	public boolean hasEntry( K _key, V _value ) {
		return this.hasKey( _key ) && this.hasValue( _value );
	}

	@Override
	public boolean hasKey( K _key ) {
		return _key == null ? this.key == null : _key.equals( this.key );
	}

	@Override
	public boolean hasValue( V _value ) {
		return  _value == null ? this.value == null : _value.equals( this.value );
	}

	@Override
	public List< Map.Entry< K, V > > entriesToList() {
		return new SingletonList<>( new StdPair<>( this.key, this.value ) );
	}

	@Override
	public List< V > getAll( K _key ) {
		if ( _key == null ? this.key == null : _key.equals( this.key ) ) {
			return new SingletonList<>( this.value );
		} else {
			return new EmptyList<>();
		}
	}

	@Override
	public V getOrFail( K _key ) throws IllegalArgumentException {
		if ( _key == null ? this.key == null : _key.equals( this.key ) ) {
			return this.value;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public V getElse( K _key, V otherwise ) throws IllegalArgumentException {
		if ( _key == null ? this.key == null : _key.equals( this.key ) ) {
			return this.value;
		} else {
			return otherwise;
		}
	}

	@Override
	public V getOrFail( K _key, int N ) throws IllegalArgumentException {
		if ( N == 0 && ( _key == null ? this.key == null : _key.equals( this.key ) ) ) {
			return this.value;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public V getElse( K _key, int N, V otherwise ) throws IllegalArgumentException {
		if ( N == 0 && ( _key == null ? this.key == null : _key.equals( this.key ) ) ) {
			return this.value;
		} else {
			return otherwise;
		}
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public int sizeEntries() {
		return 1;
	}

	@Override
	public int sizeKeys() {
		return 1;
	}

	@Override
	public int sizeEntriesWithKey( K _key ) {
		return this.hasKey( _key ) ? 1 : 0;
	}

}
