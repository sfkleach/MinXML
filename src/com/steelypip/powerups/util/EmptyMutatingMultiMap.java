package com.steelypip.powerups.util;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.steelypip.powerups.common.EmptyList;
import com.steelypip.powerups.common.EmptySet;
import com.steelypip.powerups.common.Pair;

public class EmptyMutatingMultiMap< K, V > implements MutatingMultiMap< K, V > {
	
	@SuppressWarnings("rawtypes")
	public static EmptyMutatingMultiMap INSTANCE = new EmptyMutatingMultiMap< Object, Object >();
	
	@SuppressWarnings("unchecked")
	public static < K, V > EmptyMutatingMultiMap< K, V > getInstance() { return INSTANCE; }

	@Override
	public EmptyMutatingMultiMap< K, V > clearAllEntries() {
		return this;
	}

	@Override
	public boolean hasEntry( K key, V value ) {
		return false;
	}
	
	@Override
	public boolean hasEntry( K key, int index, V value ) {
		return false;
	}

	@Override
	public boolean hasKey( K key ) {
		return false;
	}

	@Override
	public boolean hasValue( V value ) {
		return false;
	}

	@Override
	public List< Pair< K, V > > entriesAsList() {
		return new EmptyList<>();
	}

	@Override
	public List< V > getAll( K key ) {
		return new EmptyList<>();
	}

	@Override
	public V getOrFail( K key ) throws IllegalArgumentException {
		throw new IllegalArgumentException();
	}

	@Override
	public V getOrFail( K key, int N ) throws IllegalArgumentException {
		throw new IllegalArgumentException();
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public Set< K > keySet() {
		return new EmptySet<>();
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
	public int sizeEntries() {
		return 0;
	}

	@Override
	public int sizeEntries( K key ) {
		return 0;
	}

	@Override
	public int sizeKeys() {
		return 0;
	}

	
	
	@Override
	public List< V > valuesList() {
		return new EmptyList<>();
	}

	@Override
	public V getElse( K key, V otherwise ) throws IllegalArgumentException {
		return otherwise;
	}

	@Override
	public V getElse( K key, int N, V otherwise ) throws IllegalArgumentException {
		return otherwise;
	}
	
}
