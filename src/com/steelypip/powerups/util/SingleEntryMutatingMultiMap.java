package com.steelypip.powerups.util;

import java.util.List;
import java.util.Map;

import com.steelypip.powerups.common.EmptyList;
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
		return EmptyMutatingMultiMap.getInstance();
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
	public MutatingMultiMap< K, V > add( K _key, V _value ) {
		if ( _key == null ? this.key == null : _key.equals(  this.key ) ) {
			return new SharedKeyMutatingMultiMap< K, V >( key ).add( this.value ).add(  _value );
		} else {
			return new SingleValueMutatingMultiMap< K, V >().add( this.key, this.value ).add( _key, _value );
		}
	}

	@Override
	public MutatingMultiMap< K, V > removeEntry( K _key, V _value ) {
		if ( this.hasEntry( _key, _value ) ) {
			return this.clearAllEntries();
		} else {
			return this;
		}
	}

	@Override
	public MutatingMultiMap< K, V > removeEntryAt( K _key, int N ) {
		if ( this.hasKey( _key ) && N == 0 ) {
			return this.clearAllEntries();
		} else {
			return this;
		}
	}

	@Override
	public MutatingMultiMap< K, V > removeEntries( K _key ) {
		if ( this.hasKey( _key ) ) {
			return this.clearAllEntries();
		} else {
			return this;
		}
	}

	@Override
	public MutatingMultiMap< K, V > setValues( K _key, Iterable< ? extends V > values ) {
		if ( values.iterator().hasNext() ) {
			return new SharedKeyMutatingMultiMap< K, V >( _key ).addAll( values ).add( this.key, this.value );
		} else {
			return this;
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
	public int sizeEntriesWithKey( K _key ) {
		return this.hasKey( _key ) ? 1 : 0;
	}

	@Override
	public MutatingMultiMap< K, V > setSingletonValue( K _key, V _value ) {
		if ( this.hasKey( _key ) ) {
			this.value = _value;
			return this;
		} else {
			return new SingleValueMutatingMultiMap<  K, V  >().setSingletonValue( _key, _value ).setSingletonValue( this.key, this.value );
		}
	}

	@Override
	public MutatingMultiMap< K, V > updateValue( K _key, int n, V _value ) throws IllegalArgumentException {
		if ( n != 0 ) throw new IllegalArgumentException();
		if ( this.hasKey( _key ) ) {
			this.value = _value;
			return this;
		} else {
			throw new IllegalArgumentException();
		}
	}
	

}
