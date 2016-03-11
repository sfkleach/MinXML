package com.steelypip.powerups.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A SmartMap is a mutating collection of key-to-value entries. Entries are considered
 * to be ordered, with the entries being ordered by key. A mutating object implements all
 * update operations by returning an object which might or might not be new - but you are
 * obliged to continue with the returned value. The mutation pattern is designed for 
 * unshared (component) values, such as lists, with evolving implementation needs.
 * @author steve
 *
 * @param <K>
 * @param <V>
 */
public interface MutatingMultiMap< K, V > extends Iterable< Map.Entry< K, V > >{

	/** 
	 * Removes all key-value pairs from the multimap, leaving it empty. If the
	 * SmartMap is immutable, it throws an UnsupportedOperationException.
	 */
	MutatingMultiMap< K, V > clearAllEntries();
	
	/**
	 * Returns true if this multimap contains at least one key-value pair with the 
	 * key key and the value value.
	 * @param key
	 * @param value
	 * @return
	 */
	default boolean	hasEntry( K key, V value ) {
		return this.getAll( key ).contains( value );
	}
	
	default boolean	hasEntry( K key, int index, V value ) {
		try {
			V v = this.getAll( key ).get( index );
			return v == null ? value == null : v.equals( value );
		} catch ( IndexOutOfBoundsException _e ) {
			return false;
		}
	}
	
	/** 
	 * Returns true if this multimap contains at least one key-value pair with the key key.
	 */
	default boolean	hasKey( K key ) {
		try {
			this.getOrFail( key );
			return true;
		} catch ( IllegalArgumentException _e ) {
			return false;
		}
	}

	/* Returns true if this multimap contains at least one key-value pair with the value value.*/
	default boolean	hasValue( V value ) {
		for ( Map.Entry< K, V > p : this.entriesToList() ) {
			V v = p.getValue();
			if ( v == null ? value == null : v.equals( value ) ) return true;
		}
		return false;
	}
	
	/** 
	 * Returns list of all key-value pairs contained in this 
	 * multimap/ 
	 */
	List< Map.Entry< K, V > > entriesToList();
	
	default Iterator< Map.Entry< K, V > > iterator() {
		return this.entriesToList().iterator();
	}
	
	/**
	 *	Compares the specified object with this multimap for equality.  
	 */
	boolean	equals( Object obj );
	
	/** Returns a list of the values associated with key in 
	 * this multimap, if any, that is guaranteed to be a new list.
	 * @param key
	 * @return list of the values associated with key
	 */
	List< V > getAll( K key );

	
	/** Returns the first value associated with key in 
	 * this multimap
	 * @param key
	 * @return first value associated with key
	 */
	V getOrFail( K key ) throws IllegalArgumentException;
	
	/** Returns the first value associated with key in 
	 * this multimap
	 * @param key
	 * @return first value associated with key
	 */
	V getElse( K key, V otherwise ) throws IllegalArgumentException;
	
	/** Returns the Nth values associated with key in 
	 * this multimap, if any, that is guaranteed to be a new list.
	 * @param key
	 * @param N the position of the value in the list of values associated with key
	 * @return the Nth value associated with key
	 */
	V getOrFail( K key, int N ) throws IllegalArgumentException;
	
	/** Returns the Nth values associated with key in 
	 * this multimap, if any, that is guaranteed to be a new list.
	 * @param key
	 * @param N the position of the value in the list of values associated with key
	 * @return the Nth value associated with key
	 */
	V getElse( K key, int N, V otherwise ) throws IllegalArgumentException;
	
	/**
	 * Returns true if this multimap contains no key-value pairs. 
	 */
	default boolean	isEmpty() {
		return this.sizeEntries() == 0;
	}
	
	
	/**
	 * Returns the set of all distinct keys contained in this multimap.
	 */
	default Set< K > keySet() {
		return this.entriesToList().stream().map( ( Map.Entry< K, V > p ) -> p.getKey() ).collect( Collectors.toSet() );
	}
		
	/**
	 * Stores a key-value pair in this multimap.
	 */
	MutatingMultiMap< K, V > add( K key, V value );
	
	/**
	 * Stores a key-value pair in this multimap.
	 */
	default MutatingMultiMap< K, V > add( Map.Entry<  K, V > e  ) {
		return this.add( e.getKey(), e.getValue() );
	}
	
	/**
	 * Stores a key-value pair in this multimap for each of values, all using the same key, key. 
	 * @param key
	 * @param values
	 * @return
	 */
	default MutatingMultiMap< K, V > addAll( K key, Iterable< ? extends V > values ) {
		MutatingMultiMap< K, V > self = this;
		for ( V v : values ) {
			self = self.add( key, v );
		}
		return self;		
	}
	
	/**
	 * Stores a key-value pair in this multimap for each of values, in the order supplied. 
	 * @param values
	 * @return
	 */
	default MutatingMultiMap< K, V > addAllEntries( Iterable< ? extends Map.Entry< K, V > > values ) {
		MutatingMultiMap< K, V > self = this;
		for ( Map.Entry<  K, V > e : values ) {
			self = self.add( e );
		}
		return self;
	}

	
	/**
	 * Stores all key-value pairs of multimap in this multimap, in the order 
	 * returned by multimap.entries().
	 */
	default MutatingMultiMap< K, V > addAll( MutatingMultiMap< ? extends K, ? extends V > multimap ) {
		MutatingMultiMap< K, V > self = this;
		for ( Map.Entry< K, V > p : this.entriesToList() ) {
			self = self.add( p );
		}
		return self;
	}
	
	/** 
	 * Removes a single key-value pair with the key key and the value value 
	 * from this multimap, if such exists.
	 */
	MutatingMultiMap< K, V > removeEntry( K key, V value );
	
	/** 
	 * Removes a single key-value pair with the key key and the Nth value 
	 * from this multimap, if such exists.
	 */
	MutatingMultiMap< K, V > removeEntryAt( K key, int N );
	
		
	/**
	 * Removes all values associated with the key key.
	 * @param key
	 * @return
	 */
	MutatingMultiMap< K, V > removeEntries( K key );
	
	/**
	 * Stores a collection of values with the same key, replacing any existing 
	 * values for that key.
	 * @param key
	 * @param values
	 * @return
	 */
	MutatingMultiMap< K, V > setValues( K key, Iterable<? extends V> values );
	
	MutatingMultiMap< K, V > setSingletonValue( K key, V value );
	
	MutatingMultiMap< K, V > updateValue( K key, int n, V value );


	
	/**
	 * Returns the number of key-value pairs in this multimap.
	 */
	int	sizeEntries();
	
	/**
	 * Returns the number of key-value pairs in this multimap that share key key.
	 */
	int	sizeEntriesWithKey( K key );
	
	default int sizeKeys() {
		return this.keySet().size();
	}
	
	/**
	 * Returns a list containing the value from each key-value pair 
	 * contained in this multimap, without collapsing duplicates (so values().size() == size()). 
	 */
	default List< V > valuesList() {
		return this.entriesToList().stream().map( ( Map.Entry< K, V > p ) -> p.getValue() ).collect( Collectors.toList() );
	}
	
	default MutatingMultiMap< K, V > trimToSize() {
		return this;
	}
	
}
