package com.steelypip.powerups.util;

public class SingleEntryMutatingMultiMap< K, V > extends AbsSingleEntryMutatingMultiMap< K, V > {
	
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


	@Override
	public MutatingMultiMap< K, V > freezeByMutation() {
		return new SingleEntryFrozenMultiMap< K, V >( this );
	}
	
}
