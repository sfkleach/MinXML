package com.steelypip.powerups.util;

import java.util.List;
import java.util.Set;

import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.common.EmptyList;
import com.steelypip.powerups.common.EmptySet;
import com.steelypip.powerups.common.Pair;

public class EmptyStarMap< K, @Nullable V > implements StarMap< K, V > {

	@Override
	public void clear() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsEntry( K key, V value ) {
		return false;
	}

	@Override
	public boolean containsKey( Object key ) {
		return false;
	}

	@Override
	public boolean containsValue( Object value ) {
		return false;
	}

	@Override
	public List< Pair< K, V > > entriesAsList() {
		return new EmptyList< Pair< K, V > >();
	}

	@Override
	public List< V > getAll( K key ) {
		return new EmptyList< V >();
	}

	@Override
	public V get( K key ) throws IllegalArgumentException {
		return null;
	}

	@Override
	public V get( K key, int N ) throws IllegalArgumentException {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public Set< K > keySet() {
		return new EmptySet< K >();
	}

	@Override
	public void add( K key, V value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addAll( K key, Iterable< ? extends V > values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addAllPairs( Iterable< ? extends Pair< K, V > > values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addAll( StarMap< ? extends K, ? extends V > multimap ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeEntry( Object key, Object value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeEntryAt( Object key, int N ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeEntries( Object key ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValues( K key, Iterable< ? extends V > values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public List< V > values() {
		return new EmptyList< V >();
	}

	
	
}
