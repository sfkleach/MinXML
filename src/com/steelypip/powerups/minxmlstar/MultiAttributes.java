package com.steelypip.powerups.minxmlstar;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.common.Pair;
import com.steelypip.powerups.common.StdPair;
import com.steelypip.powerups.util.StarMap;

/**
 * A kind of multi-map where:
 * 		- An entry is called an Attribute
 * 		- the key part is called the Key
 * 		- the value part is called the Value
 */
public interface MultiAttributes {
	
	
	/**
	 * Gets the first attribute value associated with a given key. If
	 * this does not have an attribute of that name it throws an
	 * IllegalArgumentException.
	 * 
	 * @param key the attribute key being looked up
	 * @return the associated value or null
	 * @throws IllegalArgumentException
	 */
	String getValue( @NonNull String key ) throws IllegalArgumentException;
	
	/**
	 * Gets the attribute value at position index associated with a given key. 
	 * If this does not have an attribute of that name or the index
	 * is out of bouds it throws an IllegalArgumentException.
	 * 
	 * @param key the attribute key being looked up
	 * @return the associated value or null
	 * @throws IllegalArgumentException
	 */	
	String getValue( @NonNull String key, int index ) throws IllegalArgumentException;

	/**
	 * Gets the first attribute value associated with a given key. If
	 * this does not have an attribute of that name it returns 
	 * the supplied value_otherwise.
	 * 
	 * @param key the attribute key being looked up
	 * @param value_otherwise the value to be returned if there is no matching attribute
	 * @return the associated value
	 */	
	@Nullable String getValue( @NonNull String key, @Nullable String otherwise );

	/**
	 * Gets the attribute at position index value associated with a given key. If this
	 * does not have an attribute of that name or the index is out of bounds then it
	 * returns the supplied value_otherwise.
	 * 
	 * @param key the attribute key being looked up
	 * @param value_otherwise the value to be returned if there is no matching attribute
	 * @return the associated value
	 */	
	@Nullable String getValue( @NonNull String key, int index, @Nullable String otherwise );

	void setValue( @NonNull String key, @NonNull String value ) throws UnsupportedOperationException;
	void setValue( @NonNull String key, int index, @NonNull String value ) throws IllegalArgumentException, UnsupportedOperationException;
	
	void setAllValues( @NonNull String key, Iterable< @NonNull String > values ) throws UnsupportedOperationException;
	
	void addValue( @NonNull String key, @NonNull String value ) throws UnsupportedOperationException;

	void removeValue( @NonNull String key ) throws UnsupportedOperationException, IndexOutOfBoundsException;
	void removeValue( @NonNull String key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException;
	void clearAttributes( @NonNull String key ) throws UnsupportedOperationException;
	void clearAllAttributes() throws UnsupportedOperationException;

	
	/**
	 * Returns true if this has at least one attribute. Synonymous
	 * with this.sizeAttributes() > 0
	 * 
	 * @return true if this has one or more attributes.
	 */
	boolean hasAnyAttributes();
	
	boolean hasNoAttributes();
	
	/**
	 * Returns true if this has an attribute with the given key and
	 * value.
	 * 
	 * @param key the attribute key
	 * @param value the attribute value
	 * @return true if this has an attribute with key @key and value @value
	 */
	boolean hasAttribute( @NonNull String key );
	
	/**
	 * Returns true if this has at least index+1 attributes with the given key,
	 * implying this.hasValue( key, index ) is true.
	 * 
	 * @param key the attribute key
	 * @param index the position being looked for
	 * @return true if this has at least index+1 attributes with key @key 
	 */
	boolean hasAttribute( @NonNull String key, int index );
	
	/**
	 * Returns true if this has an attribute with the given key and
	 * value.
	 * 
	 * @param key the attribute key
	 * @param value the attribute value
	 * @return true if this has an attribute with key @key and value @value
	 */
	boolean hasAttribute( @NonNull String key, @Nullable String value );
	
	/**
	 * Returns true if this the Nth occurence of an attribute with the given key has
	 * value @value, where N = index + 1. 
	 * 
	 * @param key the attribute key
	 * @param value the attribute value
	 * @return true if this has an attribute with key @key and value @value
	 */
	boolean hasAttribute( @NonNull String key, int index, @Nullable String value );
	
	/**
	 * Returns true if there is exactly one attribute whose key is @key (i.e. not zero or
	 * multiple attributes).
	 * 
	 * @param key the attribute key
	 * @return true if the element has an attribute with key @key and value @value
	 */	
	boolean hasSingleValue( @NonNull String key );
	
	/**
	 * Returns the number of attributes i.e. the number of key-value pairs.
	 * @return the number of key-value pairs.
	 */
	int sizeAttributes();
	
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
	boolean hasSizeKeys( int n );

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
	boolean hasKeys();
	
	int sizeValues( @NonNull String key );
	boolean hasSizeValues( @NonNull String key, int n );
	boolean hasNoValues( @NonNull String key );
	boolean hasValues( @NonNull String key );
	
	@NonNull Set< @NonNull String > keysToSet();
	@NonNull List< MinXMLStar.Attr > attributesToList();
	@NonNull List< @NonNull String > valuesToList( @NonNull String key );
	@NonNull Map< @NonNull String, @NonNull String > firstValuesToMap();
	@NonNull StarMap< @NonNull String, @NonNull String > attributesToStarMap();
	@NonNull Map< Pair< @NonNull String, @NonNull Integer >, @NonNull String > attributesToPairMap();
	
}
