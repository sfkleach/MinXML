package com.steelypip.powerups.util;

import java.util.Map.Entry;

public class SharedKeyMutatingMultiMap< Key, Value > extends AbsSharedKeyMutatingMultiMap< Key, Value > {
	
	public SharedKeyMutatingMultiMap( Key key ) {
		super();
		this.key = key;
	}
	
	@Override
	public MutatingMultiMap< Key, Value > clearAllEntries() {
		return EmptyMutatingMultiMap.getInstance();
	}

	@Override
	public MutatingMultiMap< Key, Value > add( Key _key, Value _value ) {
		if ( this.hasKey( _key ) ) {
			this.values_list.add( _value );
			return this;
		} else {
			return new FlexiMutatingMultiMap< Key, Value >().addAll( this.key, this.values_list ).add( _key, _value );
		}
	}
	
	public SharedKeyMutatingMultiMap< Key, Value > add( Value value ) {
		this.values_list.add( value );
		return this;
	}
	

	@Override
	public MutatingMultiMap< Key, Value > addAll( Key _key, Iterable< ? extends Value > _values ) {
		return new FlexiMutatingMultiMap< Key, Value >().addAll( this.key, this.values_list ).addAll( _key, _values );
	}
	
	public SharedKeyMutatingMultiMap< Key, Value > addAll( Iterable< ? extends Value > values ) {
		for ( Value v : values ) {
			this.values_list.add( v );
		}
		return this;
	}

	@Override
	public MutatingMultiMap< Key, Value > addAllEntries( Iterable< ? extends Entry< Key, Value > > values ) {
		return new FlexiMutatingMultiMap< Key, Value >().addAll( this.key, this.values_list ).addAllEntries( values );
	}

	@Override
	public MutatingMultiMap< Key, Value > addAll( MutatingMultiMap< ? extends Key, ? extends Value > multimap ) {
		return new FlexiMutatingMultiMap< Key, Value >().addAll( this.key, this.values_list ).addAll( multimap );
	}

	@Override
	public MutatingMultiMap< Key, Value > removeEntry( Key _key, Value _value ) {
		if ( this.hasKey( _key ) ) {
			this.values_list.remove( _value );
		}
		return this;
	}

	@Override
	public MutatingMultiMap< Key, Value > removeEntryAt( Key _key, int N ) {
		if ( this.hasKey( _key ) ) {
			this.values_list.remove( N );
		}
		return this;
	}

	@Override
	public MutatingMultiMap< Key, Value > removeEntries( Key _key ) {
		if ( this.hasKey( _key ) ) {
			return this.clearAllEntries();
		} else {
			return this;
		}
	}

	@Override
	public MutatingMultiMap< Key, Value > setValues( Key _key, Iterable< ? extends Value > _values ) {
		if ( this.hasKey( _key ) ) {
			this.values_list.clear();
			for ( Value v : _values ) {
				this.values_list.add( v );
			}
			return this;
		} else {
			return new FlexiMutatingMultiMap< Key, Value >().addAll( this.key, this.values_list ).setValues( _key, _values );
		}
	}

	@Override
	public MutatingMultiMap< Key, Value > setSingletonValue( Key _key, Value _value ) {
		if ( this.hasKey( _key ) ) {
			this.values_list.clear();
			this.values_list.add( _value );
			return this;
		} else {
			return new FlexiMutatingMultiMap<>( this ).add( _key, _value );
		}
	}

	@Override
	public MutatingMultiMap< Key, Value > updateValue( Key _key, int n, Value _value ) throws IllegalArgumentException {
		if ( this.hasKey( _key ) ) {
			try {
				this.values_list.set( n, _value );
				return this;
			} catch ( IndexOutOfBoundsException _e ) {
				throw new IllegalArgumentException( _e );
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public MutatingMultiMap< Key, Value > freezeByMutation() {
		MutatingMultiMap < Key, Value > f = new SharedKeyFrozenMultiMap< Key, Value >( this.key );
		for ( Value v : this.values_list ) {
			f = f.add( key, v );
		}
		return f;
	}
	
	
}
