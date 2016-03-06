package com.steelypip.powerups.hydra;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.common.Pair;
import com.steelypip.powerups.util.StarMap;

public class BadHydra< Key extends Comparable< Key >, Value, Field extends Comparable< Field >, Child  > implements Hydra< Key, Value, Field, Child > {

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

//	@Override
//	public int compareTo( Named o ) {
//		throw new IllegalStateException();
//	}

	@Override
	public Value getValue( @NonNull Key key ) throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public Value getValue( @NonNull Key key, int index ) throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public @Nullable Value getValue( @NonNull Key key, @Nullable Value otherwise ) {
		throw new IllegalStateException();
	}

	@Override
	public @Nullable Value getValue( @NonNull Key key, int index, @Nullable Value otherwise ) {
		throw new IllegalStateException();
	}

	@Override
	public void setValue( @NonNull Key key, @NonNull Value value ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void setValue( @NonNull Key key, int index, @NonNull Value value ) throws IllegalArgumentException, UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void setAllValues( @NonNull Key key, Iterable< @NonNull Value > values ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void addValue( @NonNull Key key, @NonNull Value value ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void removeValue( @NonNull Key key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new IllegalStateException();
	}

	@Override
	public void removeValue( @NonNull Key key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new IllegalStateException();
	}

	@Override
	public void clearAttributes( @NonNull Key key ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void clearAllAttributes() throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public int sizeAttributes() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasNoAttributes() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasAttribute( @NonNull Key key ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasValueAt( @NonNull Key key, int index ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasAttribute( @NonNull Key key, @Nullable String value ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasAttribute( @NonNull Key key, int index, @Nullable String value ) {
		throw new IllegalStateException();
	}

	@Override
	public int sizeKeys() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasNoKeys() {
		throw new IllegalStateException();
	}

	@Override
	public int sizeValues( @NonNull Key key ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasSizeValues( @NonNull Key key, int n ) {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull Set< @NonNull Key > keysToSet() {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull List< Attribute< Key, Value > > attributesToList() {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull List< @NonNull Value > valuesToList( @NonNull Key key ) {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull Map< @NonNull Key, Value > firstValuesToMap() {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull StarMap< @NonNull Key, @Nullable Value > attributesToStarMap() {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull Map< Pair< @NonNull Key, @NonNull Integer >, Value > attributesToPairMap() {
		throw new IllegalStateException();
	}

	@Override
	public Field defaultField() {
		throw new IllegalStateException();
	}

	@Override
	public Child getChild() throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public Child getChild( int index ) throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public Child getChild( @NonNull Field field ) throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public Child getChild( @NonNull Field field, int index ) throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public @Nullable Child getChild( @NonNull Field field, @Nullable Child otherwise ) {
		throw new IllegalStateException();
	}

	@Override
	public @Nullable Child getChild( @NonNull Field field, int index, @Nullable Child otherwise ) {
		throw new IllegalStateException();
	}

	@Override
	public void setChild( @NonNull Field field, @NonNull Child value ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void setChild( @NonNull Field field, int index, @NonNull Child value ) throws IllegalArgumentException, UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void setAllChildren( @NonNull Field field, Iterable< @NonNull Child > values ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void addChild( @NonNull Field field, @NonNull Child value ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void addChild( @NonNull Child value ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void removeChild( @NonNull Field key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new IllegalStateException();
	}

	@Override
	public void removeChild( @NonNull Field key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new IllegalStateException();
	}

	@Override
	public void clearLinks( @NonNull Field key ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void clearAllLinks() throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasNoLinks() {
		throw new IllegalStateException();
	}

	@Override
	public int sizeLinks() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasLink( @NonNull Field field ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasLink( @NonNull Field field, int index ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasLink( @NonNull Field field, @Nullable Child value ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasLink( @NonNull Field field, int index, @Nullable Child value ) {
		throw new IllegalStateException();
	}

	@Override
	public int sizeFields() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasNoFields() {
		throw new IllegalStateException();
	}

	@Override
	public int sizeChildren( @NonNull Field field ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasNoChildren( @NonNull Field field ) {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull Set< @NonNull Field > fieldsToSet() {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull List< Link< Field, Child > > linksToList() {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull List< @NonNull Child > childrenToList( @NonNull String field ) {
		throw new IllegalStateException();
	}

	@Override
	public Map< @NonNull Field, Child > firstChildrenToMap() {
		throw new IllegalStateException();
	}

	@Override
	public StarMap< @NonNull Field, @NonNull Child > linksToStarMap() {
		throw new IllegalStateException();
	}

	@Override
	public Map< Pair< @NonNull Field, @NonNull Integer >, Child > linksToPairMap() {
		throw new IllegalStateException();
	}

	@Override
	public Iterator< Link< Field, Child > > iterator() {
		throw new IllegalStateException();
	}

//	@Override
//	public @NonNull Hydra< Key, Value, Field, Child > shallowCopy() {
//		throw new IllegalStateException();
//	}
//
//	@Override
//	public @NonNull Hydra< Key, Value, Field, Child > deepCopy() {
//		throw new IllegalStateException();
//	}
	


}
