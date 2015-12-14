package com.steelypip.powerups.minxmlstar;

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

public class BadMinXMLStar implements MinXMLStar {

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
	public boolean hasAttribute( @NonNull String key, int index ) {
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
	public boolean hasSingleValue( @NonNull String key ) {
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
	public boolean hasKeys() {
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
	public boolean hasValues( @NonNull String key ) {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull List< MinXMLStar.Attr > attributesToList() {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull List< @NonNull String > valuesToList( @NonNull String key ) {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull Map< @NonNull String, @NonNull String > firstValuesToMap() {
		throw new IllegalStateException();
	}
	
	@Override
	public @NonNull StarMap< @NonNull String, @NonNull String > attributesToStarMap() {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull Map< Pair< @NonNull String, @NonNull Integer >, @NonNull String > attributesToPairMap() {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull MinXMLStar getChild() throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull MinXMLStar getChild( int index ) throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull MinXMLStar getChild( @NonNull String field ) throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull MinXMLStar getChild( @NonNull String field, int index ) throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public @Nullable MinXMLStar getChild( @NonNull String field, @Nullable MinXMLStar otherwise ) {
		throw new IllegalStateException();
	}

	@Override
	public @Nullable MinXMLStar getChild( @NonNull String field, int index, @Nullable MinXMLStar otherwise ) {
		throw new IllegalStateException();
	}

	@Override
	public void setChild( @NonNull String field, @NonNull MinXMLStar value ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void setChild( @NonNull String field, int index, @NonNull MinXMLStar value ) throws IllegalArgumentException, UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void setAllChildren( @NonNull String field, Iterable< @NonNull MinXMLStar > values ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void addChild( @NonNull String field, @NonNull MinXMLStar value ) throws UnsupportedOperationException {
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
	public boolean hasLink( @NonNull String field, @Nullable MinXMLStar value ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasLink( @NonNull String field, int index, @Nullable MinXMLStar value ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasSingleChild( @NonNull String field ) {
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
	public boolean hasFields() {
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
	public @NonNull List< MinXMLStar.Link > linksToList() {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull List< @NonNull MinXMLStar > childrenToList( @NonNull String field ) {
		throw new IllegalStateException();
	}

	@Override
	public Map< @NonNull String, @NonNull MinXMLStar > firstChildrenToMap() {
		throw new IllegalStateException();
	}

	@Override
	public StarMap< @NonNull String, ? extends @NonNull MinXMLStar > linksToStarMap() {
		throw new IllegalStateException();
	}

	@Override
	public Map< Pair< @NonNull String, @NonNull Integer >, ? extends @NonNull MinXMLStar > linksToPairMap() {
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
	public @NonNull MinXMLStar shallowCopy() {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull MinXMLStar deepCopy() {
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
	public void addChild( @NonNull MinXMLStar value ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

}
