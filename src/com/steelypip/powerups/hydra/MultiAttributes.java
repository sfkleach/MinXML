package com.steelypip.powerups.hydra;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.steelypip.powerups.common.Pair;
//import com.steelypip.powerups.util.StarMap;

/**
 * A kind of multi-map where:
 * 		- An entry is called an Attribute
 * 		- the key part is called the Key
 * 		- the value part is called the Value
 */
public interface MultiAttributes< Key extends Comparable< Key >, Value > {	
	
	/**
	 * Gets the first attribute value associated with a given key. If
	 * this does not have an attribute of that name it throws an
	 * IllegalArgumentException.
	 * 
	 * @param key the attribute key being looked up
	 * @return the associated value or null
	 * @throws IllegalArgumentException
	 */
	Value getValue( Key key ) throws IllegalArgumentException;

	/**
	 * Gets the attribute value at position index associated with a given key. 
	 * If this does not have an attribute of that name or the index
	 * is out of bouds it throws an IllegalArgumentException.
	 * 
	 * @param key the attribute key being looked up
	 * @return the associated value or null
	 * @throws IllegalArgumentException
	 */	
	Value getValue( Key key, int index ) throws IllegalArgumentException;

	/**
	 * Gets the first attribute value associated with a given key. If
	 * this does not have an attribute of that name it returns 
	 * the supplied value_otherwise.
	 * 
	 * @param key the attribute key being looked up
	 * @param value_otherwise the value to be returned if there is no matching attribute
	 * @return the associated value
	 */	
	Value getValue( Key key, Value otherwise );

	/**
	 * Gets the attribute at position index value associated with a given key. If this
	 * does not have an attribute of that name or the index is out of bounds then it
	 * returns the supplied value_otherwise.
	 * 
	 * @param key the attribute key being looked up
	 * @param value_otherwise the value to be returned if there is no matching attribute
	 * @return the associated value
	 */	
	Value getValue( Key key, int index, Value otherwise );

	void updateValue( Key key, int index, Value value ) throws IllegalArgumentException, UnsupportedOperationException;
	void setValue( Key key, Value value ) throws UnsupportedOperationException;
	void setAllValues( Key key, Iterable< Value > values ) throws UnsupportedOperationException;
	
	void addValue( Key key, Value value ) throws UnsupportedOperationException;

	void removeValue( Key key ) throws UnsupportedOperationException, IndexOutOfBoundsException;
	void removeValue( Key key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException;
	void clearAttributes( Key key ) throws UnsupportedOperationException;
	void clearAllAttributes() throws UnsupportedOperationException;

	/**
	 * Returns the number of attributes i.e. the number of key-value pairs.
	 * @return the number of key-value pairs.
	 */
	int sizeAttributes();
	
	default boolean hasSizeAttributes( int n ) {
		return this.sizeAttributes() == n;
	}
	

	/**
	 * Returns true if this has at least one attribute. Synonymous
	 * with this.sizeAttributes() > 0
	 * 
	 * @return true if this has one or more attributes.
	 */
	default boolean hasAnyAttributes() {
		return ! this.hasNoAttributes();
	}
	
	boolean hasNoAttributes();
	
	/**
	 * Returns true if this has an attribute with the given key and
	 * value.
	 * 
	 * @param key the attribute key
	 * @param value the attribute value
	 * @return true if this has an attribute with key @key and value @value
	 */
	boolean hasAttribute( Key key );
	
	/**
	 * Returns true if this has at least index+1 attributes with the given key,
	 * implying this.hasValue( key, index ) is true.
	 * 
	 * @param key the attribute key
	 * @param index the position being looked for
	 * @return true if this has at least index+1 attributes with key @key 
	 */
	boolean hasValueAt( Key key, int index );
	
	/**
	 * Returns true if this has an attribute with the given key and
	 * value.
	 * 
	 * @param key the attribute key
	 * @param value the attribute value
	 * @return true if this has an attribute with key @key and value @value
	 */
	boolean hasAttribute( Key key, Value value );
	
	/**
	 * Returns true if this the Nth occurence of an attribute with the given key has
	 * value @value, where N = index + 1. 
	 * 
	 * @param key the attribute key
	 * @param value the attribute value
	 * @return true if this has an attribute with key @key and value @value
	 */
	boolean hasAttribute( Key key, int index, Value value );
	
	/**
	 * Returns true if there is exactly one attribute whose key is @key (i.e. not zero or
	 * multiple attributes).
	 * 
	 * @param key the attribute key
	 * @return true if the element has an attribute with key @key and value @value
	 */	
	default boolean hasOneValue( Key key ) {
		return this.hasSizeValues( key, 1 );		
	}
	
	/**
	 * Returns the number of distinct keys.
	 * @return the number of distinct keys.
	 */
	int sizeKeys();
	
	/**
	 * Returns true if there are n distinct keys. Synonymous with
	 * this.sizeKeys() == n
	 * @param n
	 * @return true if there are n distinct keys, else false.
	 */
	default boolean hasSizeKeys( final int n ) {
		return this.sizeKeys() == n;
	}

	/**
	 * Returns true if there no keys. Synonymous with
	 * this.sizeKeys() == 0 and also synonymous with this.hasNoAttributes().
	 * @param n
	 * @return true if there no keys (and hence no attributes)
	 */
	boolean hasNoKeys();

	/**
	 * Returns true if there are keys. Synonymous with
	 * this.sizeKeys() > 0 and also synonymous with this.hasAttributes().
	 * @param n
	 * @return true if there is one or more keys and hence one or more attributes.
	 */
	default boolean hasAnyKeys() {
		return ! this.hasNoKeys();
	}
	
	int sizeValues( Key key );
	boolean hasSizeValues( Key key, int n );
	default boolean hasNoValues( Key key ) {
		return ! this.hasAttribute( key );
	}
	default boolean hasAnyValues( Key key ) {
		return this.hasAttribute( key );
	}

	
	Set< Key > keysToSet();
	List< Map.Entry< Key, Value > > attributesToList();
	List< Value > valuesToList( Key key );
	Map< Key, Value > firstValuesToMap();
//	StarMap< Key, Value > attributesToStarMap();
	Map< Pair< Key, Integer >, Value > attributesToPairMap();
	
}
