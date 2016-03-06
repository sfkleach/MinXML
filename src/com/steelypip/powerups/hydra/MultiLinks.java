package com.steelypip.powerups.hydra;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.common.Pair;
import com.steelypip.powerups.util.StarMap;

/**
 * A kind of multi-map where:
 * 		- An entry is called a Link
 * 		- the field part is called the Field
 * 		- the value part is called the Child
 */
public interface MultiLinks< Field extends Comparable< Field >, Value > {
	
	@NonNull Field defaultField();   
		
	Value getChild() throws IllegalArgumentException;
	Value getChild( int index ) throws IllegalArgumentException;
	
	Value getChild( @NonNull Field field ) throws IllegalArgumentException;
	Value getChild( @NonNull Field field, int index ) throws IllegalArgumentException;
	
	@Nullable Value getChild( @NonNull Field field, @Nullable Value otherwise );
	@Nullable Value getChild( @NonNull Field field, int index, @Nullable Value otherwise );

	void setChild( @NonNull Field field, @NonNull Value value ) throws UnsupportedOperationException;
	void setChild( @NonNull Field field, int index, @NonNull Value value ) throws IllegalArgumentException, UnsupportedOperationException;
	
	void setAllChildren( @NonNull Field field, Iterable< @NonNull Value > values ) throws UnsupportedOperationException;
	
	void addChild( @NonNull Field field, @NonNull Value value ) throws UnsupportedOperationException;
	void addChild( @NonNull Value value ) throws UnsupportedOperationException;
	
	void removeChild( @NonNull Field key ) throws UnsupportedOperationException, IndexOutOfBoundsException;
	void removeChild( @NonNull Field key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException;
	void clearLinks( @NonNull Field key ) throws UnsupportedOperationException;
	void clearAllLinks() throws UnsupportedOperationException;

	boolean hasNoLinks();
	default boolean hasAnyLinks() {
		return ! this.hasNoLinks();
	}
	int sizeLinks();
	default boolean hasSizeLinks( int n ) {
		return this.sizeLinks() == n;
	}
	boolean hasLink( @NonNull Field field );
	boolean hasLink( @NonNull Field field, int index );
	boolean hasLink( @NonNull Field field, @Nullable Value value );
	boolean hasLink( @NonNull Field field, int index, @Nullable Value value );
	default boolean hasOneChild( @NonNull Field field ) {
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
	
	int sizeChildren( @NonNull Field field );
	default boolean hasSizeChildren( @NonNull Field field, int n ) {
		return this.sizeChildren( field ) == n;
	}
	boolean hasNoChildren( @NonNull Field field );
	default boolean hasChildren( @NonNull Field field ) {
		return ! this.hasNoChildren( field );
	}

	@NonNull Set< @NonNull Field > fieldsToSet();
	@NonNull List< Link< Field, Value > > linksToList();
	@NonNull List< @NonNull Value > childrenToList( @NonNull String field );
	Map< @NonNull Field, Value > firstChildrenToMap();
	StarMap< @NonNull Field, @NonNull Value > linksToStarMap();
	Map< Pair< @NonNull Field, @NonNull Integer >, Value > linksToPairMap();
	
}
