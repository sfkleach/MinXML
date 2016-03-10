package com.steelypip.powerups.util;

import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.common.EmptyList;
import com.steelypip.powerups.common.Pair;
import com.steelypip.powerups.common.SingletonList;
import com.steelypip.powerups.common.StdPair;

public class SingleEntryMutatingMultiMap< K, V > implements MutatingMultiMap< K, V > {
	
	private K key;
	private V value;

	public SingleEntryMutatingMultiMap( K key, V value ) {
		super();
		this.key = key;
		this.value = value;
	}

	@Override
	public MutatingMultiMap< K, V > clearAllEntries() {
		return this.newEmpty();
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
	public List< Pair< K, V > > entriesAsList() {
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
	public MutatingMultiMap< K, V > add( K _key, V _value ) {
		if ( _key == null ? this.key == null : _key.equals(  this.key ) ) {
			return new SharedKeyMutatingMultiMap< K, V >( key ).add( this.value ).add(  _value );
		} else {
			return new SingleValueMutatingMultiMap< K, V >().add( this.key, this.value ).add( _key, _value );
		}
	}
	
	@SuppressWarnings("unchecked")
	private EmptyMutatingMultiMap<K,V> newEmpty() {
		return (EmptyMutatingMultiMap<K,V>)EmptyMutatingMultiMap.INSTANCE;
	}

	@Override
	public MutatingMultiMap< K, V > removeEntry( K _key, V _value ) {
		if ( this.hasEntry( _key, _value ) ) {
			return this.newEmpty();
		} else {
			return this;
		}
	}

	@Override
	public MutatingMultiMap< K, V > removeEntryAt( K _key, int N ) {
		if ( this.hasKey( _key ) && N == 0 ) {
			return this.newEmpty();
		} else {
			return this;
		}
	}

	@Override
	public MutatingMultiMap< K, V > removeEntries( K _key ) {
		if ( this.hasKey( _key ) ) {
			return this.newEmpty();
		} else {
			return this;
		}
	}

	@Override
	public MutatingMultiMap< K, V > setValues( K _key, Iterable< ? extends V > values ) {
		if ( this.hasKey( _key ) ) {
			return new SharedKeyMutatingMultiMap< K, V >( key ).add( this.value ).addAll( values );
		} else {
			return this.newEmpty().addAll( _key, values ).add( this.key, this.value );
		}
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
	public int sizeEntries( K _key ) {
		return this.hasKey( _key ) ? 1 : 0;
	}

}
