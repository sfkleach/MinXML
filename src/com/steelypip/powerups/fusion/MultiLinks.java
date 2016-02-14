package com.steelypip.powerups.fusion;

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
		
	Fusion getChild() throws IllegalArgumentException;
	Fusion getChild( int index ) throws IllegalArgumentException;
	
	Fusion getChild( @NonNull String field ) throws IllegalArgumentException;
	Fusion getChild( @NonNull String field, int index ) throws IllegalArgumentException;
	
	Fusion getChild( @NonNull String field, @Nullable Fusion otherwise );
	Fusion getChild( @NonNull String field, int index, @Nullable Fusion otherwise );

	void setChild( @NonNull String field, @NonNull Fusion value ) throws UnsupportedOperationException;
	void setChild( @NonNull String field, int index, @NonNull Fusion value ) throws IllegalArgumentException, UnsupportedOperationException;
	
	void setAllChildren( @NonNull String field, Iterable< @NonNull Fusion > values ) throws UnsupportedOperationException;
	
	void addChild( @NonNull String field, @NonNull Fusion value ) throws UnsupportedOperationException;
	void addChild( @NonNull Fusion value ) throws UnsupportedOperationException;
	
	void removeChild( @NonNull String key ) throws UnsupportedOperationException, IndexOutOfBoundsException;
	void removeChild( @NonNull String key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException;
	void clearLinks( @NonNull String key ) throws UnsupportedOperationException;
	void clearAllLinks() throws UnsupportedOperationException;

	boolean hasNoLinks();
	default boolean hasAnyLinks() {
		return ! this.hasNoLinks();
	}
	int sizeLinks();
	default boolean hasSizeLinks( int n ) {
		return this.sizeLinks() == n;
	}
	boolean hasLink( @NonNull String field );
	boolean hasLink( @NonNull String field, int index );
	boolean hasLink( @NonNull String field, @Nullable Fusion value );
	boolean hasLink( @NonNull String field, int index, @Nullable Fusion value );
	default boolean hasOneChild( @NonNull String field ) {
		return this.hasSizeChildren( field, 1 );
	}
	
	int sizeFields();
	default boolean hasSizeFields( int n ) {
		return this.sizeFields() == n;
	}
	boolean hasNoFields();
	default boolean hasAnyFields() {
		return ! this.hasNoFields();
	}
	
	int sizeChildren( @NonNull String field );
	default boolean hasSizeChildren( @NonNull String field, int n ) {
		return this.sizeChildren( field ) == n;
	}
	boolean hasNoChildren( @NonNull String field );
	default boolean hasChildren( @NonNull String field ) {
		return ! this.hasNoChildren( field );
	}

	@NonNull Set< @NonNull String > fieldsToSet();
	@NonNull List< Fusion.Link > linksToList();
	@NonNull List< @NonNull Fusion > childrenToList( @NonNull String field );
	Map< @NonNull String, Fusion > firstChildrenToMap();
	StarMap< @NonNull String, ? extends @NonNull Fusion > linksToStarMap();
	Map< Pair< @NonNull String, @NonNull Integer >, ? extends Fusion > linksToPairMap();
	
}
