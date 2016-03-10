package com.steelypip.powerups.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.common.Pair;
import com.steelypip.powerups.common.StdPair;

public class TreeStarMap< K, V > implements StarMap< K, V > {

	private TreeMap< K, List< V > > map;
	
	public TreeStarMap() {
		this.map = new TreeMap<>();
	}
	
	public TreeStarMap( Iterable< ? extends Map.Entry< K, V > > initial ) {
		this();
		this.addAllEntries( initial );
	}


	public TreeStarMap( @NonNull Map< K, ? extends List< ? extends V > > initial ) {
		this();
		for ( Map.Entry< K, ? extends List< ? extends V > > e : initial.entrySet() ) {
			for ( V v : e.getValue() ) {
				this.add( e.getKey(), v );
			}
		}
	}


	/** 
	 * Removes all key-value pairs from the multimap, leaving it empty. If the
	 * StarMap is immutable, it throws an UnsupportedOperationException.
	 */
	public void clear() throws UnsupportedOperationException {
		this.map.clear();
	}
	
	/**
	 * Returns true if this multimap contains at least one key-value pair with the 
	 * key key and the value value.
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean containsEntry( Object key, Object value ) {
		final List< V > values = this.map.get( key );
		return values != null && values.contains( value );
	}
	
	/** 
	 * Returns true if this multimap contains at least one key-value pair with the key key.
	 */
	public boolean containsKey(Object key) {
		final List< V > values = this.map.get( key );
		return values != null && ! values.isEmpty();
	}

	/* Returns true if this multimap contains at least one key-value pair with the value value.*/
	public boolean containsValue( Object value ) {
		for ( List< V > values : this.map.values() ) {
			if ( values.contains( value ) ) return true;
		}
		return false;
	}
	
	/** 
	 * Returns list of all key-value pairs contained in this 
	 * multimap/ 
	 */
	public List< Pair< K, V > > entriesAsList() {
		final ArrayList< Pair< K, V > > list = new ArrayList<>();
		for ( Map.Entry< K, List< V > > values : this.map.entrySet() ) {
			for ( V v : values.getValue() ) {
				list.add( new StdPair<>( values.getKey(), v ) );
			}
		}
		return list;
	}
	
	/**
	 *	Compares the specified object with this multimap for equality.  
	 */
	public boolean equals( Object obj ) {
		if ( obj instanceof StarMap ) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final List< Pair< Object, Object > > list1 = ((StarMap)obj).entriesAsList();
			final List< Pair< K, V > > list2 = this.entriesAsList();
			return list1.equals( list2 );
		}  else {
			return false;
		}
	}
	
	/** Returns a collection of the values associated with key in 
	 * this multimap, if any.
	 * @param key
	 * @return
	 */
	public List<V> getAll( K key ) {
		final ArrayList< V > result = new ArrayList<>();
		final List< V > list = this.map.get( key );
		if ( list != null ) {
			result.addAll( list );
		}
		return result;
	}
	
	public V get( K key ) throws IllegalArgumentException {
		final List< V > list = this.map.get( key );
		if ( list != null && ! list.isEmpty() ) {
			return list.get( 0 );
		} else {
			throw new IllegalArgumentException( String.format( "No values associated with this key (%s)", key ) );
		}
	}
	
	/** Returns the Nth values associated with key in 
	 * this multimap, if any, that is guaranteed to be a new list.
	 * @param key
	 * @param N the position of the value in the list of values associated with key
	 * @return the Nth value associated with key
	 */
	public V get( K key, int N ) throws IllegalArgumentException {
		final List< V > list = this.map.get( key );
		if ( list != null && 0 <= N && N < list.size() ) {
			return list.get( N );
		} else {
			throw new IllegalArgumentException( String.format( "No value at position (%d) associated with this key (%s)", N, key ) );
		}
		
	}
	
	/**
	 * Returns true if this multimap contains no key-value pairs. 
	 */
	public boolean isEmpty() {
		if ( this.map.isEmpty() ) return true;
		for ( List< V > list : this.map.values() ) {
			if ( ! list.isEmpty() ) return false;
		}
		return true;
	}
	
	/**
	 * Returns a view collection of all distinct keys contained in this multimap.
	 */
	public Set<K> keySet() {
		return this.map.keySet();
	}
	
	/**
	 * Adds a key-value pair in this multimap.
	 */
	public void add( K key, V value ) {
		List< V > list = this.map.get( key );
		if ( list == null ) {
			list = new ArrayList<>();
			this.map.put( key, list );
		} 
		list.add( value );
	}
	
	/**
	 * Stores a key-value pair in this multimap for each of values, all using the same key, key. 
	 * @param key
	 * @param values
	 * @return
	 */
	public void addAll( K key, Iterable<? extends V> values ) {
		List< V > list = this.map.get( key );
		final boolean list_was_null = list == null;
		if ( list_was_null ) {
			list = new ArrayList<>();
		}
		list = Objects.requireNonNull( list );
		for ( V value : values ) {
			list.add( value );
		}
		if ( list_was_null && ! list.isEmpty() ) {
			this.map.put( key, list );
		}
	}
	
	/**
	 * Stores a key-value pair in this multimap for each of values, all using the same key, key. 
	 * @param key
	 * @param values
	 * @return
	 */
	public void addAllEntries( Iterable< ? extends Map.Entry< K, V > > values ) {
		values = Objects.requireNonNull( values );
		for ( Map.Entry< K, V > value : values ) {
			this.add( value.getKey(), value.getValue() );
		}
	}
	
	/**
	 * Stores all key-value pairs of multimap in this multimap, in the order 
	 * returned by starmap.entriesAsList().
	 */
	public void addAll( StarMap< ? extends K, ? extends V > starmap ) {
		if ( starmap != null && ! starmap.isEmpty() ) {
			for ( Pair< ? extends K, ? extends V > e : starmap.entriesAsList() ) {
				this.add( e.getFirst(), e.getSecond() );
			}
		}
	}
	
	/** 
	 * Removes a single key-value pair with the key key and the value value 
	 * from this multimap, if such exists.
	 */
	public void removeEntry( Object key, Object value ) {
		final List< V > list = this.map.get( key );
		if ( list != null ) {
			list.remove( value );
			if ( list.isEmpty() ) {
				this.map.remove( key );
			}
		}
	}
	
	/** 
	 * Removes a single key-value pair with the key key and the Nth value 
	 * from this multimap, if such exists.
	 */
	public void removeEntryAt( Object key, int N ) {
		final List< V > list = this.map.get( key );
		if ( list != null && 0 <= N && N < list.size() ) {
			list.remove( N );
		}
	}	
	
	/**
	 * Removes all values associated with the key key.
	 * @param key
	 * @return
	 */
	public void removeEntries( Object key ) {
		this.map.remove( key );
	}
	
	/**
	 * Stores a collection of values with the same key, replacing any existing 
	 * values for that key.
	 * @param key
	 * @param values
	 * @return
	 */
	public void setValues( K key, Iterable<? extends V> values ) {
		List< V > list = this.map.get( key );
		final boolean list_was_null = list == null;
		if ( list_was_null ) {
			list = new ArrayList<>();
		}
		list = Objects.requireNonNull( list );
		list.clear();
		for ( V value : values ) {
			list.add( value );
		}
		if ( list_was_null && ! list.isEmpty() ) {
			this.map.put( key, list );
		}
	}
	
	/**
	 * Updates the first key-value pair in this multimap.
	 */
	public void set( K key, V value ) {
		List< V > list = this.map.get( key );
		if ( list == null ) {
			list = new ArrayList<>();
			this.map.put( key, list );
		}
		if ( list.isEmpty() ) {
			list.add( value );
		} else {
			list.set( 0, value );
		}
	}
	
	/**
	 * Updates the Nth key-value pair in this starmap, where
	 * 0 <= N && N <= size(); // Note may be equal to size. 
	 */
	public void set( K key, int N, V value ) {
		List< V > list = this.map.get( key );
		if ( list == null ) {
			list = new ArrayList<>();
			this.map.put( key, list );
		}
		if ( 0 <= N && N < list.size() ) {
			list.set( N, value );
		} else if ( N == list.size() ) {
			list.add( value );
		} else {
			throw new IllegalArgumentException( String.format( "Cannot assign at this position (%d) for this key (%s)", N, key ) );
		}
	}
	

	
	/**
	 * Returns the number of key-value pairs in this multimap.
	 */
	public int size() {
		int sum = 0;
		for ( List< V > list : this.map.values() ) {
			sum += list.size();
		}
		return sum;
	}
	
	/**
	 * Returns a list containing the value from each key-value pair 
	 * contained in this multimap, without collapsing duplicates (so values().size() == size()). 
	 */
	public List<V> values() {
		final List< V > result = new ArrayList<>();
		for ( List< V > list : this.map.values() ) {
			result.addAll( list );
		}
		return result;	
	}
	
	@Override
	public int hashCode() {
		return this.map.hashCode();
	}
	
}
