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

public class BadMinXMLStar implements Fusion {
	
	@Override
	public @NonNull String getName() {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull String getInternedName() {
		throw new IllegalStateException();

	}

	@Override
	public boolean hasName( @Nullable String name ) {
		throw new IllegalStateException();

	}

	@Override
	public void setName( @NonNull String x ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public String getValue( @NonNull String key ) throws IllegalArgumentException {
		throw new IllegalStateException();

	}

	@Override
	public String getValue( @NonNull String key, int index ) throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public @Nullable String getValue( @NonNull String key, @Nullable String otherwise ) {
		throw new IllegalStateException();
	}

	@Override
	public @Nullable String getValue( @NonNull String key, int index, @Nullable String otherwise ) {
		throw new IllegalStateException();
	}

	@Override
	public void setValue( @NonNull String key, @NonNull String value ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void setValue( @NonNull String key, int index, @NonNull String value ) throws IllegalArgumentException, UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void setAllValues( @NonNull String key, Iterable< @NonNull String > values ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void addValue( @NonNull String key, @NonNull String value ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void removeValue( @NonNull String key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new IllegalStateException();
	}

	@Override
	public void removeValue( @NonNull String key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new IllegalStateException();
	}

	@Override
	public void clearAttributes( @NonNull String key ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void clearAllAttributes() throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasAnyAttributes() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasNoAttributes() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasAttribute( @NonNull String key ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasValueAt( @NonNull String key, int index ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasAttribute( @NonNull String key, @Nullable String value ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasAttribute( @NonNull String key, int index, @Nullable String value ) {
		throw new IllegalStateException();

	}

	@Override
	public boolean hasOneValue( @NonNull String key ) {
		throw new IllegalStateException();
	}

	@Override
	public int sizeAttributes() {
		throw new IllegalStateException();
	}

	@Override
	public int sizeKeys() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasSizeKeys( int n ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasNoKeys() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasAnyKeys() {
		throw new IllegalStateException();
	}

	@Override
	public int sizeValues( @NonNull String key ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasSizeValues( @NonNull String key, int n ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasNoValues( @NonNull String key ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasAnyValues( @NonNull String key ) {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull List< Fusion.Attr > attributesToList() {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull List< @NonNull String > valuesToList( @NonNull String key ) {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull Map< @NonNull String, String > firstValuesToMap() {
		throw new IllegalStateException();
	}
	
	@Override
	public @NonNull StarMap< @NonNull String, @Nullable String > attributesToStarMap() {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull Map< Pair< @NonNull String, @NonNull Integer >, String > attributesToPairMap() {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull Fusion getChild() throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull Fusion getChild( int index ) throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull Fusion getChild( @NonNull String field ) throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull Fusion getChild( @NonNull String field, int index ) throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public @Nullable Fusion getChild( @NonNull String field, @Nullable Fusion otherwise ) {
		throw new IllegalStateException();
	}

	@Override
	public @Nullable Fusion getChild( @NonNull String field, int index, @Nullable Fusion otherwise ) {
		throw new IllegalStateException();
	}

	@Override
	public void setChild( @NonNull String field, @NonNull Fusion value ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void setChild( @NonNull String field, int index, @NonNull Fusion value ) throws IllegalArgumentException, UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void setAllChildren( @NonNull String field, Iterable< @NonNull Fusion > values ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void addChild( @NonNull String field, @NonNull Fusion value ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void removeChild( @NonNull String key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new IllegalStateException();
	}

	@Override
	public void removeChild( @NonNull String key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new IllegalStateException();
	}

	@Override
	public void clearLinks( @NonNull String key ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void clearAllLinks() throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasAnyLinks() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasNoLinks() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasLink( @NonNull String field ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasLink( @NonNull String field, int index ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasLink( @NonNull String field, @Nullable Fusion value ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasLink( @NonNull String field, int index, @Nullable Fusion value ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasOneChild( @NonNull String field ) {
		throw new IllegalStateException();
	}

	@Override
	public int sizeLinks() {
		throw new IllegalStateException();
	}

	@Override
	public int sizeFields() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasSizeFields( int n ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasNoFields() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasAnyFields() {
		throw new IllegalStateException();
	}

	@Override
	public int sizeChildren( @NonNull String field ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasSizeChildren( @NonNull String field, int n ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasNoChildren( @NonNull String field ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasChildren( @NonNull String field ) {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull List< Fusion.Link > linksToList() {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull List< @NonNull Fusion > childrenToList( @NonNull String field ) {
		throw new IllegalStateException();
	}

	@Override
	public Map< @NonNull String, Fusion > firstChildrenToMap() {
		throw new IllegalStateException();
	}

	@Override
	public StarMap< @NonNull String, ? extends @NonNull Fusion > linksToStarMap() {
		throw new IllegalStateException();
	}

	@Override
	public Map< Pair< @NonNull String, @NonNull Integer >, ? extends @NonNull Fusion > linksToPairMap() {
		throw new IllegalStateException();
	}

	@Override
	public Iterator< Link > iterator() {
		throw new IllegalStateException();
	}

	@Override
	public void trimToSize() {
		throw new IllegalStateException();
	}

	@Override
	public void print( PrintWriter pw ) {
		throw new IllegalStateException();
	}

	@Override
	public void print( Writer w ) {
		throw new IllegalStateException();
	}

	@Override
	public void prettyPrint( PrintWriter pw ) {
		throw new IllegalStateException();
	}

	@Override
	public void prettyPrint( Writer w ) {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull Fusion shallowCopy() {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull Fusion deepCopy() {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull Set< @NonNull String > keysToSet() {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull Set< @NonNull String > fieldsToSet() {
		throw new IllegalStateException();
	}

	@Override
	public void addChild( @NonNull Fusion value ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public boolean isInteger() {
		throw new IllegalStateException();
	}

	@Override
	public @Nullable Long integerValue() {
		throw new IllegalStateException();
	}

	@Override
	public long integerValue( long otherwise ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean isFloat() {
		throw new IllegalStateException();
	}

	@Override
	public @Nullable Double floatValue() {
		throw new IllegalStateException();
	}

	@Override
	public double floatValue( double otherwise ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean isString() {
		throw new IllegalStateException();
	}

	@Override
	public @Nullable String stringValue() {
		throw new IllegalStateException();
	}

	@Override
	public String stringValue( String otherwise ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean isBoolean() {
		throw new IllegalStateException();
	}

	@Override
	public @Nullable Boolean booleanValue() {
		throw new IllegalStateException();
	}

	@Override
	public boolean booleanValue( boolean otherwise ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean isNull() {
		throw new IllegalStateException();
	}

	@Override
	public boolean isArray() {
		throw new IllegalStateException();
	}

	@Override
	public boolean isObject() {
		throw new IllegalStateException();
	}

}
