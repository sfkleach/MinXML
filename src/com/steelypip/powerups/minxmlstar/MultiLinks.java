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
 * 		- An entry is called a Link
 * 		- the field part is called the Field
 * 		- the value part is called the Child
 */
public interface MultiLinks {
		
	@NonNull MinXMLStar getChild() throws IllegalArgumentException;
	@NonNull MinXMLStar getChild( int index ) throws IllegalArgumentException;
	
	@NonNull MinXMLStar getChild( @NonNull String field ) throws IllegalArgumentException;
	@NonNull MinXMLStar getChild( @NonNull String field, int index ) throws IllegalArgumentException;
	
	@Nullable MinXMLStar getChild( @NonNull String field, @Nullable MinXMLStar otherwise );
	@Nullable MinXMLStar getChild( @NonNull String field, int index, @Nullable MinXMLStar otherwise );

	void setChild( @NonNull String field, @NonNull MinXMLStar value ) throws UnsupportedOperationException;
	void setChild( @NonNull String field, int index, @NonNull MinXMLStar value ) throws IllegalArgumentException, UnsupportedOperationException;
	
	void setAllChildren( @NonNull String field, Iterable< @NonNull MinXMLStar > values ) throws UnsupportedOperationException;
	
	void addChild( @NonNull String field, @NonNull MinXMLStar value ) throws UnsupportedOperationException;
	
	void removeChild( @NonNull String key ) throws UnsupportedOperationException, IndexOutOfBoundsException;
	void removeChild( @NonNull String key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException;
	void clearLinks( @NonNull String key ) throws UnsupportedOperationException;
	void clearAllLinks() throws UnsupportedOperationException;

	boolean hasAnyLinks();
	boolean hasNoLinks();
	boolean hasLink( @NonNull String field );
	boolean hasLink( @NonNull String field, int index );
	boolean hasLink( @NonNull String field, @Nullable MinXMLStar value );
	boolean hasLink( @NonNull String field, int index, @Nullable MinXMLStar value );
	boolean hasSingleChild( @NonNull String field );
	int sizeLinks();
	
	int sizeFields();
	boolean hasSizeFields( int n );
	boolean hasNoFields();
	boolean hasFields();
	
	int sizeChildren( @NonNull String field );
	boolean hasSizeChildren( @NonNull String field, int n );
	boolean hasNoChildren( @NonNull String field );
	boolean hasChildren( @NonNull String field );
	
	@NonNull Set< @NonNull String > fieldsToSet();
	@NonNull List< MinXMLStar.Link > linksToList();
	@NonNull List< @NonNull MinXMLStar > childrenToList( @NonNull String field );
	Map< @NonNull String, @NonNull MinXMLStar > firstChildrenToMap();
	StarMap< @NonNull String, ? extends @NonNull MinXMLStar > linksToStarMap();
	Map< Pair< @NonNull String, @NonNull Integer >, ? extends @NonNull MinXMLStar > linksToPairMap();
	
}
