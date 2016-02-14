package com.steelypip.powerups.fusion;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.common.Pair;
import com.steelypip.powerups.util.StarMap;

public class EvolvingProxyFusion implements MutableFusion {

	private @NonNull Fusion origin;

	public EvolvingProxyFusion( @NonNull Fusion origin ) {
		super();
		this.origin = origin;
	}

	public void evolve() {
		//	TODO: this is very crude.
		this.origin = FlexiFusion.deepCopy( this.origin );
	}

	public @NonNull String getName() {
		return origin.getName();
	}

	public @NonNull String getInternedName() {
		return origin.getInternedName();
	}

	public Fusion getChild() throws IllegalArgumentException {
		return origin.getChild();
	}

	public String getValue( @NonNull String key ) throws IllegalArgumentException {
		return origin.getValue( key );
	}

	public boolean hasName( @Nullable String name ) {
		return origin.hasName( name );
	}

	public Fusion getChild( int index ) throws IllegalArgumentException {
		return origin.getChild( index );
	}

	public Fusion getChild( @NonNull String field ) throws IllegalArgumentException {
		return origin.getChild( field );
	}

	public Fusion getChild( @NonNull String field, int index ) throws IllegalArgumentException {
		return origin.getChild( field, index );
	}

	public Iterator< Link > iterator() {
		return origin.iterator();
	}

	public @Nullable Fusion getChild( @NonNull String field, @Nullable Fusion otherwise ) {
		return origin.getChild( field, otherwise );
	}

	public String getValue( @NonNull String key, int index ) throws IllegalArgumentException {
		return origin.getValue( key, index );
	}

	public @Nullable Fusion getChild( @NonNull String field, int index, @Nullable Fusion otherwise ) {
		return origin.getChild( field, index, otherwise );
	}
	
	public void setName( @NonNull String x ) throws UnsupportedOperationException {
		try {
			origin.setName( x );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve();
			this.setName( x );
		}
	}

	public void setChild( @NonNull String field, @NonNull Fusion value ) throws UnsupportedOperationException {
		try {
			origin.setChild( field, value );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve();
			this.setChild( field, value );
		}
	}

	public void setChild( @NonNull String field, int index, @NonNull Fusion value ) throws IllegalArgumentException, UnsupportedOperationException {
		try {
			origin.setChild( field, index, value );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve();
			origin.setChild( field, index, value );
		}
	}

	public void setAllChildren( @NonNull String field, Iterable< @NonNull Fusion > values ) throws UnsupportedOperationException {
		try {
			origin.setAllChildren( field, values );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve();
			this.setAllChildren( field, values );
		}
	}

	public @Nullable String getValue( @NonNull String key, @Nullable String otherwise ) {
		return origin.getValue( key, otherwise );
	}

	public void addChild( @NonNull String field, @NonNull Fusion value ) throws UnsupportedOperationException {
		try {
			origin.addChild( field, value );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve();
			this.addChild( field, value );
		}
	}

	public void addChild( @NonNull Fusion value ) throws UnsupportedOperationException {
		try {
			origin.addChild( value );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve();
			this.addChild( value );
		}
	}

	public void removeChild( @NonNull String key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		try {
			origin.removeChild( key );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve();
			this.removeChild( key );
		}
	}

	public void removeChild( @NonNull String key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		try {
			origin.removeChild( key, index );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve();
			this.removeChild( key, index );
		}
	}

	public @Nullable String getValue( @NonNull String key, int index, @Nullable String otherwise ) {
		return origin.getValue( key, index, otherwise );
	}

	public void clearLinks( @NonNull String key ) throws UnsupportedOperationException {
		try {
			origin.clearLinks( key );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve();
			this.clearLinks( key );
		}
	}

	public void clearAllLinks() throws UnsupportedOperationException {
		try {
			origin.clearAllLinks();
		} catch ( UnsupportedOperationException _e ) {
			this.evolve();
			this.clearAllLinks();
		}
	}

	public boolean hasAnyLinks() {
		return origin.hasAnyLinks();
	}

	public boolean hasNoLinks() {
		return origin.hasNoLinks();
	}

	public boolean hasLink( @NonNull String field ) {
		return origin.hasLink( field );
	}

	public boolean hasLink( @NonNull String field, int index ) {
		return origin.hasLink( field, index );
	}

	public boolean hasLink( @NonNull String field, @Nullable Fusion value ) {
		return origin.hasLink( field, value );
	}

	public boolean hasLink( @NonNull String field, int index, @Nullable Fusion value ) {
		return origin.hasLink( field, index, value );
	}

	public void setValue( @NonNull String key, @NonNull String value ) throws UnsupportedOperationException {
		try {
			origin.setValue( key, value );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve();
			this.setValue( key, value );
		}
	}

	public boolean hasOneChild( @NonNull String field ) {
		return origin.hasOneChild( field );
	}

	public int sizeLinks() {
		return origin.sizeLinks();
	}

	public int sizeFields() {
		return origin.sizeFields();
	}

	public void setValue( @NonNull String key, int index, @NonNull String value ) throws IllegalArgumentException, UnsupportedOperationException {
		try {
			origin.setValue( key, index, value );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve();
			this.setValue( key, index, value );		
		}
	}

	public boolean hasSizeFields( int n ) {
		return origin.hasSizeFields( n );
	}

	public boolean hasNoFields() {
		return origin.hasNoFields();
	}

	public boolean hasAnyFields() {
		return origin.hasAnyFields();
	}

	public int sizeChildren( @NonNull String field ) {
		return origin.sizeChildren( field );
	}

	public boolean hasSizeChildren( @NonNull String field, int n ) {
		return origin.hasSizeChildren( field, n );
	}

	public void setAllValues( @NonNull String key, Iterable< @NonNull String > values ) throws UnsupportedOperationException {
		try {
			origin.setAllValues( key, values );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve();
			this.setAllValues( key, values );
		}
	}

	public boolean hasNoChildren( @NonNull String field ) {
		return origin.hasNoChildren( field );
	}

	public boolean hasChildren( @NonNull String field ) {
		return origin.hasChildren( field );
	}

	public void trimToSize() {
		origin.trimToSize();
	}

	public void addValue( @NonNull String key, @NonNull String value ) throws UnsupportedOperationException {
		try {
			origin.addValue( key, value );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve();
			this.addValue( key, value );
		}
	}

	public @NonNull Set< @NonNull String > fieldsToSet() {
		return origin.fieldsToSet();
	}

	public @NonNull List< Link > linksToList() {
		return origin.linksToList();
	}

	public void removeValue( @NonNull String key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		try {
			origin.removeValue( key );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve();
			this.removeValue( key );
		}
	}

	public @NonNull List< @NonNull Fusion > childrenToList( @NonNull String field ) {
		return origin.childrenToList( field );
	}

	public void removeValue( @NonNull String key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		try {
			origin.removeValue( key, index );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve();
			this.removeValue( key, index );
		}
	}

	public Map< @NonNull String, Fusion > firstChildrenToMap() {
		return origin.firstChildrenToMap();
	}

	public StarMap< @NonNull String, ? extends @NonNull Fusion > linksToStarMap() {
		return origin.linksToStarMap();
	}

	public void clearAttributes( @NonNull String key ) throws UnsupportedOperationException {
		origin.clearAttributes( key );
	}

	public Map< Pair< @NonNull String, @NonNull Integer >, ? extends Fusion > linksToPairMap() {
		return origin.linksToPairMap();
	}

	public void clearAllAttributes() throws UnsupportedOperationException {
		try {
			origin.clearAllAttributes();
		} catch ( UnsupportedOperationException _e ) {
			this.evolve();
			this.clearAllAttributes();
		}
	}

	public boolean hasAnyAttributes() {
		return origin.hasAnyAttributes();
	}

	public boolean equals( Object obj ) {
		return origin.equals( obj );
	}

	public void print( PrintWriter pw ) {
		origin.print( pw );
	}

	public boolean hasNoAttributes() {
		return origin.hasNoAttributes();
	}

	public boolean hasAttribute( @NonNull String key ) {
		return origin.hasAttribute( key );
	}

	public void print( Writer w ) {
		origin.print( w );
	}

	public void prettyPrint( PrintWriter pw ) {
		origin.prettyPrint( pw );
	}

	public boolean hasValueAt( @NonNull String key, int index ) {
		return origin.hasValueAt( key, index );
	}

	public void prettyPrint( Writer w ) {
		origin.prettyPrint( w );
	}

	public boolean hasAttribute( @NonNull String key, @Nullable String value ) {
		return origin.hasAttribute( key, value );
	}

	public @NonNull Fusion shallowCopy() {
		return origin.shallowCopy();
	}

	public boolean hasAttribute( @NonNull String key, int index, @Nullable String value ) {
		return origin.hasAttribute( key, index, value );
	}

	public @NonNull Fusion deepCopy() {
		return origin.deepCopy();
	}

	public boolean hasOneValue( @NonNull String key ) {
		return origin.hasOneValue( key );
	}

	public int sizeAttributes() {
		return origin.sizeAttributes();
	}

	public int sizeKeys() {
		return origin.sizeKeys();
	}

	public boolean hasSizeKeys( int n ) {
		return origin.hasSizeKeys( n );
	}

	public boolean hasNoKeys() {
		return origin.hasNoKeys();
	}

	public boolean hasAnyKeys() {
		return origin.hasAnyKeys();
	}

	public int sizeValues( @NonNull String key ) {
		return origin.sizeValues( key );
	}

	public boolean hasSizeValues( @NonNull String key, int n ) {
		return origin.hasSizeValues( key, n );
	}

	public boolean hasNoValues( @NonNull String key ) {
		return origin.hasNoValues( key );
	}

	public boolean hasAnyValues( @NonNull String key ) {
		return origin.hasAnyValues( key );
	}

	public @NonNull Set< @NonNull String > keysToSet() {
		return origin.keysToSet();
	}

	public @NonNull List< Attr > attributesToList() {
		return origin.attributesToList();
	}

	public @NonNull List< @NonNull String > valuesToList( @NonNull String key ) {
		return origin.valuesToList( key );
	}

	public @NonNull Map< @NonNull String, String > firstValuesToMap() {
		return origin.firstValuesToMap();
	}

	public @NonNull StarMap< @NonNull String, @Nullable String > attributesToStarMap() {
		return origin.attributesToStarMap();
	}

	public @NonNull Map< Pair< @NonNull String, @NonNull Integer >, String > attributesToPairMap() {
		return origin.attributesToPairMap();
	}

	public boolean isInteger() {
		return origin.isInteger();
	}

	public @Nullable Long integerValue() {
		return origin.integerValue();
	}

	public long integerValue( long otherwise ) {
		return origin.integerValue( otherwise );
	}

	public boolean isFloat() {
		return origin.isFloat();
	}

	public @Nullable Double floatValue() {
		return origin.floatValue();
	}

	public double floatValue( double otherwise ) {
		return origin.floatValue( otherwise );
	}

	public boolean isString() {
		return origin.isString();
	}

	public @Nullable String stringValue() {
		return origin.stringValue();
	}

	public String stringValue( String otherwise ) {
		return origin.stringValue( otherwise );
	}

	public boolean isBoolean() {
		return origin.isBoolean();
	}

	public @Nullable Boolean booleanValue() {
		return origin.booleanValue();
	}

	public boolean booleanValue( boolean otherwise ) {
		return origin.booleanValue( otherwise );
	}

	public boolean isNull() {
		return origin.isNull();
	}

	public boolean isArray() {
		return origin.isArray();
	}

	public boolean isObject() {
		return origin.isObject();
	}

	public boolean hasSizeLinks( int n ) {
		return origin.hasSizeLinks( n );
	}

	public boolean hasSizeAttributes( int n ) {
		return origin.hasSizeAttributes( n );
	}

	
	
}
