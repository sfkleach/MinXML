package com.steelypip.powerups.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.steelypip.powerups.common.Pair;

/**
 * A StarMap is a collection of key-to-value entries. Entries are considered
 * to be ordered, with the entries being ordered by key.
 * @author steve
 *
 * @param <K>
 * @param <V>
 */
public interface StarMap< K, V > {


	/** 
	 * Removes all key-value pairs from the multimap, leaving it empty. If the
	 * StarMap is immutable, it throws an UnsupportedOperationException.
	 */
	void clear() throws UnsupportedOperationException;
	
	/**
	 * Returns true if this multimap contains at least one key-value pair with the 
	 * key key and the value value.
	 * @param key
	 * @param value
	 * @return
	 */
	boolean	containsEntry( K key, V value );
	
	/** 
	 * Returns true if this multimap contains at least one key-value pair with the key key.
	 */
	boolean	containsKey(Object key);

	/* Returns true if this multimap contains at least one key-value pair with the value value.*/
	boolean	containsValue(Object value);
	
	/** 
	 * Returns list of all key-value pairs contained in this 
	 * multimap/ 
	 */
	List< Pair< K, V > > entriesAsList();
	
	/**
	 *	Compares the specified object with this multimap for equality.  
	 */
	boolean	equals( Object obj );
	
	/** Returns a list of the values associated with key in 
	 * this multimap, if any, that is guaranteed to be a new list.
	 * @param key
	 * @return list of the values associated with key
	 */
	List<V>	getAll(K key);
	
	/** Returns the first value associated with key in 
	 * this multimap
	 * @param key
	 * @return first value associated with key
	 */
	V get(K key) throws IllegalArgumentException;
	
	/** Returns the Nth values associated with key in 
	 * this multimap, if any, that is guaranteed to be a new list.
	 * @param key
	 * @param N the position of the value in the list of values associated with key
	 * @return the Nth value associated with key
	 */
	V get(K key, int N) throws IllegalArgumentException;
	
	/**
	 * Returns the hash code for this StarMap.
	 */
	int	hashCode();
	
	/**
	 * Returns true if this multimap contains no key-value pairs. 
	 */
	boolean	isEmpty();
	
	/**
	 * Returns a view collection containing the key from each key-value pair in this 
	 * multimap, without collapsing duplicates.
	 * @return
	 */
	//Multiset<K>	keys();
	
	
	/**
	 * Returns a view collection of all distinct keys contained in this multimap.
	 */
	Set<K>	keySet();
	
	/**
	 * Stores a key-value pair in this multimap.
	 */
	void add( K key, V value );
	
	/**
	 * Stores a key-value pair in this multimap for each of values, all using the same key, key. 
	 * @param key
	 * @param values
	 * @return
	 */
	void addAll( K key, Iterable<? extends V> values );
	
	/**
	 * Stores a key-value pair in this multimap for each of values, in the order supplied. 
	 * @param values
	 * @return
	 */
	void addAllPairs( Iterable< ? extends Pair< K, V > > values );

	
	/**
	 * Stores all key-value pairs of multimap in this multimap, in the order 
	 * returned by multimap.entries().
	 */
	void addAll(StarMap<? extends K,? extends V> multimap);
	
	/** 
	 * Removes a single key-value pair with the key key and the value value 
	 * from this multimap, if such exists.
	 */
	void removeEntry( Object key, Object value );
	
	/** 
	 * Removes a single key-value pair with the key key and the Nth value 
	 * from this multimap, if such exists.
	 */
	void removeEntryAt( Object key, int N );
	
		
	/**
	 * Removes all values associated with the key key.
	 * @param key
	 * @return
	 */
	void removeEntries( Object key );
	
	/**
	 * Stores a collection of values with the same key, replacing any existing 
	 * values for that key.
	 * @param key
	 * @param values
	 * @return
	 */
	void setValues( K key, Iterable<? extends V> values );
	
	/**
	 * Returns the number of key-value pairs in this multimap.
	 */
	int	size();
	
	/**
	 * Returns a view collection containing the value from each key-value pair 
	 * contained in this multimap, without collapsing duplicates (so values().size() == size()). 
	 */
	List<V>	values();
	
}
